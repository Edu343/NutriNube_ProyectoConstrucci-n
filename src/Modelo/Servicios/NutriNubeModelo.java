package Modelo.Servicios;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import Modelo.Core.Observer;
import Modelo.DAO.*;
import Modelo.POJOs.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

public class NutriNubeModelo {
	
    public enum LoginEstado { SUCCESS, WRONG_PASSWORD, NOT_FOUND, ERROR }

    
    private Set<Observer> observers;
    private NutriologoDAO nutriologoDAO;
    private PacienteDAO pacienteDAO;
    private ConsultaDAO consultaDAO;    
    private HashingServicio HashingServicio;
    private NutricionServicio nutricionServicio;
    private Validaciones validacionServicio;
    private MacronutrientesServicio macronutrientesServicio;
    
    private Nutriologo nutriologoActual;
    private Paciente pacienteSeleccionado;
    private Consulta consultaSeleccionada;

    public NutriNubeModelo() {
    	
        this.observers = new HashSet<>();
        this.nutriologoDAO = new NutriologoDAO();
        this.pacienteDAO = new PacienteDAO();
        this.consultaDAO = new ConsultaDAO();
        this.HashingServicio = new HashingServicio();
        this.nutricionServicio = new NutricionServicio();
        this.validacionServicio = new Validaciones();
        this.macronutrientesServicio = new MacronutrientesServicio();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String evento) {
        for (Observer observer : observers) {
                    observer.update();
            
        }
    }

    /**
     * Comprueba el estado del login sin alterar estado del modelo.
     * Devuelve SUCCESS, WRONG_PASSWORD o NOT_FOUND (o ERROR en excepción).
     */
    public LoginEstado obtenerEstadoLogin(String claveNutriologo, String contrasena) {
        try {
            Nutriologo n = nutriologoDAO.leerPorClave(claveNutriologo);
            if (n == null) return LoginEstado.NOT_FOUND;

            // verificamos usando hashingServicio
            boolean ok = HashingServicio.verificarContrasena(contrasena, n.getSaltContrasena(), n.getHashContrasena());
            return ok ? LoginEstado.SUCCESS : LoginEstado.WRONG_PASSWORD;

        } catch (SQLException e) {
            e.printStackTrace();
            return LoginEstado.ERROR;
        }
    }

    /**
     * Realiza el login: si existe y la contraseña coincide, guarda el nutriólogo
     * en memoria del modelo y notifica observers.
     *
     * @return true si login exitoso
     */
    public boolean login(String claveNutriologo, String contrasena) {
        LoginEstado estado = obtenerEstadoLogin(claveNutriologo, contrasena);

        if (estado == LoginEstado.SUCCESS) {
            try {
                this.nutriologoActual = nutriologoDAO.leerPorClave(claveNutriologo);
                this.pacienteSeleccionado = null;
                this.consultaSeleccionada = null;
                notifyObservers("LOGIN_SUCCESS");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    /**
     * Indica si existe el nutriólogo con la clave dada.
     */
    public boolean nutriologoExiste(String claveNutriologo) {
        try {
            return nutriologoDAO.leerPorClave(claveNutriologo) != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Crea un nuevo nutriólogo. Nota: en tu DAO la inserción ya hace hashing si
     * esa es la implementación que tienes; por eso aquí delegamos directamente.
     *
     * @param n Nutriologo (los campos básicos). Se espera que pasen contraseña en getHashContrasena()
     *          si tu DAO está implementado para hashear ese campo; si no, deberíamos hashearlo aquí.
     */
    public boolean crearNutriologo(Nutriologo n, String contrasenaEnTextoPlano) {
        try {
            // según tu implementación previa, el DAO hash-ea antes de guardar.
            // Para ser compatible con la versión que viste, metemos la contraseña plana
            // en el campo hashContrasena para que el DAO haga el hasheo.
            n.setHashContrasena(contrasenaEnTextoPlano);
            nutriologoDAO.insertar(n);
            notifyObservers("NUTRIOLOGO_CREATED");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void logout() {
        this.nutriologoActual = null;
        this.pacienteSeleccionado = null;
        this.consultaSeleccionada = null;
        notifyObservers("LOGOUT_SUCCESS");
    }

    public void agregarPaciente(Paciente p) {
        if (validacionServicio.isValidPaciente(p)) {
            try {
                p.setClaveNutriologo(nutriologoActual.getClaveNutriologo());
                pacienteDAO.insertar(p);
                notifyObservers("PACIENTES");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void eliminarPaciente(String clavePaciente) {
        try {
            pacienteDAO.eliminar(clavePaciente);
            notifyObservers("PACIENTES");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void seleccionarPaciente(String clavePaciente) {
        try {
            this.pacienteSeleccionado = pacienteDAO.leerPorClave(clavePaciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void guardarConsulta(Consulta c) {
        if (validacionServicio.isValidConsulta(c)) {
            try {
                consultaDAO.insertar(c);
                notifyObservers("HISTORIAL");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void eliminarConsulta(String claveConsulta) {
        try {
            consultaDAO.eliminar(claveConsulta);
            notifyObservers("HISTORIAL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void seleccionarConsulta(String claveConsulta) {
        try {
            this.consultaSeleccionada = consultaDAO.leerPorClave(claveConsulta);
        } catch (SQLException e) {
            e.printStackTrace();
            this.consultaSeleccionada = null;
        }
    }

    public NutriologoDAO getNutriologoDAO() {
        return nutriologoDAO;
    }

    public PacienteDAO getPacienteDAO() {
        return pacienteDAO;
    }

    public ConsultaDAO getConsultaDAO() {
        return consultaDAO;
    }

    public HashingServicio getHashingServicio() {
        return HashingServicio;
    }

    public NutricionServicio getNutricionServicio() {
        return nutricionServicio;
    }

    public Validaciones getValidacionServicio() {
        return validacionServicio;
    }

    public MacronutrientesServicio getMacronutrientesServicio() {
        return macronutrientesServicio;
    }
    
    public Nutriologo getNutriologoActual() {
        return nutriologoActual;
    }
    
    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }
    
    public Consulta getConsultaSeleccionada() {
        return consultaSeleccionada;
    }

    public Set<Observer> getObservers() {
        return observers;
    }

    public void setObservers(Set<Observer> observers) {
        this.observers = observers;
    }

    public void setNutriologoDAO(NutriologoDAO nutriologoDAO) {
        this.nutriologoDAO = nutriologoDAO;
    }

    public void setPacienteDAO(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    public void setConsultaDAO(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    public void setHashingServicio(HashingServicio securityServicio) {
        this.HashingServicio = securityServicio;
    }

    public void setNutricionServicio(NutricionServicio nutricionServicio) {
        this.nutricionServicio = nutricionServicio;
    }

    public void setValidacionServicio(Validaciones validacionServicio) {
        this.validacionServicio = validacionServicio;
    }

    public void setMacronutrientesServicio(MacronutrientesServicio macronutrientesServicio) {
        this.macronutrientesServicio = macronutrientesServicio;
    }
}
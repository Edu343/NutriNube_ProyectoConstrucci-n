package Modelo.Servicios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Modelo.Core.Observer;
import Modelo.DAO.*;
import Modelo.POJOs.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Modelo central que gestiona el estado de la aplicación,
 * incluyendo el nutriólogo actual, pacientes, consultas,
 * y proporciona servicios para autenticación, cálculos nutricionales,
 * y operaciones CRUD a través de DAOs.
 */

public class NutriNubeModelo {

    public enum LoginEstado {
        SUCCESS, WRONG_PASSWORD, NOT_FOUND, ERROR
    }

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

    public void notifyObservers() {
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
            if (n == null)
                return LoginEstado.NOT_FOUND;

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
     * @param n Nutriologo (los campos básicos). Se espera que pasen contraseña en
     *          getHashContrasena()
     *          si tu DAO está implementado para hashear ese campo; si no,
     *          deberíamos hashearlo aquí.
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

    public Consulta obtenerConsultaSeleccionada(String claveConsulta) throws SQLException {
        this.consultaSeleccionada = consultaDAO.leerPorClave(claveConsulta);
        return this.consultaSeleccionada;
    }

    public void calcularCaloriasYMacronutrientes(String clavePaciente, double peso, int nivelActividadFisica,
            int razonConsulta,
            int sexo, double altura, String fechaNacimiento) throws Exception {

        String fechaNac = fechaNacimiento;
        int s = sexo;
        double alt = altura;

        if (clavePaciente != null && !clavePaciente.isEmpty()) {
            // Si el paciente EXISTE, usamos los datos demográficos almacenados
            Paciente paciente = pacienteDAO.leerPorClave(clavePaciente);
            if (paciente != null) {
                fechaNac = paciente.getFechaNacimiento();
                s = paciente.getSexo();
                alt = paciente.getAltura();
            } else {
                throw new Exception("Paciente no encontrado para el cálculo.");
            }
        }

        CaloriasCalculo calculoData = new CaloriasCalculo();
        calculoData.setPeso(peso);
        calculoData.setNivelActividadFisica(nivelActividadFisica);
        calculoData.setRazonConsulta(razonConsulta);

        double caloriasTotales = nutricionServicio.calcularCalorias(calculoData, s, alt, fechaNac);
        calculoData.setCalorias(caloriasTotales);

        MetaNutricional meta = MetaFactory.obtenerMeta(razonConsulta);

        Macronutrientes macros = macronutrientesServicio.dividirCalorias(caloriasTotales, meta);

        if (this.consultaSeleccionada == null) {
            this.consultaSeleccionada = new Consulta();
            this.consultaSeleccionada.setClavePaciente(clavePaciente); // Puede ser null, pero se establece al guardar
            this.consultaSeleccionada.setClaveNutriologo(this.nutriologoActual.getClaveNutriologo());
        }
        this.consultaSeleccionada.setTotalCalorias(calculoData);
        this.consultaSeleccionada.setMacronutrientes(macros);

        notifyObservers();
    }

    public void calcularCaloriasYMacronutrientes(String clavePaciente, double peso, int nivelActividadFisica,
            int razonConsulta) throws Exception {
        Paciente paciente = pacienteDAO.leerPorClave(clavePaciente);
        if (paciente == null) {
            throw new Exception("Debe seleccionar un paciente y completar los campos demográficos.");
        }
        calcularCaloriasYMacronutrientes(clavePaciente, peso, nivelActividadFisica, razonConsulta,
                paciente.getSexo(), paciente.getAltura(), paciente.getFechaNacimiento());
    }

    public void guardarNuevoPacienteYConsulta(String claveNutriologo, String nombre, String apellido, String correo,
            int sexo, String telefono, String fechaNacimiento, double altura,
            String condicionesMedicas, String medicacion, String historialCirugias, String alergias,
            String preferenciaComida, String horarioSueno, int nivelEstres, String habitosAlimenticios,
            String tipoLiquidosConsumidos, double cantidadLiquidoConsumido, String barreraAlimenticia,
            double peso, int nivelActividadFisica, int razonConsulta, double calorias, double carbohidratos,
            double proteinas, double lipidos) throws Exception {

        Paciente nuevoPaciente = crearPaciente(claveNutriologo, nombre, apellido, correo, sexo, telefono,
                fechaNacimiento, altura);
        if (!validacionServicio.isValidPaciente(nuevoPaciente)) {
            throw new Exception("Datos de paciente incompletos o inválidos.");
        }

        Consulta nuevaConsulta = crearConsulta(null, nuevoPaciente.getClavePaciente(), claveNutriologo,
                condicionesMedicas, medicacion, historialCirugias, alergias,
                preferenciaComida, horarioSueno, nivelEstres, habitosAlimenticios, tipoLiquidosConsumidos,
                cantidadLiquidoConsumido, barreraAlimenticia,
                peso, nivelActividadFisica, razonConsulta, calorias, carbohidratos, proteinas, lipidos);

        if (!validacionServicio.isValidConsulta(nuevaConsulta)) {
            throw new Exception("Datos de la primera consulta incompletos o inválidos.");
        }

        pacienteDAO.insertar(nuevoPaciente);
        consultaDAO.insertar(nuevaConsulta);

        this.pacienteSeleccionado = null;
        this.consultaSeleccionada = null;
        notifyObservers();
    }

    public void guardarNuevaConsulta(String clavePaciente, String claveNutriologo,
            String condicionesMedicas, String medicacion, String historialCirugias, String alergias,
            String preferenciaComida, String horarioSueno, int nivelEstres, String habitosAlimenticios,
            String tipoLiquidosConsumidos, double cantidadLiquidoConsumido, String barreraAlimenticia,
            double peso, int nivelActividadFisica, int razonConsulta, double calorias, double carbohidratos,
            double proteinas, double lipidos) throws Exception {

        Consulta nuevaConsulta = crearConsulta(null, clavePaciente, claveNutriologo, condicionesMedicas, medicacion,
                historialCirugias, alergias,
                preferenciaComida, horarioSueno, nivelEstres, habitosAlimenticios, tipoLiquidosConsumidos,
                cantidadLiquidoConsumido, barreraAlimenticia,
                peso, nivelActividadFisica, razonConsulta, calorias, carbohidratos, proteinas, lipidos);

        consultaDAO.insertar(nuevaConsulta);
        this.consultaSeleccionada = null;
        notifyObservers();
    }

    public void actualizarConsulta(String claveConsulta, String clavePaciente, String claveNutriologo,
            String nombre, String apellido, String correo, int sexo, String telefono, String fechaNacimiento,
            double altura,
            // ------------------------------------------
            String condicionesMedicas, String medicacion, String historialCirugias, String alergias,
            String preferenciaComida, String horarioSueno, int nivelEstres, String habitosAlimenticios,
            String tipoLiquidosConsumidos, double cantidadLiquidoConsumido, String barreraAlimenticia,
            double peso, int nivelActividadFisica, int razonConsulta, double calorias, double carbohidratos,
            double proteinas, double lipidos) throws Exception {

        Paciente pacienteActualizado = new Paciente();
        pacienteActualizado.setClavePaciente(clavePaciente);
        pacienteActualizado.setClaveNutriologo(claveNutriologo);
        pacienteActualizado.setNombre(nombre);
        pacienteActualizado.setApellido(apellido);
        pacienteActualizado.setCorreo(correo);
        pacienteActualizado.setSexo(sexo);
        pacienteActualizado.setTelefono(telefono);
        pacienteActualizado.setFechaNacimiento(fechaNacimiento);
        pacienteActualizado.setAltura(altura);

        pacienteDAO.actualizar(pacienteActualizado);

        Consulta consultaEditada = crearConsulta(claveConsulta, clavePaciente, claveNutriologo, condicionesMedicas,
                medicacion, historialCirugias, alergias,
                preferenciaComida, horarioSueno, nivelEstres, habitosAlimenticios, tipoLiquidosConsumidos,
                cantidadLiquidoConsumido, barreraAlimenticia,
                peso, nivelActividadFisica, razonConsulta, calorias, carbohidratos, proteinas, lipidos);

        consultaDAO.actualizar(consultaEditada);

        this.consultaSeleccionada = null;
        notifyObservers();
    }

    private Paciente crearPaciente(String claveNutriologo, String nombre, String apellido, String correo, int sexo,
            String telefono, String fechaNacimiento, double altura) {
        String clavePaciente = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        return new Paciente(clavePaciente, claveNutriologo, nombre, apellido, correo, sexo, telefono, fechaNacimiento,
                altura);
    }

    private Consulta crearConsulta(String claveConsulta, String clavePaciente, String claveNutriologo,
            String condicionesMedicas, String medicacion, String historialCirugias, String alergias,
            String preferenciaComida, String horarioSueno, int nivelEstres, String habitosAlimenticios,
            String tipoLiquidosConsumidos, double cantidadLiquidoConsumido, String barreraAlimenticia,
            double peso, int nivelActividadFisica, int razonConsulta, double calorias, double carbohidratos,
            double proteinas, double lipidos) throws Exception {

        if (claveConsulta == null || claveConsulta.isEmpty()) {
            claveConsulta = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }

        String fechaVisita = LocalDate.now().toString();
        double alturaFinal = 0.0;
        int edadCalculada = 0;

        if (clavePaciente != null) {
            Paciente pacienteNuevo = pacienteDAO.leerPorClave(clavePaciente);
            if (pacienteNuevo != null) {
                alturaFinal = pacienteNuevo.getAltura();
                edadCalculada = nutricionServicio.calcularEdad(pacienteNuevo.getFechaNacimiento());
            }
        }

        AnamnesisData anamnesis = new AnamnesisData(condicionesMedicas, medicacion, historialCirugias, alergias,
                preferenciaComida, horarioSueno, nivelEstres, habitosAlimenticios,
                tipoLiquidosConsumidos, cantidadLiquidoConsumido, barreraAlimenticia);

        CaloriasCalculo calculo = new CaloriasCalculo(peso, nivelActividadFisica, razonConsulta, calorias);

        Macronutrientes macros = new Macronutrientes(carbohidratos, proteinas, lipidos);

        return new Consulta(claveConsulta, clavePaciente, claveNutriologo, fechaVisita,
                edadCalculada, alturaFinal, anamnesis, calculo, macros);
    }

    public List<Paciente> buscarPacientes(String criterio) {
        try {
            List<Paciente> lista = nutriologoDAO.obtenerListaPacientes(nutriologoActual.getClaveNutriologo());
            if (lista == null)
                lista = new ArrayList<>();
            if (criterio == null || criterio.trim().isEmpty())
                return lista;
            final String crit = criterio.trim().toLowerCase();
            return lista.stream().filter(p -> {
                String nombre = p.getNombre() == null ? "" : p.getNombre().toLowerCase();
                String apellido = p.getApellido() == null ? "" : p.getApellido().toLowerCase();
                return nombre.contains(crit) || apellido.contains(crit);
            }).collect(Collectors.toList());
        } catch (SQLException e) {
            return new ArrayList<>();
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
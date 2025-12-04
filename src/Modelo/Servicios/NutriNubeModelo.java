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
 * Modelo central que gestiona el estado global de la aplicación,
 * proporcionando acceso a DAOs, cálculo nutricional,
 * autenticación de nutriólogos y toda la lógica relacionada
 * con pacientes y consultas.
 */
public class NutriNubeModelo {

    /**
     * Representa los posibles estados del intento de login.
     */
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

    /**
     * Inicializa el modelo creando DAOs, servicios auxiliares
     * y la estructura interna de observadores.
     */
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

    /**
     * Registra un nuevo observer para recibir notificaciones del modelo.
     *
     * @param observer Observer a registrar.
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Notifica a todos los observers indicando que ocurrió un evento concreto.
     *
     * @param evento Nombre del evento ocurrido.
     */
    public void notifyObservers(String evento) {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Notifica a los observers un cambio general sin un evento específico.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Verifica si un nutriólogo existe y si la contraseña proporcionada es correcta.
     * Este método no modifica el estado del modelo.
     *
     * @param claveNutriologo Clave del nutriólogo.
     * @param contrasena Contraseña en texto plano.
     * @return Estado del proceso de login.
     */
    public LoginEstado obtenerEstadoLogin(String claveNutriologo, String contrasena) {
        try {
            Nutriologo n = nutriologoDAO.leerPorClave(claveNutriologo);
            if (n == null)
                return LoginEstado.NOT_FOUND;

            boolean ok = HashingServicio.verificarContrasena(contrasena, n.getSaltContrasena(), n.getHashContrasena());
            return ok ? LoginEstado.SUCCESS : LoginEstado.WRONG_PASSWORD;

        } catch (SQLException _) {
            return LoginEstado.ERROR;
        }
    }

    /**
     * Realiza el inicio de sesión. Si es exitoso,
     * carga el nutriólogo actual en memoria y notifica observers.
     *
     * @param claveNutriologo Clave del nutriólogo.
     * @param contrasena Contraseña ingresada.
     * @return true si el login fue exitoso.
     */
    public boolean login(String claveNutriologo, String contrasena) {
        LoginEstado estado = obtenerEstadoLogin(claveNutriologo, contrasena);

        if (estado == LoginEstado.SUCCESS) {
            try {
                this.nutriologoActual = nutriologoDAO.leerPorClave(claveNutriologo);
                limpiarSelecciones();
                notifyObservers("LOGIN_SUCCESS");
                return true;
            } catch (SQLException _) {
                return false;
            }
        }

        return false;
    }

    /**
     * Verifica si existe un nutriólogo con la clave dada.
     *
     * @param claveNutriologo Clave del nutriólogo.
     * @return true si existe.
     */
    public boolean nutriologoExiste(String claveNutriologo) {
        try {
            return nutriologoDAO.leerPorClave(claveNutriologo) != null;
        } catch (SQLException _) {
            return false;
        }
    }

    /**
     * Crea un nuevo nutriólogo delegando el proceso de hashing al DAO.
     *
     * @param nutriologo Nutriólogo a registrar.
     * @param contrasenaEnTextoPlano Contraseña original ingresada.
     * @return true si fue registrado correctamente.
     */
    public boolean crearNutriologo(Nutriologo nutriologo, String contrasenaEnTextoPlano) {
        try {
            nutriologo.setHashContrasena(contrasenaEnTextoPlano);
            nutriologoDAO.insertar(nutriologo);
            notifyObservers("NUTRIOLOGO_CREATED");
            return true;
        } catch (SQLException _) {
            return false;
        }
    }

    /**
     * Cierra sesión y limpia los datos almacenados del modelo.
     */
    public void logout() {
        this.nutriologoActual = null;
        limpiarSelecciones();
        notifyObservers("LOGOUT_SUCCESS");
    }

    /**
     * Valida e inserta un nuevo paciente.
     *
     * @param paciente Paciente a registrar.
     */
    public void agregarPaciente(Paciente paciente) {
        if (validacionServicio.isValidPaciente(paciente)) {
            try {
                paciente.setClaveNutriologo(nutriologoActual.getClaveNutriologo());
                pacienteDAO.insertar(paciente);
                notifyObservers("PACIENTES");
            } catch (SQLException _) {
            }
        }
    }

    /**
     * Elimina un paciente de la base de datos.
     *
     * @param clavePaciente Identificador del paciente.
     */
    public void eliminarPaciente(String clavePaciente) {
        try {
            pacienteDAO.eliminar(clavePaciente);
            notifyObservers("PACIENTES");
        } catch (SQLException _) {
        }
    }

    /**
     * Carga un paciente como el paciente actualmente seleccionado.
     *
     * @param clavePaciente Clave del paciente.
     */
    public void seleccionarPaciente(String clavePaciente) {
        try {
            this.pacienteSeleccionado = pacienteDAO.leerPorClave(clavePaciente);
        } catch (SQLException e) {
            this.pacienteSeleccionado = null;
        }
    }

    /**
     * Valida e inserta una nueva consulta médica.
     *
     * @param c Consulta a guardar.
     */
    

    /**
     * Elimina una consulta del historial mediante su clave.
     *
     * @param claveConsulta Identificador de la consulta.
     */
    public void eliminarConsulta(String claveConsulta) {
        try {
            consultaDAO.eliminar(claveConsulta);
            notifyObservers("HISTORIAL");
        } catch (SQLException e) {
        }
            
        
    }

    /**
     * Selecciona una consulta específica para visualizarla o editarla.
     *
     * @param claveConsulta Clave de la consulta.
     */
    public void seleccionarConsulta(String claveConsulta) {
        try {
            this.consultaSeleccionada = consultaDAO.leerPorClave(claveConsulta);
        } catch (SQLException e) {
            
            this.consultaSeleccionada = null;
        }
    }

    /**
     * Obtiene y asigna como seleccionada una consulta específica.
     *
     * @param claveConsulta Clave única de la consulta.
     * @return Consulta encontrada.
     * @throws SQLException Si falla la lectura desde el DAO.
     */
    public Consulta obtenerConsultaSeleccionada(String claveConsulta) throws SQLException {
        this.consultaSeleccionada = consultaDAO.leerPorClave(claveConsulta);
        return this.consultaSeleccionada;
    }

    /**
     * Calcula calorías y macronutrientes usando datos ingresados por el usuario
     * o los datos almacenados del paciente si ya existe.
     *
     * @throws Exception Si faltan datos o el paciente no existe.
     */
    public void calcularCaloriasYMacronutrientes(String clavePaciente, double peso, int nivelActividadFisica,
        int razonConsulta, int sexo, double altura, String fechaNacimiento) throws Exception {

        String fechaNacimientoParaCalculo = fechaNacimiento;
        int sexoParaCalculo = sexo;
        double alturaParaCalculo = altura;

        // En caso de que el paciente exista se toman los datos del paciente
        if (clavePaciente != null && !clavePaciente.isEmpty()) {

            Paciente paciente = pacienteDAO.leerPorClave(clavePaciente);
            if (paciente != null) {
                fechaNacimientoParaCalculo = paciente.getFechaNacimiento();
                sexoParaCalculo = paciente.getSexo();
                alturaParaCalculo = paciente.getAltura();
            } else {
                throw new Exception("Paciente no encontrado para el cálculo.");
            }
        }

        CaloriasCalculo calculoData = new CaloriasCalculo();
        calculoData.setPeso(peso);
        calculoData.setNivelActividadFisica(nivelActividadFisica);
        calculoData.setRazonConsulta(razonConsulta);

        double caloriasTotales = nutricionServicio.calcularCalorias(calculoData, sexoParaCalculo, alturaParaCalculo, fechaNacimientoParaCalculo);
        calculoData.setCalorias(caloriasTotales);

        MetaNutricional meta = MetaFactory.obtenerMeta(razonConsulta);

        Macronutrientes macros = macronutrientesServicio.dividirCalorias(caloriasTotales, meta);

        if (this.consultaSeleccionada == null) {
            this.consultaSeleccionada = new Consulta();
            this.consultaSeleccionada.setClavePaciente(clavePaciente);
            if (this.nutriologoActual != null) {
                this.consultaSeleccionada.setClaveNutriologo(this.nutriologoActual.getClaveNutriologo());
            }
        }
        this.consultaSeleccionada.setTotalCalorias(calculoData);
        this.consultaSeleccionada.setMacronutrientes(macros);

        notifyObservers();
    }

   

    /**
     * Crea un nuevo paciente y su primera consulta dentro de una misma operación.
     *
     * @throws Exception Si los datos están incompletos o son inválidos.
     */
    public void guardarNuevoPacienteYConsulta(Paciente nuevoPaciente, Consulta nuevaConsulta) throws Exception {

        if (!validacionServicio.isValidPaciente(nuevoPaciente)) {
            throw new Exception("Datos de paciente incompletos o inválidos.");
        }

        // Asegurar consistencia de claves
        if (nuevaConsulta.getClavePaciente() == null || !nuevaConsulta.getClavePaciente().equals(nuevoPaciente.getClavePaciente())) {
            nuevaConsulta.setClavePaciente(nuevoPaciente.getClavePaciente());
        }
        
        // Calcular edad y altura final para la consulta basado en el paciente
        nuevaConsulta.setAltura(nuevoPaciente.getAltura());
        nuevaConsulta.setEdad(nutricionServicio.calcularEdad(nuevoPaciente.getFechaNacimiento()));
        nuevaConsulta.setFechaVisita(LocalDate.now().toString());

        if (!validacionServicio.isValidConsulta(nuevaConsulta)) {
            throw new Exception("Datos de la primera consulta incompletos o inválidos.");
        }

        pacienteDAO.insertar(nuevoPaciente);
        consultaDAO.insertar(nuevaConsulta);

        limpiarSelecciones();
        notifyObservers();
    }

    /**
     * Crea y guarda una nueva consulta para un paciente ya existente.
     *
     * @throws Exception Si los datos son inválidos.
     */
    public void guardarNuevaConsulta(Consulta nuevaConsulta) throws Exception {
        
        // Actualizar datos de visita
        nuevaConsulta.setFechaVisita(LocalDate.now().toString());
        
        // Calcular edad actualizada del paciente existente
        if (nuevaConsulta.getClavePaciente() != null) {
            Paciente p = pacienteDAO.leerPorClave(nuevaConsulta.getClavePaciente());
            if (p != null) {
                nuevaConsulta.setEdad(nutricionServicio.calcularEdad(p.getFechaNacimiento()));
            }
        }

        consultaDAO.insertar(nuevaConsulta);
        this.consultaSeleccionada = null;
        notifyObservers();
    }

    /**
     * Actualiza los datos de un paciente y su correspondiente consulta.
     *
     * @throws Exception Si los datos son incompletos o inválidos.
     */
    public void actualizarConsulta(Paciente pacienteActualizado, Consulta consultaEditada) throws Exception {

        if (pacienteActualizado != null) {
             pacienteDAO.actualizar(pacienteActualizado);
             consultaEditada.setAltura(pacienteActualizado.getAltura());
             consultaEditada.setEdad(nutricionServicio.calcularEdad(pacienteActualizado.getFechaNacimiento()));
        }

        consultaDAO.actualizar(consultaEditada);

        this.consultaSeleccionada = null;
        notifyObservers();
    }

    /**
     * Genera una clave única de 8 caracteres alfanuméricos en mayúsculas.
     *
     * @return Clave única generada.
     */
    public String generarClaveUnica() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Limpia la selección actual de paciente y consulta.
     */
    private void limpiarSelecciones() {
        this.pacienteSeleccionado = null;
        this.consultaSeleccionada = null;
    }

    
    /**
     * Busca pacientes pertenecientes al nutriólogo actual
     * filtrando por nombre o apellido.
     *
     * @param criterio Texto de búsqueda.
     * @return Lista filtrada de pacientes.
     */
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

    /** @return DAO de nutriólogos. */
    public NutriologoDAO getNutriologoDAO() {
        return nutriologoDAO;
    }

    /** @return DAO de pacientes. */
    public PacienteDAO getPacienteDAO() {
        return pacienteDAO;
    }

    /** @return DAO de consultas. */
    public ConsultaDAO getConsultaDAO() {
        return consultaDAO;
    }

    /** @return Servicio de hashing. */
    public HashingServicio getHashingServicio() {
        return HashingServicio;
    }

    /** @return Servicio de cálculo nutricional. */
    public NutricionServicio getNutricionServicio() {
        return nutricionServicio;
    }

    /** @return Servicio de validación. */
    public Validaciones getValidacionServicio() {
        return validacionServicio;
    }

    /** @return Servicio de macronutrientes. */
    public MacronutrientesServicio getMacronutrientesServicio() {
        return macronutrientesServicio;
    }

    /** @return Nutriólogo actualmente autenticado. */
    public Nutriologo getNutriologoActual() {
        return nutriologoActual;
    }

    /** @return Paciente actualmente seleccionado. */
    public Paciente getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    /** @return Consulta actualmente seleccionada. */
    public Consulta getConsultaSeleccionada() {
        return consultaSeleccionada;
    }

    /** @return Observers registrados. */
    public Set<Observer> getObservers() {
        return observers;
    }

    /** @param observers Conjunto de observers a establecer. */
    public void setObservers(Set<Observer> observers) {
        this.observers = observers;
    }

    /** @param nutriologoDAO DAO de nutriólogos. */
    public void setNutriologoDAO(NutriologoDAO nutriologoDAO) {
        this.nutriologoDAO = nutriologoDAO;
    }

    /** @param pacienteDAO DAO de pacientes. */
    public void setPacienteDAO(PacienteDAO pacienteDAO) {
        this.pacienteDAO = pacienteDAO;
    }

    /** @param consultaDAO DAO de consultas. */
    public void setConsultaDAO(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    /** @param securityServicio Servicio de hashing a establecer. */
    public void setHashingServicio(HashingServicio securityServicio) {
        this.HashingServicio = securityServicio;
    }

    /** @param nutricionServicio Servicio nutricional a establecer. */
    public void setNutricionServicio(NutricionServicio nutricionServicio) {
        this.nutricionServicio = nutricionServicio;
    }

    /** @param validacionServicio Servicio de validación a establecer. */
    public void setValidacionServicio(Validaciones validacionServicio) {
        this.validacionServicio = validacionServicio;
    }

    /** @param macronutrientesServicio Servicio de macronutrientes. */
    public void setMacronutrientesServicio(MacronutrientesServicio macronutrientesServicio) {
        this.macronutrientesServicio = macronutrientesServicio;
    }

    /** @param nutriologoActual Nutriólogo autenticado. */
    public void setNutriologoActual(Nutriologo nutriologoActual) {
        this.nutriologoActual = nutriologoActual;
    }

    /** @param pacienteSeleccionado Paciente seleccionado. */
    public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    /** @param consultaSeleccionada Consulta seleccionada. */
    public void setConsultaSeleccionada(Consulta consultaSeleccionada) {
        this.consultaSeleccionada = consultaSeleccionada;
    }

    /**
     * Obtiene todas las consultas registradas para un paciente
     * en una fecha específica.
     */
    public List<Consulta> obtenerConsultasPorPacienteYFecha(String clavePaciente, String fecha) throws SQLException {
        return this.getConsultaDAO().leerPorPacienteYFecha(clavePaciente, fecha);
    }
}
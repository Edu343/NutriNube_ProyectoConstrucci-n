package Modelo.DAO;

import Modelo.POJOs.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO encargado de las operaciones de persistencia para la entidad Consulta.
 * Implementa todas las operaciones CRUD y reconstruye objetos anidados
 * a partir de los resultados de la base de datos.
 */
public class ConsultaDAO extends DatabaseManager {

    private static final String COLUMN_CLAVE_CONSULTA = "claveConsulta";
    private static final String COLUMN_CLAVE_PACIENTE = "clavePaciente";
    private static final String COLUMN_CLAVE_NUTRIOLOGO = "claveNutriologo";
    private static final String COLUMN_FECHA = "fecha";

    /**
     * Inserta una nueva consulta con todos los campos requeridos.
     */
    @Override
    public void insertar(Object entidad) throws SQLException {
        Consulta consulta = (Consulta) entidad;

        String sql = """
            INSERT INTO consulta (
              claveConsulta, clavePaciente, claveNutriologo, fecha,
              edad, altura,
              condicionesMedicas, medicacion, historialCirugias, alergias,
              preferenciaComida, horarioSueno, nivelEstres,
              habitosAlimenticios, tipoLiquidos, cantidadLiquidos,
              dificultades,
              peso, nivelActividadFisica, calorias, razonConsulta,
              carbohidratos, proteinas, lipidos
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // ---- Claves y datos generales ----
            statement.setString(1,consulta.getClaveConsulta());
            statement.setString(2, consulta.getClavePaciente());
            statement.setString(3, consulta.getClaveNutriologo());
            statement.setString(4, consulta.getFechaVisita());

            statement.setInt(5, consulta.getEdad());
            statement.setDouble(6, consulta.getAltura());

            // ---- Datos de Anamnesis ----
            statement.setString(7, consulta.getAnamnesisData().getCondicionesMedicas());
            statement.setString(8, consulta.getAnamnesisData().getMedicacion());
            statement.setString(9, consulta.getAnamnesisData().getHistorialCirugias());
            statement.setString(10, consulta.getAnamnesisData().getAlergias());
            statement.setString(11, consulta.getAnamnesisData().getPreferenciaComida());
            statement.setString(12, consulta.getAnamnesisData().getHorarioSueno());
            statement.setInt(13, consulta.getAnamnesisData().getNivelEstres());
            statement.setString(14, consulta.getAnamnesisData().getHabitoAlimenticio());
            statement.setString(15, consulta.getAnamnesisData().getTipoLiquidoConsumido());
            statement.setDouble(16, consulta.getAnamnesisData().getCantidadLiquidoConsumido());
            statement.setString(17, consulta.getAnamnesisData().getBarreraAlimenticia());

            // ---- Datos cálculo ----
            statement.setDouble(18, consulta.getCaloriasCalculo().getPeso());
            statement.setInt(19, consulta.getCaloriasCalculo().getNivelActividadFisica());
            statement.setDouble(20, consulta.getCaloriasCalculo().getCalorias());
            statement.setInt(21, consulta.getCaloriasCalculo().getRazonConsulta());

            // ---- Macronutrientes ----
            statement.setDouble(22, consulta.getMacronutrientes().getCarbohidratos());
            statement.setDouble(23, consulta.getMacronutrientes().getProteinas());
            statement.setDouble(24, consulta.getMacronutrientes().getLipidos());

            statement.executeUpdate();
        }
    }

    /**
     * Actualiza una consulta existente en la base de datos.
     */
    @Override
    public void actualizar(Object entidad) throws SQLException {
        Consulta consulta = (Consulta) entidad;

        String sql = """
            UPDATE consulta SET
              clavePaciente=?, claveNutriologo=?, fecha=?,
              edad=?, altura=?,
              condicionesMedicas=?, medicacion=?, historialCirugias=?, alergias=?,
              preferenciaComida=?, horarioSueno=?, nivelEstres=?,
              habitosAlimenticios=?, tipoLiquidos=?, cantidadLiquidos=?, dificultades=?,
              peso=?, nivelActividadFisica=?, calorias=?, razonConsulta=?,
              carbohidratos=?, proteinas=?, lipidos=?
            WHERE claveConsulta=?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // ---- Claves y datos generales ----
            statement.setString(1, consulta.getClavePaciente());
            statement.setString(2, consulta.getClaveNutriologo());
            statement.setString(3, consulta.getFechaVisita());

            statement.setInt(4, consulta.getEdad());
            statement.setDouble(5, consulta.getAltura());

            // ---- Datos de Anamnesis ----
            statement.setString(6, consulta.getAnamnesisData().getCondicionesMedicas());
            statement.setString(7, consulta.getAnamnesisData().getMedicacion());
            statement.setString(8, consulta.getAnamnesisData().getHistorialCirugias());
            statement.setString(9, consulta.getAnamnesisData().getAlergias());
            statement.setString(10, consulta.getAnamnesisData().getPreferenciaComida());
            statement.setString(11, consulta.getAnamnesisData().getHorarioSueno());
            statement.setInt(12, consulta.getAnamnesisData().getNivelEstres());
            statement.setString(13, consulta.getAnamnesisData().getHabitoAlimenticio());
            statement.setString(14, consulta.getAnamnesisData().getTipoLiquidoConsumido());
            statement.setDouble(15, consulta.getAnamnesisData().getCantidadLiquidoConsumido());
            statement.setString(16, consulta.getAnamnesisData().getBarreraAlimenticia());

            // ---- Datos cálculo ----
            statement.setDouble(17, consulta.getCaloriasCalculo().getPeso());
            statement.setInt(18, consulta.getCaloriasCalculo().getNivelActividadFisica());
            statement.setDouble(19, consulta.getCaloriasCalculo().getCalorias());
            statement.setInt(20, consulta.getCaloriasCalculo().getRazonConsulta());

            // ---- Macronutrientes ----
            statement.setDouble(21, consulta.getMacronutrientes().getCarbohidratos());
            statement.setDouble(22, consulta.getMacronutrientes().getProteinas());
            statement.setDouble(23, consulta.getMacronutrientes().getLipidos());
            
            statement.setString(24, consulta.getClaveConsulta());

            statement.executeUpdate();
        }
    }

    /**
     * Elimina una consulta mediante su clave única.
     */
    @Override
    public void eliminar(String claveConsulta) throws SQLException {
        String sql = "DELETE FROM consulta WHERE claveConsulta = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, claveConsulta);
            statement.executeUpdate();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Consulta completo con sus objetos anidados.
     * Método reutilizable para evitar duplicación de código.
     */
    private Consulta mapearConsulta(ResultSet resultSet) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setClaveConsulta(resultSet.getString(COLUMN_CLAVE_CONSULTA));
        consulta.setClavePaciente(resultSet.getString(COLUMN_CLAVE_PACIENTE));
        consulta.setClaveNutriologo(resultSet.getString(COLUMN_CLAVE_NUTRIOLOGO));
        consulta.setFechaVisita(resultSet.getString(COLUMN_FECHA));
        consulta.setEdad(resultSet.getInt("edad"));
        consulta.setAltura(resultSet.getDouble("altura"));

        // ---- Calorías ----
        CaloriasCalculo calorias = new CaloriasCalculo();
        calorias.setPeso(resultSet.getDouble("peso"));
        calorias.setCalorias(resultSet.getDouble("calorias"));
        calorias.setNivelActividadFisica(resultSet.getInt("nivelActividadFisica"));
        calorias.setRazonConsulta(resultSet.getInt("razonConsulta"));
        consulta.setTotalCalorias(calorias);

        // ---- Macronutrientes ----
        Macronutrientes macros = new Macronutrientes();
        macros.setCarbohidratos(resultSet.getDouble("carbohidratos"));
        macros.setProteinas(resultSet.getDouble("proteinas"));
        macros.setLipidos(resultSet.getDouble("lipidos"));
        consulta.setMacronutrientes(macros);

        // ---- Anamnesis ----
        AnamnesisData anamnesis = new AnamnesisData();
        anamnesis.setCondicionesMedicas(resultSet.getString("condicionesMedicas"));
        anamnesis.setMedicacion(resultSet.getString("medicacion"));
        anamnesis.setHistorialCirugias(resultSet.getString("historialCirugias"));
        anamnesis.setAlergias(resultSet.getString("alergias"));
        anamnesis.setPreferenciaComida(resultSet.getString("preferenciaComida"));
        anamnesis.setHorarioSueno(resultSet.getString("horarioSueno"));
        anamnesis.setNivelEstres(resultSet.getInt("nivelEstres"));
        anamnesis.setHabitoAlimenticio(resultSet.getString("habitosAlimenticios"));
        anamnesis.setTipoLiquidoConsumido(resultSet.getString("tipoLiquidos"));
        anamnesis.setCantidadLiquidoConsumido(resultSet.getDouble("cantidadLiquidos"));
        anamnesis.setBarreraAlimenticia(resultSet.getString("dificultades"));
        consulta.setAnamnesisData(anamnesis);

        return consulta;
    }

    /**
     * Recupera una consulta completa, incluyendo sus objetos anidados
     * como calorías, anamnesis y macronutrientes.
     */
    public Consulta leerPorClave(String claveConsulta) throws SQLException {

        String sql = "SELECT * FROM consulta WHERE claveConsulta = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, claveConsulta);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return mapearConsulta(resultSet);
        }
    }
    
    /**
     * Obtiene todas las consultas pertenecientes a un paciente.
     */
    public ArrayList<Consulta> leerPorPaciente(String clavePaciente) throws SQLException {

        ArrayList<Consulta> lista = new ArrayList<>();

        String sql = "SELECT claveConsulta FROM consulta WHERE clavePaciente = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clavePaciente);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String claveConsulta = resultSet.getString(COLUMN_CLAVE_CONSULTA);

                // Reutilizamos tu método existente que reconstruye la consulta
                Consulta consulta = leerPorClave(claveConsulta);

                lista.add(consulta);
            }
        }

        return lista;
    }

    /**
     * Recupera la fecha más reciente de consulta registrada
     * para un paciente.
     */
    public String obtenerUltimaFechaPorPaciente(String clavePaciente) throws SQLException {
        String fecha = "Sin historial";

        String sql = "SELECT fecha FROM consulta WHERE clavePaciente = ? ORDER BY fecha DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clavePaciente);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                fecha = rs.getString(COLUMN_FECHA);
            }
        }
        return fecha;
    }

    /**
     * Obtiene la última consulta registrada (más reciente por fecha)
     * reconstruyendo todos sus objetos internos.
     */
    public Consulta obtenerUltimaConsultaObjeto(String clavePaciente) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE clavePaciente = ? ORDER BY fecha DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clavePaciente);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return mapearConsulta(resultSet);
        }
    }

    /**
     * Recupera todas las consultas de un paciente filtrando por una fecha exacta.
     */
    public List<Consulta> leerPorPacienteYFecha(String clavePaciente, String fecha) throws SQLException {
        List<Consulta> lista = new ArrayList<>();

        String sql = "SELECT claveConsulta FROM consulta WHERE clavePaciente = ? AND fecha = ? ORDER BY fecha DESC";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clavePaciente);
            statement.setString(2, fecha);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String claveConsulta = rs.getString(COLUMN_CLAVE_CONSULTA);
                    Consulta c = leerPorClave(claveConsulta);
                    if (c != null) lista.add(c);
                }
            }
        }
        return lista;
    }
}

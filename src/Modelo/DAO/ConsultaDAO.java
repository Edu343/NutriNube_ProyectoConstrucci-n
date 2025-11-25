package DAO;

import POJOs.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO responsable de manejar operaciones CRUD para la entidad Consulta.
 * Reconstruye todos los objetos anidados y utiliza el orden de parámetros
 * validado por el equipo.
 */
public class ConsultaDAO extends DatabaseManager {

    /**
     * Inserta una nueva consulta utilizando los 25 campos definidos.
     *
     * @param entidad Objeto Consulta a insertar.
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
            statement.setInt(1, Integer.parseInt(consulta.getClaveConsulta()));
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
     * Actualiza una consulta existente.
     *
     * @param entidad Objeto Consulta con datos actualizados.
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
            statement.setInt(1, Integer.parseInt(consulta.getClaveConsulta()));
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
     * Elimina una consulta por su clave principal.
     */
    @Override
    public void eliminar(String claveConsulta) throws SQLException {
        String sql = "DELETE FROM consulta WHERE claveConsulta = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, claveConsulta);
            stmt.executeUpdate();
        }
    }

    /**
     * Recupera una consulta completa, reconstruyendo todos sus
     * objetos anidados.
     *
     * @param claveConsulta Identificador único.
     * @return Consulta o null si no existe.
     */
    public Consulta leerPorClave(String claveConsulta) throws SQLException {

        String sql = "SELECT * FROM consulta WHERE claveConsulta = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, claveConsulta);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Consulta consulta = new Consulta();
            consulta.setClaveConsulta(claveConsulta);
            consulta.setClavePaciente(rs.getString("clavePaciente"));
            consulta.setClaveNutriologo(rs.getString("claveNutriologo"));
            consulta.setFechaVisita(rs.getString("fecha"));
            consulta.setEdad(rs.getInt("edad"));
            consulta.setAltura(rs.getDouble("altura"));

            // ---- Calorías ----
            CaloriasCalculo calorias = new CaloriasCalculo();
            calorias.setPeso(rs.getDouble("peso"));
            calorias.setCalorias(rs.getDouble("calorias"));
            calorias.setNivelActividadFisica(rs.getInt("nivelActividadFisica"));
            calorias.setRazonConsulta(rs.getInt("razonConsulta"));
            consulta.setTotalCalorias(calorias);

            // ---- Macronutrientes ----
            Macronutrientes macros = new Macronutrientes();
            macros.setCarbohidratos(rs.getDouble("carbohidratos"));
            macros.setProteinas(rs.getDouble("proteinas"));
            macros.setLipidos(rs.getDouble("lipidos"));
            consulta.setMacronutrientes(macros);

            // ---- Anamnesis ----
            AnamnesisData anamnesis = new AnamnesisData();
            anamnesis.setCondicionesMedicas(rs.getString("condicionesMedicas"));
            anamnesis.setMedicacion(rs.getString("medicacion"));
            anamnesis.setHistorialCirugias(rs.getString("historialCirugias"));
            anamnesis.setAlergias(rs.getString("alergias"));
            anamnesis.setPreferenciaComida(rs.getString("preferenciaComida"));
            anamnesis.setHorarioSueno(rs.getString("horarioSueno"));
            anamnesis.setNivelEstres(rs.getInt("nivelEstres"));
            anamnesis.setHabitoAlimenticio(rs.getString("habitosAlimenticios"));
            anamnesis.setTipoLiquidoConsumido(rs.getString("tipoLiquidos"));
            anamnesis.setCantidadLiquidoConsumido(rs.getDouble("cantidadLiquidos"));
            anamnesis.setBarreraAlimenticia(rs.getString("dificultades"));
            consulta.setAnamnesisData(anamnesis);

            return consulta;
        }
    }
}

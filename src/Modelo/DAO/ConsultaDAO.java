package DAO;

import POJOs.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
            
            statement.setInt(24, Integer.parseInt( consulta.getClaveConsulta() ));

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
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, claveConsulta);
            statement.executeUpdate();
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
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, claveConsulta);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            Consulta consulta = new Consulta();
            consulta.setClaveConsulta(claveConsulta);
            consulta.setClavePaciente(resultSet.getString("clavePaciente"));
            consulta.setClaveNutriologo(resultSet.getString("claveNutriologo"));
            consulta.setFechaVisita(resultSet.getString("fecha"));
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
    }
    
    public ArrayList<Consulta> leerPorPaciente(String clavePaciente) throws SQLException {

        ArrayList<Consulta> lista = new ArrayList<>();

        String sql = "SELECT claveConsulta FROM consulta WHERE clavePaciente = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clavePaciente);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String claveConsulta = resultSet.getString("claveConsulta");

                // Reutilizamos tu método existente que reconstruye la consulta
                Consulta consulta = leerPorClave(claveConsulta);

                lista.add(consulta);
            }
        }

        return lista;
    }

}

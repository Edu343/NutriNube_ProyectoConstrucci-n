package Modelo.DAO;

import Modelo.POJOs.Consulta;
import Modelo.POJOs.Expediente;
import Modelo.POJOs.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DAO encargado de manejar operaciones CRUD para la entidad Paciente.
 */
public class PacienteDAO extends DatabaseManager {

    /**
     * Inserta un nuevo paciente en la base de datos.
     */
    @Override
    public void insertar(Object entidad) throws SQLException {
        Paciente paciente = (Paciente) entidad;

        String sql = """
            INSERT INTO paciente
            (clave, nombre, apellido, correo, sexo, telefono,
             claveNutriologo, altura, fechaNacimiento)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, paciente.getClavePaciente());
            statement.setString(2, paciente.getNombre());
            statement.setString(3, paciente.getApellido());
            statement.setString(4, paciente.getCorreo());
            statement.setInt(5, paciente.getSexo());
            statement.setString(6, paciente.getTelefono());
            statement.setString(7, paciente.getClaveNutriologo());
            statement.setDouble(8, paciente.getAltura());
            statement.setString(9, paciente.getFechaNacimiento());

            statement.executeUpdate();
        }
    }

    /**
     * Actualiza los datos de un paciente ya registrado.
     */
    @Override
    public void actualizar(Object entidad) throws SQLException {
        Paciente paciente = (Paciente) entidad;

        String sql = """
            UPDATE paciente SET
                nombre = ?, apellido = ?, correo = ?, sexo = ?,
                telefono = ?, claveNutriologo = ?, altura = ?, fechaNacimiento = ?
            WHERE clave = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, paciente.getNombre());
            statement.setString(2, paciente.getApellido());
            statement.setString(3, paciente.getCorreo());
            statement.setInt(4, paciente.getSexo());
            statement.setString(5, paciente.getTelefono());
            statement.setString(6, paciente.getClaveNutriologo());
            statement.setDouble(7, paciente.getAltura());
            statement.setString(8, paciente.getFechaNacimiento());
            statement.setString(9, paciente.getClavePaciente());

            statement.executeUpdate();
        }
    }

    /**
     * Elimina un paciente mediante su clave.
     */
    @Override
    public void eliminar(String clave) throws SQLException {
        String sql = "DELETE FROM paciente WHERE clave = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clave);
            statement.executeUpdate();
        }
    }

    /**
     * Recupera un paciente por su clave.
     */
    public Paciente leerPorClave(String clave) throws SQLException {

        String sql = "SELECT * FROM paciente WHERE clave = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clave);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            Paciente paciente = new Paciente();
            paciente.setClavePaciente(resultSet.getString("clave"));
            paciente.setNombre(resultSet.getString("nombre"));
            paciente.setApellido(resultSet.getString("apellido"));
            paciente.setCorreo(resultSet.getString("correo"));
            paciente.setSexo(resultSet.getInt("sexo"));
            paciente.setTelefono(resultSet.getString("telefono"));
            paciente.setClaveNutriologo(resultSet.getString("claveNutriologo"));
            paciente.setAltura(resultSet.getDouble("altura"));
            paciente.setFechaNacimiento(resultSet.getString("fechaNacimiento"));
            return paciente;
        }
    }
    
    public Expediente obtenerExpedienteCompleto(String clavePaciente) throws SQLException {

        // 1. Obtener paciente
        Paciente paciente = leerPorClave(clavePaciente);
        if (paciente == null) return null;

        // 2. Obtener historial de consultas
        ConsultaDAO consultaDAO = new ConsultaDAO();
        ArrayList<Consulta> consultas = consultaDAO.leerPorPaciente(clavePaciente);

        // 3. Crear expediente
        Expediente expediente = new Expediente();
        expediente.setPaciente(paciente);
        expediente.setConsultas(consultas);

        return expediente;
    }
    
    public ArrayList<Paciente> leerPorNutriologo(String claveNutriologo) throws SQLException {

        ArrayList<Paciente> lista = new ArrayList<>();
        // Modificamos la consulta SQL para filtrar por claveNutriologo
        String sql = "SELECT clave FROM paciente WHERE claveNutriologo = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, claveNutriologo);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String clavePaciente = resultSet.getString("clave");
                Paciente paciente = leerPorClave(clavePaciente);

                if (paciente != null) {
                    lista.add(paciente);
                }
            }
        }

        return lista;
    }

}

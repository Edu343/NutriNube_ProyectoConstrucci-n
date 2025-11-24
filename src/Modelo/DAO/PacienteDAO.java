package DAO;

import POJOs.Paciente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, paciente.getClavePaciente());
            stmt.setString(2, paciente.getNombre());
            stmt.setString(3, paciente.getApellido());
            stmt.setString(4, paciente.getCorreo());
            stmt.setBoolean(5, paciente.getSexo());
            stmt.setInt(6, paciente.getTelefono());
            stmt.setString(7, paciente.getClaveNutriologo());
            stmt.setDouble(8, paciente.getAltura());
            stmt.setString(9, paciente.getFechaNacimiento());

            stmt.executeUpdate();
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
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getCorreo());
            stmt.setString(3, paciente.getApellido());
            stmt.setBoolean(4, paciente.getSexo());
            stmt.setInt(5, paciente.getTelefono());
            stmt.setString(6, paciente.getClaveNutriologo());
            stmt.setDouble(7, paciente.getAltura());
            stmt.setString(8, paciente.getFechaNacimiento());
            stmt.setString(9, paciente.getClavePaciente());

            stmt.executeUpdate();
        }
    }

    /**
     * Elimina un paciente mediante su clave.
     */
    @Override
    public void eliminar(String clave) throws SQLException {
        String sql = "DELETE FROM paciente WHERE clave = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, clave);
            stmt.executeUpdate();
        }
    }

    /**
     * Recupera un paciente por su clave.
     */
    public Paciente leerPorClave(String clave) throws SQLException {

        String sql = "SELECT * FROM paciente WHERE clave = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, clave);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) return null;

            Paciente paciente = new Paciente();
            paciente.setClavePaciente(rs.getString("clave"));
            paciente.setNombre(rs.getString("nombre"));
            paciente.setApellido(rs.getString("apellido"));
            paciente.setCorreo(rs.getString("correo"));
            paciente.setSexo(rs.getBoolean("sexo"));
            paciente.setTelefono(rs.getInt("telefono"));
            paciente.setClaveNutriologo(rs.getString("claveNutriologo"));
            paciente.setAltura(rs.getDouble("altura"));
            paciente.setFechaNacimiento(rs.getString("fechaNacimiento"));
            return paciente;
        }
    }
}

package DAO;

import POJOs.Nutriologo;
import POJOs.Paciente;

import java.sql.*;
import java.util.ArrayList;

/**
 * DAO encargado de gestionar operaciones CRUD para la entidad Nutriologo.
 */
public class NutriologoDAO extends DatabaseManager {

    /**
     * Inserta un nutriólogo con contraseña hasheada.
     */
    @Override
    public void insertar(Object entidad) throws SQLException {
        Nutriologo nutriologo = (Nutriologo) entidad;

        String sql = """
            INSERT INTO nutriologo
            (clave, nombre, apellido, correo, hashContrasena, saltContrasena)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nutriologo.getClaveNutriologo());
            statement.setString(2, nutriologo.getNombre());
            statement.setString(3, nutriologo.getApellido());
            statement.setString(4, nutriologo.getCorreo());
            statement.setString(5, nutriologo.getHashContrasena());
            statement.setString(6, nutriologo.getSaltContrasena());

            statement.executeUpdate();
        }
    }

    /**
     * Actualiza un nutriólogo ya registrado.
     */
    @Override
    public void actualizar(Object entidad) throws SQLException {
        Nutriologo nutriologo = (Nutriologo) entidad;

        String sql = """
            UPDATE nutriologo SET
                nombre = ?, apellido = ?, correo = ?,
                saltContrasena = ?, hashContrasena = ?
            WHERE clave = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nutriologo.getClaveNutriologo());
            statement.setString(2, nutriologo.getNombre());
            statement.setString(3, nutriologo.getApellido());
            statement.setString(4, nutriologo.getCorreo());
            statement.setString(5, nutriologo.getHashContrasena());
            statement.setString(6, nutriologo.getSaltContrasena());

            statement.executeUpdate();
        }
    }

    /**
     * Elimina un nutriólogo por su clave.
     */
    @Override
    public void eliminar(String clave) throws SQLException {
        String sql = "DELETE FROM nutriologo WHERE clave = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clave);
            statement.executeUpdate();
        }
    }

    /**
     * Recupera un nutriólogo según su clave visible.
     */
    public Nutriologo leerPorClave(String clave) throws SQLException {
        String sql = "SELECT * FROM nutriologo WHERE clave = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, clave);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) return null;

            Nutriologo nutriologo = new Nutriologo();
            nutriologo.setClaveNutriologo(rs.getString("clave"));
            nutriologo.setNombre(rs.getString("nombre"));
            nutriologo.setApellido(rs.getString("apellido"));
            nutriologo.setCorreo(rs.getString("correo"));
            nutriologo.setHashContrasena(rs.getString("hashContrasena"));
            nutriologo.setSaltContrasena(rs.getString("saltContrasena"));

            return nutriologo;
        }
    }
    

    public ArrayList<Paciente> obtenerListaPacientes(String claveNutriologo) throws SQLException {
        
        
        PacienteDAO pacienteDAO = new PacienteDAO();
        ArrayList<Paciente> pacientes = pacienteDAO.leerPorNutriologo(claveNutriologo);
        
        return pacientes;
    }
}
    

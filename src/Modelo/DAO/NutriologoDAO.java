package Modelo.DAO;


import Modelo.POJOs.Nutriologo;
import Modelo.POJOs.Paciente;
import Modelo.Servicios.HashingServicio;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO encargado de gestionar operaciones CRUD para la entidad Nutriologo.
 * Aquí se aplica el hashing directamente ANTES de insertar o actualizar.
 */
public class NutriologoDAO extends DatabaseManager {

    private final HashingServicio hashingServicio = new HashingServicio();

    /**
     * Inserta un nutriólogo, generando hash y salt antes de guardar.
     */
    @Override
    public void insertar(Object entidad) throws SQLException {
        Nutriologo nutriologo = (Nutriologo) entidad;

        // ================================
        // 1. Generar salt y hash PREVIO
        // ================================
        String salt = hashingServicio.generarSaltContrasena();
        String hash = hashingServicio.generarHashContrasena(
                nutriologo.getHashContrasena(),   // ← aquí tú guardaste la contraseña en texto plano
                salt
        );

        // Sobrescribir con los valores finales que sí se guardan
        nutriologo.setSaltContrasena(salt);
        nutriologo.setHashContrasena(hash);

        String sql = """
            INSERT INTO nutriologo
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
            statement.setString(5, nutriologo.getHashContrasena()); // hash final
            statement.setString(6, nutriologo.getSaltContrasena()); // salt generado

            statement.executeUpdate();
        }
    }

    /**
     * Actualiza un nutriólogo. Si se manda una contraseña nueva,
     * se genera nuevo hash y nuevo salt.
     */
    @Override
    public void actualizar(Object entidad) throws SQLException {
        Nutriologo nutriologo = (Nutriologo) entidad;

        // ================================
        // 1. Si trae texto plano, lo hasheamos de nuevo
        // ================================
        if (nutriologo.getHashContrasena() != null && !nutriologo.getHashContrasena().isEmpty()) {

            String nuevoSalt = hashingServicio.generarSaltContrasena();
            String nuevoHash = hashingServicio.generarHashContrasena(
                    nutriologo.getHashContrasena(), // ← contraseña en texto plano
                    nuevoSalt
            );

            nutriologo.setSaltContrasena(nuevoSalt);
            nutriologo.setHashContrasena(nuevoHash);
        }

        String sql = """
            UPDATE nutriologo SET
            UPDATE nutriologo SET
                nombre = ?, apellido = ?, correo = ?,
                saltContrasena = ?, hashContrasena = ?
            WHERE clave = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nutriologo.getNombre());
            statement.setString(2, nutriologo.getApellido());
            statement.setString(3, nutriologo.getCorreo());
            statement.setString(4, nutriologo.getSaltContrasena());
            statement.setString(5, nutriologo.getHashContrasena());
            statement.setString(6, nutriologo.getClaveNutriologo());

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
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            Nutriologo nutriologo = new Nutriologo();
            nutriologo.setClaveNutriologo(resultSet.getString("clave"));
            nutriologo.setNombre(resultSet.getString("nombre"));
            nutriologo.setApellido(resultSet.getString("apellido"));
            nutriologo.setCorreo(resultSet.getString("correo"));
            nutriologo.setHashContrasena(resultSet.getString("hashContrasena"));
            nutriologo.setSaltContrasena(resultSet.getString("saltContrasena"));

            return nutriologo;
        }
    }
    

    public ArrayList<Paciente> obtenerListaPacientes(String claveNutriologo) throws SQLException {
        
        PacienteDAO pacienteDAO = new PacienteDAO();
        ArrayList<Paciente> pacientes = pacienteDAO.leerPorNutriologo(claveNutriologo);
        
        return pacientes;
    }
}
    

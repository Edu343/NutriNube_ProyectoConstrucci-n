package Modelo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase abstracta base para todos los DAO del sistema NutriNube.
 * Administra la conexión a la base de datos SQLite y define
 * métodos CRUD comunes que deben implementar las clases hijas.
 */
public abstract class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:NutriNube.db";

    /**
     * Proporciona la conexión activa hacia la base de datos.
     *
     * @return Conexión SQLite lista para usarse.
     */
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Inserta una entidad en la base de datos.
     *
     * @param entidad Objeto correspondiente al registro.
     */
    public abstract void insertar(Object entidad) throws Exception;

    /**
     * Actualiza una entidad en la base de datos.
     *
     * @param entidad Objeto correspondiente al registro.
     */
    public abstract void actualizar(Object entidad) throws Exception;

    /**
     * Elimina un registro según su clave primaria.
     *
     * @param clave Clave identificadora del registro.
     */
    public abstract void eliminar(String clave) throws Exception;
}

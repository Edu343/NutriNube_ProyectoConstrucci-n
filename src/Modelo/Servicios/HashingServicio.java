package Servicios;


import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Servicio responsable del manejo seguro de contraseñas mediante hashing PBKDF2
 * y salts aleatorios. Proporciona funciones para generar salts, generar hashes
 * de contraseñas y verificar credenciales de forma segura.
 */

public class HashingServicio {

    private static final int ITERACIONES = 10000;        
    private static final int TAMANIO_SALT_BYTES = 16;      
    private static final int LONGITUD_HASH_BITS = 256;   

    /**
     * Genera un salt criptográficamente seguro.
     * 
     * @return Salt codificado en Base64 para almacenamiento en BD.
     */
    public String generarSaltContrasena() {
        byte[] salt = new byte[TAMANIO_SALT_BYTES];

        new SecureRandom().nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Genera un hash PBKDF2-HMAC-SHA256 a partir de una contraseña y su salt asociado.
     *
     * @param contrasena Contraseña en texto plano.
     * @param saltBase64 Salt codificado en Base64.
     * @return Hash de la contraseña codificado en Base64.
     */
    public String generarHashContrasena(String contrasena, String saltBase64) {
        try {
            byte[] salt = Base64.getDecoder().decode(saltBase64);

            // Parametros necesarios para ejecutar el hash
            PBEKeySpec spec = new PBEKeySpec(
                contrasena.toCharArray(),
                salt,
                ITERACIONES,
                LONGITUD_HASH_BITS
            );

            
            // PBKDF2 con SHA-256: algoritmo estándar seguro.
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException("Error generando hash de contraseña", e);
        }
    }

    /**
     * Verifica si una contraseña ingresada coincide con la almacenada.
     *
     * @param contrasenaIngresada Contraseña proporcionada por el usuario.
     * @param saltBase64 Salt almacenado en la base de datos.
     * @param hashAlmacenado Hash almacenado correspondiente.
     * @return true si la contraseña coincide; false si no.
     */
    public boolean verificarContrasena(String contrasenaIngresada,
                                       String saltBase64,
                                       String hashAlmacenado) {

        String nuevoHash = generarHashContrasena(contrasenaIngresada, saltBase64);

        byte[] hashA = Base64.getDecoder().decode(hashAlmacenado);
        byte[] hashB = Base64.getDecoder().decode(nuevoHash);

        return MessageDigest.isEqual(hashA, hashB);
    }
}
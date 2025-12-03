package Servicios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Modelo.Servicios.HashingServicio;

/**
 * Pruebas unitarias para la clase HashingServicio.
 */
public class HashingServicioTest {

    private final HashingServicio hashingServicio = new HashingServicio();

    @Test
    @DisplayName("Generar salt: no debe ser null")
    public void saltNoDebeSerNull() {
        assertNotNull(hashingServicio.generarSaltContrasena());
    }

    @Test
    @DisplayName("Generar salt: siempre debe ser diferente")
    public void saltDebeSerUnico() {
        String salt1 = hashingServicio.generarSaltContrasena();
        String salt2 = hashingServicio.generarSaltContrasena();
        assertNotEquals(salt1, salt2);
    }

    @Test
    @DisplayName("Generar hash: no debe ser igual al texto plano")
    public void hashNoDebeSerTextoPlano() {
        String salt = hashingServicio.generarSaltContrasena();
        String hash = hashingServicio.generarHashContrasena("Password123", salt);
        assertNotEquals("Password123", hash);
    }

    @Test
    @DisplayName("Generar hash: debe cambiar cuando cambia el salt")
    public void hashCambiaConSaltDistinto() {
        String salt1 = hashingServicio.generarSaltContrasena();
        String salt2 = hashingServicio.generarSaltContrasena();

        String hash1 = hashingServicio.generarHashContrasena("Password123", salt1);
        String hash2 = hashingServicio.generarHashContrasena("Password123", salt2);

        assertNotEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Generar hash: debe cambiar cuando cambia la contraseña")
    public void hashCambiaConContrasenaDistinta() {
        String salt = hashingServicio.generarSaltContrasena();

        String hash1 = hashingServicio.generarHashContrasena("Uno", salt);
        String hash2 = hashingServicio.generarHashContrasena("Dos", salt);

        assertNotEquals(hash1, hash2);
    }

    @Test
    @DisplayName("Verificar contraseña: debe validar correctamente")
    public void verificarContrasenaCorrecta() {
        String salt = hashingServicio.generarSaltContrasena();
        String hash = hashingServicio.generarHashContrasena("MiClaveSegura", salt);

        assertTrue(hashingServicio.verificarContrasena("MiClaveSegura", salt, hash));
    }

    @Test
    @DisplayName("Verificar contraseña: debe fallar con contraseña incorrecta")
    public void verificarContrasenaIncorrecta() {
        String salt = hashingServicio.generarSaltContrasena();
        String hash = hashingServicio.generarHashContrasena("Correcta", salt);

        assertFalse(hashingServicio.verificarContrasena("Incorrecta", salt, hash));
    }

    @Test
    @DisplayName("Verificar contraseña: debe fallar si el salt no coincide")
    public void verificarContrasenaSaltIncorrecto() {
        String saltCorrecto = hashingServicio.generarSaltContrasena();
        String saltIncorrecto = hashingServicio.generarSaltContrasena();

        String hash = hashingServicio.generarHashContrasena("Clave!", saltCorrecto);

        assertFalse(hashingServicio.verificarContrasena("Clave!", saltIncorrecto, hash));
    }
}
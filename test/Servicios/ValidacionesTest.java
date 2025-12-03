package Servicios;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Modelo.POJOs.CaloriasCalculo;
import Modelo.POJOs.Consulta;
import Modelo.POJOs.Nutriologo;
import Modelo.POJOs.Paciente;
import Modelo.Servicios.Validaciones;


/**
 * Pruebas unitarias para la clase Validaciones.
 */
public class ValidacionesTest {

    private final Validaciones validaciones = new Validaciones();

    // -------------------- PACIENTE --------------------

    @Test
    @DisplayName("Paciente válido debe pasar validación")
    public void pacienteValido() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Juan");
        paciente.setApellido("Perez");
        paciente.setAltura(1.70);

        assertTrue(validaciones.isValidPaciente(paciente));
    }

    @Test
    @DisplayName("Paciente sin nombre debe fallar")
    public void pacienteSinNombre() {
        Paciente paciente = new Paciente();
        paciente.setNombre("");
        paciente.setApellido("Perez");
        paciente.setAltura(1.70);

        assertFalse(validaciones.isValidPaciente(paciente));
    }

    @Test
    @DisplayName("Paciente con altura inválida debe fallar")
    public void pacienteAlturaInvalida() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Ana");
        paciente.setApellido("Diaz");
        paciente.setAltura(0);

        assertFalse(validaciones.isValidPaciente(paciente));
    }

    // -------------------- NUTRIÓLOGO --------------------

    @Test
    @DisplayName("Nutriólogo válido debe pasar")
    public void nutriologoValido() {
        Nutriologo nutriologo = new Nutriologo();
        nutriologo.setClaveNutriologo("ABC123");
        nutriologo.setCorreo("correo@mail.com");
        nutriologo.setNombre("Luis");

        assertTrue(validaciones.isValidNutriologo(nutriologo));
    }

    @Test
    @DisplayName("Nutriólogo con clave inválida debe fallar")
    public void nutriologoClaveInvalida() {
        Nutriologo nutriologo = new Nutriologo();
        nutriologo.setClaveNutriologo("12");
        nutriologo.setCorreo("correo@mail.com");
        nutriologo.setNombre("Luis");

        assertFalse(validaciones.isValidNutriologo(nutriologo));
    }

    @Test
    @DisplayName("Nutriólogo sin correo debe fallar")
    public void nutriologoCorreoVacio() {
        Nutriologo nutriologo = new Nutriologo();
        nutriologo.setClaveNutriologo("ABC123");
        nutriologo.setCorreo("");
        nutriologo.setNombre("Luis");

        assertFalse(validaciones.isValidNutriologo(nutriologo));
    }

    @Test
    @DisplayName("Nutriólogo sin nombre debe fallar")
    public void nutriologoNombreVacio() {
        Nutriologo nutriologo = new Nutriologo();
        nutriologo.setClaveNutriologo("ABC123");
        nutriologo.setCorreo("correo@mail.com");
        nutriologo.setNombre("");

        assertFalse(validaciones.isValidNutriologo(nutriologo));
    }

    // -------------------- CONSULTA --------------------

    @Test
    @DisplayName("Consulta válida debe pasar")
    public void consultaValida() {
        Consulta consulta = new Consulta();
        CaloriasCalculo caloriasCalculo = new CaloriasCalculo();
        caloriasCalculo.setPeso(70);
        consulta.setTotalCalorias(caloriasCalculo);
        consulta.setClavePaciente("P001");

        assertTrue(validaciones.isValidConsulta(consulta));
    }

    @Test
    @DisplayName("Consulta con peso inválido debe fallar")
    public void consultaPesoInvalido() {
        Consulta consulta = new Consulta();
        CaloriasCalculo cc = new CaloriasCalculo();
        cc.setPeso(0);
        consulta.setTotalCalorias(cc);
        consulta.setClavePaciente("P001");

        assertFalse(validaciones.isValidConsulta(consulta));
    }

    @Test
    @DisplayName("Consulta sin clave de paciente debe fallar")
    public void consultaClavePacienteVacia() {
        Consulta consulta = new Consulta();
        CaloriasCalculo calcuroCalorias = new CaloriasCalculo();
        calcuroCalorias.setPeso(60);
        consulta.setTotalCalorias(calcuroCalorias);
        consulta.setClavePaciente("");

        assertFalse(validaciones.isValidConsulta(consulta));
    }
}
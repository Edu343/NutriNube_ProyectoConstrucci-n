package Servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import POJOs.CaloriasCalculo; 
import POJOs.Paciente; 

/**
 * Clase de pruebas unitarias para NutricionServicio.
 */
public class NutricionServicioTest {

    private NutricionServicio servicio;

    // --- Constantes Base (Asumiendo que el año actual es 2025 para cálculo) ---
    
    private static final String ANIO_NACIMIENTO_30 = "1995"; // Edad = 30
    private static final String ANIO_NACIMIENTO_25 = "2000"; // Edad = 25
    
    // TMR para Hombre (70kg, 175cm, 30 años)
    private static final double TMR_HOMBRE_30 = 1648.75;
    // TMR para Mujer (60kg, 165cm, 30 años)
    private static final double TMR_MUJER_30 = 1320.25;
    // TMR para Mujer (60kg, 165cm, 25 años)
    private static final double TMR_MUJER_25 = 1345.25;

 
    private static final double CALORIAS_HOMBRE_LIGERO = TMR_HOMBRE_30 * 1.1; 
    private static final double CALORIAS_HOMBRE_MODERADO = TMR_HOMBRE_30 * 1.2; 
    private static final double CALORIAS_HOMBRE_PESADO = TMR_HOMBRE_30 * 1.3; 


    private static final double CALORIAS_MUJER_LIGERO = TMR_MUJER_30 * 1.1; 
    private static final double CALORIAS_MUJER_MODERADO = TMR_MUJER_30 * 1.2; 
    private static final double CALORIAS_MUJER_PESADO = TMR_MUJER_30 * 1.3; 


    @BeforeEach
    public void setUp() {
        servicio = new NutricionServicio(); 
    }
    

    // Pruebas para calcularTmr

    
    @Test
    public void calcularTmr_Hombre70kg175cm30a_RetornaTMRHombre() {
        double resultadoObtenido = servicio.calcularTmr(70.0, 30, 175.0, true);
        assertEquals(TMR_HOMBRE_30, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularTmr_Mujer60kg165cm25a_RetornaTMRMujer() {
        double resultadoObtenido = servicio.calcularTmr(60.0, 25, 165.0, false);
        assertEquals(TMR_MUJER_25, resultadoObtenido, 0.001);
    }
    

    // Pruebas para calcularCalorias (Hombre)

    
    @Test
    public void calcularCalorias_Hombre30aLigero_RetornaCaloriasLigeras() {
        Paciente paciente = new Paciente();
        paciente.setAltura(175.0);
        paciente.setSexo(true); 
        paciente.setFechaNacimiento(ANIO_NACIMIENTO_30);

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.1); 
        datos.setPeso(70.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(CALORIAS_HOMBRE_LIGERO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Hombre30aModerado_RetornaCaloriasModeradas() {
        Paciente paciente = new Paciente();
        paciente.setAltura(175.0);
        paciente.setSexo(true);
        paciente.setFechaNacimiento(ANIO_NACIMIENTO_30);

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.2); 
        datos.setPeso(70.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(CALORIAS_HOMBRE_MODERADO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Hombre30aPesado_RetornaCaloriasPesadas() {
        Paciente paciente = new Paciente();
        paciente.setAltura(175.0);
        paciente.setSexo(true);
        paciente.setFechaNacimiento(ANIO_NACIMIENTO_30);

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.3); 
        datos.setPeso(70.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(CALORIAS_HOMBRE_PESADO, resultadoObtenido, 0.001);
    }


    // Pruebas para calcularCalorias (Mujer)

    
    @Test
    public void calcularCalorias_Mujer30aLigero_RetornaCaloriasLigeras() {
        Paciente paciente = new Paciente();
        paciente.setAltura(165.0);
        paciente.setSexo(false);
        paciente.setFechaNacimiento(ANIO_NACIMIENTO_30);

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.1); 
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(CALORIAS_MUJER_LIGERO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Mujer30aModerado_RetornaCaloriasModeradas() {
        Paciente paciente = new Paciente();
        paciente.setAltura(165.0);
        paciente.setSexo(false);
        paciente.setFechaNacimiento(ANIO_NACIMIENTO_30);

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.2); 
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(CALORIAS_MUJER_MODERADO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Mujer30aPesado_RetornaCaloriasPesadas() {
        Paciente paciente = new Paciente();
        paciente.setAltura(165.0);
        paciente.setSexo(false);
        paciente.setFechaNacimiento(ANIO_NACIMIENTO_30);

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.3); 
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(CALORIAS_MUJER_PESADO, resultadoObtenido, 0.001);
    }
    
    // ---------------------------------------------
    // Pruebas Indirectas de calcularEdad (Lógica)
    // ---------------------------------------------
    
    @Test
    public void calcularCalorias_MujerAnio2000Factor1_RetornaTMRMujer25() {
        Paciente paciente = new Paciente();
        paciente.setAltura(165.0);
        paciente.setSexo(false);
        paciente.setFechaNacimiento(ANIO_NACIMIENTO_25); // Edad 25

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.0); 
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(TMR_MUJER_25, resultadoObtenido, 0.001, "Debe retornar el TMR para edad 25 (calculada)");
    }
    
    @Test
    public void calcularCalorias_MujerNullFactor1_RetornaTMRMujer30Default() {
        Paciente paciente = new Paciente();
        paciente.setAltura(165.0);
        paciente.setSexo(false);
        paciente.setFechaNacimiento(null); // Edad 30 (Default)

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1.0); 
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, paciente);
        assertEquals(TMR_MUJER_30, resultadoObtenido, 0.001, "Debe retornar el TMR para edad 30 (default)");
    }
}
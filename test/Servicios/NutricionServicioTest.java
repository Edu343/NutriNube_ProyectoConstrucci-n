package Servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Modelo.POJOs.CaloriasCalculo;
import Modelo.POJOs.Paciente;
import Modelo.Servicios.NutricionServicio;

import static org.junit.jupiter.api.Assertions.*; 

/**
 * Clase de pruebas unitarias para NutricionServicio.
 */
public class NutricionServicioTest {

    private NutricionServicio servicio;

    // --- Constantes Base (Asumiendo que el año actual es 2025 para cálculo) ---
    
    private static final String ANIO_NACIMIENTO_30 = "1995"; // Edad = 30
    
    
    // Constantes de sexo para coincidir con la lógica del servicio
    private static final int SEXO_HOMBRE = 1;
    private static final int SEXO_MUJER = 0;
    
    // TMR para Hombre (70kg, 175cm, 30 años)
    private static final double TMR_HOMBRE_30 = 1648.75;
    // TMR para Mujer (60kg, 165cm, 30 años)
    private static final double TMR_MUJER_30 = 1320.25;
    // TMR para Mujer (60kg, 165cm, 25 años)
    private static final double TMR_MUJER_25 = 1345.25;

    // Factores indicados: 1.1, 1.2 y 1.3
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
        // Se cambió boolean true por SEXO_HOMBRE (1)
        double resultadoObtenido = servicio.calcularTmr(70.0, 30, 175.0, SEXO_HOMBRE);
        assertEquals(TMR_HOMBRE_30, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularTmr_Mujer60kg165cm25a_RetornaTMRMujer() {
        // Se cambió boolean false por SEXO_MUJER (0)
        double resultadoObtenido = servicio.calcularTmr(60.0, 25, 165.0, SEXO_MUJER);
        assertEquals(TMR_MUJER_25, resultadoObtenido, 0.001);
    }
    

    // Pruebas para calcularCalorias (Hombre)

    
    @Test
    public void calcularCalorias_Hombre30aLigero_RetornaCaloriasLigeras() {
        // Datos sueltos que simulan venir del Paciente
        double altura = 175.0;
        int sexo = SEXO_HOMBRE; 
        String fechaNac = ANIO_NACIMIENTO_30;

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1); // Nivel 1 corresponde al factor 1.1
        datos.setPeso(70.0); 

        // Se pasa la firma actualizada del método
        double resultadoObtenido = servicio.calcularCalorias(datos, sexo, altura, fechaNac);
        assertEquals(CALORIAS_HOMBRE_LIGERO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Hombre30aModerado_RetornaCaloriasModeradas() {
        double altura = 175.0;
        int sexo = SEXO_HOMBRE;
        String fechaNac = ANIO_NACIMIENTO_30;

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(2); // Nivel 2 corresponde al factor 1.2
        datos.setPeso(70.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, sexo, altura, fechaNac);
        assertEquals(CALORIAS_HOMBRE_MODERADO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Hombre30aPesado_RetornaCaloriasPesadas() {
        double altura = 175.0;
        int sexo = SEXO_HOMBRE;
        String fechaNac = ANIO_NACIMIENTO_30;

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(3); // Nivel 3 corresponde al factor 1.3
        datos.setPeso(70.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, sexo, altura, fechaNac);
        assertEquals(CALORIAS_HOMBRE_PESADO, resultadoObtenido, 0.001);
    }


    // Pruebas para calcularCalorias (Mujer)

    
    @Test
    public void calcularCalorias_Mujer30aLigero_RetornaCaloriasLigeras() {
        double altura = 165.0;
        int sexo = SEXO_MUJER;
        String fechaNac = ANIO_NACIMIENTO_30;

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(1); // Nivel 1 -> 1.1
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, sexo, altura, fechaNac);
        assertEquals(CALORIAS_MUJER_LIGERO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Mujer30aModerado_RetornaCaloriasModeradas() {
        double altura = 165.0;
        int sexo = SEXO_MUJER;
        String fechaNac = ANIO_NACIMIENTO_30;

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(2); // Nivel 2 -> 1.2
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, sexo, altura, fechaNac);
        assertEquals(CALORIAS_MUJER_MODERADO, resultadoObtenido, 0.001);
    }

    @Test
    public void calcularCalorias_Mujer30aPesado_RetornaCaloriasPesadas() {
        double altura = 165.0;
        int sexo = SEXO_MUJER;
        String fechaNac = ANIO_NACIMIENTO_30;

        CaloriasCalculo datos = new CaloriasCalculo();
        datos.setNivelActividadFisica(3); // Nivel 3 -> 1.3
        datos.setPeso(60.0); 

        double resultadoObtenido = servicio.calcularCalorias(datos, sexo, altura, fechaNac);
        assertEquals(CALORIAS_MUJER_PESADO, resultadoObtenido, 0.001);
    }
    
 
    // Pruebas Indirectas de calcularEdad (Lógica)
 

    @Test
    public void calcularEdad_FechaLiteral_RetornaEdadCorrecta() {
        // Prueba con un String literal exacto
        String fechaLiteral = "2000-01-01";
        
        // Ejecutamos el método
        int edadObtenida = servicio.calcularEdad(fechaLiteral);
        
        // Verificamos el resultado esperado (hardcodeado)
        // NOTA: Si hoy es 2025, la edad debe ser 25.
        assertEquals(25, edadObtenida, "La edad para '2000-01-01' en el año 2025 debe ser 25.");
    }
}
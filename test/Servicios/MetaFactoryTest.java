package Servicios;

import Modelo.POJOs.MetaFactory;
import Modelo.POJOs.MetaNutricional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase MetaFactory, responsable de
 * devolver los objetivos de macronutrientes predefinidos.
 */
public class MetaFactoryTest {

    // Usamos una pequeña tolerancia para la comparación de números flotantes (dobles)
    private static final double DELTA = 0.001; 

    
    // Pruebas de casos de exito y valores especificos
   

    @Test
    @DisplayName("Opción 1 (Disminuir grasa): Verifica valores correctos")
    public void obtenerMeta_Opcion1_ValoresCorrectos() {
        MetaNutricional meta = MetaFactory.obtenerMeta(1);
        
        // Valores esperados: (0.48, 0.24, 0.28)
        assertEquals(0.48, meta.getCarbohidratosObjetivo(), DELTA);
        assertEquals(0.24, meta.getLipidosObjetivo(), DELTA);
        assertEquals(0.28, meta.getProteinasObjetivo(), DELTA);
    }
    
    @Test
    @DisplayName("Opción 2 (Aumentar masa): Verifica valores correctos")
    public void obtenerMeta_Opcion2_ValoresCorrectos() {
        MetaNutricional meta = MetaFactory.obtenerMeta(2);
        
        // Valores esperados: (0.50, 0.23, 0.27)
        assertEquals(0.50, meta.getCarbohidratosObjetivo(), DELTA);
        assertEquals(0.23, meta.getLipidosObjetivo(), DELTA);
        assertEquals(0.27, meta.getProteinasObjetivo(), DELTA);
    }

    @Test
    @DisplayName("Opción 3 (Recomposición): Verifica valores correctos")
    public void obtenerMeta_Opcion3_ValoresCorrectos() {
        MetaNutricional meta = MetaFactory.obtenerMeta(3);
        
        // Valores esperados: (0.48, 0.24, 0.28) - Misma que Opción 1
        assertEquals(0.48, meta.getCarbohidratosObjetivo(), DELTA);
        assertEquals(0.24, meta.getLipidosObjetivo(), DELTA);
        assertEquals(0.28, meta.getProteinasObjetivo(), DELTA);
    }

    @Test
    @DisplayName("Opción 4 (Salud/Neutro): Verifica valores correctos")
    public void obtenerMeta_Opcion4_ValoresCorrectos() {
        MetaNutricional meta = MetaFactory.obtenerMeta(4);
        
        // Valores esperados: (0.52, 0.22, 0.26)
        assertEquals(0.52, meta.getCarbohidratosObjetivo(), DELTA);
        assertEquals(0.22, meta.getLipidosObjetivo(), DELTA);
        assertEquals(0.26, meta.getProteinasObjetivo(), DELTA);
    }

    
    // Prueba de Integridad de los datos (SUMA TOTAL)
    
    
    @Test
    @DisplayName("Opciones válidas: La suma de porcentajes debe ser 1.0 (100%)")
    public void obtenerMeta_TodasLasOpcionesSuman1() {
    	
        // Opción 1
        MetaNutricional meta1 = MetaFactory.obtenerMeta(1);
        double suma1 = meta1.getCarbohidratosObjetivo() + meta1.getLipidosObjetivo() + meta1.getProteinasObjetivo();
        assertEquals(1.0, suma1, DELTA, "La suma de la Opción 1 debe ser 1.0");

        // Opción 2
        MetaNutricional meta2 = MetaFactory.obtenerMeta(2);
        double suma2 = meta2.getCarbohidratosObjetivo() + meta2.getLipidosObjetivo() + meta2.getProteinasObjetivo();
        assertEquals(1.0, suma2, DELTA, "La suma de la Opción 2 debe ser 1.0");
        
        // Opción 3
        MetaNutricional meta3 = MetaFactory.obtenerMeta(3);
        double suma3 = meta3.getCarbohidratosObjetivo() + meta3.getLipidosObjetivo() + meta3.getProteinasObjetivo();
        assertEquals(1.0, suma3, DELTA, "La suma de la Opción 3 debe ser 1.0");

        // Opción 4
        MetaNutricional meta4 = MetaFactory.obtenerMeta(4);
        double suma4 = meta4.getCarbohidratosObjetivo() + meta4.getLipidosObjetivo() + meta4.getProteinasObjetivo();
        assertEquals(1.0, suma4, DELTA, "La suma de la Opción 4 debe ser 1.0");
    }


    
    // Pruebas de excepciones
    
    
    @Test
    @DisplayName("Opciones inválidas: Debe lanzar IllegalArgumentException")
    public void obtenerMeta_OpcionInvalida_LanzaExcepcion() {
        // Prueba con un número negativo
        assertThrows(IllegalArgumentException.class, 
                     () -> MetaFactory.obtenerMeta(-1),
                     "Una opción negativa debe lanzar IllegalArgumentException");

        // Prueba con un número fuera de rango
        assertThrows(IllegalArgumentException.class, 
                     () -> MetaFactory.obtenerMeta(5),
                     "Una opción fuera del rango (1-4) debe lanzar IllegalArgumentException");
        
        // Prueba con la opción 0
         assertThrows(IllegalArgumentException.class, 
                     () -> MetaFactory.obtenerMeta(0),
                     "La opción 0 debe lanzar IllegalArgumentException");
    }
}
package Servicios;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import Modelo.POJOs.Macronutrientes;
import Modelo.POJOs.MetaNutricional;
import Modelo.Servicios.MacronutrientesServicio;

public class MacronutrientesServicioTest {

    private MacronutrientesServicio servicio;

    @BeforeEach
    public void setUp() {
        servicio = new MacronutrientesServicio();
    }

    @Test
    public void dividirCalorias_2000KcalDistribucionEquilibrada_CalculaGramosCorrectos() {
        // Caso: 2000 kcal
        // Meta: 50% Carbs, 20% Proteína, 30% Grasa
    	
        MetaNutricional meta = new MetaNutricional();
        meta.setCarbohidratosObjetivo(0.50);
        meta.setProteinasObjetivo(0.20);
        meta.setLipidosObjetivo(0.30);

        double caloriasTotales = 2000.0;

        Macronutrientes resultado = servicio.dividirCalorias(caloriasTotales, meta);

        // Esperado Carbs: (2000 * 0.50) / 4 = 250g
        assertEquals(250.0, resultado.getCarbohidratos(), 0.01);

        // Esperado Proteína: (2000 * 0.20) / 4 = 100g
        assertEquals(100.0, resultado.getProteinas(), 0.01);

        // Esperado Lípidos: (2000 * 0.30) / 9 = 66.66g
        assertEquals(66.66, resultado.getLipidos(), 0.01);
    }

    @Test
    public void dividirCalorias_1500KcalDistribucionBajaEnCarbos_CalculaGramosCorrectos() {
        // Caso: 1500 kcal
        // Meta: 30% Carbs, 35% Proteína, 35% Grasa
        MetaNutricional meta = new MetaNutricional();
        meta.setCarbohidratosObjetivo(0.30);
        meta.setProteinasObjetivo(0.35);
        meta.setLipidosObjetivo(0.35);

        double caloriasTotales = 1500.0;

        Macronutrientes resultado = servicio.dividirCalorias(caloriasTotales, meta);

        // Esperado Carbs: (1500 * 0.30) / 4 = 112.5g
        assertEquals(112.5, resultado.getCarbohidratos(), 0.01);

        // Esperado Proteína: (1500 * 0.35) / 4 = 131.25g
        assertEquals(131.25, resultado.getProteinas(), 0.01);

        // Esperado Lípidos: (1500 * 0.35) / 9 = 58.33g
        assertEquals(58.33, resultado.getLipidos(), 0.01);
    }
}
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import POJOs.MetaNutricional;
import POJOs.Macronutrientes;
import Servicios.MacronutrientesServicio;

public class MacronutrientesServicioTest {

    @Test
    void testDividirCalorias_MasaMuscular() {
        MacronutrientesServicio service = new MacronutrientesServicio();

        double calorias = 2500;
        // Meta: 50% CHO, 23% LIP, 27% PROT
        MetaNutricional meta = new MetaNutricional(0.50, 0.23, 0.27);

        Macronutrientes result = service.dividirCalorias(calorias, meta);

        // valores esperados
        double expectedCho = (2500 * 0.50) / 4;  // 312.5 g
        double expectedLip = (2500 * 0.23) / 9;  // 63.8 g aprox
        double expectedProt = (2500 * 0.27) / 4; // 168.75 g

        assertEquals(expectedCho, result.getCarbohidratos(), 0.01);
        assertEquals(expectedLip, result.getLipidos(), 0.01);
        assertEquals(expectedProt, result.getProteinas(), 0.01);
    }

    @Test
    void testDividirCalorias_CeroCalorias() {
        MacronutrientesServicio service = new MacronutrientesServicio();

        MetaNutricional meta = new MetaNutricional(0.40, 0.30, 0.30);

        Macronutrientes result = service.dividirCalorias(0, meta);

        assertEquals(0, result.getCarbohidratos());
        assertEquals(0, result.getLipidos());
        assertEquals(0, result.getProteinas());
    }

    @Test
    void testDividirCalorias_ValoresDecimales() {
        MacronutrientesServicio service = new MacronutrientesServicio();

        double calorias = 1800;
        MetaNutricional meta = new MetaNutricional(0.455, 0.225, 0.32);

        Macronutrientes result = service.dividirCalorias(calorias, meta);

        double expectedCho = (1800 * 0.455) / 4;
        double expectedLip = (1800 * 0.225) / 9;
        double expectedProt = (1800 * 0.32) / 4;

        assertEquals(expectedCho, result.getCarbohidratos(), 0.001);
        assertEquals(expectedLip, result.getLipidos(), 0.001);
        assertEquals(expectedProt, result.getProteinas(), 0.001);
    }

    @Test
    void testDividirCalorias_CaloriasAltas() {
        MacronutrientesServicio service = new MacronutrientesServicio();

        double calorias = 6000;
        MetaNutricional meta = new MetaNutricional(0.50, 0.20, 0.30);

        Macronutrientes result = service.dividirCalorias(calorias, meta);

        assertEquals((6000 * 0.50) / 4, result.getCarbohidratos(), 0.01);
        assertEquals((6000 * 0.20) / 9, result.getLipidos(), 0.01);
        assertEquals((6000 * 0.30) / 4, result.getProteinas(), 0.01);
    }
}

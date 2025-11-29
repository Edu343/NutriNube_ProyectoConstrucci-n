package Servicios;

import POJOs.Macronutrientes;
import POJOs.MetaNutricional;

public class MacronutrientesServicio {

    public Macronutrientes dividirCalorias(double caloriasTotales, MetaNutricional meta) {

        double caloriasCho = caloriasTotales * meta.getCarbohidratos();
        double caloriasLip = caloriasTotales * meta.getLipidos();
        double caloriasProt = caloriasTotales * meta.getProteinas();

        //Convertir calorías a gramos
        double gramosCho = caloriasCho / 4.0;   // carbohidratos → 4 kcal/g
        double gramosProt = caloriasProt / 4.0; // proteína → 4 kcal/g
        double gramosLip = caloriasLip / 9.0;   // grasas → 9 kcal/g


        Macronutrientes macros = new Macronutrientes();
        macros.setCarbohidratosObjetivo(gramosCho);
        macros.setProteinasObjetivo(gramosProt);
        macros.setLipidosObjetivo(gramosLip);

        return macros;
    }
}


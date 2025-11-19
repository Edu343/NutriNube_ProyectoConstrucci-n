package Servicios;

import POJOs.Macronutrientes;
import POJOs.MetaNutricional;

public class MacronutrientesServicio {

    public Macronutrientes dividirCalorias(double caloriasTotales, MetaNutricional meta) {

        double caloriasCho = caloriasTotales * meta.getCho();
        double caloriasLip = caloriasTotales * meta.getLip();
        double caloriasProt = caloriasTotales * meta.getProt();

        //Convertir calorías a gramos
        double gramosCho = caloriasCho / 4.0;   // carbohidratos → 4 kcal/g
        double gramosProt = caloriasProt / 4.0; // proteína → 4 kcal/g
        double gramosLip = caloriasLip / 9.0;   // grasas → 9 kcal/g


        Macronutrientes macros = new Macronutrientes();
        macros.setCarbohidratos(gramosCho);
        macros.setProteinas(gramosProt);
        macros.setLipidos(gramosLip);

        return macros;
    }
}

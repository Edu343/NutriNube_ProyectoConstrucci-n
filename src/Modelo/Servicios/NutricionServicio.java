package Modelo.Servicios;

import Modelo.POJOs.CaloriasCalculo;

/**
 * Servicio responsable de calcular las calorías totales
 * y la Tasa Metabólica en Reposo (TMR) basándose en datos
 * como peso, edad, altura, sexo y nivel de actividad física.
 */

public class NutricionServicio {

    public static final int SEXO_HOMBRE = 1;
    public static final int SEXO_MUJER = 0;

    public double calcularCalorias(CaloriasCalculo datos, int sexo, double altura, String fechaNacimiento) {

        int edad = calcularEdad(fechaNacimiento);

        double tmr = calcularTmr(datos.getPeso(), edad, altura, sexo);

        double factorActividad = obtenerFactorActividad(datos.getNivelActividadFisica());
        double totalCalorias = tmr * factorActividad;

        return totalCalorias;
    }

    public double calcularTmr(double peso, int edad, double altura, int sexo) {
        double tmrBase = 0.0;

        double componentesComunes = (10 * peso) + (6.25 * altura) - (5 * edad);

        if (sexo == SEXO_HOMBRE) {
            tmrBase = componentesComunes + 5;
        } else if (sexo == SEXO_MUJER) {
            tmrBase = componentesComunes - 161;
        }

        return tmrBase;
    }

    public int calcularEdad(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty())
            return 0;

        try {
            java.time.LocalDate fechaNac = java.time.LocalDate.parse(fechaNacimiento);
            java.time.LocalDate hoy = java.time.LocalDate.now();

            int edad = java.time.Period.between(fechaNac, hoy).getYears();
            return edad;

        } catch (java.time.format.DateTimeParseException e) {

            try {
                int anioNacimiento = Integer.parseInt(fechaNacimiento);
                int anioActual = java.time.LocalDate.now().getYear();
                return anioActual - anioNacimiento;
            } catch (NumberFormatException ex) {
                return 0;
            }
        }
    }

    private double obtenerFactorActividad(int nivel) {
        switch (nivel) {
            case 1: // Ligero
                return 1.1;
            case 2: // Moderado
                return 1.2;
            case 3: // Pesado
                return 1.3;
            default:
                return 1.0;
        }
    }
}
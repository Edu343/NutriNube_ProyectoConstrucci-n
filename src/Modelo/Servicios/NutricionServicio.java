package Modelo.Servicios;

import Modelo.POJOs.CaloriasCalculo;



public class NutricionServicio {
	
	public static final int SEXO_HOMBRE = 1;
    public static final int SEXO_MUJER = 0;

    // MODIFICADO: Acepta sexo, altura, y fechaNacimiento como parámetros, no como objeto Paciente
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
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) return 0;
        
        try {
            // Asume formato YYYY-MM-DD del JDateChooser
            java.time.LocalDate fechaNac = java.time.LocalDate.parse(fechaNacimiento);
            java.time.LocalDate hoy = java.time.LocalDate.now();
            
            // Calcula la diferencia de años
            int edad = java.time.Period.between(fechaNac, hoy).getYears();
            return edad;
            
        } catch (java.time.format.DateTimeParseException e) {
            // Fallback si todavía se recibe solo el año (YYYY)
            // Ya que se está usando el JDateChooser, este fallback debería ser raro
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
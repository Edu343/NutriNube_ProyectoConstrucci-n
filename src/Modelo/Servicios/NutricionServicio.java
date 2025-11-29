package Modelo.Servicios;

import Modelo.POJOs.CaloriasCalculo;

import Modelo.POJOs.Paciente;

	

public class NutricionServicio {
	
	public static final int SEXO_HOMBRE = 1;
    public static final int SEXO_MUJER = 0;

    public double calcularCalorias(CaloriasCalculo datos, Paciente paciente) {
        double tmr = calcularTmr(datos.getPeso(), calcularEdad(paciente.getFechaNacimiento()), 
                                 paciente.getAltura(), paciente.getSexo());
        
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
    
    private int calcularEdad(String fechaNacimiento) {
        int anioNacimiento = Integer.parseInt(fechaNacimiento);
        int anioActual = java.time.LocalDate.now().getYear();
        return anioActual - anioNacimiento;
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
                // Se podría manejar con un valor por defecto o lanzar una excepción
                return 1.0; 
        }
    }
}
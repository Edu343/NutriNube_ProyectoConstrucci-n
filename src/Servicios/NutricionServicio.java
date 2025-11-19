package Servicios;

import POJOs.CaloriasCalculo;
import POJOs.Paciente;

public class NutricionServicio {

    public double calcularCalorias(CaloriasCalculo datos, Paciente paciente) {
        double tmr = calcularTmr(datos.getPeso(), calcularEdad(paciente.getFechaNacimiento()), 
                                 paciente.getAltura(), paciente.getSexo());
        
        
        double totalCalorias = tmr * datos.getNivelActividadFisica();
        
        return totalCalorias;
    }

    public double calcularTmr(double peso, int edad, double altura, boolean sexo) {
        if (sexo) {
            return  (10 * peso) + (6.25 * altura) - (5 * edad) + 5;
        } else {
            return  (10 * peso) + (6.25 * altura) - (5 * edad) - 161;
        }
    }
    
    
    
    private int calcularEdad(String fechaNacimiento) {
    	
            int anioNacimiento = Integer.parseInt(fechaNacimiento);
            int anioActual = java.time.LocalDate.now().getYear();
            return anioActual - anioNacimiento;
        
    }
}


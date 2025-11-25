package Controladores;

import Core.Controller;
import Core.MainViewLayout;
import POJOs.*;
import Vistas.ConsultaFormularioView;

/**
 * Controlador para manejar la lógica de negocio asociada al formulario de una consulta nutricional.
 * Gestiona el cálculo de calorías, el guardado de datos y la navegación de retorno.
 */

public class ConsultaController extends Controller {

    public static final String MODO_AGREGAR = "AGREGAR";
    public static final String MODO_EDITAR = "EDITAR";

    public ConsultaController(String tag) {
        super(tag);
    }

    @Override
    public void handleCalcularCalorias(CaloriasCalculo calculoData, Paciente clavePaciente) {
        // Lógica de cálculo (Se asume que la implementación real está en el modelo)
    }

    @Override
    public void handleGuardarConsulta(Consulta consultaAGuardar) {
        // Lógica para guardar la consulta en el modelo.
        // Después de guardar, regresa a la vista anterior (historial o lista).
        
        String vistaAnterior = ((ConsultaFormularioView)myView).getViewAnteriorTag();
        String clavePaciente = "CLAVE-PACIENTE-MOCK";
        
        if (vistaAnterior != null) {
            Object data = MainViewLayout.HISTORIAL_VIEW.equals(vistaAnterior) ? clavePaciente : null;
            cambiarVista(vistaAnterior, data); 
        } else {
            cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
        }
    }

    @Override
    public void handleSalir() {
        // Regresa a la vista anterior definida por el tag de retorno.
        String vistaAnterior = ((ConsultaFormularioView)myView).getViewAnteriorTag();
        String clavePaciente = ((ConsultaFormularioView)myView).getClavePacienteActual(); 
        
        if (vistaAnterior != null) {
            Object data = MainViewLayout.HISTORIAL_VIEW.equals(vistaAnterior) ? clavePaciente : null;
            cambiarVista(vistaAnterior, data);
        } else {
            cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
        }
    }

    @Override
    public void update() {
    }
}
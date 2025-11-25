package Controladores;

import Core.Controller;
import Core.MainViewLayout;
import POJOs.*;
import Vistas.ConsultaFormularioView;

public class ConsultaController extends Controller {

    public static final String MODO_AGREGAR = "AGREGAR";
    public static final String MODO_EDITAR = "EDITAR";

    public ConsultaController(String tag) {
        super(tag);
    }

    public void handleCalcularCalorias(CaloriasCalculo calculoData, String clavePaciente) {
        
    }

    public void handleGuardarConsulta(Consulta consultaAGuardar) {
        // 🚨 Vuelve a la vista anterior usando el tag guardado en la vista
        String vistaAnterior = ((ConsultaFormularioView)myView).getViewAnteriorTag();
        String clavePaciente = "CLAVE-PACIENTE-MOCK"; // Usamos una clave de prueba
        
        if (vistaAnterior != null) {
            
            Object data = MainViewLayout.HISTORIAL_VIEW.equals(vistaAnterior) ? clavePaciente : null;
            cambiarVista(vistaAnterior, data); 
        } else {
            cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
        }
    }

    @Override
    public void handleSalir() {
        // 🚨 Vuelve a la vista anterior usando el tag guardado en la vista
        String vistaAnterior = ((ConsultaFormularioView)myView).getViewAnteriorTag();
         

        if (vistaAnterior != null) {

            cambiarVista(vistaAnterior, null);
        } else {
            cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
        }
    }

    @Override
    public void update() {
    }
}
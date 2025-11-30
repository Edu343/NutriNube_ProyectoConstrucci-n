package Controlador;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;

public class HistorialController extends Controller {

    public HistorialController(String tag) {
        super(tag);
    }

    /**
     * Agregar una nueva consulta para el paciente actual.
     */
    public void handleAgregarConsulta(String clavePaciente) {

        if (clavePaciente == null) return;

        myModel.seleccionarPaciente(clavePaciente);

        // Datos que se enviarán al formulario de consulta
        var data = new java.util.HashMap<String, Object>();
        data.put("modo", "AGREGAR");
        data.put("clavePaciente", clavePaciente);
        data.put("returnViewTag", MainViewLayout.HISTORIAL_VIEW);

        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }

    /**
     * Editar una consulta existente.
     */
    public void handleEditarConsulta(String claveConsulta) {

        if (claveConsulta == null) return;

        myModel.seleccionarConsulta(claveConsulta);

        var data = new java.util.HashMap<String, Object>();
        data.put("modo", "EDITAR");
        data.put("claveConsulta", claveConsulta);
        data.put("returnViewTag", MainViewLayout.HISTORIAL_VIEW);

        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }

    /**
     * Eliminar una consulta del historial.
     */
    public void handleEliminarConsulta(String claveConsulta) {

        if (claveConsulta == null) return;

        try {
            myModel.eliminarConsulta(claveConsulta);

            // Recargar historial tras borrar
            cambiarVista(MainViewLayout.HISTORIAL_VIEW, null);

        } catch (Exception e) {
            System.err.println("Error eliminando consulta: " + e.getMessage());
        }
    }

    /**
     * Regresar a la vista de pacientes.
     */
    public void handleSalir() {
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }

    /**
     * Logout → regresar al login.
     */
    @Override
    public void handleLogout() {
        myModel.logout();
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }

    /**
     * El modelo notificó un cambio → refrescar historial.
     */
    @Override
    public void update() {
        cambiarVista(MainViewLayout.HISTORIAL_VIEW, null);
    }
}

package Controlador;

import javax.swing.JOptionPane;
import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.Consulta;
import Modelo.POJOs.Paciente;

/**
 * Controlador responsable del formulario de consulta.
 * Coordina la creación, edición y guardado de consultas.
 */
public class ConsultaController extends Controller {

    public static final String MODO_AGREGAR = "AGREGAR";
    public static final String MODO_EDITAR = "EDITAR";

    public ConsultaController(String tag) {
        super(tag);
    }

    @Override
    public void handleCalcularCalorias(String clavePaciente, double peso, int nivelActividadFisica, int razonConsulta,
            int sexo, double altura, String fechaNacimiento) {
        try {
            myModel.calcularCaloriasYMacronutrientes(
                    clavePaciente, peso, nivelActividadFisica, razonConsulta, sexo, altura, fechaNacimiento);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo realizar el cálculo nutricional.\n" +
                    "Verifica que todos los campos estén completos y correctos.",
                    "Error de cálculo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleGuardarConsulta(Paciente paciente, Consulta consulta, String modo) {

        try {
            if (modo.equals(MODO_AGREGAR)) {
                
                // Si el paciente no existía previamente en la BD (es un registro nuevo completo)
                // Verificamos si es nuevo revisando si NO hay un paciente seleccionado en el modelo
                boolean esNuevoPaciente = (paciente != null && myModel.getPacienteSeleccionado() == null);
                
                if (esNuevoPaciente) {
                    // Guardar Paciente Nuevo + Su Primera Consulta
                    myModel.guardarNuevoPacienteYConsulta(paciente, consulta);
                } else {
                    // Guardar Solo Consulta (para paciente existente)
                    myModel.guardarNuevaConsulta(consulta);
                }

            } else if (modo.equals(MODO_EDITAR)) {
                // Actualizar ambos
                myModel.actualizarConsulta(paciente, consulta);
            }

            // Lógica de retorno a la vista anterior
            String returnViewTag = myView.getViewAnteriorTag();

            if (returnViewTag.equals("PACIENTES")) {
                cambiarVista(returnViewTag, null);
            } else {
                // Si venimos del historial, necesitamos la clave para volver a cargar la lista
                String claveRetorno = (paciente != null) ? paciente.getClavePaciente() : consulta.getClavePaciente();
                
                if (claveRetorno == null && myModel.getPacienteSeleccionado() != null) {
                    claveRetorno = myModel.getPacienteSeleccionado().getClavePaciente();
                }
                cambiarVista(returnViewTag, claveRetorno);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo guardar la consulta.\n" +
                    "Verifica que todos los campos obligatorios estén completos.\n\nDetalle: " + e.getMessage(),
                    "Error al guardar",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void handleSalir() {
        String returnViewTag = myView.getViewAnteriorTag();
        String clavePaciente = myView.getClavePacienteActual();
        cambiarVista(returnViewTag, clavePaciente);
    }

    public void handleLogout() {
        myModel.logout();
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }
}

package Controlador;

import Vista.ConsultaFormularioView;
import javax.swing.JOptionPane;
import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;

/**
 * Controlador responsable del formulario de consulta.
 * Coordina la creación, edición y guardado de consultas,
 * así como navegación y cálculo de calorías según los datos ingresados.
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
            e.printStackTrace();
        }
    }

    @Override
    public void handleGuardarConsulta(String clavePaciente, String claveConsulta, String nombre, String apellido,
            String correo, int sexo, String telefono, String fechaNacimiento, double altura,
            String condicionesMedicas, String medicacion, String historialCirugias, String alergias,
            String preferenciaComida, String horarioSueno, int nivelEstres, String habitosAlimenticios,
            String tipoLiquidosConsumidos, double cantidadLiquidoConsumido, String barreraAlimenticia,
            double peso, int nivelActividadFisica, int razonConsulta, double calorias, double carbohidratos,
            double proteinas, double lipidos, String modo) {

        try {
            String claveNutriologo = myModel.getNutriologoActual().getClaveNutriologo();

            if (modo.equals(MODO_AGREGAR)) {
                
                boolean claveValida = clavePaciente == null || clavePaciente.isEmpty();
                boolean nombreValido = nombre != null && !nombre.isEmpty();

                boolean esNuevoPaciente = claveValida && nombreValido;

                if (esNuevoPaciente) {
                    myModel.guardarNuevoPacienteYConsulta(claveNutriologo, nombre, apellido, correo, sexo, telefono,
                            fechaNacimiento, altura,
                            condicionesMedicas, medicacion, historialCirugias, alergias, preferenciaComida,
                            horarioSueno, nivelEstres,
                            habitosAlimenticios, tipoLiquidosConsumidos, cantidadLiquidoConsumido, barreraAlimenticia,
                            peso, nivelActividadFisica, razonConsulta, calorias, carbohidratos, proteinas, lipidos);
                } else {
                    myModel.guardarNuevaConsulta(clavePaciente, claveNutriologo, condicionesMedicas, medicacion,
                            historialCirugias, alergias,
                            preferenciaComida, horarioSueno, nivelEstres, habitosAlimenticios, tipoLiquidosConsumidos,
                            cantidadLiquidoConsumido,
                            barreraAlimenticia, peso, nivelActividadFisica, razonConsulta, calorias, carbohidratos,
                            proteinas, lipidos);

                }
            } else if (modo.equals(MODO_EDITAR)) {

                myModel.actualizarConsulta(claveConsulta, clavePaciente, claveNutriologo,
                        nombre, apellido, correo, sexo, telefono, fechaNacimiento, altura,
                        condicionesMedicas, medicacion, historialCirugias, alergias,
                        preferenciaComida, horarioSueno, nivelEstres, habitosAlimenticios, tipoLiquidosConsumidos,
                        cantidadLiquidoConsumido,
                        barreraAlimenticia, peso, nivelActividadFisica, razonConsulta, calorias, carbohidratos,
                        proteinas, lipidos);
            }

            String returnViewTag = ((ConsultaFormularioView) myView).getViewAnteriorTag();

            if (returnViewTag.equals("PACIENTES")) {
                cambiarVista(returnViewTag, null);
            } else {
                String claveRetorno = clavePaciente;
                if (claveRetorno == null && myModel.getPacienteSeleccionado() != null) {
                    claveRetorno = myModel.getPacienteSeleccionado().getClavePaciente();
                }
                cambiarVista(returnViewTag, claveRetorno);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo guardar la consulta.\n" +
                    "Verifica que todos los campos obligatorios estén completos.",
                    "Error al guardar",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    public void handleSalir() {
        String returnViewTag = ((ConsultaFormularioView) myView).getViewAnteriorTag();
        String clavePaciente = ((ConsultaFormularioView) myView).getClavePacienteActual();
        cambiarVista(returnViewTag, clavePaciente);
    }

    public void handleLogout() {
        myModel.logout();
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }

    
}
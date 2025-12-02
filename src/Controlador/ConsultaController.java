package Controlador;

import Vista.ConsultaFormularioView;
import javax.swing.JOptionPane;
import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;

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
            JOptionPane.showMessageDialog(null, "Error al calcular: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
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

                boolean isNewPatient = (clavePaciente == null || clavePaciente.isEmpty())
                        && (nombre != null && !nombre.isEmpty());

                if (isNewPatient) {
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
            JOptionPane.showMessageDialog(null, "Error al guardar la consulta: " + e.getMessage(), "Error",
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

    @Override
    public void update() {
    }
}
package Vista;

import Modelo.Core.View;
import Modelo.POJOs.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controlador.ConsultaController;

import javax.swing.JComponent;

/**
 * Vista responsable de mostrar y gestionar el formulario de consultas.
 * Construye la interfaz, coordina interacción del usuario,
 * enlaza acciones con el controlador y sincroniza los datos del modelo.
 */
public class ConsultaFormularioView extends View {

    private ConsultaFormularioViewLayout formularioLayout;
    private Consulta consultaActual;
    private Paciente pacienteActual;

    public ConsultaFormularioView(String tag) {
        super(tag);
    }

    @Override
    protected void crearController() {
        myController = new ConsultaController(tag);
    }

    /**
     * Crea el controlador asociado a esta vista.
     * Cada vista inicializa su propio controlador según el patrón MVC.
     */
    @Override
    protected void crearViewLayout() {
        formularioLayout = new ConsultaFormularioViewLayout();
        this.mainPanel = formularioLayout.getPanel();

        // Asignación de listeners: cada botón delega su acción al controlador (MVC)
        formularioLayout.getBtnPacientes().addActionListener(e -> {
            myModel.setPacienteSeleccionado(null);
            myModel.setConsultaSeleccionada(null);
            myController.cambiarVista("PACIENTES", null);
        });

        formularioLayout.getBtnExpedientes().addActionListener(e -> {
            if (pacienteActual != null) {
                ((ConsultaController) myController).cambiarVista("HISTORIAL", pacienteActual.getClavePaciente());
            } else {
                ((ConsultaController) myController).cambiarVista("PACIENTES", null);
            }
        });

        formularioLayout.getBtnLogout().addActionListener(e -> {
            myController.handleLogout();
        });

        formularioLayout.getBtnSalir().addActionListener(e -> myController.handleSalir());

        formularioLayout.getBtnCalcular().addActionListener(e -> {
            try {
                double peso = Double.parseDouble(formularioLayout.getPesoField().getText());
                int nivelActividad = formularioLayout.getCmbNivelActividad().getSelectedIndex() + 1;
                int razonConsulta = formularioLayout.getCmbRazonConsulta().getSelectedIndex() + 1;

                String clavePaciente = (pacienteActual != null) ? pacienteActual.getClavePaciente() : null;
                int sexo = formularioLayout.getCmbSexo().getSelectedIndex();

                double altura = 0.0;
                if (pacienteActual != null) {
                    altura = pacienteActual.getAltura();
                } else {
                    try {
                        altura = Double.parseDouble(formularioLayout.getAlturaField().getText());
                    } catch (NumberFormatException ex) {
                    }
                }

                String fechaNacimiento = obtenerFechaNacimientoStr();

                myController.handleCalcularCalorias(
                        clavePaciente, peso, nivelActividad, razonConsulta, sexo, altura, fechaNacimiento);

            } catch (NumberFormatException ex) {
                mostrarError("Verifique que Peso y Altura sean números válidos.");
            } catch (Exception ex) {
                mostrarError("Error al calcular: " + ex.getMessage());
            }
        });

        formularioLayout.getBtnGuardar().addActionListener(e -> {
            try {
                String clavePaciente = (pacienteActual != null) ? pacienteActual.getClavePaciente() : null;
                String claveConsulta = (consultaActual != null) ? consultaActual.getClaveConsulta() : null;

                String nombre = formularioLayout.getTxtNombreField().getText();
                String apellido = formularioLayout.getTxtApellidoField().getText();
                String correo = formularioLayout.getTxtCorreoField().getText();
                int sexo = formularioLayout.getCmbSexo().getSelectedIndex();
                String telefonoStr = formularioLayout.getTxtTelefonoField().getText();
                String fechaNacimiento = obtenerFechaNacimientoStr();

                double altura = 0.0;
                if (clavePaciente == null) {
                    altura = Double.parseDouble(formularioLayout.getAlturaField().getText());
                } else {
                    altura = pacienteActual.getAltura();
                }

                String condicionesMedicas = formularioLayout.getTxtCondicionesMedicasField().getText();
                String medicacion = formularioLayout.getTxtMedicacionField().getText();
                String historialCirugias = formularioLayout.getTxtHistorialCirugias().getText();
                String alergias = formularioLayout.getTxtAlergiasField().getText();
                String preferenciaComida = formularioLayout.getPreferenciaComidaField().getText();
                String horarioSueno = formularioLayout.getTxtHorarioSueno().getText();
                int nivelEstres = formularioLayout.getCmbNivelEstres().getSelectedIndex();
                String habitosAlimenticios = formularioLayout.getTxtHabitoAlimenticio().getText();
                String tipoLiquidosConsumidos = formularioLayout.getTxtTipoLiquidoConsumido().getText();
                double cantidadLiquidoConsumido = 0.0;
                try {
                    cantidadLiquidoConsumido = Double.parseDouble(formularioLayout.getTxtCantidadLiquido().getText());
                } catch (NumberFormatException e1) {
                }

                String barreraAlimenticia = formularioLayout.getTxtBarreraAlimenticia().getText();

                double peso = Double.parseDouble(formularioLayout.getPesoField().getText());
                int nivelActividadFisica = formularioLayout.getCmbNivelActividad().getSelectedIndex() + 1;
                int razonConsulta = formularioLayout.getCmbRazonConsulta().getSelectedIndex() + 1;

                double calorias = parseDoubleSafe(formularioLayout.getTxtCaloriasTotales().getText());
                double carbohidratos = parseDoubleSafe(formularioLayout.getTxtCarbohidratos().getText());
                double proteinas = parseDoubleSafe(formularioLayout.getTxtProteinas().getText());
                double lipidos = parseDoubleSafe(formularioLayout.getTxtLipidos().getText());

                String modo = formularioLayout.getModoActual();

                myController.handleGuardarConsulta(
                        clavePaciente, claveConsulta, nombre, apellido, correo, sexo, telefonoStr, fechaNacimiento, altura,
                        condicionesMedicas, medicacion, historialCirugias, alergias, preferenciaComida, horarioSueno,
                        nivelEstres,
                        habitosAlimenticios, tipoLiquidosConsumidos, cantidadLiquidoConsumido, barreraAlimenticia,
                        peso, nivelActividadFisica, razonConsulta, calorias, carbohidratos, proteinas, lipidos, modo);
            } catch (Exception ex) {
                mostrarError("Error al intentar guardar: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    /**
     * Carga datos enviados desde otra vista o controlador.
     * Recibe parámetros como modo (AGREGAR/EDITAR), claves del paciente o consulta
     * y prepara la vista para mostrar los datos correspondientes.
     *
     * @param data Mapa de valores enviados por otra vista.
     */
    @Override
    public void cargarDatos(Object data) {
        this.pacienteActual = null;
        this.consultaActual = null;

        if (data instanceof Map) {
            Map<String, Object> parametrosVista = (Map<String, Object>) data;

            String modo = (String) parametrosVista.get("modo");
            String returnViewTag = (String) parametrosVista.get("returnViewTag");
            formularioLayout.setModoActual(modo);
            formularioLayout.setViewAnteriorTag(returnViewTag);

            if (parametrosVista.containsKey("clavePaciente")) {
                String clavePaciente = (String) parametrosVista.get("clavePaciente");
                try {
                    this.pacienteActual = myModel.getPacienteDAO().leerPorClave(clavePaciente);
                    myModel.setPacienteSeleccionado(this.pacienteActual);
                } catch (Exception e) {
                    mostrarError("Error al cargar datos del paciente.");
                }
            } else {
                myModel.setPacienteSeleccionado(null);
            }

            if (modo.equals(ConsultaController.MODO_EDITAR) && parametrosVista.containsKey("claveConsulta")) {
                String claveConsulta = (String) parametrosVista.get("claveConsulta");
                try {
                    this.consultaActual = myModel.obtenerConsultaSeleccionada(claveConsulta);
                    if (this.consultaActual != null) {
                        this.pacienteActual = myModel.getPacienteDAO()
                                .leerPorClave(this.consultaActual.getClavePaciente());
                    }
                } catch (Exception e) {
                    mostrarError("Error al cargar datos de la consulta.");
                }
            } else {
                myModel.setConsultaSeleccionada(null);
            }
        }

        if (this.pacienteActual == null && myModel.getPacienteSeleccionado() != null) {
            this.pacienteActual = myModel.getPacienteSeleccionado();
        }

        inicializarFormulario();
    }

    /**
     * Configura el formulario según el modo actual (AGREGAR o EDITAR),
     * prellenando información del paciente o consulta cuando corresponde.
     */
    private void inicializarFormulario() {
        String modo = formularioLayout.getModoActual();
        if (modo == null)
            return;

        boolean isAddingNewPatient = (pacienteActual == null && modo.equals(ConsultaController.MODO_AGREGAR));

        if (isAddingNewPatient) {
            limpiarCamposPacienteYConsulta();
        } else {
            if (pacienteActual != null) {
                // Pre-llenar datos fijos del paciente
                formularioLayout.getTxtNombreField().setText(pacienteActual.getNombre());
                formularioLayout.getTxtApellidoField().setText(pacienteActual.getApellido());
                formularioLayout.getTxtCorreoField().setText(pacienteActual.getCorreo());
                formularioLayout.getTxtTelefonoField().setText(String.valueOf(pacienteActual.getTelefono()));
                formularioLayout.getCmbSexo().setSelectedIndex(pacienteActual.getSexo());
                formularioLayout.getAlturaField().setText(String.valueOf(pacienteActual.getAltura()));

                try {
                    setFechaNacimientoEnChooser(pacienteActual.getFechaNacimiento());
                } catch (Exception e) {
                }

                if (modo.equals(ConsultaController.MODO_EDITAR) && consultaActual != null) {
                    cargarDatosConsultaEnFormulario(consultaActual);
                } else {
                    cargarDatosUltimaConsulta();
                }
            }
        }
    }

    /**
     * Carga la información de la última consulta registrada del paciente.
     * Útil cuando se crea una nueva consulta, ya que reutiliza datos previos.
     */
    private void cargarDatosUltimaConsulta() {
        limpiarCamposConsulta();

        try {
            Consulta ultimaConsulta = myModel.getConsultaDAO()
                    .obtenerUltimaConsultaObjeto(pacienteActual.getClavePaciente());

            if (ultimaConsulta != null) {
                cargarDatosConsultaEnFormulario(ultimaConsulta);

                formularioLayout.getPesoField().setText("");
                formularioLayout.getTxtCantidadLiquido().setText("");
                formularioLayout.getTxtTipoLiquidoConsumido().setText(""); // Opcional, a veces cambia

                formularioLayout.getTxtCaloriasTotales().setText("");
                formularioLayout.getTxtProteinas().setText("");
                formularioLayout.getTxtCarbohidratos().setText("");
                formularioLayout.getTxtLipidos().setText("");
            }
        } catch (Exception e) {
            System.err.println("No hay historial previo o error cargándolo: " + e.getMessage());
        }
    }

    /**
     * Muestra en pantalla los resultados del cálculo nutricional
     * cuando el modelo notifica cambios (patrón Observer).
     */
    @Override
    public void display() {
        Consulta consultaModelo = myModel.getConsultaSeleccionada();
        if (consultaModelo != null) {
            CaloriasCalculo caloriasCalculo = consultaModelo.getCaloriasCalculo();
            Macronutrientes macros = consultaModelo.getMacronutrientes();
            if (caloriasCalculo != null && macros != null) {
                mostrarResultadoCalculo(caloriasCalculo, macros);
            }
        }
    }

    /**
     * Llena el formulario con los datos de una consulta existente.
     *
     * @param consulta Consulta que será mostrada en los campos del formulario.
     */
    private void cargarDatosConsultaEnFormulario(Consulta consulta) {
        if (consulta == null)
            return;

        AnamnesisData anamnesisData = consulta.getAnamnesisData();
        CaloriasCalculo caloriasCalculoData = consulta.getCaloriasCalculo();
        Macronutrientes macronutrientesData = consulta.getMacronutrientes();

        if (anamnesisData != null) {
            formularioLayout.getTxtCondicionesMedicasField().setText(anamnesisData.getCondicionesMedicas());
            formularioLayout.getTxtMedicacionField().setText(anamnesisData.getMedicacion());
            formularioLayout.getTxtHistorialCirugias().setText(anamnesisData.getHistorialCirugias());
            formularioLayout.getTxtAlergiasField().setText(anamnesisData.getAlergias());
            formularioLayout.getPreferenciaComidaField().setText(anamnesisData.getPreferenciaComida());
            formularioLayout.getTxtHorarioSueno().setText(anamnesisData.getHorarioSueno());
            formularioLayout.getCmbNivelEstres().setSelectedIndex(anamnesisData.getNivelEstres());
            formularioLayout.getTxtHabitoAlimenticio().setText(anamnesisData.getHabitoAlimenticio());
            formularioLayout.getTxtTipoLiquidoConsumido().setText(anamnesisData.getTipoLiquidoConsumido());
            formularioLayout.getTxtCantidadLiquido().setText(String.valueOf(anamnesisData.getCantidadLiquidoConsumido()));
            formularioLayout.getTxtBarreraAlimenticia().setText(anamnesisData.getBarreraAlimenticia());
        }

        if (caloriasCalculoData != null) {
            formularioLayout.getPesoField().setText(String.valueOf(caloriasCalculoData.getPeso()));
            int indexActividad = caloriasCalculoData.getNivelActividadFisica() - 1;
            int indexRazon = caloriasCalculoData.getRazonConsulta() - 1;
            if (indexActividad >= 0)
                formularioLayout.getCmbNivelActividad().setSelectedIndex(indexActividad);
            if (indexRazon >= 0)
                formularioLayout.getCmbRazonConsulta().setSelectedIndex(indexRazon);
            formularioLayout.getTxtCaloriasTotales().setText(String.format("%.2f", caloriasCalculoData.getCalorias()));
        }

        if (macronutrientesData != null) {
            formularioLayout.getTxtProteinas().setText(String.format("%.2f", macronutrientesData.getProteinas()));
            formularioLayout.getTxtCarbohidratos().setText(String.format("%.2f", macronutrientesData.getCarbohidratos()));
            formularioLayout.getTxtLipidos().setText(String.format("%.2f", macronutrientesData.getLipidos()));
        }
    }
    
    /**
     * Limpia todos los campos del formulario al crear un nuevo paciente
     * junto con su primera consulta.
     */
    private void limpiarCamposPacienteYConsulta() {
        limpiarCamposConsulta();
        formularioLayout.getTxtNombreField().setText("");
        formularioLayout.getTxtApellidoField().setText("");
        formularioLayout.getTxtCorreoField().setText("");
        formularioLayout.getCmbSexo().setSelectedIndex(0);
        formularioLayout.getTxtTelefonoField().setText("");
        formularioLayout.clearAllFields();
        formularioLayout.getAlturaField().setText("");
    }

    /**
     * Limpia únicamente los campos relacionados con la consulta,
     * dejando intactos los datos personales del paciente.
     */
    private void limpiarCamposConsulta() {
        formularioLayout.getTxtCondicionesMedicasField().setText("");
        formularioLayout.getTxtMedicacionField().setText("");
        formularioLayout.getTxtHistorialCirugias().setText("");
        formularioLayout.getTxtAlergiasField().setText("");
        formularioLayout.getPreferenciaComidaField().setText("");
        formularioLayout.getTxtHorarioSueno().setText("");
        formularioLayout.getCmbNivelEstres().setSelectedIndex(0);
        formularioLayout.getTxtHabitoAlimenticio().setText("");
        formularioLayout.getTxtTipoLiquidoConsumido().setText("");
        formularioLayout.getTxtCantidadLiquido().setText("");
        formularioLayout.getTxtBarreraAlimenticia().setText("");
        formularioLayout.getPesoField().setText("");
        formularioLayout.getCmbNivelActividad().setSelectedIndex(0);
        formularioLayout.getCmbRazonConsulta().setSelectedIndex(0);
        formularioLayout.getTxtCaloriasTotales().setText("");
        formularioLayout.getTxtProteinas().setText("");
        formularioLayout.getTxtCarbohidratos().setText("");
        formularioLayout.getTxtLipidos().setText("");
    }

    /**
     * Muestra en la vista los valores de calorías y macronutrientes
     * calculados por el modelo.
     */    
    public void mostrarResultadoCalculo(CaloriasCalculo resultado, Macronutrientes macResultados) {
        if (resultado != null && macResultados != null) {
            formularioLayout.getTxtCaloriasTotales().setText(String.format("%.2f", resultado.getCalorias()));
            formularioLayout.getTxtProteinas().setText(String.format("%.2f", macResultados.getProteinas()));
            formularioLayout.getTxtCarbohidratos().setText(String.format("%.2f", macResultados.getCarbohidratos()));
            formularioLayout.getTxtLipidos().setText(String.format("%.2f", macResultados.getLipidos()));
        }
    }

    /**
     * Obtiene la fecha de nacimiento desde el componente gráfico,
     * ya sea un JDateChooser real o un JTextField de respaldo.
     *
     * @return Fecha en formato yyyy-MM-dd o cadena vacía.
     */
    private String obtenerFechaNacimientoStr() {
        Object chooser = formularioLayout.getFechaNacimientoChooser();
        if (chooser instanceof JTextField) {
            return ((JTextField) chooser).getText();
        } else if (chooser != null) {
            try {
                Date fecha = (Date) chooser.getClass().getMethod("getDate").invoke(chooser);
                if (fecha != null) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(fecha);
                }
            } catch (Exception e) {
            }
        }
        return "";
    }
    
    /**
     * Asigna en el componente gráfico la fecha de nacimiento obtenida del modelo.
     *
     * @param fechaStr Fecha en formato yyyy-MM-dd.
     */
    private void setFechaNacimientoEnChooser(String fechaStr) {
        try {
            Object chooser = formularioLayout.getFechaNacimientoChooser();
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            chooser.getClass().getMethod("setDate", Date.class).invoke(chooser, fecha);
        } catch (Exception e) {
        }
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(formularioLayout.getPanel(), msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Convierte a double un valor numérico recibido como texto.
     * Devuelve 0.0 en caso de error o formato inválido.
     *
     * @param val Texto numérico con punto o coma decimal.
     * @return Número convertido o 0.0.
     */
    private double parseDoubleSafe(String val) {
        try {
            return Double.parseDouble(val.replace(",", "."));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public String getViewAnteriorTag() {
        return formularioLayout.getViewAnteriorTag();
    }

    public String getClavePacienteActual() {
        return (pacienteActual != null) ? pacienteActual.getClavePaciente() : null;
    }
}
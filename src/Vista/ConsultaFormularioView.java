package Vista;

import Modelo.Core.View;
import Modelo.POJOs.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controlador.ConsultaController;

/**
 * Vista responsable de mostrar y gestionar el formulario de consultas.
 * Construye la interfaz, coordina interacción del usuario,
 * enlaza acciones con el controlador y sincroniza los datos del modelo.
 */
public class ConsultaFormularioView extends View {

    private ConsultaFormularioViewLayout disenoFormulario;
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
        disenoFormulario = new ConsultaFormularioViewLayout();
        this.mainPanel = disenoFormulario.getPanel();

        // Asignación de listeners: cada botón delega su acción al controlador (MVC)
        disenoFormulario.getBtnPacientes().addActionListener(e -> {
            myModel.setPacienteSeleccionado(null);
            myModel.setConsultaSeleccionada(null);
            myController.cambiarVista("PACIENTES", null);
        });

        disenoFormulario.getBtnExpedientes().addActionListener(e -> {
            if (pacienteActual != null) {
                myController.cambiarVista("HISTORIAL", pacienteActual.getClavePaciente());
            } else {
                myController.cambiarVista("PACIENTES", null);
            }
        });

        disenoFormulario.getBtnLogout().addActionListener(e -> {
            myController.handleLogout();
        });

        disenoFormulario.getBtnSalir().addActionListener(e -> myController.handleSalir());

        disenoFormulario.getBtnCalcular().addActionListener(e -> {
            try {
                double peso = Double.parseDouble(disenoFormulario.getPesoField().getText());
                int nivelActividad = disenoFormulario.getCmbNivelActividad().getSelectedIndex() + 1;
                int razonConsulta = disenoFormulario.getCmbRazonConsulta().getSelectedIndex() + 1;

                String clavePaciente = (pacienteActual != null) ? pacienteActual.getClavePaciente() : null;
                int sexo = disenoFormulario.getCmbSexo().getSelectedIndex();

                double altura = 0.0;
                if (pacienteActual != null) {
                    altura = pacienteActual.getAltura();
                } else {
                    try {
                        altura = Double.parseDouble(disenoFormulario.getAlturaField().getText());
                    } catch (NumberFormatException ex) {
                    }
                }

                String fechaNacimiento = obtenerFechaNacimientoTexto();

                myController.handleCalcularCalorias(
                        clavePaciente, peso, nivelActividad, razonConsulta, sexo, altura, fechaNacimiento);

            } catch (NumberFormatException ex) {
                mostrarError("Verifique que Peso y Altura sean números válidos.");
            } catch (Exception ex) {
                mostrarError("Error al calcular: " + ex.getMessage());
            }
        });

        disenoFormulario.getBtnGuardar().addActionListener(e -> {
            try {
                // 1. Obtener datos simples
                String nombre = disenoFormulario.getTxtNombreField().getText();
                String apellido = disenoFormulario.getTxtApellidoField().getText();
                String correo = disenoFormulario.getTxtCorreoField().getText();
                int sexo = disenoFormulario.getCmbSexo().getSelectedIndex();
                String telefono = disenoFormulario.getTxtTelefonoField().getText();
                String fechaNacimiento = obtenerFechaNacimientoTexto();
                double altura = convertirDouble(disenoFormulario.getAlturaField().getText());
                
                String claveNutriologo = (myModel.getNutriologoActual() != null) 
                        ? myModel.getNutriologoActual().getClaveNutriologo() 
                        : null;

                // 2. Determinar Clave de Paciente (Nuevo o Existente)
                String clavePaciente;
                Paciente objetoPaciente = null;

                if (pacienteActual != null) {
                    clavePaciente = pacienteActual.getClavePaciente();
                    // Actualizamos el objeto existente con los datos del formulario por si cambiaron
                    pacienteActual.setNombre(nombre);
                    pacienteActual.setApellido(apellido);
                    pacienteActual.setCorreo(correo);
                    pacienteActual.setSexo(sexo);
                    pacienteActual.setTelefono(telefono);
                    pacienteActual.setFechaNacimiento(fechaNacimiento);
                    pacienteActual.setAltura(altura);
                    objetoPaciente = pacienteActual;
                } else {
                    // Generar clave nueva usando el modelo
                    clavePaciente = myModel.generarClaveUnica();
                    // Crear objeto paciente nuevo
                    objetoPaciente = new Paciente(clavePaciente, claveNutriologo, nombre, apellido, 
                            correo, sexo, telefono, fechaNacimiento, altura);
                }

                // 3. Crear objetos anidados
                AnamnesisData anamnesis = new AnamnesisData(
                    disenoFormulario.getTxtCondicionesMedicasField().getText(),
                    disenoFormulario.getTxtMedicacionField().getText(),
                    disenoFormulario.getTxtHistorialCirugias().getText(),
                    disenoFormulario.getTxtAlergiasField().getText(),
                    disenoFormulario.getPreferenciaComidaField().getText(),
                    disenoFormulario.getTxtHorarioSueno().getText(),
                    disenoFormulario.getCmbNivelEstres().getSelectedIndex(),
                    disenoFormulario.getTxtHabitoAlimenticio().getText(),
                    disenoFormulario.getTxtTipoLiquidoConsumido().getText(),
                    convertirDouble(disenoFormulario.getTxtCantidadLiquido().getText()),
                    disenoFormulario.getTxtBarreraAlimenticia().getText()
                );

                CaloriasCalculo calculo = new CaloriasCalculo(
                    convertirDouble(disenoFormulario.getPesoField().getText()),
                    disenoFormulario.getCmbNivelActividad().getSelectedIndex() + 1,
                    disenoFormulario.getCmbRazonConsulta().getSelectedIndex() + 1,
                    convertirDouble(disenoFormulario.getTxtCaloriasTotales().getText())
                );

                Macronutrientes macros = new Macronutrientes(
                    convertirDouble(disenoFormulario.getTxtCarbohidratos().getText()),
                    convertirDouble(disenoFormulario.getTxtProteinas().getText()),
                    convertirDouble(disenoFormulario.getTxtLipidos().getText())
                );

                // 4. Determinar Clave de Consulta y Fecha
                String claveConsulta;
                String fechaVisita = null;

                if (consultaActual != null) {
                    claveConsulta = consultaActual.getClaveConsulta();
                    
                    fechaVisita = consultaActual.getFechaVisita(); 
                } else {
                    claveConsulta = myModel.generarClaveUnica();

                    // Si es nueva, la fecha es null aquí y el modelo asigna la fecha actual
                    fechaVisita = null;
                }

                // 5. Crear Objeto Consulta
                Consulta objetoConsulta = new Consulta(
                    claveConsulta,
                    clavePaciente,
                    claveNutriologo,
                    fechaVisita, 
                    0,    
                    altura,
                    anamnesis,
                    calculo,
                    macros
                );

                String modo = disenoFormulario.getModoActual();

                // 6. Enviar objetos al controlador
                myController.handleGuardarConsulta(objetoPaciente, objetoConsulta, modo);

            } catch (Exception ex) {
                mostrarError("Error al intentar guardar: " + ex.getMessage());
                
            }
        });
    }

    /**
     * Carga datos enviados desde otra vista o controlador.
     * Recibe parámetros como modo (AGREGAR/EDITAR), claves del paciente o consulta
     * y prepara la vista para mostrar los datos correspondientes.
     *
     * @param datos Mapa de valores enviados por otra vista.
     */
    @Override
    public void cargarDatos(Object datos) {
        this.pacienteActual = null;
        this.consultaActual = null;

        if (datos instanceof Map) {
            Map<String, Object> parametrosVista = (Map<String, Object>) datos;

            String modo = (String) parametrosVista.get("modo");
            String etiquetaVistaRetorno = (String) parametrosVista.get("returnViewTag");
            disenoFormulario.setModoActual(modo);
            disenoFormulario.setViewAnteriorTag(etiquetaVistaRetorno);

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
        String modo = disenoFormulario.getModoActual();
        if (modo == null)
            return;

        boolean agregandoNuevoPaciente = (pacienteActual == null && modo.equals(ConsultaController.MODO_AGREGAR));

        if (agregandoNuevoPaciente) {
            limpiarCamposPacienteYConsulta();
        } else {
            if (pacienteActual != null) {
                // Pre-llenar datos fijos del paciente
                disenoFormulario.getTxtNombreField().setText(pacienteActual.getNombre());
                disenoFormulario.getTxtApellidoField().setText(pacienteActual.getApellido());
                disenoFormulario.getTxtCorreoField().setText(pacienteActual.getCorreo());
                disenoFormulario.getTxtTelefonoField().setText(String.valueOf(pacienteActual.getTelefono()));
                disenoFormulario.getCmbSexo().setSelectedIndex(pacienteActual.getSexo());
                disenoFormulario.getAlturaField().setText(String.valueOf(pacienteActual.getAltura()));

                try {
                    establecerFechaNacimiento(pacienteActual.getFechaNacimiento());
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

                disenoFormulario.getPesoField().setText("");
                disenoFormulario.getTxtCantidadLiquido().setText("");
                disenoFormulario.getTxtTipoLiquidoConsumido().setText(""); // Opcional, a veces cambia

                disenoFormulario.getTxtCaloriasTotales().setText("");
                disenoFormulario.getTxtProteinas().setText("");
                disenoFormulario.getTxtCarbohidratos().setText("");
                disenoFormulario.getTxtLipidos().setText("");
            }
        } catch (Exception e) {
            System.err.println("No hay historial previo o error cargándolo: " + e.getMessage());
        }
    }

    /**
     * Muestra en la vista los valores de calorías y macronutrientes
     * calculados por el modelo.
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
            disenoFormulario.getTxtCondicionesMedicasField().setText(anamnesisData.getCondicionesMedicas());
            disenoFormulario.getTxtMedicacionField().setText(anamnesisData.getMedicacion());
            disenoFormulario.getTxtHistorialCirugias().setText(anamnesisData.getHistorialCirugias());
            disenoFormulario.getTxtAlergiasField().setText(anamnesisData.getAlergias());
            disenoFormulario.getPreferenciaComidaField().setText(anamnesisData.getPreferenciaComida());
            disenoFormulario.getTxtHorarioSueno().setText(anamnesisData.getHorarioSueno());
            disenoFormulario.getCmbNivelEstres().setSelectedIndex(anamnesisData.getNivelEstres());
            disenoFormulario.getTxtHabitoAlimenticio().setText(anamnesisData.getHabitoAlimenticio());
            disenoFormulario.getTxtTipoLiquidoConsumido().setText(anamnesisData.getTipoLiquidoConsumido());
            disenoFormulario.getTxtCantidadLiquido().setText(String.valueOf(anamnesisData.getCantidadLiquidoConsumido()));
            disenoFormulario.getTxtBarreraAlimenticia().setText(anamnesisData.getBarreraAlimenticia());
        }

        if (caloriasCalculoData != null) {
            disenoFormulario.getPesoField().setText(String.valueOf(caloriasCalculoData.getPeso()));
            int indexActividad = caloriasCalculoData.getNivelActividadFisica() - 1;
            int indexRazon = caloriasCalculoData.getRazonConsulta() - 1;
            if (indexActividad >= 0)
                disenoFormulario.getCmbNivelActividad().setSelectedIndex(indexActividad);
            if (indexRazon >= 0)
                disenoFormulario.getCmbRazonConsulta().setSelectedIndex(indexRazon);
            disenoFormulario.getTxtCaloriasTotales().setText(String.format("%.2f", caloriasCalculoData.getCalorias()));
        }

        if (macronutrientesData != null) {
            disenoFormulario.getTxtProteinas().setText(String.format("%.2f", macronutrientesData.getProteinas()));
            disenoFormulario.getTxtCarbohidratos().setText(String.format("%.2f", macronutrientesData.getCarbohidratos()));
            disenoFormulario.getTxtLipidos().setText(String.format("%.2f", macronutrientesData.getLipidos()));
        }
    }
    
    /**
     * Limpia todos los campos del formulario al crear un nuevo paciente
     * junto con su primera consulta.
     */
    private void limpiarCamposPacienteYConsulta() {
        limpiarCamposConsulta();
        disenoFormulario.getTxtNombreField().setText("");
        disenoFormulario.getTxtApellidoField().setText("");
        disenoFormulario.getTxtCorreoField().setText("");
        disenoFormulario.getCmbSexo().setSelectedIndex(0);
        disenoFormulario.getTxtTelefonoField().setText("");
        disenoFormulario.clearAllFields();
        disenoFormulario.getAlturaField().setText("");
    }

    /**
     * Limpia únicamente los campos relacionados con la consulta,
     * dejando intactos los datos personales del paciente.
     */
    private void limpiarCamposConsulta() {
        disenoFormulario.getTxtCondicionesMedicasField().setText("");
        disenoFormulario.getTxtMedicacionField().setText("");
        disenoFormulario.getTxtHistorialCirugias().setText("");
        disenoFormulario.getTxtAlergiasField().setText("");
        disenoFormulario.getPreferenciaComidaField().setText("");
        disenoFormulario.getTxtHorarioSueno().setText("");
        disenoFormulario.getCmbNivelEstres().setSelectedIndex(0);
        disenoFormulario.getTxtHabitoAlimenticio().setText("");
        disenoFormulario.getTxtTipoLiquidoConsumido().setText("");
        disenoFormulario.getTxtCantidadLiquido().setText("");
        disenoFormulario.getTxtBarreraAlimenticia().setText("");
        disenoFormulario.getPesoField().setText("");
        disenoFormulario.getCmbNivelActividad().setSelectedIndex(0);
        disenoFormulario.getCmbRazonConsulta().setSelectedIndex(0);
        disenoFormulario.getTxtCaloriasTotales().setText("");
        disenoFormulario.getTxtProteinas().setText("");
        disenoFormulario.getTxtCarbohidratos().setText("");
        disenoFormulario.getTxtLipidos().setText("");
    }

    /**
     * Muestra en la vista los valores de calorías y macronutrientes
     * calculados por el modelo.
     */    
    public void mostrarResultadoCalculo(CaloriasCalculo resultado, Macronutrientes macResultados) {
        if (resultado != null && macResultados != null) {
            disenoFormulario.getTxtCaloriasTotales().setText(String.format("%.2f", resultado.getCalorias()));
            disenoFormulario.getTxtProteinas().setText(String.format("%.2f", macResultados.getProteinas()));
            disenoFormulario.getTxtCarbohidratos().setText(String.format("%.2f", macResultados.getCarbohidratos()));
            disenoFormulario.getTxtLipidos().setText(String.format("%.2f", macResultados.getLipidos()));
        }
    }

    /**
     * Obtiene la fecha de nacimiento desde el componente gráfico,
     * ya sea un JDateChooser real o un JTextField de respaldo.
     *
     * @return Fecha en formato yyyy-MM-dd o cadena vacía.
     */
    private String obtenerFechaNacimientoTexto() {
        Object selectorFecha = disenoFormulario.getFechaNacimientoChooser();
        if (selectorFecha instanceof JTextField) {
            return ((JTextField) selectorFecha).getText();
        } else if (selectorFecha != null) {
            try {
                Date fecha = (Date) selectorFecha.getClass().getMethod("getDate").invoke(selectorFecha);
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
    private void establecerFechaNacimiento(String fechaStr) {
        try {
            Object selectorFecha = disenoFormulario.getFechaNacimientoChooser();
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fechaStr);
            selectorFecha.getClass().getMethod("setDate", Date.class).invoke(selectorFecha, fecha);
        } catch (Exception e) {
        }
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(disenoFormulario.getPanel(), mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Convierte a double un valor numérico recibido como texto.
     * Devuelve 0.0 en caso de error o formato inválido.
     *
     * @param valor Texto numérico con punto o coma decimal.
     * @return Número convertido o 0.0.
     */
    private double convertirDouble(String valor) {
        try {
            return Double.parseDouble(valor.replace(",", "."));
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public String getViewAnteriorTag() {
        return disenoFormulario.getViewAnteriorTag();
    }

    @Override
    public String getClavePacienteActual() {
        return (pacienteActual != null) ? pacienteActual.getClavePaciente() : null;
    }
}
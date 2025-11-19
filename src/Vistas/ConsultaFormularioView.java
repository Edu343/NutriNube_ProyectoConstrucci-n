package Vistas;


import Core.View;
import Controladores.ConsultaController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import POJOs.Consulta;
import POJOs.CaloriasCalculo;
import POJOs.AnamnesisData;
import POJOs.Macronutrientes;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class ConsultaFormularioView extends View {
    
    private ConsultaFormularioViewLayout formularioLayout;
    private Consulta consultaActual; 
    private String clavePacienteActual; 

    public ConsultaFormularioView(String tag) {
        super(tag);
    }

    @Override
    protected void crearController() {
        myController = new ConsultaController(tag); 
    }

    @Override
    protected void crearViewLayout() {
        formularioLayout = new ConsultaFormularioViewLayout();
        this.mainPanel = formularioLayout.getPanel();
        
        // --- Conexión de Eventos ---
        
        // Botón Calcular
        formularioLayout.getBtnCalcular().addActionListener(e -> {
            try {
                // 1. Recolectar datos necesarios del formulario para el cálculo
                CaloriasCalculo calculoData = collectCaloriasCalculoData();
                
                // 2. Llama al controlador para iniciar el cálculo (necesita el POJO Paciente/Datos clave)
                ((ConsultaController)myController).handleCalcularCalorias(calculoData, clavePacienteActual);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(formularioLayout.getPanel(), "Verifique los campos numéricos (Peso, Altura) o si faltan datos de selección.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Botón Guardar
        formularioLayout.getBtnGuardar().addActionListener(e -> {
            try {
                // 1. Recolectar y construir el objeto Consulta completo
                Consulta consultaAGuardar = buildConsultaObject();
                
                // 2. Llama al controlador para guardar la consulta
                ((ConsultaController)myController).handleGuardarConsulta(consultaAGuardar);
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(formularioLayout.getPanel(), "Error al guardar. Verifique entradas numéricas o faltantes.", "Error de Guardado", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Botón Salir/Cancelar
        formularioLayout.getBtnCancelar().addActionListener(e -> 
            ((ConsultaController)myController).handleSalir(clavePacienteActual));
    }

    // =========================================================
    // MÉTODOS DE RECOLECCIÓN (COLLECTORES)
    // =========================================================
    
    public CaloriasCalculo collectCaloriasCalculoData() throws NumberFormatException {
        CaloriasCalculo cc = new CaloriasCalculo();
        
        // Datos de ENTRADA para el cálculo
        cc.setPeso(Double.parseDouble(formularioLayout.getPesoField().getText()));
        cc.setNivelActividadFisica(Integer.parseInt(formularioLayout.getCmbNivelActividad().getSelectedItem().toString())); 
        cc.setRazonConsulta(Integer.parseInt(formularioLayout.getRazonConsultaBox().getSelectedItem().toString())); 
        
        // Datos de SALIDA (Lectura del resultado para guardar)
        // Solo si los campos de resultado no están vacíos
        if (!formularioLayout.getTxtCalorias().isEmpty()) {
             cc.setTotalCalorias(Double.parseDouble(formularioLayout.getTxtCalorias()));
        }
        
        return cc;
    }

    public AnamnesisData collectAnamnesisData() {
        AnamnesisData ad = new AnamnesisData();
        
        // Mapeo de campos de texto (ejemplo con los getters existentes)
        ad.setCondicionesMedicas(formularioLayout.getTxtCondicionesMedicas());
        ad.setMedicacion(formularioLayout.getTxtMedicacionField().getText());
        ad.setAlergias(formularioLayout.getTxtAlergiasField().getText());
        ad.setPreferenciaComida(formularioLayout.getCmbPreferenciaComida());
        // [ CONTINUAR MAPEANDO EL RESTO DE LOS 20+ CAMPOS DE TEXTO/COMBOBOX ]
        
        return ad;
    }
    
    public Macronutrientes collectMacronutrientesData() throws NumberFormatException {
        Macronutrientes mac = new Macronutrientes();
        // Lectura de los campos de SALIDA (resultados)
        mac.setProteinas(Double.parseDouble(formularioLayout.getTxtProteinas()));
        mac.setCarbohidratos(Double.parseDouble(formularioLayout.getTxtCarbohidratos()));
        mac.setLipidos(Double.parseDouble(formularioLayout.getTxtLipidos()));
        return mac;
    }
    
    /**
     * Construye el objeto Consulta completo para ser enviado al Controller y persistido.
     */
    public Consulta buildConsultaObject() throws NumberFormatException {
        Consulta c = (consultaActual != null) ? consultaActual : new Consulta();
        
        // 1. Datos Clave (ID del paciente y fecha)
        c.setClave_paciente(clavePacienteActual);
        c.setFecha_visita(LocalDate.now().toString()); 
        
        // 2. Componentes POJO internos (recolectados del formulario)
        c.setTotalCalorias(collectCaloriasCalculoData()); // Incluye Peso y TotalCalorias si fue calculado
        c.setAnamnesisData(collectAnamnesisData());
        c.setMacronutrientes(collectMacronutrientesData()); 

        return c;
    }

    // =========================================================
    // MÉTODOS DE ESTADO Y VISUALIZACIÓN
    // =========================================================
    
    @Override
    public void display() {
        // Implementación de la lógica de visualización (limpiar o cargar datos).
        if (consultaActual != null) {
            // MODO EDICIÓN: Cargar los datos de la consultaActual en el layout
            formularioLayout.getPesoField().setText(String.valueOf(consultaActual.getTotalCalorias().getPeso()));
            // Lógica para cargar todos los 20+ campos de AnamnesisData, Macronutrientes y Resultados.
            formularioLayout.getBtnGuardar().setEnabled(true);
        } else {
            // MODO NUEVA CONSULTA: Limpiar todos los campos
            // formularioLayout.clearAllFields(); 
            formularioLayout.getBtnGuardar().setEnabled(true);
        }
    }
    
    @Override
    public void loadData(Object data) {
        if (data instanceof String) {
            this.clavePacienteActual = (String) data;
            this.consultaActual = null; 
        } else if (data instanceof Consulta) {
            this.consultaActual = (Consulta) data;
            this.clavePacienteActual = this.consultaActual.getClave_paciente();
        }
        
        display();
    }
    
    /**
     * Muestra el resultado del cálculo recibido del Controller.
     */
    public void mostrarResultadoCalculo(CaloriasCalculo resultado, Macronutrientes macResultados, String mensaje) {
        if (resultado != null && macResultados != null) {
            // Muestra el total calórico
            formularioLayout.setTxtCalorias(String.valueOf(resultado.getTotalCalorias()));
            
            // Muestra la distribución de macronutrientes
            formularioLayout.setTxtProteinas(String.valueOf(macResultados.getProteinas()));
            formularioLayout.setTxtCarbohidratos(String.valueOf(macResultados.getCarbohidratos()));
            formularioLayout.setTxtLipidos(String.valueOf(macResultados.getLipidos()));
        }
        JOptionPane.showMessageDialog(formularioLayout.getPanel(), mensaje, "Cálculo Completo", JOptionPane.INFORMATION_MESSAGE);
    }
}
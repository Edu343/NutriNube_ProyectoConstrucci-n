package Vista;

import Modelo.Core.View;
import Controlador.ConsultaController;
import Modelo.POJOs.Consulta;
import Modelo.POJOs.CaloriasCalculo;
import Modelo.POJOs.AnamnesisData;
import Modelo.POJOs.Macronutrientes;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * Vista del formulario detallado para agregar o editar una consulta nutricional.
 * Recolecta datos de anamnesis, metas y resultados de cálculo.
 */

public class ConsultaFormularioView extends View {
    
    private ConsultaFormularioViewLayout formularioLayout;
    private Consulta consultaActual; 
    private String clavePacienteActual; 

    public ConsultaFormularioView(String tag) {
        super(tag);
    }

    @Override
    protected void crearController() {
        myController = new Controlador.ConsultaController(tag); 
    }

    @Override
    protected void crearViewLayout() {
        formularioLayout = new ConsultaFormularioViewLayout();
        this.mainPanel = formularioLayout.getPanel();
        
        formularioLayout.getBtnCalcular().addActionListener(e -> {
            try {
                CaloriasCalculo calculoData = collectCaloriasCalculoData();
                myController.handleCalcularCalorias(calculoData, null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(formularioLayout.getPanel(), 
                    "Verifique los campos numéricos (Peso, Altura, Cantidad de Líquido) o si faltan datos de selección.", 
                    "Error de Formato", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        formularioLayout.getBtnGuardar().addActionListener(e -> {
            try {
                Consulta consultaAGuardar = buildConsultaObject();
                myController.handleGuardarConsulta(consultaAGuardar);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(formularioLayout.getPanel(), 
                    "Error al guardar. Verifique entradas numéricas o faltantes.", 
                    "Error de Guardado", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        formularioLayout.getBtnSalir().addActionListener(e -> 
            myController.handleSalir());
    }

    public CaloriasCalculo collectCaloriasCalculoData() throws NumberFormatException {
        // Recolección de datos para cálculo
        CaloriasCalculo cc = new CaloriasCalculo();
        
        cc.setPeso(Double.parseDouble(formularioLayout.getPesoField().getText()));
        
        cc.setNivelActividadFisica(Integer.parseInt(formularioLayout.getCmbNivelActividad().getSelectedItem().toString()));
        
        int razonConsulta = formularioLayout.getCmbRazonConsulta().getSelectedIndex() + 1;
        cc.setRazonConsulta(razonConsulta);
        
        String caloriasText = formularioLayout.getTxtCaloriasTotales().getText();
        if (!caloriasText.isEmpty() && !caloriasText.equals("0.0")) {
            cc.setCalorias(Double.parseDouble(caloriasText));
        }
        
        return cc;
    }
    
   
    public AnamnesisData collectAnamnesisData() {
        // Recolección de datos de anamnesis
        AnamnesisData ad = new AnamnesisData();
        
        ad.setCondicionesMedicas(formularioLayout.getTxtCondicionesMedicasField().getText());
        ad.setMedicacion(formularioLayout.getTxtMedicacionField().getText());
        ad.setAlergias(formularioLayout.getTxtAlergiasField().getText());
        
        ad.setPreferenciaComida((String) formularioLayout.getCmbPreferenciaComida().getSelectedItem());
        
        ad.setHorarioSueno(formularioLayout.getTxtHorarioSueno().getText());
        ad.setHistorialCirugias(formularioLayout.getTxtHistorialCirugias().getText());
        
        int nivelEstresIndex = formularioLayout.getCmbNivelEstres().getSelectedIndex();
        ad.setNivelEstres(nivelEstresIndex + 1);
        
        ad.setHabitoAlimenticio(formularioLayout.getTxtHabitoAlimenticio().getText());
        ad.setTipoLiquidoConsumido(formularioLayout.getTxtTipoLiquidoConsumido().getText());
        
        String cantidadLiquidoStr = formularioLayout.getTxtCantidadLiquido().getText();
        if (!cantidadLiquidoStr.isEmpty()) {
            ad.setCantidadLiquidoConsumido(Double.parseDouble(cantidadLiquidoStr));
        }
        
        ad.setBarreraAlimenticia(formularioLayout.getTxtBarreraAlimenticia().getText());
        
        return ad;
    }
    
    public Macronutrientes collectMacronutrientesData() throws NumberFormatException {
        // Recolección de datos de macronutrientes
        Macronutrientes mac = new Macronutrientes();
        
        String proteinasText = formularioLayout.getTxtProteinas().getText();
        String carbohidratosText = formularioLayout.getTxtCarbohidratos().getText();
        String lipidosText = formularioLayout.getTxtLipidos().getText();
        
        if (!proteinasText.isEmpty() && !proteinasText.equals("0.0")) {
            mac.setProteinas(Double.parseDouble(proteinasText));
        }
        if (!carbohidratosText.isEmpty() && !carbohidratosText.equals("0.0")) {
            mac.setCarbohidratos(Double.parseDouble(carbohidratosText));
        }
        if (!lipidosText.isEmpty() && !lipidosText.equals("0.0")) {
            mac.setLipidos(Double.parseDouble(lipidosText));
        }
        
        return mac;
    }
    
    public Consulta buildConsultaObject() throws NumberFormatException {
        // Construye el objeto Consulta a partir de los datos recolectados.
        Consulta c = (consultaActual != null) ? consultaActual : new Consulta();
        
        c.setClavePaciente(clavePacienteActual);
        c.setFechaVisita(LocalDate.now().toString()); 
        
        c.setTotalCalorias(collectCaloriasCalculoData());
        c.setAnamnesisData(collectAnamnesisData());
        c.setMacronutrientes(collectMacronutrientesData()); 

        return c;
    }

    @Override
    public void display() {
        // Muestra el formulario según el modo (AGREGAR o EDITAR).
        String modo = formularioLayout.getModoActual();
        
        if (Controlador.ConsultaController.MODO_EDITAR.equals(modo) && consultaActual != null) {
            loadConsultaToForm(consultaActual);
        } else {
            formularioLayout.clearAllFields();
        }
        formularioLayout.getBtnGuardar().setEnabled(true);
    }
    
    private void loadConsultaToForm(Consulta consulta) {
        // Carga los datos de una consulta existente en el formulario.
        CaloriasCalculo cc = consulta.getCaloriasCalculo();
        if (cc != null) {
            formularioLayout.getPesoField().setText(String.valueOf(cc.getPeso()));
            
            double factor = cc.getNivelActividadFisica();
            int nafIndex = 0;
            if (factor == 1.375) nafIndex = 1;
            else if (factor == 1.55) nafIndex = 2;
            else if (factor == 1.725) nafIndex = 3;
            else if (factor == 1.9) nafIndex = 4;
            formularioLayout.getCmbNivelActividad().setSelectedIndex(nafIndex);
            
            formularioLayout.getCmbRazonConsulta().setSelectedIndex(cc.getRazonConsulta() - 1);
            
            formularioLayout.getTxtCaloriasTotales().setText(String.valueOf(cc.getCalorias()));
        }
        
        Macronutrientes macros = consulta.getMacronutrientes();
        if (macros != null) {
            formularioLayout.getTxtProteinas().setText(String.valueOf(macros.getProteinas()));
            formularioLayout.getTxtCarbohidratos().setText(String.valueOf(macros.getCarbohidratos()));
            formularioLayout.getTxtLipidos().setText(String.valueOf(macros.getLipidos()));
        }
        
        AnamnesisData ad = consulta.getAnamnesisData();
        if (ad != null) {
            formularioLayout.getTxtCondicionesMedicasField().setText(ad.getCondicionesMedicas());
            formularioLayout.getTxtMedicacionField().setText(ad.getMedicacion());
            formularioLayout.getTxtAlergiasField().setText(ad.getAlergias());
            formularioLayout.getTxtHorarioSueno().setText(ad.getHorarioSueno());
            formularioLayout.getTxtHistorialCirugias().setText(ad.getHistorialCirugias());
            formularioLayout.getCmbNivelEstres().setSelectedIndex(ad.getNivelEstres() - 1);
            formularioLayout.getTxtHabitoAlimenticio().setText(ad.getHabitoAlimenticio());
            formularioLayout.getTxtTipoLiquidoConsumido().setText(ad.getTipoLiquidoConsumido());
            formularioLayout.getTxtCantidadLiquido().setText(String.valueOf(ad.getCantidadLiquidoConsumido()));
            formularioLayout.getTxtBarreraAlimenticia().setText(ad.getBarreraAlimenticia());
        }
    }
    


    @Override
    public void loadData(Object data) {
        // Carga el modo de operación y el tag de la vista de retorno.
        if (data instanceof Map) {
            Map<?, ?> dataMap = (Map<?, ?>) data;
            
            String returnTag = (String) dataMap.get("returnViewTag");
            String modo = (String) dataMap.get("modo");
            
            formularioLayout.setModoActual(modo != null ? modo : Controlador.ConsultaController.MODO_AGREGAR);
            formularioLayout.setViewAnteriorTag(returnTag);

            if (dataMap.containsKey("consultaActual") && dataMap.get("consultaActual") instanceof Consulta) {
                this.consultaActual = (Consulta) dataMap.get("consultaActual");
                this.clavePacienteActual = this.consultaActual.getClavePaciente();
            } else if (dataMap.containsKey("clavePacienteActual")) {
                this.clavePacienteActual = (String) dataMap.get("clavePacienteActual");
                this.consultaActual = null;
            }
        } else {
            formularioLayout.setModoActual(Controlador.ConsultaController.MODO_AGREGAR);
            formularioLayout.setViewAnteriorTag(Modelo.Core.MainViewLayout.PACIENTES_VIEW);
            this.consultaActual = null;
            this.clavePacienteActual = null;
        }
        
        display();
    }
    
    public void mostrarResultadoCalculo(CaloriasCalculo resultado, Macronutrientes macResultados, String mensaje) {
        // Muestra los resultados del cálculo en los campos de salida.
        if (resultado != null && macResultados != null) {
            formularioLayout.getTxtCaloriasTotales().setText(String.valueOf(resultado.getCalorias()));
            formularioLayout.getTxtProteinas().setText(String.valueOf(macResultados.getProteinas()));
            formularioLayout.getTxtCarbohidratos().setText(String.valueOf(macResultados.getCarbohidratos()));
            formularioLayout.getTxtLipidos().setText(String.valueOf(macResultados.getLipidos()));
        }
        
        if (mensaje != null && !mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(formularioLayout.getPanel(), 
                mensaje, 
                "Cálculo Completo", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
 

    public String getViewAnteriorTag() {
        return formularioLayout.getViewAnteriorTag();
    }
    
    public String getClavePacienteActual() {
        return clavePacienteActual;
    }
}  
    

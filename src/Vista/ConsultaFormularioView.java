package Vistas;

import Core.View;
import Controladores.ConsultaController;
import POJOs.Consulta;
import POJOs.CaloriasCalculo;
import POJOs.AnamnesisData;
import POJOs.Macronutrientes;
import java.time.LocalDate;
import java.util.Map;

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
        
        formularioLayout.getBtnCalcular().addActionListener(e -> {
            try {
                CaloriasCalculo calculoData = collectCaloriasCalculoData();
                ((ConsultaController)myController).handleCalcularCalorias(calculoData, clavePacienteActual);
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
                ((ConsultaController)myController).handleGuardarConsulta(consultaAGuardar);
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
        Consulta c = (consultaActual != null) ? consultaActual : new Consulta();
        
        c.setClavePaciente(clavePacienteActual);
        c.setFechaVisita(LocalDate.now().toString()); 
        
        c.setTotalCalorias(collectCaloriasCalculoData());
        c.setAnamnesisData(collectAnamnesisData());
        c.setMacronutrientes(collectMacronutrientesData()); 

        return c;
    }

 // Archivo: ConsultaFormularioView.java

    @Override
    public void display() {
        // Leer el modo desde el layout
        String modo = formularioLayout.getModoActual();
        
        // Si es MODO_EDITAR y tenemos una consulta, la cargamos. Si no, limpiamos campos.
        if (Controladores.ConsultaController.MODO_EDITAR.equals(modo) && consultaActual != null) {
            loadConsultaToForm(consultaActual);
        } else {
            formularioLayout.clearAllFields();
        }
        formularioLayout.getBtnGuardar().setEnabled(true);
    }
    
    private void loadConsultaToForm(Consulta consulta) {
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
            // CmbPreferenciaComida no se carga porque no hay getter
        }
    }
    
 // Archivo: ConsultaFormularioView.java

    @Override
    public void loadData(Object data) {
        if (data instanceof Map) {
            Map<?, ?> dataMap = (Map<?, ?>) data;
            
            String returnTag = (String) dataMap.get("returnViewTag");
            String modo = (String) dataMap.get("modo");
            
            // 1. Establecer el Modo y el Tag de Retorno en el Layout
            formularioLayout.setModoActual(modo != null ? modo : Controladores.ConsultaController.MODO_AGREGAR);
            formularioLayout.setViewAnteriorTag(returnTag);

            // 2. Determinar si estamos cargando una consulta o solo una clave de paciente
            if (dataMap.containsKey("consultaActual") && dataMap.get("consultaActual") instanceof Consulta) {
                this.consultaActual = (Consulta) dataMap.get("consultaActual");
                this.clavePacienteActual = this.consultaActual.getClavePaciente();
            } else if (dataMap.containsKey("clavePacienteActual")) {
                this.clavePacienteActual = (String) dataMap.get("clavePacienteActual");
                this.consultaActual = null;
            }
        } else {
            // Fallback si no se pasó un Map, asumimos modo AGREGAR y retorno a PACIENTES
            formularioLayout.setModoActual(Controladores.ConsultaController.MODO_AGREGAR);
            formularioLayout.setViewAnteriorTag(Core.MainViewLayout.PACIENTES_VIEW);
            this.consultaActual = null;
            this.clavePacienteActual = null;
        }
        
        display();
    }
    
    public void mostrarResultadoCalculo(CaloriasCalculo resultado, Macronutrientes macResultados, String mensaje) {
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
    
    

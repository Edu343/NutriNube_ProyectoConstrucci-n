package Vistas;

import javax.swing.*;
import java.awt.*;

public class ConsultaFormularioViewLayout extends JPanel {
    
    // Declaración de constantes de color para la interfaz.
    private static final Color HEADER_COLOR = new Color(44, 54, 73);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = Color.BLACK;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    
    // Declaración de los campos de entrada de datos de la consulta.
    private JTextField pesoField;
    private JComboBox<String> cmbNivelActividad;
    private JComboBox<String> razonConsultaBox;
    private JTextField txtCondicionesMedicas;
    private JTextField txtMedicacionField;
    private JTextField txtAlergiasField;
    private JComboBox<String> cmbPreferenciaComida;
    private JTextField txtHorarioSueno;
    private JTextField txtHistorialCirugias;
    private JComboBox<String> cmbNivelEstres;
    private JTextField txtHabitoAlimenticio;
    private JTextField txtTipoLiquidoConsumido;
    private JTextField txtCantidadLiquido;
    private JTextField txtBarreraAlimenticia;
    
    // Declaración de los campos de resultados (valores calculados).
    private JTextField txtCalorias;
    private JTextField txtProteinas;
    private JTextField txtCarbohidratos;
    private JTextField txtLipidos;
    
    // Declaración de los botones de acción.
    private JButton btnCalcular;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    public ConsultaFormularioViewLayout() {
        // Configuración del layout principal como BorderLayout.
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        // --- Configuración del Panel de Cabecera (Header) ---
        
        // Creación y estilización del panel superior.
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));
        
        // Configuración y escalado de la etiqueta del logo.
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");
        Image scaledLogo = iconLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledLogo));
        headerPanel.add(lblLogo, BorderLayout.WEST);
        
        // Adición del panel de cabecera a la parte superior (NORTH) del layout.
        add(headerPanel, BorderLayout.NORTH);
        
        // --- Configuración del Panel Central (Formulario y Botones) ---
        
        // Panel principal que contendrá el formulario (con scroll) y los botones.
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setLayout(new BorderLayout());
        
        // Creación del JScrollPane para el formulario (para asegurar scroll vertical).
        JScrollPane scrollPane = new JScrollPane(createFormPanel());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior para los botones de acción.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        
        // Creación de botones usando el método auxiliar.
        btnCalcular = createBlackButton("Calcular");
        btnGuardar = createBlackButton("Guardar");
        btnCancelar = createBlackButton("Cancelar");
        
        // Adición de botones al panel inferior.
        buttonPanel.add(btnCalcular);
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Adición del panel principal al centro de la vista.
        add(mainPanel, BorderLayout.CENTER);
    }
    
    /**
     * Crea y configura el panel que contiene todos los campos del formulario.
     * @return El JPanel con el formulario completo.
     */
    private JPanel createFormPanel() {
        // Panel principal del formulario con BoxLayout vertical para apilar secciones.
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Configuración del título de la sección.
        JLabel lblTitulo = new JLabel("Formulario de Consulta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(TEXT_COLOR);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblTitulo);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // --- Sección de Datos Básicos ---
        
        // Panel con GridBagLayout para organizar etiquetas y campos en dos columnas.
        JPanel datosBasicos = new JPanel(new GridBagLayout());
        datosBasicos.setBackground(BACKGROUND_COLOR);
        datosBasicos.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Fila 0: Peso y campo de texto.
        gbc.gridx = 0;
        gbc.gridy = 0;
        datosBasicos.add(new JLabel("Peso (kg):"), gbc);
        gbc.gridx = 1;
        pesoField = new JTextField(15);
        datosBasicos.add(pesoField, gbc);
        
        // Fila 1: Nivel de Actividad y ComboBox.
        gbc.gridx = 0;
        gbc.gridy = 1;
        datosBasicos.add(new JLabel("Nivel Actividad:"), gbc);
        gbc.gridx = 1;
        cmbNivelActividad = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        datosBasicos.add(cmbNivelActividad, gbc);
        
        // Fila 2: Razón de la Consulta y ComboBox.
        gbc.gridx = 0;
        gbc.gridy = 2;
        datosBasicos.add(new JLabel("Razón Consulta:"), gbc);
        gbc.gridx = 1;
        razonConsultaBox = new JComboBox<>(new String[]{"Disminuir grasa", "Aumentar músculo", "Recomposición", "Salud"});
        datosBasicos.add(razonConsultaBox, gbc);
        
        formPanel.add(datosBasicos);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // --- Sección de Anamnesis (Historial Médico y Hábitos) ---
        
        // Panel para los campos de anamnesis con GridBagLayout.
        JPanel anamnesisPanel = new JPanel(new GridBagLayout());
        anamnesisPanel.setBackground(BACKGROUND_COLOR);
        anamnesisPanel.setBorder(BorderFactory.createTitledBorder("Anamnesis"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(5, 5, 5, 5);
        
        // Campos de anamnesis (filas 0 a 10).
        // Fila 0: Condiciones Médicas
        gbc2.gridx = 0; gbc2.gridy = 0;
        anamnesisPanel.add(new JLabel("Condiciones Médicas:"), gbc2);
        gbc2.gridx = 1;
        txtCondicionesMedicas = new JTextField(20);
        anamnesisPanel.add(txtCondicionesMedicas, gbc2);
        
        // Fila 1: Medicación
        gbc2.gridx = 0; gbc2.gridy = 1;
        anamnesisPanel.add(new JLabel("Medicación:"), gbc2);
        gbc2.gridx = 1;
        txtMedicacionField = new JTextField(20);
        anamnesisPanel.add(txtMedicacionField, gbc2);
        
        // Fila 2: Alergias
        gbc2.gridx = 0; gbc2.gridy = 2;
        anamnesisPanel.add(new JLabel("Alergias:"), gbc2);
        gbc2.gridx = 1;
        txtAlergiasField = new JTextField(20);
        anamnesisPanel.add(txtAlergiasField, gbc2);
        
        // Fila 3: Preferencia Comida
        gbc2.gridx = 0; gbc2.gridy = 3;
        anamnesisPanel.add(new JLabel("Preferencia Comida:"), gbc2);
        gbc2.gridx = 1;
        cmbPreferenciaComida = new JComboBox<>(new String[]{"Normal", "Vegetariano", "Vegano"});
        anamnesisPanel.add(cmbPreferenciaComida, gbc2);
        
        // Fila 4: Horario Sueño
        gbc2.gridx = 0; gbc2.gridy = 4;
        anamnesisPanel.add(new JLabel("Horario Sueño:"), gbc2);
        gbc2.gridx = 1;
        txtHorarioSueno = new JTextField(20);
        anamnesisPanel.add(txtHorarioSueno, gbc2);
        
        // Fila 5: Historial Cirugías
        gbc2.gridx = 0; gbc2.gridy = 5;
        anamnesisPanel.add(new JLabel("Historial Cirugías:"), gbc2);
        gbc2.gridx = 1;
        txtHistorialCirugias = new JTextField(20);
        anamnesisPanel.add(txtHistorialCirugias, gbc2);
        
        // Fila 6: Nivel Estrés
        gbc2.gridx = 0; gbc2.gridy = 6;
        anamnesisPanel.add(new JLabel("Nivel Estrés:"), gbc2);
        gbc2.gridx = 1;
        cmbNivelEstres = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        anamnesisPanel.add(cmbNivelEstres, gbc2);
        
        // Fila 7: Hábito Alimenticio
        gbc2.gridx = 0; gbc2.gridy = 7;
        anamnesisPanel.add(new JLabel("Hábito Alimenticio:"), gbc2);
        gbc2.gridx = 1;
        txtHabitoAlimenticio = new JTextField(20);
        anamnesisPanel.add(txtHabitoAlimenticio, gbc2);
        
        // Fila 8: Tipo Líquido
        gbc2.gridx = 0; gbc2.gridy = 8;
        anamnesisPanel.add(new JLabel("Tipo Líquido:"), gbc2);
        gbc2.gridx = 1;
        txtTipoLiquidoConsumido = new JTextField(20);
        anamnesisPanel.add(txtTipoLiquidoConsumido, gbc2);
        
        // Fila 9: Cantidad Líquido
        gbc2.gridx = 0; gbc2.gridy = 9;
        anamnesisPanel.add(new JLabel("Cantidad Líquido (L):"), gbc2);
        gbc2.gridx = 1;
        txtCantidadLiquido = new JTextField(20);
        anamnesisPanel.add(txtCantidadLiquido, gbc2);
        
        // Fila 10: Barrera Alimenticia
        gbc2.gridx = 0; gbc2.gridy = 10;
        anamnesisPanel.add(new JLabel("Barrera Alimenticia:"), gbc2);
        gbc2.gridx = 1;
        txtBarreraAlimenticia = new JTextField(20);
        anamnesisPanel.add(txtBarreraAlimenticia, gbc2);
        
        formPanel.add(anamnesisPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // --- Sección de Resultados (Calorías y Macros) ---
        
        // Panel para mostrar los resultados de cálculo.
        JPanel resultadosPanel = new JPanel(new GridBagLayout());
        resultadosPanel.setBackground(BACKGROUND_COLOR);
        resultadosPanel.setBorder(BorderFactory.createTitledBorder("Resultados"));
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.insets = new Insets(5, 5, 5, 5);
        
        // Fila 0: Total Calorías (campo de solo lectura)
        gbc3.gridx = 0; gbc3.gridy = 0;
        resultadosPanel.add(new JLabel("Total Calorías:"), gbc3);
        gbc3.gridx = 1;
        txtCalorias = new JTextField(15);
        txtCalorias.setEditable(false);
        resultadosPanel.add(txtCalorias, gbc3);
        
        // Fila 1: Proteínas (campo de solo lectura)
        gbc3.gridx = 0; gbc3.gridy = 1;
        resultadosPanel.add(new JLabel("Proteínas (g):"), gbc3);
        gbc3.gridx = 1;
        txtProteinas = new JTextField(15);
        txtProteinas.setEditable(false);
        resultadosPanel.add(txtProteinas, gbc3);
        
        // Fila 2: Carbohidratos (campo de solo lectura)
        gbc3.gridx = 0; gbc3.gridy = 2;
        resultadosPanel.add(new JLabel("Carbohidratos (g):"), gbc3);
        gbc3.gridx = 1;
        txtCarbohidratos = new JTextField(15);
        txtCarbohidratos.setEditable(false);
        resultadosPanel.add(txtCarbohidratos, gbc3);
        
        // Fila 3: Lípidos (campo de solo lectura)
        gbc3.gridx = 0; gbc3.gridy = 3;
        resultadosPanel.add(new JLabel("Lípidos (g):"), gbc3);
        gbc3.gridx = 1;
        txtLipidos = new JTextField(15);
        txtLipidos.setEditable(false);
        resultadosPanel.add(txtLipidos, gbc3);
        
        formPanel.add(resultadosPanel);
        
        return formPanel;
    }
    
    /**
     * Método auxiliar para crear botones negros estilizados.
     * @param text Texto del botón.
     * @return El botón estlizado.
     */
    private JButton createBlackButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BUTTON_COLOR);
        btn.setForeground(BUTTON_TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    // --- Métodos Getters y Setters ---
    
    public JPanel getPanel() {
        return this;
    }
    
    public JTextField getPesoField() {
        return pesoField;
    }
    
    public JComboBox<String> getCmbNivelActividad() {
        return cmbNivelActividad;
    }
    
    public JComboBox<String> getRazonConsultaBox() {
        return razonConsultaBox;
    }
    
    public String getTxtCondicionesMedicas() {
        return txtCondicionesMedicas.getText();
    }
    
    public JTextField getTxtMedicacionField() {
        return txtMedicacionField;
    }
    
    public JTextField getTxtAlergiasField() {
        return txtAlergiasField;
    }
    
    public String getCmbPreferenciaComida() {
        return cmbPreferenciaComida.getSelectedItem().toString();
    }
    
    public String getTxtCalorias() {
        return txtCalorias.getText();
    }
    
    public String getTxtProteinas() {
        return txtProteinas.getText();
    }
    
    public String getTxtCarbohidratos() {
        return txtCarbohidratos.getText();
    }
    
    public String getTxtLipidos() {
        return txtLipidos.getText();
    }
    
    public void setTxtCalorias(String value) {
        txtCalorias.setText(value);
    }
    
    public void setTxtProteinas(String value) {
        txtProteinas.setText(value);
    }
    
    public void setTxtCarbohidratos(String value) {
        txtCarbohidratos.setText(value);
    }
    
    public void setTxtLipidos(String value) {
        txtLipidos.setText(value);
    }
    
    public JButton getBtnCalcular() {
        return btnCalcular;
    }
    
    public JButton getBtnGuardar() {
        return btnGuardar;
    }
    
    public JButton getBtnCancelar() {
        return btnCancelar;
    }
}
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PatientRecordView extends JFrame {

    // --- Colores y Estilos ---
    private final Color PRIMARY_BG_COLOR = new Color(44, 50, 64); // Azul/Gris Oscuro (Encabezado, Botones)
    private final Color ACCENT_COLOR = new Color(0, 150, 136); // Teal/Cian para contraste (Botón Calcular)
    private final Color INPUT_BG_COLOR = new Color(245, 245, 245); // Fondo claro para campos

    // --- Componentes ---
    private JPanel headerPanel;
    // Nota para el controlador: `appLogo` muestra el logo en el encabezado. Para
    // cambiar la imagen en tiempo de ejecución use:
    // appLogo.setIcon(new ImageIcon(...));
    private JLabel appLogo;
    // Nota para el controlador: acciones del encabezado: btnPatients,
    // btnExpedients, btnLogout.
    // Añada ActionListener o use button.getActionCommand() para identificar la
    // acción.
    private JButton btnPatients, btnExpedients, btnLogout;

    // Título principal de la vista
    // Nota para el controlador: leer el texto con lblConsultaPaciente.getText() si
    // es necesario.
    private JLabel lblConsultaPaciente;

    // Paneles principales (cada sección contiene sus labels y campos)
    // Si el controlador necesita acceso directo a campos específicos dentro de
    // estas secciones,
    // considere promover esos JTextField a campos de clase y asignarlos aquí.
    private JPanel personalInfoPanel;
    private JPanel medicalHistoryPanel;
    private JPanel foodHistoryPanel;
    private JPanel physicalActivityPanel;
    private JPanel lifestylePanel;
    private JPanel liquidConsumptionPanel;
    private JPanel nutritionalGoalsPanel;
    private JPanel dispositionPanel;
    private JPanel resultsPanel;

    // Componentes que sí están expuestos como campos para acceso del controlador
    // (combos y botones principales)
    // Nota para el controlador: cmbNivelActividad -> opción de nivel de actividad
    // física
    private JComboBox<String> cmbNivelActividad;
    // Nota para el controlador: cmbRazonConsulta -> razón de consulta seleccionada
    private JComboBox<String> cmbRazonConsulta;
    // Nota para el controlador: btnCalcular -> botón para calcular metas — añadir
    // ActionListener aquí
    private JButton btnCalcular;
    // Nota para el controlador: campos de resultados: txtProteinas,
    // txtCarbohidratos, txtLipidos, txtCaloriasTotales
    private JTextField txtProteinas, txtCarbohidratos, txtLipidos, txtCaloriasTotales;
    // Nota para el controlador: btnGuardar, btnSalir -> acciones principales
    // (guardar/salir)
    private JButton btnGuardar, btnSalir;

    public PatientRecordView() {
        initComponents();
    }

    private void initComponents() {
        this.setTitle("Patient Record - NutriNube");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 900);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());

        // --- Header ---
        headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(PRIMARY_BG_COLOR);
        headerPanel.setPreferredSize(new Dimension(this.getWidth(), 70));

        GridBagConstraints gbcHeader = new GridBagConstraints();
        gbcHeader.insets = new Insets(0, 15, 0, 15);
        gbcHeader.gridy = 0;

        ImageIcon originalLogo = new ImageIcon("NutriNube.png");
        Image scaledLogo = originalLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
        appLogo = new JLabel(new ImageIcon(scaledLogo));

        gbcHeader.gridx = 0;
        gbcHeader.anchor = GridBagConstraints.WEST;
        headerPanel.add(appLogo, gbcHeader);

        gbcHeader.gridx = 1;
        gbcHeader.weightx = 1.0;
        gbcHeader.fill = GridBagConstraints.HORIZONTAL;
        headerPanel.add(Box.createGlue(), gbcHeader);

        // Nota para el controlador: btnPatients -> agregar ActionListener para mostrar
        // la lista de pacientes
        btnPatients = createHeaderButton("Pacientes");
        // Añadir icono de estetoscopio a la izquierda del texto
        // Coloque el archivo 'stethoscope.png' en la carpeta raíz del proyecto o cambie
        // la ruta
        try {
            ImageIcon stethIcon = new ImageIcon("stethoscope.png");
            Image st = stethIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            btnPatients.setIcon(new ImageIcon(st));
            btnPatients.setHorizontalTextPosition(SwingConstants.RIGHT);
            btnPatients.setIconTextGap(8);
        } catch (Exception ex) {
            // Si no se encuentra el recurso, el botón quedará sin icono; no lanzar
            // excepción
        }
        gbcHeader.gridx = 2;
        headerPanel.add(btnPatients, gbcHeader);
        // Nota para el controlador: btnExpedients -> abrir expedientes / historial
        btnExpedients = createHeaderButton("Expedientes");
        gbcHeader.gridx = 3;
        headerPanel.add(btnExpedients, gbcHeader);
        // Nota para el controlador: btnLogout -> cerrar sesión / limpiar contexto
        btnLogout = createHeaderButton("Log Out");
        gbcHeader.gridx = 4;
        headerPanel.add(btnLogout, gbcHeader);

        this.add(headerPanel, BorderLayout.NORTH);

        // --- Contenido principal ---
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        this.add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(10, 40, 10, 40);
        gbcMain.fill = GridBagConstraints.HORIZONTAL;
        gbcMain.weightx = 1.0;
        gbcMain.gridwidth = GridBagConstraints.REMAINDER;

        lblConsultaPaciente = new JLabel("Consulta de Paciente");
        lblConsultaPaciente.setFont(new Font("Arial", Font.BOLD, 26));
        lblConsultaPaciente.setForeground(PRIMARY_BG_COLOR);
        gbcMain.insets = new Insets(20, 40, 20, 40);
        mainContentPanel.add(lblConsultaPaciente, gbcMain);
        gbcMain.insets = new Insets(10, 40, 10, 40);

        // --- Secciones ---
        personalInfoPanel = createSectionPanel("Información Personal",
                new String[] { "Nombre(s)", "Apellido", "Edad", "Sexo", "Altura", "Peso" });
        mainContentPanel.add(personalInfoPanel, gbcMain);

        medicalHistoryPanel = createSectionPanel("Historial Médico",
                new String[] { "Condiciones Médicas", "Historial de cirugías", "Medicación" });
        mainContentPanel.add(medicalHistoryPanel, gbcMain);

        foodHistoryPanel = createSectionPanel("Historial Alimenticio",
                new String[] { "Alergias", "Preferencias de Comida" });
        mainContentPanel.add(foodHistoryPanel, gbcMain);

        // --- Nivel de Actividad Física ---
        physicalActivityPanel = createSectionPanel("Nivel de Actividad Física",
                new String[] { "Nivel diario de actividad física" });
        // Nota para el controlador: se reemplaza el JTextField auto-creado en esta
        // sección por un JComboBox. Si el controlador busca componentes por índice,
        // tenga en cuenta que este código elimina el componente original en la
        // posición 1 e inserta `cmbNivelActividad` en su lugar.
        JPanel fieldsPanelPA = (JPanel) physicalActivityPanel.getComponent(1);
        Component textFieldToRemovePA = fieldsPanelPA.getComponent(1);
        GridBagConstraints gbcPA = ((GridBagLayout) fieldsPanelPA.getLayout())
                .getConstraints(textFieldToRemovePA);
        fieldsPanelPA.remove(textFieldToRemovePA);

        cmbNivelActividad = createComboBox(new String[] { "Bajo", "Medio", "Alto" });
        // Nota para el controlador: puede leer la selección con
        // cmbNivelActividad.getSelectedItem() o cmbNivelActividad.getSelectedIndex().
        fieldsPanelPA.add(cmbNivelActividad, gbcPA, 1);
        mainContentPanel.add(physicalActivityPanel, gbcMain);

        lifestylePanel = createSectionPanel("Estilo de Vida",
                new String[] { "Horario de sueño", "Niveles de estrés", "Hábitos alimenticios" });
        mainContentPanel.add(lifestylePanel, gbcMain);

        liquidConsumptionPanel = createSectionPanel("Consumo de Líquidos",
                new String[] { "Tipos de líquidos consumidos", "Cantidad de líquidos consumidos" });
        mainContentPanel.add(liquidConsumptionPanel, gbcMain);

        // --- Metas Nutricionales y Disposición alineadas ---
        JPanel goalsAndDispositionContainer = new JPanel(new GridBagLayout());
        goalsAndDispositionContainer.setBackground(Color.WHITE);

        GridBagConstraints gbcGD = new GridBagConstraints();
        gbcGD.insets = new Insets(10, 20, 10, 20);
        gbcGD.fill = GridBagConstraints.HORIZONTAL;
        gbcGD.weightx = 0.5;

        nutritionalGoalsPanel = createSectionPanel("Metas Nutricionales", new String[] { "Razón de consulta" });
        // Nota para el controlador: aquí reemplazamos el JTextField por defecto por
        // un JComboBox para la razón de consulta. Si su controlador espera el
        // JTextField original, promueva ese campo a una variable de clase.
        JPanel fieldsPanelNG = (JPanel) nutritionalGoalsPanel.getComponent(1);
        Component textFieldToRemoveNG = fieldsPanelNG.getComponent(1);
        GridBagConstraints gbcNG = ((GridBagLayout) fieldsPanelNG.getLayout())
                .getConstraints(textFieldToRemoveNG);
        fieldsPanelNG.remove(textFieldToRemoveNG);

        cmbRazonConsulta = createComboBox(
                new String[] { "Perder % de grasa", "Ganar masa muscular", "Recomposición Corporal", "Mejorar salud" });
        // Nota para el controlador: obtenga la selección con
        // cmbRazonConsulta.getSelectedIndex() o cmbRazonConsulta.getSelectedItem().
        fieldsPanelNG.add(cmbRazonConsulta, gbcNG, 1);

        dispositionPanel = createSectionPanel("Disposición", new String[] { "Alguna barrera para seguir el plan" });

        gbcGD.gridx = 0;
        gbcGD.gridy = 0;
        goalsAndDispositionContainer.add(nutritionalGoalsPanel, gbcGD);

        gbcGD.gridx = 1;
        gbcGD.gridy = 0;
        goalsAndDispositionContainer.add(dispositionPanel, gbcGD);

        btnCalcular = createActionButton("Calcular");
        btnCalcular.setBackground(ACCENT_COLOR);

        JPanel btnCalcularContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCalcularContainer.setBackground(Color.WHITE);
        // Nota para el controlador: btnCalcular -> agregar ActionListener para
        // ejecutar el cálculo de metas.
        btnCalcularContainer.add(btnCalcular);

        gbcGD.gridx = 0;
        gbcGD.gridy = 1;
        gbcGD.gridwidth = 2;
        gbcGD.anchor = GridBagConstraints.CENTER;
        goalsAndDispositionContainer.add(btnCalcularContainer, gbcGD);

        gbcMain.insets = new Insets(10, 40, 10, 40);
        mainContentPanel.add(goalsAndDispositionContainer, gbcMain);

        // --- Resultados ---
        JLabel lblResultsTitle = new JLabel("Distribución Nutricional");
        lblResultsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblResultsTitle.setForeground(PRIMARY_BG_COLOR);
        gbcMain.insets = new Insets(20, 40, 5, 40);
        mainContentPanel.add(lblResultsTitle, gbcMain);

        resultsPanel = new JPanel(new GridBagLayout());
        resultsPanel.setBackground(Color.WHITE);
        resultsPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        GridBagConstraints gbcResults = new GridBagConstraints();
        gbcResults.insets = new Insets(10, 15, 10, 15);
        gbcResults.fill = GridBagConstraints.HORIZONTAL;
        gbcResults.weightx = 0.5;

        txtProteinas = createTextField();
        txtCarbohidratos = createTextField();
        txtLipidos = createTextField();
        txtCaloriasTotales = createTextField();

        gbcResults.gridx = 0;
        gbcResults.gridy = 0;
        gbcResults.anchor = GridBagConstraints.EAST;
        resultsPanel.add(createLabel("Proteínas (g):"), gbcResults);
        gbcResults.gridx = 1;
        resultsPanel.add(txtProteinas, gbcResults);

        gbcResults.gridx = 2;
        resultsPanel.add(createLabel("Lípidos (g):"), gbcResults);
        gbcResults.gridx = 3;
        resultsPanel.add(txtLipidos, gbcResults);

        gbcResults.gridx = 0;
        gbcResults.gridy = 1;
        resultsPanel.add(createLabel("Carbohidratos (g):"), gbcResults);
        gbcResults.gridx = 1;
        resultsPanel.add(txtCarbohidratos, gbcResults);

        gbcResults.gridx = 2;
        resultsPanel.add(createLabel("Calorías Totales (kcal):"), gbcResults);
        gbcResults.gridx = 3;
        resultsPanel.add(txtCaloriasTotales, gbcResults);

        gbcMain.insets = new Insets(5, 40, 20, 40);
        mainContentPanel.add(resultsPanel, gbcMain);

        // Nota para el controlador (acciones del pie de página):
        // - btnGuardar: añadir ActionListener para validar entradas y guardar el
        // expediente del paciente. Sugerencia de actionCommand: "SAVE_PATIENT".
        // - btnSalir: añadir ActionListener para cerrar la vista o navegar atrás.
        // Sugerencia de actionCommand: "EXIT".
        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        actionButtonsPanel.setBackground(Color.WHITE);
        btnGuardar = createActionButton("Guardar");
        btnSalir = createActionButton("Salir");
        actionButtonsPanel.add(btnGuardar);
        actionButtonsPanel.add(btnSalir);
        gbcMain.insets = new Insets(20, 40, 20, 40);
        mainContentPanel.add(actionButtonsPanel, gbcMain);

        JLabel lblExpedienteFooter = new JLabel("Expediente");
        lblExpedienteFooter.setFont(new Font("Arial", Font.BOLD, 20));
        lblExpedienteFooter.setForeground(PRIMARY_BG_COLOR);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 15));
        footerPanel.setBackground(INPUT_BG_COLOR.darker());
        footerPanel.add(lblExpedienteFooter);
        this.add(footerPanel, BorderLayout.SOUTH);

        this.pack();
        this.setMinimumSize(new Dimension(800, 700));
    }

    // --- Métodos utilitarios ---
    private JPanel createSectionPanel(String title, String[] fieldNames) {
        // Nota para el controlador:
        // - Este método crea una sección titulada con etiquetas y JTextFields según
        // el array `fieldNames`.
        // - Por diseño, los JTextFields se crean como componentes locales dentro de
        // este panel. Si el controlador necesita acceso directo a un campo concreto
        // (por ejemplo "Nombre"), se recomienda promocionar ese JTextField a una
        // variable de clase y asignarlo aquí.
        // - Alternativa (menos robusta): el controlador puede localizar campos en
        // tiempo de ejecución recorriendo los componentes del panel, por ejemplo:
        // JPanel fieldsPanel = (JPanel) sectionPanel.getComponent(1);
        // Component c = fieldsPanel.getComponent(index);
        // JTextField tf = (JTextField) c;
        // Esta aproximación depende del orden de los componentes y es más frágil
        // que exponer campos explícitamente.

        JPanel sectionPanel = new JPanel(new BorderLayout());
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("Arial", Font.BOLD, 18));
        sectionTitle.setForeground(PRIMARY_BG_COLOR);
        sectionTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        sectionPanel.add(sectionTitle, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < fieldNames.length; i++) {
            JLabel label = createLabel(fieldNames[i]);
            JTextField textField = createTextField();

            gbc.gridx = (i % 2) * 2;
            gbc.gridy = i / 2;
            gbc.weightx = 0.0;
            gbc.anchor = GridBagConstraints.EAST;
            fieldsPanel.add(label, gbc);

            gbc.gridx = (i % 2) * 2 + 1;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            fieldsPanel.add(textField, gbc);
        }

        sectionPanel.add(fieldsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(PRIMARY_BG_COLOR.darker());
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(INPUT_BG_COLOR);
        textField.setForeground(PRIMARY_BG_COLOR);
        textField.setCaretColor(PRIMARY_BG_COLOR);
        Border outer = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        Border inner = BorderFactory.createEmptyBorder(6, 10, 6, 10);
        textField.setBorder(BorderFactory.createCompoundBorder(outer, inner));
        textField.setPreferredSize(new Dimension(250, 30));
        return textField;
    }

    // --- ✅ ComboBox corregido (sin borde doble) ---
    private JComboBox<String> createComboBox(String[] options) {
        JComboBox<String> cmb = new JComboBox<>(options);
        cmb.setFont(new Font("Arial", Font.PLAIN, 14));
        cmb.setBackground(Color.WHITE);
        cmb.setForeground(PRIMARY_BG_COLOR);
        cmb.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        cmb.setPreferredSize(new Dimension(250, 30));
        cmb.setOpaque(true);
        return cmb;
    }

    private JButton createActionButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(PRIMARY_BG_COLOR);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private JButton createHeaderButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_BG_COLOR);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientRecordView().setVisible(true));
    }
}

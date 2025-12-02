package Vista;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Date;
// Simulación de importación de JDateChooser

public class ConsultaFormularioViewLayout extends JPanel {

    private final Color PRIMARY_BG_COLOR = new Color(44, 50, 64);
    private final Color ACCENT_COLOR = new Color(0, 150, 136);
    private final Color INPUT_BG_COLOR = new Color(245, 245, 245);

    private JPanel headerPanel;
    private JLabel appLogo;
    private JButton btnPatients, btnExpedients, btnLogout;

    private JLabel lblConsultaPaciente;

    private JPanel personalInfoPanel;
    private JPanel medicalHistoryPanel;
    private JPanel foodHistoryPanel;
    private JPanel physicalActivityPanel;
    private JPanel lifestylePanel;
    private JPanel liquidConsumptionPanel;
    private JPanel nutritionalGoalsPanel;
    private JPanel dispositionPanel;
    private JPanel resultsPanel;

    private JComboBox<String> cmbNivelActividad;
    private JComboBox<String> cmbRazonConsulta;
    private JButton btnCalcular;
    private JTextField txtProteinas, txtCarbohidratos, txtLipidos, txtCaloriasTotales;
    private JButton btnGuardar, btnSalir;

    private JTextField txtNombreField;
    private JTextField txtApellidoField;
    private Object fechaNacimientoChooser;
    private JComboBox<String> cmbSexo;
    private JTextField txtCorreoField;
    private JTextField txtTelefonoField;
    private JTextField pesoField;
    private JTextField alturaField;
    private JTextField txtCondicionesMedicasField;
    private JTextField txtMedicacionField;
    private JTextField txtAlergiasField;
    private JTextField txtPreferenciaComidaField;
    private JTextField txtHorarioSueno;
    private JTextField txtHistorialCirugias;
    private JComboBox<String> cmbNivelEstres;
    private JTextField txtHabitoAlimenticio;
    private JTextField txtTipoLiquidoConsumido;
    private JTextField txtCantidadLiquido;
    private JTextField txtBarreraAlimenticia;

    private String viewAnteriorTag;
    private String modoActual = "";

    public ConsultaFormularioViewLayout() {
        initComponents();
    }

    private void initComponents() {

        this.setSize(1000, 900);

        this.setLayout(new BorderLayout());

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

        btnPatients = createHeaderButton("Pacientes");
        try {
            ImageIcon stethIcon = new ImageIcon("stethoscope.png");
            Image st = stethIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            btnPatients.setIcon(new ImageIcon(st));
            btnPatients.setHorizontalTextPosition(SwingConstants.RIGHT);
            btnPatients.setIconTextGap(8);
        } catch (Exception ex) {
        }
        gbcHeader.gridx = 2;
        headerPanel.add(btnPatients, gbcHeader);
        btnExpedients = createHeaderButton("Expedientes");
        gbcHeader.gridx = 3;
        headerPanel.add(btnExpedients, gbcHeader);
        btnLogout = createHeaderButton("Log Out");
        gbcHeader.gridx = 4;
        headerPanel.add(btnLogout, gbcHeader);

        this.add(headerPanel, BorderLayout.NORTH);

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

        // Inicializar el JDateChooser
        try {
            Class<?> dateChooserClass = Class.forName("com.toedter.calendar.JDateChooser");
            fechaNacimientoChooser = dateChooserClass.getConstructor(Date.class).newInstance(new Date());
            dateChooserClass.getMethod("setDateFormatString", String.class).invoke(fechaNacimientoChooser,
                    "yyyy-MM-dd");
        } catch (Exception e) {
            // Fallback a JTextField si JCalendar no está disponible
            JTextField fallback = createTextField();
            fallback.setText("AAAA-MM-DD");
            fechaNacimientoChooser = fallback;
        }

        personalInfoPanel = createPersonalInfoPanel("Información Personal",
                new String[] { "Nombre(s)", "Apellido", "Fecha de Nacimiento", "Sexo", "Correo Electrónico", "Teléfono",
                        "Altura (cm)", "Peso (kg)" });
        mainContentPanel.add(personalInfoPanel, gbcMain);

        medicalHistoryPanel = createMedicalHistoryPanel("Historial Médico",
                new String[] { "Condiciones Médicas", "Historial de cirugías", "Medicación" });
        mainContentPanel.add(medicalHistoryPanel, gbcMain);

        foodHistoryPanel = createFoodHistoryPanel("Historial Alimenticio",
                new String[] { "Alergias", "Preferencias de Comida" });
        mainContentPanel.add(foodHistoryPanel, gbcMain);

        physicalActivityPanel = createPhysicalActivityPanel("Nivel de Actividad Física",
                new String[] { "Nivel diario de actividad física" });
        mainContentPanel.add(physicalActivityPanel, gbcMain);

        lifestylePanel = createLifestylePanel("Estilo de Vida",
                new String[] { "Horario de sueño", "Niveles de estrés", "Hábitos alimenticios" });
        mainContentPanel.add(lifestylePanel, gbcMain);

        liquidConsumptionPanel = createLiquidConsumptionPanel("Consumo de Líquidos",
                new String[] { "Tipos de líquidos consumidos", "Cantidad de líquidos consumidos" });
        mainContentPanel.add(liquidConsumptionPanel, gbcMain);

        JPanel goalsAndDispositionContainer = new JPanel(new GridBagLayout());
        goalsAndDispositionContainer.setBackground(Color.WHITE);

        GridBagConstraints gbcGD = new GridBagConstraints();
        gbcGD.insets = new Insets(10, 20, 10, 20);
        gbcGD.fill = GridBagConstraints.HORIZONTAL;
        gbcGD.weightx = 0.5;

        nutritionalGoalsPanel = createNutritionalGoalsPanel("Metas Nutricionales",
                new String[] { "Razón de consulta" });

        dispositionPanel = createDispositionPanel("Disposición", new String[] { "Alguna barrera para seguir el plan" });

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
        btnCalcularContainer.add(btnCalcular);

        gbcGD.gridx = 0;
        gbcGD.gridy = 1;
        gbcGD.gridwidth = 2;
        gbcGD.anchor = GridBagConstraints.CENTER;
        goalsAndDispositionContainer.add(btnCalcularContainer, gbcGD);

        gbcMain.insets = new Insets(10, 40, 10, 40);
        mainContentPanel.add(goalsAndDispositionContainer, gbcMain);

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
        gbcResults.gridy = 0;
        resultsPanel.add(createLabel("Lípidos (g):"), gbcResults);
        gbcResults.gridx = 3;
        resultsPanel.add(txtLipidos, gbcResults);

        gbcResults.gridx = 0;
        gbcResults.gridy = 1;
        resultsPanel.add(createLabel("Carbohidratos (g):"), gbcResults);
        gbcResults.gridx = 1;
        resultsPanel.add(txtCarbohidratos, gbcResults);

        gbcResults.gridx = 2;
        gbcResults.gridy = 1;
        resultsPanel.add(createLabel("Calorías Totales (kcal):"), gbcResults);
        gbcResults.gridx = 3;
        resultsPanel.add(txtCaloriasTotales, gbcResults);

        gbcMain.insets = new Insets(5, 40, 20, 40);
        mainContentPanel.add(resultsPanel, gbcMain);

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

        this.setMinimumSize(new Dimension(800, 700));
    }

    private JPanel createSectionPanel(String title, String[] fieldNames) {
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
            Component inputComponent = null;

            // Lógica para asignar el JDateChooser
            if (title.equals("Información Personal") && fieldNames[i].equals("Fecha de Nacimiento")) {
                inputComponent = (Component) fechaNacimientoChooser;
                ((Component) inputComponent).setPreferredSize(new Dimension(250, 30));
            } else if (title.equals("Información Personal") && fieldNames[i].equals("Sexo")) {
                inputComponent = createComboBox(new String[] { "Mujer", "Hombre" });
                cmbSexo = (JComboBox<String>) inputComponent;
            } else {
                // Caso por defecto: siempre crear un JTextField si no es un componente especial
                inputComponent = createTextField();

                if (title.equals("Información Personal")) {
                    if (fieldNames[i].equals("Nombre(s)"))
                        txtNombreField = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Apellido"))
                        txtApellidoField = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Correo Electrónico"))
                        txtCorreoField = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Teléfono"))
                        txtTelefonoField = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Peso (kg)"))
                        pesoField = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Altura (cm)"))
                        alturaField = (JTextField) inputComponent;
                } else if (title.equals("Historial Médico")) {
                    if (fieldNames[i].equals("Condiciones Médicas"))
                        txtCondicionesMedicasField = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Medicación"))
                        txtMedicacionField = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Historial de cirugías"))
                        txtHistorialCirugias = (JTextField) inputComponent;
                } else if (title.equals("Historial Alimenticio")) {
                    if (fieldNames[i].equals("Alergias"))
                        txtAlergiasField = (JTextField) inputComponent;
                } else if (title.equals("Estilo de Vida")) {
                    if (fieldNames[i].equals("Horario de sueño"))
                        txtHorarioSueno = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Niveles de estrés"))
                        cmbNivelEstres = (JComboBox<String>) inputComponent;
                    if (fieldNames[i].equals("Hábitos alimenticios"))
                        txtHabitoAlimenticio = (JTextField) inputComponent;
                } else if (title.equals("Consumo de Líquidos")) {
                    if (fieldNames[i].equals("Tipos de líquidos consumidos"))
                        txtTipoLiquidoConsumido = (JTextField) inputComponent;
                    if (fieldNames[i].equals("Cantidad de líquidos consumidos"))
                        txtCantidadLiquido = (JTextField) inputComponent;
                } else if (title.equals("Disposición")) {
                    if (fieldNames[i].equals("Alguna barrera para seguir el plan"))
                        txtBarreraAlimenticia = (JTextField) inputComponent;
                }
            }

            gbc.gridx = (i % 2) * 2;
            gbc.gridy = i / 2;
            gbc.weightx = 0.0;
            gbc.anchor = GridBagConstraints.EAST;
            fieldsPanel.add(label, gbc);

            gbc.gridx = (i % 2) * 2 + 1;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.WEST;
            // CORRECCIÓN: Se agrega la comprobación de null aquí, aunque la lógica superior
            // ya minimiza el riesgo.
            if (inputComponent != null) {
                fieldsPanel.add(inputComponent, gbc);
            }
        }

        sectionPanel.add(fieldsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createPersonalInfoPanel(String title, String[] fieldNames) {
        JPanel panel = createSectionPanel(title, fieldNames);
        return panel;
    }

    private JPanel createMedicalHistoryPanel(String title, String[] fieldNames) {
        JPanel panel = createSectionPanel(title, fieldNames);
        return panel;
    }

    private JPanel createFoodHistoryPanel(String title, String[] fieldNames) {
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

        txtAlergiasField = createTextField();
        txtPreferenciaComidaField = createTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[0]), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(txtAlergiasField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[1]), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(txtPreferenciaComidaField, gbc);

        sectionPanel.add(fieldsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createPhysicalActivityPanel(String title, String[] fieldNames) {
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

        cmbNivelActividad = createComboBox(new String[] { "Sedentario", "Actividad Ligera", "Actividad Moderada",
                "Actividad Activa", "Actividad Muy Activa" });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[0]), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(cmbNivelActividad, gbc);

        sectionPanel.add(fieldsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createLifestylePanel(String title, String[] fieldNames) {
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

        txtHorarioSueno = createTextField();
        cmbNivelEstres = createComboBox(new String[] { "Bajo", "Medio", "Alto" });
        txtHabitoAlimenticio = createTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[0]), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(txtHorarioSueno, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[1]), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(cmbNivelEstres, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[2]), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(txtHabitoAlimenticio, gbc);

        sectionPanel.add(fieldsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createLiquidConsumptionPanel(String title, String[] fieldNames) {
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

        txtTipoLiquidoConsumido = createTextField();
        txtCantidadLiquido = createTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[0]), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(txtTipoLiquidoConsumido, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[1]), gbc);
        gbc.gridx = 3;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(txtCantidadLiquido, gbc);

        sectionPanel.add(fieldsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createNutritionalGoalsPanel(String title, String[] fieldNames) {
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

        cmbRazonConsulta = createComboBox(
                new String[] { "Perder % de grasa", "Ganar masa muscular", "Recomposición Corporal", "Mejorar salud" });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[0]), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(cmbRazonConsulta, gbc);

        sectionPanel.add(fieldsPanel, BorderLayout.CENTER);
        return sectionPanel;
    }

    private JPanel createDispositionPanel(String title, String[] fieldNames) {
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

        txtBarreraAlimenticia = createTextField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        fieldsPanel.add(createLabel(fieldNames[0]), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(txtBarreraAlimenticia, gbc);

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

    public void clearAllFields() {
        JTextField[] textFields = {
                txtNombreField, txtApellidoField, txtPreferenciaComidaField, txtCorreoField, txtTelefonoField,
                pesoField, alturaField, txtCondicionesMedicasField, txtMedicacionField, txtAlergiasField,
                txtHorarioSueno, txtHistorialCirugias, txtHabitoAlimenticio, txtTipoLiquidoConsumido,
                txtCantidadLiquido, txtBarreraAlimenticia, txtProteinas, txtCarbohidratos, txtLipidos,
                txtCaloriasTotales
        };

        for (JTextField field : textFields) {
            if (field != null) {
                field.setText("");
            }
        }

        JComboBox<?>[] comboBoxes = {
                cmbSexo, cmbNivelActividad, cmbRazonConsulta, cmbNivelEstres
        };

        for (JComboBox<?> cmb : comboBoxes) {
            if (cmb != null && cmb.getItemCount() > 0) {
                cmb.setSelectedIndex(0);
            }
        }

        if (fechaNacimientoChooser instanceof JComponent) {
            try {
                Class.forName("com.toedter.calendar.JDateChooser")
                        .getMethod("setDate", Date.class)
                        .invoke(fechaNacimientoChooser, (Date) null);
            } catch (Exception e) {
                if (fechaNacimientoChooser instanceof JTextField) {
                    ((JTextField) fechaNacimientoChooser).setText("");
                }
            }
        }
    }

    public JPanel getPanel() {
        return this;
    }

    public JTextField getTxtNombreField() {
        return txtNombreField;
    }

    public JTextField getTxtApellidoField() {
        return txtApellidoField;
    }

    public Object getFechaNacimientoChooser() {
        return fechaNacimientoChooser;
    }

    public JComboBox<String> getCmbSexo() {
        return cmbSexo;
    }

    public JTextField getTxtCorreoField() {
        return txtCorreoField;
    }

    public JTextField getTxtTelefonoField() {
        return txtTelefonoField;
    }

    public JTextField getPesoField() {
        return pesoField;
    }

    public JTextField getAlturaField() {
        return alturaField;
    }

    public JTextField getTxtCondicionesMedicasField() {
        return txtCondicionesMedicasField;
    }

    public JTextField getTxtMedicacionField() {
        return txtMedicacionField;
    }

    public JTextField getTxtAlergiasField() {
        return txtAlergiasField;
    }

    public JTextField getPreferenciaComidaField() {
        return txtPreferenciaComidaField;
    }

    public JTextField getTxtHorarioSueno() {
        return txtHorarioSueno;
    }

    public JTextField getTxtHistorialCirugias() {
        return txtHistorialCirugias;
    }

    public JComboBox<String> getCmbNivelEstres() {
        return cmbNivelEstres;
    }

    public JTextField getTxtHabitoAlimenticio() {
        return txtHabitoAlimenticio;
    }

    public JTextField getTxtTipoLiquidoConsumido() {
        return txtTipoLiquidoConsumido;
    }

    public JTextField getTxtCantidadLiquido() {
        return txtCantidadLiquido;
    }

    public JTextField getTxtBarreraAlimenticia() {
        return txtBarreraAlimenticia;
    }

    public JButton getBtnCalcular() {
        return btnCalcular;
    }

    public JTextField getTxtProteinas() {
        return txtProteinas;
    }

    public JTextField getTxtCarbohidratos() {
        return txtCarbohidratos;
    }

    public JTextField getTxtLipidos() {
        return txtLipidos;
    }

    public JTextField getTxtCaloriasTotales() {
        return txtCaloriasTotales;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public JComboBox<String> getCmbNivelActividad() {
        return cmbNivelActividad;
    }

    public JComboBox<String> getCmbRazonConsulta() {
        return cmbRazonConsulta;
    }

    public String getViewAnteriorTag() {
        return viewAnteriorTag;
    }

    public void setViewAnteriorTag(String viewAnteriorTag) {
        this.viewAnteriorTag = viewAnteriorTag;
    }

    public String getModoActual() {
        return modoActual;
    }

    public void setModoActual(String modoActual) {
        this.modoActual = modoActual;
    }

    public JButton getBtnPatients() {
        return btnPatients;
    }

    public void setBtnPatients(JButton btnPatients) {
        this.btnPatients = btnPatients;
    }

    public JButton getBtnExpedients() {
        return btnExpedients;
    }

    public void setBtnExpedients(JButton btnExpedients) {
        this.btnExpedients = btnExpedients;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public void setBtnLogout(JButton btnLogout) {
        this.btnLogout = btnLogout;
    }

}
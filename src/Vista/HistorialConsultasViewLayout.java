package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Layout gráfico para la vista de historial de consultas.
 * Define la estructura visual completa: encabezados,
 * buscador de fecha, tabla de consultas y botones de acción.
 */
public class HistorialConsultasViewLayout extends JPanel {

    private static final Color HEADER_COLOR = new Color(44, 54, 73);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = Color.BLACK;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    private JTable tableConsultas;

    private JTextField txtBuscar;
    
    private JButton btnAgregar;

    private JButton btnEliminar;

    private JButton btnLogout;

    private JButton btnPacientes;

    private JLabel lblNombrePaciente;

    /**
     * Constructor principal que crea y acomoda todos los elementos gráficos del layout.
     */
    public HistorialConsultasViewLayout() {
  
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Encabezado superior con logo, navegación y botón de cierre de sesión
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Colocar logo a la izquierda del encabezado
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");

        // Validación de carga de imagen para evitar errores si el archivo no existe
        if (iconLogo.getIconWidth() > 0) {
            Image scaledLogo = iconLogo.getImage()
                .getScaledInstance(140, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledLogo));
        } else {
            lblLogo.setText("NutriNube");
            lblLogo.setForeground(Color.WHITE);
        }

        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Botón "Pacientes" en el centro del header
        btnPacientes = new JButton("Pacientes");
        btnPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnPacientes.setForeground(Color.WHITE);
        btnPacientes.setBackground(HEADER_COLOR);
        btnPacientes.setBorderPainted(false);
        btnPacientes.setFocusPainted(false);
        btnPacientes.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));
        btnPacientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel centerMenu = new JPanel();
        centerMenu.setBackground(HEADER_COLOR);
        centerMenu.add(btnPacientes);

        // Botón de logout alineado a la derecha del encabezado
        btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(HEADER_COLOR);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(19, 0, 0, 14));
        btnLogout.setMargin(new Insets(0, 0, 0, 0));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Intentar cargar icono del botón de logout
        ImageIcon logoutIcon = null;
        try {
            java.net.URL iconUrl = getClass().getResource("/salir_logo.png");
            if (iconUrl != null) {
                logoutIcon = new ImageIcon(iconUrl);
            } else {
                logoutIcon = new ImageIcon("salir_logo.png");
            }

            // Si existe icono, se muestra sin área de botón visible
            if (logoutIcon.getIconWidth() > 0) {
                Image img = logoutIcon.getImage()
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                btnLogout.setIcon(new ImageIcon(img));
                btnLogout.setToolTipText("Log Out");
                btnLogout.setBorderPainted(false);
                btnLogout.setContentAreaFilled(false);
                btnLogout.setOpaque(false);
            } else {
                btnLogout.setText("Log Out");
            }

        } catch (Exception ex) {
            // Fallback si ocurre cualquier error
            btnLogout.setText("Log Out");
        }

        // Panel derecho del header para alinear botón a la orilla
        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        eastPanel.setOpaque(false);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        eastPanel.add(btnLogout);

        headerPanel.add(centerMenu, BorderLayout.CENTER);
        headerPanel.add(eastPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Panel principal que contiene buscador, tabla y botones inferiores
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Título con nombre del paciente
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setBackground(BACKGROUND_COLOR);

        JLabel lblTitulo = new JLabel("Historial de Consultas: ");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(TEXT_COLOR);

        lblNombrePaciente = new JLabel("Cargando...");
        lblNombrePaciente.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblNombrePaciente.setForeground(TEXT_COLOR);

        titlePanel.add(lblTitulo);
        titlePanel.add(lblNombrePaciente);

        mainPanel.add(titlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Panel superior de búsqueda por fecha
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));

        // Campo de búsqueda con placeholder y validación
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

        searchPanel.setMaximumSize(new Dimension(200, 30));
        searchPanel.setPreferredSize(new Dimension(200, 30));

        txtBuscar = new JTextField("AAAA-MM-DD");

        // Lógica del placeholder del campo de búsqueda
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtBuscar.getText().equals("AAAA-MM-DD")) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    txtBuscar.setText("AAAA-MM-DD");
                    txtBuscar.setForeground(Color.GRAY);
                }
            }
        });

        // Listener de documento para filtrar en tiempo real
        txtBuscar.getDocument().addDocumentListener(
            new javax.swing.event.DocumentListener() {

            private void filtrar() {
                String texto = txtBuscar.getText().trim();

                // Ignorar valores vacíos o placeholder
                if (texto.equals("") || texto.equals("Buscar fecha")
                    || texto.equals("Buscar") || texto.equals("Buscar fecha")) {
                    firePropertyChange("filtrarFecha", null, "");
                    return;
                }

                // Validar formato correcto YYYY-MM-DD
                if (!texto.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return;
                }

                firePropertyChange("filtrarFecha", null, texto);
            }

            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
        });

        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(null);
        txtBuscar.setForeground(Color.GRAY);

        searchPanel.add(new JLabel(" 🔍 "), BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);

        topPanel.add(searchPanel, BorderLayout.EAST);

        mainPanel.add(topPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(200, 30)));

        // Configuración de la tabla del historial
        DefaultTableModel modelo = new DefaultTableModel() {
             @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("Clave Consulta");
        modelo.addColumn("Fecha");
        modelo.addColumn("Calorías Calculadas");

        tableConsultas = new JTable(modelo);

        tableConsultas.setRowHeight(28);
        tableConsultas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableConsultas.setShowGrid(false);
        tableConsultas.setIntercellSpacing(new Dimension(0, 0));
        tableConsultas.setFillsViewportHeight(true);
        tableConsultas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        tableConsultas.getTableHeader().setReorderingAllowed(false);
        tableConsultas.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollTabla = new JScrollPane(tableConsultas);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(800, 250));
        
        mainPanel.add(scrollTabla);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Panel de botones inferiores
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnAgregar = createBlackButton("Agregar Consulta");
        btnEliminar = createBlackButton("Eliminar");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEliminar);

        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Crea un botón estilizado en negro utilizado en la parte inferior del layout.
     * @param text texto que aparecerá en el botón.
     * @return botón configurado.
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

    public JTable getTablaConsultas() {
        return tableConsultas;
    }

    public void setTableConsultas(JTable tableConsultas) {
        this.tableConsultas = tableConsultas;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JButton getBtnLogout() {
        return btnLogout;
    }

    public void setBtnLogout(JButton btnLogout) {
        this.btnLogout = btnLogout;
    }

    public JButton getBtnPacientes() {
        return btnPacientes;
    }

    public void setBtnPacientes(JButton btnPacientes) {
        this.btnPacientes = btnPacientes;
    }

    public JPanel getPanel() {
        return this;
    }
    
    /**
     * Establece dinámicamente el nombre del paciente mostrado en el encabezado.
     * @param nombre nombre completo del paciente.
     */
    public void setNombrePaciente(String nombre) {
        this.lblNombrePaciente.setText(nombre);
    }
}

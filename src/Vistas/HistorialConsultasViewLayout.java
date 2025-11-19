package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistorialConsultasViewLayout extends JPanel {

    // Declaración de constantes de color.
    private static final Color HEADER_COLOR = new Color(44, 54, 73);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = Color.BLACK;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    // Declaración de componentes de la interfaz.
    private JTable tableHistorial;
    private JTextField txtBuscarFecha;
    private JButton btnAgregarConsulta;
    private JButton btnEliminar;
    private JButton btnLogout;
    private JButton btnPacientes;
    private JLabel lblNombrePaciente;

    public HistorialConsultasViewLayout() {
        // Configuración del layout principal como BorderLayout.
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // --- Configuración del Panel de Cabecera (Header) ---

        // Creación del panel de cabecera.
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Configuración y escalado de la etiqueta del logo.
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");
        Image scaledLogo = iconLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledLogo));
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Configuración del botón "Pacientes" (navegación central).
        btnPacientes = new JButton("Pacientes");
        btnPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnPacientes.setForeground(Color.WHITE);
        btnPacientes.setBackground(HEADER_COLOR);
        btnPacientes.setBorderPainted(false);
        btnPacientes.setFocusPainted(false);
        btnPacientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Panel central para los botones de navegación (actualmente solo 'Pacientes').
        JPanel centerMenu = new JPanel();
        centerMenu.setBackground(HEADER_COLOR);
        centerMenu.add(btnPacientes);

        // Configuración del botón de Logout ("Salir").
        btnLogout = new JButton();
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(HEADER_COLOR);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLogout.setMargin(new Insets(0, 0, 0, 0));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setText("Salir");

        // Panel derecho para el botón de Logout.
        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        eastPanel.setOpaque(false);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        eastPanel.add(btnLogout);
        headerPanel.add(eastPanel, BorderLayout.EAST);

        // Adición del menú central y del panel de cabecera a la vista.
        headerPanel.add(centerMenu, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // --- Configuración del Panel Central de Contenido ---

        // Creación del panel principal con BoxLayout vertical.
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Panel para agrupar el título y el nombre del paciente.
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setBackground(BACKGROUND_COLOR);

        // Configuración del título principal.
        JLabel lblTitulo = new JLabel("Historial de Consultas:");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(TEXT_COLOR);

        // Etiqueta dinámica para mostrar el nombre del paciente.
        lblNombrePaciente = new JLabel("");
        lblNombrePaciente.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblNombrePaciente.setForeground(TEXT_COLOR);

        titlePanel.add(lblTitulo);
        titlePanel.add(lblNombrePaciente);

        mainPanel.add(titlePanel);
        // Espaciador entre título y controles.
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Panel superior para la barra de búsqueda.
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Configuración del Campo de Búsqueda ---
        
        // Panel contenedor para la barra de búsqueda de fecha.
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        
        // Campo de texto para buscar por fecha.
        txtBuscarFecha = new JTextField(" Buscar fecha");
        txtBuscarFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscarFecha.setBorder(null);
        txtBuscarFecha.setForeground(Color.GRAY);
        
        // Ícono de lupa y campo de texto.
        searchPanel.add(new JLabel(" 🔍 "), BorderLayout.WEST);
        searchPanel.add(txtBuscarFecha, BorderLayout.CENTER);
        searchPanel.setPreferredSize(new Dimension(200, 30));

        // La barra de búsqueda se coloca a la derecha del topPanel.
        topPanel.add(searchPanel, BorderLayout.EAST);
        mainPanel.add(topPanel);
        // Espaciador entre búsqueda y tabla.
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Configuración de la Tabla de Historial ---

        // Definición de las columnas de la tabla de consultas.
        String[] columnas = { "Clave", "Fecha Visita", "Calorías" };
        Object[][] datos = {};

        // Creación del modelo y la tabla.
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        tableHistorial = new JTable(modelo);
        
        // Estilización y configuración de la tabla.
        tableHistorial.setRowHeight(28);
        tableHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableHistorial.setShowGrid(false);
        tableHistorial.setIntercellSpacing(new Dimension(0, 0));
        tableHistorial.setFillsViewportHeight(true);
        tableHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableHistorial.getTableHeader().setReorderingAllowed(false);
        tableHistorial.getTableHeader().setResizingAllowed(false);

        // JScrollPane para desplazamiento de la tabla.
        JScrollPane scrollTabla = new JScrollPane(tableHistorial);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(800, 250));

        mainPanel.add(scrollTabla);

        // Espaciador entre tabla y botones de acción.
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- Configuración del Panel de Botones de Acción ---

        // Panel para los botones Agregar y Eliminar consulta.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creación de botones usando el método auxiliar.
        btnAgregarConsulta = createBlackButton("Agregar Consulta");
        btnEliminar = createBlackButton("Eliminar");

        buttonPanel.add(btnAgregarConsulta);
        buttonPanel.add(btnEliminar);

        mainPanel.add(buttonPanel);

        // Adición del panel principal al centro de la vista.
        add(mainPanel, BorderLayout.CENTER);
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
    
    // --- Métodos Getters y Setter ---
    
    public JPanel getPanel() {
        return this;
    }
    
    public JTable getTableHistorial() {
        return tableHistorial;
    }
    
    public JButton getBtnAgregarConsulta() {
        return btnAgregarConsulta;
    }
    
    public JButton getBtnEliminar() {
        return btnEliminar;
    }
    
    public JButton getBtnLogout() {
        return btnLogout;
    }
    
    public JButton getBtnPacientes() {
        return btnPacientes;
    }
    
    public void setNombrePaciente(String nombre) {
        lblNombrePaciente.setText(nombre);
    }
}
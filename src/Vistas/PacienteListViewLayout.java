package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PacienteListViewLayout extends JPanel {

    // Declaración de constantes de color para un tema consistente.
    private static final Color HEADER_COLOR = new Color(44, 54, 73);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = Color.BLACK;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    // Declaración de componentes principales de la interfaz.
    private JTable tblPacientes;
    private JTextField txtBuscar;
    private JButton btnAnadirPaciente;
    private JButton btnEliminarPaciente;
    private JButton btnLogout;

    public PacienteListViewLayout() {
        // Configuración del layout principal como BorderLayout.
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // --- Configuración del Panel de Cabecera (Header) ---

        // Creación del panel de cabecera con BorderLayout.
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));

        // Configuración de la etiqueta para el logo.
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        
        // Carga y escalado de la imagen del logo.
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");
        Image scaledLogo = iconLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledLogo));
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Panel central sin contenido (para empujar el logo y el botón a los extremos).
        JPanel centerMenu = new JPanel();
        centerMenu.setBackground(HEADER_COLOR);
        headerPanel.add(centerMenu, BorderLayout.CENTER);

        // Configuración del botón de Logout (Cerrar Sesión).
        btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(HEADER_COLOR);
        // Eliminación de bordes y enfoque para un estilo limpio.
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        headerPanel.add(btnLogout, BorderLayout.EAST);

        // Adición del panel de cabecera a la parte superior (NORTH) del layout principal.
        add(headerPanel, BorderLayout.NORTH);

        // --- Configuración del Panel Central de Contenido ---

        // Creación del panel central que contiene la lista y los controles.
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        // Uso de BoxLayout vertical para apilar los elementos.
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Configuración del título "Pacientes".
        JLabel lblTitulo = new JLabel("Pacientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(TEXT_COLOR);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblTitulo);

        // Espaciador rígido entre el título y el panel de controles.
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Panel superior para la barra de búsqueda y los botones de acción.
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- Configuración del Campo de Búsqueda ---
        
        // Panel contenedor para la barra de búsqueda (para simular un campo con borde).
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        
        // Campo de texto para la búsqueda con texto placeholder inicial.
        txtBuscar = new JTextField(" Buscar paciente");
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(null);
        txtBuscar.setForeground(Color.GRAY);
        
        // Etiqueta de ícono de lupa.
        searchPanel.add(new JLabel(" 🔍 "), BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);
        searchPanel.setPreferredSize(new Dimension(200, 10)); // Ajuste de tamaño
        topPanel.add(searchPanel, BorderLayout.WEST);

        // --- Configuración del Panel de Botones ---

        // Panel FlowLayout para los botones Añadir y Eliminar.
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        // Creación y estilización de los botones de acción.
        btnAnadirPaciente = createBlackButton("Añadir Paciente");
        btnEliminarPaciente = createBlackButton("Eliminar Paciente");

        buttonPanel.add(btnAnadirPaciente);
        buttonPanel.add(btnEliminarPaciente);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Adición del panel de controles superior al panel principal.
        mainPanel.add(topPanel);

        // Espaciador rígido entre controles y la tabla.
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Configuración de la Tabla de Pacientes ---

        // Definición de las columnas de la tabla.
        String[] columnas = { "Clave", "Nombre", "Última Visita" };
        Object[][] datos = {};

        // Creación del modelo y la tabla.
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        tblPacientes = new JTable(modelo);
        
        // Estilización de las filas y fuentes.
        tblPacientes.setRowHeight(28);
        tblPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblPacientes.setShowGrid(false);
        tblPacientes.setIntercellSpacing(new Dimension(0, 0));
        tblPacientes.setFillsViewportHeight(true);
        
        // Estilización y configuración de la cabecera de la tabla.
        tblPacientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblPacientes.getTableHeader().setReorderingAllowed(false);
        tblPacientes.getTableHeader().setResizingAllowed(false);

        // Envolver la tabla en un JScrollPane para permitir el desplazamiento.
        JScrollPane scrollTabla = new JScrollPane(tblPacientes);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(800, 300));

        // Adición de la tabla al panel principal.
        mainPanel.add(scrollTabla);

        // Adición del panel principal al centro del layout de la vista.
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Método auxiliar para crear botones negros con estilo uniforme.
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
    
    // --- Métodos Getters ---

    public JPanel getPanel() {
        return this;
    }
    
    public JTable getTblPacientes() {
        return tblPacientes;
    }
    
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }
    
    public JButton getBtnAnadirPaciente() {
        return btnAnadirPaciente;
    }
    
    public JButton getBtnEliminarPaciente() {
        return btnEliminarPaciente;
    }
    
    public JButton getBtnLogout() {
        return btnLogout;
    }
}
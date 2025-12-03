package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Layout gráfico para la vista de lista de pacientes.
 * Define toda la estructura visual de la pantalla, incluyendo:
 * encabezado, buscador, botones y tabla de pacientes.
 * Esta clase no contiene lógica de negocio; solo construcción de interfaz.
 */
public class PacienteListViewLayout extends JPanel {

    // Constantes de estilo compartidas con otras vistas
    private static final Color HEADER_COLOR = new Color(44, 54, 73);
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = Color.BLACK;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    // Componentes principales de la vista
    private JTable tablePacientes;
    private JTextField txtBuscar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnLogout;

    // Etiqueta donde se mostrará el nombre del nutriólogo activo
    private JLabel lblSubtituloNutriologo;

    /**
     * Constructor que inicializa y arma todo el layout de la vista.
     * Configura encabezado, buscador, botones y la tabla.
     */
    public PacienteListViewLayout() {

        setSize(900, 600);
        setLayout(new BorderLayout());

        // Encabezado superior
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));

        // Logo de la aplicación
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");

        if (iconLogo.getIconWidth() > 0) {
            Image scaledLogo = iconLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledLogo));
        } else {
            lblLogo.setText("NutriNube");
            lblLogo.setForeground(Color.WHITE);
        }

        headerPanel.add(lblLogo, BorderLayout.WEST);

        JPanel centerMenu = new JPanel();
        centerMenu.setBackground(HEADER_COLOR);
        headerPanel.add(centerMenu, BorderLayout.CENTER);

        // Botón Log Out
        btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(HEADER_COLOR);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        headerPanel.add(btnLogout, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Título de la vista
        JLabel lblTitulo = new JLabel("Pacientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(TEXT_COLOR);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblTitulo);

        // Subtítulo con el nombre del nutriólogo
        lblSubtituloNutriologo = new JLabel("Nutriólogo: ");
        lblSubtituloNutriologo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtituloNutriologo.setForeground(Color.GRAY);
        lblSubtituloNutriologo.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblSubtituloNutriologo);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Panel de buscador y botones
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Cuadro de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

        txtBuscar = new JTextField(" Buscar paciente");
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(null);
        txtBuscar.setForeground(Color.GRAY);

        searchPanel.add(new JLabel(" 🔍 "), BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);
        searchPanel.setPreferredSize(new Dimension(200, 30));

        topPanel.add(searchPanel);

        // Botones de acciones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnAgregar = createBlackButton("Añadir Paciente");
        btnEliminar = createBlackButton("Eliminar Paciente");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEliminar);

        topPanel.add(buttonPanel);
        mainPanel.add(topPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Tabla de pacientes
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabla de solo lectura
            }
        };

        modelo.addColumn("Clave");
        modelo.addColumn("Nombre Completo");
        modelo.addColumn("Última Visita");

        tablePacientes = new JTable(modelo);
        tablePacientes.setRowHeight(28);
        tablePacientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablePacientes.setShowGrid(false);
        tablePacientes.setIntercellSpacing(new Dimension(0, 0));
        tablePacientes.setFillsViewportHeight(true);
        tablePacientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablePacientes.getTableHeader().setReorderingAllowed(false);
        tablePacientes.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollTabla = new JScrollPane(tablePacientes);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(800, 300));

        mainPanel.add(scrollTabla);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Crea un botón estilizado con el tema negro principal de la aplicación.
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

    /** @return tabla donde se muestran los pacientes. */
    public JTable getTablaPacientes() {
        return tablePacientes;
    }

    public void setTablaPacientes(JTable tablePacientes) {
        this.tablePacientes = tablePacientes;
    }

    /** @return texto actual del campo de búsqueda. */
    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    /** @return botón para agregar pacientes. */
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    /** @return botón para eliminar pacientes. */
    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    /** @return botón de cierre de sesión. */
    public JButton getBtnLogout() {
        return btnLogout;
    }

    public void setBtnLogout(JButton btnLogout) {
        this.btnLogout = btnLogout;
    }

    /** @return panel principal ya construido. */
    public JPanel getPanel() {
        return this;
    }

    /**
     * Actualiza el subtítulo que indica qué nutriólogo está actualmente logueado.
     */
    public void setNombreNutriologo(String nombre) {
        this.lblSubtituloNutriologo.setText("Nutriólogo: " + nombre);
    }
}

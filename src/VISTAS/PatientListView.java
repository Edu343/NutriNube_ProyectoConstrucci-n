import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientListView extends JFrame {

    // Colores principales
    private static final Color HEADER_COLOR = new Color(44, 54, 73); // Azul oscuro
    private static final Color TEXT_COLOR = Color.BLACK;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = Color.BLACK;
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    // Componentes
    private JTable tablePacientes;
    private JTextField txtBuscar;
    private JButton btnAgregar;
    private JButton btnEliminar;
    private JButton btnLogout;

    public PatientListView() {
        setTitle("Gestión de Pacientes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Header superior ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));

        // Logo izquierdo
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");
        Image scaledLogo = iconLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledLogo));
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Botón "Pacientes" con ícono de estetoscopio

        JPanel centerMenu = new JPanel();
        centerMenu.setBackground(HEADER_COLOR);

        headerPanel.add(centerMenu, BorderLayout.CENTER);

        // Botón Log Out (misma apariencia que btnPacientes)
        btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(HEADER_COLOR);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        headerPanel.add(btnLogout, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- Panel principal ---
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // vertical layout

        // Título
        JLabel lblTitulo = new JLabel("Pacientes");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(TEXT_COLOR);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblTitulo);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25))); // espacio debajo del título

        // Panel superior (buscador + botones)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Buscador
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
        topPanel.add(searchPanel, BorderLayout.WEST);

        // Botones a la derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnAgregar = createBlackButton("Añadir Paciente");
        btnEliminar = createBlackButton("Eliminar Paciente");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEliminar);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(topPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // más espacio antes de la tabla

        // --- Tabla de pacientes ---
        String[] columnas = { "Clave", "Nombre", "Última Visita" };
        Object[][] datos = {
                { "FIG-123", "Jorge Vazquez", "Dec 5" },
                { "FIG-122", "Angel Del Rio", "Dec 5" },
                { "FIG-120", "Manuel Matos", "Dec 5" },
                { "FIG-119", "Mauricio Moreno", "Dec 5" },
                { "FIG-118", "Peter Parker", "Dec 5" }
        };

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        tablePacientes = new JTable(modelo);
        tablePacientes.setRowHeight(28);
        tablePacientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablePacientes.setShowGrid(false);
        tablePacientes.setIntercellSpacing(new Dimension(0, 0));
        tablePacientes.setFillsViewportHeight(true);
        tablePacientes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // 🔒 Fijar columnas
        tablePacientes.getTableHeader().setReorderingAllowed(false);
        tablePacientes.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollTabla = new JScrollPane(tablePacientes);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(800, 300)); // altura inicial estable

        mainPanel.add(scrollTabla);

        add(mainPanel, BorderLayout.CENTER);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PatientListView view = new PatientListView();
            view.setVisible(true);
        });
    }
}

package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

//vista de la lista de pacientes del nutriologo
public class PacienteListViewLayout extends JPanel {

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

    // NUEVO: Etiqueta para el nombre del nutriólogo
    private JLabel lblSubtituloNutriologo;

    public PacienteListViewLayout() {

        setSize(900, 600);

        setLayout(new BorderLayout());

        // --- Header superior ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));

        // Logo izquierdo
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");
        // Verificación básica para evitar errores si no carga la imagen
        if (iconLogo.getIconWidth() > 0) {
            Image scaledLogo = iconLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledLogo));
        } else {
            lblLogo.setText("NutriNube");
            lblLogo.setForeground(Color.WHITE);
        }
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Centro (vacío o menú)
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

        // NUEVO: Subtítulo para el Nutriólogo
        lblSubtituloNutriologo = new JLabel("Nutriólogo: ");
        lblSubtituloNutriologo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtituloNutriologo.setForeground(Color.GRAY);
        lblSubtituloNutriologo.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblSubtituloNutriologo);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25))); // espacio debajo del título

        // Panel superior (buscador + botones)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
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

        topPanel.add(searchPanel);

        // Botones a la derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnAgregar = createBlackButton("Añadir Paciente");
        btnEliminar = createBlackButton("Eliminar Paciente");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEliminar);

        topPanel.add(buttonPanel);

        mainPanel.add(topPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30))); // más espacio antes de la tabla

        // NUEVO: Modelo de tabla con columnas definidas
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Esto hace que todas las celdas de la tabla NO sean editables.
                return false;
            }
        };

        // Agregamos las columnas para que la tabla se dibuje correctamente
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

    public JTable getTablaPacientes() {
        return tablePacientes;
    }

    public void setTablePacientes(JTable tablePacientes) {
        this.tablePacientes = tablePacientes;
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

    public JPanel getPanel() {
        return this;
    }

    // NUEVO: Método para actualizar el nombre del nutriólogo desde la vista
    public void setNombreNutriologo(String nombre) {
        this.lblSubtituloNutriologo.setText("Nutriólogo: " + nombre);
    }
}
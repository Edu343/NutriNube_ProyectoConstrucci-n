package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Modelo.Core.ViewLayout;

import java.awt.*;

/**
 * Layout gráfico para la vista de lista de pacientes.
 * Define toda la estructura visual de la pantalla, incluyendo:
 * encabezado, buscador, botones y tabla de pacientes.
 * Esta clase no contiene lógica de negocio; solo construcción de interfaz.
 */
public class PacienteListViewLayout extends ViewLayout {

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
     */
    public PacienteListViewLayout() {

        setSize(900, 600);
        setLayout(new BorderLayout());

        // Encabezado superior
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));

        // Logo de la aplicación
        JLabel lblLogo = crearLogo();

        headerPanel.add(lblLogo, BorderLayout.WEST);

        JPanel centerMenu = new JPanel();
        centerMenu.setBackground(HEADER_COLOR);
        headerPanel.add(centerMenu, BorderLayout.CENTER);

        // Botón Log Out
        btnLogout = crearBotonLogout();
        btnLogout.setMargin(new Insets(0, 0, 0, 10));

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
        txtBuscar = new JTextField(" Buscar paciente");

        agregarPlaceholderBehavior(txtBuscar, " Buscar paciente");

        JPanel searchPanel = crearPanelBusqueda(txtBuscar);

        topPanel.add(searchPanel);

        // Botones de acciones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnAgregar = crearBotonNegro("Añadir Paciente");
        btnEliminar = crearBotonNegro("Eliminar Paciente");

        buttonPanel.add(btnAgregar);
        buttonPanel.add(btnEliminar);

        topPanel.add(buttonPanel);
        mainPanel.add(topPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Tabla de pacientes
        DefaultTableModel modelo = crearModeloTablaNoEditable();

        modelo.addColumn("Clave");
        modelo.addColumn("Nombre Completo");
        modelo.addColumn("Última Visita");

        tablePacientes = new JTable(modelo);
        configurarTabla(tablePacientes);

        JScrollPane scrollTabla = new JScrollPane(tablePacientes);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(800, 300));

        mainPanel.add(scrollTabla);

        add(mainPanel, BorderLayout.CENTER);
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
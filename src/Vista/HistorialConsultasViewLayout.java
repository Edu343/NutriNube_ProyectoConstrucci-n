package Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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

    public HistorialConsultasViewLayout() {
  
        setSize(900, 600);

        setLayout(new BorderLayout());

        // --- Header superior ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(HEADER_COLOR);
        headerPanel.setPreferredSize(new Dimension(900, 60));
        // Quitar bordes del header para que los componentes puedan llegar a los
        // extremos (pegados a la ventana)
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Logo izquierdo
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        ImageIcon iconLogo = new ImageIcon("NutriNube.png");
        Image scaledLogo = iconLogo.getImage().getScaledInstance(140, 100, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(scaledLogo));
        headerPanel.add(lblLogo, BorderLayout.WEST);

        // Botón "Pacientes" con ícono de estetoscopio

        btnPacientes = new JButton("Pacientes");
        btnPacientes.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnPacientes.setForeground(Color.WHITE);
        btnPacientes.setBackground(HEADER_COLOR);
        btnPacientes.setBorderPainted(false);
        btnPacientes.setFocusPainted(false);
        btnPacientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel centerMenu = new JPanel();
        centerMenu.setBackground(HEADER_COLOR);
        // Añadimos botones centrados (Pacientes y Log Out) en la misma línea
        centerMenu.add(btnPacientes);

        // Botón Log Out al borde derecho (pegado al extremo) — mostrar solo icono
        btnLogout = new JButton();
        btnLogout.setFocusPainted(false);
        btnLogout.setBackground(HEADER_COLOR);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        // Quitar padding interno/borde para que el botón quede lo más pegado posible
        btnLogout.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        btnLogout.setMargin(new Insets(0, 0, 0, 0));
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Intentar cargar 'salir_logo.png' desde classpath primero, si no existe
        // intentar desde ruta relativa; si no se encuentra, mostrar texto "Salir".
        ImageIcon logoutIcon = null;
        try {
            java.net.URL iconUrl = getClass().getResource("/salir_logo.png");
            if (iconUrl != null) {
                logoutIcon = new ImageIcon(iconUrl);
            } else {
                // fallback a archivo en ruta relativa al working dir
                logoutIcon = new ImageIcon("salir_logo.png");
            }
            if (logoutIcon.getIconWidth() > 0) {
                Image img = logoutIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                btnLogout.setIcon(new ImageIcon(img));
                btnLogout.setToolTipText("Log Out");
                // Hacer el botón visualmente transparente para que solo se vea el icono
                btnLogout.setBorderPainted(false);
                btnLogout.setContentAreaFilled(false);
                btnLogout.setOpaque(false);
            } else {
                btnLogout.setText("Log Out");
            }
        } catch (Exception ex) {
            // En caso de error inesperado, mostrar texto alternativo
            btnLogout.setText("Log Out");
        }

        // Colocamos el botón dentro de un panel EAST sin márgenes y con FlowLayout
        // sin gaps para garantizar que el botón quede pegado al borde derecho.
        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        eastPanel.setOpaque(false);
        eastPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        eastPanel.add(btnLogout);
        headerPanel.add(eastPanel, BorderLayout.EAST);

        headerPanel.add(centerMenu, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // --- Panel principal ---
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // --- Título doble ---
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setBackground(BACKGROUND_COLOR);

        JLabel lblTitulo = new JLabel("Historial de Consultas:");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(TEXT_COLOR);

        JLabel lblNombrePaciente = new JLabel("Eduardo Matos"); // placeholder dinámico
        lblNombrePaciente.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblNombrePaciente.setForeground(TEXT_COLOR);

        titlePanel.add(lblTitulo);
        titlePanel.add(lblNombrePaciente);

        mainPanel.add(titlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- Panel superior con buscador ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        // 🚨 CORRECCIÓN CLAVE 1: Limitar la altura MÁXIMA del topPanel
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); 
        topPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 30));

        // Buscador pequeño con placeholder
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        searchPanel.setMaximumSize(new Dimension(200, 30)); 
        searchPanel.setPreferredSize(new Dimension(200, 30));
        txtBuscar = new JTextField(" Buscar fecha");
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(null);
        txtBuscar.setForeground(Color.GRAY);
        searchPanel.add(new JLabel(" 🔍 "), BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);
        searchPanel.setPreferredSize(new Dimension(200, 30));

        topPanel.add(searchPanel, BorderLayout.EAST);
        mainPanel.add(topPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(200, 30)));

        // --- Tabla de historial ---
        String[] columnas = { "Clave", "Fecha Visita", "Calorías" };
        Object[][] datos = {
                { "FIG-123", "Dec 30", "2000" },
                { "FIG-123", "Dec 21", "2200" }
        };

        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        tableConsultas = new JTable(modelo);
        tableConsultas.setRowHeight(28);
        tableConsultas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableConsultas.setShowGrid(false);
        tableConsultas.setIntercellSpacing(new Dimension(0, 0));
        tableConsultas.setFillsViewportHeight(true);
        tableConsultas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // 🔒 Fijar columnas
        tableConsultas.getTableHeader().setReorderingAllowed(false);
        tableConsultas.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollTabla = new JScrollPane(tableConsultas);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        // Centrar la tabla horizontalmente en el mainPanel
        scrollTabla.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollTabla.setPreferredSize(new Dimension(800, 250));

        mainPanel.add(scrollTabla);

        // --- Botones inferiores ---
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Panel de botones centrado
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

	public JTable getTableConsultas() {
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
    
}


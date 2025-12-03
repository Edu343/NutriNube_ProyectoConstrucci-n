package Vista;

import Modelo.Core.View;

import Controlador.HistorialController;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import Modelo.POJOs.Consulta;
import Modelo.POJOs.Expediente;

/**
 * Vista encargada de mostrar el historial de consultas de un paciente.
 * Gestiona la interacción con la tabla, búsquedas por fecha, selección
 * de consulta, eliminación y navegación hacia otras vistas.
 */
public class HistorialConsultasView extends View {
    
    private HistorialConsultasViewLayout historialLayout;

    private String clavePacienteActual;
    
    public HistorialConsultasView(String tag) {
        super(tag);
    }
    
    @Override
    protected void crearController() {
        myController = new HistorialController(tag);
    }
    
    /**
     * Construye y configura todos los componentes gráficos del layout.
     * Incluye listeners de búsqueda, doble clic, navegación y acciones principales.
     */
    @Override
    protected void crearViewLayout() {
        historialLayout = new HistorialConsultasViewLayout();
        this.mainPanel = historialLayout.getPanel();
        
        // Escucha de filtrado por fecha desde el campo de búsqueda del layout.
        historialLayout.addPropertyChangeListener("filtrarFecha", evt -> {
            String fecha = (String) evt.getNewValue();

            if (fecha == null || fecha.isEmpty()) {
                cargarHistorialConsultas();
                return;
            }

            // Solicitar al controlador la lista filtrada por fecha
            List<Consulta> filtradas =
                ((HistorialController) myController)
                .buscarConsultasPorFecha(clavePacienteActual, fecha);

            actualizarTablaConsultas(filtradas);
        });

        // Listener del campo de texto para búsqueda en tiempo real.
        historialLayout.getTxtBuscar().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = historialLayout.getTxtBuscar().getText().trim();

                if (texto.equals("") || texto.equals("Buscar fecha") || texto.equals(" Buscar fecha")) {
                    cargarHistorialConsultas();
                    return;
                }

                // Validar formato YYYY-MM-DD
                if (!texto.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return;
                }

                List<Consulta> filtradas =
                    ((HistorialController) myController)
                    .buscarConsultasPorFecha(clavePacienteActual, texto);

                actualizarTablaConsultas(filtradas);
            }
        });

        // Botón para agregar una nueva consulta
        historialLayout.getBtnAgregar().addActionListener(e -> 
            myController.handleAgregarConsulta(clavePacienteActual));
        
        // Botón para eliminar la consulta seleccionada
        historialLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = historialLayout.getTablaConsultas().getSelectedRow();
            if (selectedRow != -1) {
                String claveConsulta =
                    (String) historialLayout.getTablaConsultas().getValueAt(selectedRow, 0);
                myController.handleEliminarConsulta(claveConsulta);
            }
        });
        
        // Regresar a la vista de pacientes
        historialLayout.getBtnPacientes().addActionListener(e ->
            ((HistorialController)myController).handleSalir());
            
        historialLayout.getBtnLogout().addActionListener(e ->
            myController.handleLogout());
            
        historialLayout.getTablaConsultas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String claveConsulta =
                            (String) historialLayout.getTablaConsultas().getValueAt(row, 0);
                        myController.handleEditarConsulta(claveConsulta);
                    }
                }
            }
        });
    }
    
    @Override
    public void display() {
        if (clavePacienteActual != null) {
            cargarHistorialConsultas();
        }
    }
    
    /**
     * Recibe la clave del paciente y prepara la vista para mostrar su historial.
     * @param data clave única del paciente seleccionada desde otra vista.
     */
    @Override
    public void cargarDatos(Object data) {
        if (data instanceof String) {
            this.clavePacienteActual = (String) data;
        } 
        display();
    }
    
    /**
     * Actualiza la tabla con una lista de consultas ya filtradas.
     * @param consultas lista de consultas a mostrar.
     */
    public void actualizarTablaConsultas(List<Consulta> consultas) {
        DefaultTableModel model =
            (DefaultTableModel) historialLayout.getTablaConsultas().getModel();

        model.setRowCount(0);

        if (consultas == null || consultas.isEmpty()) {
            // Vaciar tabla cuando no hay resultados
            SwingUtilities.invokeLater(() -> {
                historialLayout.getTablaConsultas().revalidate();
                historialLayout.getTablaConsultas().repaint();
            });
            return;
        }

        for (Consulta c : consultas) {
            Object[] row = {
                c.getClaveConsulta(),
                c.getFechaVisita(),
                c.getCaloriasCalculo() != null
                    ? String.valueOf(c.getCaloriasCalculo().getCalorias())
                    : "0"
            };
            model.addRow(row);
        }

        SwingUtilities.invokeLater(() -> {
            historialLayout.getTablaConsultas().revalidate();
            historialLayout.getTablaConsultas().repaint();
        });
    }

    /**
     * Carga el expediente completo del paciente desde el modelo
     * y reconstruye la tabla del historial de consultas.
     * Maneja fallas de acceso a la base de datos.
     */
    private void cargarHistorialConsultas() {
        
        Expediente expediente = null;
        List<Consulta> consultas = null;

        try {
            expediente = myModel.getPacienteDAO().obtenerExpedienteCompleto(clavePacienteActual);
            consultas = expediente.getConsultas();
            
            if (expediente.getPaciente() != null) {
                String nombre =
                    expediente.getPaciente().getNombre() + " " +
                    expediente.getPaciente().getApellido();
                historialLayout.setNombrePaciente(nombre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        DefaultTableModel model =
            (DefaultTableModel) historialLayout.getTablaConsultas().getModel();

        model.setRowCount(0);
        
        // Cargar resultados en la tabla si existen
        if (consultas != null) {
            for (Consulta consulta : consultas) {
                Object[] row = {
                    consulta.getClaveConsulta(),
                    consulta.getFechaVisita(),
                    consulta.getCaloriasCalculo() != null
                        ? String.valueOf(consulta.getCaloriasCalculo().getCalorias())
                        : "0"
                };
                model.addRow(row);
            }
        }

        SwingUtilities.invokeLater(() -> {
            historialLayout.getTablaConsultas().revalidate();
            historialLayout.getTablaConsultas().repaint();
        });
    }
}

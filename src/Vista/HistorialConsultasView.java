package Vista;

import Modelo.Core.View;

import Controlador.HistorialController;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import Modelo.POJOs.Consulta;
import Modelo.POJOs.Expediente;

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
    
    @Override
    protected void crearViewLayout() {
        historialLayout = new HistorialConsultasViewLayout();
        this.mainPanel = historialLayout.getPanel();
        
        historialLayout.addPropertyChangeListener("filtrarFecha", evt -> {
            String fecha = (String) evt.getNewValue();

            if (fecha == null || fecha.isEmpty()) {
                cargarHistorialConsultas();
                return;
            }

            List<Consulta> filtradas =
                myController.buscarConsultasPorFecha(clavePacienteActual, fecha);

            actualizarTablaConsultas(filtradas);
        });

        // Listener buscar (YYYY-MM-DD)
        historialLayout.getTxtBuscar().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = historialLayout.getTxtBuscar().getText().trim();
                
                if (texto.equals("") 
                    || texto.equals("Buscar fecha") 
                    || texto.equals(" Buscar fecha")) {
                    cargarHistorialConsultas();
                    return;
                }

                if (!texto.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return;
                }

                List<Consulta> filtradas =
                    myController.buscarConsultasPorFecha(clavePacienteActual, texto);

                actualizarTablaConsultas(filtradas);
            }
        });

        historialLayout.getBtnAgregar().addActionListener(e -> 
            myController.handleAgregarConsulta(clavePacienteActual)
        );
        
        
        historialLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = historialLayout.getTablaConsultas().getSelectedRow();
            
            if (selectedRow != -1) {

                String fechaConsulta = (String) historialLayout
                        .getTablaConsultas().getValueAt(selectedRow, 1);
                
                int respuesta = JOptionPane.showConfirmDialog(
                    mainPanel,
                    "¿Estás seguro que deseas eliminar la consulta del fecha " 
                    + fechaConsulta + "?\nEsta acción no se puede deshacer.",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    String claveConsulta = (String) historialLayout
                            .getTablaConsultas().getValueAt(selectedRow, 0);

                    myController.handleEliminarConsulta(claveConsulta);
                    cargarHistorialConsultas();
                }

            } else {
                JOptionPane.showMessageDialog(
                    mainPanel,
                    "Por favor selecciona una consulta para eliminar."
                );
            }
        }); // ← ← ← ESTA ES LA LLAVE QUE FALTABA
        
        historialLayout.getBtnPacientes().addActionListener(e ->
            myController.handleSalir()
        );
            
        historialLayout.getBtnLogout().addActionListener(e ->
            myController.handleLogout()
        );
            
        historialLayout.getTablaConsultas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String claveConsulta = (String) historialLayout
                                .getTablaConsultas().getValueAt(row, 0);
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
    
    @Override
    public void cargarDatos(Object data) {
        if (data instanceof String) {
            this.clavePacienteActual = (String) data;
        } 
        display();
    }
    
    public void actualizarTablaConsultas(List<Consulta> consultas) {
        DefaultTableModel model = 
            (DefaultTableModel) historialLayout.getTablaConsultas().getModel();
        
        model.setRowCount(0);

        if (consultas == null || consultas.isEmpty()) {
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

    
    private void cargarHistorialConsultas() {
        
        Expediente expediente = null;
        List<Consulta> consultas = null;

        try {
            expediente = myModel.getPacienteDAO()
                    .obtenerExpedienteCompleto(clavePacienteActual);

            if (expediente != null) {
                consultas = expediente.getConsultas();

                if (expediente.getPaciente() != null) {
                    String nombre = expediente.getPaciente().getNombre();
                    String apellido = expediente.getPaciente().getApellido();
                    String nombrePac = (nombre != null ? nombre : "")
                        + " "
                        + (apellido != null ? apellido : "");
                    historialLayout.setNombrePaciente(nombrePac.trim());
                }
            }
        } catch (SQLException e) {
            
        }
            
        DefaultTableModel model = 
            (DefaultTableModel) historialLayout.getTablaConsultas().getModel();
        
        model.setRowCount(0);
            
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

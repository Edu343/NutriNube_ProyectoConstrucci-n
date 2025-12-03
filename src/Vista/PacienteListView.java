package Vista;

import Modelo.Core.View;
import Modelo.POJOs.Paciente;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import Controlador.PacienteListController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PacienteListView extends View {
    
    private PacienteListViewLayout pacienteLayout;
    
    public PacienteListView(String tag) {
        super(tag);
    }
    
    @Override
    protected void crearController() {
        myController = new PacienteListController(tag);
    }
    
    @Override
    protected void crearViewLayout() {
        pacienteLayout = new PacienteListViewLayout();
        this.mainPanel = pacienteLayout.getPanel();
        
        // Botón Agregar
        pacienteLayout.getBtnAgregar().addActionListener(e -> 
            myController.handleAnadirPaciente()
        );
        
        
        pacienteLayout.getBtnEliminar().addActionListener(e -> {
            
            int selectedRow = pacienteLayout.getTablaPacientes().getSelectedRow();
            
            if (selectedRow != -1) {
                
                String nombrePaciente = (String) pacienteLayout.getTablaPacientes()
                        .getValueAt(selectedRow, 1);
                
                int respuesta = JOptionPane.showConfirmDialog(
                    mainPanel,
                    "¿Seguro que quieres eliminar al paciente " + nombrePaciente + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    String clavePaciente = (String) pacienteLayout.getTablaPacientes()
                            .getValueAt(selectedRow, 0);

                    myController.handleEliminarPaciente(clavePaciente);
                    cargarTablaPacientes();
                }
                
            } else {
                JOptionPane.showMessageDialog(mainPanel, 
                        "Por favor selecciona un paciente de la lista.");
            }
        }); 

        
        
        pacienteLayout.getTablaPacientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String clavePaciente = (String) pacienteLayout.getTablaPacientes()
                                .getValueAt(row, 0);
                        myController.handlePacienteSeleccionado(clavePaciente);
                    }
                }
            }
        });
        
        
        pacienteLayout.getTxtBuscar().addActionListener(e -> {
            String criterio = pacienteLayout.getTxtBuscar().getText();
            myController.handleBuscarPaciente(criterio);
        });
        
        
        pacienteLayout.getBtnLogout().addActionListener(e -> 
            myController.handleLogout()
        );
    }
    
    @Override
    public void display() {
        if (myModel.getNutriologoActual() != null) {
            String nombreCompleto = myModel.getNutriologoActual().getNombre()
                    + " " + myModel.getNutriologoActual().getApellido();
            pacienteLayout.setNombreNutriologo(nombreCompleto);
        }
        cargarTablaPacientes();
    }
    
    private void cargarTablaPacientes() {
        if (myModel.getNutriologoActual() != null) {
            List<Paciente> pacientes = null;
            try {
                pacientes = myModel.getNutriologoDAO()
                        .obtenerListaPacientes(myModel.getNutriologoActual()
                        .getClaveNutriologo());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            DefaultTableModel model = 
                    (DefaultTableModel) pacienteLayout.getTablaPacientes().getModel();
            model.setRowCount(0);
            
            if (pacientes != null) {
                for (Paciente paciente : pacientes) {
                    String ultimaFecha = "Sin historial";
                    try {
                        String fechaBD = myModel.getConsultaDAO()
                                .obtenerUltimaFechaPorPaciente(paciente.getClavePaciente());
                        if (fechaBD != null && !fechaBD.equals("Sin historial")) {
                            ultimaFecha = fechaBD;
                        }
                    } catch (Exception e) {}
                    
                    model.addRow(new Object[] {
                        paciente.getClavePaciente(),
                        paciente.getNombre() + " " + paciente.getApellido(),
                        ultimaFecha
                    });
                }
            }
        }
        
        SwingUtilities.invokeLater(() -> {
            pacienteLayout.getTablaPacientes().revalidate();
            pacienteLayout.getTablaPacientes().repaint();
        });
    }
    
    public JTable getTblPacientes() {
        return pacienteLayout.getTablaPacientes();
    }
}

package Vista;

import Modelo.Core.View;


import Controlador.PacienteListController;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import Modelo.POJOs.Paciente;

/**
 * Vista de la lista principal que muestra todos los pacientes del nutriólogo.
 * Permite buscar, agregar, eliminar y seleccionar un paciente para ver su historial.
 */

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
        
        pacienteLayout.getBtnAgregar().addActionListener(e -> 
            myController.handleAnadirPaciente(null));
        
        pacienteLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = pacienteLayout.getTablaPacientes().getSelectedRow();
            if (selectedRow != -1) {
                String clavePaciente = (String) pacienteLayout.getTablaPacientes().getValueAt(selectedRow, 0);
                myController.handleEliminarPaciente(clavePaciente);
            }
        });
        
        pacienteLayout.getTablaPacientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String clavePaciente = (String) pacienteLayout.getTablaPacientes().getValueAt(row, 0);
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
            myController.handleLogout());
    }
    
    @Override
    public void display() {
        if (myModel.getNutriologoActual() != null) {
            String nombreCompleto = myModel.getNutriologoActual().getNombre() + " " + myModel.getNutriologoActual().getApellido();
            pacienteLayout.setNombreNutriologo(nombreCompleto);
        }
        cargarTablaPacientes();
    }
    
    private void cargarTablaPacientes() {
        if (myModel.getNutriologoActual() != null) {
            List<Paciente> pacientes = null;
            try {
                pacientes = myModel.getNutriologoDAO().obtenerListaPacientes(myModel.getNutriologoActual().getClaveNutriologo());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            DefaultTableModel model = (DefaultTableModel) pacienteLayout.getTablaPacientes().getModel();
            model.setRowCount(0);
            
            if (pacientes != null) {
                for (Paciente paciente : pacientes) {
                    String ultimaFecha = "Sin historial";
                    try {
                        String fechaBD = myModel.getConsultaDAO().obtenerUltimaFechaPorPaciente(paciente.getClavePaciente());
                        if (fechaBD != null && !fechaBD.equals("Sin historial")) {
                            ultimaFecha = fechaBD;
                        }
                    } catch (Exception e) {
                    }

                    Object[] row = {
                        paciente.getClavePaciente(),
                        paciente.getNombre() + " " + paciente.getApellido(),
                        ultimaFecha
                    };
                    model.addRow(row);
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
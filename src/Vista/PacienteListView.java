package Vistas;

import Core.View;
import Controladores.PacienteListController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import POJOs.Paciente;

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
            ((PacienteListController)myController).handleAnadirPacienteRequest());
        
        pacienteLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = pacienteLayout.getTablePacientes().getSelectedRow();
            if (selectedRow != -1) {
                String clavePaciente = (String) pacienteLayout.getTablePacientes().getValueAt(selectedRow, 0);
                ((PacienteListController)myController).handleEliminarPacienteRequest(clavePaciente);
            }
        });
        
        pacienteLayout.getTablePacientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String clavePaciente = (String) pacienteLayout.getTablePacientes().getValueAt(row, 0);
                        ((PacienteListController)myController).handlePacienteSeleccionado(clavePaciente);
                    }
                }
            }
        });
        
        pacienteLayout.getTxtBuscar().addActionListener(e -> {
            String criterio = pacienteLayout.getTxtBuscar().getText();
            ((PacienteListController)myController).handleBuscarPacienteRequest(criterio);
        });
        
        pacienteLayout.getBtnLogout().addActionListener(e -> 
            ((PacienteListController)myController).handleLogoutRequest());
    }
    
    @Override
    public void display() {
        cargarTablaPacientes();
    }
    
    private void cargarTablaPacientes() {
        if (myModel.getNutriologoActual() != null) {
            List<Paciente> pacientes = null;
            try {
                pacientes = myModel.getPacienteDAO().readAllByNutriologo(myModel.getNutriologoActual().getClaveNutriologo());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            DefaultTableModel model = (DefaultTableModel) pacienteLayout.getTablePacientes().getModel();
            model.setRowCount(0);
            
            for (Paciente p : pacientes) {
                Object[] row = {
                    p.getClavePaciente(),
                    p.getNombre() + " " + p.getApellido(),
                    "N/A"
                };
                model.addRow(row);
            }
        }
    }
    
    public JTable getTblPacientes() {
        return pacienteLayout.getTablePacientes();
    }
}
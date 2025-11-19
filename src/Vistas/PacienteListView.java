package Vistas;

import Core.View;
import Controladores.PacienteController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import POJOs.Paciente;

public class PacienteListView extends View {
    
    private PacienteListViewLayout pacienteLayout;
    
    public PacienteListView(String tag) {
        super(tag);
    }
    
    @Override
    protected void crearController() {
        myController = new PacienteController(tag);
    }
    
    @Override
    protected void crearViewLayout() {
        pacienteLayout = new PacienteListViewLayout();
        this.mainPanel = pacienteLayout.getPanel();
        
        pacienteLayout.getBtnAnadirPaciente().addActionListener(e -> 
            ((PacienteController)myController).handleAnadirPacienteRequest());
        
        pacienteLayout.getBtnEliminarPaciente().addActionListener(e -> {
            int selectedRow = pacienteLayout.getTblPacientes().getSelectedRow();
            if (selectedRow != -1) {
                String clavePaciente = (String) pacienteLayout.getTblPacientes().getValueAt(selectedRow, 0);
                ((PacienteController)myController).handleEliminarPacienteRequest(clavePaciente);
            }
        });
        
        pacienteLayout.getBtnLogout().addActionListener(e -> 
            ((PacienteController)myController).handleLogoutRequest());
        
        pacienteLayout.getTblPacientes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = pacienteLayout.getTblPacientes().getSelectedRow();
                    if (row != -1) {
                        String clavePaciente = (String) pacienteLayout.getTblPacientes().getValueAt(row, 0);
                        ((PacienteController)myController).handlePacienteSeleccionadoRequest(clavePaciente);
                    }
                }
            }
        });
        
        pacienteLayout.getTxtBuscar().addActionListener(e -> {
            String criterio = pacienteLayout.getTxtBuscar().getText();
            ((PacienteController)myController).handleBuscarPacienteRequest(criterio);
        });
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            DefaultTableModel model = (DefaultTableModel) pacienteLayout.getTblPacientes().getModel();
            model.setRowCount(0);
            
            for (Paciente p : pacientes) {
                Object[] row = {
                    p.getClave_paciente(),
                    p.getNombre() + " " + p.getApellido(),
                    "N/A"
                };
                model.addRow(row);
            }
        }
    }
    
    public JTable getTblPacientes() {
        return pacienteLayout.getTblPacientes();
    }
}
package Vistas;

import Core.View;
import Controladores.HistorialController;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import POJOs.Consulta;
import POJOs.Paciente;

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
        
        historialLayout.getBtnAgregarConsulta().addActionListener(e -> 
            ((HistorialController)myController).handleAgregarConsultaRequest(clavePacienteActual));
        
        historialLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = historialLayout.getTableHistorial().getSelectedRow();
            if (selectedRow != -1) {
                String claveConsulta = (String) historialLayout.getTableHistorial().getValueAt(selectedRow, 0);
                ((HistorialController)myController).handleEliminarConsultaRequest(claveConsulta);
            }
        });
        
        historialLayout.getBtnPacientes().addActionListener(e -> 
            ((HistorialController)myController).handleVolverPacientesRequest());
        
        historialLayout.getBtnLogout().addActionListener(e -> 
            ((HistorialController)myController).handleLogoutRequest());
        
        historialLayout.getTableHistorial().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = historialLayout.getTableHistorial().getSelectedRow();
                    if (row != -1) {
                        String claveConsulta = (String) historialLayout.getTableHistorial().getValueAt(row, 0);
                        ((HistorialController)myController).handleEditarConsultaRequest(claveConsulta);
                    }
                }
            }
        });
    }
    
    @Override
    public void display() {
        cargarHistorialConsultas();
    }
    
    @Override
    public void loadData(Object data) {
        if (data instanceof String) {
            this.clavePacienteActual = (String) data;
            actualizarNombrePaciente();
        }
        display();
    }
    
    private void actualizarNombrePaciente() {
        if (clavePacienteActual != null) {
            Paciente paciente = null;
			try {
				paciente = myModel.getPacienteDAO().read(clavePacienteActual);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (paciente != null) {
                historialLayout.setNombrePaciente(paciente.getNombre() + " " + paciente.getApellido());
            }
        }
    }
    
    private void cargarHistorialConsultas() {
        if (clavePacienteActual != null) {
            List<Consulta> consultas = null;
			try {
				consultas = myModel.getConsultaDAO().recuperarConsultas(clavePacienteActual);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            DefaultTableModel model = (DefaultTableModel) historialLayout.getTableHistorial().getModel();
            model.setRowCount(0);
            
            for (Consulta c : consultas) {
                Object[] row = {
                    c.getClave_consulta(),
                    c.getFecha_visita(),
                    c.getTotalCalorias() != null ? String.valueOf(c.getTotalCalorias().getTotalCalorias()) : "0"
                };
                model.addRow(row);
            }
        }
    }
}
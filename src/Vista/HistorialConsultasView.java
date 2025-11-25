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
        
        historialLayout.getBtnAgregar().addActionListener(e -> 
            ((HistorialController)myController).handleAgregarConsultaRequest(clavePacienteActual));
        
        historialLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = historialLayout.getTableConsultas().getSelectedRow();
            if (selectedRow != -1) {
                String claveConsulta = (String) historialLayout.getTableConsultas().getValueAt(selectedRow, 0);
                ((HistorialController)myController).handleEliminarConsultaRequest(claveConsulta);
            }
        });
        
        historialLayout.getBtnPacientes().addActionListener(e ->
            ((HistorialController)myController).handleVolverPacientesRequest());
            
        historialLayout.getBtnLogout().addActionListener(e ->
            ((HistorialController)myController).handleLogoutRequest());
            
        historialLayout.getTableConsultas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String claveConsulta = (String) historialLayout.getTableConsultas().getValueAt(row, 0);
                        ((HistorialController)myController).handleEditarConsultaRequest(claveConsulta);
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
    
 // Archivo: HistorialConsultasView.java

    @Override
    public void loadData(Object data) {
        if (data instanceof String) {
            // Se recibe la clave del paciente (desde PacienteListView o desde el Formulario al guardar/salir)
            this.clavePacienteActual = (String) data;
        } 
        
        display();
    }


    
    private void cargarHistorialConsultas() {
        if (clavePacienteActual != null) {
            List<Consulta> consultas = null;
            try {
                consultas = myModel.getConsultaDAO().recuperarConsultas(clavePacienteActual);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            DefaultTableModel model = (DefaultTableModel) historialLayout.getTableConsultas().getModel();
            model.setRowCount(0);
            
            for (Consulta c : consultas) {
                Object[] row = {
                    c.getClaveConsulta(),
                    c.getFechaVisita(),
                    c.getCaloriasCalculo() != null ? String.valueOf(c.getCaloriasCalculo().getCalorias()) : "0"
                };
                model.addRow(row);
            }
        }
    }
}
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


/**
 * Vista que muestra la lista de todas las consultas registradas para un paciente específico.
 * Permite agregar, eliminar o editar consultas.
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
    
    @Override
    protected void crearViewLayout() {
        historialLayout = new HistorialConsultasViewLayout();
        this.mainPanel = historialLayout.getPanel();
        
        historialLayout.getBtnAgregar().addActionListener(e -> 
            myController.handleAgregarConsulta(clavePacienteActual));
        
        historialLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = historialLayout.getTableConsultas().getSelectedRow();
            if (selectedRow != -1) {
                String claveConsulta = (String) historialLayout.getTableConsultas().getValueAt(selectedRow, 0);
                myController.handleEliminarConsulta(claveConsulta);
            }
        });
        
        historialLayout.getBtnPacientes().addActionListener(e ->
            ((HistorialController)myController).handleSalir());
            
        historialLayout.getBtnLogout().addActionListener(e ->
            myController.handleLogout());
            
        historialLayout.getTableConsultas().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        String claveConsulta = (String) historialLayout.getTableConsultas().getValueAt(row, 0);
                        myController.handleEditarConsulta(claveConsulta);
                    }
                }
            }
        });
    }
    
    @Override
    public void display() {
        // Llama a cargar la tabla si hay un paciente activo.
        if (clavePacienteActual != null) {
            cargarHistorialConsultas();
        }
    }
    
    @Override
    public void loadData(Object data) {
        // Carga la clave del paciente cuando se accede a esta vista.
        if (data instanceof String) {
            this.clavePacienteActual = (String) data;
        } 
        
        display();
    }


    
    private void cargarHistorialConsultas() {
        // Lógica para cargar los datos de la tabla.
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
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
                cargarHistorialConsultas(); // recarga todo
                return;
            }

            List<Consulta> filtradas =
                ((HistorialController) myController)
                .buscarConsultasPorFecha(clavePacienteActual, fecha);

            actualizarTablaConsultas(filtradas);
        });

        
     historialLayout.getTxtBuscar().addKeyListener(new java.awt.event.KeyAdapter() {
         @Override
         public void keyReleased(java.awt.event.KeyEvent e) {
             String texto = historialLayout.getTxtBuscar().getText().trim();
             // ignorar placeholder
             if (texto.equals("") || texto.equals("Buscar fecha") || texto.equals(" Buscar fecha")) {
                 // recargar todas las consultas
                 cargarHistorialConsultas();
                 return;
             }

             // validar formato YYYY-MM-DD
             if (!texto.matches("\\d{4}-\\d{2}-\\d{2}")) {
                 // formato inválido: no filtrar (o podrías mostrar error)
                 return;
             }

             // pedir al controller los resultados filtrados
             List<Consulta> filtradas = ((HistorialController) myController).buscarConsultasPorFecha(clavePacienteActual, texto);
             actualizarTablaConsultas(filtradas);
         }
     });

        
        historialLayout.getBtnAgregar().addActionListener(e -> 
            myController.handleAgregarConsulta(clavePacienteActual));
        
        historialLayout.getBtnEliminar().addActionListener(e -> {
            int selectedRow = historialLayout.getTablaConsultas().getSelectedRow();
            if (selectedRow != -1) {
                String claveConsulta = (String) historialLayout.getTablaConsultas().getValueAt(selectedRow, 0);
                myController.handleEliminarConsulta(claveConsulta);
            }
        });
        
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
                        String claveConsulta = (String) historialLayout.getTablaConsultas().getValueAt(row, 0);
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
        DefaultTableModel model = (DefaultTableModel) historialLayout.getTablaConsultas().getModel();
        model.setRowCount(0);

        if (consultas == null || consultas.isEmpty()) {
            // nada que mostrar (o podrías recargar todas)
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
                c.getCaloriasCalculo() != null ? String.valueOf(c.getCaloriasCalculo().getCalorias()) : "0"
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
            	expediente = myModel.getPacienteDAO().obtenerExpedienteCompleto(clavePacienteActual);
                consultas = expediente.getConsultas();
                
                if (expediente.getPaciente() != null) {
                    String nombrePac = expediente.getPaciente().getNombre() + " " + expediente.getPaciente().getApellido();
                    historialLayout.setNombrePaciente(nombrePac);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            DefaultTableModel model = (DefaultTableModel) historialLayout.getTablaConsultas().getModel();
            model.setRowCount(0);
            
            if (consultas != null) {
                for (Consulta consulta : consultas) {
                    Object[] row = {
                        consulta.getClaveConsulta(),
                        consulta.getFechaVisita(),
                        consulta.getCaloriasCalculo() != null ? String.valueOf(consulta.getCaloriasCalculo().getCalorias()) : "0"
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
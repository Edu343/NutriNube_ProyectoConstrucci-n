

import javax.swing.SwingUtilities;

import Modelo.Core.MainViewLayout;
import Modelo.Servicios.NutriNubeModelo;

public class Main {

    public static void main(String[] args) {
        
        // Ejecutar la interfaz gráfica en el Hilo de Despacho de Eventos
        SwingUtilities.invokeLater(() -> {
            
            // 1. Crear el Modelo central 
            NutriNubeModelo modelo = new NutriNubeModelo();
            
            // 2. Crear la vista principal (que inicializa todas las demás vistas,
            // controladores, y el JFrame, y se adjunta como Observer al Modelo)
            MainViewLayout layoutPrincipal = new MainViewLayout(modelo);
            
        });
    }
}
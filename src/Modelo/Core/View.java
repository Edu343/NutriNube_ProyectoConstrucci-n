package Modelo.Core;

import javax.swing.JPanel;

import Modelo.Servicios.NutriNubeModelo;

/**
 * Clase abstracta base para todas las vistas (lógica) de la aplicación.
 * Contiene referencias al Modelo y al Controlador y define el ciclo de vida de la vista (inicializar, crearController, display).
 * Implementa la interfaz Observer para actualizar su contenido automáticamente.
 */

public abstract class View implements Observer {
    protected NutriNubeModelo myModel;
    protected Controller myController;
    protected JPanel mainPanel; 
    protected String tag;

    public View(String tag) {
        this.tag = tag;
    }

    public void inicializar(NutriNubeModelo model, MainViewLayout layout) {
        myModel = model;
        crearController();
        // 🚨 MODIFICADO: Pasamos el layout al inicializar el controlador
        myController.initialize(myModel, this, layout); 
        crearViewLayout();
        myModel.attach(this);
    }

    protected abstract void crearController();

    protected abstract void crearViewLayout(); // Se implementa en la Vista concreta

    public abstract void display();

    @Override
    public void update() {
        display();
    }
    
    public void loadData(Object data) {
    }
    
    public JPanel getViewLayout() {
        return mainPanel; // Método para que MainViewLayout lo agregue al CardLayout
    }

}	
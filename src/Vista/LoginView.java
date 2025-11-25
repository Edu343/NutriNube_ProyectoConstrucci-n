package Vistas;

import Core.View;
import Controladores.LoginController;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.image.BufferedImage; 

public class LoginView extends View {
    
    private LoginViewLayout loginLayout; 

    public LoginView(String tag) {
        super(tag);
    }

    @Override
    protected void crearController() {
        myController = new Controladores.LoginController(tag);
    }

    @Override
    protected void crearViewLayout() {
        // 🚨 CORRECCIÓN: Inicializar loginLayout y asignar mainPanel.
        this.loginLayout = new LoginViewLayout();
        this.mainPanel = loginLayout.getPanel();
        
        loginLayout.getBtnAcceder().addActionListener(e -> {
            // Se asume que estos métodos devuelven el String o char[] directamente (como en LoginViewLayout)
            String user = loginLayout.getTxtUsuario();
            String pass = loginLayout.getTxtContrasena(); // Ya devuelve String en LoginViewLayout
            
            // Llama al controlador para el login
            ((LoginController)myController).handleLogin(user, pass); 
        });
    }

    @Override
    public void display() {
        // Lógica: Mostrar o limpiar errores en el layout
    }
    
    public void mostrarError(String mensaje) {
        // Llama a un método en el layout para mostrar el mensaje de error
        System.out.println("ERROR LOGIN: " + mensaje);
    }
}
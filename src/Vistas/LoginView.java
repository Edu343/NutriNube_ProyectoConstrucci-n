package Vistas;

import Core.View;
import Controladores.LoginController;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.image.BufferedImage; // Para lógica de imagen/placeholder

public class LoginView extends View {
    
    private LoginViewLayout loginLayout; // Sigue llamándose Layout por la herencia del nombre

    public LoginView(String tag) {
        super(tag);
    }

    @Override
    protected void crearController() {
        myController = new Controladores.LoginController(tag);
    }

    @Override
    protected void crearViewLayout() {
        // La clase LoginViewLayout debe ser convertida a un JPanel builder.
        // Aquí se inicializa y se obtiene el JPanel resultante.
        
        loginLayout = new LoginViewLayout(); // Asumo que el constructor sin tag es el que quieres usar
        this.mainPanel = loginLayout.getPanel(); // Asumo que LoginViewLayout ya tiene un método getPanel()
        
        // --- Conexión de Eventos ---
        loginLayout.getBtnAcceder().addActionListener(e -> {
            String usuario = loginLayout.getTxtUsuario();
            String contrasena = loginLayout.getTxtContrasena();
            ((LoginController)myController).handleAccederRequest(usuario, contrasena);
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
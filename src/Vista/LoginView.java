package Vistas;

import Core.View;


/**
 * Vista para la interfaz de inicio de sesión.
 * Gestiona la captura de credenciales y la interacción con el LoginController.
 */

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
        // Inicializa el Layout de la vista y asigna el panel principal.
        this.loginLayout = new LoginViewLayout();
        this.mainPanel = loginLayout.getPanel();
        
        loginLayout.getBtnAcceder().addActionListener(e -> {
            // Recolecta usuario y contraseña y llama al controlador.
            String user = loginLayout.getTxtUsuario();
            String pass = loginLayout.getTxtContrasena(); 
            
            myController.handleLogin(user, pass); 
        });
    }

    @Override
    public void display() {
        // Lógica de visualización.
    }
    
    public void mostrarError(String mensaje) {
        // Muestra un mensaje de error al usuario.
        System.out.println("ERROR LOGIN: " + mensaje);
    }
}
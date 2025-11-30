package Vista;

import Modelo.Core.View;
import Controlador.LoginController;

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
        myController = new LoginController(tag);
    }

    @Override
    protected void crearViewLayout() {
        // Inicializa el Layout de la vista y asigna el panel principal.
        this.loginLayout = new LoginViewLayout();
        this.mainPanel = loginLayout.getPanel();

        // Botón "Acceder" lanza acción de login del controller
        loginLayout.getBtnAcceder().addActionListener(e -> {
            String user = loginLayout.getTxtUsuario();
            String pass = loginLayout.getTxtContrasena();
            myController.handleLogin(user, pass);
        });
    }

    @Override
    public void display() {
        // Lógica de visualización si aplica
    }

    public void mostrarError(String mensaje) {
        // Muestra un mensaje de error al usuario (la vista puede usar un JOptionPane)
        javax.swing.JOptionPane.showMessageDialog(null, mensaje, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

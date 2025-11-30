package Controlador;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;

/**
 * Controlador para manejar la lógica de inicio de sesión de la aplicación.
 * Procesa credenciales y gestiona la navegación al dashboard de pacientes.
 */

public class LoginController extends Controller {
    
    public LoginController(String tag) {
        super(tag);
    }
    
    @Override
    public void handleLogin(String usuario, String contrasena) {
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            return;
        }
        
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }	
    
    
    @Override
    public void update() {
    }
}
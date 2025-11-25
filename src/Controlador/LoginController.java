package Controladores;

import Core.Controller;
import Core.MainViewLayout;

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
        // Simulación básica de validación (El modelo debería manejar la validación real).
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            // Manejar error de campos vacíos si fuera necesario
            return;
        }
        
        // Asumiendo login exitoso o manejado en el modelo:
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }	
    
    
    @Override
    public void update() {
    }
}
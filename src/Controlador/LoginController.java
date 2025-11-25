package Controladores;

import Core.Controller;
import Core.MainViewLayout;
import java.sql.SQLException;

public class LoginController extends Controller {
    
    public LoginController(String tag) {
        super(tag);
    }
    
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
package Controlador;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;
import Modelo.Servicios.NutriNubeModelo;
import Modelo.Servicios.NutriNubeModelo.LoginEstado;
import Modelo.POJOs.Nutriologo;
import javax.swing.JOptionPane;

/**
 * Controller dedicado al flujo de login.
 * - Pide al modelo comprobar existencia/estado
 * - Si no existe, ofrece crear nuevo nutriólogo (diálogo)
 * - Si existe y contraseña es correcta, navega a la vista de pacientes
 * - Si existe pero contraseña incorrecta, muestra aviso (no cambio de contraseña)
 */
public class LoginController extends Controller {

    public LoginController(String tag) {
        super(tag);
    }

    @Override
    public void handleLogin(String usuario, String contrasena) {
        if (usuario == null || usuario.trim().isEmpty() || contrasena == null) {
            myView.mostrarError("Usuario o contraseña vacíos.");
            return;
        }

        NutriNubeModelo modelo = (NutriNubeModelo) myModel;

        LoginEstado estado = modelo.obtenerEstadoLogin(usuario, contrasena);

        switch (estado) {
            case SUCCESS:
                modelo.login(usuario, contrasena);
                cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
                break;

            case WRONG_PASSWORD:
                JOptionPane.showMessageDialog(null,
                        "Contraseña incorrecta. Si la olvidaste, contacta al administrador.",
                        "Contraseña incorrecta",
                        JOptionPane.WARNING_MESSAGE);
                break;

            case NOT_FOUND:
                int opcion = JOptionPane.showConfirmDialog(null,
                        "No se encontraron las claves para '" + usuario + "'.\n¿Deseas registrar un nuevo nutriólogo con esa clave?",
                        "Nutriólogo no encontrado",
                        JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    String nombre = JOptionPane.showInputDialog(null, "Nombre:");
                    if (nombre == null) return;
                    String apellido = JOptionPane.showInputDialog(null, "Apellido:");
                    if (apellido == null) return;
                    String correo = JOptionPane.showInputDialog(null, "Correo:");
                    if (correo == null) return;
                    String password = JOptionPane.showInputDialog(null, "Contraseña (texto plano):");
                    if (password == null) return;

                    Nutriologo nuevo = new Nutriologo();
                    nuevo.setClaveNutriologo(usuario);
                    nuevo.setNombre(nombre);
                    nuevo.setApellido(apellido);
                    nuevo.setCorreo(correo);

                    boolean creado = modelo.crearNutriologo(nuevo, password);
                    if (creado) {
                        JOptionPane.showMessageDialog(null,
                                "Nutriólogo creado correctamente. Inicia sesión de nuevo.",
                                "Creación exitosa",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se pudo crear el nutriólogo. Revisa el log.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;

            case ERROR:
            default:
                JOptionPane.showMessageDialog(null,
                        "Ocurrió un error verificando las credenciales. Revisa el log.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    
}

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// para que sea un solo archivo ejecutable.
public class LoginView extends JFrame {

    private JLabel lblLogo;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblPrompt;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {
        // 1. Configuración de la Ventana (JFrame)
        this.setTitle("NutriNube - Inicia Sesión");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 600); // Tamaño inicial
        this.setLocationRelativeTo(null); // Centrar en la pantalla
        this.getContentPane().setBackground(Color.WHITE);

        //
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 15, 8, 15);

        // 3. Crear y añadir los componentes

        // Título "NutriNube"
        lblTitle = new JLabel("NutriNube");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Logo
        // Cargar la imagen desde el classpath (funciona en IDE y dentro de JAR)
        lblLogo = new JLabel();
        java.net.URL logoUrl = LoginView.class.getResource("nutrinubeLogo.jpg");
        // Intentar buscar también con ruta absoluta en classpath (/Package/path)
        if (logoUrl == null) {
            logoUrl = LoginView.class.getResource("/NutrinubeVistas/nutrinubeLogo.jpg");
        }

        if (logoUrl != null) {
            ImageIcon logoIcon = new ImageIcon(logoUrl);
            // Escalar si la imagen es demasiado ancha para la UI
            int maxWidth = 200; // ajustar según diseño
            int w = logoIcon.getIconWidth();
            int h = logoIcon.getIconHeight();
            if (w > maxWidth && w > 0) {
                Image img = logoIcon.getImage();
                double scale = (double) maxWidth / (double) w;
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);
                // Escalado de alta calidad usando BufferedImage y RenderingHints
                BufferedImage buffered = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = buffered.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(img, 0, 0, newW, newH, null);
                g2.dispose();
                logoIcon = new ImageIcon(buffered);
            }
            lblLogo.setIcon(logoIcon);
        } else {
            lblLogo.setText("[Logo no encontrado]");
            System.err.println("Error: No se encontró 'nutrinubeLogo.jpg' en el classpath. " +
                    "Coloca el archivo en el mismo paquete 'NutrinubeVistas' o en resources y asegúrate de que se copie al classpath.");
        }
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        // Subtítulo "Inicia Sesión"
        lblSubtitle = new JLabel("Inicia Sesión");
        lblSubtitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Prompt "Ingrese su usuario y contraseña"
        lblPrompt = new JLabel("Ingrese su usuario y contraseña");
        lblPrompt.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrompt.setForeground(Color.GRAY);
        lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);

        // Campo de Usuario (con placeholder)
        txtUser = new JTextField(20);
        txtUser.setFont(new Font("Arial", Font.PLAIN, 14));
        addPlaceholder(txtUser, "nutriPadre");

        // Campo de Contraseña (con placeholder)
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        addPlaceholder(txtPassword, "Contraseña123");

        // Botón "Acceder"
        btnLogin = new JButton("Acceder");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setOpaque(true);
        btnLogin.setBorderPainted(false);
        btnLogin.setPreferredSize(new Dimension(100, 40));

        // --- Agregar todos los componentes al frame ---

        // Constraints para elementos que NO se estiran
        GridBagConstraints gbcNoFill = (GridBagConstraints) gbc.clone();
        gbcNoFill.fill = GridBagConstraints.NONE;

        this.add(lblTitle, gbcNoFill);
        this.add(lblLogo, gbcNoFill);
        this.add(lblSubtitle, gbcNoFill);
        this.add(lblPrompt, gbcNoFill);
        this.add(txtUser, gbc);
        this.add(txtPassword, gbc);

        gbcNoFill.insets = new Insets(15, 15, 8, 15); // Espacio extra arriba del botón
        this.add(btnLogin, gbcNoFill);
    }

    /**
     * Método práctico para agregar texto de "placeholder" (texto guía)
     * a un JTextField o JPasswordField.
     * 
     * @param textField
     * @param placeholder
     */
    public void addPlaceholder(JTextField textField, String placeholder) {
        // Guardar el estado inicial del password field
        final char defaultEchoChar = (textField instanceof JPasswordField) ? ((JPasswordField) textField).getEchoChar()
                : 0;

        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        if (textField instanceof JPasswordField) {
            ((JPasswordField) textField).setEchoChar((char) 0);
        }

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar(defaultEchoChar);
                    }
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String currentText = (textField instanceof JPasswordField)
                        ? new String(((JPasswordField) textField).getPassword())
                        : textField.getText();

                if (currentText.isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                    if (textField instanceof JPasswordField) {
                        ((JPasswordField) textField).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    // --- PUNTO DE ENTRADA ---
    // Este método main lanza la ventana
    public static void main(String[] args) {
        // Se asegura de que la UI se cree en el hilo correcto de Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Crea una instancia de nuestra ventana y la hace visible
                new LoginView().setVisible(true);
            }
        });
    }
}
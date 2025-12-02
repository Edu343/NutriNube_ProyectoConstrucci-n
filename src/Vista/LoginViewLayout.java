package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
// vista  del login de la aplicacion NutriNube

public class LoginViewLayout extends JPanel {

    private JLabel lblLogo;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblPrompt;
    private JTextField txtUser;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginViewLayout() {
        // Configuración del Layout Manager (GridBagLayout)
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 15, 8, 15);

        // Título "NutriNube"
        lblTitle = new JLabel("NutriNube");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Logo
        lblLogo = new JLabel();
        java.net.URL logoUrl = LoginViewLayout.class.getResource("nutrinubeLogo.jpg");

        if (logoUrl == null) {
            logoUrl = LoginViewLayout.class.getResource("/NutrinubeVistas/nutrinubeLogo.jpg");
        }

        if (logoUrl != null) {
            ImageIcon logoIcon = new ImageIcon(logoUrl);
            int maxWidth = 200;
            int w = logoIcon.getIconWidth();
            int h = logoIcon.getIconHeight();
            if (w > maxWidth && w > 0) {
                Image img = logoIcon.getImage();
                double scale = (double) maxWidth / (double) w;
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);

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

        // --- Agregar todos los componentes al panel ---

        GridBagConstraints gbcNoFill = (GridBagConstraints) gbc.clone();
        gbcNoFill.fill = GridBagConstraints.NONE;

        this.add(lblTitle, gbcNoFill);
        this.add(lblLogo, gbcNoFill);
        this.add(lblSubtitle, gbcNoFill);
        this.add(lblPrompt, gbcNoFill);
        this.add(txtUser, gbc);
        this.add(txtPassword, gbc);

        gbcNoFill.insets = new Insets(15, 15, 8, 15);
        this.add(btnLogin, gbcNoFill);
    }

    /**
     * Método práctico para agregar texto de "placeholder" (texto guía)
     * a un JTextField o JPasswordField.
     */
    public void addPlaceholder(JTextField textField, String placeholder) {
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

    // --- Getters ---

    public JPanel getPanel() {
        return this;
    }

    public String getTxtUser() {
        return txtUser.getText();
    }

    public String getTxtUsuario() {
        return txtUser.getText();
    }

    public String getTxtPassword() {
        return new String(txtPassword.getPassword());
    }

    public String getTxtContrasena() {
        return new String(txtPassword.getPassword());
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public JButton getBtnAcceder() {
        return btnLogin;
    }

    public JTextField getTxtUserField() {
        return txtUser;
    }

    public JPasswordField getTxtPasswordField() {
        return txtPassword;
    }
}
package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// Clase que extiende JPanel para la vista de Login.
public class LoginViewLayout extends JPanel {

    // Declaración de componentes de la UI.
    private JLabel lblLogo;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblPrompt;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnAcceder;
    
    
    
    public LoginViewLayout() {
        // Asignación del Layout Manager (GridBagLayout) para control de posición.
        this.setLayout(new GridBagLayout());
        
        // Creación y configuración de las restricciones base (GridBagConstraints).
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Cada componente ocupa una fila.
        gbc.anchor = GridBagConstraints.CENTER;      // Centrado horizontal y vertical.
        gbc.fill = GridBagConstraints.HORIZONTAL;    // Estirar horizontalmente los campos de texto.
        gbc.insets = new Insets(8, 15, 8, 15);      // Márgenes para separar componentes.



        // Configuración de la etiqueta de título "NutriNube".
        lblTitle = new JLabel("NutriNube");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuración de la etiqueta para el logo.
        lblLogo = new JLabel();
        
        // Búsqueda de la URL del logo en el Classpath.
        java.net.URL logoUrl = LoginViewLayout.class.getResource("nutrinubeLogo.jpg");
        
        
        // Búsqueda alternativa de la URL si la primera falla.
        if (logoUrl == null) {
            logoUrl = LoginViewLayout.class.getResource("/NutrinubeVistas/nutrinubeLogo.jpg");
        }

        // Lógica para cargar y escalar el logo si la URL es válida.
        if (logoUrl != null) {
            ImageIcon logoIcon = new ImageIcon(logoUrl);
            int maxWidth = 200; 
            int w = logoIcon.getIconWidth();
            int h = logoIcon.getIconHeight();
            if (w > maxWidth && w > 0) {
                // Se realiza el escalado de la imagen.
                Image img = logoIcon.getImage();
                double scale = (double) maxWidth / (double) w;
                int newW = (int) (w * scale);
                int newH = (int) (h * scale);
                
                // Se utiliza BufferedImage para asegurar escalado de alta calidad.
                BufferedImage buffered = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = buffered.createGraphics();
                
                
                // Se activan las sugerencias de renderizado para mejor calidad.
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(img, 0, 0, newW, newH, null);
                g2.dispose();
                logoIcon = new ImageIcon(buffered);
            }
            // Se asigna el logo escalado a la etiqueta.
            lblLogo.setIcon(logoIcon);
        } else {
            // Manejo de error si el logo no se encuentra.
            lblLogo.setText("[Logo no encontrado]");
            System.err.println("Error: No se encontró 'nutrinubeLogo.jpg' en el classpath. Coloca el archivo en el mismo paquete 'NutrinubeVistas' o en resources y asegúrate de que se copie al classpath.");
        }
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuración del subtítulo "Inicia Sesión".
        lblSubtitle = new JLabel("Inicia Sesión");
        lblSubtitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuración del texto de ayuda "Ingrese su usuario y contraseña".
        lblPrompt = new JLabel("Ingrese su usuario y contraseña");
        lblPrompt.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrompt.setForeground(Color.GRAY);
        lblPrompt.setHorizontalAlignment(SwingConstants.CENTER);

        // Configuración del campo de texto para Usuario.
        txtUsuario = new JTextField(20);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        // Se añade el efecto de texto guía (placeholder).
        addPlaceholder(txtUsuario, "nutriPadre");

        // Configuración del campo de texto para Contraseña.
        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 14));
        // Se añade el efecto de texto guía (placeholder).
        addPlaceholder(txtContrasena, "Contraseña123");

        // Configuración del botón "Acceder".
        btnAcceder = new JButton("Acceder");
        btnAcceder.setFont(new Font("Arial", Font.BOLD, 14));
        btnAcceder.setBackground(Color.BLACK);
        btnAcceder.setForeground(Color.WHITE);
        btnAcceder.setOpaque(true);
        btnAcceder.setBorderPainted(false);
        btnAcceder.setPreferredSize(new Dimension(100, 40));

        // --- Adición de Componentes al Panel (Usando GridBagLayout) ---

        // Clonación de GBC para elementos que no deben estirarse horizontalmente.
        GridBagConstraints gbcNoFill = (GridBagConstraints) gbc.clone();
        gbcNoFill.fill = GridBagConstraints.NONE;

        this.add(lblTitle, gbcNoFill);
        this.add(lblLogo, gbcNoFill);
        this.add(lblSubtitle, gbcNoFill);
        this.add(lblPrompt, gbcNoFill);
        // Se añaden campos de texto que sí se estiran horizontalmente.
        this.add(txtUsuario, gbc);
        this.add(txtContrasena, gbc);

        // Se ajustan los márgenes para el botón (más espacio superior).
        gbcNoFill.insets = new Insets(15, 15, 8, 15);
        this.add(btnAcceder, gbcNoFill);
    }

    /**
     * Método para añadir el comportamiento de texto guía (placeholder)
     * a un campo de texto.
     * * @param textField Campo de texto o contraseña.
     * @param placeholder Texto guía a mostrar.
     */
    public void addPlaceholder(JTextField textField, String placeholder) {
        // Se obtiene el carácter de eco por defecto para JPasswordField.
        final char defaultEchoChar = (textField instanceof JPasswordField) ? ((JPasswordField) textField).getEchoChar()
                : 0;

        // Se configura el estado inicial (mostrar placeholder).
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        

        if (textField instanceof JPasswordField) {
            // Se desactiva el ocultamiento de caracteres para mostrar el placeholder.
            ((JPasswordField) textField).setEchoChar((char) 0);
        }

        // Se añade un FocusListener para manejar el placeholder.
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Lógica al GANAR foco: Quitar placeholder y restaurar color/carácter de eco.
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
                // Lógica al PERDER foco: Restaurar placeholder si el campo está vacío.
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
    
    // --- Métodos Getters ---
    
    public String getTxtUsuario() {
        return txtUsuario.getText();
    }

    public String getTxtContrasena() {
        return new String(txtContrasena.getPassword());
    }
    
    public JButton getBtnAcceder() {
        return btnAcceder;
    }
    
    // Método para devolver el panel si la clase principal lo requiere.
    public JPanel getPanel() {
        return this;
    }

}
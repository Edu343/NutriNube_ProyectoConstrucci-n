package Modelo.Core;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public abstract class ViewLayout extends JPanel {

    protected static final Color HEADER_COLOR = new Color(44, 54, 73);
    protected static final Color TEXT_COLOR = Color.BLACK;
    protected static final Color BACKGROUND_COLOR = Color.WHITE;
    protected static final Color BUTTON_COLOR = Color.BLACK;
    protected static final Color BUTTON_TEXT_COLOR = Color.WHITE;

    protected JButton crearBotonNegro(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(BUTTON_COLOR);
        btn.setForeground(BUTTON_TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    protected JLabel crearLogo() {
        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        java.net.URL imgUrlLogo = getClass().getResource("NutriNube.png");

        if (imgUrlLogo != null) {
            ImageIcon iconLogo = new ImageIcon(imgUrlLogo);
            Image scaledLogo = iconLogo.getImage().getScaledInstance(80, 50, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledLogo));
        } else {
            lblLogo.setText("NutriNube");
            lblLogo.setForeground(Color.WHITE);
        }

        return lblLogo;
    }

    protected JButton crearBotonLogout() {
        JButton btnLogout = new JButton("Log Out");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(HEADER_COLOR);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        java.net.URL logoutUrl = getClass().getResource("salir_logo.png");
        if (logoutUrl != null) {
            ImageIcon logoutIcon = new ImageIcon(logoutUrl);
            Image scaledLogout = logoutIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            btnLogout.setIcon(new ImageIcon(scaledLogout));
            btnLogout.setText(null);
            btnLogout.setToolTipText("Log Out");
            btnLogout.setBorderPainted(false);
            btnLogout.setContentAreaFilled(false);
            btnLogout.setOpaque(false);
        }

        return btnLogout;
    }

    protected JPanel crearPanelBusqueda(JTextField txtBuscar) {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        searchPanel.setPreferredSize(new Dimension(200, 30));

        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setBorder(null);
        txtBuscar.setForeground(Color.GRAY);

        searchPanel.add(new JLabel(" 🔍 "), BorderLayout.WEST);
        searchPanel.add(txtBuscar, BorderLayout.CENTER);

        return searchPanel;
    }

    protected void configurarTabla(JTable tabla) {
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setResizingAllowed(false);
    }

    protected DefaultTableModel crearModeloTablaNoEditable() {
        return new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    protected void agregarPlaceholderBehavior(JTextField textField, String placeholder) {
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    protected void agregarPlaceholderPasswordBehavior(JPasswordField passwordField, String placeholder) {
        final char defaultEchoChar = passwordField.getEchoChar();

        passwordField.setText(placeholder);
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);

        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                String currentText = new String(passwordField.getPassword());
                if (currentText.equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar(defaultEchoChar);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                String currentText = new String(passwordField.getPassword());
                if (currentText.isEmpty()) {
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setText(placeholder);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
    }
}

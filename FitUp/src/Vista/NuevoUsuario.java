package Vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NuevoUsuario extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textNombre;
    private JTextField textApellido1;
    private JTextField textApellido2;
    private JTextField textCorreo;
    private JTextField textContraseña;
    private JTextField textFechaNac;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NuevoUsuario frame = new NuevoUsuario();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NuevoUsuario() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 648, 478);
        setLocationRelativeTo(null);
        setResizable(false);

        // Fondo con degradado oscuro
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(10, 10, 10);
                Color color2 = new Color(60, 60, 60);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, width, height);
            }
        };

        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Logo
        JLabel lblLogo = new JLabel();
        ImageIcon icono = new ImageIcon("FitUp_Logo_SinFondo.png");
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon(imagen.getScaledInstance(300, 150, Image.SCALE_SMOOTH));
        lblLogo.setIcon(iconoEscalado);
        lblLogo.setBounds(252, -15, 300, 120);
        contentPane.add(lblLogo);

        Font labelFont = new Font("Tahoma", Font.BOLD, 16);
        Font fieldFont = new Font("Tahoma", Font.PLAIN, 15);

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(labelFont);
        lblNombre.setBounds(72, 96, 150, 25);
        contentPane.add(lblNombre);

        textNombre = new JTextField();
        textNombre.setFont(fieldFont);
        textNombre.setBounds(252, 93, 300, 35);
        textNombre.setBackground(new Color(230, 230, 230));
        textNombre.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPane.add(textNombre);

        // Primer apellido
        JLabel lblApellido1 = new JLabel("Primer apellido:");
        lblApellido1.setForeground(Color.WHITE);
        lblApellido1.setFont(labelFont);
        lblApellido1.setBounds(72, 141, 150, 25);
        contentPane.add(lblApellido1);

        textApellido1 = new JTextField();
        textApellido1.setFont(fieldFont);
        textApellido1.setBounds(252, 138, 300, 35);
        textApellido1.setBackground(new Color(230, 230, 230));
        textApellido1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPane.add(textApellido1);

        // Segundo apellido
        JLabel lblApellido2 = new JLabel("Segundo apellido:");
        lblApellido2.setForeground(Color.WHITE);
        lblApellido2.setFont(labelFont);
        lblApellido2.setBounds(72, 186, 180, 25);
        contentPane.add(lblApellido2);

        textApellido2 = new JTextField();
        textApellido2.setFont(fieldFont);
        textApellido2.setBounds(252, 183, 300, 35);
        textApellido2.setBackground(new Color(230, 230, 230));
        textApellido2.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPane.add(textApellido2);

        // Correo electrónico
        JLabel lblCorreo = new JLabel("Correo electrónico:");
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setFont(labelFont);
        lblCorreo.setBounds(72, 231, 200, 25);
        contentPane.add(lblCorreo);

        textCorreo = new JTextField();
        textCorreo.setFont(fieldFont);
        textCorreo.setBounds(252, 228, 300, 35);
        textCorreo.setBackground(new Color(230, 230, 230));
        textCorreo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPane.add(textCorreo);

        // Contraseña
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setForeground(Color.WHITE);
        lblContraseña.setFont(labelFont);
        lblContraseña.setBounds(72, 276, 150, 25);
        contentPane.add(lblContraseña);

        textContraseña = new JTextField();
        textContraseña.setFont(fieldFont);
        textContraseña.setBounds(252, 273, 300, 35);
        textContraseña.setBackground(new Color(230, 230, 230));
        textContraseña.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPane.add(textContraseña);

        // Fecha de nacimiento
        JLabel lblFechaNac = new JLabel("Fecha de nacimiento:");
        lblFechaNac.setForeground(Color.WHITE);
        lblFechaNac.setFont(labelFont);
        lblFechaNac.setBounds(72, 321, 200, 25);
        contentPane.add(lblFechaNac);

        textFechaNac = new JTextField();
        textFechaNac.setFont(fieldFont);
        textFechaNac.setBounds(252, 318, 300, 35);
        textFechaNac.setBackground(new Color(230, 230, 230));
        textFechaNac.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPane.add(textFechaNac);

        // Botón Registrarse
        JButton btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnRegistrarse.setBackground(new Color(50, 50, 50));
        btnRegistrarse.setForeground(Color.WHITE);
        btnRegistrarse.setFocusPainted(false);
        btnRegistrarse.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
        btnRegistrarse.setBounds(268, 381, 130, 30);
        contentPane.add(btnRegistrarse);

        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnVolver.setBackground(new Color(50, 50, 50));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
        btnVolver.setBounds(26, 26, 100, 30);
        contentPane.add(btnVolver);

        // Acción del botón Registrarse
        btnRegistrarse.addActionListener(e -> {
            String nombre = textNombre.getText().trim();
            String apellido1 = textApellido1.getText().trim();
            String apellido2 = textApellido2.getText().trim();
            String correo = textCorreo.getText().trim();
            String contraseña = textContraseña.getText().trim();
            String fechaNac = textFechaNac.getText().trim();

            if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() ||
                correo.isEmpty() || contraseña.isEmpty() || fechaNac.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ningún campo puede estar vacío", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (!SoloTexto(nombre) || !SoloTexto(apellido1) || !SoloTexto(apellido2)) {
                JOptionPane.showMessageDialog(null, "Nombre y apellidos no pueden contener números ni símbolos", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (!CorreoValido(correo)) {
                JOptionPane.showMessageDialog(null, "El correo electrónico no es válido", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (!ContraseñaValida(contraseña)) {
                JOptionPane.showMessageDialog(null, "La contraseña debe contener al menos una mayúscula y un número", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                this.setVisible(false);
                Inicio nuevo = new Inicio("Usuario registrado con éxito");
                nuevo.setVisible(true);
            }
        });

        // Acción del botón Volver
        btnVolver.addActionListener(e -> {
            this.setVisible(false);
            Inicio nuevo = new Inicio("");
            nuevo.setVisible(true);
        });
    }

    // Métodos de validación
    private boolean SoloTexto(String text) {
        if (text == null || text.isBlank()) return false;
        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') return false;
        }
        return true;
    }

    private boolean CorreoValido(String email) {
        if (email == null || email.isBlank()) return false;
        int atIndex = email.indexOf('@');
        int lastAtIndex = email.lastIndexOf('@');
        if (atIndex <= 0 || atIndex != lastAtIndex) return false;
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);
        if (localPart.isEmpty() || domainPart.isEmpty()) return false;
        if (!domainPart.contains(".") || domainPart.startsWith(".") || domainPart.endsWith(".")) return false;
        if (email.contains(" ")) return false;
        return true;
    }

    private boolean ContraseñaValida(String contraseña) {
        if (contraseña == null || contraseña.isEmpty()) return false;
        boolean hasUpper = false, hasDigit = false;
        for (char c : contraseña.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            if (Character.isDigit(c)) hasDigit = true;
            if (hasUpper && hasDigit) return true;
        }
        return hasUpper && hasDigit;
    }
}

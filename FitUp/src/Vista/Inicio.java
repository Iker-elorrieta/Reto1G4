package Vista;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Inicio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    BufferedImage mImagen = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Inicio frame = new Inicio(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Inicio(String registrado) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 648, 478);

        // Panel con degradado en gris
        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                // Degradado vertical
                Color color1 = new Color(15, 15, 15);   
                Color color2 = new Color(70, 70, 70); 
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
        ImageIcon iconoEscalado = new ImageIcon(imagen.getScaledInstance(350, 200, Image.SCALE_SMOOTH));
        lblLogo.setIcon(iconoEscalado);
        lblLogo.setBounds(220, 78, 350, 173);
        contentPane.add(lblLogo);

        // Título
        JLabel lblTitulo = new JLabel("Bienvenido a ");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setBounds(176, 143, 300, 50);
        contentPane.add(lblTitulo);

        // Botones
        JButton btnInicioSesion = new JButton("Iniciar sesion");
        btnInicioSesion.setBounds(160, 275, 124, 23);
        contentPane.add(btnInicioSesion);
        btnInicioSesion.setBackground(new Color(50, 50, 50));
        btnInicioSesion.setForeground(Color.WHITE);

        JButton btnNuevoUsuario = new JButton("Nuevo Usuario");
        btnNuevoUsuario.setBounds(349, 275, 124, 23);
        contentPane.add(btnNuevoUsuario);
        btnNuevoUsuario.setBackground(new Color(50, 50, 50));
        btnNuevoUsuario.setForeground(Color.WHITE);
        
        JLabel lblRegistrado = new JLabel("New label");
        lblRegistrado.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblRegistrado.setForeground(new Color(0, 132, 0));
        lblRegistrado.setBounds(234, 227, 172, 14);
        contentPane.add(lblRegistrado);
        
        lblRegistrado.setText(registrado);
        
        btnNuevoUsuario.addActionListener(e -> {
        	this.setVisible(false);
        	NuevoUsuario nuevo = new NuevoUsuario();
        	nuevo.setVisible(true);
        });
        
        btnInicioSesion.addActionListener(e -> {
        	this.setVisible(false);
        	InicioSesion nuevo = new InicioSesion();
        	nuevo.setVisible(true);
        });
    }
}

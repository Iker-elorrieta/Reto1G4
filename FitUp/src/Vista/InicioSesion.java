package Vista;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controlador.Controlador;

import Modelo.*;
public class InicioSesion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNombre;
	private JPasswordField textFieldContraseña;
Controlador controlador1 = new Controlador();
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				InicioSesion frame = new InicioSesion();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@SuppressWarnings("unused")
	public InicioSesion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 478);
		setLocationRelativeTo(null);
		setResizable(false);

		// Panel con degradado oscuro y estilo elegante
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
		lblLogo.setBounds(257, 63, 250, 116);
		contentPane.add(lblLogo);

		// Label Usuario
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsuario.setBounds(140, 190, 150, 30);
		contentPane.add(lblUsuario);

		// Campo Usuario
		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldNombre.setBounds(280, 190, 270, 35);
		textFieldNombre.setBackground(new Color(230, 230, 230));
		textFieldNombre.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textFieldNombre);
		String phUsuario = "Introduzca el nombre de usuario";
		setPlaceholder(textFieldNombre, phUsuario, Color.gray);

		// Label Contraseña
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(Color.WHITE);
		lblContraseña.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblContraseña.setBounds(140, 250, 150, 30);
		contentPane.add(lblContraseña);

		// Campo Contraseña
		textFieldContraseña = new JPasswordField();
		textFieldContraseña.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldContraseña.setBounds(280, 250, 270, 35);
		textFieldContraseña.setBackground(new Color(230, 230, 230));
		textFieldContraseña.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textFieldContraseña);
		String phContraseña = "Introduzca su contraseña";
		setPlaceholder(textFieldContraseña, phContraseña, Color.gray);

		// Botón Iniciar sesión
		JButton btnInicioSesion = new JButton("Iniciar sesión");
		btnInicioSesion.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnInicioSesion.setBackground(new Color(50, 50, 50));
		btnInicioSesion.setForeground(Color.WHITE);
		btnInicioSesion.setFocusPainted(false);
		btnInicioSesion.setBounds(335, 322, 150, 40);
		btnInicioSesion.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
		contentPane.add(btnInicioSesion);

		// Botón Volver
		JButton btnVolver = new JButton("Volver");
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnVolver.setBackground(new Color(50, 50, 50));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFocusPainted(false);
		btnVolver.setBounds(26, 26, 100, 30);
		btnVolver.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
		contentPane.add(btnVolver);

		// Acción del botón Volver
		btnVolver.addActionListener(e -> {
			this.setVisible(false);
			Inicio nuevo = new Inicio("");
			nuevo.setVisible(true);
		});

		// Acción del botón Iniciar sesión
		btnInicioSesion.addActionListener(e -> {
			String nombre = textFieldNombre.getText().trim();
			String contraseña = new String(textFieldContraseña.getPassword()).trim();

			if (nombre.trim().isEmpty() || contraseña.trim().isEmpty()) {
				System.out.println("Rellena ambos campos");
			} else if (!soloTexto(nombre) || (!contraseñaValida(contraseña))) {
					
					System.out.println("No validos");
				
			} else {
				try {
					usuario usuario1 = new usuario();
					usuario1.setNombre(nombre);
					usuario1.setContraseña(contraseña);
					 
			
					if (controlador1.inicioSesion(usuario1)) {
					this.setVisible(false);
					ListadoWorkouts nuevo = new ListadoWorkouts();
					nuevo.setVisible(true);
					}else {
				System.out.println("Incorrectos");
					
					}
					}catch(Exception e1) {
				e1.printStackTrace();
			}
		}});
	}

	public static void setPlaceholder(JTextField field, String placeholder, Color color) {
		field.setForeground(color);
		field.setText(placeholder);

		field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (field.getText().equals(placeholder)) {
					field.setText("");
					field.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (field.getText().isEmpty()) {
					field.setForeground(Color.GRAY);
					field.setText(placeholder);
				}
			}
		});
	}

	private boolean contraseñaValida(String contraseña) {
		if (contraseña == null || contraseña.isEmpty())
			return false;
		boolean hasUpper = false, hasDigit = false;
		for (char c : contraseña.toCharArray()) {
			if (Character.isUpperCase(c))
				hasUpper = true;
			if (Character.isDigit(c))
				hasDigit = true;
			if (hasUpper && hasDigit)
				return true;
		}
		return hasUpper && hasDigit;
	}

	private boolean soloTexto(String text) {

		return text != null && text.matches("^[a-zA-Z]+$");
	}

}

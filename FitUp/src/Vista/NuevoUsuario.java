package Vista;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
		lblLogo.setBounds(252, -15, 300, 102);
		contentPane.add(lblLogo);

		Font labelFont = new Font("Tahoma", Font.BOLD, 16);
		Font fieldFont = new Font("Tahoma", Font.PLAIN, 15);

		// Nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(Color.WHITE);
		lblNombre.setFont(labelFont);
		lblNombre.setBounds(46, 98, 150, 25);
		
		contentPane.add(lblNombre);

		textNombre = new JTextField();
		textNombre.setFont(fieldFont);
		textNombre.setBounds(252, 93, 300, 35);
		textNombre.setBackground(new Color(230, 230, 230));
		textNombre.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		textNombre.setForeground(Color.GRAY);
		String placeholderNombre = ("Introduzca su nombre...");
		textNombre.setText(placeholderNombre);
		contentPane.add(textNombre);
		setPlaceholder(textNombre, placeholderNombre,Color.gray);
       

		// Primer apellido
		JLabel lblApellido1 = new JLabel("Primer apellido:");
		lblApellido1.setForeground(Color.WHITE);
		lblApellido1.setFont(labelFont);
		lblApellido1.setBounds(46, 143, 150, 25);
		contentPane.add(lblApellido1);

		textApellido1 = new JTextField();
		textApellido1.setFont(fieldFont);
		textApellido1.setBounds(252, 138, 300, 35);
		textApellido1.setBackground(new Color(230, 230, 230));
		textApellido1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		String placeholderApellido = ("Introduzca su primer apellido...");
		contentPane.add(textApellido1);
		setPlaceholder(textApellido1, placeholderApellido,Color.gray);

	
		// Segundo apellido
		JLabel lblApellido2 = new JLabel("Segundo apellido:");
		lblApellido2.setForeground(Color.WHITE);
		lblApellido2.setFont(labelFont);
		lblApellido2.setBounds(46, 188, 180, 25);
		contentPane.add(lblApellido2);

		textApellido2 = new JTextField();
		textApellido2.setFont(fieldFont);
		textApellido2.setBounds(252, 183, 300, 35);
		textApellido2.setBackground(new Color(230, 230, 230));
		textApellido2.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textApellido2);
		String placeholderApellido2 = "Introduce tu segundo apellido...";
		setPlaceholder(textApellido2, placeholderApellido2,Color.gray);


		// Correo electrónico
		JLabel lblCorreo = new JLabel("Correo electrónico:");
		lblCorreo.setForeground(Color.WHITE);
		lblCorreo.setFont(labelFont);
		lblCorreo.setBounds(46, 233, 200, 25);
		contentPane.add(lblCorreo);

		textCorreo = new JTextField();
		textCorreo.setFont(fieldFont);
		textCorreo.setBounds(252, 228, 300, 35);
		textCorreo.setBackground(new Color(230, 230, 230));
		textCorreo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textCorreo);
		String placeholderCorreo = "Introduce tu correo electrónico...";
		setPlaceholder(textCorreo, placeholderCorreo, Color.gray);

		// Contraseña
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(Color.WHITE);
		lblContraseña.setFont(labelFont);
		lblContraseña.setBounds(46, 278, 150, 25);
		contentPane.add(lblContraseña);

		textContraseña = new JTextField();
		textContraseña.setFont(fieldFont);
		textContraseña.setBounds(252, 273, 300, 35);
		textContraseña.setBackground(new Color(230, 230, 230));
		textContraseña.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textContraseña);
		String placeholdercontraseña = "Introduce tu contraseña...";
		setPlaceholder(textContraseña, placeholdercontraseña,Color.gray);


		// Fecha de nacimiento
		JLabel lblFechaNac = new JLabel("Fecha de nacimiento:");
		lblFechaNac.setForeground(Color.WHITE);
		lblFechaNac.setFont(labelFont);
		lblFechaNac.setBounds(46, 323, 200, 25);
		contentPane.add(lblFechaNac);

		textFechaNac = new JTextField();
		textFechaNac.setFont(fieldFont);
		textFechaNac.setBounds(252, 318, 300, 35);
		textFechaNac.setBackground(new Color(230, 230, 230));
		textFechaNac.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textFechaNac);
		String fecha = "Introduce tu fecha de nacimiento";
		setPlaceholder(textFechaNac, fecha,Color.gray);


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

		JLabel lblVacio = new JLabel("Ningún campo puede estar vacio");
		lblVacio.setForeground(new Color(255, 0, 0));
		lblVacio.setBounds(252, 73, 310, 14);
		contentPane.add(lblVacio);
		lblVacio.setVisible(false);

		JLabel lblMsgError = new JLabel("");
		lblMsgError.setForeground(new Color(255, 0, 0));
		lblMsgError.setBounds(262, 103, 290, 18);
		contentPane.add(lblMsgError);


		// Acción del botón Registrarse
		btnRegistrarse.addActionListener(e -> {
			String nombre = textNombre.getText().trim();
			String apellido1 = textApellido1.getText().trim();
			String apellido2 = textApellido2.getText().trim();
			String correo = textCorreo.getText().trim();
			String contraseña = textContraseña.getText().trim();
			String fechaNac = textFechaNac.getText().trim();
			String error = "Formato de ";
			String errorCorreo = "Formato de correo incorrecto";
			String errorContraseña = "Sssssres";

			
		   
			
			if (nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() || correo.isEmpty()
					|| contraseña.isEmpty() || fechaNac.isEmpty()) {
				lblVacio.setVisible(true);
				
			} else if (!SoloTexto(nombre) || !SoloTexto(apellido1) || !SoloTexto(apellido2)) {
				if (!SoloTexto(nombre)) {					
					textNombre.setText(error);
					setPlaceholder(textNombre, error,Color.RED);
					
				} else if (!SoloTexto(apellido1)) {
					textApellido1.setText(error);
					setPlaceholder(textApellido1, error,Color.RED);					
				}else if(!SoloTexto(apellido2)) {
					textApellido2.setText(error);
					setPlaceholder(textApellido2, error,Color.RED);
				}
			} else if (!CorreoValido(correo)) {
				textCorreo.setText(errorCorreo);
				setPlaceholder(textCorreo, errorCorreo,Color.RED);
			} else if (!ContraseñaValida(contraseña)) {
				textContraseña.setText(errorContraseña);
				setPlaceholder(textContraseña, errorContraseña,Color.RED);
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
		if (text == null || text.isBlank())
			return false;
		for (char c : text.toCharArray()) {
			if (!Character.isLetter(c) && c != ' ')
				return false;
		}
		return true;
	}

	private boolean CorreoValido(String email) {
		if (email == null || email.isBlank())
			return false;
		int atIndex = email.indexOf('@');
		int lastAtIndex = email.lastIndexOf('@');
		if (atIndex <= 0 || atIndex != lastAtIndex)
			return false;
		String localPart = email.substring(0, atIndex);
		String domainPart = email.substring(atIndex + 1);
		if (localPart.isEmpty() || domainPart.isEmpty())
			return false;
		if (!domainPart.contains(".") || domainPart.startsWith(".") || domainPart.endsWith("."))
			return false;
		if (email.contains(" "))
			return false;
		return true;
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
	private boolean ContraseñaValida(String contraseña) {
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
}

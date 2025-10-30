package Vista;

import java.awt.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controlador.Controlador;
import Modelo.*;

public class NuevoUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textApellido1;
	private JTextField textApellido2;
	private JTextField textCorreo;
	private JTextField textContraseña;
	private JTextField textFechaNac;
	
	Usuario usuario = new Usuario();


	public NuevoUsuario(Controlador controlador) {
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
		textNombre.setBounds(252, 93, 347, 35);
		textNombre.setBackground(new Color(230, 230, 230));
		textNombre.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		textNombre.setForeground(Color.GRAY);
		String placeholderNombre = ("Introduzca su nombre...");
		contentPane.add(textNombre);
		controlador.setPlaceholder(textNombre, placeholderNombre,Color.gray);
       

		// Primer apellido
		JLabel lblApellido1 = new JLabel("Primer apellido:");
		lblApellido1.setForeground(Color.WHITE);
		lblApellido1.setFont(labelFont);
		lblApellido1.setBounds(46, 143, 150, 25);
		contentPane.add(lblApellido1);

		textApellido1 = new JTextField();
		textApellido1.setFont(fieldFont);
		textApellido1.setBounds(252, 138, 347, 35);
		textApellido1.setBackground(new Color(230, 230, 230));
		textApellido1.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		String placeholderApellido = ("Introduzca su primer apellido...");
		contentPane.add(textApellido1);
		controlador.setPlaceholder(textApellido1, placeholderApellido,Color.gray);

	
		// Segundo apellido
		JLabel lblApellido2 = new JLabel("Segundo apellido:");
		lblApellido2.setForeground(Color.WHITE);
		lblApellido2.setFont(labelFont);
		lblApellido2.setBounds(46, 188, 180, 25);
		contentPane.add(lblApellido2);

		textApellido2 = new JTextField();
		textApellido2.setFont(fieldFont);
		textApellido2.setBounds(252, 183, 347, 35);
		textApellido2.setBackground(new Color(230, 230, 230));
		textApellido2.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textApellido2);
		String placeholderApellido2 = "Introduce tu segundo apellido...";
		controlador.setPlaceholder(textApellido2, placeholderApellido2,Color.gray);


		// Correo electrónico
		JLabel lblCorreo = new JLabel("Correo electrónico:");
		lblCorreo.setForeground(Color.WHITE);
		lblCorreo.setFont(labelFont);
		lblCorreo.setBounds(46, 233, 200, 25);
		contentPane.add(lblCorreo);

		textCorreo = new JTextField();
		textCorreo.setFont(fieldFont);
		textCorreo.setBounds(252, 228, 347, 35);
		textCorreo.setBackground(new Color(230, 230, 230));
		textCorreo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textCorreo);
		String placeholderCorreo = "Introduce tu correo electrónico...";
		controlador.setPlaceholder(textCorreo, placeholderCorreo, Color.gray);

		// Contraseña
		JLabel lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setForeground(Color.WHITE);
		lblContraseña.setFont(labelFont);
		lblContraseña.setBounds(46, 278, 150, 25);
		contentPane.add(lblContraseña);

		textContraseña = new JPasswordField();
		textContraseña.setFont(fieldFont);
		textContraseña.setBounds(252, 273, 347, 35);
		textContraseña.setBackground(new Color(230, 230, 230));
		textContraseña.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textContraseña);
		String placeholdercontraseña = "Introduce tu contraseña...";
		controlador.setPlaceholder(textContraseña, placeholdercontraseña,Color.gray);

		// Fecha de nacimiento
		JLabel lblFechaNac = new JLabel("Fecha de nacimiento:");
		lblFechaNac.setForeground(Color.WHITE);
		lblFechaNac.setFont(labelFont);
		lblFechaNac.setBounds(46, 323, 200, 25);
		contentPane.add(lblFechaNac);

		textFechaNac = new JTextField();
		textFechaNac.setFont(fieldFont);
		textFechaNac.setBounds(252, 318, 347, 35);
		textFechaNac.setBackground(new Color(230, 230, 230));
		textFechaNac.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		contentPane.add(textFechaNac);
		String placeholderfecha = "Introduce tu fecha de nacimiento";
		controlador.setPlaceholder(textFechaNac, placeholderfecha,Color.gray);


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

		    lblVacio.setVisible(false); // Ocultar aviso inicial

		    boolean todosVacios = (nombre.isEmpty() || nombre.equals("Introduzca su nombre...")) ||
		                          (apellido1.isEmpty() || apellido1.equals("Introduzca su primer apellido...")) ||
		                          (apellido2.isEmpty() || apellido2.equals("Introduce tu segundo apellido...")) ||
		                          (correo.isEmpty() || correo.equals("Introduce tu correo electrónico...")) ||
		                          (contraseña.isEmpty() || contraseña.equals("Introduce tu contraseña...")) ||
		                          (fechaNac.isEmpty() || fechaNac.equals("Introduce tu fecha de nacimiento"));

		    if (todosVacios) {
		        lblVacio.setVisible(true);
		        return; 
		    } else {

		        if (!controlador.soloTexto(nombre)) {
		        	controlador.setPlaceholder(textNombre, "Formato de texto incorrecto", Color.RED);
		        } else if (!controlador.soloTexto(apellido1)) {
		        	controlador.setPlaceholder(textApellido1, "Formato de texto incorrecto", Color.RED);
		        } else if (!controlador.soloTexto(apellido2)) {
		        	controlador. setPlaceholder(textApellido2, "Formato de texto incorrecto", Color.RED);
		        } else {
		            try {
		                if (!controlador.correoValido(correo)) {
		                	controlador.setPlaceholder(textCorreo, "Formato de correo incorrecto o ya registrado", Color.RED);
		                } else if (!controlador.contraseñaValida(contraseña)) {
		                    lblVacio.setText("1 número, 1 Mayus, 1 minus MINIMO");
		                    lblVacio.setVisible(true);
		                    controlador.setPlaceholder(textContraseña, "Formato de contraseña incorrecto", Color.RED);
		                } else if (!controlador.formatoFechaValido(fechaNac)) {
		                	controlador.setPlaceholder(textFechaNac, "Formato de fecha incorrecto (dd/MM/yyyy)", Color.RED);
		                } else if (!controlador.fechaNoFutura(fechaNac)) {
		                	controlador.setPlaceholder(textFechaNac, "La fecha no puede ser posterior a hoy", Color.RED);
		                } else {
		                    usuario.setNombre(nombre);
		                    usuario.setApellido1(apellido1);
		                    usuario.setApellido2(apellido2);
		                    usuario.setContraseña(contraseña);
		                    usuario.setCorreo(correo);
		                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    		        LocalDate fechaLocal = LocalDate.parse(fechaNac, formato);
		    		        Date fechaNacDate = java.sql.Date.valueOf(fechaLocal);
		    		    	usuario.setFechaNac(fechaNacDate);
		                    usuario.setNivel(0);

		                    try {
		                        controlador.nuevoUsuario(usuario);
		                    } catch (Exception e1) {
		                        e1.printStackTrace();
		                    }

		                    this.setVisible(false);
		                    Inicio nuevo = new Inicio("Usuario registrado con éxito", controlador);
		                    nuevo.setVisible(true);
		                }
		            } catch (Exception e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		}); 

		// Acción del botón Volver
		btnVolver.addActionListener(ex -> {
		    this.setVisible(false);
		    Inicio nuevo = new Inicio("", controlador);
		    nuevo.setVisible(true);
		});
	}


}

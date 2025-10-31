package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controlador.Controlador;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HacerEjercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCronometroWorkout;
	private JTextField txtDescEjer;
	private JTextField txtNomWorkout;
	private JTextField txtTiempoEjer;
	private JTextField txtDescanso;


	/**
	 * Create the frame.
	 */
	public HacerEjercicio(Controlador controlador) {
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
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSalir.setFocusPainted(false);
		btnSalir.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
		btnSalir.setBackground(new Color(50, 50, 50));
		btnSalir.setBounds(522, 398, 100, 30);
		contentPane.add(btnSalir);
		
		txtCronometroWorkout = new JTextField();
		txtCronometroWorkout.setText("Cronometro Workout");
		txtCronometroWorkout.setBounds(10, 11, 214, 20);
		contentPane.add(txtCronometroWorkout);
		txtCronometroWorkout.setColumns(10);
		
		txtDescEjer = new JTextField();
		txtDescEjer.setColumns(10);
		txtDescEjer.setBounds(234, 11, 214, 20);
		contentPane.add(txtDescEjer);
		
		txtNomWorkout = new JTextField();
		txtNomWorkout.setColumns(10);
		txtNomWorkout.setBounds(458, 11, 153, 20);
		contentPane.add(txtNomWorkout);
		
		txtTiempoEjer = new JTextField();
		txtTiempoEjer.setText("Tiempo ejercicio: ");
		txtTiempoEjer.setColumns(10);
		txtTiempoEjer.setBounds(10, 53, 146, 20);
		contentPane.add(txtTiempoEjer);
		
		txtDescanso = new JTextField();
		txtDescanso.setText("Descanso");
		txtDescanso.setColumns(10);
		txtDescanso.setBounds(10, 84, 146, 57);
		contentPane.add(txtDescanso);
		
		JPanel panel = new JPanel();
		panel.setBounds(218, 129, 214, 88);
		contentPane.add(panel);

	}
}

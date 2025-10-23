package Vista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controlador.Controlador;
import Modelo.Ejercicio;
import Modelo.Series;
import Modelo.Workout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ListadoEjercicios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel modeloTabla;
	private JTable table;
	private ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();
	private ArrayList<Series> listaSeries = new ArrayList<>();


	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 * @param idEjercicio 
	 * @throws Exception 
	 */
	public ListadoEjercicios(Controlador controlador, String idEjercicio) throws Exception {
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

		// Tabla

		String[] columnas = { "Nombre", "Nº series", "Descanso" };
		modeloTabla = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // No permitir edición directa
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				switch (columnIndex) {
				case 1:
				case 2:
					return Integer.class;
				default:
					return String.class;
				}
			}
		};
		table = new JTable(modeloTabla);
		table.setBounds(35, 123, 565, 248);
		table.setBackground(new Color(40, 40, 40));
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setRowHeight(25);
		table.setGridColor(new Color(70, 70, 70));
		

		// Color y fuente del encabezado
		table.getTableHeader().setBackground(new Color(30, 30, 30));
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.getTableHeader().setReorderingAllowed(false);
		
		// Selección de filas
		table.setSelectionBackground(new Color(60, 120, 180));
		table.setSelectionForeground(Color.WHITE);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(36, 123, 565, 294);
		contentPane.add(scrollPane);
		
		listadoEjercicios(controlador, idEjercicio);
		listadoSeries(controlador, idEjercicio);
		
		//Boton volver
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnVolver.setFocusPainted(false);
		btnVolver.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
		btnVolver.setBackground(new Color(50, 50, 50));
		btnVolver.setBounds(25, 25, 100, 30);
		contentPane.add(btnVolver);

		// Logo
		JLabel lblLogo = new JLabel();
		ImageIcon icono = new ImageIcon("FitUp_Logo_SinFondo.png");
		Image imagen = icono.getImage();
		ImageIcon iconoEscalado = new ImageIcon(imagen.getScaledInstance(300, 150, Image.SCALE_SMOOTH));
		lblLogo.setIcon(iconoEscalado);
		lblLogo.setBounds(382, -29, 250, 116);
		contentPane.add(lblLogo);

		//Label 
		JLabel lblListaEjercicios = new JLabel("Lista de los ejercicios del workout");
		lblListaEjercicios.setForeground(Color.WHITE);
		lblListaEjercicios.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblListaEjercicios.setBounds(35, 76, 540, 30);
		contentPane.add(lblListaEjercicios);

		//Boton inicio
		JButton btnInicio = new JButton("Inicio");
		btnInicio.setForeground(Color.WHITE);
		btnInicio.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnInicio.setFocusPainted(false);
		btnInicio.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
		btnInicio.setBackground(new Color(50, 50, 50));
		btnInicio.setBounds(263, 380, 100, 30);
		contentPane.add(btnInicio);

		// Boton volver funcionalidad
		btnVolver.addActionListener(e -> {
			this.setVisible(false);
			ListadoWorkouts listado = null;
			try {
				listado = new ListadoWorkouts(controlador, "");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			  ListadoEjercicios.this.dispose();
			listado.setVisible(true);
		});}
	
		private void listadoEjercicios(Controlador controlador, String idEjercicio) throws Exception {
			listaEjercicios = controlador.listarEjercicios(idEjercicio);
		    modeloTabla.setRowCount(0);
		    if (!listaEjercicios.isEmpty()) {
		        for (int i = 0; i < listaEjercicios.size(); i++) {
		            Ejercicio e = listaEjercicios.get(i);
		            Object[] fila = { 
		                e.getNombre(), 
		                e.getNumSeries(), 
		                e.getDescanso(), 
		            };
		            modeloTabla.addRow(fila);
		            table.repaint();
		        }
		    } else {
		        System.out.println("No hay workouts disponibles.");
		    }
	}
		
		 public void listadoSeries(Controlador controlador, String idEjercicio) throws Exception {
		    	listaSeries = controlador.listarSeries(idEjercicio);
		    	System.out.println(listaSeries);
		    }
}

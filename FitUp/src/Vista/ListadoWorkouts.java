package Vista;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controlador.Controlador;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.awt.Font;
import javax.swing.JTable;

import Modelo.*;

public class ListadoWorkouts extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modeloTabla; 
	private ArrayList<workout> listaWorkouts = new ArrayList<>();
	Controlador controlador1 = new Controlador();
	private usuario usuario1 = controlador1.workoutsId();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
		
					ListadoWorkouts frame;
					try {
						frame = new ListadoWorkouts();

					
					frame.setVisible(true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public ListadoWorkouts() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 648, 478);
        
        // Panel con degradado oscuro 
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
        
        JLabel lblWorkout = new JLabel("Lista de tus workouts, selecciona el deseado");
        lblWorkout.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblWorkout.setForeground(Color.WHITE);
        lblWorkout.setBounds(36, 82, 540, 30);
        contentPane.add(lblWorkout);
        
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
        btnCerrarSesion.setBackground(new Color(50, 50, 50));
        btnCerrarSesion.setBounds(25, 26, 100, 30);
        contentPane.add(btnCerrarSesion);
        
        //Tabla
        
        String[] columnas = {"Nombre", "Nº ejercicios", "Nivel", "Video"};
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
        
		
		agregarWorkout();
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = table.rowAtPoint(e.getPoint());
				int columna = table.columnAtPoint(e.getPoint());

				if (columna == 3) { // Columna URL
					 workout w = listaWorkouts.get(fila); 
					String url = w.getURL();
					try {
						Desktop.getDesktop().browse(new URI(url));
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		// Cambiar cursor al pasar sobre la URL
		table.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int columna = table.columnAtPoint(e.getPoint());
				if (columna == 3) {
					table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
				} else {
					table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
				}
			}
		});

        

        // Logo
        JLabel lblLogo = new JLabel();
        ImageIcon icono = new ImageIcon("FitUp_Logo_SinFondo.png");
        Image imagen = icono.getImage();
        ImageIcon iconoEscalado = new ImageIcon(imagen.getScaledInstance(300, 150, Image.SCALE_SMOOTH));
        lblLogo.setIcon(iconoEscalado);
        lblLogo.setBounds(382, -29, 250, 116);
        contentPane.add(lblLogo);
        
	}
	
	private void agregarWorkout() throws Exception {
	    listaWorkouts = controlador1.listarWorkouts();

	    if (!listaWorkouts.isEmpty()) {
	        for (int i = 0; i < listaWorkouts.size(); i++) {
	            workout w = listaWorkouts.get(i);

	            Object[] fila = { 
	                w.getNombre(), 
	                w.getNumEjercicios(), 
	                w.getNivel(), 
	                "URL tutorial" 
	            };

	            modeloTabla.addRow(fila);
	        }
	    } else {
	        System.out.println("No hay workouts disponibles.");
	    }
	}

}

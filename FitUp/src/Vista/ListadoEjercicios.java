package Vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Controlador.Controlador;
import Modelo.Ejercicio;

import java.util.ArrayList;

public class ListadoEjercicios extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private DefaultTableModel modeloTabla;
    private JTable table;
    private ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();

    @SuppressWarnings("unused")
	private String idWorkout;
    private String nombreWorkout;

    public ListadoEjercicios(Controlador controlador, String idWorkout) throws Exception {
        this.idWorkout = idWorkout;

        // Obtiene el nombre del workout desde el controlador (si tu controlador tiene ese método)
        try {
        	this.nombreWorkout = "Workout " + idWorkout;
        } catch (Exception e) {
            this.nombreWorkout = "Workout";
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 648, 478);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("FitUp - Lista de ejercicios");

        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 10), 0, height, new Color(60, 60, 60));
                g2.setPaint(gp);
                g2.fillRect(0, 0, width, height);
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Tabla
        String[] columnas = {"Nombre", "Nº series", "Descanso"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
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
        table.setBackground(new Color(40, 40, 40));
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(60, 120, 180));
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setBackground(new Color(30, 30, 30));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(36, 123, 565, 240);
        contentPane.add(scrollPane);

        // Cargar ejercicios
        listadoEjercicios(controlador, idWorkout);

        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
        btnVolver.setBackground(new Color(50, 50, 50));
        btnVolver.setBounds(25, 25, 100, 30);
        contentPane.add(btnVolver);

        btnVolver.addActionListener(e -> {
            this.dispose();
            try {
                new ListadoWorkouts(controlador, "").setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
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

        // Título
        JLabel lblListaEjercicios = new JLabel("Ejercicios del workout");
        lblListaEjercicios.setForeground(Color.WHITE);
        lblListaEjercicios.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblListaEjercicios.setBounds(35, 76, 540, 30);
        contentPane.add(lblListaEjercicios);

        // Botón Iniciar
        JButton btnInicio = new JButton("Iniciar workout");
        btnInicio.setForeground(Color.WHITE);
        btnInicio.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnInicio.setFocusPainted(false);
        btnInicio.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
        btnInicio.setBackground(new Color(60, 120, 180));
        btnInicio.setBounds(250, 385, 140, 35);
        btnInicio.setEnabled(true);
        contentPane.add(btnInicio);

        btnInicio.addActionListener(e -> {
            try {
                // Cargar series de cada ejercicio
                for (Ejercicio ej : listaEjercicios) {
                    ArrayList<Modelo.Series> series = controlador.listarSeries(idWorkout, String.valueOf(ej.getId()));
                    if (series == null) series = new ArrayList<>();
                    ej.setSeries(series);
                }

                // Abrir HacerEjercicio
                HacerEjercicio hacer = new HacerEjercicio(controlador, idWorkout, listaEjercicios, nombreWorkout);
                hacer.setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al cargar las series del workout.");
            }
        });
    }

    private void listadoEjercicios(Controlador controlador, String idWorkout) throws Exception {
        listaEjercicios = controlador.listarEjercicios(idWorkout);
        modeloTabla.setRowCount(0);
        for (Ejercicio e : listaEjercicios) {
            Object[] fila = {e.getNombre(), e.getNumSeries(), e.getDescanso()};
            modeloTabla.addRow(fila);
        }
    }
}

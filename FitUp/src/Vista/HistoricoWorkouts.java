package Vista;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import Controlador.Controlador;
import Modelo.Historico;

public class HistoricoWorkouts extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel modeloTabla;

    public HistoricoWorkouts(Controlador controlador) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);

        // Panel con degradado
        contentPane = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 15, 15),
                        0, height, new Color(70, 70, 70));
                g2.setPaint(gp);
                g2.fillRect(0, 0, width, height);
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Botón Volver
        JButton btnVolver = new JButton("Volver");
        btnVolver.setBounds(27, 26, 100, 30);
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(50, 50, 50));
        btnVolver.setFocusPainted(false);
        btnVolver.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnVolver.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 1));
        btnVolver.addActionListener(e -> {
            // Cerrar la ventana actual
            dispose();
            // Aquí puedes abrir otra ventana si quieres
        });
        contentPane.add(btnVolver);

        // Label
        JLabel lblTitulo = new JLabel("Histórico de tus workouts");
        lblTitulo.setBounds(27, 100, 540, 30);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        contentPane.add(lblTitulo);

        // Tabla y modelo
        String[] columnas = {"Nombre", "Nivel", "Tiempo total", "Tiempo previsto", "Fecha", "%Completado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1: case 2: case 4: case 5: case 7:
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
        table.setGridColor(new Color(70, 70, 70));
        table.getTableHeader().setBackground(new Color(30, 30, 30));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(60, 120, 180));
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(36, 140, 700, 300);
        contentPane.add(scrollPane);

        // Cargar datos en la tabla
        try {
            agregarHistorico(controlador, controlador.getUsuarioActual().getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void agregarHistorico(Controlador controlador, int idUsuario) throws Exception {
        ArrayList<Historico> historico = controlador.listarHistorico(idUsuario);
        modeloTabla.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (Historico h : historico) {
            // Formato fecha
            String fechaFormateada = h.getFecha() != null ? sdf.format(h.getFecha()) : "";

            // Formato tiempo total
            int totalSegundos = h.getTiempoTotal();
            int minutos = totalSegundos / 60;
            int segundos = totalSegundos % 60;
            String tiempoFormateado = (minutos < 10 ? "0" : "") + minutos + ":" + (segundos < 10 ? "0" : "") + segundos;

            // Formato tiempo previsto
            int tiempoPrevSegundos = controlador.conseguirTiempoPrevisto(h.getWorkout().getId());
            int minutosPrev = tiempoPrevSegundos / 60;
            int segundosPrev = tiempoPrevSegundos % 60;
            String tiempoPrevFormateado = (minutosPrev < 10 ? "0" : "") + minutosPrev + ":" + (segundosPrev < 10 ? "0" : "") + segundosPrev;

            Object[] fila = {
                    h.getWorkout().getNombre(),
                    h.getWorkout().getNivel(),
                    tiempoFormateado,
                    tiempoPrevFormateado,
                    fechaFormateada,
                    h.getCompletado()
            };
            modeloTabla.addRow(fila);
        }
    }


}

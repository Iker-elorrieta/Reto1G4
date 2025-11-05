package Vista;

import java.awt.*;
import javax.swing.*;

import Controlador.Controlador;

import java.awt.event.*;

public class ResultadoEjercicio extends JFrame {

    private static final long serialVersionUID = 1L;

    public ResultadoEjercicio(String nombreWorkout, int tiempoTotalSeg, int totalEjercicios, int ejerciciosCompletados, Controlador controlador) {
        setTitle("Resultado del Workout");
        setBounds(100, 100, 900, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel content = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 8224966868817033636L;

			@Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(10, 10, 10), 0, h, new Color(50, 50, 50));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), h);
            }
        };
        content.setLayout(null);
        setContentPane(content);

        JLabel lblTitulo = new JLabel("Resumen del Workout: " + nombreWorkout);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setBounds(220, 40, 600, 40);
        content.add(lblTitulo);

        // Tiempo total formateado
        String tiempoTotal = formatHora(tiempoTotalSeg);

        JLabel lblTiempo = new JLabel("⏱ Tiempo total: " + tiempoTotal);
        lblTiempo.setForeground(Color.WHITE);
        lblTiempo.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTiempo.setBounds(274, 137, 400, 30);
        content.add(lblTiempo);

        int porcentaje = (int) ((ejerciciosCompletados * 100.0f) / totalEjercicios);

        JLabel lblPorcentaje = new JLabel("Ejercicios completados: " + ejerciciosCompletados + " / " + totalEjercicios + " (" + porcentaje + "%)");
        lblPorcentaje.setForeground(new Color(255, 255, 255));
        lblPorcentaje.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPorcentaje.setBounds(274, 191, 600, 30);
        content.add(lblPorcentaje);

        // Mensaje motivacional
        String mensaje = obtenerMensajeMotivacional(porcentaje);
        JLabel lblMensaje = new JLabel(mensaje, SwingConstants.CENTER);
        lblMensaje.setForeground(Color.CYAN);
        lblMensaje.setFont(new Font("Tahoma", Font.ITALIC, 22));
        lblMensaje.setBounds(150, 260, 600, 50);
        content.add(lblMensaje);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(370, 400, 150, 40);
        btnAceptar.setBackground(new Color(60, 120, 180));
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 16));
        content.add(btnAceptar);

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    ListadoWorkouts listado = new ListadoWorkouts(controlador, " ");
                    listado.setVisible(true);

                    // Obtener mensaje del cambio de nivel
                    String mensajeNivel = controlador.cambiarNivel();
                    String mensajeNivel2 = controlador.cambiarNivel();
                    //Mostrarlo en el label del ListadoWorkouts
                    listado.mostrarMensajeNivel(mensajeNivel, mensajeNivel2);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    private String obtenerMensajeMotivacional(int porcentaje) {
        if (porcentaje == 100) return "¡Increíble! Has completado todo el workout.";
        if (porcentaje >= 75) return "¡Muy bien! Estás casi en la cima.";
        if (porcentaje >= 50) return "Buen trabajo. Sigue esforzándote.";
        if (porcentaje > 0) return "Has dado el primer paso ¡Sigue adelante!";
        return "No pasa nada. Mañana será mejor.";
    }

    private String formatHora(int totalSeg) {
        int h = totalSeg / 3600;
        int m = (totalSeg % 3600) / 60;
        int s = totalSeg % 60;

        String hora = (h < 10 ? "0" + h : "" + h);
        String min = (m < 10 ? "0" + m : "" + m);
        String seg = (s < 10 ? "0" + s : "" + s);

        return hora + ":" + min + ":" + seg;
    }
}

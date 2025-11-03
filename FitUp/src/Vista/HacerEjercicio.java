package Vista;

import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

import Controlador.Controlador;
import Modelo.Cronometro;
import Modelo.CronometroEjercicio;
import Modelo.CronometroSerie;
import Modelo.CronometroDescanso;
import Modelo.Ejercicio;
import Modelo.Series;

public class HacerEjercicio extends JFrame {

    private static final long serialVersionUID = 1L;

    private Controlador controlador;
    private String idWorkout;
    private String nombreWorkout;
    private ArrayList<Ejercicio> ejercicios;

    // UI
    private JLabel lblTituloEjercicio;
    private JLabel lblNombreWorkout;
    private JLabel lblCronoWorkout;
    private JLabel lblCronoEjercicio;
    private JLabel lblCronoSerie;
    private JLabel lblCronoDescanso;
    private JPanel panelSeries;
    private JButton btnIniciar;
    private JButton btnSalir;

    // Cronómetros
    private Cronometro cronoWorkout;
    private CronometroEjercicio cronoEjercicio;
    private CronometroSerie cronoSerie;
    private CronometroDescanso cronoDescanso;

    // Estado
    private int indiceEjercicio = 0;
    private int indiceSerie = 0;
    private boolean workoutEnCurso = false;
    private boolean pausado = false;
    private boolean enDescanso = false;

    public HacerEjercicio(Controlador controlador, String idWorkout, ArrayList<Ejercicio> ejercicios, String nombreWorkout) {
        this.controlador = controlador;
        this.idWorkout = idWorkout;
        this.ejercicios = (ejercicios != null) ? ejercicios : new ArrayList<>();
        this.nombreWorkout = (nombreWorkout != null && !nombreWorkout.isEmpty()) ? nombreWorkout : "Workout";

        setBounds(100, 100, 900, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel content = new JPanel() {
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

        // Cronómetro total
        lblCronoWorkout = new JLabel("Workout: 00:00:00");
        lblCronoWorkout.setForeground(Color.WHITE);
        lblCronoWorkout.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblCronoWorkout.setBounds(30, 20, 250, 30);
        content.add(lblCronoWorkout);

        lblTituloEjercicio = new JLabel("Ejercicio: -");
        lblTituloEjercicio.setForeground(Color.WHITE);
        lblTituloEjercicio.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTituloEjercicio.setBounds(300, 20, 500, 30);
        content.add(lblTituloEjercicio);

        lblNombreWorkout = new JLabel(nombreWorkout);
        lblNombreWorkout.setForeground(Color.WHITE);
        lblNombreWorkout.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombreWorkout.setBounds(750, 20, 140, 30);
        content.add(lblNombreWorkout);

        lblCronoEjercicio = new JLabel("Ejercicio actual: -");
        lblCronoEjercicio.setForeground(Color.WHITE);
        lblCronoEjercicio.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblCronoEjercicio.setBounds(60, 60, 300, 30);
        content.add(lblCronoEjercicio);

        lblCronoSerie = new JLabel("Serie: -");
        lblCronoSerie.setForeground(Color.WHITE);
        lblCronoSerie.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblCronoSerie.setBounds(330, 60, 300, 30);
        content.add(lblCronoSerie);

        lblCronoDescanso = new JLabel("Descanso: -");
        lblCronoDescanso.setForeground(Color.LIGHT_GRAY);
        lblCronoDescanso.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblCronoDescanso.setBounds(600, 60, 250, 30);
        content.add(lblCronoDescanso);

        panelSeries = new JPanel();
        panelSeries.setLayout(null);
        panelSeries.setBackground(new Color(30, 30, 30));
        panelSeries.setBounds(100, 130, 700, 300);
        content.add(panelSeries);

        btnIniciar = new JButton("Iniciar");
        btnIniciar.setBounds(320, 450, 120, 36);
        btnIniciar.setBackground(new Color(60, 120, 180));
        btnIniciar.setForeground(Color.WHITE);
        content.add(btnIniciar);

        btnSalir = new JButton("Salir");
        btnSalir.setBounds(580, 450, 120, 36);
        btnSalir.setBackground(new Color(150, 50, 50));
        btnSalir.setForeground(Color.WHITE);
        content.add(btnSalir);

        btnSalir.addActionListener(e -> detenerTodo());

        if (!ejercicios.isEmpty()) {
            cargarEjercicioActualEnPantalla();
        } else {
            lblTituloEjercicio.setText("No hay ejercicios en este workout");
            btnIniciar.setEnabled(false);
        }

        btnIniciar.addActionListener(e -> manejarBotonPrincipal());
    }

    // ===================== FUNCIONALIDAD =====================

    private void cargarEjercicioActualEnPantalla() {
        Ejercicio ej = ejercicios.get(indiceEjercicio);
        lblTituloEjercicio.setText("Ejercicio: " + ej.getNombre());
        panelSeries.removeAll();

        ArrayList<Series> sList = ej.getSeries();
        if (sList == null) sList = new ArrayList<>();

        int y = 10;
        for (int i = 0; i < sList.size(); i++) {
            Series s = sList.get(i);
            JPanel p = new JPanel(null);
            p.setBackground(new Color(40, 40, 40));
            p.setBounds(10, y, 680, 80);

            JLabel lblIndex = new JLabel("Serie " + (i + 1));
            lblIndex.setForeground(Color.WHITE);
            lblIndex.setBounds(10, 10, 80, 20);
            p.add(lblIndex);

            JLabel lblReps = new JLabel("Reps: " + s.getRepeticiones());
            lblReps.setForeground(Color.WHITE);
            lblReps.setBounds(120, 10, 120, 20);
            p.add(lblReps);

            JLabel lblDur = new JLabel("Duración: " + s.getDuracion() + " s");
            lblDur.setForeground(Color.WHITE);
            lblDur.setBounds(260, 10, 150, 20);
            p.add(lblDur);

            // FOTO
            JLabel lblFoto = new JLabel();
            lblFoto.setBounds(540, 5, 120, 70);
            cargarFotoDesdeURL(lblFoto, ej.getFoto());
            p.add(lblFoto);

            panelSeries.add(p);
            y += 90;
        }
        panelSeries.revalidate();
        panelSeries.repaint();

        lblCronoSerie.setText("Serie: -");
        lblCronoDescanso.setText("Descanso: -");
    }

    private void cargarFotoDesdeURL(JLabel lbl, String urlFoto) {
        if (urlFoto == null || urlFoto.isEmpty()) {
            lbl.setText("Sin foto");
            lbl.setForeground(Color.LIGHT_GRAY);
            lbl.setIcon(null);
            return;
        }

        new Thread(() -> {
            try {
                URL url = new URL(urlFoto);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.connect();

                if (connection.getResponseCode() == 200) {
                    Image img = ImageIO.read(connection.getInputStream());
                    if (img != null) {
                        Image scaled = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
                        SwingUtilities.invokeLater(() -> {
                            lbl.setIcon(new ImageIcon(scaled));
                            lbl.setText("");
                        });
                    }
                } else {
                    SwingUtilities.invokeLater(() -> {
                        lbl.setText("Sin foto");
                        lbl.setIcon(null);
                    });
                }
                connection.disconnect();
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    lbl.setText("Sin foto");
                    lbl.setIcon(null);
                });
            }
        }).start();
    }

    // ---- Lógica del flujo ----
    private void manejarBotonPrincipal() {
        String txt = btnIniciar.getText();

        if (txt.equals("Iniciar")) {
            iniciarWorkoutGlobal();
            btnIniciar.setText("Pausar");
            btnIniciar.setBackground(new Color(200, 120, 50));
        } else if (txt.equals("Pausar")) {
            pausarTodo();
            btnIniciar.setText("Reanudar");
            btnIniciar.setBackground(new Color(80, 80, 80));
        } else if (txt.equals("Reanudar")) {
            reanudarTodo();
            btnIniciar.setText("Pausar");
            btnIniciar.setBackground(new Color(200, 120, 50));
        } else if (txt.equals("Siguiente ejercicio")) {
            avanzarAlSiguienteEjercicio();
        }
    }

    private void iniciarWorkoutGlobal() {
        if (cronoWorkout == null) {
            cronoWorkout = new Cronometro(seg ->
                    SwingUtilities.invokeLater(() -> lblCronoWorkout.setText("Workout: " + formatHora(seg))));
            cronoWorkout.iniciar();
        }
        workoutEnCurso = true;
        iniciarEjercicio();
    }

    private void iniciarEjercicio() {
        Ejercicio ej = ejercicios.get(indiceEjercicio);
        lblTituloEjercicio.setText("Ejercicio: " + ej.getNombre());

        if (cronoEjercicio != null) cronoEjercicio.parar();
        cronoEjercicio = new CronometroEjercicio(seg ->
                SwingUtilities.invokeLater(() -> lblCronoEjercicio.setText("Ejercicio: " + formatHora(seg))));
        cronoEjercicio.iniciar();

        indiceSerie = 0;
        iniciarSerie();
    }

    private void iniciarSerie() {
        Ejercicio ej = ejercicios.get(indiceEjercicio);
        if (indiceSerie >= ej.getSeries().size()) {
            btnIniciar.setText("Siguiente ejercicio");
            return;
        }
        Series s = ej.getSeries().get(indiceSerie);
        int dur = s.getDuracion();

        if (cronoSerie != null) cronoSerie.parar();
        cronoSerie = new CronometroSerie(dur, new CronometroSerie.OnCountdownListener() {
            @Override public void onTick(int segRest) {
                SwingUtilities.invokeLater(() ->
                        lblCronoSerie.setText("Serie " + (indiceSerie + 1) + ": " + segRest + " s"));
            }
            @Override public void onFinish() {
                iniciarDescanso();
            }
        });
        cronoSerie.iniciar();
    }

    private void iniciarDescanso() {
        int descansoSeg = ejercicios.get(indiceEjercicio).getDescanso();
        if (cronoDescanso != null) cronoDescanso.parar();
        cronoDescanso = new CronometroDescanso(descansoSeg, new CronometroDescanso.OnRestListener() {
            @Override public void onTick(int seg) {
                SwingUtilities.invokeLater(() ->
                        lblCronoDescanso.setText("Descanso: " + seg + " s"));
            }
            @Override public void onFinish() {
                SwingUtilities.invokeLater(() ->
                        lblCronoDescanso.setText("Descanso finalizado"));
                indiceSerie++;
                iniciarSerie();
            }
        });
        cronoDescanso.iniciar();
    }

    private void pausarTodo() {
        pausado = true;
        if (cronoWorkout != null) cronoWorkout.pausar();
        if (cronoEjercicio != null) cronoEjercicio.pausar();
        if (cronoSerie != null) cronoSerie.pausar();
        if (cronoDescanso != null) cronoDescanso.pausar();
    }

    private void reanudarTodo() {
        pausado = false;
        if (cronoWorkout != null) cronoWorkout.pausar();
        if (cronoEjercicio != null) cronoEjercicio.pausar();
        if (cronoSerie != null) cronoSerie.pausar();
        if (cronoDescanso != null) cronoDescanso.pausar();
    }

    private void avanzarAlSiguienteEjercicio() {
        indiceEjercicio++;
        indiceSerie = 0;
        if (indiceEjercicio >= ejercicios.size()) {
            detenerTodo();
            dispose();
            return;
        }
        cargarEjercicioActualEnPantalla();
        iniciarEjercicio();
        btnIniciar.setText("Pausar");
    }

    private void detenerTodo() {
        if (cronoWorkout != null) cronoWorkout.parar();
        if (cronoEjercicio != null) cronoEjercicio.parar();
        if (cronoSerie != null) cronoSerie.parar();
        if (cronoDescanso != null) cronoDescanso.parar();

        int tiempoTotal = (cronoWorkout != null) ? cronoWorkout.getSegundos() : 0;
        int totalEj = ejercicios.size();
        int completados = indiceEjercicio + (indiceSerie > 0 ? 1 : 0);

        try {
            //Registrar histórico correctamente (a través del controlador)
            controlador.registrarHistorico(idWorkout, nombreWorkout, tiempoTotal, completados);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar el histórico: " + e.getMessage());
        }

        // Mostrar pantalla de resultados
        ResultadoEjercicio resumen = new ResultadoEjercicio(nombreWorkout, tiempoTotal, totalEj, completados, controlador);
        resumen.setVisible(true);

        dispose();
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

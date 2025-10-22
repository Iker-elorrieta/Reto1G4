package fitUp;

import java.awt.EventQueue;

import Controlador.Controlador;
import Vista.Inicio;

public class FitUp {
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	
                Controlador controlador1 = new Controlador();
                
            	Inicio frame = new Inicio(null,controlador1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

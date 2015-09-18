package ambere;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Reloj {

    private static int hora = 0, min = 0, seg = 0;
    private static boolean seguir = false;

    private static RelojInterface oyente = null;

    public Reloj(RelojInterface inter) {
        oyente = inter;
    }

    public void empezar() {
        seguir = true;
        Thread hilo = new Thread() {
            public void run() {
                
                while (seguir) {
                    try {

                        sleep(1000);
                        seg++;
                        if (seg == 60) {
                            seg = 0;
                            min++;
                        }
                        if (min == 60) {
                            min = 0;
                            hora++;
                        }
                        if (hora == 60) {
                            hora = 0;
                        }

                        String hms = hora + ":" + min + ":" + seg;
                        oyente.showTime(hms);

                    } catch (InterruptedException ex) {
                        Logger.getLogger(Reloj.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        hilo.start();
    }

    public void parar() {
        seguir = false;
        hora = 0; min = 0; seg = 0;
    }

}

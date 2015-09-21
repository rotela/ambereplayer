package ambere;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import vista.AmberePlayerVista;

public class AmberePlayer implements
        BasicPlayerListener,
        ActionListener,
        KeyListener,
        MouseListener,
        ChangeListener {

    public BasicPlayer player;
    public BasicController control;
    static boolean pausa = false;
    static int cant = 0;
    private final Collection<ArchivoVo> archivosColl;
    private final Collection<ArchivoVo> encontradosColl;
    private ArchivoVo seleccionado;

    private final AmberePlayerVista vista;
    private double volumen;
    private boolean reproduciendo;

    private int h = 0, m = 0, s = 0;
    private long p = 0;

    public AmberePlayer() {

        this.player = new BasicPlayer();
        this.control = this.player;
        this.player.addBasicPlayerListener(this);

        this.vista = new AmberePlayerVista(this);
        this.vista.setVisible();

        DefaultListModel dlm = new DefaultListModel();

        this.vista.getListLista().setModel(dlm);

        this.archivosColl = new ArrayList<>();
        this.encontradosColl = new ArrayList<>();

        this.volumen = this.getVolumen();

    }

    public void reproducir(ArchivoVo vo) {
        if (vo != null) {

            this.vista.getLblTitulo().setText(vo.getId() + "- " + vo.getNombre());

            try {
                this.reproduciendo = true;
                this.control.open(new File(vo.getCompleto()));
                this.control.play();
                this.control.setGain(this.volumen);
                this.control.setPan(0.0);
            } catch (BasicPlayerException ex) {
                display(ex.getMessage());
            }
        }
    }

    public void display(String msg) {

        System.out.println(msg);

    }

    private void irSiguiente() {
        this.parar();

        int actual;

        if (this.vista.getRdoOrdenada().isSelected()) {
            actual = this.seleccionado.getId();
            actual++;
        } else {
            actual = this.getRandon();
        }

        ArchivoVo vo = this.getArchivo(actual);
        if (vo != null) {
            this.seleccionado = vo;
        } else {
            this.seleccionado = this.getArchivo(1);
        }
        this.reproducir(this.seleccionado);
    }

    private void irAnterior() {
        this.parar();

        int actual = this.seleccionado.getId();
        actual--;
        ArchivoVo vo = this.getArchivo(actual);
        if (vo != null) {
            this.seleccionado = vo;
        } else {
            this.seleccionado = this.getArchivo(this.vista.getListLista().getModel().getSize());
        }
        this.reproducir(this.seleccionado);
    }

    public void parar() {
        if (this.reproduciendo) {
            try {

                this.control.stop();
                this.reproduciendo = false;

            } catch (BasicPlayerException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    private int getRandon() {
        int hasta = this.archivosColl.size();
        if (this.encontradosColl.size() > 0) {
            hasta = this.encontradosColl.size();
        }
        int min = 1, max = hasta;

        Random rand = new Random();

        return rand.nextInt((max - min) + 1) + min;
    }

    private void buscar() {
        this.encontradosColl.clear();
        int x = 0;
        String targe = this.vista.getTxtTarge().getText().toLowerCase().trim();

        if (targe.isEmpty()) {
            this.armarLista(archivosColl);
        } else {
            for (ArchivoVo vox : archivosColl) {

                if (vox.getCompleto().toLowerCase().contains(targe)) {
                    x++;
                    this.encontradosColl.add(new ArchivoVo(
                            x,
                            vox.getNombre(),
                            vox.getDireccion(),
                            vox.getCompleto())
                    );
                }
            }
            this.armarLista(encontradosColl);
        }

    }

    private void selDir() {
        this.limpiarTodo();
        cant = 0;
//        DefaultListModel dlm = new DefaultListModel();
//        this.vista.getListLista().setModel(dlm);
        String archivo = "", archivoFull = "";
        JFileChooser c = new JFileChooser(".");
        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // demostrar el diálogo abrir
        int rVal = c.showOpenDialog(this.vista);
        // si se ha seleccionado algo
        if (rVal == JFileChooser.APPROVE_OPTION) {
            archivo = c.getSelectedFile().getName();
            archivoFull = c.getSelectedFile().getPath();
            //System.out.println("archivo: " + archivo);
            this.vista.getLblDirectorio().setText(archivoFull);
            listarArchivos(archivoFull);
        }
    }

    private void listarArchivos(String directorio) {
        this.armarColl(directorio);
        this.armarLista(archivosColl);
    }

    private void armarLista(Collection<ArchivoVo> coll) {
        DefaultListModel dlm = new DefaultListModel();

        for (ArchivoVo vo : coll) {
            dlm.addElement(vo.getId() + "- " + vo.getNombre());
        }

        this.vista.getListLista().setModel(dlm);
        this.vista.getLblCantidad().setText(Integer.toString(dlm.getSize()));
    }

    private void armarColl(String directorio) {

        File f = new File(directorio);

        if (f.isDirectory()) {

            String[] lista = f.list();
            Object[] items = new Object[lista.length];

            System.arraycopy(lista, 0, items, 0, lista.length);

            for (String ar : lista) {

                String full = directorio + "/" + ar;
                File arc = new File(full);
                if (arc.isDirectory()) {
                    this.armarColl(full);
                } else {
                    cant++;
                    this.archivosColl.add(new ArchivoVo(cant, ar, directorio, full));
                }
            }
        }
    }

    private ArchivoVo getArchivo(int x) {

        ArchivoVo vo = null;

        Collection<ArchivoVo> coll = this.archivosColl;

        if (encontradosColl.size() > 0) {
            coll = encontradosColl;
        }

        if (x <= coll.size()) {

            for (ArchivoVo vox : coll) {
                if (vox.getId() == x) {
                    vo = vox;
                    break;
                }
            }
        }
        return vo;
    }

    private void seleccionar() {
        int sel = this.vista.getListLista().getSelectedIndex();
        if (sel != -1) {
            sel++;

            this.seleccionado = this.getArchivo(sel);

            this.vista.getLblDirectorio().setText(this.seleccionado.getNombre());
        }

    }

    private void limpiarTodo() {
        this.archivosColl.clear();
        this.encontradosColl.clear();
        this.armarLista(archivosColl);
    }

    private void getTiempo(long micro) {

        long seconds = (long) (micro / 1000000);

        s = (int) seconds % 60;
        m = (int) (seconds / 60) % 60;
        h = (int) (seconds / (60 * 60)) % 24;

        this.vista.getLblTiempo().setText(String.format("%02d:%02d:%02d", h, m, s));
    }

    @Override
    public void opened(Object o, Map map) {
        display("opend: " + map.toString());
    }

    @Override
    public void progress(int i, long l, byte[] bytes, Map map) {
        if (map.get("mp3.position.microseconds") != null) {
            long micro = (long) map.get("mp3.position.microseconds");
            this.getTiempo(micro);
        }
    }

    @Override
    public void stateUpdated(BasicPlayerEvent bpe) {

//        if (bpe.toString().trim().equals("STOPPED:-1")) {
//            this.irSiguiente();
//        }
        display("estado actualizado: " + bpe.toString());
    }

    @Override
    public void setController(BasicController bc) {
        display("setcontroller: " + bc.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // PLAY
        if (e.getSource() == this.vista.getBtnPlay()) {
            if (this.seleccionado != null) {
                String archivo = this.seleccionado.getCompleto();
                if (!archivo.isEmpty()) {
                    this.reproducir(this.seleccionado);
                }
            }
        }
        // PAUSE
        if (e.getSource() == this.vista.getBtnPause()) {
            try {
                this.control.pause();
            } catch (BasicPlayerException ex) {
                Logger.getLogger(AmberePlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // STOP
        if (e.getSource() == this.vista.getBtnStop()) {
            this.parar();
        }
        // SIGUIENTE
        if (e.getSource() == this.vista.getBtnSiguiente()) {
            this.irSiguiente();
        }
        // ANTERIOR
        if (e.getSource() == this.vista.getBtnAnterior()) {
            this.irAnterior();
        }
        // AGREGAR
        if (e.getSource() == this.vista.getBtnAgregar()) {
            this.selDir();
        }
        // ELIMINAR
        if (e.getSource() == this.vista.getBtnEliminar()) {
            this.limpiarTodo();
        }
        // SALIR
        if (e.getSource() == this.vista.getmItemSalir()) {
            System.exit(0);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.vista.getListLista()) {
            this.seleccionar();
            if (e.getClickCount() == 2) {
                this.reproducir(this.seleccionado);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == this.vista.getTxtTarge()) {
            this.buscar();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.vista.getSdrVolumen()) {
            this.volumen = this.getVolumen();
            if (reproduciendo) {
                try {
                    this.control.setGain(this.getVolumen());
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(AmberePlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    private double getVolumen() {
        int x = this.vista.getSdrVolumen().getValue();
        double d = x * 0.01;
        return d;
    }

    public static void main(String[] args) {
        new AmberePlayer();
    }

}

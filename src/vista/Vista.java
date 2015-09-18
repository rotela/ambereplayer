package vista;

import ambere.ArchivoVo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Vista extends javax.swing.JFrame implements
        ActionListener,
        MouseListener,
        KeyListener {

    static boolean pausa = false;
    static boolean play = false;
    static int cant = 0;
    private Collection<ArchivoVo> archivosColl;
    private Collection<ArchivoVo> encontradosColl;
    private ArchivoVo seleccionado;

    public Vista() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.btnAgregar.addActionListener(this);
        this.btnEliminar.addActionListener(this);
        this.btnPause.addActionListener(this);
        this.btnStop.addActionListener(this);
        this.btnPlay.addActionListener(this);
        this.btnSiguiente.addActionListener(this);
        this.btnAnterior.addActionListener(this);
        this.listLista.addMouseListener(this);
        this.txtTarge.addKeyListener(this);

        DefaultListModel dlm = new DefaultListModel();

        this.listLista.setModel(dlm);

        this.archivosColl = new ArrayList<>();
        this.encontradosColl = new ArrayList<>();
    }

    private void irSiguiente() {
        int actual;
        if (this.rdoOrdenada.isSelected()) {
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
        play = false;
        this.reproducir(this.seleccionado);
    }

    private void irAnterior() {
        int actual = this.seleccionado.getId();
        actual--;
        ArchivoVo vo = this.getArchivo(actual);
        if (vo != null) {
            this.seleccionado = vo;
        } else {
            this.seleccionado = this.getArchivo(this.listLista.getModel().getSize());
        }
        play = false;
        this.reproducir(this.seleccionado);
    }

    private int getRandon() {
        int hasta = this.archivosColl.size();
        if (this.encontradosColl.size() > 0) {
            hasta = this.encontradosColl.size();
        }
        int min = 1, max = hasta;
        
        Random rand = new Random();
        
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private void reproducir(ArchivoVo vo) {
        if (vo != null) {

            this.lblTitulo.setText(vo.getId() + "- " + vo.getNombre().trim());
            this.listLista.setSelectedIndex((vo.getId() - 1));
            try {
                Thread.sleep(500);
                pausa = false;
                play = true;
                try {
                    FileInputStream fis = new FileInputStream(vo.getCompleto());
                    Player pl = new Player(fis);

                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                while (play) {
                                    //if (!pausa) {
                                    lblTiempo.setText(Integer.toString(pl.getPosition()));
                                    if (!pl.play(1)) {
                                        break;
                                    }
                                    //}
                                }
                                play = false;
                                if (pl.isComplete()) {
                                    irSiguiente();
                                }
                            } catch (JavaLayerException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }.start();
                } catch (JavaLayerException e1) {
                    JOptionPane.showMessageDialog(this, "No es un fichero de audio");
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Vista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void buscar() {
        this.encontradosColl.clear();

        int x = 0;
        String targe = this.txtTarge.getText().toLowerCase().trim();

        if (targe.isEmpty()) {
            this.armarLista(archivosColl);
        } else {
            for (ArchivoVo vox : archivosColl) {

                if (vox.getNombre().toLowerCase().contains(targe)) {
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
        cant = 0;
        DefaultListModel dlm = new DefaultListModel();
        this.listLista.setModel(dlm);
        String archivo = "", archivoFull = "";
        JFileChooser c = new JFileChooser(".");
        c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // demostrar el diálogo abrir
        int rVal = c.showOpenDialog(this);
        // si se ha seleccionado algo
        if (rVal == JFileChooser.APPROVE_OPTION) {
            archivo = c.getSelectedFile().getName();
            archivoFull = c.getSelectedFile().getPath();
            //System.out.println("archivo: " + archivo);
            this.lblDirectorio.setText(archivoFull);
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

        this.listLista.setModel(dlm);
        this.lblCantidad.setText(Integer.toString(dlm.getSize()));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgReproduccion = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblTiempo = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        rdoRandon = new javax.swing.JRadioButton();
        rdoOrdenada = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listLista = new javax.swing.JList();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblCantidad = new javax.swing.JLabel();
        txtTarge = new javax.swing.JTextField();
        lblDirectorio = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Amberé Player");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitulo.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        lblTitulo.setText("Título");

        lblTiempo.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        lblTiempo.setText("Tiempo");

        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/control_play_blue.png"))); // NOI18N

        btnPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/control_pause_blue.png"))); // NOI18N
        btnPause.setToolTipText("");

        btnStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/control_stop_blue.png"))); // NOI18N

        btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/control_rewind_blue.png"))); // NOI18N
        btnAnterior.setToolTipText("");

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/control_fastforward_blue.png"))); // NOI18N

        bgReproduccion.add(rdoRandon);
        rdoRandon.setMnemonic('r');
        rdoRandon.setText("Randon");

        bgReproduccion.add(rdoOrdenada);
        rdoOrdenada.setMnemonic('o');
        rdoOrdenada.setSelected(true);
        rdoOrdenada.setText("Ordenada");
        rdoOrdenada.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPause, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnterior, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 322, Short.MAX_VALUE)
                        .addComponent(rdoOrdenada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdoRandon))
                    .addComponent(lblTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTiempo)
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPlay)
                        .addComponent(btnPause)
                        .addComponent(btnStop)
                        .addComponent(btnAnterior)
                        .addComponent(btnSiguiente))
                    .addComponent(rdoOrdenada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rdoRandon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listLista.setFont(new java.awt.Font("DejaVu Sans", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(listLista);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnEliminar.setToolTipText("");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantidad.setText("0");

        lblDirectorio.setText("Directorio");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTarge)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblDirectorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDirectorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTarge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCantidad))
                        .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(btnAgregar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.limpiarTodo();
    }//GEN-LAST:event_btnEliminarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Vista().setVisible(true);
            }
        });
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
        int sel = this.listLista.getSelectedIndex();
        if (sel != -1) {
            sel++;
            seleccionado = this.getArchivo(sel);
            this.lblTitulo.setText(seleccionado.getNombre().trim());
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgReproduccion;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnStop;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblDirectorio;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList listLista;
    private javax.swing.JRadioButton rdoOrdenada;
    private javax.swing.JRadioButton rdoRandon;
    private javax.swing.JTextField txtTarge;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnPlay) {
            if (this.seleccionado != null) {
                String archivo = this.seleccionado.getCompleto();
                if (!archivo.isEmpty()) {
                    this.reproducir(this.seleccionado);
                }
            }
        }
        if (e.getSource() == this.btnPause) {
            pausa = true;
        }

        if (e.getSource() == this.btnStop) {
            play = false;
        }
        if (e.getSource() == this.btnSiguiente) {
            this.irSiguiente();
        }

        if (e.getSource() == this.btnAnterior) {
            this.irAnterior();
        }

        if (e.getSource() == this.btnAgregar) {
            this.selDir();
        }
    }

    private void limpiarTodo() {
        this.archivosColl.clear();
        this.encontradosColl.clear();
        this.armarLista(archivosColl);
        this.lblTitulo.setText("Seleccione una música");
        this.lblTiempo.setText("0");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.listLista) {
            this.seleccionar();
            if (e.getClickCount() == 2) {
                play = false;
                this.reproducir(this.seleccionado);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == this.txtTarge) {
            this.buscar();
        }
    }
}

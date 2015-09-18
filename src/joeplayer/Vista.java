package joeplayer;

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
    private Collection<ArchivosVo> archivosColl;
    private Collection<ArchivosVo> encontradosColl;

    public Vista() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.btnAgregar.addActionListener(this);
        this.btnEliminar.addActionListener(this);
        this.btnPause.addActionListener(this);
        this.btnStop.addActionListener(this);
        this.btnPlay.addActionListener(this);
        this.listLista.addMouseListener(this);
        this.txtTarge.addKeyListener(this);

        DefaultListModel dlm = new DefaultListModel();
        this.listLista.setModel(dlm);

        this.archivosColl = new ArrayList<>();
        this.encontradosColl = new ArrayList<>();
    }

    private void reproducir(String archivo) {

        try {
            Thread.sleep(500);
            pausa = false;
            play = true;
            try {
                FileInputStream fis = new FileInputStream(archivo);
                Player pl = new Player(fis);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            while (play) {
                                if (!pausa) {
                                    lblTiempo.setText(Integer.toString(pl.getPosition()));
                                    if (!pl.play(1)) {
                                        break;
                                    }
                                }
                            }
                            play = false;
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

    private void buscar() {
        this.encontradosColl.clear();
        System.out.println("cantidad de archivos " + archivosColl.size() + " can encon " + encontradosColl.size());
        int x = 0;
        String targe = this.txtTarge.getText().trim();

        if (targe.isEmpty()) {
            this.armarLista(archivosColl);
        } else {
            for (ArchivosVo vox : archivosColl) {
                System.out.println("comparando " + vox.getNombre() + " con " + targe);
                if (vox.getNombre().contains(targe)) {
                    x++;
                    this.encontradosColl.add(new ArchivosVo(
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
            System.out.println("archivo: " + archivo);
            System.out.println("full: " + archivoFull);
            listarArchivos(archivoFull);
        }
    }

    private void listarArchivos(String directorio) {
        this.armarColl(directorio);
        this.armarLista(archivosColl);
    }

    private void armarLista(Collection<ArchivosVo> coll) {
        DefaultListModel dlm = new DefaultListModel();

        for (ArchivosVo vo : coll) {
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
                    this.archivosColl.add(new ArchivosVo(cant, ar, directorio, full));
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        btnPause = new javax.swing.JButton();
        lblTiempo = new javax.swing.JLabel();
        btnStop = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listLista = new javax.swing.JList();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblCantidad = new javax.swing.JLabel();
        txtTarge = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JoePlayer");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitulo.setText("Título");

        btnPlay.setText(">");

        btnPause.setText("||");

        lblTiempo.setText("Tiempo");

        btnStop.setText("X");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnPlay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPause)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTiempo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPlay)
                    .addComponent(btnPause)
                    .addComponent(btnStop))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jScrollPane1.setViewportView(listLista);

        btnAgregar.setText("+");

        btnEliminar.setText("-");

        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCantidad.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTarge))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTarge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnEliminar)
                    .addComponent(lblCantidad))
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

    private ArchivosVo getArchivo(int x) {
        x = x + 1;
        ArchivosVo vo = null;

        if (encontradosColl.size() > 0) {
            for (ArchivosVo vox : encontradosColl) {

                if (vox.getId() == x) {

                    vo = vox;
                    break;

                }
            }
        } else {
            for (ArchivosVo vox : archivosColl) {

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
            ArchivosVo vo = this.getArchivo(sel);
            this.lblTitulo.setText(vo.getCompleto().trim());
        }

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnPause;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnStop;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList listLista;
    private javax.swing.JTextField txtTarge;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnPlay) {
            String archivo = this.lblTitulo.getText().trim();
            if (!archivo.isEmpty()) {
                this.reproducir(archivo);
            }
        }
        if (e.getSource() == this.btnPause) {
            pausa = true;
        }

        if (e.getSource() == this.btnStop) {
            play = false;
        }

        if (e.getSource() == this.btnAgregar) {
            this.selDir();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.listLista) {
            this.seleccionar();
            if (e.getClickCount() == 2) {
                play = false;
                String archivo = this.lblTitulo.getText().trim();
                this.reproducir(archivo);
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

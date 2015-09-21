package vista;

import ambere.AmberePlayer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class AmberePlayerVista extends javax.swing.JFrame {

    public AmberePlayerVista(AmberePlayer oye) {
        tema();
        initComponents();
        this.setLocationRelativeTo(null);
        this.btnAgregar.addActionListener(oye);
        this.btnEliminar.addActionListener(oye);
        this.btnPause.addActionListener(oye);
        this.btnStop.addActionListener(oye);
        this.btnPlay.addActionListener(oye);
        this.btnSiguiente.addActionListener(oye);
        this.btnAnterior.addActionListener(oye);
        this.listLista.addMouseListener(oye);
        this.txtTarge.addKeyListener(oye);
        this.sdrVolumen.addChangeListener(oye);
        
        this.mItemAddArchivo.addActionListener(oye);
        this.mItemAddDirectorio.addActionListener(oye);
        this.mItemSalir.addActionListener(oye);
    }

    public JMenuItem getmItemAddArchivo() {
        return mItemAddArchivo;
    }

    public JMenuItem getmItemAddDirectorio() {
        return mItemAddDirectorio;
    }

    public JMenuItem getmItemSalir() {
        return mItemSalir;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnAnterior() {
        return btnAnterior;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnPause() {
        return btnPause;
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    public JButton getBtnStop() {
        return btnStop;
    }

    public JLabel getLblCantidad() {
        return lblCantidad;
    }

    public JLabel getLblDirectorio() {
        return lblDirectorio;
    }

    public JLabel getLblTiempo() {
        return lblTiempo;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public JList getListLista() {
        return listLista;
    }

    public JRadioButton getRdoOrdenada() {
        return rdoOrdenada;
    }

    public JRadioButton getRdoRandon() {
        return rdoRandon;
    }

    public JTextField getTxtTarge() {
        return txtTarge;
    }

    public JSlider getSdrVolumen() {
        return sdrVolumen;
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
        sdrVolumen = new javax.swing.JSlider();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listLista = new javax.swing.JList();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblCantidad = new javax.swing.JLabel();
        txtTarge = new javax.swing.JTextField();
        lblDirectorio = new javax.swing.JLabel();
        menubar = new javax.swing.JMenuBar();
        mPrincipal = new javax.swing.JMenu();
        mItemAddArchivo = new javax.swing.JMenuItem();
        mItemAddDirectorio = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mItemSalir = new javax.swing.JMenuItem();
        mReproduccion = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mAcerca = new javax.swing.JMenu();

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

        sdrVolumen.setMajorTickSpacing(10);
        sdrVolumen.setMinorTickSpacing(10);
        sdrVolumen.setToolTipText("Volumen");
        sdrVolumen.setValue(80);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sdrVolumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(rdoRandon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sdrVolumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listLista.setFont(new java.awt.Font("DejaVu Sans", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(listLista);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnEliminar.setToolTipText("");

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
                    .addComponent(lblDirectorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDirectorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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

        mPrincipal.setMnemonic('p');
        mPrincipal.setText("Principal");

        mItemAddArchivo.setMnemonic('a');
        mItemAddArchivo.setText("Añadir Archivo");
        mPrincipal.add(mItemAddArchivo);

        mItemAddDirectorio.setMnemonic('d');
        mItemAddDirectorio.setText("Añadir Directorio");
        mPrincipal.add(mItemAddDirectorio);
        mPrincipal.add(jSeparator1);

        mItemSalir.setMnemonic('s');
        mItemSalir.setText("Salir");
        mPrincipal.add(mItemSalir);

        menubar.add(mPrincipal);

        mReproduccion.setText("Reproducción");

        jMenuItem1.setText("jMenuItem1");
        mReproduccion.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        mReproduccion.add(jMenuItem2);

        jMenuItem3.setText("jMenuItem3");
        mReproduccion.add(jMenuItem3);

        menubar.add(mReproduccion);

        mAcerca.setMnemonic('a');
        mAcerca.setText("Acerca");
        mAcerca.setToolTipText("");
        menubar.add(mAcerca);

        setJMenuBar(menubar);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setVisible() {
        this.setVisible(true);
    }

    private static void tema() {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AmberePlayerVista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblDirectorio;
    private javax.swing.JLabel lblTiempo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JList listLista;
    private javax.swing.JMenu mAcerca;
    private javax.swing.JMenuItem mItemAddArchivo;
    private javax.swing.JMenuItem mItemAddDirectorio;
    private javax.swing.JMenuItem mItemSalir;
    private javax.swing.JMenu mPrincipal;
    private javax.swing.JMenu mReproduccion;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JRadioButton rdoOrdenada;
    private javax.swing.JRadioButton rdoRandon;
    private javax.swing.JSlider sdrVolumen;
    private javax.swing.JTextField txtTarge;
    // End of variables declaration//GEN-END:variables

}

package view;

import control.RecycleBinController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectWindow extends javax.swing.JDialog {
    private static final int RECYCLE_BIN_PORT = 1990;

    private static ConnectWindow instance;
    private final java.awt.Frame parent;

    private enum Valid {
        YES, NO
    }

    private ConnectWindow(final java.awt.Frame parent, final boolean modal) {
        super(parent, modal);
        initComponents();
        this.parent = parent;
    }

    @Override
    public void dispose() {
        instance = null;
        super.dispose();
        parent.setVisible(true);
    }

    public static void showModal(final java.awt.Frame parent) {
        parent.setVisible(false);
        if (instance == null) {
            instance = new ConnectWindow(parent, true);
        }
        instance.setVisible(true);
    }

    private static Valid validator(final String ip) {
        if (ip == null) {
            return Valid.NO;
        } else {
            final String ipReplaced = ip.replace('.', ';');
            final String[] piecesIP = ipReplaced.split(";");
            if (piecesIP.length != 4) {
                return Valid.NO;
            } else {
                try {
                    final int pieceIP0 = Integer.parseInt(piecesIP[0]);
                    final int pieceIP1 = Integer.parseInt(piecesIP[1]);
                    final int pieceIP2 = Integer.parseInt(piecesIP[2]);
                    final int pieceIP3 = Integer.parseInt(piecesIP[3]);
                    if (pieceIP0 > 255 || pieceIP0 < 0) {
                        return Valid.NO;
                    } else if (pieceIP1 > 255 || pieceIP1 < 0) {
                        return Valid.NO;
                    } else if (pieceIP2 > 255 || pieceIP2 < 0) {
                        return Valid.NO;
                    } else if (pieceIP3 > 255 || pieceIP3 < 0) {
                        return Valid.NO;
                    } else {
                        return Valid.YES;
                    }
                } catch (final NumberFormatException ex) {
                    return Valid.NO;
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textIP = new javax.swing.JTextField();
        textIPDescription = new javax.swing.JLabel();
        btnConnect = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Conectar Lixeira");
        setAlwaysOnTop(true);
        setResizable(false);

        textIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textIPKeyReleased(evt);
            }
        });

        textIPDescription.setText("Endereço de IP (IPv4):");

        btnConnect.setText("Conectar");
        btnConnect.setEnabled(false);
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textIPDescription)
                    .addComponent(textIP, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnConnect)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(textIPDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(textIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConnect))
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textIPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textIPKeyReleased
        btnConnect.setEnabled(validator(textIP.getText()) == Valid.YES);
    }//GEN-LAST:event_textIPKeyReleased

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        try {
            RecycleBinController.getInstance().connectToServer(textIP.getText(), RECYCLE_BIN_PORT);
        } catch (IOException ex) {
            Logger.getLogger(ConnectWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConnectActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JTextField textIP;
    private javax.swing.JLabel textIPDescription;
    // End of variables declaration//GEN-END:variables
}

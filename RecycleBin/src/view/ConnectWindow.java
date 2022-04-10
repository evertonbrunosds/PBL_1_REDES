package view;

import control.RecycleBinController;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import uefs.ComumBase.interfaces.Factory;


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
    
    private void connectToServer() {
        try {
            RecycleBinController.getInstance().connectToServer(textIP.getText(), RECYCLE_BIN_PORT);
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(
                    this,
                    "Erro ao conectar-se ao servidor! Código de erro: ".concat(ex.getMessage().concat(".")),
                    "Mensagem de Erro",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    @Override
    public void dispose() {
        instance = null;
        final String id = RecycleBinController.getInstance().getId();
        final String name = "Lixeira";
        final String title = name + " [" + id + "]";
        parent.setTitle(id.equals("UNDETERMINED") ? name : title);
        super.dispose();
        parent.setVisible(true);
    }

    public static void showModal(final java.awt.Frame parent) {
        parent.setVisible(false);
        if (instance == null) {
            instance = new ConnectWindow(parent, true);
        }
        instance.progressBar.setVisible(false);
        instance.setVisible(true);
    }

    private static Valid validator(final String ip) {
        if (ip == null) {
            return Valid.NO;
        } else {
            final String ipReplaced = ip.replace('.', ';');
            int count = 0;
            for (char c : ipReplaced.toCharArray()) {
                if (c == ';') {
                    count++;
                }
            }
            if (count != 3) {
                return Valid.NO;
            }
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
        progressBar = new javax.swing.JProgressBar();

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

        progressBar.setIndeterminate(true);

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void textIPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textIPKeyReleased
        btnConnect.setEnabled(validator(textIP.getText()) == Valid.YES);
        if (btnConnect.isEnabled()) {
            if(evt.getKeyCode() == KeyEvent.VK_ENTER){
                btnConnectActionPerformed(null);
            }
        }
    }//GEN-LAST:event_textIPKeyReleased

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        Factory.thread(() -> {
            progressBar.setVisible(true);
            textIP.setEnabled(false);
            btnConnect.setEnabled(false);
            connectToServer();
            textIP.setEnabled(true);
            btnConnect.setEnabled(true);
            progressBar.setVisible(false);
        }).start();
        
    }//GEN-LAST:event_btnConnectActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JTextField textIP;
    private javax.swing.JLabel textIPDescription;
    // End of variables declaration//GEN-END:variables
}

package view;

import control.RecycleBinController;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
        setToOrange(usageNone);
    }

    @Override
    public void dispose() {
        try {
            RecycleBinController.getInstance().disconnect();
        } catch (final IOException ex) {
            JOptionPane.showConfirmDialog(
                    this,
                    "Erro ao desconectar-se do servidor! Código de erro: ".concat(ex.getMessage().concat(".")),
                    "Mensagem de Erro",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );
        }
        super.dispose();
    }

    private static void setToOrange(final JLabel jLabel) {
        final float[] rGBColor = Color.RGBtoHSB(194, 104, 2, null);
        jLabel.setForeground(Color.getHSBColor(rGBColor[0], rGBColor[1], rGBColor[2]));
    }

    private void setToBlackAllTextFieldUsage() {
        usageNone.setForeground(Color.BLACK);
        usageLow.setForeground(Color.BLACK);
        usageMedium.setForeground(Color.BLACK);
        usageHigh.setForeground(Color.BLACK);
        usageTotal.setForeground(Color.BLACK);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnConnectToServer = new javax.swing.JButton();
        labelDescriptionState = new javax.swing.JLabel();
        panelUsage = new javax.swing.JPanel();
        usageNone = new javax.swing.JLabel();
        usageLow = new javax.swing.JLabel();
        usageMedium = new javax.swing.JLabel();
        usageHigh = new javax.swing.JLabel();
        usageTotal = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lixeira");
        setResizable(false);

        btnConnectToServer.setText("Conectar");
        btnConnectToServer.setFocusable(false);
        btnConnectToServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectToServerActionPerformed(evt);
            }
        });

        labelDescriptionState.setText("Descrição de Estado: Livre");

        usageNone.setText("nenhum");
        usageNone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usageNone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usageNoneMouseReleased(evt);
            }
        });

        usageLow.setText("baixo");
        usageLow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usageLow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usageLowMouseReleased(evt);
            }
        });

        usageMedium.setText("médio");
        usageMedium.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usageMedium.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usageMediumMouseReleased(evt);
            }
        });

        usageHigh.setText("alto");
        usageHigh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usageHigh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usageHighMouseReleased(evt);
            }
        });

        usageTotal.setText("total");
        usageTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        usageTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usageTotalMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout panelUsageLayout = new javax.swing.GroupLayout(panelUsage);
        panelUsage.setLayout(panelUsageLayout);
        panelUsageLayout.setHorizontalGroup(
            panelUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsageLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(usageNone)
                .addGap(43, 43, 43)
                .addComponent(usageLow)
                .addGap(74, 74, 74)
                .addComponent(usageMedium)
                .addGap(77, 77, 77)
                .addComponent(usageHigh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(usageTotal)
                .addContainerGap())
        );
        panelUsageLayout.setVerticalGroup(
            panelUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsageLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(panelUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(usageNone)
                    .addComponent(usageLow)
                    .addComponent(usageMedium)
                    .addComponent(usageHigh)
                    .addComponent(usageTotal))
                .addGap(0, 0, 0))
        );

        progressBar.setMaximum(4);
        progressBar.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                progressBarStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnConnectToServer)
                    .addComponent(labelDescriptionState)
                    .addComponent(panelUsage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelUsage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelDescriptionState)
                .addGap(15, 15, 15)
                .addComponent(btnConnectToServer)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectToServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectToServerActionPerformed
        ConnectWindow.showModal(this);
    }//GEN-LAST:event_btnConnectToServerActionPerformed

    private void usageNoneMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usageNoneMouseReleased
        if (progressBar.getValue() != 0) {
            progressBar.setValue(0);
        }
    }//GEN-LAST:event_usageNoneMouseReleased

    private void usageLowMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usageLowMouseReleased
        if (progressBar.getValue() != 1) {
            progressBar.setValue(1);
        }
    }//GEN-LAST:event_usageLowMouseReleased

    private void usageMediumMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usageMediumMouseReleased
        if (progressBar.getValue() != 2) {
            progressBar.setValue(2);
        }
    }//GEN-LAST:event_usageMediumMouseReleased

    private void usageHighMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usageHighMouseReleased
        if (progressBar.getValue() != 3) {
            progressBar.setValue(3);
        }
    }//GEN-LAST:event_usageHighMouseReleased

    private void usageTotalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usageTotalMouseReleased
        if (progressBar.getValue() != 4) {
            progressBar.setValue(4);
        }
    }//GEN-LAST:event_usageTotalMouseReleased

    private void progressBarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_progressBarStateChanged
        setToBlackAllTextFieldUsage();
        switch (progressBar.getValue()) {
            case 0:
                setToOrange(usageNone);
                break;
            case 1:
                setToOrange(usageLow);
                break;
            case 2:
                setToOrange(usageMedium);
                break;
            case 3:
                setToOrange(usageHigh);
                break;
            case 4:
                setToOrange(usageTotal);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_progressBarStateChanged

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnectToServer;
    private javax.swing.JLabel labelDescriptionState;
    private javax.swing.JPanel panelUsage;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel usageHigh;
    private javax.swing.JLabel usageLow;
    private javax.swing.JLabel usageMedium;
    private javax.swing.JLabel usageNone;
    private javax.swing.JLabel usageTotal;
    // End of variables declaration//GEN-END:variables
}

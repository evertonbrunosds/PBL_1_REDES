package view;

import controller.MainController;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {

    public Main() {
        initComponents();
        MainController.getInstance().addActionChangeConnection(isConnected -> {
            setEnabledComponents(isConnected);
            if (isConnected) {
                btnConnectToServer.setText("Desconectar");
                setTitle("Administrador Conectado");
            } else {
                btnConnectToServer.setText("Conectar");
                setTitle("Administrador Desconectado");
                progressBar.setValue(0);
                comboBoxIDs.removeAll();
                cBoxIsBlocked.setSelected(false);
                cBoxPriority.setSelected(false);
            }
        });
        MainController.getInstance().disconnect();
    }

    private void setEnabledComponents(final boolean enable) {
        comboBoxIDs.setEnabled(enable);
        cBoxPriority.setEnabled(enable);
        cBoxIsBlocked.setEnabled(enable);
        usageNone.setEnabled(enable);
        usageLow.setEnabled(enable);
        usageMedium.setEnabled(enable);
        usageHigh.setEnabled(enable);
        usageTotal.setEnabled(enable);
        progressBar.setEnabled(enable);
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBoxIDs = new javax.swing.JComboBox<>();
        cBoxPriority = new javax.swing.JCheckBox();
        panelUsage = new javax.swing.JPanel();
        usageNone = new javax.swing.JLabel();
        usageLow = new javax.swing.JLabel();
        usageMedium = new javax.swing.JLabel();
        usageHigh = new javax.swing.JLabel();
        usageTotal = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        cBoxIsBlocked = new javax.swing.JCheckBox();
        btnConnectToServer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrador");
        setResizable(false);

        comboBoxIDs.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboBoxIDsItemStateChanged(evt);
            }
        });
        comboBoxIDs.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                comboBoxIDsPopupMenuWillBecomeVisible(evt);
            }
        });

        cBoxPriority.setText("Priorizar a Coleta de Lixo da Lixeira Atual");

        usageNone.setText("nenhum");
        usageNone.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        usageLow.setText("baixo");
        usageLow.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        usageMedium.setText("médio");
        usageMedium.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        usageHigh.setText("alto");
        usageHigh.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        usageTotal.setText("total");
        usageTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        cBoxIsBlocked.setText("Bloquear a Inserção e Coleta de Lixo da Lixeira Atual");

        btnConnectToServer.setText("Conectar");
        btnConnectToServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectToServerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(comboBoxIDs, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cBoxPriority)
                    .addComponent(panelUsage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cBoxIsBlocked)
                    .addComponent(btnConnectToServer))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnConnectToServer)
                .addGap(15, 15, 15)
                .addComponent(comboBoxIDs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(cBoxPriority)
                .addGap(15, 15, 15)
                .addComponent(cBoxIsBlocked)
                .addGap(15, 15, 15)
                .addComponent(panelUsage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxIDsPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboBoxIDsPopupMenuWillBecomeVisible
        try {
            MainController.getInstance().listRecycleBins(allIds -> {
                comboBoxIDs.removeAllItems();
                for (final String id : allIds) {
                    comboBoxIDs.addItem(id);
                }
                comboBoxIDs.setSelectedIndex(-1);
            });
        } catch (final IOException ex) {
            JOptionPane.showConfirmDialog(
                    this,
                    "Conexão perdida! Código de erro: ".concat(ex.getMessage().concat(".")),
                    "Mensagem de Erro",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_comboBoxIDsPopupMenuWillBecomeVisible

    private void btnConnectToServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectToServerActionPerformed
        if (!MainController.getInstance().isConnected()) {
            ConnectWindow.showModal(this);
        } else {
            MainController.getInstance().disconnect();
        }
    }//GEN-LAST:event_btnConnectToServerActionPerformed

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

    private void comboBoxIDsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboBoxIDsItemStateChanged
        if (comboBoxIDs.getSelectedIndex() > -1) {
            try {
                final String id = comboBoxIDs.getSelectedItem().toString();
                MainController.getInstance().showRecycleDetails(id);
            } catch (IOException ex) {
                JOptionPane.showConfirmDialog(
                        this,
                        "Conexão perdida! Código de erro: ".concat(ex.getMessage().concat(".")),
                        "Mensagem de Erro",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }//GEN-LAST:event_comboBoxIDsItemStateChanged

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
    private javax.swing.JCheckBox cBoxIsBlocked;
    private javax.swing.JCheckBox cBoxPriority;
    private javax.swing.JComboBox<String> comboBoxIDs;
    private javax.swing.JPanel panelUsage;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel usageHigh;
    private javax.swing.JLabel usageLow;
    private javax.swing.JLabel usageMedium;
    private javax.swing.JLabel usageNone;
    private javax.swing.JLabel usageTotal;
    // End of variables declaration//GEN-END:variables
}

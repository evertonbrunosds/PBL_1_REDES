package view;

import controller.MainController;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static util.Constants.*;
import static util.Usage.*;

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
                comboBoxIDs.setSelectedIndex(-1);
                cBoxIsBlocked.setSelected(false);
                cBoxPriority.setSelected(false);
            }
        });
        MainController.getInstance().addActionChangeRecycle(data -> {
            switch (data.getString(USAGE)) {
                case NONE:
                    progressBar.setValue(0);
                    break;
                case LOW:
                    progressBar.setValue(1);
                    break;
                case MEDIUM:
                    progressBar.setValue(2);
                    break;
                case HIGH:
                    progressBar.setValue(3);
                    break;
                case TOTAL:
                    progressBar.setValue(4);
                    break;
                default:
                    progressBar.setValue(0);
                    break;
            }
            cBoxPriority.setSelected(data.getString(IS_PRIORITY).equals("TRUE"));
            cBoxIsBlocked.setSelected(data.getString(IS_BLOCKED).equals("TRUE"));
        });
        MainController.getInstance().disconnect();
    }

    private void setEnabledComponents(final boolean enable) {
        comboBoxIDs.setEnabled(enable);
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
        cBoxIsBlocked = new javax.swing.JCheckBox();
        btnConnectToServer = new javax.swing.JButton();
        panelUsage = new javax.swing.JPanel();
        usageNone = new javax.swing.JLabel();
        usageLow = new javax.swing.JLabel();
        usageMedium = new javax.swing.JLabel();
        usageHigh = new javax.swing.JLabel();
        usageTotal = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        btnShow = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administrador");
        setResizable(false);

        comboBoxIDs.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboBoxIDsPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                comboBoxIDsPopupMenuWillBecomeVisible(evt);
            }
        });

        cBoxPriority.setText("Priorizar a Coleta de Lixo da Lixeira Atual");
        cBoxPriority.setEnabled(false);
        cBoxPriority.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cBoxPriorityMouseReleased(evt);
            }
        });

        cBoxIsBlocked.setText("Bloquear a Inserção e Coleta de Lixo da Lixeira Atual");
        cBoxIsBlocked.setEnabled(false);
        cBoxIsBlocked.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                cBoxIsBlockedMouseReleased(evt);
            }
        });

        btnConnectToServer.setText("Conectar");
        btnConnectToServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectToServerActionPerformed(evt);
            }
        });

        usageNone.setText("uso: 0.00m³");
        usageNone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        usageLow.setText("uso: 0.25m³");
        usageLow.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        usageMedium.setText("uso: 0.50m³");
        usageMedium.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        usageHigh.setText("uso: 0.75m³");
        usageHigh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        usageTotal.setText("uso: 1.00m³");
        usageTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout panelUsageLayout = new javax.swing.GroupLayout(panelUsage);
        panelUsage.setLayout(panelUsageLayout);
        panelUsageLayout.setHorizontalGroup(
            panelUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsageLayout.createSequentialGroup()
                .addComponent(usageNone)
                .addGap(27, 27, 27)
                .addComponent(usageLow)
                .addGap(40, 40, 40)
                .addComponent(usageMedium)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(usageHigh)
                .addGap(43, 43, 43)
                .addComponent(usageTotal))
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

        btnShow.setText("Atualizar Exibição");
        btnShow.setEnabled(false);
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cBoxPriority)
                    .addComponent(cBoxIsBlocked)
                    .addComponent(btnConnectToServer)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(comboBoxIDs, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(btnShow)))
                .addGap(89, 89, 89))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelUsage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBar, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnConnectToServer)
                .addGap(15, 15, 15)
                .addComponent(cBoxPriority)
                .addGap(15, 15, 15)
                .addComponent(cBoxIsBlocked)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboBoxIDs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(panelUsage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void cBoxPriorityMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cBoxPriorityMouseReleased
        final String isPriority = Boolean.toString(cBoxPriority.isSelected()).toUpperCase();
        try {
            MainController.getInstance().setIsPriority(isPriority);
        } catch (final IOException ex) {
            JOptionPane.showConfirmDialog(
                    this,
                    "Falha ao se comunicar com o servidor",
                    "Mensagem de Erro",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );
            cBoxPriority.setSelected(!cBoxPriority.isSelected());
        }
    }//GEN-LAST:event_cBoxPriorityMouseReleased

    private void cBoxIsBlockedMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cBoxIsBlockedMouseReleased
        final String isBlocked = Boolean.toString(cBoxIsBlocked.isSelected()).toUpperCase();
        try {
            MainController.getInstance().setIsBlocked(isBlocked);
        } catch (final IOException ex) {
            JOptionPane.showConfirmDialog(
                    this,
                    "Falha ao se comunicar com o servidor",
                    "Mensagem de Erro",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );
            cBoxIsBlocked.setSelected(!cBoxIsBlocked.isSelected());
        }
    }//GEN-LAST:event_cBoxIsBlockedMouseReleased

    private void comboBoxIDsPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboBoxIDsPopupMenuWillBecomeInvisible
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
            cBoxIsBlocked.setEnabled(true);
            cBoxPriority.setEnabled(true);
            btnShow.setEnabled(true);
        } else {
            cBoxIsBlocked.setEnabled(false);
            cBoxPriority.setEnabled(false);
            btnShow.setEnabled(false);
        }
    }//GEN-LAST:event_comboBoxIDsPopupMenuWillBecomeInvisible

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
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
        } else {
            JOptionPane.showConfirmDialog(
                    this,
                    "Selecione alguma lixeira",
                    "Mensagem de Erro",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnShowActionPerformed

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
    private javax.swing.JButton btnShow;
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

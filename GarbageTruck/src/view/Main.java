package view;

import control.Controller;
import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import uefs.ComumBase.classes.AVLTree;
import static util.Constants.*;
import static util.Usage.*;

/**
 * Classe responsável por comportar-se como janela principal.
 *
 * @author Everton Bruno Silva dos Santos.
 * @version 1.0
 */
public class Main extends javax.swing.JFrame {

    private final AVLTree<Integer, String> garbageTruckUsageTree;

    private static void gerateDataUsageGarbageTruck(final AVLTree<Integer, String> aVLTree) {
        aVLTree.put(0, "0.00m³");
        aVLTree.put(1, "0.25m³");
        aVLTree.put(2, "0.50m³");
        aVLTree.put(3, "0.75m³");
        aVLTree.put(4, "1.00m³");
        aVLTree.put(5, "1.25m³");
        aVLTree.put(6, "1.50m³");
        aVLTree.put(7, "1.75m³");
        aVLTree.put(8, "2.00m³");
        aVLTree.put(9, "2.25m³");
        aVLTree.put(10, "2.50m³");
        aVLTree.put(11, "2.75m³");
        aVLTree.put(12, "3.00m³");
        aVLTree.put(13, "3.25m³");
        aVLTree.put(14, "3.50m³");
        aVLTree.put(15, "3.75m³");
        aVLTree.put(16, "4.00m³");
        aVLTree.put(17, "4.25m³");
        aVLTree.put(18, "4.50m³");
        aVLTree.put(19, "4.75m³");
        aVLTree.put(20, "5.00m³");
        aVLTree.put(21, "5.25m³");
        aVLTree.put(22, "5.50m³");
        aVLTree.put(23, "5.75m³");
        aVLTree.put(24, "6.00m³");
        aVLTree.put(25, "6.25m³");
        aVLTree.put(26, "6.50m³");
        aVLTree.put(27, "6.75m³");
        aVLTree.put(28, "7.00m³");
        aVLTree.put(29, "7.25m³");
        aVLTree.put(30, "7.50m³");
        aVLTree.put(31, "7.75m³");
        aVLTree.put(32, "8.00m³");
        aVLTree.put(33, "8.25m³");
        aVLTree.put(34, "8.50m³");
        aVLTree.put(35, "8.75m³");
        aVLTree.put(36, "9.00m³");
        aVLTree.put(37, "9.25m³");
        aVLTree.put(38, "9.50m³");
        aVLTree.put(39, "9.75m³");
        aVLTree.put(40, "10.00m³");
    }

    /**
     * Construtor responsável por instanciar a janela principal.
     */
    public Main() {
        garbageTruckUsageTree = new AVLTree<>(Integer::compareTo);
        gerateDataUsageGarbageTruck(garbageTruckUsageTree);
        initComponents();
        Controller.getInstance().addActionChangeConnection(isConnected -> {
            comboBoxIDs.setEnabled(isConnected);
            if (isConnected) {
                btnConnectToServer.setText("Desconectar");
                setTitle("Caminhão de Lixo Conectado");
            } else {
                btnConnectToServer.setText("Conectar");
                setTitle("Caminhão de Lixo Desconectado");
                progressBarRecycleBin.setValue(0);
                comboBoxIDs.removeAll();
                comboBoxIDs.setSelectedIndex(-1);
            }
        });
        labelLocation.setVisible(false);
        labelPriority.setVisible(false);
        Controller.getInstance().addActionChangeRecycle(data -> {
            switch (data.getString(USAGE)) {
                case NONE:
                    progressBarRecycleBin.setValue(0);
                    break;
                case LOW:
                    progressBarRecycleBin.setValue(1);
                    break;
                case MEDIUM:
                    progressBarRecycleBin.setValue(2);
                    break;
                case HIGH:
                    progressBarRecycleBin.setValue(3);
                    break;
                case TOTAL:
                    progressBarRecycleBin.setValue(4);
                    break;
                default:
                    progressBarRecycleBin.setValue(0);
                    break;
            }
            final String[] location = data.getString(LOCATION).split(";");
            labelLocation.setText("Localização: "
                    .concat("latitude: ").concat(location[0])
                    .concat(", longitude: ").concat(location[1])
            );
            labelPriority.setText("Prioridade: ".concat(
                    data.getString(IS_PRIORITY).equals("TRUE")
                    ? "SIM."
                    : "NÃO."
            ));
        });
        Controller.getInstance().disconnect();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBoxIDs = new javax.swing.JComboBox<>();
        btnConnectToServer = new javax.swing.JButton();
        btnShow = new javax.swing.JButton();
        labelLocation = new javax.swing.JLabel();
        btnClearTrash = new javax.swing.JButton();
        labelPriority = new javax.swing.JLabel();
        panelRecycleBin = new javax.swing.JPanel();
        panelRecycleBinUsage = new javax.swing.JPanel();
        lebelRecycleBinUsage = new javax.swing.JLabel();
        progressBarRecycleBin = new javax.swing.JProgressBar();
        panelUsageGarbageTruck = new javax.swing.JPanel();
        panelGarbageTruckUsage = new javax.swing.JPanel();
        lebelGarbageTruckUsage = new javax.swing.JLabel();
        progressBarGarbageTruck = new javax.swing.JProgressBar();
        jSeparator2 = new javax.swing.JSeparator();

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

        btnConnectToServer.setText("Conectar");
        btnConnectToServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectToServerActionPerformed(evt);
            }
        });

        btnShow.setText("Atualizar Exibição");
        btnShow.setEnabled(false);
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        labelLocation.setText("Localização: ");

        btnClearTrash.setText("Esvaziar Lixeira");
        btnClearTrash.setEnabled(false);
        btnClearTrash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearTrashActionPerformed(evt);
            }
        });

        labelPriority.setText("Prioridade:");

        lebelRecycleBinUsage.setText("Uso da Lixeira: 0.00m³");
        lebelRecycleBinUsage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lebelRecycleBinUsage.setEnabled(false);

        javax.swing.GroupLayout panelRecycleBinUsageLayout = new javax.swing.GroupLayout(panelRecycleBinUsage);
        panelRecycleBinUsage.setLayout(panelRecycleBinUsageLayout);
        panelRecycleBinUsageLayout.setHorizontalGroup(
            panelRecycleBinUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecycleBinUsageLayout.createSequentialGroup()
                .addComponent(lebelRecycleBinUsage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRecycleBinUsageLayout.setVerticalGroup(
            panelRecycleBinUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecycleBinUsageLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lebelRecycleBinUsage)
                .addGap(0, 0, 0))
        );

        progressBarRecycleBin.setMaximum(4);
        progressBarRecycleBin.setEnabled(false);
        progressBarRecycleBin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                progressBarRecycleBinStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelRecycleBinLayout = new javax.swing.GroupLayout(panelRecycleBin);
        panelRecycleBin.setLayout(panelRecycleBinLayout);
        panelRecycleBinLayout.setHorizontalGroup(
            panelRecycleBinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecycleBinLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRecycleBinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(panelRecycleBinUsage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBarRecycleBin, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRecycleBinLayout.setVerticalGroup(
            panelRecycleBinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRecycleBinLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelRecycleBinUsage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(progressBarRecycleBin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        lebelGarbageTruckUsage.setText("Uso do Caminhão: 0.00m³");
        lebelGarbageTruckUsage.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout panelGarbageTruckUsageLayout = new javax.swing.GroupLayout(panelGarbageTruckUsage);
        panelGarbageTruckUsage.setLayout(panelGarbageTruckUsageLayout);
        panelGarbageTruckUsageLayout.setHorizontalGroup(
            panelGarbageTruckUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGarbageTruckUsageLayout.createSequentialGroup()
                .addComponent(lebelGarbageTruckUsage)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGarbageTruckUsageLayout.setVerticalGroup(
            panelGarbageTruckUsageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGarbageTruckUsageLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lebelGarbageTruckUsage)
                .addGap(0, 0, 0))
        );

        progressBarGarbageTruck.setMaximum(40);
        progressBarGarbageTruck.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                progressBarGarbageTruckStateChanged(evt);
            }
        });

        javax.swing.GroupLayout panelUsageGarbageTruckLayout = new javax.swing.GroupLayout(panelUsageGarbageTruck);
        panelUsageGarbageTruck.setLayout(panelUsageGarbageTruckLayout);
        panelUsageGarbageTruckLayout.setHorizontalGroup(
            panelUsageGarbageTruckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsageGarbageTruckLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelUsageGarbageTruckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(panelGarbageTruckUsage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progressBarGarbageTruck, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelUsageGarbageTruckLayout.setVerticalGroup(
            panelUsageGarbageTruckLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsageGarbageTruckLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(panelGarbageTruckUsage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(progressBarGarbageTruck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(comboBoxIDs, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConnectToServer)
                    .addComponent(btnShow)
                    .addComponent(labelLocation)
                    .addComponent(btnClearTrash)
                    .addComponent(labelPriority)
                    .addComponent(panelRecycleBin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelUsageGarbageTruck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnConnectToServer)
                .addGap(15, 15, 15)
                .addComponent(labelLocation)
                .addGap(15, 15, 15)
                .addComponent(labelPriority)
                .addGap(15, 15, 15)
                .addComponent(btnClearTrash)
                .addGap(15, 15, 15)
                .addComponent(comboBoxIDs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(panelRecycleBin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(panelUsageGarbageTruck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxIDsPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboBoxIDsPopupMenuWillBecomeVisible
        try {
            Controller.getInstance().listRecycleBins(allIds -> {
                System.out.println(Arrays.toString(allIds));
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
        if (!Controller.getInstance().isConnected()) {
            ConnectWindow.showModal(this);
        } else {
            Controller.getInstance().disconnect();
        }
    }//GEN-LAST:event_btnConnectToServerActionPerformed

    private void progressBarRecycleBinStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_progressBarRecycleBinStateChanged
        switch (progressBarRecycleBin.getValue()) {
            case 0:
                lebelRecycleBinUsage.setForeground(getRGBColor(0, 0, 0));
                lebelRecycleBinUsage.setText("Uso da Lixeira: 0.00m³");
                break;
            case 1:
                lebelRecycleBinUsage.setForeground(getRGBColor(194, 104, 2));
                lebelRecycleBinUsage.setText("Uso da Lixeira: 0.25m³");
                break;
            case 2:
                lebelRecycleBinUsage.setForeground(getRGBColor(194, 104, 2));
                lebelRecycleBinUsage.setText("Uso da Lixeira: 0.50m³");
                break;
            case 3:
                lebelRecycleBinUsage.setForeground(getRGBColor(194, 104, 2));
                lebelRecycleBinUsage.setText("Uso da Lixeira: 0.75m³");
                break;
            case 4:
                lebelRecycleBinUsage.setForeground(getRGBColor(194, 104, 2));
                lebelRecycleBinUsage.setText("Uso da Lixeira: 1.00m³");
                break;
            default:
                break;
        }
    }//GEN-LAST:event_progressBarRecycleBinStateChanged

    private void comboBoxIDsPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboBoxIDsPopupMenuWillBecomeInvisible
        if (comboBoxIDs.getSelectedIndex() > -1) {
            if (!"".equals(comboBoxIDs.getItemAt(0))) {
                try {
                    final String id = comboBoxIDs.getSelectedItem().toString();
                    Controller.getInstance().showRecycleDetails(id);
                } catch (final IOException ex) {
                    JOptionPane.showConfirmDialog(
                            this,
                            "Conexão perdida! Código de erro: ".concat(ex.getMessage().concat(".")),
                            "Mensagem de Erro",
                            JOptionPane.CLOSED_OPTION,
                            JOptionPane.ERROR_MESSAGE
                    );
                }
                btnClearTrash.setEnabled(true);
                btnShow.setEnabled(true);
                labelLocation.setVisible(true);
                labelPriority.setVisible(true);
                progressBarRecycleBin.setEnabled(true);
                lebelRecycleBinUsage.setEnabled(true);
            } else {
                setEnabledActiveComponents(false, 0);
            }
        } else {
            setEnabledActiveComponents(false, 0);
        }
    }//GEN-LAST:event_comboBoxIDsPopupMenuWillBecomeInvisible

    private void setEnabledActiveComponents(final boolean state, final int position) {
        btnClearTrash.setEnabled(state);
        btnShow.setEnabled(state);
        progressBarRecycleBin.setValue(position);
        progressBarRecycleBin.setEnabled(state);
        lebelRecycleBinUsage.setEnabled(state);
        labelLocation.setVisible(state);
        labelPriority.setVisible(state);
    }

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        if (comboBoxIDs.getSelectedIndex() > -1) {
            try {
                final String id = comboBoxIDs.getSelectedItem().toString();
                Controller.getInstance().showRecycleDetails(id);
            } catch (final IOException ex) {
                JOptionPane.showConfirmDialog(
                        this,
                        "Conexão perdida! Código de erro: ".concat(ex.getMessage().concat(".")),
                        "Mensagem de Erro",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE
                );
                comboBoxIDs.removeAllItems();
                comboBoxIDsPopupMenuWillBecomeInvisible(null);
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

    private void btnClearTrashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearTrashActionPerformed
        if (comboBoxIDs.getSelectedIndex() > -1) {
            final int progress = progressBarRecycleBin.getValue() + progressBarGarbageTruck.getValue();
            if (progress <= 40) {
                try {
                    final String id = comboBoxIDs.getSelectedItem().toString();
                    Controller.getInstance().clearRecycle(id);
                    progressBarGarbageTruck.setValue(progress);
                    comboBoxIDs.setSelectedIndex(-1);
                    comboBoxIDsPopupMenuWillBecomeInvisible(null);
                } catch (final IOException ex) {
                    JOptionPane.showConfirmDialog(
                            this,
                            "Conexão perdida! Razão de erro: ".concat(ex.getMessage().concat(".")),
                            "Mensagem de Erro",
                            JOptionPane.CLOSED_OPTION,
                            JOptionPane.ERROR_MESSAGE
                    );
                    comboBoxIDs.removeAllItems();
                    comboBoxIDsPopupMenuWillBecomeInvisible(null);
                }
            } else {
                JOptionPane.showConfirmDialog(
                        this,
                        "Caminhão Cheio! Não é possível esvaziar esta lixeira.",
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
    }//GEN-LAST:event_btnClearTrashActionPerformed

    private void progressBarGarbageTruckStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_progressBarGarbageTruckStateChanged
        final String usage = garbageTruckUsageTree.find(
                progressBarGarbageTruck.getValue()
        ).getValue();
        System.out.println(usage);
        lebelGarbageTruckUsage.setForeground(progressBarGarbageTruck.getValue() > 0
                ? getRGBColor(194, 104, 2)
                : getRGBColor(0, 0, 0)
        );
        lebelGarbageTruckUsage.setText(progressBarGarbageTruck.getValue() > 0
                ? "Uso do Caminhão: ".concat(usage)
                : "Uso do Caminhão: 0.00m³"
        );
    }//GEN-LAST:event_progressBarGarbageTruckStateChanged

    private static Color getRGBColor(final int r, final int g, final int b) {
        final float[] rGBColor = Color.RGBtoHSB(r, g, b, null);
        return Color.getHSBColor(rGBColor[0], rGBColor[1], rGBColor[2]);
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
    private javax.swing.JButton btnClearTrash;
    private javax.swing.JButton btnConnectToServer;
    private javax.swing.JButton btnShow;
    private javax.swing.JComboBox<String> comboBoxIDs;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelLocation;
    private javax.swing.JLabel labelPriority;
    private javax.swing.JLabel lebelGarbageTruckUsage;
    private javax.swing.JLabel lebelRecycleBinUsage;
    private javax.swing.JPanel panelGarbageTruckUsage;
    private javax.swing.JPanel panelRecycleBin;
    private javax.swing.JPanel panelRecycleBinUsage;
    private javax.swing.JPanel panelUsageGarbageTruck;
    private javax.swing.JProgressBar progressBarGarbageTruck;
    private javax.swing.JProgressBar progressBarRecycleBin;
    // End of variables declaration//GEN-END:variables
}

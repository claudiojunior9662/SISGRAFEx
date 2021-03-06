/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.consulta.simatex;

import exception.EnvioExcecao;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ui.controle.Controle;

/**
 *
 * @author claud
 */
public class ConsultaSimatexFrame extends javax.swing.JInternalFrame {

    private JLabel loading;

    private static ConsultaSimatexFrame consultaSimatexFrame;

    public static ConsultaSimatexFrame getInstancia(JLabel loading) {
        return new ConsultaSimatexFrame(loading);
    }

    /**
     * Creates new form ConsultaSimatexFrame
     */
    public ConsultaSimatexFrame(JLabel loading) {
        initComponents();
        this.loading = loading;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        textoPesquisa = new javax.swing.JTextField();
        pesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaConsulta = new javax.swing.JTable();
        nomeMaterial = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        codigoMaterial = new javax.swing.JRadioButton();

        setTitle("CONSULTA ESTOQUE SIMATEX");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/consulta_banco.png"))); // NOI18N

        pesquisar.setText("PESQUISAR");
        pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisarActionPerformed(evt);
            }
        });

        tabelaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "C??DIGO MATERIAL", "NOME MATERIAL", "DESCRI????O", "QUANTIDADE", "VALOR UNIT??RIO", "INCLUS??O CARGA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelaConsulta);
        if (tabelaConsulta.getColumnModel().getColumnCount() > 0) {
            tabelaConsulta.getColumnModel().getColumn(0).setMinWidth(5);
            tabelaConsulta.getColumnModel().getColumn(0).setPreferredWidth(5);
            tabelaConsulta.getColumnModel().getColumn(2).setMinWidth(300);
            tabelaConsulta.getColumnModel().getColumn(2).setPreferredWidth(300);
            tabelaConsulta.getColumnModel().getColumn(3).setMinWidth(5);
            tabelaConsulta.getColumnModel().getColumn(3).setPreferredWidth(5);
            tabelaConsulta.getColumnModel().getColumn(4).setMinWidth(5);
            tabelaConsulta.getColumnModel().getColumn(4).setPreferredWidth(5);
            tabelaConsulta.getColumnModel().getColumn(5).setMinWidth(5);
            tabelaConsulta.getColumnModel().getColumn(5).setPreferredWidth(5);
        }

        buttonGroup1.add(nomeMaterial);
        nomeMaterial.setSelected(true);
        nomeMaterial.setText("NOME MATERIAL");

        jLabel2.setText("PESQUISAR POR:");

        buttonGroup1.add(codigoMaterial);
        codigoMaterial.setText("C??DIGO MATERIAL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(nomeMaterial)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(codigoMaterial))
                            .addComponent(textoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisar)
                        .addGap(0, 507, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeMaterial)
                    .addComponent(jLabel2)
                    .addComponent(codigoMaterial))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisarActionPerformed
        new Thread() {
            @Override
            public void run() {
                try {

                    loading.setVisible(true);
                    loading.setText("CARREGANDO...");

                    DefaultTableModel modeloTabela = (DefaultTableModel) tabelaConsulta.getModel();
                    modeloTabela.setNumRows(0);

                    if (textoPesquisa.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "INSIRA UM VALOR NO CAMPO 'NOME MATERIAL'", "ERRO", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (ConsultaSimatexBEAN consultaSimatexBEAN : ConsultaSimatexDAO.consultaMaterialSimatex(textoPesquisa.getText().toUpperCase(),
                            nomeMaterial.isSelected() ? (byte) 1 : (byte) 2)) {
                        modeloTabela.addRow(new Object[]{
                            consultaSimatexBEAN.getCodigoMaterial(),
                            consultaSimatexBEAN.getNomeMaterial(),
                            consultaSimatexBEAN.getDescricao(),
                            consultaSimatexBEAN.getQuantidade(),
                            consultaSimatexBEAN.getValorUnitario(),
                            consultaSimatexBEAN.getDataMovimentacao().toString()
                        });
                    }

                } catch (SQLException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                }
                loading.setVisible(false);
            }
        }.start();
    }//GEN-LAST:event_pesquisarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton codigoMaterial;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton nomeMaterial;
    private javax.swing.JButton pesquisar;
    private javax.swing.JTable tabelaConsulta;
    private javax.swing.JTextField textoPesquisa;
    // End of variables declaration//GEN-END:variables
}

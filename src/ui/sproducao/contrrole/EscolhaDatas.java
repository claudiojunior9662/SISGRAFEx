/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.sproducao.contrrole;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import entities.sisgrafex.OrdemProducao;
import exception.EnvioExcecao;
import javax.swing.JLabel;
import model.dao.OrdemProducaoDAO;
import ui.controle.Controle;

/**
 *
 * @author claud
 */
public class EscolhaDatas extends javax.swing.JInternalFrame {

    private static EscolhaDatas escolhaDatasNovo;
    private JLabel loading;

    public static EscolhaDatas getInstancia(JLabel loading) {
        return new EscolhaDatas(loading);
    }

    /**
     * Creates new form EscolhaDatasNovo
     */
    public EscolhaDatas(JLabel loading) {
        initComponents();
        this.loading = loading;

        Date data;
        for (OrdemProducao opBEAN : verificar_data()) {
            switch (TelaAcompanhamento.botao) {
                case 1:
                    data = opBEAN.getData1aProva();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 2:
                    data = opBEAN.getData2aProva();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 3:
                    data = opBEAN.getData3aProva();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 4:
                    data = opBEAN.getData4aProva();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 5:
                    data = opBEAN.getData5aProva();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 6:
                    data = opBEAN.getDataAprCliente();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 7:
                    data = opBEAN.getDataEntFinal();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 8:
                    data = opBEAN.getDataImpDir();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 9:
                    data = opBEAN.getDataEnvioDivCmcl();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 10:
                    data = opBEAN.getDataEntOffset();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 11:
                    data = opBEAN.getDataEntTipografia();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 12:
                    data = opBEAN.getDataEntAcabamento();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
                case 13:
                    data = opBEAN.getDataEntDigital();
                    if (data == null) {
                        calendario.setDate(new Date());
                    } else {
                        calendario.setDate(data);
                    }
                    texto_info.setText("A DATA PODE SER REDEFINIDA.");
                    break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        definir_data = new javax.swing.JButton();
        texto_info = new javax.swing.JLabel();
        calendario = new com.toedter.calendar.JCalendar();

        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/periodo.png"))); // NOI18N

        definir_data.setText("DEFINIR");
        definir_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                definir_dataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calendario, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(texto_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(definir_data)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(definir_data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(texto_info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void definir_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_definir_dataActionPerformed
        try {
            List descAtualizacao = new ArrayList();
            descAtualizacao.add(TelaAcompanhamento.botao);
            descAtualizacao.add(Controle.dataPadrao.format(calendario.getDate()));
            OrdemProducaoDAO.atualizaDadosLogOp(TelaAcompanhamento.numOp, descAtualizacao, (byte) 4);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        TelaAcompanhamento.salvaAlteracoes();
        this.dispose();
    }//GEN-LAST:event_definir_dataActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static com.toedter.calendar.JCalendar calendario;
    private javax.swing.JButton definir_data;
    private javax.swing.JLabel texto_info;
    // End of variables declaration//GEN-END:variables

    private List<OrdemProducao> verificar_data() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<OrdemProducao> cadastroop = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT * FROM tabela_ordens_producao WHERE cod = ?");
            stmt.setInt(1, (int) TelaAcompanhamento.numeroOp.getValue());
            rs = stmt.executeQuery();
            while (rs.next()) {
                OrdemProducao opBEAN = new OrdemProducao();
                opBEAN.setData1aProva(rs.getDate("data_1a_prova"));
                opBEAN.setData2aProva(rs.getDate("data_2a_prova"));
                opBEAN.setData3aProva(rs.getDate("data_3a_prova"));
                opBEAN.setData4aProva(rs.getDate("data_4a_prova"));
                opBEAN.setData5aProva(rs.getDate("data_5a_prova"));
                opBEAN.setDataAprCliente(rs.getDate("data_apr_cliente"));
                opBEAN.setDataEntFinal(rs.getDate("data_ent_final"));
                opBEAN.setDataImpDir(rs.getDate("data_imp_dir"));
                opBEAN.setDataEntOffset(rs.getDate("data_ent_offset"));
                opBEAN.setDataEntDigital(rs.getDate("DT_ENT_DIGITAL"));
                opBEAN.setDataEntTipografia(rs.getDate("data_ent_tipografia"));
                opBEAN.setDataEntAcabamento(rs.getDate("data_ent_acabamento"));
                opBEAN.setDataEnvioDivCmcl(rs.getDate("data_envio_div_cmcl"));
                cadastroop.add(opBEAN);
            }
            return cadastroop;
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
            return null;
        }
    }
}

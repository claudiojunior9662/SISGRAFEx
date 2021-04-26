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
import ui.controle.Controle;

/**
 *
 * @author claud
 */
public class EscolhaDatas extends javax.swing.JInternalFrame {

    private static EscolhaDatas escolhaDatasNovo;

    public static EscolhaDatas getInstancia() {
        return new EscolhaDatas();
    }

    /**
     * Creates new form EscolhaDatasNovo
     */
    public EscolhaDatas() {
        initComponents();

        Date data;
        for (OrdemProducao opBEAN : verificar_data()) {
            if (TelaAcompanhamento.botao.equals("primeira_prova") & (opBEAN.getData1aProva() != null)) {
                data = opBEAN.getData1aProva();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("segunda_prova") & (opBEAN.getData2aProva() != null)) {
                data = opBEAN.getData2aProva();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("terceira_prova") & (opBEAN.getData3aProva() != null)) {
                data = opBEAN.getData3aProva();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("quarta_prova") & (opBEAN.getData4aProva() != null)) {
                data = opBEAN.getData4aProva();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("quinta_prova") & (opBEAN.getData5aProva() != null)) {
                data = opBEAN.getData5aProva();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("aprovacao_cliente") & (opBEAN.getDataAprCliente() != null)) {
                data = opBEAN.getDataAprCliente();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("entrega_final") & (opBEAN.getDataEntFinal() != null)) {
                data = opBEAN.getDataEntFinal();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("imposta_direcao") & (opBEAN.getDataImpDir() != null)) {
                data = opBEAN.getDataImpDir();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }
            
            if (TelaAcompanhamento.botao.equals("entrada_digital") & (opBEAN.getDataEntDigital() != null)) {
                data = opBEAN.getDataEntDigital();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("entrada_offset") & (opBEAN.getDataEntOffset() != null)) {
                data = opBEAN.getDataEntOffset();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("entrada_tipografia") & (opBEAN.getDataEntTipografia() != null)) {
                data = opBEAN.getDataEntTipografia();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("entrada_acabamento") & (opBEAN.getDataEntAcabamento() != null)) {
                data = opBEAN.getDataEntAcabamento();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
            }

            if (TelaAcompanhamento.botao.equals("envio_div_cmcl") & (opBEAN.getDataEnvioDivCmcl() != null)) {
                data = opBEAN.getDataEnvioDivCmcl();
                calendario.setDate(data);
                texto_info.setText("A DATA PODE SER REDEFINIDA.");
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
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio();
            return null;
        }
        return cadastroop;
    }
}

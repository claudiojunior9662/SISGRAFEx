package ui.principal;

import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import ui.login.TelaAutenticacao;

public class ModulosInt extends javax.swing.JFrame {

    private GerenteJanelas gerenteJanelas;

    public ModulosInt() {
        initComponents();

        URL url = this.getClass().getResource("/ui/login/logo.png");
        Image imagemLogo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(imagemLogo);

        orcamento.setEnabled(false);
        estoque.setEnabled(false);
        producao.setEnabled(false);
        expedicao.setEnabled(false);
        financeiro.setEnabled(false);
        ordenador.setEnabled(false);
        tutoriais.setEnabled(true);

        if (TelaAutenticacao.getUsrLogado().getAcessoOrc() == 1
                | TelaAutenticacao.getUsrLogado().getLogin().equals("admin")) {
            orcamento.setEnabled(true);
        }
        if (TelaAutenticacao.getUsrLogado().getAcessoProd() == 1
                | TelaAutenticacao.getUsrLogado().getLogin().equals("admin")) {
            producao.setEnabled(true);
        }
        if (TelaAutenticacao.getUsrLogado().getAcessoExp() == 1
                | TelaAutenticacao.getUsrLogado().getLogin().equals("admin")) {
            expedicao.setEnabled(true);
        }
        if (TelaAutenticacao.getUsrLogado().getAcessoFin() == 1
                | TelaAutenticacao.getUsrLogado().getLogin().equals("admin")) {
            financeiro.setEnabled(true);
        }
        if (TelaAutenticacao.getUsrLogado().getAcessoEst() == 1
                | TelaAutenticacao.getUsrLogado().getLogin().equals("admin")) {
            estoque.setEnabled(true);
        }
        if (TelaAutenticacao.getUsrLogado().getAcessoOrd() == 1
                | TelaAutenticacao.getUsrLogado().getLogin().equals("admin")) {
            ordenador.setEnabled(true);
        }

        this.setDefaultCloseOperation(ModulosInt.EXIT_ON_CLOSE);

        if (TelaAutenticacao.getUsrLogado().getTipo().equals("ADMINISTRADOR")
                | TelaAutenticacao.getUsrLogado().getLogin().equals("admin")) {
            painelAdministrador.setEnabled(true);
        } else {
            painelAdministrador.setEnabled(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel2 = new javax.swing.JPanel();
        producao = new javax.swing.JButton();
        financeiro = new javax.swing.JButton();
        orcamento = new javax.swing.JButton();
        estoque = new javax.swing.JButton();
        expedicao = new javax.swing.JButton();
        ordenador = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        tutoriais = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        painelAdministrador = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "MÓDULOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 11))); // NOI18N

        producao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/producao.png"))); // NOI18N
        producao.setText("PRODUÇÃO");
        producao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                producaoActionPerformed(evt);
            }
        });

        financeiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/financeiro.png"))); // NOI18N
        financeiro.setText("FINANCEIRO");
        financeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                financeiroActionPerformed(evt);
            }
        });

        orcamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/orçamento.png"))); // NOI18N
        orcamento.setText("ORÇAMENTO");
        orcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orcamentoActionPerformed(evt);
            }
        });

        estoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/estoque.png"))); // NOI18N
        estoque.setText("ESTOQUE");
        estoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                estoqueActionPerformed(evt);
            }
        });

        expedicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/carrinho.png"))); // NOI18N
        expedicao.setText("EXPEDIÇÃO");
        expedicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expedicaoActionPerformed(evt);
            }
        });

        ordenador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/ordenador_despesas.png"))); // NOI18N
        ordenador.setText("ORD. DESPESAS");
        ordenador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordenadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(financeiro)
                    .addComponent(orcamento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(producao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(expedicao))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(estoque)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ordenador))))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {estoque, expedicao, financeiro, orcamento, ordenador, producao});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(producao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(expedicao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(estoque, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(financeiro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ordenador, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "PÚBLICO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 11))); // NOI18N

        tutoriais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/tutoriais.png"))); // NOI18N
        tutoriais.setText("TUTORIAIS");
        tutoriais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tutoriaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(tutoriais)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(tutoriais, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/principal/brasaoGrafica-133x191.png"))); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "ADMINISTRADOR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 11))); // NOI18N

        painelAdministrador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/painel_controle.png"))); // NOI18N
        painelAdministrador.setText("PAINEL DE CONTROLE");
        painelAdministrador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                painelAdministradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(painelAdministrador)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(painelAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 50, Short.MAX_VALUE))
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("© SISGRAFEx - Seção de Informática - Gráfica do Exército (2018 - 2021)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tutoriaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tutoriaisActionPerformed
        Desktop d = Desktop.getDesktop();
        try {
            JOptionPane.showMessageDialog(null, "Usuário Emby: 'grafica ex' - Sem senha");
            d.browse(new URI("http://10.67.32.251:8096"));
        } catch (URISyntaxException ex) {
            Logger.getLogger(ModulosInt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModulosInt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tutoriaisActionPerformed

    private void orcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orcamentoActionPerformed
        OrcamentoFrame orcamentoNovo = new OrcamentoFrame();
        orcamentoNovo.setLocationRelativeTo(null);
        orcamentoNovo.setDefaultCloseOperation(EXIT_ON_CLOSE);
        orcamentoNovo.setTitle("MÓDULOS - ORÇAMENTO");
        this.setVisible(false);
        orcamentoNovo.setVisible(true);
    }//GEN-LAST:event_orcamentoActionPerformed

    private void producaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_producaoActionPerformed
        Producao p = new Producao();
        p.setLocationRelativeTo(null);
        p.setDefaultCloseOperation(EXIT_ON_CLOSE);
        p.setExtendedState(MAXIMIZED_BOTH);
        p.setTitle("MÓDULOS - PRODUÇÃO");
        this.setVisible(false);
        p.setVisible(true);
    }//GEN-LAST:event_producaoActionPerformed

    private void expedicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expedicaoActionPerformed
        Expedicao exp = new Expedicao();
        exp.setLocationRelativeTo(null);
        exp.setDefaultCloseOperation(EXIT_ON_CLOSE);
        exp.setTitle("MÓDULOS - EXPEDIÇÃO");
        exp.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_expedicaoActionPerformed

    private void financeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_financeiroActionPerformed
        Financeiro fin = new Financeiro();
        fin.setLocationRelativeTo(null);
        fin.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fin.setTitle("MÓDULOS - FINANCEIRO");
        fin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_financeiroActionPerformed

    private void estoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_estoqueActionPerformed
        Estoque estoque = new Estoque();
        estoque.setLocationRelativeTo(null);
        estoque.setDefaultCloseOperation(EXIT_ON_CLOSE);
        estoque.setTitle("MÓDULOS - ESTOQUE");
        this.dispose();
        estoque.setVisible(true);
    }//GEN-LAST:event_estoqueActionPerformed

    private void ordenadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordenadorActionPerformed
        OrdenadorInt ordenador = new OrdenadorInt();
        ordenador.setLocationRelativeTo(null);
        ordenador.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ordenador.setTitle("MÓDULOS - ORDENADOR DE DESPESAS");
        this.dispose();
        ordenador.setVisible(true);
    }//GEN-LAST:event_ordenadorActionPerformed

    private void painelAdministradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_painelAdministradorActionPerformed
        Administrador administrador = new Administrador();
        administrador.setLocationRelativeTo(null);
        administrador.setDefaultCloseOperation(EXIT_ON_CLOSE);
        administrador.setTitle("MÓDULOS - ADMINISTRADOR");
        this.dispose();
        administrador.setVisible(true);
    }//GEN-LAST:event_painelAdministradorActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModulosInt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModulosInt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModulosInt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModulosInt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModulosInt().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton estoque;
    public static javax.swing.JButton expedicao;
    public static javax.swing.JButton financeiro;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JButton orcamento;
    public static javax.swing.JButton ordenador;
    public static javax.swing.JButton painelAdministrador;
    public static javax.swing.JButton producao;
    public static javax.swing.JButton tutoriais;
    // End of variables declaration//GEN-END:variables
    public void abrirJanelas(JInternalFrame jInternalFrame) {
        if (jInternalFrame.isVisible()) {
            jInternalFrame.toFront();
            jInternalFrame.requestFocus();
        } else {

        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.principal;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import ui.cadastros.clientes.ClienteBEAN;
import ui.cadastros.clientes.ClienteCadastro;
import ui.cadastros.clientes.ClienteDAO;
import ui.cadastros.clientes.RelatorioContabilidade;
import ui.cadastros.notas.NCFrame;
import ui.controle.Controle;
import ui.login.TelaAutenticacao;
import ui.relatorios.financeiro.RelatorioFat;
import ui.relatorios.financeiro.RelatoriosNotasCredito;

/**
 *
 * @author claud
 */
public class Financeiro extends javax.swing.JFrame {

    /**
     * Creates new form FinanceiroNovo
     */
    public Financeiro() {
        initComponents();

        atualizacao.setVisible(false);
        
        StringBuilder avProd = new StringBuilder();
        new Thread("Atualizações financeiro") {
            @Override
            public void run() {
                while (true) {
                    try {
                        try {
                            for (ClienteBEAN cliente : ClienteDAO.retornaCredNeg()) {
                                DecimalFormat df = new DecimalFormat("###,##0.00");
                                avProd.append("\n\n- " + cliente.getCod() + " (" + (cliente.getTipoCliente() == 1 ? "PF" : "PJ") + ")");
                                avProd.append(" / " + (cliente.getTipoCliente() == 1 ? cliente.getNome() : cliente.getNomeFantasia()));
                                avProd.append(" / R$ " + df.format(cliente.getCredito()));
                            }
                            
                            if (avProd.length() == 0) {
                                avProd.append("Sem Avisos");
                            }
                            
                            taAvisos.setText("* Dicas de utilização:\n\n"
                                    + Controle.retornaAvFin()
                                    + "\n\n* Clientes com saldo negativo:"
                                    + avProd.toString()
                            );
                            
                            if(Controle.verificaVersao(TelaAutenticacao.getCodVersao(), 
                                    TelaAutenticacao.getUpdate())){
                                atualizacao.setVisible(true);
                            }
                            Thread.sleep(600000);
                        } catch (SQLException ex) {
                            Thread.sleep(600000);
                            Logger.getLogger(Financeiro.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (InterruptedException ex) {
                        Thread.interrupted();
                        Logger.getLogger(Financeiro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }.start();

        URL url = this.getClass().getResource("/ui/login/logo.png");
        Image imagemLogo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(imagemLogo);

        Controle.defineStatus(statusPane);
        Controle.setDefaultGj(new GerenteJanelas(areaDeTrabalho));
        menuRelatorios.setEnabled(true);
        loadingHide();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        statusPane = new javax.swing.JTextPane();
        ImageIcon icon = new ImageIcon(getClass().getResource("/ui/principal/brasaoGraficaBala-475x288.png"));
        Image image = icon.getImage();
        areaDeTrabalho = new javax.swing.JDesktopPane(){

            public void paintComponent(Graphics g){
                Dimension d = areaDeTrabalho.getSize();
                g.drawImage(image,(d.width - 475) / 2,(d.height - 288) / 2,475,288,null);
            }

        };
        jScrollPane2 = new javax.swing.JScrollPane();
        taAvisos = new javax.swing.JTextArea();
        atualizacao = new javax.swing.JLabel();
        loading = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuPrincipal = new javax.swing.JMenu();
        menuCadastrosClientes = new javax.swing.JMenuItem();
        menuNotaCredito = new javax.swing.JMenuItem();
        menuRelatorios = new javax.swing.JMenu();
        relatoriosNotaCredito = new javax.swing.JMenuItem();
        relatoriosNotaVenda = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        menuModulos = new javax.swing.JMenu();
        menuSair = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);

        jScrollPane1.setViewportView(statusPane);

        areaDeTrabalho.setBackground(java.awt.Color.lightGray);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("AVISOS"));
        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setFocusable(false);
        jScrollPane2.setRequestFocusEnabled(false);

        taAvisos.setEditable(false);
        taAvisos.setColumns(20);
        taAvisos.setLineWrap(true);
        taAvisos.setRows(5);
        taAvisos.setWrapStyleWord(true);
        taAvisos.setDragEnabled(true);
        jScrollPane2.setViewportView(taAvisos);

        areaDeTrabalho.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout areaDeTrabalhoLayout = new javax.swing.GroupLayout(areaDeTrabalho);
        areaDeTrabalho.setLayout(areaDeTrabalhoLayout);
        areaDeTrabalhoLayout.setHorizontalGroup(
            areaDeTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, areaDeTrabalhoLayout.createSequentialGroup()
                .addContainerGap(519, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        areaDeTrabalhoLayout.setVerticalGroup(
            areaDeTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(areaDeTrabalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addContainerGap())
        );

        atualizacao.setBackground(new java.awt.Color(255, 102, 102));
        atualizacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atualizacao.setText("Uma atualização do sistema está disponível. Quando finalizar as operações, feche esta instância e abra novamente pela INTRANET.");
        atualizacao.setOpaque(true);

        loading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/orcamentos/operacoes/carregando.gif"))); // NOI18N

        menuPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/menu.png"))); // NOI18N
        menuPrincipal.setText("MENU");
        menuPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuPrincipalMousePressed(evt);
            }
        });
        menuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrincipalActionPerformed(evt);
            }
        });

        menuCadastrosClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/cadastros.png"))); // NOI18N
        menuCadastrosClientes.setText("CADASTRO DE CLIENTES");
        menuCadastrosClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadastrosClientesActionPerformed(evt);
            }
        });
        menuPrincipal.add(menuCadastrosClientes);

        menuNotaCredito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/notaCredito.png"))); // NOI18N
        menuNotaCredito.setText("NOTA DE CRÉDITO");
        menuNotaCredito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuNotaCreditoMousePressed(evt);
            }
        });
        menuNotaCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNotaCreditoActionPerformed(evt);
            }
        });
        menuPrincipal.add(menuNotaCredito);

        jMenuBar1.add(menuPrincipal);

        menuRelatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/relatorios.png"))); // NOI18N
        menuRelatorios.setText("RELATÓRIOS");
        menuRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelatoriosActionPerformed(evt);
            }
        });

        relatoriosNotaCredito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/notaCredito.png"))); // NOI18N
        relatoriosNotaCredito.setText("NOTAS DE CRÉDITO");
        relatoriosNotaCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatoriosNotaCreditoActionPerformed(evt);
            }
        });
        menuRelatorios.add(relatoriosNotaCredito);

        relatoriosNotaVenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/faturamento.png"))); // NOI18N
        relatoriosNotaVenda.setText("FATURAMENTOS");
        relatoriosNotaVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatoriosNotaVendaActionPerformed(evt);
            }
        });
        menuRelatorios.add(relatoriosNotaVenda);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/creditoClientes.png"))); // NOI18N
        jMenuItem1.setText("CRÉDITO DE CLIENTES");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuRelatorios.add(jMenuItem1);

        jMenuBar1.add(menuRelatorios);

        menuModulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/modulos.png"))); // NOI18N
        menuModulos.setText("MÓDULOS");
        menuModulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuModulosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuModulosMousePressed(evt);
            }
        });
        menuModulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuModulosActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuModulos);

        menuSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sair.png"))); // NOI18N
        menuSair.setText("SAIR");
        menuSair.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuSairMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menuSairMousePressed(evt);
            }
        });
        menuSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSairActionPerformed(evt);
            }
        });
        jMenuBar1.add(menuSair);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 843, Short.MAX_VALUE)
            .addComponent(areaDeTrabalho)
            .addComponent(loading, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(atualizacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(areaDeTrabalho)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(atualizacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loading, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCadastrosClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadastrosClientesActionPerformed
        Controle.getDefaultGj().abrirJanelas(ClienteCadastro.getInstancia(loading,Controle.getDefaultGj(), (byte) 3), "CADASTRO DE CLIENTES");
    }//GEN-LAST:event_menuCadastrosClientesActionPerformed

    private void menuNotaCreditoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuNotaCreditoMousePressed

    }//GEN-LAST:event_menuNotaCreditoMousePressed

    private void menuNotaCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNotaCreditoActionPerformed
        Controle.getDefaultGj().abrirJanelas(NCFrame.getInstancia(loading,Controle.getDefaultGj()), "NOTA DE CRÉDITO");
    }//GEN-LAST:event_menuNotaCreditoActionPerformed

    private void menuPrincipalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPrincipalMousePressed

    }//GEN-LAST:event_menuPrincipalMousePressed

    private void menuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrincipalActionPerformed

    }//GEN-LAST:event_menuPrincipalActionPerformed

    private void relatoriosNotaCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatoriosNotaCreditoActionPerformed
        Controle.getDefaultGj().abrirJanelas(RelatoriosNotasCredito.getInstancia(loading), "RELATÓRIOS - NOTAS DE CRÉDITO");
    }//GEN-LAST:event_relatoriosNotaCreditoActionPerformed

    private void menuRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelatoriosActionPerformed

    }//GEN-LAST:event_menuRelatoriosActionPerformed

    private void menuModulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuModulosMouseClicked

    }//GEN-LAST:event_menuModulosMouseClicked

    private void menuModulosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuModulosMousePressed
        ModulosInt m = new ModulosInt();
        m.setLocationRelativeTo(null);
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        m.setTitle("MÓDULOS");
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuModulosMousePressed

    private void menuModulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuModulosActionPerformed

    }//GEN-LAST:event_menuModulosActionPerformed

    private void menuSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSairMouseClicked

    }//GEN-LAST:event_menuSairMouseClicked

    private void menuSairMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSairMousePressed
        Controle.sair(this);
    }//GEN-LAST:event_menuSairMousePressed

    private void menuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSairActionPerformed

    }//GEN-LAST:event_menuSairActionPerformed

    private void relatoriosNotaVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatoriosNotaVendaActionPerformed
        Controle.getDefaultGj().abrirJanelas(RelatorioFat.getInstancia(loading), "RELATÓRIOS - FATURAMENTOS");
    }//GEN-LAST:event_relatoriosNotaVendaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Controle.getDefaultGj().abrirJanelas(RelatorioContabilidade.getInstancia(loading), "RELATÓRIOS - CRÉDITO DE CLIENTES");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Financeiro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Financeiro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane areaDeTrabalho;
    public static javax.swing.JLabel atualizacao;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel loading;
    private javax.swing.JMenuItem menuCadastrosClientes;
    private javax.swing.JMenu menuModulos;
    private javax.swing.JMenuItem menuNotaCredito;
    private javax.swing.JMenu menuPrincipal;
    private javax.swing.JMenu menuRelatorios;
    private javax.swing.JMenu menuSair;
    private javax.swing.JMenuItem relatoriosNotaCredito;
    private javax.swing.JMenuItem relatoriosNotaVenda;
    private javax.swing.JTextPane statusPane;
    private javax.swing.JTextArea taAvisos;
    // End of variables declaration//GEN-END:variables
public static void loadingVisible(String texto) {
        loading.setVisible(true);
        loading.setText(texto);
    }

    public static void loadingHide() {
        loading.setVisible(false);
        loading.setText("");
    }
}

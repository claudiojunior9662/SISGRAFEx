/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.principal;

import entities.sisgrafex.AlteraData;
import entities.sisgrafex.Orcamento;
import exception.EnvioExcecao;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.dao.OrcamentoDAO;
import model.dao.OrdemProducaoDAO;
import ui.cadastros.acabamentos.AcabamentosCadastro;
import ui.cadastros.chapas.ChapaCadastro;
import ui.cadastros.clientes.ClienteCadastro;
import model.dao.ClienteDAO;
import ui.cadastros.papeis.PapelCadastro;
import model.dao.ProdutoDAO;
import ui.cadastros.produtos.ProdutoFrame;
import ui.cadastros.produtos.ProdutoPrEntBEAN;
import ui.cadastros.servicos.ServicosFrame;
import ui.consulta.simatex.ConsultaSimatexFrame;
import ui.controle.Controle;
import ui.login.TelaAutenticacao;
import ui.orcamentos.operacoes.OrcamentoPrincipalFrame;
import ui.ordemProducao.consultas.OpConsultaFrame;
import ui.relatorios.orcamentos.RelatorioOrcamentos;
import ui.relatorios.ordemProducao.RelatoriosOrdemProducao;
import ui.relatorios.papeis.RelatoriosPapeis;

/**
 *
 * @author claud
 */
public class OrcamentoFrame extends javax.swing.JFrame {

    /**
     * Creates new form OrcamentoNovo
     */
    public OrcamentoFrame() {
        initComponents();
        atualizacao.setVisible(false);
        Controle.defineStatus(statusPane);
        Controle.setDefaultGj(new GerenteJanelas(areaDeTrabalho));
        loadingHide();

        new Thread("Atualizações orçamento") {
            @Override
            public void run() {
                while (true) {
                    try {
                        StringBuilder avProd = new StringBuilder();
                        StringBuilder avOrc = new StringBuilder();
                        try {
                            for (ProdutoPrEntBEAN prod : ProdutoDAO.retornaAvisoEstoquePe()) {
                                avProd.append("- ");
                                avProd.append(" CÓDIGO: " + prod.getCodigo());
                                avProd.append(" / DESCRIÇÃO: " + prod.getDescricao());
                                avProd.append(" / ESTOQUE DISPONÍVEL: " + prod.getEstoque());
                                avProd.append("\n");
                            }

                            if (avProd.length() == 0) {
                                avProd.append("Sem avisos");
                            }

                            for (Orcamento orc : OrcamentoDAO.retornaApOD()) {
                                avOrc.append("- ");
                                avOrc.append(" " + orc.getCod());
                                avOrc.append(" / " + ClienteDAO.retornaNomeCliente(
                                        orc.getCodCliente(), (byte) orc.getTipoPessoa()));
                                avOrc.append("\n");
                            }

                            if (avOrc.length() == 0) {
                                avOrc.append("Sem avisos");
                            }

                            taAvisos.setText("* Dicas de utilização:\n\n"
                                    + Controle.retornaAvOrc()
                                    + "\n\n* Propostas aprovadas pelo OD:\n\n"
                                    + avOrc.toString()
                                    + "\n* Produtos abaixo do estoque:\n\n"
                                    + avProd.toString());

                            if (Controle.verificaVersao(TelaAutenticacao.getCodVersao(),
                                    TelaAutenticacao.getUpdate())) {
                                atualizacao.setVisible(true);
                            }
                            Thread.sleep(600000);
                        } catch (SQLException ex) {
                            Thread.sleep(600000);
                            Logger.getLogger(OrcamentoFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (InterruptedException ex) {
                        Thread.interrupted();
                        Logger.getLogger(OrcamentoFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
        cancelaPorPrazo();

        try {
            StringBuilder listaAlteracoes = new StringBuilder();
            listaAlteracoes.append("ALTERAÇÕES NAS DATAS DE ENTREGA DE OP!!\n\n");
            for (AlteraData ad : OrcamentoDAO.consultarAlteracoes()) {
                listaAlteracoes.append(
                        "OP: " + ad.getCodigoOp()
                        + "\n"
                        + "DATA ANTERIOR: "
                        + Controle.dataPadrao.format(ad.getDataAnterior())
                        + "\n"
                        + "NOVA DATA: "
                        + Controle.dataPadrao.format(OrdemProducaoDAO.retornaDataEntregaOp(ad.getCodigoOp()))
                        + "\n"
                        + "MOTIVO: "
                        + ad.getMotivo()
                        + "\n"
                        + "USUÁRIO: "
                        + ad.getUsuario()
                        + "\n\n");
            }
            JOptionPane.showMessageDialog(null, listaAlteracoes.toString(), "ALTERAÇÕES DE ORDEM DE PRODUÇÃO", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        statusPane = new javax.swing.JTextPane();
        //ImageIcon icon = new ImageIcon(getClass().getResource("/ui/principal/brasaoGraficaBala-475x288.png"));
        //Image image = icon.getImage();
        areaDeTrabalho = new javax.swing.JDesktopPane(){

            //    public void paintComponent(Graphics g){
                //        Dimension d = areaDeTrabalho.getSize();
                //        g.drawImage(image,(d.width - 475) / 2,(d.height - 288) / 2,475,288,null);
                //    }

        };
        jScrollPane2 = new javax.swing.JScrollPane();
        taAvisos = new javax.swing.JTextArea();
        loading = new javax.swing.JLabel();
        atualizacao = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuPrincipal = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        menClientes = new javax.swing.JMenuItem();
        menAcabamentos = new javax.swing.JMenuItem();
        menSvOrcamento = new javax.swing.JMenuItem();
        menTipoPapel = new javax.swing.JMenuItem();
        menMedidaPapel = new javax.swing.JMenuItem();
        menFormatoImpresao = new javax.swing.JMenuItem();
        menMateriais = new javax.swing.JMenuItem();
        menTintas = new javax.swing.JMenuItem();
        menPapeis = new javax.swing.JMenuItem();
        menChapas = new javax.swing.JMenuItem();
        menProdutos = new javax.swing.JMenuItem();
        menuOrcamento = new javax.swing.JMenuItem();
        menuOrdensProducao = new javax.swing.JMenuItem();
        menuConsultaSimatex = new javax.swing.JMenuItem();
        menuRelatorios = new javax.swing.JMenu();
        relatoriosOrdemProducao = new javax.swing.JMenuItem();
        relatoriosOrcamentos = new javax.swing.JMenuItem();
        relatoriosConsPapeis = new javax.swing.JMenuItem();
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

        javax.swing.GroupLayout areaDeTrabalhoLayout = new javax.swing.GroupLayout(areaDeTrabalho);
        areaDeTrabalho.setLayout(areaDeTrabalhoLayout);
        areaDeTrabalhoLayout.setHorizontalGroup(
            areaDeTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, areaDeTrabalhoLayout.createSequentialGroup()
                .addContainerGap(518, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        areaDeTrabalhoLayout.setVerticalGroup(
            areaDeTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(areaDeTrabalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addContainerGap())
        );
        areaDeTrabalho.setLayer(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        loading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/orcamentos/operacoes/carregando.gif"))); // NOI18N

        atualizacao.setBackground(new java.awt.Color(255, 102, 102));
        atualizacao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atualizacao.setText("Uma atualização do sistema está disponível. Quando finalizar as operações, feche esta instância e abra novamente pela INTRANET.");
        atualizacao.setOpaque(true);

        menuPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/menu.png"))); // NOI18N
        menuPrincipal.setText("MENU");
        menuPrincipal.setToolTipText("Fazer e consultas orçamentos");
        menuPrincipal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPrincipalActionPerformed(evt);
            }
        });

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/cadastros.png"))); // NOI18N
        jMenu1.setText("CADASTROS");

        menClientes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK));
        menClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/cliente.png"))); // NOI18N
        menClientes.setText("CLIENTES");
        menClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menClientesActionPerformed(evt);
            }
        });
        jMenu1.add(menClientes);

        menAcabamentos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        menAcabamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/acabamentos.png"))); // NOI18N
        menAcabamentos.setText("ACABAMENTOS");
        menAcabamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAcabamentosActionPerformed(evt);
            }
        });
        jMenu1.add(menAcabamentos);

        menSvOrcamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        menSvOrcamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/servicos_orcamento.png"))); // NOI18N
        menSvOrcamento.setText("SERVIÇOS DO ORÇAMENTO");
        menSvOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menSvOrcamentoActionPerformed(evt);
            }
        });
        jMenu1.add(menSvOrcamento);

        menTipoPapel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/tipo_de_papel.png"))); // NOI18N
        menTipoPapel.setText("TIPOS DE PAPÉIS");
        menTipoPapel.setEnabled(false);
        jMenu1.add(menTipoPapel);

        menMedidaPapel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/medida_de_papel.png"))); // NOI18N
        menMedidaPapel.setText("MEDIDAS DE PAPEL");
        menMedidaPapel.setEnabled(false);
        jMenu1.add(menMedidaPapel);

        menFormatoImpresao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/formato_de_impressao.png"))); // NOI18N
        menFormatoImpresao.setText("FORMATO DE IMPRESSÃO");
        menFormatoImpresao.setEnabled(false);
        jMenu1.add(menFormatoImpresao);

        menMateriais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/materiais.png"))); // NOI18N
        menMateriais.setText("MATERIAIS");
        menMateriais.setEnabled(false);
        jMenu1.add(menMateriais);

        menTintas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/tintas.png"))); // NOI18N
        menTintas.setText("TINTAS");
        menTintas.setEnabled(false);
        jMenu1.add(menTintas);

        menPapeis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        menPapeis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/papeis.png"))); // NOI18N
        menPapeis.setText("PAPÉIS");
        menPapeis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPapeisActionPerformed(evt);
            }
        });
        jMenu1.add(menPapeis);

        menChapas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        menChapas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/chapas.png"))); // NOI18N
        menChapas.setText("CHAPAS");
        menChapas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menChapasActionPerformed(evt);
            }
        });
        jMenu1.add(menChapas);

        menProdutos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        menProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/produto.png"))); // NOI18N
        menProdutos.setText("PRODUTOS");
        menProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menProdutosActionPerformed(evt);
            }
        });
        jMenu1.add(menProdutos);

        menuPrincipal.add(jMenu1);

        menuOrcamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuOrcamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/orçamento.png"))); // NOI18N
        menuOrcamento.setText("ORÇAMENTO");
        menuOrcamento.setToolTipText("Produzir e consultar orçamentos (Atalho CTRL + O)");
        menuOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOrcamentoActionPerformed(evt);
            }
        });
        menuPrincipal.add(menuOrcamento);

        menuOrdensProducao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        menuOrdensProducao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/carrinho.png"))); // NOI18N
        menuOrdensProducao.setText("ORDEM DE PRODUÇÃO");
        menuOrdensProducao.setToolTipText("Consultar ordem de produção (Atalho CTRL + P)");
        menuOrdensProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOrdensProducaoActionPerformed(evt);
            }
        });
        menuPrincipal.add(menuOrdensProducao);

        menuConsultaSimatex.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuConsultaSimatex.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/consulta_banco.png"))); // NOI18N
        menuConsultaSimatex.setText("CONSULTA SIMATEX");
        menuConsultaSimatex.setToolTipText("Consultar estoque existente no SIMATEx (Atalho CTRL + S)");
        menuConsultaSimatex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConsultaSimatexActionPerformed(evt);
            }
        });
        menuPrincipal.add(menuConsultaSimatex);

        jMenuBar1.add(menuPrincipal);

        menuRelatorios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/relatorios.png"))); // NOI18N
        menuRelatorios.setText("RELATÓRIOS");
        menuRelatorios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelatoriosActionPerformed(evt);
            }
        });

        relatoriosOrdemProducao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        relatoriosOrdemProducao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/carrinho.png"))); // NOI18N
        relatoriosOrdemProducao.setText("ORDEM DE PRODUÇÃO");
        relatoriosOrdemProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatoriosOrdemProducaoActionPerformed(evt);
            }
        });
        menuRelatorios.add(relatoriosOrdemProducao);

        relatoriosOrcamentos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        relatoriosOrcamentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/orçamento.png"))); // NOI18N
        relatoriosOrcamentos.setText("ORÇAMENTOS");
        relatoriosOrcamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatoriosOrcamentosActionPerformed(evt);
            }
        });
        menuRelatorios.add(relatoriosOrcamentos);

        relatoriosConsPapeis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        relatoriosConsPapeis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/papeis.png"))); // NOI18N
        relatoriosConsPapeis.setText("CONSUMO DE PAPÉIS");
        relatoriosConsPapeis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                relatoriosConsPapeisActionPerformed(evt);
            }
        });
        menuRelatorios.add(relatoriosConsPapeis);

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
            .addComponent(jScrollPane1)
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

    private void menuOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOrcamentoActionPerformed
        Controle.getDefaultGj().abrirJanelas(OrcamentoPrincipalFrame.getInstancia(loading, Controle.getDefaultGj(), (byte) 1), "ORÇAMENTO");
    }//GEN-LAST:event_menuOrcamentoActionPerformed

    private void menuOrdensProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOrdensProducaoActionPerformed
        Controle.getDefaultGj().abrirJanelas(OpConsultaFrame.getInstancia(loading, (byte) 1), "CONSULTAS DE ORDENS DE PRODUÇÃO");
    }//GEN-LAST:event_menuOrdensProducaoActionPerformed

    private void menuPrincipalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPrincipalActionPerformed

    }//GEN-LAST:event_menuPrincipalActionPerformed

    private void menuModulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuModulosActionPerformed

    }//GEN-LAST:event_menuModulosActionPerformed

    private void menuSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSairActionPerformed

    }//GEN-LAST:event_menuSairActionPerformed

    private void menuModulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuModulosMouseClicked

    }//GEN-LAST:event_menuModulosMouseClicked

    private void menuSairMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSairMouseClicked

    }//GEN-LAST:event_menuSairMouseClicked

    private void menuRelatoriosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelatoriosActionPerformed

    }//GEN-LAST:event_menuRelatoriosActionPerformed

    private void relatoriosOrdemProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatoriosOrdemProducaoActionPerformed
        Controle.getDefaultGj().abrirJanelas(RelatoriosOrdemProducao.getInstancia(loading), "RELATÓRIOS - ORDEM DE PRODUÇÃO");
    }//GEN-LAST:event_relatoriosOrdemProducaoActionPerformed

    private void menuModulosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuModulosMousePressed
        ModulosInt m = new ModulosInt();
        m.setLocationRelativeTo(null);
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        m.setTitle("MÓDULOS");
        m.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuModulosMousePressed

    private void menuSairMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuSairMousePressed
        Controle.sair(this);
    }//GEN-LAST:event_menuSairMousePressed

    private void menuConsultaSimatexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConsultaSimatexActionPerformed
        Controle.getDefaultGj().abrirJanelas(ConsultaSimatexFrame.getInstancia(loading),
                "CONSULTA MATERIAL SIMATEX");
    }//GEN-LAST:event_menuConsultaSimatexActionPerformed

    private void relatoriosOrcamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatoriosOrcamentosActionPerformed
        Controle.getDefaultGj().abrirJanelas(RelatorioOrcamentos.getInstancia(loading),
                "RELATÓRIOS - ORÇAMENTOS");
    }//GEN-LAST:event_relatoriosOrcamentosActionPerformed

    private void relatoriosConsPapeisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatoriosConsPapeisActionPerformed
        Controle.getDefaultGj().abrirJanelas(RelatoriosPapeis.getInstancia(loading),
                "RELATÓRIO DE PAPÉIS");
    }//GEN-LAST:event_relatoriosConsPapeisActionPerformed

    private void menClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menClientesActionPerformed
        Controle.getDefaultGj().abrirJanelas(ClienteCadastro.getInstancia(loading, Controle.getDefaultGj(), (byte) 2),
                "CADASTRO DE CLIENTES");
    }//GEN-LAST:event_menClientesActionPerformed

    private void menAcabamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAcabamentosActionPerformed
        Controle.getDefaultGj().abrirJanelas(AcabamentosCadastro.getInstancia((byte) 1, loading),
                "CADASTRO DE ACABAMENTOS");
    }//GEN-LAST:event_menAcabamentosActionPerformed

    private void menSvOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menSvOrcamentoActionPerformed
        Controle.getDefaultGj().abrirJanelas(ServicosFrame.getInstancia(loading, Controle.getDefaultGj()),
                "SERVIÇOS DO ORÇAMENTO");
    }//GEN-LAST:event_menSvOrcamentoActionPerformed

    private void menPapeisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPapeisActionPerformed
        PapelCadastro pc = new PapelCadastro();
        pc.setLocationRelativeTo(null);
        pc.setDefaultCloseOperation(pc.DISPOSE_ON_CLOSE);
        pc.setTitle("CADASTRO DE PAPÉIS");
        PapelCadastro.listaPrecoPapeis = false;
        PapelCadastro.listaPrecoPapeisEditar = false;
        pc.setVisible(true);
    }//GEN-LAST:event_menPapeisActionPerformed

    private void menChapasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menChapasActionPerformed
        ChapaCadastro cc = new ChapaCadastro();
        cc.setLocationRelativeTo(null);
        cc.setDefaultCloseOperation(cc.DISPOSE_ON_CLOSE);
        cc.setTitle("CADASTRO DE CHAPAS");
        cc.setVisible(true);
    }//GEN-LAST:event_menChapasActionPerformed

    private void menProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menProdutosActionPerformed
        Controle.getDefaultGj().abrirJanelas(ProdutoFrame.getInstancia(loading, Controle.getDefaultGj()),
                "CADASTRO DE PRODUTOS");
    }//GEN-LAST:event_menProdutosActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrcamentoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrcamentoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrcamentoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrcamentoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrcamentoFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane areaDeTrabalho;
    public static javax.swing.JLabel atualizacao;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel loading;
    private javax.swing.JMenuItem menAcabamentos;
    private javax.swing.JMenuItem menChapas;
    private javax.swing.JMenuItem menClientes;
    private javax.swing.JMenuItem menFormatoImpresao;
    private javax.swing.JMenuItem menMateriais;
    private javax.swing.JMenuItem menMedidaPapel;
    private javax.swing.JMenuItem menPapeis;
    private javax.swing.JMenuItem menProdutos;
    private javax.swing.JMenuItem menSvOrcamento;
    private javax.swing.JMenuItem menTintas;
    private javax.swing.JMenuItem menTipoPapel;
    private javax.swing.JMenuItem menuConsultaSimatex;
    private javax.swing.JMenu menuModulos;
    private javax.swing.JMenuItem menuOrcamento;
    private javax.swing.JMenuItem menuOrdensProducao;
    private javax.swing.JMenu menuPrincipal;
    private javax.swing.JMenu menuRelatorios;
    private javax.swing.JMenu menuSair;
    private javax.swing.JMenuItem relatoriosConsPapeis;
    private javax.swing.JMenuItem relatoriosOrcamentos;
    private javax.swing.JMenuItem relatoriosOrdemProducao;
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

    public static void cancelaPorPrazo() {
        new Thread("Cancela por prazo") {
            @Override
            public void run() {
                try {
                    List<Orcamento> listaPropostas = OrcamentoDAO.consultaPropostasVencidas();
                    for (Orcamento po : listaPropostas) {

                        OrcamentoDAO.alteraStatus(po.getCod(), (byte) 14);
                    }
                } catch (SQLException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                }
            }
        }.start();

    }

}

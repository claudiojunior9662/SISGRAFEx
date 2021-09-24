/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.orcamentos.operacoes;

import ui.ordemProducao.enviar.EnviarOrdemProducaoFrame;
import entities.sisgrafex.Cliente;
import entities.sisgrafex.Contato;
import entities.sisgrafex.Endereco;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import ui.cadastros.produtos.AcabamentoProdBEAN;
import ui.cadastros.papeis.PapelBEAN;
import entities.sisgrafex.ProdutoBEAN;
import entities.sisgrafex.Orcamento;
import entities.sisgrafex.CalculosOp;
import model.dao.ProdutoDAO;
import model.dao.OrcamentoDAO;
import entities.sisgrafex.Servicos;
import entities.sisgrafex.StsOrcamento;
import exception.OrcamentoInexistenteException;
import exception.SemAcabamentoException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import entities.sisgrafex.ProdOrcamento;
import exception.EnvioExcecao;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableCellRenderer;
import model.tabelas.OrcamentoExtTableModel;
import model.tabelas.OrcamentoIntTableModel;
import ui.cadastros.acabamentos.AcabamentoDAO;
import model.dao.ClienteDAO;
import model.dao.EnderecoDAO;
import ui.cadastros.clientes.ClienteCadastro;
import model.dao.PapelDAO;
import ui.cadastros.produtos.ProdutoFrame;
import ui.cadastros.produtos.ProdutoPrEntBEAN;
import ui.cadastros.servicos.ServicosFrame;
import model.dao.ServicoDAO;
import ui.controle.Controle;
import ui.login.TelaAutenticacao;
import ui.principal.Estoque;
import ui.principal.GerenteJanelas;
import ui.principal.OrcamentoFrame;

/**
 *
 * @author claud
 */
public class OrcamentoPrincipalFrame extends javax.swing.JInternalFrame {

    private class ModoImpressao extends JComboBox {

        ModoImpressao() {
            addItem("SELECIONE...");
            addItem("OFFSET");
            addItem("DIGITAL");
            setSelectedIndex(0);
        }
    }

    private class TipoImpressao extends JComboBox {

        TipoImpressao() {
            addItem("SELECIONE..");
            addItem("IMPRESSÃO CHAPADA");
            addItem("RETÍCULA 70%");
            addItem("RETÍCULA 40%");
            addItem("RETÍCULA 30%");
            addItem("TEXTO");
        }
    }

    private class PesoEspTinta extends JComboBox {

        PesoEspTinta() {
            addItem("SELECIONE..");
            addItem("CORES DE ESCALA");
            addItem("CORES TRANSPARENTES");
            addItem("CORES OPACAS");
            addItem("BANCO");
        }
    }

    public static int CODIGO_ORCAMENTO = 0;
    public static int CODIGO_PRODUTO = 0;
    public static boolean EDITANDO = false;
    private int STATUS = 0;
    private static JLabel loading;
    private static GerenteJanelas gj;
    /**
     * @param CLASSE_PAI 1- ORÇAMENTO GRÁFICA, 2 - OD GRÁFICA, 3 - OD CLIENTE
     */
    private static byte CLASSE_PAI;
    /**
     * @param TIPO_ORCAMENTO 1 - PRONTA ENTREGA (PE) 2 - PRODUÇÃO
     */
    private static byte TIPO_ORCAMENTO = 0;
    private static final OrcamentoExtTableModel modelExt = new OrcamentoExtTableModel();
    private static final OrcamentoIntTableModel modelInt = new OrcamentoIntTableModel();

    public static byte getTIPO_ORCAMENTO() {
        return TIPO_ORCAMENTO;
    }

    private static double VLR_ANT;
    public static int CODIGO_CONTATO = 0;
    public static int CODIGO_ENDERECO = 0;
    public static String p1Aux = null;
    public static String p2Aux = null;
    public static String p3Aux = null;

    private static int totalRegistros;

    public static int getTotalRegistros() {
        return totalRegistros;
    }

    public static void setTotalRegistros(int totalRegistros) {
        OrcamentoPrincipalFrame.totalRegistros = totalRegistros;
    }

    public static byte getCLASSE_PAI() {
        return CLASSE_PAI;
    }

    public static void setCLASSE_PAI(byte CLASSE_PAI) {
        OrcamentoPrincipalFrame.CLASSE_PAI = CLASSE_PAI;
    }

    MaskFormatter mascaraPesquisa = null;
    JFrame frame = new JFrame();
    JTable tabelaFrame = new JTable();
    DefaultTableModel modeloFrame = (DefaultTableModel) tabelaFrame.getModel();

    private static OrcamentoPrincipalFrame orcamentoPrincipalNovo2;

    public static OrcamentoPrincipalFrame getInstancia(JLabel loading, GerenteJanelas gj, byte CLASSE_PAI) {
        return new OrcamentoPrincipalFrame(loading, gj, CLASSE_PAI);
    }

    /**
     * Creates new form OrcamentoPrincipalNovo2
     *
     * @param loading
     * @param gj
     * @param CLASSE_PAI
     */
    public OrcamentoPrincipalFrame(JLabel loading, GerenteJanelas gj, byte CLASSE_PAI) {
        initComponents();
        OrcamentoPrincipalFrame.loading = loading;
        this.gj = gj;
        this.CLASSE_PAI = CLASSE_PAI;
        estadoInicial();

        switch (CLASSE_PAI) {
            case 1:
            case 2:
                tabelaConsulta.setModel(modelInt);
                break;
            case 3:
                tabelaConsulta.setModel(modelExt);
                break;
        }

        tabsInformacoes.setEnabledAt(2, false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grOd = new javax.swing.ButtonGroup();
        tabsOrcamento = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        dataValidade = new com.toedter.calendar.JCalendar();
        jPanel2 = new javax.swing.JPanel();
        nomeCliente = new javax.swing.JTextField();
        pesquisarCliente = new javax.swing.JButton();
        nomeContatoCliente = new javax.swing.JTextField();
        cidadeCliente = new javax.swing.JTextField();
        telefoneContatoCliente = new javax.swing.JTextField();
        ufCliente = new javax.swing.JTextField();
        tipoPessoa = new javax.swing.JTextField();
        codigoCliente = new javax.swing.JFormattedTextField();
        credito = new javax.swing.JFormattedTextField();
        docCliente = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        tabsInformacoes = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaProdutos = new javax.swing.JTable();
        observacaoProduto = new javax.swing.JButton();
        removerProduto = new javax.swing.JButton();
        adicionarProduto = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelaTiragens = new javax.swing.JTable();
        valoresManuais = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        JSPImpressao = new javax.swing.JScrollPane();

        tblImpressao = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tabelaPapeis = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelaAcabamentos = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        adicionarServico = new javax.swing.JButton();
        removeServico = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelaServicos = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        observacoesOrcamento = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cif = new javax.swing.JFormattedTextField();
        desconto = new javax.swing.JFormattedTextField();
        jLabel20 = new javax.swing.JLabel();
        frete = new javax.swing.JFormattedTextField();
        haFrete = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        vlrTotal = new javax.swing.JFormattedTextField();
        calcularOrcamento = new javax.swing.JButton();
        salvarOrcamento = new javax.swing.JButton();
        limpar = new javax.swing.JButton();
        downloadEstoque = new javax.swing.JButton();
        limpar1 = new javax.swing.JButton();
        jckbArte = new javax.swing.JCheckBox();
        jLabel21 = new javax.swing.JLabel();
        jftfArte = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        codigoOrcamento = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaConsulta = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        p1 = new javax.swing.JComboBox<>();
        p3Texto = new javax.swing.JTextField();
        botaoPesquisar = new javax.swing.JButton();
        mostrarTodos = new javax.swing.JButton();
        p2 = new javax.swing.JComboBox<>();
        p3Data = new com.toedter.calendar.JDateChooser();
        gerarPdf = new javax.swing.JButton();
        p3Formatado = new javax.swing.JFormattedTextField();
        tipoPdf = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        editar = new javax.swing.JButton();
        excluir = new javax.swing.JButton();
        naoAprovadoCliente = new javax.swing.JButton();
        enviarEmailAnexo = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        enviarProducao = new javax.swing.JButton();
        enviarExpedicao = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        autorizarProducao = new javax.swing.JButton();
        negarProducao = new javax.swing.JButton();
        rdOdGrafica = new javax.swing.JRadioButton();
        rdOdCliente = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        antPag = new javax.swing.JLabel();
        proxPag = new javax.swing.JLabel();
        pagAtual = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setTitle("ORÇAMENTO");
        setToolTipText("");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/orçamento.png"))); // NOI18N

        tabsOrcamento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "INFORMAÇÕES DO CLIENTE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        nomeCliente.setEditable(false);
        nomeCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("NOME DO CLIENTE"));

        pesquisarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisar.png"))); // NOI18N
        pesquisarCliente.setText("PESQUISAR");
        pesquisarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisarClienteActionPerformed(evt);
            }
        });

        nomeContatoCliente.setEditable(false);
        nomeContatoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("NOME P/ CONTATO"));

        cidadeCliente.setEditable(false);
        cidadeCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CIDADE"));

        telefoneContatoCliente.setEditable(false);
        telefoneContatoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("TELEFONE PRINCIPAL"));

        ufCliente.setEditable(false);
        ufCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("UF"));

        tipoPessoa.setEditable(false);
        tipoPessoa.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO DE PESSOA"));

        codigoCliente.setEditable(false);
        codigoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CÓDIGO DO CLIENTE"));
        codigoCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        credito.setEditable(false);
        credito.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CRÉDITO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        credito.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(java.text.NumberFormat.getCurrencyInstance())));
        credito.setToolTipText("ESTE CRÉDITO É ATUALIZADO DE ACORDO COM OS LANÇAMENTOS DE NOTA DE VENDA E NOTA DE CRÉDITO");
        credito.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        credito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creditoActionPerformed(evt);
            }
        });

        docCliente.setEditable(false);
        docCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("CPF/CNPJ"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(tipoPessoa, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                    .addComponent(nomeContatoCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(codigoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(docCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(telefoneContatoCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ufCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(credito, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nomeCliente)
                        .addComponent(docCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tipoPessoa)
                        .addComponent(codigoCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nomeContatoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(telefoneContatoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pesquisarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(credito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ufCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {credito, pesquisarCliente});

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "INFORMAÇÕES SOBRE O ORÇAMENTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 12))); // NOI18N

        tabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "DESCRIÇÃO", "LARGURA", "ALTURA", "QTD. PÁGINAS", "OBSERVAÇÕES"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaProdutos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaProdutosMouseClicked(evt);
            }
        });
        tabelaProdutos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tabelaProdutosPropertyChange(evt);
            }
        });
        tabelaProdutos.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tabelaProdutosVetoableChange(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaProdutos);

        observacaoProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/editar.png"))); // NOI18N
        observacaoProduto.setEnabled(false);
        observacaoProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                observacaoProdutoActionPerformed(evt);
            }
        });

        removerProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/remove.png"))); // NOI18N
        removerProduto.setEnabled(false);
        removerProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerProdutoActionPerformed(evt);
            }
        });

        adicionarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/incluir.png"))); // NOI18N
        adicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarProdutoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(adicionarProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removerProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(observacaoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1422, Short.MAX_VALUE))
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {adicionarProduto, observacaoProduto, removerProduto});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adicionarProduto)
                .addGap(7, 7, 7)
                .addComponent(removerProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(observacaoProduto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabsInformacoes.addTab("PRODUTOS", new javax.swing.ImageIcon(getClass().getResource("/icones/produto.png")), jPanel6); // NOI18N

        tabelaTiragens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUTO", "QUANTIDADE", "DIGITAL", "OFFSET", "VLR IMP. DIG.", "VALOR UNIT."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTiragens.setColumnSelectionAllowed(true);
        tabelaTiragens.setUpdateSelectionOnSort(false);
        tabelaTiragens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaTiragensMouseClicked(evt);
            }
        });
        tabelaTiragens.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tabelaTiragensPropertyChange(evt);
            }
        });
        tabelaTiragens.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaTiragensKeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(tabelaTiragens);
        tabelaTiragens.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        valoresManuais.setText("DIGITAR VALORES MANUALMENTE");
        valoresManuais.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                valoresManuaisItemStateChanged(evt);
            }
        });
        valoresManuais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valoresManuaisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(1268, Short.MAX_VALUE)
                .addComponent(valoresManuais)
                .addContainerGap())
            .addComponent(jScrollPane8)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(valoresManuais)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabsInformacoes.addTab("TIRAGENS", new javax.swing.ImageIcon(getClass().getResource("/icones/tiragens.png")), jPanel8); // NOI18N

        tblImpressao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUTO", "PAPEL", "MODO DE IMP.", "TIPO DE IMP.", "PESO ESP. TINTA", "FORMATO IMP.", "% DE PERCA", "VLR. IMP. DIGITAL"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblImpressao.setColumnSelectionAllowed(true);
        tblImpressao.setUpdateSelectionOnSort(false);
        tblImpressao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblImpressaoMouseClicked(evt);
            }
        });
        tblImpressao.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblImpressaoPropertyChange(evt);
            }
        });
        tblImpressao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblImpressaoKeyPressed(evt);
            }
        });
        JSPImpressao.setViewportView(tblImpressao);
        tblImpressao.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tblImpressao.getColumnModel().getColumnCount() > 0) {
            tblImpressao.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new ModoImpressao()));
            tblImpressao.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new TipoImpressao()));
            tblImpressao.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new PesoEspTinta()));
        }

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1483, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(JSPImpressao, javax.swing.GroupLayout.DEFAULT_SIZE, 1483, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(JSPImpressao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
        );

        tabsInformacoes.addTab("IMPRESSÃO", jPanel14);

        tabelaPapeis.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUTO", "CÓD PAPEL", "DESCRIÇÃO", "TIPO", "CF", "CV", "FORMATO IMP.", "PERCA (%)", "GASTO FOLHA", "PREÇO FOLHA", "QTD. CHAPAS", "PREÇO CHAPA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tabelaPapeis);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 1483, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        tabsInformacoes.addTab("PAPEL", new javax.swing.ImageIcon(getClass().getResource("/icones/papeis.png")), jPanel7); // NOI18N

        tabelaAcabamentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUTO", "CÓD ACABAMENTO", "DESCRIÇÃO", "PREÇO ACABAMENTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tabelaAcabamentos);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1483, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        tabsInformacoes.addTab("ACABAMENTOS", new javax.swing.ImageIcon(getClass().getResource("/icones/acabamentos.png")), jPanel9); // NOI18N

        adicionarServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/incluir.png"))); // NOI18N
        adicionarServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adicionarServicoActionPerformed(evt);
            }
        });

        removeServico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/remove.png"))); // NOI18N
        removeServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeServicoActionPerformed(evt);
            }
        });

        tabelaServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓD SERVIÇO", "DESCRIÇÃO", "VALOR SERVIÇO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tabelaServicos);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(adicionarServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(removeServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1439, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adicionarServico)
                .addGap(7, 7, 7)
                .addComponent(removeServico)
                .addContainerGap(129, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        tabsInformacoes.addTab("SERVIÇOS", new javax.swing.ImageIcon(getClass().getResource("/icones/servicos_orcamento.png")), jPanel10); // NOI18N

        observacoesOrcamento.setColumns(20);
        observacoesOrcamento.setLineWrap(true);
        observacoesOrcamento.setRows(5);
        observacoesOrcamento.setWrapStyleWord(true);
        jScrollPane5.setViewportView(observacoesOrcamento);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1483, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        tabsInformacoes.addTab("OBSERVAÇÕES", new javax.swing.ImageIcon(getClass().getResource("/icones/editar.png")), jPanel11); // NOI18N

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setText("CIF (%):");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel19.setText("DESCONTO (%):");

        cif.setEditable(false);
        cif.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        cif.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cifPropertyChange(evt);
            }
        });

        desconto.setEditable(false);
        desconto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        desconto.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                descontoPropertyChange(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("FRETE (R$):");

        frete.setEditable(false);
        frete.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        frete.setEnabled(false);
        frete.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fretePropertyChange(evt);
            }
        });

        haFrete.setEnabled(false);
        haFrete.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                haFreteItemStateChanged(evt);
            }
        });
        haFrete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                haFreteMouseClicked(evt);
            }
        });
        haFrete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                haFreteActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("VALOR TOTAL (R$):");

        vlrTotal.setEditable(false);
        vlrTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        vlrTotal.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        vlrTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlrTotalActionPerformed(evt);
            }
        });

        calcularOrcamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/calcular.png"))); // NOI18N
        calcularOrcamento.setText("CALCULAR");
        calcularOrcamento.setEnabled(false);
        calcularOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularOrcamentoActionPerformed(evt);
            }
        });

        salvarOrcamento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/gravar.png"))); // NOI18N
        salvarOrcamento.setText("SALVAR ORÇAMENTO");
        salvarOrcamento.setEnabled(false);
        salvarOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarOrcamentoActionPerformed(evt);
            }
        });

        limpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/cancelar.png"))); // NOI18N
        limpar.setText("LIMPAR");
        limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparActionPerformed(evt);
            }
        });

        downloadEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/download.png"))); // NOI18N
        downloadEstoque.setText("DOWNLOAD ESTOQUE");
        downloadEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadEstoqueActionPerformed(evt);
            }
        });

        limpar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/formato_de_impressao.png"))); // NOI18N
        limpar1.setText("TABELA DE CORTE DE PAPEL");
        limpar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpar1ActionPerformed(evt);
            }
        });

        jckbArte.setEnabled(false);
        jckbArte.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jckbArteItemStateChanged(evt);
            }
        });
        jckbArte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jckbArteMouseClicked(evt);
            }
        });
        jckbArte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jckbArteActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("ARTE (R$):");

        jftfArte.setEditable(false);
        jftfArte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jftfArte.setEnabled(false);
        jftfArte.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jftfArtePropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabsInformacoes)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jckbArte)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jftfArte, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(haFrete)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(frete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(desconto, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cif, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(vlrTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(limpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downloadEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(limpar1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calcularOrcamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(salvarOrcamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(4, 4, 4))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabsInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 231, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jckbArte)
                    .addComponent(jLabel21)
                    .addComponent(jftfArte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(haFrete)
                    .addComponent(frete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(desconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(cif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(vlrTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(salvarOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calcularOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(downloadEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(limpar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cif, desconto, frete, haFrete, jLabel13, jLabel18, jLabel19, jLabel20, jLabel21, jckbArte, jftfArte, vlrTotal});

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setText("DATA DE VALIDADE:");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel30.setText("CÓDIGO DO ORÇAMENTO:");

        codigoOrcamento.setEditable(false);
        codigoOrcamento.setDisabledTextColor(new java.awt.Color(255, 0, 0));
        codigoOrcamento.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        codigoOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoOrcamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel30)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(codigoOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dataValidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel30)
                        .addComponent(codigoOrcamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        tabsOrcamento.addTab("NOVO/EDITAR", new javax.swing.ImageIcon(getClass().getResource("/icones/novo_editar.png")), jPanel1); // NOI18N

        tabelaConsulta.setAutoCreateRowSorter(true);
        tabelaConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelaConsulta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaConsultaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelaConsultaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tabelaConsultaMouseExited(evt);
            }
        });
        tabelaConsulta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaConsultaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaConsulta);

        jLabel16.setText("PESQUISAR POR:");

        p1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE...", "CÓDIGO", "CLIENTE", "DATA EMISSÃO", "DATA VALIDADE", "STATUS" }));
        p1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                p1ItemStateChanged(evt);
            }
        });
        p1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p1ActionPerformed(evt);
            }
        });

        botaoPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisar.png"))); // NOI18N
        botaoPesquisar.setText("PESQUISAR");
        botaoPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPesquisarActionPerformed(evt);
            }
        });

        mostrarTodos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/ultimos.png"))); // NOI18N
        mostrarTodos.setText("MOSTRAR TODOS (MÁX 45)");
        mostrarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarTodosActionPerformed(evt);
            }
        });

        p2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE..." }));
        p2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                p2ItemStateChanged(evt);
            }
        });

        gerarPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pdf.png"))); // NOI18N
        gerarPdf.setText("GERAR PDF");
        gerarPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerarPdfActionPerformed(evt);
            }
        });

        tipoPdf.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SIMPLES", "DETALHADO" }));

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "ORÇAMENTAÇÃO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        jPanel12.setPreferredSize(new java.awt.Dimension(390, 30));

        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/editar.png"))); // NOI18N
        editar.setText("EDITAR");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });

        excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/remove.png"))); // NOI18N
        excluir.setText("EXCLUIR");
        excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirActionPerformed(evt);
            }
        });

        naoAprovadoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/cancelar.png"))); // NOI18N
        naoAprovadoCliente.setText("NÃO APROVADO PELO CLIENTE");
        naoAprovadoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                naoAprovadoClienteActionPerformed(evt);
            }
        });

        enviarEmailAnexo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/email.png"))); // NOI18N
        enviarEmailAnexo.setText("ENVIAR EMAIL (ANEXO)");
        enviarEmailAnexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarEmailAnexoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(naoAprovadoCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enviarEmailAnexo, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {editar, excluir, naoAprovadoCliente});

        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(naoAprovadoCliente)
                    .addComponent(enviarEmailAnexo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(excluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editar))
        );

        jPanel12Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {editar, enviarEmailAnexo, excluir, naoAprovadoCliente});

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "PRODUÇÃO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        enviarProducao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/enviar_trabalho.png"))); // NOI18N
        enviarProducao.setText("ENVIAR PARA PRODUÇÃO");
        enviarProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarProducaoActionPerformed(evt);
            }
        });

        enviarExpedicao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/enviar_trabalho.png"))); // NOI18N
        enviarExpedicao.setText("ENVIAR PARA EXPEDIÇÃO");
        enviarExpedicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarExpedicaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(enviarProducao)
            .addComponent(enviarExpedicao)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(enviarProducao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(enviarExpedicao)
                .addGap(0, 52, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "ORDENADOR DE DESPESAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        autorizarProducao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/confirma.png"))); // NOI18N
        autorizarProducao.setText("(OD) AUTORIZAR PRODUÇÃO");
        autorizarProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autorizarProducaoActionPerformed(evt);
            }
        });

        negarProducao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/cancelar.png"))); // NOI18N
        negarProducao.setText("(OD) NEGAR PRODUÇÃO");
        negarProducao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negarProducaoActionPerformed(evt);
            }
        });

        grOd.add(rdOdGrafica);
        rdOdGrafica.setSelected(true);
        rdOdGrafica.setText("OD - GRÁFICA");

        grOd.add(rdOdCliente);
        rdOdCliente.setText("OD - CLIENTE");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(autorizarProducao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(negarProducao, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(rdOdGrafica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdOdCliente))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {autorizarProducao, negarProducao});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rdOdCliente, rdOdGrafica});

        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdOdGrafica)
                    .addComponent(rdOdCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(autorizarProducao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(negarProducao))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {autorizarProducao, negarProducao});

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {rdOdCliente, rdOdGrafica});

        jLabel1.setText("PÁGINA");

        antPag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/anterior.png"))); // NOI18N
        antPag.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        antPag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                antPagMouseClicked(evt);
            }
        });

        proxPag.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/proximo.png"))); // NOI18N
        proxPag.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        proxPag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proxPagMouseClicked(evt);
            }
        });

        pagAtual.setEditable(false);
        pagAtual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pagAtual.setEnabled(false);
        pagAtual.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                pagAtualPropertyChange(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 215, 0));
        jLabel2.setForeground(new java.awt.Color(255, 215, 0));
        jLabel2.setOpaque(true);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("PROPOSTAS RECEBIDAS PELO MÓDULO INTEGRADOR");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(p1, 0, 395, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(p3Data, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(p2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(p3Texto)
                            .addComponent(p3Formatado, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(botaoPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mostrarTodos))
                        .addGap(87, 87, 87))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3))
                            .addComponent(jLabel16)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tipoPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(gerarPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(antPag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pagAtual, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(proxPag)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {botaoPesquisar, mostrarTodos});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel16)
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p3Data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(p3Formatado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(botaoPesquisar))
                    .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(p3Texto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mostrarTodos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoPdf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(antPag)
                            .addComponent(proxPag)
                            .addComponent(pagAtual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(gerarPdf))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {antPag, proxPag});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel12, jPanel13, jPanel15});

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botaoPesquisar, p1, p2, p3Data, p3Formatado, p3Texto});

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabsOrcamento.addTab("CONSULTAS", new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisar.png")), jPanel4); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsOrcamento)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabsOrcamento)
        );

        tabsOrcamento.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pesquisarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisarClienteActionPerformed
        gj.abrirJanelas(ClienteCadastro.getInstancia(loading, gj, (byte) 1), "PESQUISA DE CLIENTES");
        ClienteCadastro.tipoSelecionar = "ORCAMENTO";
        ClienteCadastro.orcamentoSelecionar = true;
        ClienteCadastro.orcamentoEditarSelecionar = false;
    }//GEN-LAST:event_pesquisarClienteActionPerformed

    private void tabelaProdutosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaProdutosMouseClicked
        switch (tabelaProdutos.getRowCount()) {
            case 0:
                observacaoProduto.setEnabled(false);
                removerProduto.setEnabled(false);
                break;
            default:
                observacaoProduto.setEnabled(true);
                removerProduto.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_tabelaProdutosMouseClicked

    private void tabelaProdutosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tabelaProdutosPropertyChange

    }//GEN-LAST:event_tabelaProdutosPropertyChange

    private void tabelaProdutosVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tabelaProdutosVetoableChange

    }//GEN-LAST:event_tabelaProdutosVetoableChange

    private void observacaoProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_observacaoProdutoActionPerformed
        gj.abrirJanelas(AdicionarObservacaoProdutoFrame.getInstancia(loading, gj),
                "OBSERVAÇÃO PRODUTO " + tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0));
        AdicionarObservacaoProdutoFrame.observacaoProdutoText.setText((String) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 5));
        AdicionarObservacaoProdutoFrame.linha = tabelaProdutos.getSelectedRow();
    }//GEN-LAST:event_observacaoProdutoActionPerformed

    private void removerProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removerProdutoActionPerformed
        int indiceLinha = -1;

        //LISTA PARA GUARDAR OS ÍNDICES DE EXCLUSÃO DA TABELA-------------------
        List excluirPapeis = new ArrayList();
        List excluirAcabamentos = new ArrayList();
        List excluirServicos = new ArrayList();
        List excluirQuantidades = new ArrayList();
        excluirPapeis.clear();
        excluirAcabamentos.clear();
        excluirServicos.clear();
        //----------------------------------------------------------------------

        //PEGA O CÓDIGO DO PRODUTO----------------------------------------------
        DefaultTableModel modeloProdutos = (DefaultTableModel) tabelaProdutos.getModel();
        CODIGO_PRODUTO
                = Integer.valueOf(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0).toString());
        modeloProdutos.removeRow(tabelaProdutos.getSelectedRow());
        //----------------------------------------------------------------------

        //REMOVE OS PAPÉIS DA TABELA PAPÉIS-------------------------------------
        if (TIPO_ORCAMENTO == 2) {
            DefaultTableModel modeloPapeis = (DefaultTableModel) tabelaPapeis.getModel();
            for (int i = 0; i < tabelaPapeis.getRowCount(); i++) {
                if (Integer.valueOf(tabelaPapeis.getValueAt(i, 0).toString()) == CODIGO_PRODUTO) {
                    excluirPapeis.add(i);
                }
            }
            Collections.reverse(excluirPapeis);
            for (int i = 0; i < excluirPapeis.size(); i++) {
                modeloPapeis.removeRow((int) excluirPapeis.get(i));
            }
        }
        //----------------------------------------------------------------------

        //REMOVE OS ACABAMENTOS DA TABELA ACABAMENTOS---------------------------
        if (TIPO_ORCAMENTO == 2) {
            for (int i = 0; i < tabelaAcabamentos.getRowCount(); i++) {
                if (Integer.valueOf(tabelaAcabamentos.getValueAt(i, 0).toString()) == CODIGO_PRODUTO) {
                    excluirAcabamentos.add(i);
                }
            }
            DefaultTableModel modeloAcabamentos = (DefaultTableModel) tabelaAcabamentos.getModel();
            Collections.reverse(excluirAcabamentos);
            for (int i = 0; i < excluirAcabamentos.size(); i++) {
                modeloAcabamentos.removeRow((int) excluirAcabamentos.get(i));
            }
        }

        //----------------------------------------------------------------------
        //REMOVE AS QUANTIDADES DA TABELA QUANTIDADES---------------------------
        DefaultTableModel modeloQuantidades = (DefaultTableModel) tabelaTiragens.getModel();
        for (int i = 0; i < tabelaTiragens.getRowCount(); i++) {
            if (Integer.valueOf(tabelaTiragens.getValueAt(i, 0).toString()) == CODIGO_PRODUTO) {
                excluirQuantidades.add(i);
            }
        }
        Collections.reverse(excluirQuantidades);
        for (int i = 0; i < excluirQuantidades.size(); i++) {
            modeloQuantidades.removeRow((int) excluirQuantidades.get(i));
        }
        //----------------------------------------------------------------------

        //RESETA OS CAMPOS DE VALORES-------------------------------------------
        if (tabelaTiragens.getRowCount() == 0) {
            desconto.setValue(0);
            cif.setValue(0);
            vlrTotal.setValue(0);
            TIPO_ORCAMENTO = 0;
        }
        //----------------------------------------------------------------------
        //RESETA OS BOTÕES------------------------------------------------------
        salvarOrcamento.setEnabled(false);
        observacaoProduto.setEnabled(false);
        //----------------------------------------------------------------------

        //GR ESTADOS------------------------------------------------------------
        if (modeloQuantidades.getRowCount() > 0) {
            estadoProdSv((byte) 1);
        } else {
            estadoProdSv((byte) 2);
        }
        //----------------------------------------------------------------------
    }//GEN-LAST:event_removerProdutoActionPerformed

    private void adicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarProdutoActionPerformed
        DefaultTableModel modeloTiragem = (DefaultTableModel) tabelaTiragens.getModel();
//        if (modeloTiragem.getRowCount() > 0 & TIPO_ORCAMENTO == 1) {
//            JOptionPane.showMessageDialog(null,
//                    "ESSA FUNÇÃO ESTÁ LIMITADA A APENAS 1 PRODUTO POR PROPOSTA.",
//                    "ATENÇÃO",
//                    JOptionPane.INFORMATION_MESSAGE);
//        } else {
        gj.abrirJanelas(ProdutoFrame.getInstancia(loading, gj), "CADASTRO DE PRODUTOS");
        ProdutoFrame.orcamentoNovo = true;
        ProdutoFrame.setSEL_ORC(true);
//        }
    }//GEN-LAST:event_adicionarProdutoActionPerformed

    private void tabelaTiragensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaTiragensMouseClicked
        if ((Boolean) tabelaTiragens.getValueAt(tabelaTiragens.getSelectedRow(), 3)
                & (Boolean) tabelaTiragens.getValueAt(tabelaTiragens.getSelectedRow(), 2) == false) {
            tabelaTiragens.setValueAt(0d, tabelaTiragens.getSelectedRow(), 4);
        }

        if (TIPO_ORCAMENTO == 1) {
            VLR_ANT = Double.valueOf(tabelaTiragens.getValueAt(
                    tabelaTiragens.getSelectedRow(), 5).toString());
            tabelaTiragens.setValueAt(false, tabelaTiragens.getSelectedRow(), 2);
            tabelaTiragens.setValueAt(false, tabelaTiragens.getSelectedRow(), 3);
            tabelaTiragens.setValueAt(VLR_ANT, tabelaTiragens.getSelectedRow(), 5);
        }
    }//GEN-LAST:event_tabelaTiragensMouseClicked

    private void tabelaTiragensPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tabelaTiragensPropertyChange
        salvarOrcamento.setEnabled(false);
    }//GEN-LAST:event_tabelaTiragensPropertyChange

    private void tabelaTiragensKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaTiragensKeyPressed
        salvarOrcamento.setEnabled(false);

        if (TIPO_ORCAMENTO == 1) {
            VLR_ANT = Double.valueOf(tabelaTiragens.getValueAt(
                    tabelaTiragens.getSelectedRow(), 5).toString());
            tabelaTiragens.setValueAt(false, tabelaTiragens.getSelectedRow(), 2);
            tabelaTiragens.setValueAt(false, tabelaTiragens.getSelectedRow(), 3);
            tabelaTiragens.setValueAt(VLR_ANT, tabelaTiragens.getSelectedRow(), 5);
        }
    }//GEN-LAST:event_tabelaTiragensKeyPressed

    private void valoresManuaisItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_valoresManuaisItemStateChanged

    }//GEN-LAST:event_valoresManuaisItemStateChanged

    private void valoresManuaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valoresManuaisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valoresManuaisActionPerformed

    private void adicionarServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adicionarServicoActionPerformed
        gj.abrirJanelas(ServicosFrame.getInstancia(loading, gj), "CADASTRO DE SERVIÇOS");
        ServicosFrame.tabsServico.setSelectedIndex(1);
        ServicosFrame.orcamentoPrincipal = true;
        ServicosFrame.orcamentoEditar = false;
        salvarOrcamento.setEnabled(false);
    }//GEN-LAST:event_adicionarServicoActionPerformed

    private void removeServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeServicoActionPerformed
        DefaultTableModel modeloServicos = (DefaultTableModel) tabelaServicos.getModel();
        modeloServicos.removeRow(tabelaServicos.getSelectedRow());
        if (modeloServicos.getRowCount() > 0) {
            estadoProdSv((byte) 1);
        } else {
            estadoProdSv((byte) 2);
        }
        tabelaServicos.validate();
        salvarOrcamento.setEnabled(false);
    }//GEN-LAST:event_removeServicoActionPerformed

    private void cifPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cifPropertyChange
        salvarOrcamento.setEnabled(false);
    }//GEN-LAST:event_cifPropertyChange

    private void descontoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_descontoPropertyChange
        salvarOrcamento.setEnabled(false);
    }//GEN-LAST:event_descontoPropertyChange

    private void fretePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fretePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_fretePropertyChange

    private void haFreteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_haFreteMouseClicked

    }//GEN-LAST:event_haFreteMouseClicked

    private void haFreteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_haFreteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_haFreteActionPerformed

    private void vlrTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlrTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vlrTotalActionPerformed

    private void tabelaConsultaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaConsultaMouseClicked
        new Thread() {
            @Override
            public void run() {
                try {

                    //JANELA PRODUTOS-------------------------------------------
                    int row = 0;
                    int code = 0;

                    row = tabelaConsulta.rowAtPoint(evt.getPoint());
                    code = Integer.valueOf(tabelaConsulta.getValueAt(row, 0).toString());

                    gj.abrirJanelas(VisualizadorProdutosFrame.getInstancia("ORÇAMENTO " + code), "ORÇAMENTO " + code);
                    DefaultTableModel modeloProdutos = (DefaultTableModel) VisualizadorProdutosFrame.tabelaProdutosAux.getModel();
                    modeloProdutos.setNumRows(0);

                    for (ProdOrcamento bean : OrcamentoDAO.retornaProdutos(code)) {
                        modeloProdutos.addRow(new Object[]{
                            ProdutoDAO.traduzCodProd(bean.getCodProduto(), bean.getTipoProduto()),
                            bean.getDescricaoProduto(),
                            bean.getQuantidade(),
                            bean.getPrecoUnitario()
                        });

                        switch (bean.getTipoProduto()) {
                            case 1:
                                TIPO_ORCAMENTO = 2;
                                break;
                            case 2:
                                TIPO_ORCAMENTO = 1;
                                break;
                            case 3:
                                TIPO_ORCAMENTO = 3;
                                break;
                            default:
                                return;
                        }
                    }
                    //----------------------------------------------------------

                    String statusSplit[];
                    switch (CLASSE_PAI) {
                        case 1:
                        case 2:
                            statusSplit = tabelaConsulta.getValueAt(
                                    tabelaConsulta.getSelectedRow(), 6).toString().split(" ");
                            STATUS = Integer.valueOf(statusSplit[0]);
                            break;
                        case 3:
                            statusSplit = tabelaConsulta.getValueAt(
                                    tabelaConsulta.getSelectedRow(), 4).toString().split(" ");
                            STATUS = Integer.valueOf(statusSplit[0]);
                            break;
                    }

                    loading.setVisible(true);
                    loading.setText("CARREGANDO...");

                    tipoPdf.setEnabled(true);
                    gerarPdf.setEnabled(true);

                    switch (CLASSE_PAI) {
                        case 2:
                            switch (STATUS) {
                                case 1:
                                    if (TIPO_ORCAMENTO == 1) {
                                        enviarProducao.setEnabled(false);
                                        enviarExpedicao.setEnabled(true);
                                    } else {
                                        enviarProducao.setEnabled(true);
                                        enviarExpedicao.setEnabled(false);
                                    }
                                    if (TelaAutenticacao.getUsrLogado().getAcessoOrcAdm() == 1) {
                                        excluir.setEnabled(true);
                                    } else {
                                        excluir.setEnabled(false);
                                    }
                                    editar.setEnabled(true);
                                    naoAprovadoCliente.setEnabled(true);
                                    break;
                                case 2:
                                case 3:
                                case 5:
                                case 6:
                                case 8:
                                case 9:
                                case 15:
                                    enviarProducao.setEnabled(false);
                                    enviarExpedicao.setEnabled(false);
                                    excluir.setEnabled(false);
                                    editar.setEnabled(false);
                                    naoAprovadoCliente.setEnabled(false);
                                    break;
                                case 4:
                                    if (TIPO_ORCAMENTO == 1) {
                                        enviarProducao.setEnabled(false);
                                        enviarExpedicao.setEnabled(true);
                                    } else {
                                        enviarProducao.setEnabled(true);
                                        enviarExpedicao.setEnabled(false);
                                    }
                                    if (TelaAutenticacao.getUsrLogado().getAcessoOrcAdm() == 1) {
                                        excluir.setEnabled(true);
                                    }
                                    editar.setEnabled(true);
                                    naoAprovadoCliente.setEnabled(true);
                                    break;
                                case 14:
                                    if (TIPO_ORCAMENTO == 1) {
                                        enviarProducao.setEnabled(false);
                                        enviarExpedicao.setEnabled(false);
                                        excluir.setEnabled(true);
                                        editar.setEnabled(true);
                                        naoAprovadoCliente.setEnabled(true);
                                    } else {
                                        enviarProducao.setEnabled(false);
                                        enviarExpedicao.setEnabled(false);
                                        excluir.setEnabled(true);
                                        editar.setEnabled(true);
                                        naoAprovadoCliente.setEnabled(true);
                                    }
                                    break;
                            }
                            autorizarProducao.setEnabled(true);
                            negarProducao.setEnabled(true);
                            break;
                        case 3:
                            switch (STATUS) {
                                case 3:
                                    autorizarProducao.setEnabled(true);
                                    negarProducao.setEnabled(true);
                                    break;
                                default:
                                    autorizarProducao.setEnabled(false);
                                    negarProducao.setEnabled(false);
                                    break;
                            }
                            break;
                        default:
                            switch (STATUS) {
                                case 1:
                                    if (TIPO_ORCAMENTO == 1) {
                                        enviarProducao.setEnabled(false);
                                        enviarExpedicao.setEnabled(true);
                                    } else {
                                        enviarProducao.setEnabled(true);
                                        enviarExpedicao.setEnabled(false);
                                    }
                                    if (TelaAutenticacao.getUsrLogado().getAcessoOrcAdm() == 1) {
                                        excluir.setEnabled(true);
                                    }
                                    editar.setEnabled(true);
                                    naoAprovadoCliente.setEnabled(true);
                                    //enviarEmailAnexo.setEnabled(true);
                                    break;
                                case 2:
                                case 3:
                                case 5:
                                case 6:
                                case 8:
                                case 9:
                                    enviarProducao.setEnabled(false);
                                    excluir.setEnabled(false);
                                    editar.setEnabled(false);
                                    naoAprovadoCliente.setEnabled(false);
                                    break;
                                case 4:
                                    if (TIPO_ORCAMENTO == 1) {
                                        enviarProducao.setEnabled(false);
                                        enviarExpedicao.setEnabled(true);
                                    } else {
                                        enviarProducao.setEnabled(true);
                                        enviarExpedicao.setEnabled(false);
                                    }
                                    excluir.setEnabled(true);
                                    editar.setEnabled(true);
                                    naoAprovadoCliente.setEnabled(true);
                                    break;
                            }
                            break;
                    }

                    loading.setVisible(false);
                } catch (SQLException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                }
                loading.setVisible(false);
            }
        }.start();
    }//GEN-LAST:event_tabelaConsultaMouseClicked

    private void tabelaConsultaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaConsultaMouseExited

    }//GEN-LAST:event_tabelaConsultaMouseExited

    private void tabelaConsultaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaConsultaMouseEntered

    }//GEN-LAST:event_tabelaConsultaMouseEntered

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        new Thread() {
            @Override
            public void run() {
                /**
                 * Limpa a tela de orçamento
                 */
                limpa();

                /**
                 * Mostra a mensagem de carregamento
                 */
                loading.setVisible(true);
                loading.setText("CARREGANDO EDIÇÃO...");

                /**
                 * Estado de edição
                 */
                EDITANDO = true;

                try {
                    /**
                     * Recebe o código do orçamento
                     */
                    CODIGO_ORCAMENTO = Integer.valueOf(tabelaConsulta.getValueAt(
                            tabelaConsulta.getSelectedRow(), 0).toString());

                    /**
                     * Carrega as informações do orçamento
                     */
                    Orcamento orcamento = OrcamentoDAO.carregaEdicaoOrcamento(CODIGO_ORCAMENTO);

                    /**
                     * Carrega as informações do cliente
                     */
                    Cliente cliente = ClienteDAO.selInfoNota(
                            (byte) orcamento.getTipoPessoa(), orcamento.getCodCliente());

                    /**
                     * Carrega as informações do contato do cliente
                     */
                    Contato contato = ClienteDAO.selInfoContato(orcamento.getCodContato());
                    CODIGO_CONTATO = orcamento.getCodContato();

                    /**
                     * Carrega as informações do endereço do cliente
                     */
                    Endereco endereco = EnderecoDAO.selInfoEndereco(orcamento.getCodEndereco());
                    CODIGO_ENDERECO = orcamento.getCodEndereco();

                    /**
                     * Preenche as informações do orçamento
                     */
                    codigoOrcamento.setValue(CODIGO_ORCAMENTO);
                    dataValidade.setDate(Controle.dataPadrao.parse(tabelaConsulta.getValueAt(
                            tabelaConsulta.getSelectedRow(), 4).toString()));
                    STATUS = orcamento.getStatus();
                    switch (orcamento.getTipoPessoa()) {
                        case 1:
                            tipoPessoa.setText("PESSOA FÍSICA");
                            docCliente.setText(cliente.getCpf());
                            break;
                        case 2:
                            tipoPessoa.setText("PESSOA JURÍDICA");
                            docCliente.setText(cliente.getCnpj());
                            break;
                    }
                    codigoCliente.setValue(orcamento.getCodCliente());
                    nomeCliente.setText(cliente.getNome());
                    nomeContatoCliente.setText(contato.getNomeContato());
                    telefoneContatoCliente.setText(contato.getTelefone());
                    cidadeCliente.setText(endereco.getCidade());
                    ufCliente.setText(endereco.getUf());
                    desconto.setValue((int) orcamento.getDesconto());
                    cif.setValue((int) orcamento.getCif());
                    observacoesOrcamento.setText(orcamento.getDescricao());
                    valoresManuais.setSelected((orcamento.getPrecosManuais() == 1));
                    if (orcamento.getFrete() != 0d) {
                        haFrete.setSelected(true);
                        frete.setEnabled(true);
                        frete.setValue(orcamento.getFrete());
                    } else {
                        haFrete.setSelected(false);
                        frete.setEnabled(false);
                        frete.setValue(0d);
                    }

                    if (orcamento.getArte() != 0d) {
                        jckbArte.setSelected(true);
                        jftfArte.setEnabled(true);
                        jftfArte.setValue(orcamento.getArte());
                    } else {
                        jckbArte.setSelected(false);
                        jftfArte.setEnabled(false);
                        jftfArte.setValue(0d);
                    }

                    vlrTotal.setValue(orcamento.getValorTotal());

                    /**
                     * Carrega os produtos do orçamento
                     */
                    for (ProdOrcamento prodOrc : ProdutoDAO.carregaProdutosOrcamento(
                            (int) codigoOrcamento.getValue())) {
                        /**
                         * TIPO_ORCAMENTO 1 - PRONTA ENTREGA 2 - PRODUÇÃO
                         */
                        TIPO_ORCAMENTO = prodOrc.getTipoProduto() == 1 ? (byte) 2 : (byte) 1;
                        CODIGO_PRODUTO = prodOrc.getCodProduto();

                        switch (prodOrc.getTipoProduto()) {
                            case 1:
                                ProdutoBEAN produto = ProdutoDAO.retornaInfoProd(CODIGO_PRODUTO, (byte) 1);
                                DefaultTableModel modeloProdutos = (DefaultTableModel) tabelaProdutos.getModel();
                                modeloProdutos.addRow(new Object[]{
                                    CODIGO_PRODUTO,
                                    produto.getDescricao(),
                                    produto.getLargura(),
                                    produto.getAltura(),
                                    produto.getQuantidadeFolhas(),
                                    prodOrc.getObservacaoProduto()});

                                for (PapelBEAN papel : PapelDAO.carregaPapeisProd(CODIGO_PRODUTO)) {
                                    CalculosOp calculosBEAN = OrcamentoDAO.retornaCalculosProposta((int) codigoOrcamento.getValue(), CODIGO_PRODUTO,
                                            prodOrc.getTipoProduto(),
                                            papel.getCodigo(),
                                            papel.getTipoPapel());
                                    DefaultTableModel modeloPapeis = (DefaultTableModel) tabelaPapeis.getModel();
                                    modeloPapeis.addRow(new Object[]{
                                        CODIGO_PRODUTO,
                                        papel.getCodigo(),
                                        ProdutoDAO.retornaDescricaoPapel(papel.getCodigo()),
                                        papel.getTipoPapel(),
                                        papel.getCorFrente(),
                                        papel.getCorVerso(),
                                        calculosBEAN.getFormato(),
                                        calculosBEAN.getPerca(),
                                        calculosBEAN.getQtdFolhasTotal(),
                                        ProdutoDAO.retornaPrecoPapel(papel.getCodigo()),
                                        calculosBEAN.getQtdChapas(),
                                        ProdutoDAO.retornaPrecoChapa(317)});
                                }
                                try {
                                    for (AcabamentoProdBEAN cadastroProdutosComponentesBEAN
                                            : AcabamentoDAO.retornaAcabamentosProduto(CODIGO_PRODUTO)) {
                                        DefaultTableModel modeloAcabamentos = (DefaultTableModel) tabelaAcabamentos.getModel();
                                        modeloAcabamentos.addRow(new Object[]{
                                            CODIGO_PRODUTO,
                                            cadastroProdutosComponentesBEAN.getCodigoAcabamento(),
                                            AcabamentoDAO.retornaDescricaoAcabamentos(cadastroProdutosComponentesBEAN.getCodigoAcabamento())});
                                    }
                                } catch (SemAcabamentoException ex) {
                                    //NENHUMA AÇÃO
                                }
                                break;
                            case 2:
                                ProdutoPrEntBEAN produtoPrEnt = ProdutoDAO.retornaInfoPe(CODIGO_PRODUTO);
                                DefaultTableModel modeloProdutosPrEnt = (DefaultTableModel) tabelaProdutos.getModel();
                                modeloProdutosPrEnt.addRow(new Object[]{
                                    CODIGO_PRODUTO,
                                    produtoPrEnt.getDescricao(),
                                    produtoPrEnt.getLargura(),
                                    produtoPrEnt.getAltura(),
                                    produtoPrEnt.getQtdPaginas(),
                                    prodOrc.getObservacaoProduto()});
                                break;
                            case 3:
                                return;
                        }

                        DefaultTableModel modeloQuantidades = (DefaultTableModel) tabelaTiragens.getModel();
                        modeloQuantidades.addRow(new Object[]{
                            CODIGO_PRODUTO,
                            prodOrc.getQuantidade(),
                            TIPO_ORCAMENTO == 2 ? (prodOrc.getMaquina() == 1) : false,
                            TIPO_ORCAMENTO == 2 ? (prodOrc.getMaquina() == 2) : false,
                            prodOrc.getValorDigital(),
                            prodOrc.getPrecoUnitario()});
                    }

                    /**
                     * Carrega os serviços do orçamento
                     */
                    for (Servicos servicos : ServicoDAO.carregaServicosOrcamento((int) codigoOrcamento.getValue())) {
                        DefaultTableModel modeloServicos = (DefaultTableModel) tabelaServicos.getModel();
                        modeloServicos.addRow(new Object[]{
                            servicos.getCod(),
                            ServicoDAO.carregaDescricaoServicos(servicos.getCod()),
                            ServicoDAO.retornaVlrSrvOrcNExistente(servicos.getCod())
                        });
                    }

                    /**
                     * Reseta o estado da tela de consultas
                     */
                    excluir.setEnabled(false);
                    editar.setEnabled(false);
                    gerarPdf.setEnabled(false);
                    enviarProducao.setEnabled(false);

                    /**
                     * Vai para a aba inicial do orçamento
                     */
                    tabsOrcamento.setSelectedIndex(0);
                    tabsInformacoes.setSelectedIndex(0);

                    /**
                     * Verifica se é orçamento para produção ou orçamento para
                     * pronta entrega
                     */
                    tabsInformacoes.setEnabledAt(2, (TIPO_ORCAMENTO != 1));
                    tabsInformacoes.setEnabledAt(3, (TIPO_ORCAMENTO != 1));

                    /**
                     * Verifica se existe produtos
                     */
                    estadoProdSv(tabelaProdutos.getRowCount() == 0 ? (byte) 2 : (byte) 1);
                } catch (SQLException | ParseException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                }
                loading.setVisible(false);
            }
        }.start();
    }//GEN-LAST:event_editarActionPerformed

    private void excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirActionPerformed
        new Thread("Excluir orçamento") {
            @Override
            public void run() {
                exclui();
            }
        }.start();
    }//GEN-LAST:event_excluirActionPerformed

    private void p1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_p1ItemStateChanged
        p2.removeAllItems();
        if (p1.getSelectedItem().equals("CÓDIGO")) {
            p2.setEnabled(false);
            p3Data.setEnabled(false);
            p3Formatado.setEnabled(false);
        } else if (p1.getSelectedItem().equals("CLIENTE")) {
            p2.addItem("PESSOA FÍSICA - CÓDIGO");
            p2.addItem("PESSOA FÍSICA - NOME");
            p2.addItem("PESSOA FÍSICA - CPF (SOMENTE NÚMEROS)");
            p2.addItem("PESSOA JURÍDICA - CÓDIGO");
            p2.addItem("PESSOA JURÍDICA - NOME");
            p2.addItem("PESSOA JURÍDICA - NOME FANTASIA");
            p2.addItem("PESSOA JURÍDICA - CNPJ (SOMENTE NÚMEROS)");
            p2.setEnabled(true);
        } else if (p1.getSelectedItem().equals("STATUS")) {
            for (StsOrcamento status : Controle.stsOrcamento) {
                p2.addItem(status.toString());
            }
            p2.setEnabled(true);
            p3Data.setEnabled(false);
            p3Formatado.setEnabled(false);
        } else if (p1.getSelectedItem().equals("DATA EMISSÃO") | p1.getSelectedItem().equals("DATA VALIDADE")) {
            p2.setEnabled(false);
            p3Data.setEnabled(true);
            p3Texto.setEnabled(false);
            p3Formatado.setEnabled(false);
        } else {
            p2.setEnabled(false);
            p3Data.setEnabled(false);
            p3Formatado.setEnabled(false);
        }
    }//GEN-LAST:event_p1ItemStateChanged

    private void p1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p1ActionPerformed

    private void botaoPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPesquisarActionPerformed
        new Thread() {
            @Override
            public void run() {
                pesquisar();
            }
        }.start();
    }//GEN-LAST:event_botaoPesquisarActionPerformed

    private void mostrarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarTodosActionPerformed
        new Thread() {
            @Override
            public void run() {
                mostraTodos();
            }
        }.start();
    }//GEN-LAST:event_mostrarTodosActionPerformed

    private void p2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_p2ItemStateChanged
        try {
            if (p2.getItemCount() != 0) {
                if (p2.getSelectedItem().toString().equals("PESSOA FÍSICA - CPF (SOMENTE NÚMEROS)")) {
                    mascaraPesquisa = new MaskFormatter("###.###.###-##");
                    p3Formatado.setFormatterFactory(new DefaultFormatterFactory(mascaraPesquisa));
                    p3Formatado.setValue("");
                    p3Formatado.setEnabled(true);
                    p3Texto.setEnabled(false);

                }
                if (p2.getSelectedItem().toString().equals("PESSOA JURÍDICA - CNPJ (SOMENTE NÚMEROS)")) {
                    mascaraPesquisa = new MaskFormatter("##.###.###/####-##");
                    p3Formatado.setFormatterFactory(new DefaultFormatterFactory(mascaraPesquisa));
                    p3Formatado.setValue("");
                    p3Formatado.setEnabled(true);
                    p3Texto.setEnabled(false);
                }
                if (p2.getSelectedItem().toString().contains("NOME") || p2.getSelectedItem().toString().contains("CÓDIGO")) {
                    p3Formatado.setEnabled(false);
                    p3Texto.setEnabled(true);
                }
            }
        } catch (ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }//GEN-LAST:event_p2ItemStateChanged

    private void gerarPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerarPdfActionPerformed
        if (tipoPdf.getSelectedItem().equals("SIMPLES")) {
            switch (CLASSE_PAI) {
                case 1:
                case 2:
                    Orcamento.geraPdf((int) tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0),
                            true,
                            false,
                            Integer.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 6).toString().substring(0, 1)),
                            loading,
                            false);
                    break;
                case 3:
                    Orcamento.geraPdf((int) tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0),
                            true,
                            false,
                            Integer.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 4).toString().substring(0, 1)),
                            loading,
                            false);
                    break;
            }
        } else {
            switch (CLASSE_PAI) {
                case 1:
                case 2:
                    Orcamento.geraPdf((int) tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0),
                            true,
                            true,
                            Integer.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 6).toString().substring(0, 1)),
                            loading,
                            false);
                    break;
                case 3:
                    Orcamento.geraPdf((int) tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0),
                            true,
                            true,
                            Integer.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 4).toString().substring(0, 1)),
                            loading,
                            false);
                    break;
            }
        }
        excluir.setEnabled(false);
        editar.setEnabled(false);
        gerarPdf.setEnabled(false);
        tipoPdf.setEnabled(false);
        enviarProducao.setEnabled(false);
    }//GEN-LAST:event_gerarPdfActionPerformed

    private void enviarProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarProducaoActionPerformed
        try {
            //VERIFICA SE EXISTEM CÁLCULO DA OP PARA ORC PRODUÇÃO---------------
            CODIGO_ORCAMENTO = (int) tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0);
            if (!OrcamentoDAO.verificaCalculos(CODIGO_ORCAMENTO) & TIPO_ORCAMENTO == 2) {
                JOptionPane.showMessageDialog(null, "NÃO FOI ENCONTRADO OS CÁLCULOS DO ORÇAMENTO.\nEDITE O ORÇAMENTO E FAÇA OS CÁLCULOS NOVAMENTE.", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //------------------------------------------------------------------

            //CARREGA OS DADOS DO ORÇEMENTO-------------------------------------
            Orcamento orcamento = OrcamentoDAO.carregaEdicaoOrcamento(CODIGO_ORCAMENTO);
            //------------------------------------------------------------------

            //VERIFICA SE O CLIENTE POSSUI CRÉDITO SUFICIENTE-------------------
            if ((ClienteDAO.retornaCredCliente(orcamento.getCodCliente(), orcamento.getTipoPessoa())
                    < Float.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 5).toString()))
                    & STATUS != 4) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this,
                        "O CLIENTE NÃO POSSUI CRÉDITO SUFICIENTE PARA A PRODUÇÃO.\n"
                        + "DESEJA ENVIAR O ORÇAMENTO PARA A AVALIAÇÃO DO OD?",
                        "ERRO",
                        dialogButton);
                if (dialogResult == 0) {
                    //ENVIA O ORÇAMENTO PARA A AVALIAÇÃO DO OD------------------
                    OrcamentoDAO.alteraStatus(CODIGO_ORCAMENTO, 3);
                    mostraTodos();
                    JOptionPane.showMessageDialog(null,
                            "O ORÇAMENTO FOI ENCAMINHADO PARA A AVALIAÇÃO DO ORDENADOR DE DESPESAS",
                            "MENSAGEM",
                            JOptionPane.INFORMATION_MESSAGE);
                    //----------------------------------------------------------
                } else {
                    return;
                }
                //------------------------------------------------------------------

            } else {
                //ABRE A TELA DE ORDEM DE PRODUÇÃO------------------------------
                gj.abrirJanelas(EnviarOrdemProducaoFrame.getInstancia(loading, gj), "ABRIR ORDEM DE PRODUÇÃO");
                //--------------------------------------------------------------

                //CARREGA OS MODELOS DAS TABELAS--------------------------------
                DefaultTableModel modeloProdutos = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaProdutos.getModel();
                DefaultTableModel modeloPapel = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaPapeis.getModel();
                DefaultTableModel modeloAcabamentos = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaAcabamentos.getModel();
                DefaultTableModel modeloServicos = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaServicos.getModel();
                DefaultTableModel modeloQuantidades = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaQuantidades.getModel();
                //--------------------------------------------------------------

                //PREENCHE OS CAMPOS DA TELA------------------------------------
                EnviarOrdemProducaoFrame.ORC_BASE = CODIGO_ORCAMENTO;
                EnviarOrdemProducaoFrame.codigoOrcamentoBase.setValue(CODIGO_ORCAMENTO);
                EnviarOrdemProducaoFrame.setVLR_ORC(
                        Double.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 5).toString()));

                //ZERAR AS TABELAS----------------------------------------------
                modeloProdutos.setNumRows(0);
                modeloPapel.setNumRows(0);
                modeloAcabamentos.setNumRows(0);
                modeloServicos.setNumRows(0);
                modeloQuantidades.setNumRows(0);
                //--------------------------------------------------------------

                //CARREGA OS DADOS DO CLIENTE-----------------------------------
                Cliente cliente = ClienteDAO.selInfoOp(orcamento.getTipoPessoa(), orcamento.getCodCliente());
                if (orcamento.getTipoPessoa() == 1) {
                    EnviarOrdemProducaoFrame.tipoPessoa.setText("PESSOA FÍSICA");
                    EnviarOrdemProducaoFrame.cpfCliente.setValue(cliente.getCpf());
                } else {
                    EnviarOrdemProducaoFrame.tipoPessoa.setText("PESSOA JURÍDICA");
                    EnviarOrdemProducaoFrame.cnpjCliente.setValue(cliente.getCnpj());
                }
                EnviarOrdemProducaoFrame.codigoCliente.setValue(orcamento.getCodCliente());
                EnviarOrdemProducaoFrame.nomeCliente.setText(cliente.getNome());
                for (Contato a1 : OrcamentoDAO.retornaInformacoesContatos(orcamento.getCodContato())) {
                    EnviarOrdemProducaoFrame.nomeContato.setText(a1.getNomeContato());
                    EnviarOrdemProducaoFrame.telefoneContato.setText(a1.getTelefone());
                }
                EnviarOrdemProducaoFrame.setCOD_CONTATO(orcamento.getCodContato());
                for (Endereco a1 : OrcamentoDAO.retornaInformacoesEnderecos(orcamento.getCodEndereco())) {
                    EnviarOrdemProducaoFrame.cidade.setText(a1.getCidade());
                    EnviarOrdemProducaoFrame.uf.setText(a1.getUf());
                }
                EnviarOrdemProducaoFrame.setCOD_ENDERECO(orcamento.getCodEndereco());
                //--------------------------------------------------------------

                //CARREGA OS PRODUTOS DO ORÇAMENTO------------------------------
                for (ProdOrcamento produtos : ProdutoDAO.carregaProdutosOrcamento(CODIGO_ORCAMENTO)) {
                    if (produtos.getCodProduto() != 0) {
                        //ARMAZENA O CÓDIGO DO PRODUTO--------------------------
                        CODIGO_PRODUTO = produtos.getCodProduto();
                        //------------------------------------------------------

                        //VERIFICA SE EXISTE ESTOQUE DISPONÍVEL CASO PR ENT-----
                        if (TIPO_ORCAMENTO == 1) {
                            if (ProdutoDAO.verificaEstoque(CODIGO_PRODUTO,
                                    produtos.getQuantidade())) {
                                JOptionPane.showMessageDialog(null,
                                        "A QUANTIDADE SOLICITADA É MAIOR DO QUE A QUANTIDADE DISPONÍVEL EM ESTOQUE."
                                        + "\nFALE COM O SETOR RESPONSÁVEL E TENTE NOVAMENTE.",
                                        "ERRO",
                                        JOptionPane.ERROR_MESSAGE);
                                gj.fecharJanelas(EnviarOrdemProducaoFrame.class);
                                return;
                            }
                        }
                        //------------------------------------------------------

                        //INSERE NA TABELA PRODUTOS-----------------------------
                        if (TIPO_ORCAMENTO == 1) {
                            ProdutoPrEntBEAN produto = ProdutoDAO.retornaInfoPe(CODIGO_PRODUTO);
                            modeloProdutos.addRow(new Object[]{
                                CODIGO_PRODUTO,
                                produto.getDescricao(),
                                produto.getLargura(),
                                produto.getAltura(),
                                produto.getQtdPaginas(),
                                "",
                                "",
                                produtos.getObservacaoProduto()});
                        } else if (TIPO_ORCAMENTO == 2) {
                            ProdutoBEAN produto = ProdutoDAO.retornaInfoProd(CODIGO_PRODUTO, (byte) 1);
                            modeloProdutos.addRow(new Object[]{
                                CODIGO_PRODUTO,
                                produto.getDescricao(),
                                produto.getLargura(),
                                produto.getAltura(),
                                produto.getQuantidadeFolhas(),
                                "",
                                "",
                                produtos.getObservacaoProduto()});
                        }
                        //------------------------------------------------------

                        //INSERE NA TABELA QUANTIDADES--------------------------
                        modeloQuantidades.addRow(new Object[]{
                            CODIGO_PRODUTO,
                            produtos.getQuantidade()});
                        //------------------------------------------------------

                        //INSERE NA TABELA PAPÉIS-------------------------------
                        if (TIPO_ORCAMENTO == 2) {
                            for (PapelBEAN papeisCadastroBEAN : ProdutoDAO.retornaInformacoesPapel(Integer.valueOf(CODIGO_PRODUTO))) {
                                CalculosOp calculosBEAN = OrcamentoDAO.retornaCalculosProposta(CODIGO_ORCAMENTO,
                                        Integer.valueOf(CODIGO_PRODUTO),
                                        produtos.getTipoProduto(),
                                        papeisCadastroBEAN.getCodigo(),
                                        papeisCadastroBEAN.getTipoPapel());
                                modeloPapel.addRow(new Object[]{
                                    CODIGO_PRODUTO,
                                    papeisCadastroBEAN.getCodigo(),
                                    papeisCadastroBEAN.getDescricaoPapel(),
                                    papeisCadastroBEAN.getTipoPapel(),
                                    papeisCadastroBEAN.getCorFrente(),
                                    papeisCadastroBEAN.getCorVerso(),
                                    calculosBEAN.getQtdFolhasTotal(),
                                    calculosBEAN.getPerca(),
                                    calculosBEAN.getFormato(),
                                    calculosBEAN.getQtdChapas()
                                });

                            }
                        }
                        //------------------------------------------------------
                    } else {
                        //CASO O ORÇAMENTO SEJA SOMENTE DE SERVIÇO--------------
                        modeloProdutos.addRow(new Object[]{
                            0,
                            "SERVIÇOS",
                            "-",
                            "-",
                            "-",
                            "",
                            "-"});
                        //------------------------------------------------------
                    }

                    //INSERE NA TABELA ACABAMENTOS------------------------------
                    if (TIPO_ORCAMENTO == 2) {
                        try {
                            for (AcabamentoProdBEAN lcBEAN
                                    : AcabamentoDAO.retornaAcabamentosProduto(CODIGO_PRODUTO)) {
                                modeloAcabamentos.addRow(new Object[]{
                                    CODIGO_PRODUTO,
                                    lcBEAN.getCodigoAcabamento(),
                                    AcabamentoDAO.retornaDescricaoAcabamentos(lcBEAN.getCodigoAcabamento())});
                            }
                        } catch (SemAcabamentoException ex) {
                            //NENHUMA AÇÃO
                        }
                    }
                    //----------------------------------------------------------

                    //INFORMA O TIPO DE PRODUTO---------------------------------
                    EnviarOrdemProducaoFrame.setTIPO_PROD(produtos.getTipoProduto());
                    //----------------------------------------------------------
                }

                //CARREGA OS SERVIÇOS DO ORÇAMENTO------------------------------
                for (Servicos servicos : ServicoDAO.carregaServicosOrcamento(CODIGO_ORCAMENTO)) {
                    modeloServicos.addRow(new Object[]{
                        servicos.getCod(),
                        ServicoDAO.carregaDescricaoServicos(servicos.getCod())
                    });
                }
                //--------------------------------------------------------------

                //RESETA OS ESTADOS DA UI ORÇAMENTOS----------------------------
                excluir.setEnabled(false);
                editar.setEnabled(false);
                gerarPdf.setEnabled(false);
                enviarProducao.setEnabled(false);

                if (TIPO_ORCAMENTO == 1) {
                    EnviarOrdemProducaoFrame.estadoPe();
                }
                //--------------------------------------------------------------
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }//GEN-LAST:event_enviarProducaoActionPerformed

    private void enviarEmailAnexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarEmailAnexoActionPerformed

    }//GEN-LAST:event_enviarEmailAnexoActionPerformed

    private void calcularOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularOrcamentoActionPerformed
        new Thread() {
            @Override
            public void run() {
                btnCalcularAction();
            }
        }.start();
    }//GEN-LAST:event_calcularOrcamentoActionPerformed

    private void salvarOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarOrcamentoActionPerformed
        new Thread() {
            @Override
            public void run() {
                salvar();
            }
        }.start();

    }//GEN-LAST:event_salvarOrcamentoActionPerformed

    private void codigoOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoOrcamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoOrcamentoActionPerformed

    private void limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparActionPerformed
        int retorno = JOptionPane.showConfirmDialog(null, "ESTA OPERAÇÃO IRÁ REMOVER TODAS AS INFORMAÇÕES DO ORÇAMENTO", "CONFIRMAR LIMPEZA?", JOptionPane.OK_CANCEL_OPTION);
        if (retorno == 0) {
            limpa();
        }
    }//GEN-LAST:event_limparActionPerformed

    private void downloadEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadEstoqueActionPerformed
        Estoque.downloadEstoqueFunction((byte) 2);
    }//GEN-LAST:event_downloadEstoqueActionPerformed

    private void creditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creditoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creditoActionPerformed

    private void naoAprovadoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_naoAprovadoClienteActionPerformed
        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this,
                    "ESTE ORÇAMENTO NÃO PODERÁ MAIS SOFRER MODIFICAÇÕES",
                    "CONFIRMAÇÃO",
                    dialogButton);
            if (dialogResult == 0) {
                OrcamentoDAO.alteraStatus(Integer.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0).toString()), 6);
                mostraTodos();
            } else {
                return;
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }//GEN-LAST:event_naoAprovadoClienteActionPerformed

    private void autorizarProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autorizarProducaoActionPerformed
        new Thread("Autorizar produção") {
            @Override
            public void run() {
                try {
                    if (rdOdGrafica.isSelected()) {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "AUTORIZAR PRODUÇÃO PARA O ORÇAMENTO "
                                + tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0).toString() + "?",
                                "CONFIRMAÇÃO",
                                dialogButton);
                        if (dialogResult == 0) {
                            OrcamentoDAO.alteraStatus(Integer.valueOf(tabelaConsulta.getValueAt(
                                    tabelaConsulta.getSelectedRow(), 0).toString()), 4);
                            pesquisar();
                        } else {
                            return;
                        }
                    } else if (rdOdCliente.isSelected()) {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "AUTORIZAR PRODUÇÃO PARA O ORÇAMENTO "
                                + tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0).toString() + "?",
                                "CONFIRMAÇÃO",
                                dialogButton);
                        if (dialogResult == 0) {
                            OrcamentoDAO.alteraStatus(Integer.valueOf(tabelaConsulta.getValueAt(
                                    tabelaConsulta.getSelectedRow(), 0).toString()), 11);
                            pesquisar();
                        } else {
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                }
                loading.setVisible(false);
            }
        }.start();
    }//GEN-LAST:event_autorizarProducaoActionPerformed

    private void negarProducaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negarProducaoActionPerformed
        new Thread("Negar Producao") {
            @Override
            public void run() {
                try {
                    if (rdOdGrafica.isSelected()) {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "NEGAR PRODUÇÃO PARA O ORÇAMENTO "
                                + tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0).toString() + "?",
                                "CONFIRMAÇÃO",
                                dialogButton);
                        if (dialogResult == 0) {
                            OrcamentoDAO.alteraStatus(Integer.valueOf(tabelaConsulta.getValueAt(
                                    tabelaConsulta.getSelectedRow(), 0).toString()), 5);
                            pesquisar();
                        } else {
                            return;
                        }
                    } else if (rdOdCliente.isSelected()) {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null,
                                "NEGAR PRODUÇÃO PARA O ORÇAMENTO "
                                + tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0).toString() + "?",
                                "CONFIRMAÇÃO",
                                dialogButton);
                        if (dialogResult == 0) {
                            OrcamentoDAO.alteraStatus(Integer.valueOf(tabelaConsulta.getValueAt(
                                    tabelaConsulta.getSelectedRow(), 0).toString()), 12);
                            pesquisar();
                        } else {
                            return;
                        }
                    }
                } catch (SQLException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                }
                loading.setVisible(false);
            }
        }.start();
    }//GEN-LAST:event_negarProducaoActionPerformed

    private void haFreteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_haFreteItemStateChanged
        if (haFrete.isSelected() == true) {
            frete.setEditable(true);
            frete.setEnabled(true);
        } else {
            frete.setEditable(false);
            frete.setEnabled(false);
        }
    }//GEN-LAST:event_haFreteItemStateChanged

    private void proxPagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proxPagMouseClicked
        new Thread("Mudar página consulta orçamento") {
            @Override
            public void run() {
                pagAtual.setValue((int) pagAtual.getValue() + 1);
                if ((int) pagAtual.getValue() > 1) {
                    mudarPag();
                }
            }
        }.start();
    }//GEN-LAST:event_proxPagMouseClicked

    private void antPagMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_antPagMouseClicked
        new Thread("Mudar página consulta orçamento") {
            @Override
            public void run() {
                if ((int) pagAtual.getValue() > 1) {
                    pagAtual.setValue((int) pagAtual.getValue() - 1);
                    mudarPag();
                }
            }
        }.start();
    }//GEN-LAST:event_antPagMouseClicked

    private void pagAtualPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pagAtualPropertyChange

    }//GEN-LAST:event_pagAtualPropertyChange

    private void tabelaConsultaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaConsultaKeyReleased

    }//GEN-LAST:event_tabelaConsultaKeyReleased

    private void limpar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpar1ActionPerformed
        ImageIcon imagem = new ImageIcon(this.getClass().getResource("/icones/cortePapel66x96.gif"));
        JOptionPane.showMessageDialog(null,
                "",
                "",
                JOptionPane.INFORMATION_MESSAGE,
                imagem);
    }//GEN-LAST:event_limpar1ActionPerformed

    private void enviarExpedicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarExpedicaoActionPerformed
        try {
            //CARREGA OS DADOS DO ORÇEMENTO-------------------------------------
            CODIGO_ORCAMENTO = (int) tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0);
            Orcamento orcamento = OrcamentoDAO.carregaEdicaoOrcamento(CODIGO_ORCAMENTO);
            //------------------------------------------------------------------

            //VERIFICA SE O CLIENTE POSSUI CRÉDITO SUFICIENTE-------------------
            if ((ClienteDAO.retornaCredCliente(orcamento.getCodCliente(), orcamento.getTipoPessoa())
                    < Float.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 5).toString()))
                    & STATUS != 4) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(this,
                        "O CLIENTE NÃO POSSUI CRÉDITO SUFICIENTE PARA A PRODUÇÃO.\n"
                        + "DESEJA ENVIAR O ORÇAMENTO PARA A AVALIAÇÃO DO OD?",
                        "ERRO",
                        dialogButton);
                if (dialogResult == 0) {
                    //ENVIA O ORÇAMENTO PARA A AVALIAÇÃO DO OD------------------
                    OrcamentoDAO.alteraStatus(CODIGO_ORCAMENTO, 3);
                    mostraTodos();
                    JOptionPane.showMessageDialog(null,
                            "O ORÇAMENTO FOI ENCAMINHADO PARA A AVALIAÇÃO DO ORDENADOR DE DESPESAS",
                            "MENSAGEM",
                            JOptionPane.INFORMATION_MESSAGE);
                    //----------------------------------------------------------
                } else {
                    return;
                }
                //------------------------------------------------------------------

            } else {
                //ABRE A TELA DE ORDEM DE PRODUÇÃO------------------------------
                gj.abrirJanelas(EnviarOrdemProducaoFrame.getInstancia(loading, gj), "ENVIAR PEDIDO PARA EXPEDIÇÃO");
                //--------------------------------------------------------------

                //CARREGA OS MODELOS DAS TABELAS--------------------------------
                DefaultTableModel modeloProdutos = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaProdutos.getModel();
                DefaultTableModel modeloServicos = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaServicos.getModel();
                DefaultTableModel modeloQuantidades = (DefaultTableModel) EnviarOrdemProducaoFrame.tabelaQuantidades.getModel();
                //--------------------------------------------------------------

                //PREENCHE OS CAMPOS DA TELA------------------------------------
                EnviarOrdemProducaoFrame.ORC_BASE = CODIGO_ORCAMENTO;
                EnviarOrdemProducaoFrame.codigoOrcamentoBase.setValue(CODIGO_ORCAMENTO);
                EnviarOrdemProducaoFrame.setVLR_ORC(
                        Double.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 5).toString()));

                //ZERAR AS TABELAS----------------------------------------------
                modeloProdutos.setNumRows(0);
                modeloServicos.setNumRows(0);
                modeloQuantidades.setNumRows(0);
                //--------------------------------------------------------------

                //CARREGA OS DADOS DO CLIENTE-----------------------------------
                Cliente cliente = ClienteDAO.selInfoOp(orcamento.getTipoPessoa(), orcamento.getCodCliente());
                if (orcamento.getTipoPessoa() == 1) {
                    EnviarOrdemProducaoFrame.tipoPessoa.setText("PESSOA FÍSICA");
                    EnviarOrdemProducaoFrame.cpfCliente.setValue(cliente.getCpf());
                } else {
                    EnviarOrdemProducaoFrame.tipoPessoa.setText("PESSOA JURÍDICA");
                    EnviarOrdemProducaoFrame.cnpjCliente.setValue(cliente.getCnpj());
                }
                EnviarOrdemProducaoFrame.codigoCliente.setValue(orcamento.getCodCliente());
                EnviarOrdemProducaoFrame.nomeCliente.setText(cliente.getNome());
                for (Contato a1 : OrcamentoDAO.retornaInformacoesContatos(orcamento.getCodContato())) {
                    EnviarOrdemProducaoFrame.nomeContato.setText(a1.getNomeContato());
                    EnviarOrdemProducaoFrame.telefoneContato.setText(a1.getTelefone());
                }
                EnviarOrdemProducaoFrame.setCOD_CONTATO(orcamento.getCodContato());
                for (Endereco a1 : OrcamentoDAO.retornaInformacoesEnderecos(orcamento.getCodEndereco())) {
                    EnviarOrdemProducaoFrame.cidade.setText(a1.getCidade());
                    EnviarOrdemProducaoFrame.uf.setText(a1.getUf());
                }
                EnviarOrdemProducaoFrame.setCOD_ENDERECO(orcamento.getCodEndereco());
                //--------------------------------------------------------------

                //CARREGA OS PRODUTOS DO ORÇAMENTO------------------------------
                for (ProdOrcamento produtos : ProdutoDAO.carregaProdutosOrcamento(CODIGO_ORCAMENTO)) {
                    if (produtos.getCodProduto() != 0) {
                        //ARMAZENA O CÓDIGO DO PRODUTO--------------------------
                        CODIGO_PRODUTO = produtos.getCodProduto();
                        //------------------------------------------------------

                        //VERIFICA SE EXISTE ESTOQUE DISPONÍVEL CASO PR ENT-----
                        if (TIPO_ORCAMENTO == 1) {
                            if (ProdutoDAO.verificaEstoque(CODIGO_PRODUTO,
                                    produtos.getQuantidade())) {
                                JOptionPane.showMessageDialog(null,
                                        "A QUANTIDADE SOLICITADA É MAIOR DO QUE A QUANTIDADE DISPONÍVEL EM ESTOQUE."
                                        + "\nFALE COM O SETOR RESPONSÁVEL E TENTE NOVAMENTE.",
                                        "ERRO",
                                        JOptionPane.ERROR_MESSAGE);
                                gj.fecharJanelas(EnviarOrdemProducaoFrame.class);
                                return;
                            }
                        }
                        //------------------------------------------------------

                        //INSERE NA TABELA PRODUTOS-----------------------------
                        if (TIPO_ORCAMENTO == 1) {
                            ProdutoPrEntBEAN produto = ProdutoDAO.retornaInfoPe(CODIGO_PRODUTO);
                            modeloProdutos.addRow(new Object[]{
                                CODIGO_PRODUTO,
                                produto.getDescricao(),
                                produto.getLargura(),
                                produto.getAltura(),
                                produto.getQtdPaginas(),
                                "",
                                produtos.getObservacaoProduto()});
                        } else if (TIPO_ORCAMENTO == 2) {
                            ProdutoBEAN produto = ProdutoDAO.retornaInfoProd(CODIGO_PRODUTO, (byte) 1);
                            modeloProdutos.addRow(new Object[]{
                                CODIGO_PRODUTO,
                                produto.getDescricao(),
                                produto.getLargura(),
                                produto.getAltura(),
                                produto.getQuantidadeFolhas(),
                                "",
                                produtos.getObservacaoProduto()});
                        }
                        //------------------------------------------------------

                        //INSERE NA TABELA QUANTIDADES--------------------------
                        modeloQuantidades.addRow(new Object[]{
                            CODIGO_PRODUTO,
                            produtos.getQuantidade()});
                        //------------------------------------------------------

                    } else {
                        //CASO O ORÇAMENTO SEJA SOMENTE DE SERVIÇO--------------
                        modeloProdutos.addRow(new Object[]{
                            0,
                            "SERVIÇOS",
                            "-",
                            "-",
                            "-",
                            "",
                            "-"});
                        //------------------------------------------------------
                    }

                    //INFORMA O TIPO DE PRODUTO---------------------------------
                    EnviarOrdemProducaoFrame.setTIPO_PROD(produtos.getTipoProduto());
                    //----------------------------------------------------------
                }

                //CARREGA OS SERVIÇOS DO ORÇAMENTO------------------------------
                for (Servicos servicos : ServicoDAO.carregaServicosOrcamento(CODIGO_ORCAMENTO)) {
                    modeloServicos.addRow(new Object[]{
                        servicos.getCod(),
                        ServicoDAO.carregaDescricaoServicos(servicos.getCod())
                    });
                }
                //--------------------------------------------------------------

                //RESETA OS ESTADOS DA UI ORÇAMENTOS----------------------------
                excluir.setEnabled(false);
                editar.setEnabled(false);
                gerarPdf.setEnabled(false);
                enviarProducao.setEnabled(false);

                if (TIPO_ORCAMENTO == 1) {
                    EnviarOrdemProducaoFrame.estadoPe();
                }
                //--------------------------------------------------------------
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }//GEN-LAST:event_enviarExpedicaoActionPerformed

    private void jckbArteItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jckbArteItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jckbArteItemStateChanged

    private void jckbArteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jckbArteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jckbArteMouseClicked

    private void jckbArteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jckbArteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jckbArteActionPerformed

    private void jftfArtePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jftfArtePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jftfArtePropertyChange

    private void tblImpressaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblImpressaoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblImpressaoMouseClicked

    private void tblImpressaoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblImpressaoPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblImpressaoPropertyChange

    private void tblImpressaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblImpressaoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblImpressaoKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane JSPImpressao;
    private javax.swing.JButton adicionarProduto;
    private javax.swing.JButton adicionarServico;
    private javax.swing.JLabel antPag;
    public static javax.swing.JButton autorizarProducao;
    public static javax.swing.JButton botaoPesquisar;
    public static javax.swing.JButton calcularOrcamento;
    public static javax.swing.JTextField cidadeCliente;
    public static javax.swing.JFormattedTextField cif;
    public static javax.swing.JFormattedTextField codigoCliente;
    public static javax.swing.JFormattedTextField codigoOrcamento;
    public static javax.swing.JFormattedTextField credito;
    private com.toedter.calendar.JCalendar dataValidade;
    public static javax.swing.JFormattedTextField desconto;
    public static javax.swing.JTextField docCliente;
    private javax.swing.JButton downloadEstoque;
    public static javax.swing.JButton editar;
    public static javax.swing.JButton enviarEmailAnexo;
    public static javax.swing.JButton enviarExpedicao;
    public static javax.swing.JButton enviarProducao;
    public static javax.swing.JButton excluir;
    public static javax.swing.JFormattedTextField frete;
    public static javax.swing.JButton gerarPdf;
    public static javax.swing.ButtonGroup grOd;
    public static javax.swing.JCheckBox haFrete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    public static javax.swing.JCheckBox jckbArte;
    public static javax.swing.JFormattedTextField jftfArte;
    private javax.swing.JButton limpar;
    private javax.swing.JButton limpar1;
    public static javax.swing.JButton mostrarTodos;
    public static javax.swing.JButton naoAprovadoCliente;
    public static javax.swing.JButton negarProducao;
    public static javax.swing.JTextField nomeCliente;
    public static javax.swing.JTextField nomeContatoCliente;
    public static javax.swing.JButton observacaoProduto;
    private javax.swing.JTextArea observacoesOrcamento;
    public static javax.swing.JComboBox<String> p1;
    public static javax.swing.JComboBox<String> p2;
    public static com.toedter.calendar.JDateChooser p3Data;
    public static javax.swing.JFormattedTextField p3Formatado;
    public static javax.swing.JTextField p3Texto;
    public static javax.swing.JFormattedTextField pagAtual;
    private javax.swing.JButton pesquisarCliente;
    private javax.swing.JLabel proxPag;
    public static javax.swing.JRadioButton rdOdCliente;
    public static javax.swing.JRadioButton rdOdGrafica;
    private javax.swing.JButton removeServico;
    public static javax.swing.JButton removerProduto;
    public static javax.swing.JButton salvarOrcamento;
    public static javax.swing.JTable tabelaAcabamentos;
    public static javax.swing.JTable tabelaConsulta;
    public static javax.swing.JTable tabelaPapeis;
    public static javax.swing.JTable tabelaProdutos;
    public static javax.swing.JTable tabelaServicos;
    public static javax.swing.JTable tabelaTiragens;
    public static javax.swing.JTabbedPane tabsInformacoes;
    public static javax.swing.JTabbedPane tabsOrcamento;
    public static javax.swing.JTable tblImpressao;
    public static javax.swing.JTextField telefoneContatoCliente;
    public static javax.swing.JComboBox tipoPdf;
    public static javax.swing.JTextField tipoPessoa;
    public static javax.swing.JTextField ufCliente;
    public static javax.swing.JCheckBox valoresManuais;
    public static javax.swing.JFormattedTextField vlrTotal;
    // End of variables declaration//GEN-END:variables
    private synchronized void limpa() {
        EDITANDO = false;
        TIPO_ORCAMENTO = 0;
        codigoOrcamento.setValue(0);
        dataValidade.setDate(new Date());
        tipoPessoa.setText("");
        codigoCliente.setText("");
        nomeCliente.setText("");
        docCliente.setText("");
        nomeContatoCliente.setText("");
        telefoneContatoCliente.setText("");
        cidadeCliente.setText("");
        ufCliente.setText("");
        DefaultTableModel modeloPapel = (DefaultTableModel) tabelaPapeis.getModel();
        modeloPapel.setNumRows(0);
        DefaultTableModel modeloAcabamentos = (DefaultTableModel) tabelaAcabamentos.getModel();
        modeloAcabamentos.setNumRows(0);
        DefaultTableModel modeloServicos = (DefaultTableModel) tabelaServicos.getModel();
        modeloServicos.setNumRows(0);
        DefaultTableModel modeloProdutos = (DefaultTableModel) tabelaProdutos.getModel();
        modeloProdutos.setNumRows(0);
        DefaultTableModel modeloQuantidades = (DefaultTableModel) tabelaTiragens.getModel();
        modeloQuantidades.setNumRows(0);
        vlrTotal.setText("");
        haFrete.setSelected(false);
        frete.setValue(0d);
        cif.setValue(0);
        desconto.setValue(0);
        observacoesOrcamento.setText("");
        credito.setText("");
        estadoProdSv((byte) 2);
    }

    private void estadoInicial() {
        observacoesOrcamento.setLineWrap(true);
        p2.setEnabled(false);
        p3Data.setEnabled(false);
        excluir.setEnabled(false);
        editar.setEnabled(false);
        gerarPdf.setEnabled(false);
        tipoPdf.setEnabled(false);
        naoAprovadoCliente.setEnabled(false);
        //OD--------------------------------
        autorizarProducao.setEnabled(false);
        negarProducao.setEnabled(false);
        //----------------------------------
        enviarProducao.setEnabled(false);
        enviarExpedicao.setEnabled(false);
        salvarOrcamento.setEnabled(false);
        desconto.setValue(0);
        cif.setValue(0);
        frete.setValue(0d);
        jftfArte.setValue(0d);
        enviarEmailAnexo.setEnabled(false);

        frame.setSize(400, 200);
        tabelaFrame.setSize(380, 180);
        tabelaFrame.setShowGrid(true);
        modeloFrame.addColumn("CÓDIGO");
        modeloFrame.addColumn("DESCRIÇÃO");
        frame.getContentPane().add(tabelaFrame);
        codigoOrcamento.setEditable(false);
    }

    public synchronized static void estadoExpedicao() {
        tabsOrcamento.setEnabledAt(0, false);
        tipoPdf.setVisible(false);
        gerarPdf.setVisible(false);
        enviarProducao.setVisible(false);
        enviarEmailAnexo.setVisible(false);
        editar.setVisible(false);
        excluir.setVisible(false);
        naoAprovadoCliente.setVisible(false);
        autorizarProducao.setVisible(false);
        negarProducao.setVisible(false);
    }

    public synchronized static void estadoOdExt() {
        tabsOrcamento.setEnabledAt(0, false);
        tabsOrcamento.setSelectedIndex(1);
        p1.setSelectedIndex(2);
        p2.setSelectedIndex(3);
        p1.setEnabled(false);
        p2.setEnabled(false);
        p3Texto.setText(TelaAutenticacao.getUsrLogado().getLogin());
        p3Texto.setEnabled(false);
        rdOdCliente.setSelected(true);
        rdOdCliente.setEnabled(false);
        rdOdGrafica.setEnabled(false);
        mostrarTodos.setEnabled(false);
        setCLASSE_PAI((byte) 3);
    }

    /**
     * @param tipo 1 - COM PRODUTOS, 2 - SEM PRODUTOS
     */
    public synchronized static void estadoProdSv(byte tipo) {
        switch (tipo) {
            case 1:
                desconto.setEditable(true);
                cif.setEditable(true);
                removerProduto.setEnabled(true);
                observacaoProduto.setEnabled(true);
                haFrete.setEnabled(true);
                calcularOrcamento.setEnabled(true);
                break;
            case 2:
                desconto.setEditable(false);
                cif.setEditable(false);
                removerProduto.setEnabled(false);
                observacaoProduto.setEnabled(false);
                haFrete.setEnabled(false);
                calcularOrcamento.setEnabled(false);
                break;
        }
    }

    public synchronized static void mostraTodos() {
        loading.setVisible(true);
        loading.setText("CARREGANDO...");

        try {
            switch (CLASSE_PAI) {
                case 1:
                case 2:
                    modelInt.setNumRows(0);
                    break;
                case 3:
                    modelExt.setNumRows(0);
                    break;
            }

            for (Orcamento orcamento : OrcamentoDAO.mostraTodos()) {
                switch (CLASSE_PAI) {
                    case 1:
                    case 2:
                        modelInt.addRow(new Orcamento(
                                orcamento.getCod(),
                                orcamento.getCodigoLI(),
                                OrcamentoDAO.carregaNomeCliente(orcamento.getTipoPessoa(), orcamento.getCodCliente()),
                                orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                orcamento.getValorTotal(),
                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                orcamento.getCodEmissor()
                        ));
                        break;
                    case 3:
                        modelExt.addRow(new Orcamento(
                                orcamento.getCod(),
                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                orcamento.getValorTotal(),
                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                        ));
                        break;
                }
            }
            corTabelaOrcamento(tabelaConsulta);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }

    public synchronized static void pesquisar() {
        try {
            loading.setVisible(true);
            loading.setText("CARREGANDO...");

            switch (CLASSE_PAI) {
                case 1:
                case 2:
                    modelInt.setNumRows(0);
                    break;
                case 3:
                    modelExt.setNumRows(0);
                    break;
            }

            /**
             * Executa a pesquisa no banco de dados
             */
            switch (p1.getSelectedIndex()) {
                case 0:
                    JOptionPane.showMessageDialog(null,
                            "SELECIONE UM TIPO DE PESQUISA E TENTE NOVAMENTE.",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case 1:
                    for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                            null,
                            p3Texto.getText(),
                            1)) {
                        switch (CLASSE_PAI) {
                            case 1:
                            case 2:
                                modelInt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        orcamento.getCodigoLI(),
                                        orcamento.getNomeCliente(),
                                        orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                        orcamento.getCodEmissor()
                                ));
                                break;
                            case 3:
                                modelExt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                ));
                                break;
                        }
                    }
                    break;
                case 2:
                    switch (p2.getSelectedIndex()) {
                        case 0:
                        case 1:
                        case 3:
                        case 4:
                        case 5:
                            for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                                    p2.getSelectedItem().toString(),
                                    p3Texto.getText(),
                                    1)) {
                                switch (CLASSE_PAI) {
                                    case 1:
                                    case 2:
                                        modelInt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                orcamento.getCodigoLI(),
                                                orcamento.getNomeCliente(),
                                                orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                                orcamento.getCodEmissor()
                                        ));
                                        break;
                                    case 3:
                                        modelExt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                        ));
                                        break;
                                }
                            }
                            break;
                        default:
                            for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                                    p2.getSelectedItem().toString(),
                                    p3Formatado.getText(),
                                    1)) {
                                switch (CLASSE_PAI) {
                                    case 1:
                                    case 2:
                                        modelInt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                orcamento.getCodigoLI(),
                                                orcamento.getNomeCliente(),
                                                orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                                orcamento.getCodEmissor()
                                        ));
                                        break;
                                    case 3:
                                        modelExt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                        ));
                                        break;
                                }
                            }
                            break;
                    }
                    break;
                case 3:
                case 4:
                    for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                            null,
                            Controle.dataPadrao.format(p3Data.getDate()),
                            1)) {
                        switch (CLASSE_PAI) {
                            case 1:
                            case 2:
                                modelInt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        orcamento.getCodigoLI(),
                                        orcamento.getNomeCliente(),
                                        orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                        orcamento.getCodEmissor()
                                ));
                                break;
                            case 3:
                                modelExt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                ));
                                break;
                        }
                    }
                    break;
                case 5:
                    for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                            String.valueOf(p2.getSelectedIndex() + 1),
                            null,
                            1)) {
                        switch (CLASSE_PAI) {
                            case 1:
                            case 2:
                                modelInt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        orcamento.getCodigoLI(),
                                        orcamento.getNomeCliente(),
                                        orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                        orcamento.getCodEmissor()
                                ));
                                break;
                            case 3:
                                modelExt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                ));
                                break;
                        }
                    }
                    break;
            }
            corTabelaOrcamento(tabelaConsulta);
            pagAtual.setValue(1);
        } catch (SQLException | ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }

    private synchronized void geraFrame(java.awt.event.MouseEvent evt) {
        try {
            modeloFrame.setNumRows(0);
            int code = Integer.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.rowAtPoint(evt.getPoint()),
                    0).toString());
            for (ProdOrcamento bean : OrcamentoDAO.retornaProdutos(code)) {
                modeloFrame.addRow(new Object[]{
                    bean.getCodProduto(),
                    bean.getDescricaoProduto()
                });
            }
            frame.setTitle("ORÇAMENTO " + String.valueOf(code));
            frame.setVisible(true);

        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
    }

    private synchronized void mudarPag() {
        try {
            loading.setVisible(true);
            loading.setText("CARREGANDO...");
            //INSTANCIA AS FUNÇÕES E VARIÁVEIS NECESSÁRIAS----------------------
            String tipoPessoa = null;

            switch (CLASSE_PAI) {
                case 1:
                case 2:
                    modelInt.setNumRows(0);
                    break;
                case 3:
                    modelExt.setNumRows(0);
                    break;
            }
            //FAZ A CONSULTA----------------------------------------------------
            switch (p1.getSelectedIndex()) {
                case 0:
                    JOptionPane.showMessageDialog(null,
                            "SELECIONE UM TIPO DE PESQUISA E TENTE NOVAMENTE.",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                case 1:
                    for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                            null,
                            p3Texto.getText(),
                            (int) pagAtual.getValue())) {
                        switch (CLASSE_PAI) {
                            case 1:
                            case 2:
                                modelInt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        orcamento.getNomeCliente(),
                                        orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                        orcamento.getCodEmissor()
                                ));
                                break;
                            case 3:
                                modelExt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                ));
                                break;
                        }
                    }
                    break;
                case 2:
                    switch (p2.getSelectedIndex()) {
                        case 0:
                        case 1:
                        case 3:
                        case 4:
                        case 5:
                            for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                                    p2.getSelectedItem().toString(),
                                    p3Texto.getText(),
                                    (int) pagAtual.getValue())) {
                                switch (CLASSE_PAI) {
                                    case 1:
                                    case 2:
                                        modelInt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                orcamento.getNomeCliente(),
                                                orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                                orcamento.getCodEmissor()
                                        ));
                                        break;
                                    case 3:
                                        modelExt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                        ));
                                        break;
                                }
                            }
                            break;
                        default:
                            for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                                    p2.getSelectedItem().toString(),
                                    p3Formatado.getText(),
                                    (int) pagAtual.getValue())) {
                                switch (CLASSE_PAI) {
                                    case 1:
                                    case 2:
                                        modelInt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                orcamento.getNomeCliente(),
                                                orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                                orcamento.getCodEmissor()
                                        ));
                                        break;
                                    case 3:
                                        modelExt.addRow(new Orcamento(
                                                orcamento.getCod(),
                                                Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                                Controle.dataPadrao.format(orcamento.getDataValidade()),
                                                orcamento.getValorTotal(),
                                                Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                        ));
                                        break;
                                }
                            }
                            break;
                    }
                    break;
                case 3:
                case 4:
                    for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                            null,
                            Controle.dataPadrao.format(p3Data.getDate()),
                            (int) pagAtual.getValue())) {
                        switch (CLASSE_PAI) {
                            case 1:
                            case 2:
                                modelInt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        orcamento.getNomeCliente(),
                                        orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                        orcamento.getCodEmissor()
                                ));
                                break;
                            case 3:
                                modelExt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                ));
                                break;
                        }
                    }
                    break;
                case 5:
                    for (Orcamento orcamento : OrcamentoDAO.pesquisaRegistro((byte) p1.getSelectedIndex(),
                            p2.getSelectedItem().toString().substring(0, 1),
                            null,
                            (int) pagAtual.getValue())) {
                        switch (CLASSE_PAI) {
                            case 1:
                            case 2:
                                modelInt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        orcamento.getNomeCliente(),
                                        orcamento.getTipoPessoa() == 1 ? "PESSOA FÍSICA" : "PESSOA JURÍDICA",
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString(),
                                        orcamento.getCodEmissor()
                                ));
                                break;
                            case 3:
                                modelExt.addRow(new Orcamento(
                                        orcamento.getCod(),
                                        Controle.dataPadrao.format(orcamento.getDataEmissao()),
                                        Controle.dataPadrao.format(orcamento.getDataValidade()),
                                        orcamento.getValorTotal(),
                                        Controle.stsOrcamento.get(orcamento.getStatus() - 1).toString()
                                ));
                                break;
                        }

                    }
                    break;
            }
            corTabelaOrcamento(tabelaConsulta);
            loading.setVisible(false);
        } catch (SQLException | ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
    }

    //Cálculo-------------------------------------------------------------------
    /**
     * Calcula a quantidade de papéis a serem gastos
     */
    private synchronized static void calculaPapeis() {
        int quantidadePaginas = 0;
        int tiragem = 0;
        int numeroVias = 0;
        int numeroJogos = 0;
        int codigoProduto = 0;

        for (int i = 0; i < tabelaPapeis.getRowCount(); i++) {
            numeroVias = 0;
            codigoProduto = Integer.valueOf(tabelaPapeis.getValueAt(i, 0).toString());
            try {
                /*
                 1 - FOLHA
                 2 - LIVRO
                 3 - BLOCO
                 4 - BANNER
                 5 - OUTROS
                 */
                byte tipoProduto = 0;
                String tipoProdutoString = ProdutoDAO.retornaTipoProduto(codigoProduto);
                switch (tipoProdutoString) {
                    case "FOLHA":
                        tipoProduto = 1;
                        break;
                    case "LIVRO":
                        tipoProduto = 2;
                        break;
                    case "BLOCO":
                        tipoProduto = 3;
                        break;
                    case "BANNER":
                        tipoProduto = 4;
                        break;
                    case "OUTROS":
                        tipoProduto = 5;
                        break;
                }

                byte tipoPapel = 0;
                String tipoPapelAux = tabelaPapeis.getValueAt(i, 3).toString();

                switch (tipoPapelAux) {
                    case "FOLHA":
                        tipoPapel = 1;
                        break;
                    case "CAPA":
                        tipoPapel = 2;
                        break;
                    case "MIOLO":
                        tipoPapel = 3;
                        break;
                    case "1ª VIA":
                        tipoPapel = 4;
                        break;
                    case "2ª VIA":
                        tipoPapel = 5;
                        break;
                    case "3ª VIA":
                        tipoPapel = 6;
                        break;
                }

                if (tipoProduto == 3) {
                    for (int j = 0; j < tabelaPapeis.getRowCount(); j++) {
                        tipoPapelAux = tabelaPapeis.getValueAt(j, 3).toString();
                        if (tipoPapelAux.equals("1ª VIA")) {
                            numeroVias += 1;
                        } else if (tipoPapelAux.equals("2ª VIA")) {
                            numeroVias += 1;
                        } else if (tipoPapelAux.equals("3ª VIA")) {
                            numeroVias += 1;
                        }
                    }
                }

                for (int j = 0; j < tabelaProdutos.getRowCount(); j++) {
                    if (codigoProduto == Integer.valueOf(tabelaProdutos.getValueAt(j, 0).toString())) {
                        quantidadePaginas = Integer.valueOf(tabelaProdutos.getValueAt(j, 4).toString());
                        break;
                    }
                }

                for (int j = 0; j < tabelaTiragens.getRowCount(); j++) {
                    if (codigoProduto == Integer.valueOf(tabelaTiragens.getValueAt(j, 0).toString())) {
                        tiragem = Integer.valueOf(tabelaTiragens.getValueAt(j, 1).toString());
                        break;
                    }
                }

                tabelaPapeis.setValueAt(Orcamento.retornaQuantidadeFolhas(tipoProduto,
                        tipoPapel,
                        quantidadePaginas,
                        Integer.valueOf(tabelaPapeis.getValueAt(i, 6).toString()),
                        tiragem,
                        numeroVias,
                        Double.valueOf(tabelaPapeis.getValueAt(i, 7).toString())),
                        i,
                        8);

                tabelaPapeis.setValueAt(ProdutoDAO.retornaPrecoPapel(Integer.valueOf(tabelaPapeis.getValueAt(i, 1).toString())), i, 9);

            } catch (SQLException ex) {
                EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                EnvioExcecao.envio(loading);
            }
        }
    }

    /**
     * Calcula a quantidade de chapas a serem gastas
     */
    private synchronized static void calculaChapas() {
        int codigoProduto = 0;
        int tiragem = 0;
        int numeroPaginas = 0;
        Boolean digital;

        for (int i = 0; i < tabelaPapeis.getRowCount(); i++) {
            digital = false;
            try {
                codigoProduto = Integer.valueOf(tabelaPapeis.getValueAt(i, 0).toString());
                for (int j = 0; j < tabelaProdutos.getRowCount(); j++) {
                    if (codigoProduto == Integer.valueOf(tabelaPapeis.getValueAt(j, 0).toString())
                            & Boolean.valueOf(tabelaTiragens.getValueAt(j, 2).toString())) {
                        digital = true;
                        break;
                    }
                    if (codigoProduto == Integer.valueOf(tabelaPapeis.getValueAt(j, 0).toString())) {
                        numeroPaginas = Integer.valueOf(tabelaProdutos.getValueAt(j, 4).toString());
                        break;
                    }
                }

                for (int j = 0; j < tabelaTiragens.getRowCount(); j++) {
                    if (codigoProduto == Integer.valueOf(tabelaTiragens.getValueAt(j, 0).toString())) {
                        tiragem = Integer.valueOf(tabelaTiragens.getValueAt(j, 1).toString());
                        break;
                    }
                }

                /*
                 TIPOS PRODUTO
                 1 - FOLHA
                 2 - LIVRO
                 3 - BLOCO
                 4 - BANNER
                 5 - OUTROS

                 TIPOS PAPEL:
                 1 - FOLHA
                 2 - CAPA
                 3 - MIOLO
                 4 - 1A VIA
                 5 - 2A VIA
                 6 - 3A VIA
                 */
                byte tipoProduto = 0;
                String tipoProdutoString = ProdutoDAO.retornaTipoProduto(codigoProduto);
                switch (tipoProdutoString) {
                    case "FOLHA":
                        tipoProduto = 1;
                        break;
                    case "LIVRO":
                        tipoProduto = 2;
                        break;
                    case "BLOCO":
                        tipoProduto = 3;
                        break;
                    case "BANNER":
                        tipoProduto = 4;
                        break;
                    case "OUTROS":
                        tipoProduto = 5;
                        break;
                }

                byte tipoPapel = 0;
                switch (tabelaPapeis.getValueAt(i, 3).toString()) {
                    case "FOLHA":
                        tipoPapel = 1;
                        break;
                    case "CAPA":
                        tipoPapel = 2;
                        break;
                    case "MIOLO":
                        tipoPapel = 3;
                        break;
                    case "1ª VIA":
                        tipoPapel = 4;
                        break;
                    case "2ª VIA":
                        tipoPapel = 5;
                        break;
                    case "3ª VIA":
                        tipoPapel = 6;
                        break;
                }

                tabelaPapeis.setValueAt(digital ? 0 : Orcamento.retornaQuantidadeChapas(tipoProduto,
                        tipoPapel,
                        codigoProduto,
                        tiragem,
                        Integer.valueOf(tabelaPapeis.getValueAt(i, 4).toString()),
                        Integer.valueOf(tabelaPapeis.getValueAt(i, 5).toString()),
                        Integer.valueOf(tabelaPapeis.getValueAt(i, 6).toString()),
                        numeroPaginas),
                        i,
                        10);

                tabelaPapeis.setValueAt(ProdutoDAO.retornaPrecoChapa(317),
                        i,
                        11);

            } catch (SQLException ex) {
                EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                EnvioExcecao.envio(loading);
            }
        }
    }

    /**
     * Calcula os valores unitários
     */
    private synchronized void calculaValoresUnitarios() {
        double valorPapeis;
        double valorAcabamentos;
        double valorChapas;
        double valorImpressao;
        double valorUnitario;
        Integer codigoProduto;
        double cifDividido = Double.valueOf(cif.getText().replace(",", ".")) / (double) tabelaTiragens.getRowCount();
        double descontoDividido = Double.valueOf(desconto.getText().replace(",", ".")) / (double) tabelaTiragens.getRowCount();
        /*
         1 - DIGITAL
         2 - OFFSET
         */
        byte tipoMaquina;

        for (int i = 0; i < tabelaTiragens.getRowCount(); i++) {
            valorPapeis = 0d;
            valorAcabamentos = 0d;
            valorChapas = 0d;
            valorImpressao = 0d;
            valorUnitario = 0d;
            codigoProduto = Integer.valueOf(tabelaTiragens.getValueAt(i, 0).toString());
            tipoMaquina = (Boolean) tabelaTiragens.getValueAt(i, 2) ? (byte) 1 : (byte) 2;

            try {

                if (TIPO_ORCAMENTO != 1) {
                    for (int j = 0; j < tabelaPapeis.getRowCount(); j++) {
                        if (codigoProduto
                                == (int) tabelaPapeis.getValueAt(j, 0)) {
                            valorPapeis += Double.valueOf(tabelaPapeis.getValueAt(j, 8).toString())
                                    * Double.valueOf(tabelaPapeis.getValueAt(j, 9).toString());

                            if (tipoMaquina == 2) {
                                valorChapas += Double.valueOf(tabelaPapeis.getValueAt(j, 10).toString())
                                        * Double.valueOf(tabelaPapeis.getValueAt(j, 11).toString());
                            }
                        }
                    }

                    if (Boolean.valueOf(tabelaTiragens.getValueAt(i, 2).toString())) {
                        valorImpressao += Double.valueOf(tabelaTiragens.getValueAt(i, 4).toString());
                    }

                    valorAcabamentos += AcabamentoDAO.retornaPrecoUnitarioProduto(Integer.valueOf(tabelaTiragens.getValueAt(i, 0).toString()));

                    valorUnitario += (double) (valorAcabamentos * (Integer.valueOf(tabelaTiragens.getValueAt(i, 1).toString())));
                    valorUnitario += valorPapeis;
                    valorUnitario += valorChapas;
                    valorUnitario /= Double.valueOf(tabelaTiragens.getValueAt(i, 1).toString());
                    valorUnitario += valorImpressao;
                    valorUnitario += (double) (valorUnitario * cifDividido) / 100d;
                    valorUnitario -= (double) (valorUnitario * descontoDividido) / 100d;

                    BigDecimal format = new BigDecimal(Double.toString(valorUnitario)).setScale(2, RoundingMode.HALF_EVEN);
                    tabelaTiragens.setValueAt(format.doubleValue(), i, 5);
                } else {
                    valorUnitario = Double.valueOf(tabelaTiragens.getValueAt(i, 5).toString());

                    valorUnitario += (double) (valorUnitario * cifDividido) / 100d;
                    valorUnitario -= (double) (valorUnitario * descontoDividido) / 100d;

                    BigDecimal format = new BigDecimal(Double.toString(valorUnitario)).setScale(2, RoundingMode.HALF_EVEN);
                    tabelaTiragens.setValueAt(format.doubleValue(), i, 5);

                }

            } catch (SQLException ex) {
                EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                EnvioExcecao.envio(loading);
            }
        }
    }

    /**
     * Calcula o valor total
     */
    private synchronized void calculaValorTotal() {
        try {
            boolean semProduto = tabelaProdutos.getRowCount() == 0 ? true : false;
            double valorTotal = 0d;
            DecimalFormat df = new DecimalFormat("###,##0.000");

            if (!semProduto) {
                for (int i = 0; i < tabelaTiragens.getRowCount(); i++) {
                    valorTotal += Double.valueOf(tabelaTiragens.getValueAt(i, 1).toString())
                            * Double.valueOf(tabelaTiragens.getValueAt(i, 5).toString());
                }
            }

            if (haFrete.isSelected()) {
                valorTotal += Double.valueOf(frete.getText().replace(",", "."));
            }

            if (jckbArte.isSelected()) {
                valorTotal += Double.valueOf(jftfArte.getText().replace(",", "."));
            }

            for (int i = 0; i < tabelaServicos.getRowCount(); i++) {
                valorTotal += ServicoDAO.retornaVlrSrvOrcNExistente(Integer.valueOf(tabelaServicos.getValueAt(i, 0).toString()));
            }

            BigDecimal format = new BigDecimal(Double.toString(valorTotal)).setScale(2, RoundingMode.HALF_EVEN);
            vlrTotal.setValue(format.doubleValue());
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }

    }

    /**
     * Carrega os produtos selecionados para a tela
     *
     * @param codProd código do produto
     * @param tipoProd 1 - PERSONALIZADO (PP), 2 - PRONTA ENTREGA (PE), 3 -
     * INTERNET (PI)
     */
    public synchronized static void carregaProdutos(int codProd, byte tipoProd) {
        try {

            /**
             * verifica se existem produtos iguais na tabela
             */
            if (verificaCodProd(codProd) == 0) {
                return;
            }

            /**
             * verifica se a tabela está vazia
             */
            if (tabelaProdutos.getRowCount() == 0) {
                TIPO_ORCAMENTO = 0;
            }

            /**
             * @param tipoProduto 1 - FOLHA 2 - LIVRO 3 - BLOCO 4 - BANNER 5 -
             * OUTROS
             */
            byte tipoProduto = 0;
            String tipoProdutoString = null;

            /**
             * função condição para produtos a pronta entrega
             */
            if (tipoProd == 2) {
                /**
                 * verifica se existem somente prod produção ou somente prod
                 * entrega
                 */
                if (TIPO_ORCAMENTO == 2) {
                    JOptionPane.showMessageDialog(null,
                            "O ORÇAMENTO SOMENTE PODERÁ CONTER PRODUTOS PARA PRODUÇÃO OU SOMENTE PRODUTOS PARA PRONTA"
                            + " ENTREGA", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                /**
                 * define o tipo de orçamento para 1
                 */
                TIPO_ORCAMENTO = 1;

                /**
                 * desativa as abas que não são utilizadas
                 */
                tabsInformacoes.setEnabledAt(3, false);
                tabsInformacoes.setEnabledAt(4, false);

                /**
                 * carrega as informações do produto
                 */
                ProdutoPrEntBEAN produto = ProdutoDAO.retornaInfoPe(codProd);

                /**
                 * preenche a tabela produtos
                 */
                DefaultTableModel modeloProdutos = (DefaultTableModel) tabelaProdutos.getModel();
                modeloProdutos.addRow(new Object[]{
                    codProd,
                    produto.getDescricao(),
                    produto.getLargura(),
                    produto.getAltura(),
                    produto.getQtdPaginas(),
                    ""
                });

                /**
                 * preenche a tabela tiragens
                 */
                DefaultTableModel modeloTiragens = (DefaultTableModel) tabelaTiragens.getModel();
                modeloTiragens.addRow(new Object[]{
                    codProd,
                    1,
                    false,
                    false,
                    0d,
                    produto.getVlrUnit()
                });
                estadoProdSv((byte) 1);

            } /**
             * função para produtos para produção
             */
            else {
                /**
                 * verifica se existem somente prod produção ou somente prod
                 * entrega
                 */
                if (TIPO_ORCAMENTO == 1) {
                    JOptionPane.showMessageDialog(null,
                            "O ORÇAMENTO SOMENTE PODERÁ CONTER PRODUTOS PARA PRODUÇÃO OU SOMENTE PRODUTOS PARA PRONTA"
                            + " ENTREGA!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                /**
                 * define o tipo de orçamento para 2
                 */
                TIPO_ORCAMENTO = 2;

                /**
                 * ativa as abas que serão utilizadas
                 */
                tabsInformacoes.setEnabledAt(3, true);
                tabsInformacoes.setEnabledAt(4, true);

                /**
                 * define o tipo de produto string-byte
                 */
                tipoProdutoString = ProdutoDAO.retornaTipoProduto(Integer.valueOf(codProd));
                switch (tipoProdutoString) {
                    case "FOLHA":
                        tipoProduto = 1;
                        break;
                    case "LIVRO":
                        tipoProduto = 2;
                        break;
                    case "BLOCO":
                        tipoProduto = 3;
                        break;
                    case "BANNER":
                        tipoProduto = 4;
                        break;
                    case "OUTROS":
                        tipoProduto = 5;
                        break;
                }

                /**
                 * preenche a tabela produtos
                 */
                DefaultTableModel modeloProdutos = (DefaultTableModel) tabelaProdutos.getModel();
                ProdutoBEAN produto = ProdutoDAO.retornaInfoProd(codProd, (byte) 1);
                modeloProdutos.addRow(new Object[]{
                    codProd,
                    produto.getDescricao(),
                    produto.getLargura(),
                    produto.getAltura(),
                    produto.getQuantidadeFolhas(),
                    ""
                });
                /**
                 * preenche a tabela papeis e tabela impressão
                 */
                DefaultTableModel modeloPapeis = (DefaultTableModel) tabelaPapeis.getModel();
                DefaultTableModel modeloImpressao = (DefaultTableModel) tblImpressao.getModel();
                for (PapelBEAN papeisCadastroBEAN : ProdutoDAO.retornaInformacoesPapel(Integer.valueOf(codProd))) {
                    modeloPapeis.addRow(new Object[]{
                        codProd,
                        papeisCadastroBEAN.getCodigo(),
                        papeisCadastroBEAN.getDescricaoPapel(),
                        papeisCadastroBEAN.getTipoPapel(),
                        papeisCadastroBEAN.getCorFrente(),
                        papeisCadastroBEAN.getCorVerso(),
                        0,
                        5d,
                        0,
                        0,
                        0,
                        0
                    });

                    modeloImpressao.addRow(new Object[]{
                        produto.getDescricao(),
                        papeisCadastroBEAN.getTipoPapel(),});
                }

                /**
                 * preenche a tabela tiragens
                 */
                DefaultTableModel modeloTiragens = (DefaultTableModel) tabelaTiragens.getModel();
                modeloTiragens.addRow(new Object[]{
                    codProd,
                    1,
                    true,
                    false,
                    1d,
                    0d
                });

                /**
                 * preenche a tabela acabamentos
                 */
                DefaultTableModel modeloAcabamentos = (DefaultTableModel) tabelaAcabamentos.getModel();
                for (AcabamentoProdBEAN cadastroProdutosComponentesBEAN : AcabamentoDAO.retornaAcabamentosProduto(codProd)) {
                    modeloAcabamentos.addRow(new Object[]{
                        codProd,
                        cadastroProdutosComponentesBEAN.getCodigoAcabamento(),
                        AcabamentoDAO.retornaDescricaoAcabamentos(cadastroProdutosComponentesBEAN.getCodigoAcabamento()),
                        AcabamentoDAO.retornaValorAcabamento(cadastroProdutosComponentesBEAN.getCodigoAcabamento())
                    });
                }

            }
            /**
             * estado para tabela com produtos
             */
            estadoProdSv((byte) 1);

        } catch (SQLException | SemAcabamentoException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);

        }
    }

    /**
     * Ação do botão calcular
     */
    private void btnCalcularAction() {
        try {
            //CARREGA IMAGEM CALCULANDO---------------------------------------------
            loading.setVisible(true);
            loading.setText("CALCULANDO...");
            //----------------------------------------------------------------------

            //VERIFICA O ESTOQUE PARA PRODUTOS PR ENT-------------------------------
            if (TIPO_ORCAMENTO == 1) {
                if (ProdutoDAO.verificaEstoque(
                        Integer.valueOf(tabelaTiragens.getValueAt(0, 0).toString()),
                        Integer.valueOf(tabelaTiragens.getValueAt(0, 1).toString()))) {
                    JOptionPane.showMessageDialog(null,
                            "NÃO HÁ ESTOQUE DISPONÍVEL PARA A QUANTIDADE SOLICITADA",
                            "ATENÇÃO",
                            JOptionPane.INFORMATION_MESSAGE);
                    loading.setVisible(false);
                    return;
                }
            }
            //----------------------------------------------------------------------

            //VERIFICA ERROS--------------------------------------------------------
            if (TIPO_ORCAMENTO != 1) {
                for (int i = 0; i < tabelaPapeis.getRowCount(); i++) {
                    if (Integer.valueOf(tabelaPapeis.getValueAt(i, 6).toString()) == 0) {
                        JOptionPane.showMessageDialog(null, "DIGITE O FORMATO DE IMPRESSÃO DO PRODUTO!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                        loading.setVisible(false);
                        return;
                    }
                }

                for (int i = 0; i < tabelaTiragens.getRowCount(); i++) {
                    if (ProdutoDAO.retornaTipoProduto(Integer.valueOf(tabelaTiragens.getValueAt(i, 0).toString())).equals("BLOCO")
                            & Integer.valueOf(tabelaTiragens.getValueAt(i, 1).toString()) == 0) {
                        JOptionPane.showMessageDialog(null, "DIGITE O NÚMERO DE JOGOS DO PRODUTO", "INFO", JOptionPane.INFORMATION_MESSAGE);
                        loading.setVisible(false);
                        return;
                    }
                }

                for (int i = 0; i < tabelaProdutos.getRowCount(); i++) {
                    int codigoProduto = Integer.valueOf(tabelaProdutos.getValueAt(i, 0).toString());
                    if (tabelaTiragens.getValueAt(i, 2).equals(true) & tabelaTiragens.getValueAt(i, 3).equals(false)) {
                        if (Double.valueOf(tabelaTiragens.getValueAt(i, 4).toString()) == 0d) {
                            JOptionPane.showMessageDialog(null, "DIGITE O VALOR DA IMPRESSÃO DIGITAL DO PRODUTO " + codigoProduto + "!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                            loading.setVisible(false);
                            return;
                        }
                    } else if ((tabelaTiragens.getValueAt(i, 2).equals(true) && tabelaTiragens.getValueAt(i, 3).equals(true))) {
                        JOptionPane.showMessageDialog(null, "SELECIONE SOMENTE UMA MÁQUINA DE IMPRESSÃO.", "ERRO", JOptionPane.ERROR_MESSAGE);
                        OrcamentoFrame.loadingHide();
                        return;
                    } else if ((tabelaTiragens.getValueAt(i, 2).equals(false) && tabelaTiragens.getValueAt(i, 3).equals(false))) {
                        JOptionPane.showMessageDialog(null, "SELECIONE UMA MÁQUINA DE IMPRESSÃO.", "ERRO", JOptionPane.ERROR_MESSAGE);
                        loading.setVisible(false);
                        return;
                    }
                }
            }

            if (tabelaProdutos.getRowCount() == 0) {
                for (int i = 0; i < tabelaProdutos.getRowCount(); i++) {
                    String codigoProdutoAux = tabelaProdutos.getValueAt(i, 0).toString();
                    for (int j = 0; j < tabelaProdutos.getRowCount(); j++) {
                        if (j != i
                                & (codigoProdutoAux == tabelaProdutos.getValueAt(j, 0).toString()
                                & (tabelaProdutos.getValueAt(j, 5).toString().equals(tabelaProdutos.getValueAt(j, 5).toString())))) {
                            JOptionPane.showMessageDialog(null, "PRODUTOS COM MESMO CÓDIGO E MESMA OBSERVAÇÃO", "ERRO", JOptionPane.ERROR_MESSAGE);
                            loading.setVisible(false);
                            return;
                        }
                    }
                }
            }
            //----------------------------------------------------------------------

            //CÁLCULOS--------------------------------------------------------------
            if (TIPO_ORCAMENTO != 1) {
                calculaPapeis();
                calculaChapas();
            }

            if (!valoresManuais.isSelected()) {
                calculaValoresUnitarios();
            }

            calculaValorTotal();
            //----------------------------------------------------------------------

            //HABILITA O SALVAMENTO-------------------------------------------------        
            salvarOrcamento.setEnabled(true);
            //----------------------------------------------------------------------

            //FECHA A O STATUS CARREGANDO-------------------------------------------
            loading.setVisible(false);
            //----------------------------------------------------------------------
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }

    //Crud----------------------------------------------------------------------
    /**
     * Salva o orçamento
     */
    private synchronized void salvar() {
        try {

            loading.setText("SALVANDO...");
            loading.setVisible(true);

            Orcamento orcamento = new Orcamento();
            ProdOrcamento produtosOrcamentoBEAN = new ProdOrcamento();

            DefaultTableModel modeloServicos = (DefaultTableModel) tabelaServicos.getModel();
            DefaultTableModel modeloProdutos = (DefaultTableModel) tabelaProdutos.getModel();

            boolean semProduto = tabelaProdutos.getRowCount() == 0;

            //VERIFICAÇÃO DE ERROS--------------------------------------------------
            if (semProduto) {
                if (tabelaServicos.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "UM PRODUTO E/OU SERVIÇO DEVE SER ADICIONADO!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    loading.setVisible(false);
                    return;
                }
            }

            if (codigoCliente.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "UM CLIENTE DEVE SER ADICIONADO!", "ERRO", JOptionPane.ERROR_MESSAGE);
                loading.setVisible(false);
                return;
            }

            if (TelaAutenticacao.getUsrLogado() == null) {
                JOptionPane.showMessageDialog(null, "É NECESSÁRIO FAZER LOGIN PARA SALVAR O ORÇAMENTO", "ERRO", JOptionPane.ERROR_MESSAGE);
                loading.setVisible(false);
                return;
            }

            if (Double.valueOf(vlrTotal.getText().replace(",", ".")) == 0d) {
                JOptionPane.showMessageDialog(null, "NÃO É PERMITIDO A EMISSÃO DE ORÇAMENTO COM VALOR R$ 0,00", "ERRO", JOptionPane.ERROR_MESSAGE);
                loading.setVisible(false);
                return;
            }

            //FIM DA VERIFICAÇÃO DE ERROS-------------------------------------------
            orcamento.setCodEmissor(TelaAutenticacao.getUsrLogado().getCodigo());

            if (EDITANDO) {
                CODIGO_ORCAMENTO = (int) codigoOrcamento.getValue();
                orcamento.setCod(CODIGO_ORCAMENTO);
            } else {
                CODIGO_ORCAMENTO = OrcamentoDAO.retornaUltimoRegistro() + 1;
                orcamento.setCod(CODIGO_ORCAMENTO);
            }

            orcamento.setCodCliente(Integer.valueOf(codigoCliente.getText()));
            if (tipoPessoa.getText().equals("PESSOA FÍSICA")) {
                orcamento.setTipoPessoa(1);
            } else if (tipoPessoa.getText().equals("PESSOA JURÍDICA")) {
                orcamento.setTipoPessoa(2);
            }
            orcamento.setDataValidade(dataValidade.getDate());
            orcamento.setDataEmissao(new Date());
            orcamento.setCif(Float.valueOf(cif.getText().replace(",", ".")));
            orcamento.setDesconto(Float.valueOf(desconto.getText().replace(",", ".")));
            orcamento.setFrete(Double.valueOf(frete.getText().replace(",", ".")));
            orcamento.setArte(Double.valueOf(jftfArte.getText().replace(",", ".")));
            orcamento.setValorTotal(Float.valueOf(vlrTotal.getText().replace(",", ".")));
            orcamento.setDescricao(observacoesOrcamento.getText().toUpperCase());
            orcamento.setNomeContato(nomeContatoCliente.getText());
            orcamento.setTelefoneContato(telefoneContatoCliente.getText());
            orcamento.setCodContato(CODIGO_CONTATO);
            orcamento.setCodEndereco(CODIGO_ENDERECO);

            if (valoresManuais.isSelected() == true) {
                orcamento.setPrecosManuais(1);
            } else {
                orcamento.setPrecosManuais(0);
            }

            if (EDITANDO) {
                OrcamentoDAO.excluiCalculosProposta(CODIGO_ORCAMENTO);
                OrcamentoDAO.excluiProdOrc(CODIGO_ORCAMENTO);
                ServicoDAO.desassociaSvOrcamento(CODIGO_ORCAMENTO);
                OrcamentoDAO.excluiOrcamento(CODIGO_ORCAMENTO);
            }

            OrcamentoDAO.createOrcamentos(orcamento);

            if (tabelaServicos.getRowCount() != 0) {
                for (int i = 0; i < tabelaServicos.getRowCount(); i++) {
                    OrcamentoDAO.createServicosOrcamento(new Servicos(Integer.valueOf(tabelaServicos.getValueAt(i, 0).toString()),
                            CODIGO_ORCAMENTO,
                            2));
                }
            }

            if (!semProduto) {
                for (int j = 0; j < tabelaProdutos.getRowCount(); j++) {
                    produtosOrcamentoBEAN.setCodOrcamento(CODIGO_ORCAMENTO);
                    CODIGO_PRODUTO
                            = Integer.valueOf(tabelaProdutos.getValueAt(j, 0).toString());
                    produtosOrcamentoBEAN.setCodProduto(CODIGO_PRODUTO);
                    produtosOrcamentoBEAN.setDescricaoProduto(tabelaProdutos.getValueAt(j, 1).toString());
                    if (Integer.valueOf(tabelaTiragens.getValueAt(j, 0).toString())
                            == CODIGO_PRODUTO) {
                        produtosOrcamentoBEAN.setQuantidade(Integer.valueOf(tabelaTiragens.getValueAt(j, 1).toString()));
                    }

                    if ((boolean) tabelaTiragens.getValueAt(j, 2)) {
                        produtosOrcamentoBEAN.setMaquina(1);
                        produtosOrcamentoBEAN.setValorDigital(Double.valueOf(tabelaTiragens.getValueAt(j, 4).toString()));
                    } else {
                        produtosOrcamentoBEAN.setMaquina(2);
                        produtosOrcamentoBEAN.setValorDigital(0d);
                    }

                    if (tabelaProdutos.getValueAt(j, 5) == null) {
                        produtosOrcamentoBEAN.setObservacaoProduto("");
                    } else {
                        produtosOrcamentoBEAN.setObservacaoProduto(tabelaProdutos.getValueAt(j, 5).toString().toUpperCase());
                    }

                    produtosOrcamentoBEAN.setPrecoUnitario(Float.valueOf(tabelaTiragens.getValueAt(j, 5).toString().replace(",", ".")));

                    produtosOrcamentoBEAN.setTipoProduto(TIPO_ORCAMENTO == 1 ? (byte) 2 : (byte) 1);

                    OrcamentoDAO.criaProdOrc(produtosOrcamentoBEAN);

                    for (int i = 0; i < tabelaPapeis.getRowCount(); i++) {
                        if (Integer.valueOf(tabelaPapeis.getValueAt(i, 0).toString())
                                == CODIGO_PRODUTO) {
                            CalculosOp calculosBEAN = new CalculosOp();
                            calculosBEAN.setCodOp(0);
                            calculosBEAN.setCodProduto(CODIGO_PRODUTO);
                            calculosBEAN.setTipoProduto(TIPO_ORCAMENTO == 1 ? (byte) 2 : (byte) 1);
                            calculosBEAN.setTipoPapel(tabelaPapeis.getValueAt(i, 3).toString());
                            calculosBEAN.setQtdFolhasTotal(Integer.valueOf(tabelaPapeis.getValueAt(i, 8).toString()));
                            calculosBEAN.setFormato(Integer.valueOf(tabelaPapeis.getValueAt(i, 6).toString()));
                            calculosBEAN.setQtdChapas(Integer.valueOf(tabelaPapeis.getValueAt(i, 10).toString()));
                            calculosBEAN.setPerca(Float.valueOf(tabelaPapeis.getValueAt(i, 7).toString()));
                            calculosBEAN.setCodigoProposta(CODIGO_ORCAMENTO);
                            calculosBEAN.setCodigoPapel(Integer.valueOf(tabelaPapeis.getValueAt(i, 1).toString()));
                            OrcamentoDAO.createCalculosProposta(calculosBEAN);
                        }
                    }
                }
            }

            if (EDITANDO) {
                JOptionPane.showMessageDialog(null, "ORÇAMENTO ALTERADO COM SUCESSO!\nCÓDIGO DO ORÇAMENTO >>> " + CODIGO_ORCAMENTO + "\nDISPONÍVEL NA ABA DE CONSULTAS.");
            } else {
                JOptionPane.showMessageDialog(null, "ORÇAMENTO CRIADO COM SUCESSO!\nCÓDIGO DO ORÇAMENTO >>> " + CODIGO_ORCAMENTO + "\nDISPONÍVEL NA ABA DE CONSULTAS.");
            }

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "DESEJA GERAR O PDF DO ORÇAMENTO " + CODIGO_ORCAMENTO + " ?", "GERAR PDF", dialogButton);
            if (dialogResult == 0) {
                Orcamento.geraPdf(CODIGO_ORCAMENTO,
                        false,
                        false,
                        orcamento.getStatus(),
                        loading,
                        false);
            }
            limpa();
            loading.setVisible(false);

        } catch (OrcamentoInexistenteException | SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }

    /**
     * Exclui o orçamento
     */
    private synchronized void exclui() {
        try {
            CODIGO_ORCAMENTO
                    = Integer.valueOf(tabelaConsulta.getValueAt(tabelaConsulta.getSelectedRow(), 0).toString());

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(
                    this,
                    "EXCLUIR ORÇAMENTO Nº " + CODIGO_ORCAMENTO + " ?",
                    "CONFIRMAÇÃO DE EXCLUSÃO",
                    dialogButton
            );
            if (dialogResult == 0) {
                OrcamentoDAO.excluiProdOrc(CODIGO_ORCAMENTO);
                ServicoDAO.desassociaSvOrcamento(CODIGO_ORCAMENTO);
                OrcamentoDAO.excluiOrcamento(CODIGO_ORCAMENTO);
                OrcamentoDAO.excluiCalculosProposta(CODIGO_ORCAMENTO);
                mostraTodos();
            } else {
                return;
            }

            excluir.setEnabled(false);
            editar.setEnabled(false);
            gerarPdf.setEnabled(false);
            enviarProducao.setEnabled(false);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(loading);
        }
        loading.setVisible(false);
    }

    private static synchronized int verificaCodProd(int codProd) {
        for (int i = 0; i < tabelaProdutos.getRowCount(); i++) {
            if (tabelaProdutos.getValueAt(i, 0).toString().equals(codProd)) {
                JOptionPane.showMessageDialog(null,
                        "ADICIONAR PRODUTOS IGUAIS NO MESMO ORÇAMENTO ESTÁ EM FASE DE TESTES.",
                        "ATENÇÃO",
                        JOptionPane.INFORMATION_MESSAGE);
                return 0;
            }
        }
        return codProd;
    }

    /**
     * @param jTable tabelaControle
     */
    public synchronized static void corTabelaOrcamento(JTable jTable) {
        new Thread("Cor tabela") {
            @Override
            public void run() {
                try {
                    jTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value,
                                boolean isSelected, boolean hasFocus,
                                int row, int column) {
                            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            //***********************
                            Color c = Color.WHITE;
                            String texto = null;

                            String codEmissor = jTable.getValueAt(row, 8).toString();

                            switch (codEmissor) {
                                case "SIS":
                                    c = Color.getColor("amarelo1");
                                    label.setForeground(Color.BLACK);
                                    break;
                                default:
                                    c = Color.WHITE;
                                    label.setForeground(Color.BLACK);
                                    break;
                            }
                            label.setBackground(c);
                            //***********************
                            return label;
                        }
                    });
                    jTable.setSelectionForeground(Color.BLUE);
                } catch (RuntimeException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(null);
                }

            }
        }.start();

    }

}

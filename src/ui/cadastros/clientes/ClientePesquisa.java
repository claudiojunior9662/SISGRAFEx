/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.cadastros.clientes;

import model.dao.ClienteDAO;
import entities.sisgrafex.Cliente;
import exception.EnvioExcecao;
import exception.TipoPessoaIncorretoException;
import entities.sisgrafex.Endereco;
import entities.sisgrafex.Contato;
import ui.cadastros.notas.FatFrame;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import model.dao.ContatoDAO;
import model.dao.EnderecoDAO;
import ui.controle.Controle;

/**
 *
 * @author claud
 */
public class ClientePesquisa extends javax.swing.JInternalFrame {

    /**
     * 1 - CADASTRO DE CLIENTES, 2 - NOTA DE CRÉDITO
     */
    private static byte tela = 0;
    private Cliente clienteSel = null;

    public static byte getTela() {
        return tela;
    }

    public static void setTela(byte tela) {
        ClientePesquisa.tela = tela;
    }

    private static ClientePesquisa cadastroClientes2PesquisaNovo;

    public static ClientePesquisa getInstancia() {
        return new ClientePesquisa();
    }

    /**
     * Creates new form CadastroClientes2PesquisaNovo
     */
    public ClientePesquisa() {
        initComponents();
        estado1();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPesquisa = new javax.swing.JTable();
        botaoSelecionar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        p1 = new javax.swing.JComboBox<>();
        p2 = new javax.swing.JComboBox<>();
        p3 = new javax.swing.JTextField();
        botaoPesquisar = new javax.swing.JButton();
        botaoMostrarUltimos = new javax.swing.JButton();

        setTitle("PESQUISA DE CLIENTES");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisar.png"))); // NOI18N

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tabelaPesquisa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "NOME", "FANTASIA", "CPF", "CNPJ", "ATIVIDADE", "CRÉDITO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tabelaPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaPesquisaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaPesquisa);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        botaoSelecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/confirma.png"))); // NOI18N
        botaoSelecionar.setText("SELECIONAR");
        botaoSelecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSelecionarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        p1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE...", "PESSOA FÍSICA", "PESSOA JURÍDICA" }));
        p1.setBorder(javax.swing.BorderFactory.createTitledBorder("PESQUISAR POR"));
        p1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                p1ItemStateChanged(evt);
            }
        });

        p2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                p2ItemStateChanged(evt);
            }
        });

        botaoPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/pesquisar.png"))); // NOI18N
        botaoPesquisar.setText("PESQUISAR");
        botaoPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoPesquisarActionPerformed(evt);
            }
        });

        botaoMostrarUltimos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/ultimos.png"))); // NOI18N
        botaoMostrarUltimos.setText("MOSTRAR 45 ÚLTIMOS");
        botaoMostrarUltimos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoMostrarUltimosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoPesquisar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoMostrarUltimos)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoPesquisar)
                    .addComponent(botaoMostrarUltimos)
                    .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {botaoMostrarUltimos, botaoPesquisar, p1, p2, p3});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botaoSelecionar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botaoSelecionar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabelaPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaPesquisaMouseClicked
        botaoSelecionar.setEnabled(true);
    }//GEN-LAST:event_tabelaPesquisaMouseClicked

    private void botaoSelecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSelecionarActionPerformed
        try {
            ClienteCadastro.CODIGO_CLIENTE
                    = Integer.valueOf(tabelaPesquisa.getValueAt(tabelaPesquisa.getSelectedRow(), 0).toString());

            switch (p1.getSelectedIndex()) {
                case 1:
                    ClienteCadastro.TIPO_PESSOA = 1;
                    break;
                case 2:
                    ClienteCadastro.TIPO_PESSOA = 2;
                    break;
                default:
                    JOptionPane.showMessageDialog(
                            null,
                            "SELECIONE UMA OPÇÃO NO PRIMEIRO CAMPO E TENTE NOVAMENTE",
                            "ERRO",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
            }

            switch (getTela()) {
                case 1:
                    //INFORMAÇÕES CLIENTE---------------------------------------
                    clienteSel = ClienteDAO.selecionaInformacoes(ClienteCadastro.TIPO_PESSOA,
                            ClienteCadastro.CODIGO_CLIENTE);

                    switch (ClienteCadastro.TIPO_PESSOA) {
                        case 1:
                            ClienteCadastro.pessoaFisica.setSelected(true);
                            ClienteCadastro.pessoaJuridica.setSelected(false);
                            ClienteCadastro.codigo.setText(String.valueOf(ClienteCadastro.CODIGO_CLIENTE));
                            ClienteCadastro.nomeCliente.setText(clienteSel.getNome());
                            ClienteCadastro.cpf.setText(clienteSel.getCpf().replace(".", "").replace("-", ""));
                            ClienteCadastro.atividade.setText(clienteSel.getAtividade());
                            ClienteCadastro.codigoAtendente.setText(clienteSel.getCodigoAtendente());
                            ClienteCadastro.nomeAtendente.setText(clienteSel.getNomeAtendente());
                            ClienteCadastro.observacoes.setText(clienteSel.getObservacoes());
                            ClienteCadastro.creditoDisponivel.setValue(clienteSel.getCredito());
                            break;
                        case 2:
                            ClienteCadastro.pessoaJuridica.setSelected(true);
                            ClienteCadastro.pessoaFisica.setSelected(false);
                            ClienteCadastro.codigo.setText(String.valueOf(ClienteCadastro.CODIGO_CLIENTE));
                            ClienteCadastro.nomeCliente.setText(clienteSel.getNome());
                            ClienteCadastro.nomeFantasia.setText(clienteSel.getNomeFantasia());
                            ClienteCadastro.cnpj.setText(clienteSel.getCnpj().replace(".", "").replace("-", "").replace("/", ""));
                            ClienteCadastro.atividade.setText(clienteSel.getAtividade());
                            ClienteCadastro.filialColigada.setText(clienteSel.getFilialColigada());
                            ClienteCadastro.codigoAtendente.setText(clienteSel.getCodigoAtendente());
                            ClienteCadastro.nomeAtendente.setText(clienteSel.getNomeAtendente());
                            ClienteCadastro.observacoes.setText(clienteSel.getObservacoes());
                            ClienteCadastro.creditoDisponivel.setValue(clienteSel.getCredito());
                            break;
                        default:
                            throw new TipoPessoaIncorretoException();
                    }

                    //ENDERECOS-------------------------------------------------
                    DefaultTableModel modeloEnderecos = (DefaultTableModel) ClienteCadastro.tabelaEnderecos.getModel();
                    modeloEnderecos.setNumRows(0);
                    List retorno = EnderecoDAO.selecionaEnderecos(ClienteCadastro.TIPO_PESSOA,
                            ClienteCadastro.CODIGO_CLIENTE);
                    for (Endereco cadastroClientes2EnderecosBEAN : EnderecoDAO.retornaEnderecos(retorno)) {
                        String cep = null;
                        MaskFormatter mascaraCep = new MaskFormatter("##.###-###");
                        mascaraCep.setValueContainsLiteralCharacters(false);
                        cep = mascaraCep.valueToString(cadastroClientes2EnderecosBEAN.getCep().replace(".", "").replace("-", ""));

                        modeloEnderecos.addRow(new Object[]{
                            cadastroClientes2EnderecosBEAN.getCodigo(),
                            cadastroClientes2EnderecosBEAN.getTipoEndereco(),
                            cep,
                            cadastroClientes2EnderecosBEAN.getLogadouro(),
                            cadastroClientes2EnderecosBEAN.getCidade(),
                            cadastroClientes2EnderecosBEAN.getBairro(),
                            cadastroClientes2EnderecosBEAN.getUf(),
                            cadastroClientes2EnderecosBEAN.getComplemento()
                        });
                    }

                    //CONTATOS--------------------------------------------------
                    DefaultTableModel modeloContatos = (DefaultTableModel) ClienteCadastro.tabelaContatos.getModel();
                    modeloContatos.setNumRows(0);
                    retorno = ContatoDAO.selecionaContatos(ClienteCadastro.TIPO_PESSOA, ClienteCadastro.CODIGO_CLIENTE);
                    for (Contato auxBEAN : ClienteDAO.retornaDescricaoContatos(retorno)) {
                        modeloContatos.addRow(new Object[]{
                            auxBEAN.getCod(),
                            auxBEAN.getNomeContato(),
                            auxBEAN.getEmail(),
                            auxBEAN.getTelefone(),
                            auxBEAN.getRamal(),
                            auxBEAN.getTelefone2(),
                            auxBEAN.getRamal2(),
                            auxBEAN.getDepartamento()
                        });
                    }
                    ClienteCadastro.estadoPosPesquisar();
                    ClienteCadastro.tabelaContatos.setEnabled(true);
                    ClienteCadastro.tabelaEnderecos.setEnabled(true);

                    //CARREGA ORÇAMENTOS----------------------------------------
                    break;
                case 2:
                    //INFORMAÇÕES CLIENTE-----------------------------------------------
                    Cliente cliente = ClienteDAO.selInfoNota(ClienteCadastro.TIPO_PESSOA,
                            ClienteCadastro.CODIGO_CLIENTE);
                    if (ClienteCadastro.TIPO_PESSOA == 1) {
                        FatFrame.tipoCliente.setText("PESSOA FÍSICA");
                        FatFrame.cnpjCpf.setText(cliente.getCpf());
                    }
                    if (ClienteCadastro.TIPO_PESSOA == 2) {
                        FatFrame.tipoCliente.setText("PESSOA JURÍDICA");
                        FatFrame.cnpjCpf.setText(cliente.getCnpj());
                    }
                    FatFrame.codigoCliente.setValue(cliente.getCodigo());
                    FatFrame.nomeCliente.setText(cliente.getNome());

                    //ENDEREÇOS--------------------------------------------------
                    retorno = EnderecoDAO.selecionaEnderecos(ClienteCadastro.TIPO_PESSOA, ClienteCadastro.CODIGO_CLIENTE);
                    for (Endereco auxBEAN : EnderecoDAO.retornaEnderecos(retorno)) {
                        String cep = null;
                        MaskFormatter mascaraCep = new MaskFormatter("##.###-###");
                        mascaraCep.setValueContainsLiteralCharacters(false);
                        cep = mascaraCep.valueToString(auxBEAN.getCep());
                        FatFrame.bairroCliente.setText(auxBEAN.getBairro());
                        FatFrame.cidadeCliente.setText(auxBEAN.getCidade());
                        FatFrame.ufCliente.setText(auxBEAN.getUf());
                        FatFrame.complementoCliente.setText(auxBEAN.getComplemento());
                        FatFrame.logadouroCliente.setText(auxBEAN.getLogadouro());
                        FatFrame.cepCliente.setText(cep);
                        FatFrame.tipoEndereco.setText(auxBEAN.getTipoEndereco());
                    }
                    break;
            }
        } catch (SQLException | TipoPessoaIncorretoException | ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        this.dispose();
    }//GEN-LAST:event_botaoSelecionarActionPerformed

    private void p1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_p1ItemStateChanged
        if (p1.getSelectedItem().toString().equals("SELECIONE...") == false) {
            p2.removeAllItems();
            p2.addItem("SELECIONE...");
            if (p1.getSelectedItem().toString().equals("PESSOA FÍSICA")) {
                p2.addItem("CÓDIGO");
                p2.addItem("NOME");
                p2.addItem("CPF");
                p2.addItem("ATIVIDADE");
            }
            if (p1.getSelectedItem().toString().equals("PESSOA JURÍDICA")) {
                p2.addItem("CÓDIGO");
                p2.addItem("NOME");
                p2.addItem("NOME FANTASIA");
                p2.addItem("CNPJ");
                p2.addItem("ATIVIDADE");
            }
            p2.setEnabled(true);
            botaoMostrarUltimos.setEnabled(true);
        } else {
            p2.setEnabled(false);
            botaoMostrarUltimos.setEnabled(false);
        }
    }//GEN-LAST:event_p1ItemStateChanged

    private void p2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_p2ItemStateChanged
        if (p2.getItemCount() != 0) {
            if (p2.getSelectedItem().toString().equals("SELECIONE...") == false) {
                if (p2.getSelectedItem().toString().equals("CPF") || p2.getSelectedItem().toString().equals("CNPJ") || p2.getSelectedItem().toString().equals("CÓDIGO")) {
                    p3.setToolTipText("SOMENTE NÚMEROS");
                } else {
                    p3.setToolTipText("DIGITE A PESQUISA AQUI");
                }
                p3.setEnabled(true);
            } else {
                p3.setEnabled(false);
            }
        }
    }//GEN-LAST:event_p2ItemStateChanged

    private void botaoPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoPesquisarActionPerformed
        try {
            DefaultTableModel modeloPesquisa = (DefaultTableModel) tabelaPesquisa.getModel();

            modeloPesquisa.setNumRows(0);

            MaskFormatter mascaraCnpj = null;
            MaskFormatter mascaraCpf = null;
            mascaraCnpj = new MaskFormatter("##.###.###/####-##");
            mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCnpj.setValueContainsLiteralCharacters(false);
            mascaraCpf.setValueContainsLiteralCharacters(false);

            String p3Txt = null;
            switch (p2.getSelectedItem().toString()) {
                case "CPF":
                    p3Txt = mascaraCpf.valueToString(p3.getText()
                            .replace(".", "")
                            .replace(" ", "")
                            .replace("-", ""));
                    break;
                case "CNPJ":
                    p3Txt = mascaraCnpj.valueToString(p3.getText().replace("/", "")
                            .replace(".", "")
                            .replace("-", "")
                            .replace(" ", ""));
                    break;
                default:
                    p3Txt = p3.getText();
                    break;
            }

            for (Cliente cliente : ClienteDAO.retornaPesquisa(p1.getSelectedItem().toString(), p2.getSelectedItem().toString(), p3Txt)) {
                modeloPesquisa.addRow(new Object[]{
                    cliente.getCodigo(),
                    cliente.getNome(),
                    cliente.getNomeFantasia(),
                    cliente.getCpf(),
                    cliente.getCnpj(),
                    cliente.getAtividade(),
                    cliente.getCredito()
                });
            }
        } catch (SQLException | ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_botaoPesquisarActionPerformed

    private void botaoMostrarUltimosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoMostrarUltimosActionPerformed
        try {
            DefaultTableModel modeloPesquisa = (DefaultTableModel) tabelaPesquisa.getModel();

            modeloPesquisa.setNumRows(0);

            for (Cliente cliente : ClienteDAO.mostraUltimos(p1.getSelectedItem().toString())) {
                modeloPesquisa.addRow(new Object[]{
                    cliente.getCodigo(),
                    cliente.getNome(),
                    cliente.getNomeFantasia(),
                    cliente.getCpf(),
                    cliente.getCnpj(),
                    cliente.getAtividade(),
                    cliente.getCredito()
                });
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_botaoMostrarUltimosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoMostrarUltimos;
    private javax.swing.JButton botaoPesquisar;
    private javax.swing.JButton botaoSelecionar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> p1;
    private javax.swing.JComboBox<String> p2;
    private javax.swing.JTextField p3;
    private javax.swing.JTable tabelaPesquisa;
    // End of variables declaration//GEN-END:variables
        public void estado1() {
        p1.setSelectedIndex(0);
        p2.removeAllItems();
        p2.setEnabled(false);
        p3.setEnabled(false);
        botaoMostrarUltimos.setEnabled(false);
        botaoSelecionar.setEnabled(false);
    }
}

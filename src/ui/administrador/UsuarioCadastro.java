/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.administrador;

import entities.sisgrafex.Usuario;
import model.dao.UsuarioDAO;
import exception.EnvioExcecao;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.tabelas.UsuarioTableModel;
import ui.controle.Controle;

/**
 *
 * @author claud
 */
public class UsuarioCadastro extends javax.swing.JInternalFrame {

    private static UsuarioCadastro usuarioCadastro;
    Usuario usuario;
    byte editando = 0;
    UsuarioTableModel model = new UsuarioTableModel();

    public static UsuarioCadastro getInstancia() {
        return new UsuarioCadastro();
    }

    /**
     * Creates new form CadastroFuncionariosNovo
     */
    public UsuarioCadastro() {
        initComponents();
        btnResetarSenha.setEnabled(false);
        btnEditar.setEnabled(false);
        btnAtivar.setEnabled(false);
        btnDesativar.setEnabled(false);

        carregaLista(null, null, null);
        resetaAcessos();
        tblUsr.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jtfNomeUsr = new javax.swing.JTextField();
        jtfLoginUsr = new javax.swing.JTextField();
        jcbTipoUsr = new javax.swing.JComboBox<>();
        jftfCodigoUsr = new javax.swing.JFormattedTextField();
        btnCadastrar = new javax.swing.JButton();
        jpfSenhaUsr = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsr = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnDesativar = new javax.swing.JButton();
        btnAtivar = new javax.swing.JButton();
        btnResetarSenha = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAcessos = new javax.swing.JTable();

        setTitle("CADASTRO DE ATENDENTES");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jtfNomeUsr.setBorder(javax.swing.BorderFactory.createTitledBorder("NOME"));

        jtfLoginUsr.setBorder(javax.swing.BorderFactory.createTitledBorder("LOGIN"));
        jtfLoginUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfLoginUsrFocusLost(evt);
            }
        });
        jtfLoginUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfLoginUsrActionPerformed(evt);
            }
        });

        jcbTipoUsr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECIONE...", "ADMINISTRADOR", "USUÁRIO" }));
        jcbTipoUsr.setBorder(javax.swing.BorderFactory.createTitledBorder("TIPO"));
        jcbTipoUsr.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbTipoUsrItemStateChanged(evt);
            }
        });
        jcbTipoUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTipoUsrActionPerformed(evt);
            }
        });

        jftfCodigoUsr.setBorder(javax.swing.BorderFactory.createTitledBorder("CÓDIGO"));
        try {
            jftfCodigoUsr.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("???")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfCodigoUsr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jftfCodigoUsrFocusLost(evt);
            }
        });

        btnCadastrar.setText("CADASTRAR");
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        jpfSenhaUsr.setBorder(javax.swing.BorderFactory.createTitledBorder("SENHA"));

        tblUsr.setAutoCreateRowSorter(true);
        tblUsr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME", "CÓDIGO", "LOGIN", "TIPO", "ORC", "PROD", "EXP", "FIN", "EST", "ATIVO", "ULT LOGIN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUsr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsrMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsr);

        btnEditar.setText("EDITAR");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDesativar.setText("DESATIVAR");
        btnDesativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesativarActionPerformed(evt);
            }
        });

        btnAtivar.setText("ATIVAR");
        btnAtivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtivarActionPerformed(evt);
            }
        });

        btnResetarSenha.setText("RESETAR SENHA");
        btnResetarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetarSenhaActionPerformed(evt);
            }
        });

        tblAcessos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"USUÁRIO", null, null, null, null, null, null},
                {"ADMIN", null, null, null, null,  new Boolean(false), null}
            },
            new String [] {
                "TIPO ACESSO", "ORÇAMENTO", "PRODUÇÃO", "EXPEDIÇÃO", "FINANCEIRO", "ESTOQUE", "ORD DESPESAS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblAcessos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfNomeUsr, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jftfCodigoUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfLoginUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jpfSenhaUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbTipoUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnResetarSenha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtivar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDesativar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCadastrar)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jpfSenhaUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbTipoUsr, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfLoginUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jftfCodigoUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jtfNomeUsr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrar)
                    .addComponent(btnEditar)
                    .addComponent(btnDesativar)
                    .addComponent(btnAtivar)
                    .addComponent(btnResetarSenha))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jcbTipoUsr, jftfCodigoUsr, jpfSenhaUsr, jtfLoginUsr, jtfNomeUsr});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcbTipoUsrItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbTipoUsrItemStateChanged

    }//GEN-LAST:event_jcbTipoUsrItemStateChanged

    private void jcbTipoUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTipoUsrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbTipoUsrActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        try {
            usuario = new Usuario();

            /**
             * Verifica se todos os campos foram preenchidos corretamente
             */
            if (jtfNomeUsr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "INSIRA O NOME DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }
            if (jftfCodigoUsr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "INSIRA O CÓDIGO DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }
            if (jtfLoginUsr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "INSIRA O LOGIN DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }
            if (jcbTipoUsr.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "SELECIONE O TIPO DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }

            /**
             * Preenche as informações do usuário
             */
            usuario.setNome(jtfNomeUsr.getText().toUpperCase());
            usuario.setCodigo(jftfCodigoUsr.getText().toUpperCase());
            usuario.setLogin(jtfLoginUsr.getText().toUpperCase());
            if (jpfSenhaUsr.getText().isEmpty()) {
                usuario.setSenha(jtfLoginUsr.getText().toLowerCase());
            } else {
                usuario.setSenha(jpfSenhaUsr.getText());
            }
            usuario.setUltMudSenha(new Date());
            switch (jcbTipoUsr.getSelectedIndex()) {
                case 1:
                    usuario.setTipo(jcbTipoUsr.getSelectedItem().toString());
                    break;
                case 2:
                    usuario.setTipo(jcbTipoUsr.getSelectedItem().toString());
                    break;
            }

            /**
             * Preenche as informações de acessos do usuário
             */
            System.out.println(Boolean.valueOf(tblAcessos.getValueAt(0, 1).toString()));
            System.out.println(Boolean.valueOf(tblAcessos.getValueAt(1, 1).toString()));

            usuario.setAcessoOrc(Boolean.valueOf(tblAcessos.getValueAt(0, 1).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 1).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoOrcAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 1).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoProd(Boolean.valueOf(tblAcessos.getValueAt(0, 2).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 2).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoProdAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 2).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoExp(Boolean.valueOf(tblAcessos.getValueAt(0, 3).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 3).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoExpAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 3).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoFin(Boolean.valueOf(tblAcessos.getValueAt(0, 4).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 4).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoFinAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 4).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoEst(Boolean.valueOf(tblAcessos.getValueAt(0, 5).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 5).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoOrd(Boolean.valueOf(tblAcessos.getValueAt(0, 6).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 6).toString()) ? (byte) 1 : (byte) 0);

            /**
             * Insere o usuário no banco de dados
             */
            UsuarioDAO.cria(usuario);

            /**
             * Exibe mensagem para o usuário
             */
            JOptionPane.showMessageDialog(
                    null,
                    "O USUÁRIO FOI CADASTRADO COM SUCESSO",
                    "CONFIRMAÇÃO",
                    1
            );
            limpa();
            carregaLista(null, null, null);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void tblUsrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsrMouseClicked
        usuario = model.getValueAt(tblUsr.getSelectedRow());
        resetaAcessos();
        editando = 1;

        jtfNomeUsr.setText(usuario.getNome());
        jftfCodigoUsr.setText(usuario.getCodigo());
        jtfLoginUsr.setText(usuario.getLogin());
        jcbTipoUsr.setSelectedItem(usuario.getTipo());

        btnResetarSenha.setEnabled(true);
        btnEditar.setEnabled(true);
        btnAtivar.setEnabled(true);
        btnDesativar.setEnabled(true);

        if (usuario.getAcessoOrc() == 1) {
            if (usuario.getAcessoOrcAdm() == 1) {
                tblAcessos.setValueAt(true, 1, 1);
            } else {
                tblAcessos.setValueAt(true, 0, 1);
            }
        }
        if (usuario.getAcessoProd() == 1) {
            if (usuario.getAcessoProdAdm() == 1) {
                tblAcessos.setValueAt(true, 1, 2);
            } else {
                tblAcessos.setValueAt(true, 0, 2);
            }
        }
        if (usuario.getAcessoExp() == 1) {
            if (usuario.getAcessoExpAdm() == 1) {
                tblAcessos.setValueAt(true, 1, 3);
            } else {
                tblAcessos.setValueAt(true, 0, 3);
            }
        }
        if (usuario.getAcessoFin() == 1) {
            if (usuario.getAcessoFinAdm() == 1) {
                tblAcessos.setValueAt(true, 1, 4);
            } else {
                tblAcessos.setValueAt(true, 0, 4);
            }
        }
        if (usuario.getAcessoEst() == 1) {
            tblAcessos.setValueAt(true, 0, 5);
        }
        if (usuario.getAcessoOrd() == 1) {
            tblAcessos.setValueAt(true, 0, 6);
        }
    }//GEN-LAST:event_tblUsrMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            
            /**
             * Verifica se todos os campos foram preenchidos corretamente
             */
            if (jtfNomeUsr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "INSIRA O NOME DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }
            if (jftfCodigoUsr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "INSIRA O CÓDIGO DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }
            if (jtfLoginUsr.getText().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "INSIRA O LOGIN DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }
            if (jcbTipoUsr.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "SELECIONE O TIPO DO USUÁRIO",
                        "ERRO",
                        0
                );
                return;
            }

            /**
             * Preenche as informações do usuário
             */
            usuario.setNome(jtfNomeUsr.getText().toUpperCase());
            usuario.setCodigo(jftfCodigoUsr.getText().toUpperCase());
            usuario.setLogin(jtfLoginUsr.getText().toUpperCase());
            if (jpfSenhaUsr.getText().isEmpty()) {
                usuario.setSenha(jtfLoginUsr.getText().toLowerCase());
            } else {
                usuario.setSenha(jpfSenhaUsr.getText());
            }
            usuario.setUltMudSenha(new Date());
            switch (jcbTipoUsr.getSelectedIndex()) {
                case 1:
                    usuario.setTipo(jcbTipoUsr.getSelectedItem().toString());
                    break;
                case 2:
                    usuario.setTipo(jcbTipoUsr.getSelectedItem().toString());
                    break;
            }

            /**
             * Preenche as informações de acessos do usuário
             */

            usuario.setAcessoOrc(Boolean.valueOf(tblAcessos.getValueAt(0, 1).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 1).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoOrcAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 1).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoProd(Boolean.valueOf(tblAcessos.getValueAt(0, 2).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 2).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoProdAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 2).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoExp(Boolean.valueOf(tblAcessos.getValueAt(0, 3).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 3).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoExpAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 3).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoFin(Boolean.valueOf(tblAcessos.getValueAt(0, 4).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 4).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoFinAdm(Boolean.valueOf(tblAcessos.getValueAt(1, 4).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoEst(Boolean.valueOf(tblAcessos.getValueAt(0, 5).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 5).toString()) ? (byte) 1 : (byte) 0);
            usuario.setAcessoOrd(Boolean.valueOf(tblAcessos.getValueAt(0, 6).toString())
                    | Boolean.valueOf(tblAcessos.getValueAt(1, 6).toString()) ? (byte) 1 : (byte) 0);

            /**
             * Insere o usuário no banco de dados
             */
            UsuarioDAO.atualiza(usuario);

            /**
             * Exibe mensagem para o usuário
             */
            JOptionPane.showMessageDialog(
                    null,
                    "O USUÁRIO FOI ATUALIZADO COM SUCESSO",
                    "CONFIRMAÇÃO",
                    1
            );
            limpa();
            carregaLista(null, null, null);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDesativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesativarActionPerformed
        try {
            UsuarioDAO.ativaDesativa(0, (String) tblUsr.getValueAt(tblUsr.getSelectedRow(), 1));
            JOptionPane.showMessageDialog(
                    null,
                    "ATENDENTE DESATIVADO COM SUCESSO",
                    "CONFIRMAÇÃO",
                    JOptionPane.INFORMATION_MESSAGE
            );
            carregaLista(null, null, null);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_btnDesativarActionPerformed

    private void btnAtivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtivarActionPerformed
        try {
            UsuarioDAO.ativaDesativa(1, (String) tblUsr.getValueAt(tblUsr.getSelectedRow(), 1));
            JOptionPane.showMessageDialog(
                    null,
                    "ATENDENTE ATIVADO COM SUCESSO",
                    "CONFIRMAÇÃO",
                    JOptionPane.INFORMATION_MESSAGE
            );
            carregaLista(null, null, null);
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_btnAtivarActionPerformed

    private void btnResetarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetarSenhaActionPerformed
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "A SENHA SERÁ O LOGIN DO ATENDENTE COM LETRAS MINÚSCULAS", "ATENÇÃO!", dialogButton);
        if (dialogResult != 0) {
            return;
        } else {
            if (tblUsr.getValueAt(tblUsr.getSelectedRow(), 2).toString().equals("")
                    | tblUsr.getValueAt(tblUsr.getSelectedRow(), 2) == null) {
                JOptionPane.showMessageDialog(null, "SELECIONE UM ATENDENTE PARA CONTINUAR", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                UsuarioDAO.resetaSenha(tblUsr.getValueAt(tblUsr.getSelectedRow(), 2).toString().toLowerCase());
                JOptionPane.showMessageDialog(null, "SENHA RESETADA COM SUCESSO", "CONFIRMAÇÃO", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                EnvioExcecao.envio(null);
            }
        }
    }//GEN-LAST:event_btnResetarSenhaActionPerformed

    private void jftfCodigoUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfCodigoUsrFocusLost
        try {
            if (UsuarioDAO.verificaCodEx(jftfCodigoUsr.getText()) & editando == 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "O CÓDIGO DIGITADO JÁ EXISTE",
                        "ERRO",
                        0);
                jftfCodigoUsr.setText("");
                jftfCodigoUsr.requestFocus();
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_jftfCodigoUsrFocusLost

    private void jtfLoginUsrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfLoginUsrFocusLost
        try {
            if (UsuarioDAO.verificaLoginEx(jtfLoginUsr.getText()) & editando == 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "O LOGIN DIGITADO JÁ EXISTE",
                        "ERRO",
                        0);
                jtfLoginUsr.setText("");
                jtfLoginUsr.requestFocus();
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }//GEN-LAST:event_jtfLoginUsrFocusLost

    private void jtfLoginUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfLoginUsrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfLoginUsrActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtivar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnDesativar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnResetarSenha;
    public static javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JComboBox<String> jcbTipoUsr;
    public static javax.swing.JFormattedTextField jftfCodigoUsr;
    public static javax.swing.JPasswordField jpfSenhaUsr;
    public static javax.swing.JTextField jtfLoginUsr;
    public static javax.swing.JTextField jtfNomeUsr;
    private javax.swing.JTable tblAcessos;
    private javax.swing.JTable tblUsr;
    // End of variables declaration//GEN-END:variables
 public void limpa() {
        jtfNomeUsr.setText("");
        jpfSenhaUsr.setText("");
        jftfCodigoUsr.setText("");
        jtfLoginUsr.setText("");
        jcbTipoUsr.setSelectedIndex(0);
        resetaAcessos();
        editando = 0;
    }

    public void carregaLista(String tipo, String tipoAux, String texto) {
        try {
            model.setNumRows(0);

            if (tipo == null && tipoAux == null && texto == null) {
                for (Usuario usuario : UsuarioDAO.carregaLista()) {
                    model.addRow(usuario);
                }
            } else {
                for (Usuario usuario
                        : UsuarioDAO.retornaPesquisa(tipo, tipoAux, texto)) {
                    model.addRow(usuario);
                }
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }

    private void resetaAcessos() {
        DefaultTableModel modeloAcessos = (DefaultTableModel) tblAcessos.getModel();
        modeloAcessos.setNumRows(0);
        modeloAcessos.addRow(new Object[]{
            "USUÁRIO",
            false,
            false,
            false,
            false,
            false,
            false
        });
        modeloAcessos.addRow(new Object[]{
            "ADMIN",
            false,
            false,
            false,
            false,
            false,
            false
        });

        modeloAcessos.setValueAt(false, 0, 1);
        modeloAcessos.setValueAt(false, 0, 2);
        modeloAcessos.setValueAt(false, 0, 3);
        modeloAcessos.setValueAt(false, 0, 4);
        modeloAcessos.setValueAt(false, 0, 5);
        modeloAcessos.setValueAt(false, 0, 6);

        modeloAcessos.setValueAt(false, 1, 1);
        modeloAcessos.setValueAt(false, 1, 2);
        modeloAcessos.setValueAt(false, 1, 3);
        modeloAcessos.setValueAt(false, 1, 4);
        modeloAcessos.setValueAt(false, 1, 5);
        modeloAcessos.setValueAt(false, 1, 6);
    }

}

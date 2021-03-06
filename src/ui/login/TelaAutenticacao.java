/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.login;

import model.dao.LoginDAO;
import exception.EnvioExcecao;
import exception.UsuarioSenhaIncorretosException;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import ui.principal.ModulosInt;
import model.dao.OrcamentoDAO;
import entities.sisgrafex.Usuario;
import model.dao.UsuarioDAO;
import model.dao.ClienteDAO;
import ui.controle.Controle;
import ui.principal.ModulosExt;
import java.sql.SQLException;
import model.dao.OrdemProducaoDAO;

/**
 *
 * @author Seçao SPD3
 */
public class TelaAutenticacao extends javax.swing.JFrame {
    
    private static final String codVersao = "2.7.0";
    private static final String update = "19";
    private static Usuario atendenteLogado;

    public static Usuario getUsrLogado() {
        return atendenteLogado;
    }

    public static void setAtendenteLogado(Usuario atendenteLogado) {
        TelaAutenticacao.atendenteLogado = atendenteLogado;
    }

    public static String getCodVersao() {
        return codVersao;
    }

    public static String getUpdate() {
        return update;
    }

    public TelaAutenticacao() {
        initComponents();

        URL url = this.getClass().getResource("logo.png");
        java.awt.Image imagemLogo = Toolkit.getDefaultToolkit().getImage(url);
        this.setIconImage(imagemLogo);

        lblVersao.setText("v. " + codVersao + " " + update);
        this.setLocationRelativeTo(null);
        this.setTitle("AUTENTICAÇÃO - PROJETO BALA DE PRATA");

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
        jPanel2 = new javax.swing.JPanel();
        campoUsuario = new javax.swing.JTextField();
        campoSenha = new javax.swing.JPasswordField();
        lblVersao = new javax.swing.JLabel();
        lblEntrar = new javax.swing.JLabel();
        lblEsqueciSenha = new javax.swing.JLabel();
        lblSobre = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        fechar = new javax.swing.JLabel();
        minimizar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(100, 141, 174));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        campoUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "USUÁRIO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        campoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoUsuarioActionPerformed(evt);
            }
        });

        campoSenha.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SENHA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        lblVersao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblVersao.setForeground(new java.awt.Color(255, 255, 255));
        lblVersao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lblEntrar.setBackground(new java.awt.Color(0, 153, 51));
        lblEntrar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEntrar.setForeground(new java.awt.Color(255, 255, 255));
        lblEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/entrar_sair_branco.png"))); // NOI18N
        lblEntrar.setText("ENTRAR");
        lblEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEntrar.setOpaque(true);
        lblEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEntrarMouseClicked(evt);
            }
        });

        lblEsqueciSenha.setBackground(new java.awt.Color(51, 51, 255));
        lblEsqueciSenha.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEsqueciSenha.setForeground(new java.awt.Color(255, 255, 255));
        lblEsqueciSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/interrogacao_branco.png"))); // NOI18N
        lblEsqueciSenha.setText("ESQUECI MINHA SENHA");
        lblEsqueciSenha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEsqueciSenha.setOpaque(true);
        lblEsqueciSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEsqueciSenhaMouseClicked(evt);
            }
        });

        lblSobre.setBackground(new java.awt.Color(102, 102, 102));
        lblSobre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSobre.setForeground(new java.awt.Color(255, 255, 255));
        lblSobre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/sobre_branco.png"))); // NOI18N
        lblSobre.setText("SOBRE O PROGRAMA");
        lblSobre.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSobre.setOpaque(true);
        lblSobre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSobreMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblVersao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(campoUsuario)
                            .addComponent(campoSenha)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblEsqueciSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblSobre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(campoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(campoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEsqueciSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEntrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSobre, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblEntrar, lblEsqueciSenha, lblSobre});

        jPanel3.setBackground(new java.awt.Color(100, 141, 174));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(74, 74, 6));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ui/principal/brasaoGrafica-133x191.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(100, 141, 174));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText(" AUTENTICAÇÃO - SISTEMA DE PRODUÇÃO GRÁFICA");

        fechar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/fechar.png"))); // NOI18N
        fechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fechar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                fecharMouseMoved(evt);
            }
        });
        fechar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fecharMouseClicked(evt);
            }
        });

        minimizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        minimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/minimizar.png"))); // NOI18N
        minimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minimizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(minimizar)
                        .addComponent(fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fechar, minimizar});

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoUsuarioActionPerformed

    private void fecharMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fecharMouseClicked
        System.exit(0);
    }//GEN-LAST:event_fecharMouseClicked

    private void minimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizarMouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizarMouseClicked

    private void fecharMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fecharMouseMoved

    }//GEN-LAST:event_fecharMouseMoved

    private void lblEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntrarMouseClicked
        try {
            lblEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            ModulosInt mInt;
            ModulosExt mExt;
            LoginDAO loginDAO = new LoginDAO();
            String senha = campoSenha.getText();
            
            if (campoUsuario.getText().matches("[0-9]*")) {
                if (ClienteDAO.autenticaCliente(Integer.valueOf(campoUsuario.getText()), campoSenha.getText())) {
                    atendenteLogado = new Usuario(
                            "ODC",
                            campoUsuario.getText(),
                            ClienteDAO.retornaNomeCliente(Integer.valueOf(campoUsuario.getText()), (byte) 2),
                            "USUARIO"
                    );
                    ClienteDAO.atualizaClientes(Integer.valueOf(campoUsuario.getText()));
                    mExt = new ModulosExt();
                    mExt.setLocationRelativeTo(null);
                    mExt.setDefaultCloseOperation(ModulosInt.EXIT_ON_CLOSE);
                    mExt.setTitle("MÓDULOS");
                    mExt.setVisible(true);
                    this.setVisible(false);
                    Controle.stsOrcamento = OrcamentoDAO.retornaStsOrcamento();
                } else {
                    throw new UsuarioSenhaIncorretosException();
                }

            } else if (loginDAO.verificaNome(campoUsuario.getText().toUpperCase(), campoSenha.getText())) {
                atendenteLogado = UsuarioDAO.retornaInfoUsr(campoUsuario.getText().toUpperCase(), campoSenha.getText());
//                if (loginDAO.verificaExpiracaoSenha(atendenteLogado.getCodigo())
//                        && !campoUsuario.getText().equals("admin")) {
//                    JOptionPane.showMessageDialog(null, "SUA SENHA EXPIROU.\nO SR(A) SERÁ REDIRECIONADO PARA A MUDANÇA DE SENHA.");
//                    MudancaSenha md = new MudancaSenha();
//                    md.setLocationRelativeTo(null);
//                    md.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//                    md.setTitle("MUDANÇA DE SENHA");
//                    MudancaSenha.SENHA = senha;
//                    md.setVisible(true);
                if (Controle.getTipoLogin() == 1) {
                    Controle.inicializa();
                    mInt = new ModulosInt();
                    mInt.setLocationRelativeTo(null);
                    mInt.setDefaultCloseOperation(ModulosInt.EXIT_ON_CLOSE);
                    mInt.setTitle("MÓDULOS");
                    mInt.setVisible(true);
                    this.setVisible(false);

                    Controle.avisosUsuario((byte) 2, "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 9 (08-09-2021)\n"
                            + "- Correção da consulta de notas de crédito;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 10 (13-09-2021)\n"
                            + "- Correção da alteração do valor unitário dos produtos a pronta entrega;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 11 (13-09-2021)\n"
                            + "- Ajuste do relatório de orçamentos (Agrupamento e somatório);\n"
                            + "- Ajuste do relatório de detalhamento de cliente (somatório das propostas de orçamento)\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 12 (16-09-2021)\n"
                            + "- Ajuste na definição de datas no módulo produção;\n\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 13 (19-09-2021)\n"
                            + "- Adição dos status das OP 14 - GRAVAÇÃO DE CHAPAS e 15 - ESPERANDO ARQUIVO;\n"
                            + "- Verificação na seleção de datas no módulo produção;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 14 (21-09-2021)\n"
                            + "- Testes e verificações da integração com a Loja Integrada (2/5) - Status: Pronto;\n"
                            + "- Ajuste no botão de observações da OP, na tela de controle de OP;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 15 (23-09-2021)\n"
                            + "- Ajuste na alteração da seleção das datas no módulo Produção;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 16 (24-09-2021)\n"
                            + "- Testes e verificações da integração com a Loja Integrada (2/5) - Status: Pronto;\n"
                            + "- Inclusão da coluna de código do ecommerce na consulta de orçamentos;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 17 (27-09-2021)\n"
                            + "- Correção na pesquisa dos orçamentos;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 18 (30-09-2021)\n"
                            + "- Adição do tipo de trabalho 'PLOTTER' no controle da Produção;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.0 19 (11-11-2021)\n"
                            + "- Ajuste no upload dos arquivos;\n"
                            + "CORREÇÕES E ATUALIZAÇÕES DA v.2.7.1 1 (16-11-2021)\n"
                            + "- Implementação do contador de cliques para a impressão digital;\n\n"
                            + "AVISOS:\n"
                            + "- Todas as sugestões de melhoria de usabilidade do programa serão estudadas, mas não há previsão de implementação;\n"
                            + "- Qualquer problema nas correções acima expostas deverão ser informados o mais rápido possível à seção;\n"
                            + "Seç Infor - Gráfica do Exército - Missão de Grandeza, Servir!");
                    Controle.stsOrcamento = OrcamentoDAO.retornaStsOrcamento();
                    Controle.stsOp = OrdemProducaoDAO.retornaStsOp();
                    Controle.criaDiretorio(Controle.TEMP_DIR);
                } else {
                    this.dispose();
                }
            }else{
                lblEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        lblEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_lblEntrarMouseClicked

    private void lblEsqueciSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEsqueciSenhaMouseClicked
        JOptionPane.showMessageDialog(null,
                "O SR(A) DEVERÁ CONTATAR A SEÇÃO DE PROCESSAMENTO DE DADOS\nPARA FAZER A ALTERAÇÃO DA SENHA.",
                "ESQUECI A SENHA",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lblEsqueciSenhaMouseClicked

    private void lblSobreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSobreMouseClicked
        Sobre sobre = new Sobre();
        sobre.setLocationRelativeTo(null);
        sobre.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        sobre.setVisible(true);
    }//GEN-LAST:event_lblSobreMouseClicked

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
                if (System.getProperty("os.name").contains("Windows")) {
                    if ("Metal".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                } else {
                    if ("Metal".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaAutenticacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAutenticacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAutenticacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAutenticacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaAutenticacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPasswordField campoSenha;
    public static javax.swing.JTextField campoUsuario;
    private javax.swing.JLabel fechar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblEntrar;
    private javax.swing.JLabel lblEsqueciSenha;
    private javax.swing.JLabel lblSobre;
    private javax.swing.JLabel lblVersao;
    private javax.swing.JLabel minimizar;
    // End of variables declaration//GEN-END:variables
}

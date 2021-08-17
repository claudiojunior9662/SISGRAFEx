/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controle;

import com.itextpdf.text.BaseColor;
import connection.ConnectionFactory;
import entities.sisgrafex.StsOp;
import entities.sisgrafex.StsOrcamento;
import exception.EnvioExcecao;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import ui.login.TelaAutenticacao;
import ui.principal.GerenteJanelas;

/**
 *
 * @author 1113778771
 */
public class Controle {

    /**
     * Define o tipo de acessos de acordo com o estado da aplicação
     *
     * @param tipoVersao 1 - produção 2 - desenvolvimento rede 3 -
     * desenvolvimento local 4 - peixoto
     */
    private static byte tipoVersao = 1;

    //VARIÁVEIS DE SISTEMA------------------------------------------------------
    //CONEXÃO COM SERVIDOR ARQUIVOS---------------------------------------------
    private String host;
    private String usuario;
    private String senha;
    private int porta;

    //CONTROLE DE ARQUIVOS------------------------------------------------------
    public static String ESTOQUE_NAME = "estoque";
    public static String TEMP_DIR = System.getProperty("java.io.tmpdir") + "SISGRAFEx";
    public static String SENHA_ESTOQUE = "JMW#3645";

    //VARIÁVEIS PARA CONEXÃO COM FIREBIRD (SIMATEX)-----------------------------
    final static public String DRIVER_FIREBIRD = "org.firebirdsql.jdbc.FBDriver";
    public static String CHARSET = "?encoding=ISO8859_1";
    public static String SERVIDOR = null;
    public static String PORTA = null;
    public static String USUARIO = null;
    public static String SENHA = null;
    public static String ENDERECO_BD = null;

    //VARIÁVEIS PARA INTEGRAÇÃO COM LOJA INTEGRADA------------------------------
    public static String CHAVE_APLICACAO = null;
    public static String CHAVE_API = null;
    public static String LINK_API = null;
    public static String VERSAO_API = null;
    public static String SEPARADOR = "/";
    public static String FORMATO_SAIDA = "json";
    public static Timestamp SYNC_PEDIDOS = null;

    //VARIÁVEIS PARA CONEXÃO INTERNET-------------------------------------------
    public static boolean USO_PROXY = false;
    public static String HOST_PROXY = null;
    public static int PORT_PROXY = 0;
    public static String USER_PROXY = null;
    public static String PASSWORD_PROXY = null;

    //ORÇAMENTOS E OP-----------------------------------------------------------
    public static BaseColor fundoDestaque = new BaseColor(211, 211, 211);
    public static Border bordaLinhaVermelha = BorderFactory.createLineBorder(Color.red);
    public static Border bordaLinhaVerde = BorderFactory.createLineBorder(Color.green);
    public static SimpleDateFormat dataPadrao = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat dataPadraoDiretorio = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat horaPadrao = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat horaPadraoDiretorio = new SimpleDateFormat("HH-mm-ss");
    public static SimpleDateFormat dataPadraoLojaIntegrada = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat horaPadraoLojaIntegrada = new SimpleDateFormat("HH:mm:ss");
    public static DecimalFormat formatoVlrPadrao = new DecimalFormat("###,##0.00");
    public static String tipoDesign = "";
    public static String urlTempWindows = System.getProperty("java.io.tmpdir") + "\\";
    public static String urlTempUnix = System.getProperty("java.io.tmpdir") + "/";
    public static List<StsOrcamento> stsOrcamento;
    public static List<StsOp> stsOp;

    //MENSAGENS PADRÃO----------------------------------------------------------
    public static String naoAdm = "VOCÊ PRECISA SER UM ADMINISTRADOR DESTE MÓDULO PARA ACESSAR ESTA FUNÇÃO";

    /**
     *
     * @param tipo 1 - ERRO, 2 - INFORMAÇÃO
     * @param mensagem
     */
    public static void avisosUsuario(byte tipo, String mensagem) {
        switch (tipo) {
            case 1:
                JOptionPane.showMessageDialog(null, mensagem, "ERRO", JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, mensagem, "INFORMAÇÃO", JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    public static byte getTipoVersao() {
        return tipoVersao;
    }

    public static void setTipoVersao(byte tipoVersao) {
        Controle.tipoVersao = tipoVersao;
    }

    /**
     * Define o tipo de login da tela de autenticação
     *
     * @param tipoLogin 1 - Primeiro, 2 - Alterar base
     */
    private static byte tipoLogin = 1;

    public static byte getTipoLogin() {
        return tipoLogin;
    }

    public static void setTipoLogin(byte tipoLogin) {
        Controle.tipoLogin = tipoLogin;
    }

    public static void sair(JFrame frame) {
        tipoLogin = 1;
        TelaAutenticacao ta = new TelaAutenticacao();
        ta.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ta.setLocationRelativeTo(null);
        ta.setVisible(true);
        ta.campoUsuario.setText(TelaAutenticacao.getUsrLogado().getLogin());
        frame.dispose();
    }

    public static void defineStatus(JTextPane status) {
        status.setEditable(false);
        status.setText("GRÁFICA DO EXÉRCITO | USUÁRIO: "
                + TelaAutenticacao.getUsrLogado().getNome()
                + " | BASE DE DADOS: "
                + Controle.getSelBanco()
                + " | " + Controle.dataPadrao.format(new Date()));
    }

    public static void inicializa() {

        new Thread("INICIALIZA VARIÁVEIS") {
            @Override
            public void run() {
                preencheDadosConexaoSimatex();

                preencheDadosConexaoLojaIntegrada();

                preencheDadosConexaoInternet();
            }

        }.start();
    }

    /**
     * Gerente de janelas
     */
    private static GerenteJanelas defaultGj = null;

    public static GerenteJanelas getDefaultGj() {
        return defaultGj;
    }

    public static void setDefaultGj(GerenteJanelas defaultGj) {
        Controle.defaultGj = defaultGj;
    }

    public Controle(String host, String usuario, String senha, int porta) {
        this.host = host;
        this.usuario = usuario;
        this.senha = senha;
        this.porta = porta;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    //DAO-----------------------------------------------------------------------
    public static Controle retornaDadosConexao(String senha) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Controle retorno = null;
//Estoque2566
        try {
            stmt = con.prepareStatement("SELECT host, usuario, senha, porta "
                    + "FROM tabela_controle "
                    + "WHERE senha = md5(?)");
            stmt.setString(1, senha);
            rs = stmt.executeQuery();
            if (rs.next()) {
                retorno = new Controle(
                        rs.getString("host"),
                        rs.getString("usuario"),
                        senha,
                        rs.getInt("porta")
                );
            } else {
                retorno = null;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return retorno;
    }

    public static String retornaDirEstoque() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT dirEstoque FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("dirEstoque");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return "";
    }
    
    public static String retornaDirArquivo() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT DIR_ARQUIVOS FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("DIR_ARQUIVOS");
            }
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return "";
    }

    /**
     * Retorna os avisos definidos pelos administradores para edição
     *
     * @return
     * @throws SQLException
     */
    public static List<String> retornaAvisos() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> retorno = new ArrayList();

        try {
            stmt = con.prepareStatement("SELECT AVISOS_ORC, AVISOS_PROD, AVISOS_EXP, AVISOS_FIN, AVISOS_ORD "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                retorno.add(rs.getString("AVISOS_ORC"));
                retorno.add(rs.getString("AVISOS_PROD"));
                retorno.add(rs.getString("AVISOS_EXP"));
                retorno.add(rs.getString("AVISOS_FIN"));
                retorno.add(rs.getString("AVISOS_ORD"));
            }
            return retorno;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna avisos definidos pelos administradores
     *
     * @return
     * @throws SQLException
     */
    public static String retornaAvOrc() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT AVISOS_ORC "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("AVISOS_ORC");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna avisos definidos pelos administradores
     *
     * @return
     * @throws SQLException
     */
    public static String retornaAvExp() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT AVISOS_EXP "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("AVISOS_EXP");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna avisos definidos pelos administradores
     *
     * @return
     * @throws SQLException
     */
    public static String retornaAvProd() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT AVISOS_PROD "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("AVISOS_PROD");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna avisos definidos pelos administradores
     *
     * @return
     * @throws SQLException
     */
    public static String retornaAvFin() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT AVISOS_FIN "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("AVISOS_fin");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Retorna avisos definidos pelos administradores
     *
     * @return
     * @throws SQLException
     */
    public static String retornaAvOrd() throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT AVISOS_ORD "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("AVISOS_ORD");
            }
            return null;
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Atualiza os avisos para os módulos
     *
     * @param avisos lista de avisos
     * @throws SQLException
     */
    public static void atualizaAvisos(List<String> avisos) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE tabela_controle SET AVISOS_ORC = ?, AVISOS_PROD = ?,"
                    + "AVISOS_EXP = ?, AVISOS_FIN = ?, AVISOS_ORD = ?");
            stmt.setString(1, avisos.get(0));
            stmt.setString(2, avisos.get(1));
            stmt.setString(3, avisos.get(2));
            stmt.setString(4, avisos.get(3));
            stmt.setString(5, avisos.get(4));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Verifica a versão atual do sistema
     *
     * @param codigo código da versão
     * @param atualizacao atualização da versão
     * @return
     * @throws SQLException
     */
    public static boolean verificaVersao(String codigo, String atualizacao) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT CODIGO, ATUALIZACAO "
                    + "FROM versao "
                    + "WHERE CODIGO != ? OR ATUALIZACAO != ?");
            stmt.setString(1, codigo);
            stmt.setString(2, atualizacao);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Preenche os dados para a conexão com o banco de dados do Simatex, com
     * parâmetros do banco de dados
     */
    public static void preencheDadosConexaoSimatex() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT SRV_SIMATEX, " + "PORTA_SIMATEX, " + "USR_SIMATEX, " + "SENHA_SIMATEX, " + "END_BD_SIMATEX " + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                SERVIDOR = rs.getString("SRV_SIMATEX");
                PORTA = String.valueOf(rs.getInt("PORTA_SIMATEX"));
                USUARIO = rs.getString("USR_SIMATEX");
                SENHA = rs.getString("SENHA_SIMATEX");
                ENDERECO_BD = rs.getString("END_BD_SIMATEX");
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Preenche os dados para a integração com a Loja Integrada
     */
    public static void preencheDadosConexaoLojaIntegrada() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT CHAVE_APP_LI, "
                    + "CHAVE_API_LI, "
                    + "LINK_API_LI, "
                    + "VERSAO_API_LI,"
                    + "SYNC_PEDIDOS "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                CHAVE_APLICACAO = rs.getString("CHAVE_APP_LI");
                CHAVE_API = rs.getString("CHAVE_API_LI");
                LINK_API = rs.getString("LINK_API_LI");
                VERSAO_API = rs.getString("VERSAO_API_LI");
                SYNC_PEDIDOS = rs.getTimestamp("SYNC_PEDIDOS");
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Preenche os dados para a conexão com a internet
     */
    public static void preencheDadosConexaoInternet() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.prepareStatement("SELECT USO_PROXY, "
                    + "HOST_PROXY, "
                    + "PORT_PROXY, "
                    + "USER_PROXY, "
                    + "PASSWORD_PROXY "
                    + "FROM tabela_controle");
            rs = stmt.executeQuery();
            if (rs.next()) {
                USO_PROXY = rs.getByte("USO_PROXY") == 1;
                HOST_PROXY = rs.getString("HOST_PROXY");
                PORT_PROXY = rs.getInt("PORT_PROXY");
                USER_PROXY = rs.getString("USER_PROXY");
                PASSWORD_PROXY = rs.getString("PASSWORD_PROXY");
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }
    
    /**
     * Atualiza a última sincronização no banco de dados
     * @param novoDateTime 
     */
    public static void atualizaSyncPedidos(java.sql.Timestamp novoDateTime){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        System.out.println(novoDateTime);
        try{
            stmt = con.prepareStatement("UPDATE tabela_controle "
                    + "SET tabela_controle.SYNC_PEDIDOS = ? ");
            stmt.setTimestamp(1, novoDateTime);
            stmt.executeUpdate();
            SYNC_PEDIDOS = novoDateTime;
        }catch(SQLException ex){
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //status--------------------------------------------------------------------    
    /**
     * Retorna a descrição do status do orçamento pelo código do status
     * informado
     *
     * @param codStatus código do status
     * @return
     */
    public static String getStatusByCod(int codStatus) {
        for (StsOrcamento status : stsOrcamento) {
            if (status.getCod() == codStatus) {
                return status.getStatus();
            }
        }
        return null;
    }

    //banco selecionado---------------------------------------------------------
    /**
     * Banco de dados
     *
     * @return
     */
    public static String getSelBanco() {
        switch (tipoVersao) {
            case 1:
                return "PRODUÇÃO";
            case 2:
                return "DEV REDE";
            case 3:
                return "DEV LOCAL";
            case 4:
                return "TESTE PEIXOTO";
            default:
                return null;
        }
    }

    //funções de formatação de string-------------------------------------------
    /**
     * Retorna o cep formatado
     *
     * @param cep número de telefone
     * @return
     */
    public static synchronized String retornaCepFormatado(String cep) {
        try {
            String formatar = cep;
            formatar = formatar.replace(".", "");
            formatar = formatar.replace("-", "");
            MaskFormatter formatoCep = null;

            formatoCep = new MaskFormatter("##.###-###");
            formatoCep.setValueContainsLiteralCharacters(false);
            formatar = formatoCep.valueToString(formatar);

            return formatar;
        } catch (ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
            return null;
        }
    }

    /**
     * Retorna o CNPJ/CPF formatado
     *
     * @param documento documento
     * @param tipoPessoa
     * @return
     */
    public static synchronized String retornaDocumentoFormatado(String documento, byte tipoPessoa) {
        try {
            String formatar = documento;
            formatar = formatar.replace(".", "");
            formatar = formatar.replace("-", "");
            formatar = formatar.replace(" ", "");
            MaskFormatter formatoDocumento = null;
            switch (tipoPessoa) {
                case 1:
                    formatoDocumento = new MaskFormatter("###.###.###-##");
                    formatoDocumento.setValueContainsLiteralCharacters(false);
                    formatar = formatoDocumento.valueToString(formatar);
                    break;
                case 2:
                    formatoDocumento = new MaskFormatter("##.###.###/####-##");
                    formatoDocumento.setValueContainsLiteralCharacters(false);
                    formatar = formatoDocumento.valueToString(formatar);
                    break;
            }
            return formatar;
        } catch (ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
            return null;
        }
    }

    /**
     * Retorna o telefone formatado
     *
     * @param telefone número de telefone
     * @return
     */
    public static synchronized String retornaTelefoneFormatado(String telefone) {
        try {
            String formatar = telefone;
            formatar = formatar.replace("(", "");
            formatar = formatar.replace(")", "");
            formatar = formatar.replace(" ", "");
            formatar = formatar.replace("-", "");
            MaskFormatter formatoTelefone = null;
            if (formatar.length() == 10) {
                formatoTelefone = new MaskFormatter("(##) ####-####");
                formatoTelefone.setValueContainsLiteralCharacters(false);
                formatar = formatoTelefone.valueToString(formatar);
            }
            if (formatar.length() == 11) {
                formatoTelefone = new MaskFormatter("(##) # ####-####");
                formatoTelefone.setValueContainsLiteralCharacters(false);
                formatar = formatoTelefone.valueToString(formatar);

            }
            return formatar;

        } catch (ParseException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
            return null;
        }
    }

    /**
     * Transforma tipo cliente byte em String
     *
     * @param tipoCliente
     * @return
     */
    public static synchronized String retornaTipoCliente(byte tipoCliente) {
        switch (tipoCliente) {
            case 1:
                return "PESSOA FÍSICA";
            case 2:
                return "PESSOA JURÍDICA";
        }
        return null;
    }
    
    /**
     * Abre diretório de arquivos
     * @param caminho
     * @throws IOException 
     */
    public static void abreDiretorio(String caminho) throws IOException {
        String os = System.getProperty("os.name");
        os = os.toLowerCase();
        if (os.contains("win") && !caminho.equals("")) {
            try {
                Runtime.getRuntime().exec("explorer.exe " + Controle.TEMP_DIR);
            } catch (IOException ex) {
                Controle.avisosUsuario((byte) 1, "FALHA AO ABRIR O DIRETÓRIO: " + ex);
            }
        } else if (!caminho.equals("")) {
            try {
                Runtime.getRuntime().exec("nautilus " + caminho);
            } catch (IOException ex) {
                Controle.avisosUsuario((byte) 1, "FALHA AO ABRIR O DIRETÓRIO: " + ex);
            }
        }
    }
    
    public static void criaDiretorio(String caminho) {
        try {
            if (!verificaDiretorio(caminho)) {
                File dir = new File(caminho);
                dir.mkdir();
            }
        } catch (Exception ex) {
            Controle.avisosUsuario((byte) 1, "ERRO AO CRIAR O DIRETORIO");
        }
    }
    
    public static boolean verificaDiretorio(String caminho) {
        boolean retorno = false;
        File dir = new File(caminho);
        if (!dir.exists()) {
            retorno = false;
        } else {
            retorno = true;
        }
        return retorno;
    }

}

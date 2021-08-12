package connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import exception.EnvioExcecao;
import entities.sisgrafex.Endereco;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import org.json.JSONObject;
import ui.controle.Controle;
import ui.principal.Estoque;
import ui.principal.OrcamentoFrame;

/**
 *
 * @author Seçao SPD3
 */
public class ConnectionFactory {

    /**
     * Variáveis para conexão com o banco de dados
     */
    private static String DRIVER_SQL;
    private static String URL;
    private static String USER;
    private static String PASS;

    public static Session session;
    public static boolean ready;
    public static Channel channel;
    public static InputStream commandOutput;

    public static void main(String[] args) {
        
    }

    private static void defineVar() {
        switch (Controle.getTipoVersao()) {
            case 1:
                DRIVER_SQL = "com.mysql.cj.jdbc.Driver";
                URL = "jdbc:mysql://10.67.32.252:3306/bala_dev";
                USER = "remoto";
                PASS = "ALLAH@366";
                break;
            case 2:
                DRIVER_SQL = "com.mysql.cj.jdbc.Driver";
                URL = "jdbc:mysql://10.67.32.252:3306/bala_dev2";
                USER = "remoto";
                PASS = "ALLAH@366";
                break;
            case 3:
                DRIVER_SQL = "com.mysql.cj.jdbc.Driver";
                URL = "jdbc:mysql://localhost:3306/bala_dev";
                USER = "root";
                PASS = "";
                break;
            case 4:
                DRIVER_SQL = "com.mysql.cj.jdbc.Driver";
                URL = "jdbc:mysql://10.67.32.252:3306/bala_dev2";
                USER = "remoto";
                PASS = "ALLAH@366";
                break;
        }
    }

    public static Connection getConnection() {
        try {
            defineVar();
            Class.forName(DRIVER_SQL);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException ex) {
            return null;
        }
    }

    public static void closeConnection(Connection con) {

        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {

        try {

            if (stmt != null) {
                stmt.close();
            }

        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        closeConnection(con);
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {

        closeConnection(con, stmt);

        try {

            if (rs != null) {
                rs.close();
            }

        } catch (SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }

    public static Boolean verificaCnpj(String cnpj) {
        Boolean retorno = false;

        String queryURL = "https://www.receitaws.com.br/v1/cnpj/" + cnpj;

        JSONObject object;

        SocketAddress addr = new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);

        HttpURLConnection con = null;

        try {
            URL url = new URL(queryURL);
            con = (HttpURLConnection) url.openConnection(proxy);

            con.setRequestMethod("GET");

            StringBuilder result = new StringBuilder();

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(result.toString());

            if (obj.getString("status").equals("OK")) {
                retorno = true;
            } else {
                retorno = false;
            }
        } catch (MalformedURLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } catch (IOException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } finally {
            con.disconnect();
        }

        return retorno;
    }

    public static Endereco retornaInformacoesCEP(String cep) {
        HashMap values = new HashMap();
        ObjectMapper objectMapper = null;
        String requestBody = null;
        HttpClient client = null;
        HttpRequest request = null;
        HttpResponse<String> response = null;
        HttpHeaders headers = null;
        Endereco retorno = null;

        try {

            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    if (getRequestorType() == RequestorType.PROXY) {
                        String prot = getRequestingProtocol().toLowerCase();
                        String host = System.getProperty(prot + ".proxyHost", Controle.HOST_PROXY);
                        String port = System.getProperty(prot + ".proxyPort", String.valueOf(Controle.PORT_PROXY));
                        String user = System.getProperty(prot + ".proxyUser", Controle.USER_PROXY);
                        String password = System.getProperty(prot + ".proxyPassword", Controle.PASSWORD_PROXY);

                        if (getRequestingHost().equalsIgnoreCase(host)) {
                            if (Integer.parseInt(port) == getRequestingPort()) {
                                // Seems to be OK.
                                return new PasswordAuthentication(user, password.toCharArray());
                            }
                        }
                    }
                    return null;
                }
            });

            objectMapper = new ObjectMapper();
            requestBody = objectMapper.writeValueAsString(values);

            client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                    .authenticator(Authenticator.getDefault())
                    .build();

            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://viacep.com.br/ws/"
                            + cep
                            + "/json/"))
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            headers = response.headers();

            if (response.body().isEmpty()) {
                JOptionPane.showMessageDialog(null, "O 'CEP' DIGITADO NÃO EXISTE!");
            } else {
                Map<String, Object> responseMap = new ObjectMapper().readValue(response.body(), HashMap.class);
                retorno = new Endereco(responseMap.get("logradouro").toString(),
                        responseMap.get("complemento").toString(),
                        responseMap.get("bairro").toString(),
                        responseMap.get("localidade").toString(),
                        responseMap.get("uf").toString());
            }

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(IntegracaoLojaIntegrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    /**
     * @param operacao 1 - upload, 2 - download
     * @param className 1 - estoque, 2 - orçamento
     * @return 
     *
     */
    public static boolean connectSSH(byte operacao, byte className) {
        try {
            String resposta = null;
            int retorno = 0;
            if (operacao == 1) {
                JLabel label = new JLabel("DIGITE A SENHA");
                JPasswordField jpf = new JPasswordField();
                retorno = JOptionPane.showConfirmDialog(null, new Object[]{label, jpf}, "SENHA MESTRA:", JOptionPane.OK_CANCEL_OPTION);
                resposta = String.valueOf(jpf.getPassword());
            } else if (operacao == 2) {
                resposta = Controle.SENHA_ESTOQUE;
                retorno = 0;
            }
            Controle controle = Controle.retornaDadosConexao(resposta);
            if (className == 1) {
                Estoque.loadingVisible("VERIFICANDO SENHA...");
            } else {
                OrcamentoFrame.loadingVisible("VERIFICANDO SENHA...");
            }

            if (retorno != 0 || controle == null) {
                JOptionPane.showMessageDialog(null, "SENHA INCORRETA!");
                if (className == 1) {
                    Estoque.loadingHide();
                } else {
                    OrcamentoFrame.loadingHide();
                }
                return false;
            } else {
                JSch ssh = new JSch();
                session = ssh.getSession(controle.getUsuario(), controle.getHost(), controle.getPorta());
                session.setPassword(controle.getSenha());
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect(30000);
                ready = true;
                return true;
            }
        } catch (SQLException | JSchException ex) {
            if (className == 1) {
                Estoque.loadingHide();
            } else {
                OrcamentoFrame.loadingHide();
            }
            ready = false;
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        return false;
    }

    public static void closeSSH() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        ready = false;
    }

    public static boolean uploadEstoqueSSH(String origem, String destino, String dirDestino) {
        try {
            if (connectSSH((byte) 1, (byte) 1)) {
                Estoque.loadingVisible("CARREGANDO ARQUIVO...");
                if (existFile()) {
                    deleteFile();
                }
                ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
                sftp.connect();
                sftp.cd(dirDestino);
                sftp.put(origem, destino);
                sftp.disconnect();
                closeSSH();
                return true;
            }
        } catch (JSchException ex) {
            ready = false;
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } catch (SftpException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } catch (Exception ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        return false;
    }

    /**
     * @param dirLocal diretório local
     * @param className 1 - estoque, 2 - orcamento
     * @return 
     *
     */
    public static boolean downloadEstoqueSSH(String dirLocal, byte className) {
        try {
            connectSSH((byte) 2, className);
            if (className == 1) {
                Estoque.loadingVisible("EXCLUINDO ARQUIVOS EXISTENTES...");
            } else {
                OrcamentoFrame.loadingVisible("EXCLUINDO ARQUIVOS EXISTENTES...");
            }
            deleteLocalFile(dirLocal);
            if (className == 1) {
                Estoque.loadingVisible("FAZENDO O DOWNLOAD...");
            } else {
                OrcamentoFrame.loadingVisible("FAZENDO O DOWNLOAD...");
            }
            ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();
            sftp.get(Controle.retornaDirEstoque() + "/" + Controle.ESTOQUE_NAME + ".*", dirLocal);
            sftp.disconnect();
            closeSSH();
            return true;
        } catch (JSchException | SftpException | SQLException ex) {
            if (className == 1) {
                Estoque.loadingHide();
            } else {
                OrcamentoFrame.loadingHide();
            }
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } catch (Exception ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        return false;
    }

    public static String write(String comando) {
        try {
            BufferedReader reader = null;
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(comando);
            commandOutput = channel.getInputStream();
            reader = new BufferedReader(new InputStreamReader(commandOutput));
            channel.connect(30000);

            StringBuilder stringBuilder = new StringBuilder();
            String lido = reader.readLine();

            while (lido != null) {
                stringBuilder.append(lido);
                stringBuilder.append("\n");
                lido = reader.readLine();
            }

            return stringBuilder.toString();
        } catch (JSchException | IOException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        return null;
    }

    public static boolean existFile() {
        try {
            if (ready) {
                String retorno = write("cd " + Controle.retornaDirEstoque() + " && [ -f " + Controle.ESTOQUE_NAME + ".* ] && echo 1 || echo 0");
                if (retorno != null) {
                    retorno = retorno.replace("\n", "");
                    return Long.parseLong(retorno) > 0;
                }
            }
        } catch (Exception ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        return false;
    }

    public static void deleteFile() {
        try {
            if (ready) {
                write("rm -f " + Controle.retornaDirEstoque() + "/" + Controle.ESTOQUE_NAME + ".*");
            }
        } catch (Exception ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }

    public static void deleteLocalFile(String dirLocal) {
        try {
            File file = new File(dirLocal);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; ++i) {
                    if (files[i].getName().contains(Controle.ESTOQUE_NAME)) {
                        files[i].delete();
                        return;
                    }
                }
            }
        } catch (Exception ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
    }
    
    //CONTROLE DE ARQUIVOS OP
    /**
     * Realiza o envio do arquivo para o diretório
     * @param codOp código da OP
     * @param tipoVersao tipo de versão do arquivo 1 - V1, 2 - VF
     */
    public static boolean uploadArquivo(String codOp, byte tipoVersao, String origem){
        try {
            if (connectSSH((byte) 2, (byte) 1)) {
                ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
                sftp.connect();
                
                //Entra no diretório de arquivo
                sftp.cd(Controle.retornaDirArquivo());
                
                //Verifica se a pasta para a OP existe, caso negativo, a cria
                switch(write("[ -d " + codOp + " ] && echo 1 || echo 0")){
                    case "0":
                        sftp.mkdir(codOp);
                        sftp.cd(codOp);
                        switch(write("[ -d V" + String.valueOf(tipoVersao) + " ] && echo 1 || echo 0")){
                            case "1":
                                sftp.cd("V" + codOp);
                                sftp.put(origem, "V" + String.valueOf(tipoVersao));
                                break;
                            case "0":
                                sftp.mkdir("V" + codOp);
                                sftp.cd("V" + codOp);
                                sftp.put(origem, "V" + String.valueOf(tipoVersao));
                                break;
                        }
                        break;
                    case "1":
                        sftp.cd(codOp);
                        switch(write("[ -d V" + String.valueOf(tipoVersao) + " ] && echo 1 || echo 0")){
                            case "1":
                                sftp.cd("V" + codOp);
                                sftp.put(origem, "V" + String.valueOf(tipoVersao));
                                break;
                            case "0":
                                sftp.mkdir("V" + codOp);
                                sftp.cd("V" + codOp);
                                sftp.put(origem, "V" + String.valueOf(tipoVersao));
                                break;
                        }
                        break;
                }
                sftp.disconnect();
                closeSSH();
                return true;
            }
        } catch (JSchException ex) {
            ready = false;
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } catch (SftpException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } catch (Exception ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        return false;
    }

}

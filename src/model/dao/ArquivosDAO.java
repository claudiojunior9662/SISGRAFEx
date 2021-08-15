/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import connection.ConnectionFactory;
import entities.sisgrafex.Arquivos;
import exception.EnvioExcecao;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import ui.controle.Controle;
import ui.login.TelaAutenticacao;

/**
 *
 * @author Claudio Júnior
 */
public class ArquivosDAO {

    /**
     * Verifica se o registro para a OP já existe
     *
     * @param codOp
     * @param tipoVersao
     * @return
     * @throws SQLException
     */
    public static boolean verificaRegistro(int codOp, byte tipoVersao) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT OP "
                    + "FROM dir_arquivos "
                    + "WHERE OP = ? AND TIPO = ?");
            stmt.setInt(1, codOp);
            stmt.setByte(2, tipoVersao);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    /**
     * Cria o registro para a OP
     *
     * @param regArquivo
     * @throws SQLException
     */
    public static void criaRegistro(Arquivos regArquivo) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO dir_arquivos(OP, TIPO, DIRETORIO, DT_MOD, USR_MOD) "
                    + "VALUES(?,?,?,?,?)");
            stmt.setInt(1, regArquivo.getOp());;
            stmt.setByte(2, regArquivo.getTipo());
            stmt.setString(3, regArquivo.getDiretorio());
            stmt.setTimestamp(4, regArquivo.getDtMod());
            stmt.setString(5, regArquivo.getUsrMod());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    /**
     * Atualiza o registro da OP
     *
     * @param regArquivo
     * @throws SQLException
     */
    public static void atualizaRegistro(Arquivos regArquivo) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE dir_arquivos(OP, TIPO, DIRETORIO, DT_MOD, USR_MOD) "
                    + "SET TIPO = ?, DIRETORIO = ?, DT_MOD = ?, USR_MOD = ? "
                    + "WHERE OP = ?");
            stmt.setByte(1, regArquivo.getTipo());
            stmt.setString(2, regArquivo.getDiretorio());
            stmt.setTimestamp(3, regArquivo.getDtMod());
            stmt.setString(4, regArquivo.getUsrMod());
            stmt.setInt(5, regArquivo.getOp());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //CONTROLE DE ARQUIVOS OP
    /**
     * Realiza o envio do arquivo para o diretório
     *
     * @param codOp código da OP
     * @param tipoVersao tipo de versão do arquivo 1 - V1, 2 - VF
     * @param origem
     * @return
     */
    public static boolean uploadArquivo(String codOp, byte tipoVersao, String origem, String tipoDestino, JLabel loading) {
        try {
            if (!tipoDestino.equals(".zip")) {
                Controle.avisosUsuario((byte) 1, "PERMITIDOS APENAS ARQUIVOS '.zip'.");
                return false;
            } else {
                if (ConnectionFactory.connectSSH((byte) 2, loading)) {
                    ChannelSftp sftp = (ChannelSftp) ConnectionFactory.session.openChannel("sftp");
                    String dirArquivo = Controle.retornaDirArquivo();
                    sftp.connect();
                    //Entra no diretório de arquivo
                    sftp.cd(dirArquivo);
                    loading.setText("ENTRANDO NO DIRETÓRIO...");
                    //Verifica se a pasta para a OP existe, caso negativo, a cria
                    switch (ConnectionFactory.write("cd " + dirArquivo + " && [ -d " + codOp + " ] && echo 1 || echo 0").replace("\n", "")) {
                        case "0":
                            sftp.mkdir(codOp);
                            sftp.cd(codOp);
                            sftp.chmod(777, sftp.pwd());
                            //Verifica se a pasta da versão existe, caso negativo a cria
                            switch (ConnectionFactory.write("cd " + dirArquivo + "/" + codOp + " && [ -d V" + String.valueOf(tipoVersao) + " ] && echo 1 || echo 0").replace("\n", "")) {
                                case "1":
                                    sftp.cd("V" + String.valueOf(tipoVersao));
                                    loading.setText("FAZENDO UPLOAD, AGUARDE...");
                                    sftp.put(origem, "OP" + codOp + "V" + String.valueOf(tipoVersao) + tipoDestino);
                                    break;
                                case "0":
                                    sftp.mkdir("V" + String.valueOf(tipoVersao));
                                    sftp.cd("V" + String.valueOf(tipoVersao));
                                    loading.setText("FAZENDO UPLOAD, AGUARDE...");
                                    sftp.put(origem, "OP" + codOp + "V" + String.valueOf(tipoVersao) + tipoDestino);
                                    break;
                            }
                            break;
                        case "1":
                            sftp.cd(codOp);
                            //Verifica se a pasta da versão existe, caso negativo a cria
                            switch (ConnectionFactory.write("cd " + dirArquivo + "/" + codOp + " && [ -d V" + String.valueOf(tipoVersao) + " ] && echo 1 || echo 0").replace("\n", "")) {
                                case "0":
                                    sftp.mkdir("V" + String.valueOf(tipoVersao));
                                    sftp.cd("V" + String.valueOf(tipoVersao));
                                    sftp.chmod(777, sftp.pwd());
                                    loading.setText("FAZENDO UPLOAD, AGUARDE...");
                                    sftp.put(origem, "OP" + codOp + "V" + String.valueOf(tipoVersao) + tipoDestino);
                                    break;
                                case "1":
                                    sftp.cd("V" + String.valueOf(tipoVersao));
                                    ConnectionFactory.write("cd " + dirArquivo + "/" + codOp + "/" + "V" + String.valueOf(tipoVersao) + " && rm -rf V" + String.valueOf(tipoVersao) + ".*");
                                    loading.setText("FAZENDO UPLOAD, AGUARDE...");
                                    sftp.put(origem, "OP" + codOp + "V" + String.valueOf(tipoVersao) + tipoDestino);
                                    break;
                            }
                            break;
                    }
                    sftp.disconnect();
                    ConnectionFactory.closeSSH();

                    //Faz o registro no histório de arquivo
                    //Verifica se o registro já existe
                    loading.setText("REGISTRANDO...");
                    if (verificaRegistro(Integer.valueOf(codOp), tipoVersao)) {
                        atualizaRegistro(new Arquivos(
                                Integer.valueOf(codOp),
                                tipoVersao,
                                dirArquivo + "/" + codOp + "/" + "/V" + tipoVersao + "OP" + codOp + "V" + String.valueOf(tipoVersao) + tipoDestino,
                                java.sql.Timestamp.from(new Date().toInstant()),
                                TelaAutenticacao.getUsrLogado().getCodigo()
                        ));
                    } else {
                        criaRegistro(new Arquivos(
                                Integer.valueOf(codOp),
                                tipoVersao,
                                dirArquivo + "/" + codOp + "/" + "/V" + tipoVersao + "OP" + codOp + "V" + String.valueOf(tipoVersao) + tipoDestino,
                                java.sql.Timestamp.from(new Date().toInstant()),
                                TelaAutenticacao.getUsrLogado().getCodigo()
                        ));
                    }
                    return true;
                }
            }
        } catch (JSchException ex) {
            ConnectionFactory.ready = false;
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
     * Realiza o download do arquivo
     * @param codOp
     * @param tipoVersao
     * @param loading
     * @return 
     */
    public static boolean downloadArquivo(int codOp, byte tipoVersao, JLabel loading) {
        try {
            if (ConnectionFactory.connectSSH((byte) 2, loading)) {
                loading.setText("FAZENDO DOWNLOAD...");
                ChannelSftp sftp = (ChannelSftp) ConnectionFactory.session.openChannel("sftp");
                sftp.connect();
                sftp.get(Controle.retornaDirArquivo() + "/" + String.valueOf(codOp) + "/V" + String.valueOf(tipoVersao) + "/" + "*.*", Controle.TEMP_DIR);
                sftp.disconnect();
                ConnectionFactory.closeSSH();
                return true;
            }
        } catch (JSchException ex) {
            Logger.getLogger(ArquivosDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SftpException | SQLException ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        } catch (Exception ex) {
            EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
            EnvioExcecao.envio(null);
        }
        return false;

    }
}

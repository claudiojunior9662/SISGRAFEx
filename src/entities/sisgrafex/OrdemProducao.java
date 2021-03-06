/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

import com.itextpdf.text.BaseColor;
import ui.cadastros.produtos.AcabamentoProdBEAN;
import ui.cadastros.papeis.PapelBEAN;
import ui.controle.Controle;
import ui.login.TelaAutenticacao;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import exception.EnvioExcecao;
import exception.SemAcabamentoException;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import model.dao.OrcamentoDAO;
import model.dao.OrdemProducaoDAO;
import ui.cadastros.acabamentos.AcabamentoDAO;
import model.dao.ClienteDAO;
import model.dao.EnderecoDAO;
import model.dao.NotaDAO;
import ui.cadastros.notas.TransporteBEAN;
import ui.cadastros.notas.VolumeBEAN;
import model.dao.PapelDAO;
import model.dao.ProdutoDAO;
import model.dao.ServicoDAO;

/**
 *
 * @author claud
 */
public class OrdemProducao {

    /**
     * Campos do banco de dados
     */
    private int codigo;
    private int orcamentoBase;
    private byte tipoProduto;
    private int codProduto;
    private int codCliente;
    private int codContato;
    private int codEndereco;
    private String codEmissor;
    private byte tipoCliente;
    private Date dataEmissao;
    private Date dataEntrega;
    private byte status;
    private String descricao;
    private Date data1aProva;
    private Date data2aProva;
    private Date data3aProva;
    private Date data4aProva;
    private Date data5aProva;
    private Date dataAprCliente;
    private Date dataEntFinal;
    private Date dataImpDir;
    private Date dataEntdOffset;
    private Date dataEntDigital;
    private Date dataEntdTipografia;
    private Date dataEntdAcabamento;
    private Date dataEnvioDivCmcl;
    private Date dataCancelamento;
    private Date dataEntgProva;
    private int indEntgPrazo;
    private int indEntgErro;
    private String tipoTrabalho;
    private String opSecao;
    private String obsFrete;
    private String codAtendente;

    private int quantidade;
    private float valorParcial;

    //TABELA CONSULTA
    private String statusString;
    private String clienteString;
    private String tipoPessoaString;
    private String dataEmissaoString;
    private String dataEntregaString;

    public OrdemProducao() {

    }

    public OrdemProducao(Date dataEmissao, Date dataEntrega) {
        this.dataEmissao = dataEmissao;
        this.dataEntrega = dataEntrega;
    }

    public OrdemProducao(int codigo,
            int orcamentoBase,
            String dataEmissaoString,
            String dataEntregaString,
            String descricao,
            String statusString,
            String clienteString,
            String tipoPessoaString) {
        this.codigo = codigo;
        this.orcamentoBase = orcamentoBase;
        this.dataEmissaoString = dataEmissaoString;
        this.dataEntregaString = dataEntregaString;
        this.descricao = descricao;
        this.statusString = statusString;
        this.clienteString = clienteString;
        this.tipoPessoaString = tipoPessoaString;
    }

    public OrdemProducao(int codigo, String status) {
        this.codigo = codigo;
        this.statusString = status;
    }

    public OrdemProducao(int cod,
            Date dataEntrega,
            byte status,
            int codProduto) {
        this.codigo = cod;
        this.dataEntrega = dataEntrega;
        this.status = status;
        this.codProduto = codProduto;
    }

    public OrdemProducao(int cod,
            Integer orcamentoBase,
            Integer codCliente,
            byte tipoPessoa,
            int codProduto,
            byte tipoProduto,
            Integer codContato,
            Integer codEndereco,
            Date dataEmissao,
            Date dataEntrega,
            byte status,
            String descricao) {
        this.codigo = cod;
        this.orcamentoBase = orcamentoBase;
        this.codCliente = codCliente;
        this.tipoCliente = tipoPessoa;
        this.codProduto = codProduto;
        this.tipoProduto = tipoProduto;
        this.codContato = codContato;
        this.codEndereco = codEndereco;
        this.dataEmissao = dataEmissao;
        this.dataEntrega = dataEntrega;
        this.status = status;
        this.descricao = descricao;
    }

    public OrdemProducao(int cod,
            int orcamentoBase,
            int codProduto,
            byte tipoProduto,
            int codCliente,
            int codContato,
            int codEndereco,
            byte tipoPessoa,
            String codEmissor) {
        this.codigo = cod;
        this.orcamentoBase = orcamentoBase;
        this.codProduto = codProduto;
        this.tipoProduto = tipoProduto;
        this.codCliente = codCliente;
        this.codContato = codContato;
        this.codEndereco = codEndereco;
        this.tipoCliente = tipoPessoa;
        this.codEmissor = codEmissor;
    }

    public OrdemProducao(int cod,
            int orcamentoBase,
            int codCliente,
            int codContato,
            int codEndereco,
            int codProduto,
            byte tipoProduto,
            Date dataEntrega,
            Date dataEmissao,
            Date dataEntgProva,
            byte tipoPessoa,
            String codEmissor,
            Date dataCancelamento,
            String obsFrete) {
        this.codigo = cod;
        this.orcamentoBase = orcamentoBase;
        this.codCliente = codCliente;
        this.codContato = codContato;
        this.codEndereco = codEndereco;
        this.dataEntrega = dataEntrega;
        this.dataEmissao = dataEmissao;
        this.tipoCliente = tipoPessoa;
        this.codEmissor = codEmissor;
        this.dataCancelamento = dataCancelamento;
        this.codProduto = codProduto;
        this.tipoProduto = tipoProduto;
        this.dataEntgProva = dataEntgProva;
        this.obsFrete = obsFrete;
    }

    public OrdemProducao(int cod,
            int codCliente,
            byte tipoPessoa,
            Date dataEmissao,
            Date dataEntrega,
            byte status,
            String descricao,
            String opSecao,
            String codEmissor,
            Date data1aProva,
            Date data2aProva,
            Date data3aProva,
            Date data4aProva,
            Date data5aProva,
            Date dataAprCliente,
            Date dataEntFinal,
            Date dataImpDir,
            Date dataEntOffset,
            Date dataEntDigital,
            Date dataEntTipografia,
            Date dataEntAcabamento,
            Date dataEnvioDivCmcl,
            int indEntPrazo,
            int indEntErro,
            int codProduto,
            byte tipoProduto,
            int orcamentoBase,
            int codContato,
            int codEndereco,
            Date dataCancelamento,
            String tipoTrabalho,
            String codAtendente) {
        this.codigo = cod;
        this.codCliente = codCliente;
        this.tipoCliente = tipoPessoa;
        this.dataEmissao = dataEmissao;
        this.dataEntrega = dataEntrega;
        this.status = status;
        this.descricao = descricao;
        this.opSecao = opSecao;
        this.codEmissor = codEmissor;
        this.data1aProva = data1aProva;
        this.data2aProva = data2aProva;
        this.data3aProva = data3aProva;
        this.data4aProva = data4aProva;
        this.data5aProva = data5aProva;
        this.dataAprCliente = dataAprCliente;
        this.dataEntFinal = dataEntFinal;
        this.dataImpDir = dataImpDir;
        this.dataEntdOffset = dataEntOffset;
        this.dataEntDigital = dataEntDigital;
        this.dataEntdTipografia = dataEntTipografia;
        this.dataEntdAcabamento = dataEntAcabamento;
        this.dataEnvioDivCmcl = dataEnvioDivCmcl;
        this.indEntgPrazo = indEntPrazo;
        this.indEntgErro = indEntErro;
        this.codProduto = codProduto;
        this.orcamentoBase = orcamentoBase;
        this.codContato = codContato;
        this.codEndereco = codEndereco;
        this.dataCancelamento = dataCancelamento;
        this.tipoTrabalho = tipoTrabalho;
        this.tipoProduto = tipoProduto;
        this.codAtendente = codAtendente;
    }

    public OrdemProducao(int cod,
            int orcamentoBase,
            int codProduto,
            byte tipoProduto,
            int codCliente,
            byte tipoCliente,
            Date dataEmissao,
            Date dataEntrega,
            byte status) {
        this.codigo = cod;
        this.orcamentoBase = orcamentoBase;
        this.codProduto = codProduto;
        this.tipoProduto = tipoProduto;
        this.codCliente = codCliente;
        this.tipoCliente = tipoCliente;
        this.dataEmissao = dataEmissao;
        this.dataEntrega = dataEntrega;
        this.status = status;
    }

    public OrdemProducao(int orcamentoBase,
            int codOp,
            Date dataEmissao,
            Date dataEntrega,
            String descricao,
            float valorParcial,
            byte status) {
        this.orcamentoBase = orcamentoBase;
        this.codigo = codOp;
        this.dataEmissao = dataEmissao;
        this.dataEntrega = dataEntrega;
        this.descricao = descricao;
        this.valorParcial = valorParcial;
        this.status = status;
    }

    public String getCodAtendente() {
        return codAtendente;
    }

    public void setCodAtendente(String codAtendente) {
        this.codAtendente = codAtendente;
    }

    public String getDataEmissaoString() {
        return dataEmissaoString;
    }

    public void setDataEmissaoString(String dataEmissaoString) {
        this.dataEmissaoString = dataEmissaoString;
    }

    public String getDataEntregaString() {
        return dataEntregaString;
    }

    public void setDataEntregaString(String dataEntregaString) {
        this.dataEntregaString = dataEntregaString;
    }

    public String getClienteString() {
        return clienteString;
    }

    public void setClienteString(String clienteString) {
        this.clienteString = clienteString;
    }

    public String getTipoPessoaString() {
        return tipoPessoaString;
    }

    public void setTipoPessoaString(String tipoPessoaString) {
        this.tipoPessoaString = tipoPessoaString;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getObsFrete() {
        return obsFrete;
    }

    public void setObsFrete(String obsFrete) {
        this.obsFrete = obsFrete;
    }

    public Date getDataEntDigital() {
        return dataEntDigital;
    }

    public void setDataEntDigital(Date dataEntDigital) {
        this.dataEntDigital = dataEntDigital;
    }

    public Date getDataEntgProva() {
        return dataEntgProva;
    }

    public void setDataEntgProva(Date dataEntgProva) {
        this.dataEntgProva = dataEntgProva;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorParcial() {
        return valorParcial;
    }

    public void setValorParcial(float valorParcial) {
        this.valorParcial = valorParcial;
    }

    public Date getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(Date dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public byte getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(byte tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public int getCodContato() {
        return codContato;
    }

    public void setCodContato(int codContato) {
        this.codContato = codContato;
    }

    public int getCodEndereco() {
        return codEndereco;
    }

    public void setCodEndereco(int codEndereco) {
        this.codEndereco = codEndereco;
    }

    public int getOrcBase() {
        return orcamentoBase;
    }

    public void setOrcBase(int orcBase) {
        this.orcamentoBase = orcBase;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public byte getTipoPessoa() {
        return tipoCliente;
    }

    public void setTipoPessoa(byte tipoPessoa) {
        this.tipoCliente = tipoPessoa;
    }

    public String getCodEmissor() {
        return codEmissor;
    }

    public void setCodEmissor(String codEmissor) {
        this.codEmissor = codEmissor;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getData1aProva() {
        return data1aProva;
    }

    public void setData1aProva(Date data1aProva) {
        this.data1aProva = data1aProva;
    }

    public Date getData2aProva() {
        return data2aProva;
    }

    public void setData2aProva(Date data2aProva) {
        this.data2aProva = data2aProva;
    }

    public Date getData3aProva() {
        return data3aProva;
    }

    public void setData3aProva(Date data3aProva) {
        this.data3aProva = data3aProva;
    }

    public Date getData4aProva() {
        return data4aProva;
    }

    public void setData4aProva(Date data4aProva) {
        this.data4aProva = data4aProva;
    }

    public Date getData5aProva() {
        return data5aProva;
    }

    public void setData5aProva(Date data5aProva) {
        this.data5aProva = data5aProva;
    }

    public Date getDataAprCliente() {
        return dataAprCliente;
    }

    public void setDataAprCliente(Date dataAprCliente) {
        this.dataAprCliente = dataAprCliente;
    }

    public Date getDataEntFinal() {
        return dataEntFinal;
    }

    public void setDataEntFinal(Date dataEntFinal) {
        this.dataEntFinal = dataEntFinal;
    }

    public Date getDataImpDir() {
        return dataImpDir;
    }

    public void setDataImpDir(Date dataImpDir) {
        this.dataImpDir = dataImpDir;
    }

    public Date getDataEntOffset() {
        return dataEntdOffset;
    }

    public void setDataEntOffset(Date dataEntOffset) {
        this.dataEntdOffset = dataEntOffset;
    }

    public Date getDataEntTipografia() {
        return dataEntdTipografia;
    }

    public void setDataEntTipografia(Date dataEntTipografia) {
        this.dataEntdTipografia = dataEntTipografia;
    }

    public Date getDataEntAcabamento() {
        return dataEntdAcabamento;
    }

    public void setDataEntAcabamento(Date dataEntAcabamento) {
        this.dataEntdAcabamento = dataEntAcabamento;
    }

    public Date getDataEnvioDivCmcl() {
        return dataEnvioDivCmcl;
    }

    public void setDataEnvioDivCmcl(Date dataEnvioDivCmcl) {
        this.dataEnvioDivCmcl = dataEnvioDivCmcl;
    }

    public int getIndEntPrazo() {
        return indEntgPrazo;
    }

    public void setIndEntPrazo(int indEntPrazo) {
        this.indEntgPrazo = indEntPrazo;
    }

    public int getIndEntErro() {
        return indEntgErro;
    }

    public void setIndEntErro(int indEntErro) {
        this.indEntgErro = indEntErro;
    }

    public String getTipoTrabalho() {
        return tipoTrabalho;
    }

    public void setTipoTrabalho(String tipoTrabalho) {
        this.tipoTrabalho = tipoTrabalho;
    }

    public String getOpSecao() {
        return opSecao;
    }

    public void setOpSecao(String opSecao) {
        this.opSecao = opSecao;
    }

    /**
     * Gera o PDF da ordem de produ????o
     *
     * @param codOp c??digo da ordem de produ????o
     * @param codOrcBase c??digo do or??amento base
     * @param tipo tipo de emiss??o 1 - PARA PRODU????O, 2 - PARA FATURAMENTO
     * @param fat
     * @param historico 1 - COM HIST??RICO, 2 - SEM HIST??RICO
     */
    public static void gerarPdfOp(int codOp,
            int codOrcBase,
            byte tipo,
            Faturamento fat,
            boolean historico) {
        new Thread("Gera PDF OP") {
            @Override
            public void run() {
                try {
                    /**
                     * Cria o documento
                     */
                    com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, 30, 20, 20, 30);

                    /**
                     * Procura a ordem de produ????o
                     */
                    OrdemProducao op = OrdemProducaoDAO.carregaPdfOp(codOp);

                    /**
                     * Procura as informa????es do cliente
                     */
                    Cliente cliente = ClienteDAO.selInfoOp(op.getTipoPessoa(), op.getCodCliente());

                    /**
                     * Procura as informa????es do contato
                     */
                    Contato contato = ClienteDAO.selInfoContato(op.getCodContato());

                    /**
                     * Procura o produto
                     */
                    ProdOrcamento prodOrc = null;
                    if (op.getCodProduto() != 0) {
                        prodOrc = OrcamentoDAO.retornaProdutoOrcamento(codOrcBase, op.getCodProduto());
                    }

                    /**
                     * Instancia o arquivo
                     */
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("java.io.tmpdir") + "/ordemProducao" + codOp + ".pdf"));

                    /**
                     * Define as dimens??es do documento
                     */
                    document.setMargins(20, 20, 20, 20);
                    document.setPageSize(PageSize.A4);
                    document.open();

                    /**
                     * Define as tabelas a serem utilizadas
                     */
                    PdfPTable tblCabecalho = new PdfPTable(new float[]{3f, 8f, 3f});
                    tblCabecalho.setWidthPercentage(100);
                    PdfPTable tblCliente = new PdfPTable(new float[]{3f, 2f, 3f});
                    tblCliente.setWidthPercentage(100);
                    PdfPTable tblIdDest = new PdfPTable(new float[]{5f, 5f, 5f, 5f});
                    tblIdDest.setWidthPercentage(100);
                    PdfPTable tblProduto = new PdfPTable(new float[]{1f, 3f});
                    tblProduto.setWidthPercentage(100);
                    PdfPTable tblPapel = new PdfPTable(new float[]{3f, 3f, 3f});
                    tblPapel.setWidthPercentage(100);
                    PdfPTable tblAcabamentos = new PdfPTable(new float[]{3f, 3f});
                    tblAcabamentos.setWidthPercentage(100);
                    PdfPTable tblChapas = new PdfPTable(new float[]{3f, 3f, 3f});
                    tblChapas.setWidthPercentage(100);
                    PdfPTable tblObservacoes = new PdfPTable(new float[]{3f});
                    tblObservacoes.setWidthPercentage(100);
                    tblObservacoes.setKeepTogether(true);
                    PdfPTable tblServicos = new PdfPTable(new float[]{3f, 3f});
                    tblServicos.setWidthPercentage(100);
                    tblServicos.setKeepTogether(true);
                    PdfPTable tblValores = new PdfPTable(new float[]{5f, 5f, 5f, 5f, 5f});
                    tblValores.setWidthPercentage(100);
                    PdfPTable tblHistorico = new PdfPTable(new float[]{5f, 5f, 5f, 5f, 5f, 5f});
                    tblHistorico.setWidthPercentage(100);
                    PdfPTable tblTransporte = new PdfPTable(new float[]{5f, 5f, 5f, 5f});
                    tblTransporte.setWidthPercentage(100);
                    PdfPTable tblValor = new PdfPTable(new float[]{5f, 5f, 5f});
                    tblValor.setWidthPercentage(100);
                    PdfPTable tblPostagem = new PdfPTable(new float[]{5f, 5f});
                    tblPostagem.setWidthPercentage(100);
                    PdfPTable tblHistoricoAlteracoes = new PdfPTable(new float[]{5f, 5f, 5f});
                    tblHistoricoAlteracoes.setWidthPercentage(100);

                    /**
                     * Define o formato de n??mero
                     */
                    DecimalFormat df = new DecimalFormat("###,##0.00");

                    /**
                     * Define as c??lulas a serem utilizadas
                     */
                    PdfPCell cell1 = null;
                    PdfPCell cell2 = null;
                    PdfPCell cell3 = null;
                    PdfPCell cell4 = null;
                    PdfPCell cell5 = null;
                    PdfPCell cell6 = null;
                    StringBuilder sb = null;

                    /**
                     * Cria o c??digo de barras
                     */
                    String codToString = String.valueOf(codOp);
                    for (int i = codToString.length() - 1; i < 13; i++) {
                        if (codToString.charAt(i) != 0) {
                            codToString = codToString + "0";
                        }
                    }
                    codToString = new StringBuilder(codToString).reverse().toString();
                    PdfContentByte cb = writer.getDirectContent();
                    BarcodeEAN codeEAN = new BarcodeEAN();
                    codeEAN.setCodeType(codeEAN.EAN13);
                    codeEAN.setCode(codToString);
                    Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
                    Paragraph p = new Paragraph(new Chunk(imageEAN, 0, 0));
                    p.setAlignment(2);
                    document.add(p);
                    document.add(new Paragraph("\n"));

                    /**
                     * Preenche o cabe??alho
                     */
                    cell1 = new PdfPCell(new Phrase("ORCAMENTO BASE: "
                            + op.getOrcBase()
                            + "\n"
                            + "EMISSOR: "
                            + TelaAutenticacao.getUsrLogado().getCodigo()
                            + "\n"
                            + "EMISS??O: "
                            + Controle.dataPadrao.format(op.getDataEmissao()),
                            FontFactory.getFont("arial.ttf", 9)));
                    cell1.setRowspan(2);
                    cell1.setVerticalAlignment(Rectangle.ALIGN_CENTER);
                    if (tipo == 2) {
                        if (prodOrc.getTipoProduto() == 2) {
                            cell2 = new PdfPCell(new Phrase("PEDIDO DE VENDA n?? " + op.getCodigo() + "\nRECIBO DE ENTREGA n?? " + fat.getCod(),
                                    FontFactory.getFont("arial.ttf", 15, Font.BOLD)));
                            cell2.setHorizontalAlignment(1);
                            cell2.setVerticalAlignment(1);
                        } else {
                            cell2 = new PdfPCell(new Phrase("ORDEM DE PRODU????O n?? " + op.getCodigo() + "\nRECIBO DE ENTREGA n?? " + fat.getCod(),
                                    FontFactory.getFont("arial.ttf", 15, Font.BOLD)));
                            cell2.setHorizontalAlignment(1);
                            cell2.setVerticalAlignment(1);
                        }

                    } else {
                        if (prodOrc.getTipoProduto() == 2) {
                            cell2 = new PdfPCell(new Phrase("PEDIDO DE VENDA\n" + op.getCodigo(),
                                    FontFactory.getFont("arial.ttf", 15, Font.BOLD)));
                            cell2.setHorizontalAlignment(1);
                        } else {
                            cell2 = new PdfPCell(new Phrase("ORDEM DE PRODU????O\n" + op.getCodigo(),
                                    FontFactory.getFont("arial.ttf", 15, Font.BOLD)));
                            cell2.setHorizontalAlignment(1);
                        }
                    }
                    cell2.setRowspan(2);
                    cell2.setVerticalAlignment(1);

                    if (prodOrc.getTipoProduto() == 2) {
                        cell3 = new PdfPCell(new Phrase(op.getDataCancelamento() == null
                                ? "DATA DE ENTREGA:\n" + Controle.dataPadrao.format(op.getDataEntrega())
                                : "DATA DE CANCELAMENTO:\n" + Controle.dataPadrao.format(op.getDataCancelamento()), FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                        cell4 = new PdfPCell(new Phrase("DATA DE ENTREGA DA PROVA: N??O ?? O CASO", FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                    } else {
                        cell3 = new PdfPCell(new Phrase(op.getDataCancelamento() == null
                                ? "DATA PROV??VEL DE ENTREGA:\n" + Controle.dataPadrao.format(op.getDataEntrega())
                                : "DATA DE CANCELAMENTO:\n" + Controle.dataPadrao.format(op.getDataCancelamento()), FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                        cell4 = new PdfPCell(new Phrase(op.getDataEntgProva() == null
                                ? "DATA DE ENTREGA DA PROVA: N??O DEFINIDA"
                                : "DATA DE ENTREGA DA PROVA: " + Controle.dataPadrao.format(op.getDataEntgProva()), FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                    }
                    cell3.setHorizontalAlignment(1);
                    cell3.setVerticalAlignment(1);
                    cell4.setHorizontalAlignment(1);
                    cell4.setVerticalAlignment(1);
                    cell3.setBackgroundColor(op.getDataCancelamento() == null ? BaseColor.WHITE : Controle.fundoDestaque);
                    tblCabecalho.addCell(cell1);
                    tblCabecalho.addCell(cell2);
                    tblCabecalho.addCell(cell3);
                    tblCabecalho.addCell(cell4);
                    document.add(tblCabecalho);
                    document.add(new Paragraph("\n"));

                    /**
                     * Preenche as informa????es do cliente, verificando se o tipo
                     * ?? para produ????o ou faturamento
                     */
                    switch (tipo) {
                        case 1:
                            cell1 = new PdfPCell(new Phrase("CLIENTE: " + cliente.getNome(), FontFactory.getFont("arial.ttf", 9)));
                            cell1.setColspan(2);
                            cell5 = new PdfPCell(new Phrase("EMISSOR: \n\n --------------", FontFactory.getFont("arial.ttf", 10)));
                            cell5.setRowspan(4);
                            cell5.setHorizontalAlignment(1);
                            cell1.setBorder(0);
                            cell5.setBorder(0);
                            tblCliente.addCell(cell1);
                            tblCliente.addCell(cell5);

                            /**
                             * Preenche as informa????es do contato
                             */
                            cell2 = new PdfPCell(new Phrase("CONTATO: " + contato.getNomeContato().toString(), FontFactory.getFont("arial.ttf", 9)));
                            cell2.setBorder(0);
                            cell1 = new PdfPCell(new Phrase("TELEFONE: " + contato.getTelefone().toString(), FontFactory.getFont("arial.ttf", 9)));
                            cell1.setBorder(0);
                            cell3 = new PdfPCell(new Phrase("EMAIL: " + contato.getEmail().toString(), FontFactory.getFont("arial.ttf", 9)));
                            cell3.setBorder(0);
                            cell3.setColspan(2);
                            tblCliente.addCell(cell2);
                            tblCliente.addCell(cell1);
                            tblCliente.addCell(cell3);

                            /**
                             * Carrega as informa????es do vendedor
                             */
                            cell1 = new PdfPCell(new Phrase("VENDEDOR: " + OrcamentoDAO.carregaNomeVendedorOp(codOrcBase), FontFactory.getFont("arial.ttf", 9)));

                            cell2 = new PdfPCell(new Phrase("COD CLIENTE: " + op.getCodCliente(), FontFactory.getFont("arial.ttf", 9)));
                            cell2.setBorder(0);
                            cell1.setBorder(0);
                            tblCliente.addCell(cell1);
                            tblCliente.addCell(cell2);
                            cell1 = new PdfPCell(new Phrase("EMISSOR: "
                                    + TelaAutenticacao.getUsrLogado().getNome()
                                    + " - "
                                    + TelaAutenticacao.getUsrLogado().getCodigo(),
                                    FontFactory.getFont("arial.ttf", 9)));
                            cell1.setBorder(0);
                            cell1.setColspan(3);
                            tblCliente.addCell(cell1);
                            document.add(tblCliente);
                            document.add(new Paragraph("\n"));

                            break;
                        case 2:
                            cell1 = new PdfPCell(new Phrase("DESTINAT??RIO",
                                    FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                            cell1.setBackgroundColor(Controle.fundoDestaque);
                            cell1.setColspan(4);
                            cell1.setBorder(0);
                            cell1.setHorizontalAlignment(0);
                            tblIdDest.addCell(cell1);

                            if (op.getTipoPessoa() == 1) {
                                cell1 = new PdfPCell(new Phrase("NOME/RAZ??O SOCIAL - C??DIGO\n\n" + cliente.getNome() + " - " + op.getCodCliente(), FontFactory.getFont("arial.ttf", 9)));
                                cell2 = new PdfPCell(new Phrase("CNPJ/CPF\n\n" + cliente.getCpf(), FontFactory.getFont("arial.ttf", 9)));
                            } else if (op.getTipoPessoa() == 2) {
                                if (cliente.getNomeFantasia() == null) {
                                    cell1 = new PdfPCell(new Phrase("NOME/RAZ??O SOCIAL - C??DIGO\n\n" + cliente.getNome() + " - " + op.getCodCliente(), FontFactory.getFont("arial.ttf", 9)));
                                } else {
                                    cell1 = new PdfPCell(new Phrase("NOME/RAZ??O SOCIAL - C??DIGO\n\n" + cliente.getNome() + " (" + cliente.getNomeFantasia() + ") - " + op.getCodCliente(), FontFactory.getFont("arial.ttf", 9)));
                                }
                                cell2 = new PdfPCell(new Phrase("CNPJ/CPF\n\n" + cliente.getCnpj(), FontFactory.getFont("arial.ttf", 9)));
                            }
                            cell1.setColspan(2);
                            cell2.setColspan(2);
                            tblIdDest.addCell(cell1);
                            tblIdDest.addCell(cell2);

                            /**
                             * Procura o endere??o
                             */
                            Endereco endereco = EnderecoDAO.selInfoEndereco(op.getCodEndereco());

                            /**
                             * Preenche informa????es do endere??o
                             */
                            cell1 = new PdfPCell(new Phrase("LOGADOURO\n\n" + endereco.getLogadouro(), FontFactory.getFont("arial.ttf", 9)));
                            tblIdDest.addCell(cell1);
                            cell2 = new PdfPCell(new Phrase("BAIRRO\n\n" + endereco.getBairro(), FontFactory.getFont("arial.ttf", 9)));
                            cell3 = new PdfPCell(new Phrase("CIDADE\n\n" + endereco.getCidade(), FontFactory.getFont("arial.ttf", 9)));
                            cell4 = new PdfPCell(new Phrase("CEP\n\n" + Endereco.retornaCepFormatado(
                                    endereco.getCep()), FontFactory.getFont("arial.ttf", 9)));
                            cell1 = new PdfPCell(new Phrase("UF\n\n" + endereco.getUf(), FontFactory.getFont("arial.ttf", 9)));

                            /**
                             * Preenche informa????es do contato
                             */
                            tblIdDest.addCell(cell2);
                            tblIdDest.addCell(cell3);
                            tblIdDest.addCell(cell4);
                            tblIdDest.addCell(cell1);
                            cell2 = new PdfPCell(new Phrase("FONE/FAX\n\n" + contato.getTelefone(), FontFactory.getFont("arial.ttf", 9)));
                            cell3 = new PdfPCell(new Phrase("CONTATO\n\n" + contato.getNomeContato(), FontFactory.getFont("arial.ttf", 9)));
                            cell3.setColspan(2);
                            tblIdDest.addCell(cell2);
                            tblIdDest.addCell(cell3);

                            /**
                             * Adiciona as informa????es no documento
                             */
                            document.add(tblIdDest);

                            document.add(new Paragraph("\n"));

                            /**
                             * Adiciona o nome do vendedor
                             */
                            document.add(new Paragraph(new Phrase("VENDEDOR: "
                                    + OrcamentoDAO.carregaNomeVendedorOp(codOrcBase), FontFactory.getFont("arial.ttf", 9))));

                            document.add(new Paragraph("\n"));

                            break;

                    }

                    /**
                     * Verifica se ?? op ou servi??o
                     */
                    if (op.getCodProduto() != 0) {

                        /**
                         * Procura as informa????es do produto
                         */
                        ProdutoBEAN produto = ProdutoDAO.retornaInfoProd(op.getCodProduto(), op.getTipoProduto());

                        /**
                         * Preenche as informa????es do produto
                         */
                        cell1 = new PdfPCell(new Phrase(produto.getDescricao(), FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setColspan(2);
                        cell1.setBorder(0);
                        cell1.setHorizontalAlignment(1);
                        tblProduto.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("QUANTIDADE: " + prodOrc.getQuantidade(), FontFactory.getFont("arial.ttf", 9)));
                        cell1.setBorder(0);
                        tblProduto.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("FORMATO: " + produto.getLargura() + " X " + produto.getAltura(), FontFactory.getFont("arial.ttf", 9)));
                        cell1.setBorder(0);
                        tblProduto.addCell(cell1);

                        if (produto.getTipoProduto().equals("FOLHA") || produto.getTipoProduto().equals("OUTROS")) {
                            cell1 = new PdfPCell(new Phrase("", FontFactory.getFont("arial.ttf", 9)));
                            cell1.setBorder(0);
                        } else {
                            cell1 = new PdfPCell(new Phrase("P??GINAS: " + produto.getQuantidadeFolhas() + " PGS", FontFactory.getFont("arial.ttf", 9)));
                            cell1.setBorder(0);
                        }

                        if (tipo == 1) {
                            tblProduto.addCell(cell1);
                            sb = new StringBuilder().append("OBSERVA????ES: ");
                            if (prodOrc.getObservacaoProduto() == null || prodOrc.getObservacaoProduto().isEmpty()) {
                                sb.append("SEM OBSERVA????ES");
                            } else {
                                sb.append(prodOrc.getObservacaoProduto());
                            }

                            if (prodOrc.getTipoProduto() == 2) {
                                sb.append("\n\nPRAZO DE ENTREGA: 5 A 15 DIAS ??TEIS* AP??S ENVIO DA PROPOSTA "
                                        + "ASSINADA E/OU CARIMBADA PELO OD E/OU COMANDANTE DA OM.");
                            } else {
                                sb.append("\n\nENTREGA: ______ DIAS ??TEIS AP??S A APROVA????O DO 'MODELO DE PROVA'.");
                            }
                            cell2 = new PdfPCell(new Phrase(sb.toString(),
                                    FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                            cell2.setBorder(0);
                            cell2.setBackgroundColor(Controle.fundoDestaque);
                            tblProduto.addCell(cell2);
                        } else {
                            cell1.setColspan(2);
                            tblProduto.addCell(cell1);
                        }

                        document.add(tblProduto);

                        tblProduto.deleteBodyRows();
                        p = new Paragraph("\n");
                        document.add(p);
                        cell1 = new PdfPCell(new Phrase("TIPO PRODUTO: " + produto.getTipoProduto(), FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setBorder(0);
                        cell1.setColspan(3);
                        cell1.setHorizontalAlignment(1);
                        tblProduto.addCell(cell1);
                    } else {
                        cell1 = new PdfPCell(new Phrase("SERVI??OS", FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setColspan(2);
                        cell1.setBorder(0);
                        cell1.setHorizontalAlignment(1);
                        tblProduto.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("QUANTIDADE: -", FontFactory.getFont("arial.ttf", 9)));
                        cell1.setBorder(0);
                        tblProduto.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("FORMATO: -", FontFactory.getFont("arial.ttf", 9)));
                        cell1.setBorder(0);
                        tblProduto.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("P??GINAS: -", FontFactory.getFont("arial.ttf", 9)));
                        cell1.setBorder(0);

                        sb = new StringBuilder().append("OBSERVA????ES: ");
                        if (prodOrc.getObservacaoProduto() == null || prodOrc.getObservacaoProduto().isEmpty()) {
                            sb.append("SEM OBSERVA????ES");
                        } else {
                            sb.append(prodOrc.getObservacaoProduto());
                        }
                        sb.append("\n\nENTREGA: ______ DIAS ??TEIS AP??S A APROVA????O DO 'MODELO DE PROVA'.");
                        cell2 = new PdfPCell(new Phrase(sb.toString(),
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));

                        document.add(tblProduto);
                        tblProduto.deleteBodyRows();
                        p = new Paragraph("\n");
                        document.add(p);
                        cell1 = new PdfPCell(new Phrase("TIPO PRODUTO: " + "SERVI??O", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setBorder(0);
                        cell1.setColspan(3);
                        cell1.setHorizontalAlignment(1);
                        tblProduto.addCell(cell1);
                    }
                    document.add(tblProduto);
                    tblProduto.deleteBodyRows();

                    /**
                     * Verifica se ?? produto ou servi??o
                     */
                    if (op.getCodProduto() != 0) {

                        /**
                         * Verifica se o produto ?? pr ent
                         */
                        if (prodOrc.getTipoProduto() != 2) {

                            /**
                             * Preenche as informa????es dos pap??is
                             */
                            cell1 = new PdfPCell(new Phrase("PAP??IS", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                            cell1.setBorder(Rectangle.BOTTOM);
                            cell1.setColspan(3);
                            tblPapel.addCell(cell1);
                            for (PapelBEAN papel : PapelDAO.carregaPapeisProd(op.getCodProduto())) {
                                cell1 = new PdfPCell(new Phrase("DESCRI????O DO PAPEL:\n" + papel.getDescricaoPapel(), FontFactory.getFont("arial.ttf", 9)));
                                cell1.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                                cell1.setPaddingBottom(7);
                                cell1.setPaddingTop(5);
                                tblPapel.addCell(cell1);
                                cell1 = new PdfPCell(new Phrase("GRAMATURA: " + OrdemProducaoDAO.retornaGramaturaPapel(papel.getCodigo()), FontFactory.getFont("arial.ttf", 9)));
                                cell1.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
                                cell1.setPaddingTop(5);
                                cell1.setPaddingBottom(5);
                                tblPapel.addCell(cell1);
                                cell1 = new PdfPCell(new Phrase("TIPO PAPEL: " + papel.getTipoPapel(), FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                cell1.setBorder(Rectangle.BOX);
                                cell1.setPaddingTop(5);
                                cell1.setPaddingBottom(5);
                                tblPapel.addCell(cell1);
                                for (CalculosOp calculo : OrdemProducaoDAO.retornaCalculosOp(
                                        codOp, prodOrc.getTipoProduto(),
                                        op.getCodProduto(), papel.getTipoPapel())) {
                                    cell2 = new PdfPCell(new Phrase("GASTO DE FOLHAS: " + calculo.getQtdFolhasTotal(), FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                    cell2.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
                                    cell2.setColspan(2);
                                    cell1.setPaddingBottom(5);
                                    tblPapel.addCell(cell2);
                                    cell2 = new PdfPCell(new Phrase("CORES FRENTE: " + papel.getCorFrente(), FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                    cell2.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                                    cell1.setPaddingBottom(5);
                                    tblPapel.addCell(cell2);
                                    cell2 = new PdfPCell(new Phrase("FORMATO DE IMPRESS??O: " + calculo.getFormato(), FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                    cell2.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                                    cell2.setColspan(2);
                                    cell1.setPaddingBottom(20);
                                    tblPapel.addCell(cell2);
                                    cell2 = new PdfPCell(new Phrase("CORES VERSO: " + papel.getCorVerso(), FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                    cell2.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
                                    cell1.setPaddingBottom(5);
                                    tblPapel.addCell(cell2);
                                    document.add(tblPapel);
                                    tblPapel.deleteBodyRows();
                                    cell2 = new PdfPCell(new Phrase("PERDA: " + df.format(calculo.getPerca()) + "%", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                    cell2.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
                                    cell2.setColspan(3);
                                    cell1.setPaddingBottom(5);
                                    tblPapel.addCell(cell2);
                                    document.add(tblPapel);
                                    tblPapel.deleteBodyRows();
                                }
                            }
                            cell1 = new PdfPCell(new Phrase(""));
                            cell1.setBorder(Rectangle.TOP);
                            cell1.setColspan(3);
                            tblPapel.addCell(cell1);
                            document.add(tblPapel);
                            document.add(new Paragraph("\n"));
                        }
                    }

                    /**
                     * Verifica se n??o ?? servi??o
                     */
                    if (op.getCodProduto() != 0) {

                        /**
                         * Verifica se o produto ?? pr ent
                         */
                        if (prodOrc.getTipoProduto() != 2) {

                            /**
                             * Preenche as informa????es sobre as chapas
                             */
                            cell1 = new PdfPCell(new Phrase("CHAPAS", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                            cell1.setBorder(Rectangle.BOTTOM);
                            cell1.setColspan(3);
                            tblChapas.addCell(cell1);
                            for (CalculosOp calculosBEAN : OrdemProducaoDAO.retornaQtdChapas(codOp)) {
                                if (calculosBEAN.getQtdChapas() != 0) {
                                    cell1 = new PdfPCell(new Phrase("C??DIGO PAPEL:  " + calculosBEAN.getCodigoPapel(), FontFactory.getFont("arial.ttf", 9)));
                                    cell1.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                                    cell1.setPaddingBottom(7);
                                    cell1.setPaddingTop(5);
                                    tblChapas.addCell(cell1);
                                    cell1 = new PdfPCell(new Phrase("DESCRI????O DA CHAPA:  " + "CHAPA POSITIVA SM", FontFactory.getFont("arial.ttf", 9)));
                                    cell1.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                                    cell1.setPaddingBottom(7);
                                    cell1.setPaddingTop(5);
                                    tblChapas.addCell(cell1);
                                    cell1 = new PdfPCell(new Phrase("QUANTIDADE: " + calculosBEAN.getQtdChapas(), FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                    cell1.setBorder(Rectangle.BOX);
                                    cell1.setPaddingTop(5);
                                    cell1.setPaddingBottom(5);
                                    tblChapas.addCell(cell1);
                                } else {
                                    cell1 = new PdfPCell(new Phrase("C??DIGO PAPEL:  " + calculosBEAN.getCodigoPapel(), FontFactory.getFont("arial.ttf", 9)));
                                    cell1.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                                    cell1.setPaddingBottom(7);
                                    cell1.setPaddingTop(5);
                                    tblChapas.addCell(cell1);
                                    cell1 = new PdfPCell(new Phrase("NENHUMA SELECIONADA", FontFactory.getFont("arial.ttf", 9)));
                                    cell1.setHorizontalAlignment(1);
                                    cell1.setColspan(2);
                                    tblChapas.addCell(cell1);
                                }
                            }
                            cell1 = new PdfPCell(new Phrase(""));
                            cell1.setBorder(Rectangle.TOP);
                            cell1.setColspan(2);
                            tblChapas.addCell(cell1);
                            document.add(tblChapas);
                            tblChapas.deleteBodyRows();
                            document.add(new Paragraph("\n"));
                        }
                    }

                    /**
                     * Verifica se a op n??o ?? servi??o
                     */
                    if (op.getCodProduto() != 0) {

                        /**
                         * Verifica se o produto n??o ?? pr ent
                         */
                        if (prodOrc.getTipoProduto() != 2) {

                            /**
                             * Preenche os acabamentos
                             */
                            cell1 = new PdfPCell(new Phrase("ACABAMENTOS DA L??MINA", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                            cell1.setBorder(PdfPCell.BOTTOM);
                            cell1.setColspan(2);
                            tblAcabamentos.addCell(cell1);
                            try {
                                for (AcabamentoProdBEAN acabamento : AcabamentoDAO.retornaAcabamentosProduto(op.getCodProduto())) {
                                    cell1 = new PdfPCell(new Phrase("C??DIGO: " + acabamento.getCodigoAcabamento(), FontFactory.getFont("arial.ttf", 9)));
                                    cell1.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                                    tblAcabamentos.addCell(cell1);
                                    cell1 = new PdfPCell(new Phrase("DESCRI????O: " + AcabamentoDAO.retornaDescricaoAcabamentos(acabamento.getCodigoAcabamento()), FontFactory.getFont("arial.ttf", 9)));
                                    cell1.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
                                    tblAcabamentos.addCell(cell1);
                                }
                            } catch (SemAcabamentoException ex) {
                                //NENHUMA A????O
                            }

                            if (tblAcabamentos.size() == 1) {
                                cell1 = new PdfPCell(new Phrase("NENHUM SELECIONADO", FontFactory.getFont("arial.ttf", 9)));
                                cell1.setHorizontalAlignment(1);
                                cell1.setColspan(2);
                                tblAcabamentos.addCell(cell1);
                            }
                            cell1 = new PdfPCell(new Phrase(""));
                            cell1.setBorder(Rectangle.TOP);
                            cell1.setColspan(2);
                            tblAcabamentos.addCell(cell1);
                            document.add(tblAcabamentos);
                            tblAcabamentos.deleteBodyRows();
                            document.add(new Paragraph("\n"));
                            document.add(new Paragraph("\n"));
                        }
                    }

                    /**
                     * Preenche os servi??os
                     */
                    cell1 = new PdfPCell(new Phrase(" SERVI??OS DO OR??AMENTO", FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                    cell1.setBackgroundColor(Controle.fundoDestaque);
                    cell1.setBorder(0);
                    cell1.setColspan(3);
                    cell1.setHorizontalAlignment(0);
                    tblProduto.addCell(cell1);
                    document.add(tblProduto);
                    tblProduto.deleteBodyRows();

                    for (Servicos servicos : OrdemProducaoDAO.retornaComponentesOrcamento(codOrcBase)) {
                        cell1 = new PdfPCell(new Phrase("C??DIGO: " + servicos.getCod(), FontFactory.getFont("arial.ttf", 9)));
                        cell1.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
                        tblServicos.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("DESCRI????O: " + OrdemProducaoDAO.retornaDescricaoServico(servicos.getCod()), FontFactory.getFont("arial.ttf", 9)));
                        cell1.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
                        tblServicos.addCell(cell1);
                    }
                    if (tblServicos.size() == 0) {
                        cell1 = new PdfPCell(new Phrase("NENHUM SELECIONADO", FontFactory.getFont("arial.ttf", 9)));
                        cell1.setHorizontalAlignment(1);
                        cell1.setColspan(2);
                        tblServicos.addCell(cell1);
                    }
                    cell1 = new PdfPCell(new Phrase(""));
                    cell1.setBorder(Rectangle.TOP);
                    cell1.setColspan(2);
                    tblServicos.addCell(cell1);
                    document.add(tblServicos);
                    tblServicos.deleteBodyRows();
                    document.add(new Paragraph("\n"));

                    /**
                     * Preenche as informa????es sobre a ordem de produ????o
                     */
                    if (prodOrc.getTipoProduto() == 2) {
                        cell1 = new PdfPCell(new Phrase(" OBSERVA????ES DO PEDIDO DE VENDA",
                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                    } else {
                        cell1 = new PdfPCell(new Phrase(" OBSERVA????ES DA ORDEM DE PRODU????O",
                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                    }

                    cell1.setBackgroundColor(Controle.fundoDestaque);
                    cell1.setBorder(0);
                    cell1.setHorizontalAlignment(0);
                    tblObservacoes.addCell(cell1);
                    cell1 = new PdfPCell(new Phrase(OrdemProducaoDAO.retornaObservacao(codOp).isEmpty() ? "SEM OBSERVA????ES" : OrdemProducaoDAO.retornaObservacao(codOp), FontFactory.getFont("arial.ttf", 9)));
                    cell1.setHorizontalAlignment(1);
                    cell1.setBorder(Rectangle.BOX);
                    tblObservacoes.addCell(cell1);
                    document.add(tblObservacoes);
                    tblObservacoes.deleteBodyRows();

                    document.add(new Paragraph("\n"));

                    if (historico) {
                        cell1 = new PdfPCell(new Phrase("HIST??RICO DE PRODU????O",
                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setColspan(3);
                        cell1.setBorder(0);
                        cell1.setHorizontalAlignment(0);
                        tblHistoricoAlteracoes.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("ALTERA????O",
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell1.setHorizontalAlignment(1);
                        tblHistoricoAlteracoes.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("DATA E HORA",
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell1.setHorizontalAlignment(1);
                        tblHistoricoAlteracoes.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("USU??RIO",
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell1.setHorizontalAlignment(1);
                        tblHistoricoAlteracoes.addCell(cell1);
                        for (AlteracoesOP alteracao : OrdemProducaoDAO.retornaAlteracoes(codOp)) {
                            cell1 = new PdfPCell(new Phrase(alteracao.getAlteracaoDesc(),
                                    FontFactory.getFont("arial.ttf", 6)));
                            cell1.setHorizontalAlignment(0);
                            tblHistoricoAlteracoes.addCell(cell1);

                            cell1 = new PdfPCell(new Phrase(Controle.dataPadrao.format(alteracao.getDataHora()) + " " + Controle.horaPadrao.format(alteracao.getDataHora()),
                                    FontFactory.getFont("arial.ttf", 6)));
                            cell1.setHorizontalAlignment(0);
                            tblHistoricoAlteracoes.addCell(cell1);

                            cell1 = new PdfPCell(new Phrase(alteracao.getUsuario(),
                                    FontFactory.getFont("arial.ttf", 6)));
                            cell1.setHorizontalAlignment(0);
                            tblHistoricoAlteracoes.addCell(cell1);
                        }
                        document.add(tblHistoricoAlteracoes);
                    }

                    document.add(new Paragraph("\n"));

                    /**
                     * Se for para o faturamento, acrescenta informa????es
                     * adicionais
                     */
                    if (tipo == 2) {
                        /**
                         * Armazena dados para uso posterior
                         */
                        Double vlrUnit = 0d;
                        Double vlrFrete = OrcamentoDAO.retornaValorFrete(codOrcBase);
                        Double vlrSv = ServicoDAO.retornaVlrSvOrcExistente(codOrcBase);

                        /**
                         * Preenche o hist??rico de recibos
                         */
                        cell1 = new PdfPCell(new Phrase("HIST??RICO DE RECIBOS DE ENTREGA",
                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setColspan(6);
                        cell1.setBorder(0);
                        cell1.setHorizontalAlignment(0);
                        tblHistorico.addCell(cell1);

                        cell1 = new PdfPCell(new Phrase("C??DIGO DO RECIBO\n\n" + fat.getCod(),
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell2 = new PdfPCell(new Phrase("DATA DE ENTREGA\n\n"
                                + Controle.dataPadrao.format(fat.getDtFat()),
                                FontFactory.getFont("arial.ttf", 9)));
                        cell3 = new PdfPCell(new Phrase("QUANTIDADE ENTREGUE\n\n" + fat.getQtdEntregue(),
                                FontFactory.getFont("arial.ttf", 9)));
                        sb = new StringBuilder().append("VALOR FRETE\n\nR$ ")
                                .append(fat.getFreteFat() == 1 ? df.format(vlrFrete)
                                        : "0,00");
                        cell4 = new PdfPCell(new Phrase(sb.toString(),
                                FontFactory.getFont("arial.ttf", 9)));
                        sb = new StringBuilder().append("VALOR SERVI??OS\n\nR$ ")
                                .append(fat.getFreteFat() == 1 ? df.format(vlrSv)
                                        : "0,00");
                        cell5 = new PdfPCell(new Phrase(sb.toString(),
                                FontFactory.getFont("arial.ttf", 9)));
                        cell6 = new PdfPCell(new Phrase("VALOR FATURADO\n\n" + "R$ " + df.format(fat.getVlrFat()),
                                FontFactory.getFont("arial.ttf", 9, BaseColor.WHITE)));
                        cell6.setBackgroundColor(BaseColor.GRAY);

                        tblHistorico.addCell(cell1);
                        tblHistorico.addCell(cell2);
                        tblHistorico.addCell(cell3);
                        tblHistorico.addCell(cell4);
                        tblHistorico.addCell(cell5);
                        tblHistorico.addCell(cell6);

                        sb = new StringBuilder().append("OBSERVA????ES\n\n");
                        if (fat.getObservacoes() == null || fat.getObservacoes().isEmpty()) {
                            sb.append("SEM OBSERVA????ES");
                        } else {
                            sb.append(fat.getObservacoes());
                        }
                        cell1 = new PdfPCell(new Phrase(sb.toString(),
                                FontFactory.getFont("arial.ttf", 9)));
                        cell1.setColspan(6);
                        tblHistorico.addCell(cell1);

                        /**
                         * Preenche recibos anteriores, se houver
                         */
                        List<Faturamento> recibos
                                = NotaDAO.retornaRecibos(fat.getCod(), fat.getCodOrc(), fat.getCodOp());
                        if (recibos != null) {

                            for (Faturamento recibo : recibos) {
                                cell1 = new PdfPCell(new Phrase("C??DIGO DO RECIBO\n\n" + recibo.getCod(),
                                        FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                                cell2 = new PdfPCell(new Phrase("DATA DE ENTREGA\n\n"
                                        + Controle.dataPadrao.format(recibo.getDtFat()),
                                        FontFactory.getFont("arial.ttf", 9)));
                                cell3 = new PdfPCell(new Phrase("QUANTIDADE ENTREGUE\n\n" + recibo.getQtdEntregue(),
                                        FontFactory.getFont("arial.ttf", 9)));
                                sb = new StringBuilder().append("VALOR FRETE\n\nR$ ")
                                        .append(fat.getFreteFat() == 1 ? df.format(vlrFrete)
                                                : "0,00");
                                cell4 = new PdfPCell(new Phrase(sb.toString(),
                                        FontFactory.getFont("arial.ttf", 9)));
                                sb = new StringBuilder().append("VALOR SERVI??OS\n\nR$ ")
                                        .append(fat.getFreteFat() == 1 ? df.format(vlrSv)
                                                : "0,00");
                                cell5 = new PdfPCell(new Phrase(sb.toString(),
                                        FontFactory.getFont("arial.ttf", 9)));
                                cell6 = new PdfPCell(new Phrase("VALOR FATURADO\n\n" + "R$ " + df.format(recibo.getVlrFat()),
                                        FontFactory.getFont("arial.ttf", 9, BaseColor.WHITE)));
                                cell6.setBackgroundColor(BaseColor.GRAY);

                                tblHistorico.addCell(cell1);
                                tblHistorico.addCell(cell2);
                                tblHistorico.addCell(cell3);
                                tblHistorico.addCell(cell4);
                                tblHistorico.addCell(cell5);
                                tblHistorico.addCell(cell6);

                                sb = new StringBuilder().append("OBSERVA????ES\n\n");
                                if (recibo.getObservacoes() == null || recibo.getObservacoes().isEmpty()) {
                                    sb.append("SEM OBSERVA????ES");
                                } else {
                                    sb.append(recibo.getObservacoes());
                                }
                                cell1 = new PdfPCell(new Phrase(sb.toString(),
                                        FontFactory.getFont("arial.ttf", 9)));
                                cell1.setColspan(6);
                                tblHistorico.addCell(cell1);

                            }
                            /**
                             * Adiciona ao documento
                             */
                            document.add(tblHistorico);
                        }

                        document.add(new Paragraph("\n"));

                        /**
                         * Adiciona informa????es de valores
                         */
                        cell1 = new PdfPCell(new Phrase("TOTAL",
                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setColspan(5);
                        cell1.setBorder(0);
                        cell1.setHorizontalAlignment(0);
                        tblValores.addCell(cell1);

                        if (codOp == 0) {
                            vlrUnit = ProdutoDAO.retornaVlrPe(op.getCodProduto());
                            cell1 = new PdfPCell(new Phrase("VALOR UNIT??RIO\n\nR$ " + df.format(vlrUnit),
                                    FontFactory.getFont("arial.ttf", 9)));
                        } else {
                            vlrUnit = (double) prodOrc.getPrecoUnitario();
                            cell1 = new PdfPCell(new Phrase("VALOR UNIT??RIO\n\nR$ " + df.format(vlrUnit),
                                    FontFactory.getFont("arial.ttf", 9)));
                        }

                        cell2 = new PdfPCell(new Phrase("QUANTIDADE SOLICITADA\n\n" + prodOrc.getQuantidade(),
                                FontFactory.getFont("arial.ttf", 9)));

                        cell3 = new PdfPCell(new Phrase("VALOR FRETE\n\n"
                                + "R$ " + (fat.getFreteFat() == 1
                                ? df.format(vlrFrete)
                                : "0,00"),
                                FontFactory.getFont("arial.ttf", 9)));
                        cell4 = new PdfPCell(new Phrase("VALOR SERVI??OS\n\nR$ " + (fat.getFreteFat() == 1
                                ? df.format(vlrSv)
                                : "0,00"), FontFactory.getFont("arial.ttf", 9)));

                        if (fat.getFreteFat() == 1 && fat.getServicosFat() == 1) {
                            cell5 = new PdfPCell(new Phrase("VALOR TOTAL FATURADO\n\nR$ "
                                    + df.format((vlrUnit * prodOrc.getQuantidade()) + vlrFrete + vlrSv),
                                    FontFactory.getFont("arial.ttf", 9, BaseColor.WHITE)));
                        } else if (fat.getFreteFat() == 1 && fat.getServicosFat() == 0) {
                            cell5 = new PdfPCell(new Phrase("VALOR TOTAL FATURADO\n\nR$ "
                                    + df.format((vlrUnit * prodOrc.getQuantidade()) + vlrFrete),
                                    FontFactory.getFont("arial.ttf", 9, BaseColor.WHITE)));
                        } else if (fat.getFreteFat() == 0 && fat.getServicosFat() == 1) {
                            cell5 = new PdfPCell(new Phrase("VALOR TOTAL FATURADO\n\nR$ "
                                    + df.format((vlrUnit * prodOrc.getQuantidade()) + vlrSv),
                                    FontFactory.getFont("arial.ttf", 9, BaseColor.WHITE)));
                        } else if (fat.getFreteFat() == 0 && fat.getServicosFat() == 0) {
                            cell5 = new PdfPCell(new Phrase("VALOR TOTAL FATURADO\n\nR$ "
                                    + df.format((vlrUnit * prodOrc.getQuantidade())),
                                    FontFactory.getFont("arial.ttf", 9, BaseColor.WHITE)));
                        }

                        cell5.setBackgroundColor(BaseColor.GRAY);
                        tblValores.addCell(cell1);
                        tblValores.addCell(cell2);
                        tblValores.addCell(cell3);
                        tblValores.addCell(cell4);
                        tblValores.addCell(cell5);

                        /**
                         * Adiciona ao documento
                         */
                        document.add(tblValores);

                        document.add(new Paragraph("\n"));

                        /**
                         * Adiciona informa????es de transporte
                         */
                        /**
                         * Procura as informa????es do transporte
                         */
                        TransporteBEAN transporte = NotaDAO.selTransporte(fat.getCod());

                        /**
                         * Preenche a tabela
                         */
                        cell1 = new PdfPCell(new Phrase("TRANSPORTADOR/VOLUMES TRANSPORTADOS",
                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setColspan(4);
                        cell1.setBorder(0);
                        cell1.setHorizontalAlignment(0);
                        tblTransporte.addCell(cell1);

                        cell1 = new PdfPCell(new Phrase("RAZ??O SOCIAL\n\n" + transporte.getNomeTransportador(), FontFactory.getFont("arial.ttf", 9)));
                        cell1.setColspan(2);
                        cell2 = new PdfPCell(new Phrase("MODALIDADE\n\n" + transporte.getModalidadeFrete(), FontFactory.getFont("arial.ttf", 9)));
                        cell3 = new PdfPCell(new Phrase("PESO PRODUTO\n\n" + transporte.getPesoProduto(), FontFactory.getFont("arial.ttf", 9)));
                        tblTransporte.addCell(cell1);
                        tblTransporte.addCell(cell2);
                        tblTransporte.addCell(cell3);

                        /**
                         * Adiciona informa????es de volumes
                         */
                        cell1 = new PdfPCell(new Phrase("RELA????O DE VOLUMES", FontFactory.getFont("arial.ttf", 9)));
                        cell1.setColspan(4);
                        tblTransporte.addCell(cell1);

                        cell1 = new PdfPCell(new Phrase("N??MERO", FontFactory.getFont("arial.ttf", 9)));
                        cell2 = new PdfPCell(new Phrase("ALTURA", FontFactory.getFont("arial.ttf", 9)));
                        cell3 = new PdfPCell(new Phrase("LARGURA", FontFactory.getFont("arial.ttf", 9)));
                        cell4 = new PdfPCell(new Phrase("PESO", FontFactory.getFont("arial.ttf", 9)));
                        tblTransporte.addCell(cell1);
                        tblTransporte.addCell(cell2);
                        tblTransporte.addCell(cell3);
                        tblTransporte.addCell(cell4);

                        List<VolumeBEAN> volumes = NotaDAO.selecionaVolumes(transporte.getCod());
                        if (volumes.isEmpty()) {
                            cell1 = new PdfPCell(new Phrase("NENHUM REGISTRADO",
                                    FontFactory.getFont("arial.ttf", 9)));
                            cell1.setColspan(4);
                            cell1.setHorizontalAlignment(1);
                            tblTransporte.addCell(cell1);
                        } else {
                            for (VolumeBEAN volume : volumes) {
                                cell1 = new PdfPCell(new Phrase(String.valueOf(volume.getNumeroVolume()), FontFactory.getFont("arial.ttf", 9)));
                                cell2 = new PdfPCell(new Phrase(df.format(volume.getAlturaVolume()), FontFactory.getFont("arial.ttf", 9)));
                                cell3 = new PdfPCell(new Phrase(df.format(volume.getLarguraVolume()), FontFactory.getFont("arial.ttf", 9)));
                                cell4 = new PdfPCell(new Phrase(df.format(volume.getPesoVolume()), FontFactory.getFont("arial.ttf", 9)));
                                tblTransporte.addCell(cell1);
                                tblTransporte.addCell(cell2);
                                tblTransporte.addCell(cell3);
                                tblTransporte.addCell(cell4);
                            }
                        }
                        document.add(tblTransporte);

                        document.add(new Paragraph("\n"));

                        /**
                         * Informa????es local, data
                         */
                        p = new Paragraph("Quartel em Bras??lia-DF, "
                                + Controle.dataPadrao.format(new Date()) + " "
                                + Controle.horaPadrao.format(new Date()),
                                FontFactory.getFont("arial.ttf", 9));
                        p.setAlignment(1);
                        document.add(p);

                        document.add(new Paragraph("\n"));

                        /**
                         * Campo de assinatura do emissor
                         */
                        p = new Paragraph("_________________________________________");
                        p.setAlignment(1);
                        document.add(p);
                        p = new Paragraph(TelaAutenticacao.getUsrLogado().getNome(),
                                FontFactory.getFont("arial.ttf", 9));
                        p.setAlignment(1);
                        document.add(p);
                        p = new Paragraph("GR??FICA DO EX??RCITO - DIVIS??O COMERCIAL",
                                FontFactory.getFont("arial.ttf", 9));
                        p.setAlignment(1);
                        document.add(p);

                        document.add(new Paragraph("\n"));

                        /**
                         * Campo de assinatura do cliente
                         */
                        p = new Paragraph("_________________________________________");
                        p.setAlignment(1);
                        document.add(p);
                        p = new Paragraph("NOME: ______________ DOCUMENTO: ______________ EMISSOR: ________", FontFactory.getFont("arial.ttf", 9));
                        p.setAlignment(1);
                        document.add(p);
                        p = new Paragraph(cliente.getTipoPessoa() == 1
                                ? cliente.getNome()
                                : (cliente.getNome() + " - " + cliente.getNomeFantasia()),
                                FontFactory.getFont("arial.ttf", 9));
                        p.setAlignment(1);
                        document.add(p);

                    } /**
                     * Se n??o for para faturamento, acrescenta o valor unit??rio
                     * dos produtos e os dados de postagem do produto
                     */
                    else {
                        cell1 = new PdfPCell(new Phrase("DADOS DE POSTAGEM",
                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        cell1.setBackgroundColor(Controle.fundoDestaque);
                        cell1.setBorder(0);
                        cell1.setHorizontalAlignment(0);
                        cell1.setColspan(2);
                        tblPostagem.addCell(cell1);
                        cell1 = new PdfPCell(new Phrase("ENDERE??O DE ENTREGA",
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell2 = new PdfPCell(new Phrase(EnderecoDAO.retornaEndereco(op.getCodEndereco()),
                                FontFactory.getFont("arial.ttf", 9)));
                        tblPostagem.addCell(cell1);
                        tblPostagem.addCell(cell2);
                        cell1 = new PdfPCell(new Phrase("OP ASSOCIADAS A PROPOSTA",
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell2 = new PdfPCell(new Phrase(OrdemProducaoDAO.retornaOpsAssociadas(op.getOrcBase(), false).toString(),
                                FontFactory.getFont("arial.ttf", 9)));
                        tblPostagem.addCell(cell1);
                        tblPostagem.addCell(cell2);
                        cell1 = new PdfPCell(new Phrase("OBSERVA????ES DO FRETE",
                                FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell2 = new PdfPCell(new Phrase(op.getObsFrete(),
                                FontFactory.getFont("arial.ttf", 9)));
                        tblPostagem.addCell(cell1);
                        tblPostagem.addCell(cell2);
                        document.add(tblPostagem);

                        cell1 = new PdfPCell(new Phrase("QUANTIDADE", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell2 = new PdfPCell(new Phrase("VALOR UNIT??RIO", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        cell3 = new PdfPCell(new Phrase("VALOR TOTAL", FontFactory.getFont("arial.ttf", 9, Font.BOLD)));
                        tblValor.addCell(cell1);
                        tblValor.addCell(cell2);
                        tblValor.addCell(cell3);
                        cell1 = new PdfPCell(new Phrase(String.valueOf(prodOrc.getQuantidade()),
                                FontFactory.getFont("arial.ttf", 9)));
                        cell2 = new PdfPCell(new Phrase("R$ " + df.format(OrcamentoDAO.retornaValorUnitario(op.getOrcBase(),
                                op.getCodProduto())), FontFactory.getFont("arial.ttf", 9)));
                        cell3 = new PdfPCell(new Phrase("R$ " + df.format(OrcamentoDAO.retornaVlrParcProd(op.getOrcBase(),
                                prodOrc.getTipoProduto(), op.getCodProduto())), FontFactory.getFont("arial.ttf", 9)));
                        tblValor.addCell(cell1);
                        tblValor.addCell(cell2);
                        tblValor.addCell(cell3);
                        document.add(tblValor);
                    }

                    //FECHA A INST??NCIA DO DOCUMENTO------------------------------------
                    document.close();
                    //------------------------------------------------------------------

                    //ABRE O DOCUMENTO--------------------------------------------------
                    java.awt.Desktop.getDesktop().open(new File(System.getProperty("java.io.tmpdir") + "/ordemProducao" + codOp + ".pdf"));
                    //------------------------------------------------------------------
                } catch (SQLException | DocumentException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(null);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null,
                            "O ARQUIVO EST?? SENDO UTILIZADO POR OUTRO PROCESSO.\nVERIFIQUE E TENTE NOVAMENTE",
                            "ARQUIVO ABERTO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }.start();
    }

    /**
     * @param jTable tabelaControle
     * @param classTabela 1 - TELA ACOMPANHAMENTO 2 - CONSULTA ORCAMENTO 3 -
     * CONSULTA OR??AMENTO EXTERNO
     */
    public synchronized static void corTabela(JTable jTable, byte classTabela) {
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

                            byte STATUS = 0;
                            String[] statusSplit = null;

                            switch (classTabela) {
                                case 1:
                                    statusSplit = jTable.getValueAt(
                                            row, 3).toString().split(" ");
                                    STATUS = (byte) Byte.valueOf(statusSplit[0]);
                                    break;
                                case 2:
                                    statusSplit = jTable.getValueAt(
                                            row, 7).toString().split(" ");
                                    STATUS = (byte) Byte.valueOf(statusSplit[0]);
                                    break;
                                case 3:
                                    statusSplit = jTable.getValueAt(
                                            row, 4).toString().split(" ");
                                    STATUS = (byte) Byte.valueOf(statusSplit[0]);
                                    break;
                            }

                            switch (STATUS) {
                                case 1:
                                    c = Color.getColor("vermelho1");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 2:
                                    c = Color.getColor("amarelo1");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 3:
                                    c = Color.getColor("amarelo2");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 4:
                                    c = Color.getColor("amarelo3");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 5:
                                    c = Color.getColor("vermelho2");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 6:
                                    c = Color.getColor("verde1");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 7:
                                    c = Color.getColor("verde1");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 8:
                                    c = Color.getColor("verde2");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 9:
                                    c = Color.getColor("verde3");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 10:
                                    c = Color.WHITE;
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 11:
                                    c = Color.WHITE;
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 12:
                                    c = Color.getColor("verde4");
                                    label.setForeground(Color.BLACK);
                                    break;
                                case 13:
                                    c = Color.BLACK;
                                    label.setForeground(Color.WHITE);
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

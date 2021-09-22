/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.sisgrafex;

import ui.cadastros.produtos.AcabamentoProdBEAN;
import model.dao.ProdutoDAO;
import ui.controle.Controle;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import exception.EnvioExcecao;
import exception.SemAcabamentoException;
import exception.SemServicoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import model.dao.OrcamentoDAO;
import model.dao.OrdemProducaoDAO;
import ui.cadastros.acabamentos.AcabamentoDAO;
import model.dao.ClienteDAO;
import model.dao.PapelDAO;
import model.dao.ServicoDAO;
import ui.login.TelaAutenticacao;

/**
 *
 * @author spd3
 */
public class Orcamento {

    private int codigo;
    private int codigoCliente;
    private String codigoEmissor;
    private Date dataValidade;
    private Date dataEmissao;
    private int quantidade;
    private float valorUnitario;
    private float sif;
    private int status;
    private String descricao;
    private int tipoPessoa;
    private float desconto;
    private int codProduto;
    private float valorTotal;
    private String nomeContato;
    private String telefoneContato;
    private int codContato;
    private int codEndereco;
    private int precosManuais;
    private String nomeCliente;
    private Double frete;
    private Double arte;
    private String descricaoProduto;
    private int codigoOp;
    private int codigoLI;

    //TABELA CONSULTA
    private String tipoPessoaString;
    private String dataEmissaoString;
    private String dataValidadeString;
    private String statusString;

    public Orcamento(int codigoCliente,
            int codContato,
            int codEndereco,
            Double frete,
            Double arte,
            int tipoPessoa) {
        this.codigoCliente = codigoCliente;
        this.codContato = codContato;
        this.codEndereco = codEndereco;
        this.frete = frete;
        this.arte = arte;
        this.tipoPessoa = tipoPessoa;
    }

    public Orcamento(int codigo,
            int codigoCliente,
            String codigoEmissor,
            Date dataValidade,
            Date dataEmissao,
            float sif,
            int status,
            String descricao,
            int tipoPessoa,
            float desconto,
            float valorTotal,
            int codContato,
            int codEndereco,
            int precosManuais,
            Double frete,
            Double arte) {
        this.codigo = codigo;
        this.codigoCliente = codigoCliente;
        this.codigoEmissor = codigoEmissor;
        this.dataValidade = dataValidade;
        this.dataEmissao = dataEmissao;
        this.sif = sif;
        this.status = status;
        this.descricao = descricao;
        this.tipoPessoa = tipoPessoa;
        this.desconto = desconto;
        this.valorTotal = valorTotal;
        this.codContato = codContato;
        this.codEndereco = codEndereco;
        this.precosManuais = precosManuais;
        this.frete = frete;
        this.arte = arte;
    }
    
    public Orcamento(int codigo,
            int codigoLI,
            int codigoCliente,
            String codigoEmissor,
            Date dataValidade,
            Date dataEmissao,
            float sif,
            int status,
            String descricao,
            int tipoPessoa,
            float desconto,
            float valorTotal,
            int codContato,
            int codEndereco,
            int precosManuais,
            Double frete,
            Double arte) {
        this.codigo = codigo;
        this.codigoLI = codigoLI;
        this.codigoCliente = codigoCliente;
        this.codigoEmissor = codigoEmissor;
        this.dataValidade = dataValidade;
        this.dataEmissao = dataEmissao;
        this.sif = sif;
        this.status = status;
        this.descricao = descricao;
        this.tipoPessoa = tipoPessoa;
        this.desconto = desconto;
        this.valorTotal = valorTotal;
        this.codContato = codContato;
        this.codEndereco = codEndereco;
        this.precosManuais = precosManuais;
        this.frete = frete;
        this.arte = arte;
    }

    public Orcamento(int codigo, String dataEmissao, String dataValidade, float valorTotal, String status) {
        this.codigo = codigo;
        this.dataValidadeString = dataValidade;
        this.dataEmissaoString = dataEmissao;
        this.statusString = status;
        this.valorTotal = valorTotal;
    }
    
    public Orcamento(int codigo, String dataEmissao, String dataValidade, float valorTotal, String statusString, int status) {
        this.codigo = codigo;
        this.dataValidadeString = dataValidade;
        this.dataEmissaoString = dataEmissao;
        this.statusString = statusString;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public Orcamento(int codigo, String nomeCliente, String tipoPessoa, String dataEmissao, String dataValidade, float valorTotal, String status, String codEmissor) {
        this.codigo = codigo;
        this.dataValidadeString = dataValidade;
        this.dataEmissaoString = dataEmissao;
        this.statusString = status;
        this.tipoPessoaString = tipoPessoa;
        this.nomeCliente = nomeCliente;
        this.valorTotal = valorTotal;
        this.codigoEmissor = codEmissor;
    }

    public Orcamento(int codigo,
            int codCliente,
            int tipoPessoa) {
        this.codigo = codigo;
        this.codigoCliente = codCliente;
        this.tipoPessoa = tipoPessoa;
    }

    public Orcamento() {
    }
    
    public Orcamento(int codigo, 
            Date dataValidade,
            int status){
        this.codigo = codigo;
        this.dataValidade = dataValidade;
        this.status = status;
    }

    public int getCodigoLI() {
        return codigoLI;
    }

    public void setCodigoLI(int codigoLI) {
        this.codigoLI = codigoLI;
    }
    
    public Double getArte() {
        return arte;
    }

    public void setArte(Double arte) {
        this.arte = arte;
    }

    public int getCodigoOp() {
        return codigoOp;
    }

    public void setCodigoOp(int codigoOp) {
        this.codigoOp = codigoOp;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Double getFrete() {
        return frete;
    }

    public String getDataEmissaoString() {
        return dataEmissaoString;
    }

    public void setDataEmissaoString(String dataEmissaoString) {
        this.dataEmissaoString = dataEmissaoString;
    }

    public String getDataValidadeString() {
        return dataValidadeString;
    }

    public void setDataValidadeString(String dataValidadeString) {
        this.dataValidadeString = dataValidadeString;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public void setFrete(Double frete) {
        this.frete = frete;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getPrecosManuais() {
        return precosManuais;
    }

    public void setPrecosManuais(int precosManuais) {
        this.precosManuais = precosManuais;
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

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getTelefoneContato() {
        return telefoneContato;
    }

    public void setTelefoneContato(String telefoneContato) {
        this.telefoneContato = telefoneContato;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public int getCod_produto() {
        return codProduto;
    }

    public void setCod_produto(int cod_produto) {
        this.codProduto = cod_produto;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public String getCodEmissor() {
        return codigoEmissor;
    }

    public void setCodEmissor(String cod_emissor) {
        this.codigoEmissor = cod_emissor;
    }

    public int getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(int tipo_pessoa) {
        this.tipoPessoa = tipo_pessoa;
    }

    public int getCod() {
        return codigo;
    }

    public void setCod(int cod) {
        this.codigo = cod;
    }

    public int getCodCliente() {
        return codigoCliente;
    }

    public void setCodCliente(int cod_cliente) {
        this.codigoCliente = cod_cliente;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getValorUnitario() {
        return valorUnitario;
    }

    public void setValor_unitario(float valor_unitario) {
        this.valorUnitario = valor_unitario;
    }

    public float getCif() {
        return sif;
    }

    public void setCif(float sif) {
        this.sif = sif;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoPessoaString() {
        return tipoPessoaString;
    }

    public void setTipoPessoaString(String tipoPessoaString) {
        this.tipoPessoaString = tipoPessoaString;
    }

    /**
     * Gera o Pdf do orçamento
     *
     * @param codOrc código do orçamento
     * @param consulta se é consulta
     * @param detalhado se é detalhado
     * @param status status do orçamento
     * @param loading imagem de carregamento
     * @param email se é para enviar como email
     * @return
     */
    public static String geraPdf(int codOrc,
            Boolean consulta,
            Boolean detalhado,
            int status,
            JLabel loading,
            Boolean email) {

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, 30, 20, 20, 30);

        String caminho = System.getProperty("java.io.tmpdir") + "/orcamento" + codOrc + ".pdf";

        new Thread("Gerar PDF orçamento") {
            @Override
            public void run() {
                loading.setText("GERANDO PDF...");
                loading.setVisible(true);
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(System.getProperty("java.io.tmpdir") + "/orcamento" + codOrc + ".pdf"));

                    document.setMargins(20, 20, 20, 20);
                    document.setPageSize(PageSize.A4);
                    document.open();

                    Image imagem = Image.getInstance(getClass().getResource("/ui/orcamentos/operacoes/cabecalhoPropostaPng.png"));
                    imagem.setAlignment(0);
                    imagem.scaleToFit(500, 1000);
                    document.add(imagem);

                    Paragraph p = new Paragraph("\n");
                    document.add(p);

                    PdfPTable tabelaCabecalho = new PdfPTable(new float[]{1f});
                    tabelaCabecalho.setWidthPercentage(100);
                    PdfPCell celulaCabecalho = null;

                    List<ProdOrcamento> produtos
                            = OrcamentoDAO.carregaProdOrc(codOrc);

                    if (consulta == true) {
                        switch (status) {
                            case 1:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 10:
                            case 11:
                            case 13:
                                if (produtos.get(0).getTipoProduto() == 2) {
                                    celulaCabecalho = new PdfPCell(new Phrase("PROPOSTA DE PEDIDO DE VENDA Nº "
                                            + codOrc,
                                            FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                                } else {
                                    celulaCabecalho = new PdfPCell(new Phrase("PROPOSTA DE ORÇAMENTO Nº "
                                            + codOrc,
                                            FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                                }
                                break;
                            case 2:
                            case 7:
                            case 9:
                                List nOp = OrdemProducaoDAO.retornaCodOpOrc(codOrc);
                                switch (OrdemProducaoDAO.retornaTipoProduto((int) nOp.get(0))) {
                                    case 1:
                                        celulaCabecalho = new PdfPCell(new Phrase("PROPOSTA DE ORÇAMENTO Nº "
                                                + codOrc + " / ORDEM DE PRODUÇÃO Nº " + nOp,
                                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                                        break;
                                    case 2:
                                        celulaCabecalho = new PdfPCell(new Phrase("PROPOSTA DE ORÇAMENTO Nº "
                                                + codOrc + " / PEDIDO DE VENDA Nº " + nOp,
                                                FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                                        break;
                                }
                                break;

                        }
                    } else {
                        if (produtos.get(0).getTipoProduto() == 2) {
                            celulaCabecalho = new PdfPCell(new Phrase("PROPOSTA DE PEDIDO DE VENDA Nº "
                                    + codOrc,
                                    FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        } else {
                            celulaCabecalho = new PdfPCell(new Phrase("PROPOSTA DE ORÇAMENTO Nº "
                                    + codOrc,
                                    FontFactory.getFont("arial.ttf", 12, Font.BOLD)));
                        }
                    }
                    celulaCabecalho.setHorizontalAlignment(1);
                    celulaCabecalho.setBorder(0);
                    tabelaCabecalho.addCell(celulaCabecalho);

                    document.add(tabelaCabecalho);

                    Paragraph p2 = null;
                    Paragraph sep = null;
                    PdfPTable tblObservacao = new PdfPTable(new float[]{1.5f, 3f});
                    tblObservacao.setWidthPercentage(100);

                    Orcamento orcamento = OrcamentoDAO.carregaEdicaoOrcamento(codOrc);
                    sep = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------");
                    document.add(sep);
                    p2 = new Paragraph("CÓDIGO EMISSOR: " + TelaAutenticacao.getUsrLogado().getCodigo()
                            + "        DATA EMISSÃO: " + Controle.dataPadrao.format(
                                    orcamento.getDataEmissao()) + "        HORA EMISSÃO: "
                            + Controle.horaPadrao.format(new Date()), FontFactory.getFont("arial.ttf", 9));
                    p2.setAlignment(1);
                    document.add(p2);
                    document.add(sep);

                    //INFORMAÇÕES DO CLIENTE------------------------------------
                    p2 = new Paragraph("INFORMAÇÕES DO CLIENTE", FontFactory.getFont("arial.ttf", 10, Font.BOLD));
                    p2.setAlignment(1);
                    document.add(p2);
                    document.add(new Phrase("\n"));

                    Cliente cliente = ClienteDAO.selecionaInformacoes((byte) orcamento.getTipoPessoa(), orcamento.getCodCliente());
                    p2 = new Paragraph("CLIENTE: " + cliente.getNome() + "        CÓDIGO CLIENTE: " + orcamento.getCodCliente(), FontFactory.getFont("arial.ttf", 9));
                    document.add(p2);

                    //INFORMAÇÕES DOS CONTATOS----------------------------------
                    Contato contato = ClienteDAO.selInfoContato(orcamento.getCodContato());
                    p2 = new Paragraph("CONTATO: " + contato.getNomeContato().toString()
                            + "        TELEFONE PRINCIPAL: " + contato.getTelefone().toString()
                            + "        TELEFONE SECUNDÁRIO: " + contato.getTelefone2().toString(), FontFactory.getFont("arial.ttf", 9));
                    document.add(p2);

                    p2 = new Paragraph("VENDEDOR: " + OrcamentoDAO.carregaNomeVendedor(orcamento.getCodEmissor()), FontFactory.getFont("arial.ttf", 9));
                    document.add(p2);
                    p2 = new Paragraph("OBSERVAÇÕES: " + cliente.getObservacoes(), FontFactory.getFont("arial.ttf", 9));
                    document.add(p2);
                    document.add(sep);
                    //----------------------------------------------------------
                    //----------------------------------------------------------

                    //INFORMAÇÕES DO ORÇAMENTO----------------------------------
                    p2 = new Paragraph("DESCRIÇÃO DO ORÇAMENTO", FontFactory.getFont("arial.ttf", 10, Font.BOLD));
                    p2.setAlignment(1);
                    document.add(p2);
                    //INFORMAÇÕES DO PRODUTO------------------------------------
                    for (ProdOrcamento produto : produtos) {
                        document.add(new Paragraph("\n"));
                        p2 = new Paragraph("CÓDIGO PRODUTO: " + produto.getCodProduto(), FontFactory.getFont("arial.ttf", 9, Font.UNDERLINE));
                        document.add(p2);
                        p2 = new Paragraph(produto.getDescricaoProduto().toUpperCase()
                                + "    QUANTIDADE: " + produto.getQuantidade()
                                + "    TAMANHO: " + ProdutoDAO.carregaAlturaProduto(produto.getCodProduto(),
                                        produto.getTipoProduto())
                                + " X " + ProdutoDAO.carregaLarguraProduto(produto.getCodProduto(),
                                        produto.getTipoProduto())
                                + "    PÁGINAS: " + ProdutoDAO.retornaQuantidadePaginas(produto.getCodProduto(),
                                        produto.getTipoProduto()),
                                FontFactory.getFont("arial.ttf", 9));
                        document.add(p2);

                        if (produtos.get(0).getTipoProduto() != 2) {
                            if (detalhado) {
                                //PAPÉIS DO PRODUTO-----------------------------
                                p2 = new Paragraph("\n");
                                document.add(p2);
                                for (Papel papel : PapelDAO.retornaPapeisOrcamento(Integer.valueOf(produto.getCodProduto()))) {
                                    p2 = new Paragraph("CÓDIGO PAPEL: " + papel.getCod()
                                            + "    TIPO: " + papel.getTipo()
                                            + "    DESCRIÇÃO: " + papel.getDescricao(),
                                            FontFactory.getFont("arial.ttf", 9));
                                    document.add(p2);
                                    p2 = new Paragraph("\n");
                                }
                                //----------------------------------------------
                                document.add(p2);
                                //ACABAMENTOS DO PRODUTO------------------------
                                try {
                                    for (AcabamentoProdBEAN acabamento : AcabamentoDAO.retornaAcabamentosProduto(produto.getCodProduto())) {
                                        p2 = new Paragraph("CÓDIGO ACABAMENTO: " + acabamento.getCodigoAcabamento()
                                                + "    DESCRIÇÃO: " + AcabamentoDAO.retornaDescricaoAcabamentos(acabamento.getCodigoAcabamento()),
                                                FontFactory.getFont("arial.ttf", 9));
                                        document.add(p2);
                                    }
                                } catch (SemAcabamentoException ex) {
                                    p2 = new Paragraph("- NENHUM ACABAMENTO SELECIONADO.",
                                            FontFactory.getFont("arial.ttf", 9));
                                    document.add(p2);
                                }
                                //----------------------------------------------
                            }
                        }

                        p2 = new Paragraph("\n");
                        document.add(p2);
                        PdfPCell cellObservacao = new PdfPCell(new Phrase("OBSERVAÇÕES DO PRODUTO:", FontFactory.getFont("arial.ttf", 9)));
                        cellObservacao.setBorder(0);
                        cellObservacao.setHorizontalAlignment(0);
                        tblObservacao.addCell(cellObservacao);
                        cellObservacao = new PdfPCell(new Phrase(produto.getObservacaoProduto(), FontFactory.getFont("arial.ttf", 9)));
                        cellObservacao.setBorder(0);
                        cellObservacao.setHorizontalAlignment(0);
                        tblObservacao.addCell(cellObservacao);
                        document.add(tblObservacao);
                        tblObservacao.deleteBodyRows();
                    }
                    //----------------------------------------------------------
                    document.add(sep);

                    //SERVIÇOS DO ORÇAMENTO-------------------------------------
                    p2 = new Paragraph("SERVIÇOS DO ORÇAMENTO", FontFactory.getFont("arial.ttf", 10, Font.BOLD));
                    p2.setAlignment(1);
                    document.add(p2);

                    try {
                        for (Servicos servicos : OrcamentoDAO.retornaServicosOrcamento(codOrc)) {
                            p2 = new Paragraph("- CÓDIGO: " + servicos.getCod() + "  |  " + ServicoDAO.carregaDescricaoServicos(servicos.getCod()).toUpperCase(), FontFactory.getFont("arial.ttf", 9));
                            document.add(p2);
                        }
                    } catch (SemServicoException ex) {
                        p2 = new Paragraph("\nNENHUM SERVIÇO SELECIONADO.", FontFactory.getFont("arial.ttf", 9));
                        p2.setAlignment(1);
                        document.add(p2);
                    }
                    //----------------------------------------------------------

                    //VALORES DO ORÇAMENTO--------------------------------------
                    document.add(sep);
                    p2 = new Paragraph("DESCRIÇÃO DE VALORES", FontFactory.getFont("arial.ttf", 10, Font.BOLD));
                    p2.setAlignment(1);
                    document.add(p2);
                    document.add(new Paragraph("\n"));
                    DecimalFormat df = new DecimalFormat("###,##0.00");

                    PdfPTable tblValores = new PdfPTable(new float[]{5f, 5f, 5f, 5f, 5f});
                    PdfPCell celulaCodigo = new PdfPCell(new Phrase("CÓDIGO PRODUTO/SERVIÇO", FontFactory.getFont("arial.ttf", 8)));
                    celulaCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell celulaDescricao = new PdfPCell(new Phrase("DESCRIÇÃO", FontFactory.getFont("arial.ttf", 9)));
                    celulaDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell celulaValorUnitario = new PdfPCell(new Phrase("VALOR UNITÁRIO", FontFactory.getFont("arial.ttf", 9)));
                    celulaValorUnitario.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell celulaQuantidade = new PdfPCell(new Phrase("QUANTIDADE", FontFactory.getFont("arial.ttf", 9)));
                    celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
                    PdfPCell celulaValorTotal = new PdfPCell(new Phrase("VALOR TOTAL", FontFactory.getFont("arial.ttf", 9)));
                    celulaValorTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tblValores.addCell(celulaCodigo);
                    tblValores.addCell(celulaDescricao);
                    tblValores.addCell(celulaValorUnitario);
                    tblValores.addCell(celulaQuantidade);
                    tblValores.addCell(celulaValorTotal);

                    //VALOR PRODUTOS--------------------------------------------
                    double valorUnitario = 0d;
                    for (ProdOrcamento produto : OrcamentoDAO.carregaProdOrc(codOrc)) {
                        if (produtos.get(0).getTipoProduto() == 2) {
                            valorUnitario = ProdutoDAO.retornaVlrPe(produto.getCodProduto());
                        } else {
                            valorUnitario = OrcamentoDAO.retornaValorUnitario(codOrc, produto.getCodProduto());
                        }

                        celulaCodigo = new PdfPCell(new Phrase(String.valueOf(produto.getCodProduto()), FontFactory.getFont("arial.ttf", 9)));
                        celulaQuantidade = new PdfPCell(new Phrase(String.valueOf(produto.getQuantidade()), FontFactory.getFont("arial.ttf", 9)));
                        celulaDescricao = new PdfPCell(new Phrase(String.valueOf(produto.getDescricaoProduto()), FontFactory.getFont("arial.ttf", 9)));
                        celulaValorUnitario = new PdfPCell(new Phrase(df.format(valorUnitario), FontFactory.getFont("arial.ttf", 9)));
                        celulaValorTotal = new PdfPCell(new Phrase(df.format(valorUnitario * (double) produto.getQuantidade()), FontFactory.getFont("arial.ttf", 9)));
                        celulaValorTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaValorTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celulaValorUnitario.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaValorUnitario.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celulaCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaCodigo.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celulaDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);

                        celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaQuantidade.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        tblValores.addCell(celulaCodigo);
                        tblValores.addCell(celulaDescricao);
                        tblValores.addCell(celulaValorUnitario);
                        tblValores.addCell(celulaQuantidade);
                        tblValores.addCell(celulaValorTotal);
                    }
                    //----------------------------------------------------------

                    //VALOR SERVIÇOS--------------------------------------------
                    try {
                        for (Servicos servico : OrcamentoDAO.retornaServicosOrcamento(codOrc)) {
                            celulaCodigo = new PdfPCell(new Phrase(String.valueOf(servico.getCod()) + " - SV", FontFactory.getFont("arial.ttf", 9)));
                            celulaDescricao = new PdfPCell(new Phrase(String.valueOf(ServicoDAO.carregaDescricaoServicos(servico.getCod())), FontFactory.getFont("arial.ttf", 9)));
                            celulaValorUnitario = new PdfPCell(new Phrase(df.format(ServicoDAO.retornaVlrSrvOrcNExistente(servico.getCod())), FontFactory.getFont("arial.ttf", 9)));
                            celulaValorTotal = new PdfPCell(new Phrase(String.valueOf(ServicoDAO.retornaVlrSrvOrcNExistente(servico.getCod())), FontFactory.getFont("arial.ttf", 9)));
                            celulaQuantidade = new PdfPCell(new Phrase("1", FontFactory.getFont("arial.ttf", 9)));
                            celulaValorTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                            celulaValorTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            celulaValorUnitario.setHorizontalAlignment(Element.ALIGN_CENTER);
                            celulaValorUnitario.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            celulaCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
                            celulaCodigo.setVerticalAlignment(Element.ALIGN_MIDDLE);

                            celulaDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);

                            celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
                            celulaQuantidade.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            tblValores.addCell(celulaCodigo);
                            tblValores.addCell(celulaDescricao);
                            tblValores.addCell(celulaValorUnitario);
                            tblValores.addCell(celulaQuantidade);
                            tblValores.addCell(celulaValorTotal);
                        }
                    } catch (SemServicoException ex) {
                        //
                    }
                    //----------------------------------------------------------

                    //VALOR FRETE-----------------------------------------------
                    Double valorFrete = OrcamentoDAO.retornaValorFrete(codOrc);
                    if (valorFrete != 0d) {
                        celulaCodigo = new PdfPCell(new Phrase("FRETE", FontFactory.getFont("arial.ttf", 9)));
                        celulaDescricao = new PdfPCell(new Phrase("-", FontFactory.getFont("arial.ttf", 9)));
                        celulaValorUnitario = new PdfPCell(new Phrase(df.format(valorFrete), FontFactory.getFont("arial.ttf", 9)));
                        celulaValorTotal = new PdfPCell(new Phrase(df.format(valorFrete), FontFactory.getFont("arial.ttf", 9)));
                        celulaQuantidade = new PdfPCell(new Phrase("1", FontFactory.getFont("arial.ttf", 9)));
                        celulaValorTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaValorTotal.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celulaValorUnitario.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaValorUnitario.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celulaCodigo.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaCodigo.setVerticalAlignment(Element.ALIGN_MIDDLE);

                        celulaDescricao.setHorizontalAlignment(Element.ALIGN_CENTER);

                        celulaQuantidade.setHorizontalAlignment(Element.ALIGN_CENTER);
                        celulaQuantidade.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        tblValores.addCell(celulaCodigo);
                        tblValores.addCell(celulaDescricao);
                        tblValores.addCell(celulaValorUnitario);
                        tblValores.addCell(celulaQuantidade);
                        tblValores.addCell(celulaValorTotal);
                    }
                    //----------------------------------------------------------

                    celulaCodigo = new PdfPCell(new Phrase("TOTAL (R$)", FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                    celulaCodigo.setColspan(4);
                    celulaCodigo.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    celulaValorTotal = new PdfPCell(new Phrase(df.format((double) orcamento.getValorTotal()), FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                    celulaValorTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tblValores.addCell(celulaCodigo);
                    tblValores.addCell(celulaValorTotal);
                    tblValores.keepRowsTogether(0);
                    document.add(tblValores);
                    document.add(sep);
                    //----------------------------------------------------------

                    //OBSERVAÇÕES E MÉTODOS DE PGTO-----------------------------
                    p2 = new Paragraph("OBSERVAÇÕES DO ORÇAMENTO: " + orcamento.getDescricao(), FontFactory.getFont("arial.ttf", 8));
                    document.add(p2);
                    document.add(new Paragraph("\n"));
                    p2 = new Paragraph("VALIDADE DA PROPOSTA: " + Controle.dataPadrao.format(orcamento.getDataValidade()), FontFactory.getFont("arial.ttf", 10, Font.BOLD));
                    document.add(p2);
                    if (orcamento.tipoPessoa == 1) {
                        p2 = new Paragraph("MÉTODO DE PAGAMENTO: GRU Simples, pagamento exclusivo no Banco do Brasil".toUpperCase(), FontFactory.getFont("arial.ttf", 10, Font.BOLD));
                        document.add(p2);
                    } else {
                        p2 = new Paragraph("Método de pagamento: \n  "
                                + "Transferência entre contas no SIGA, opção pagamento de serviço (Cod GRU 226963 e favorecido – UG 167083 para ND 33 90 00 ou ND 33 90 30). ".toUpperCase(), FontFactory.getFont("arial.ttf", 10, Font.BOLD));
                        document.add(p2);
                    }

                    document.add(new Paragraph("\n"));

                    PdfPTable tabelaRodape = new PdfPTable(new float[]{1f});
                    tabelaRodape.setWidthPercentage(100);
                    PdfPCell celulaRodape = null;
                    if (produtos.get(0).getTipoProduto() == 2) {
                        if (orcamento.getTipoPessoa() == 1) {
                            celulaRodape = new PdfPCell(new Phrase("ENTREGA: 5 A 10 DIAS ÚTEIS APÓS A CONFIRMAÇÃO DO PAGAMENTO DA GRU.", FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                        } else {
                            celulaRodape = new PdfPCell(new Phrase("ENTREGA: 5 A 10 DIAS ÚTEIS APÓS O RECEBIMENTO DA PROPOSTA ASSINADA.", FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                        }
                    } else {
                        celulaRodape = new PdfPCell(new Phrase("ENTREGA: ______ DIAS ÚTEIS APÓS A APROVAÇÃO DO 'MODELO DE PROVA'.", FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                    }
                    celulaRodape.setBackgroundColor(Controle.fundoDestaque);
                    celulaRodape.setBorder(0);
                    celulaRodape.setHorizontalAlignment(1);
                    tabelaRodape.addCell(celulaRodape);
                    document.add(tabelaRodape);

                    document.add(new Paragraph("\n"));

                    tabelaRodape.deleteBodyRows();
                    celulaRodape = new PdfPCell(new Phrase("AUTORIZO A INSERÇÃO DO QR CODE DA GRÁFICA DO EXÉRCITO NA 4ª CAPA (  ) SIM (  ) NÃO", FontFactory.getFont("arial.ttf", 10, Font.BOLD)));
                    celulaRodape.setBackgroundColor(Controle.fundoDestaque);
                    celulaRodape.setBorder(0);
                    celulaRodape.setHorizontalAlignment(1);
                    tabelaRodape.addCell(celulaRodape);
                    document.add(tabelaRodape);

                    document.add(new Paragraph("\n"));

                    p2 = new Paragraph("BRASÍLIA-DF, "
                            + Controle.dataPadrao.format(new Date())
                            + ".\n\n"
                            + "________________________________________________\n"
                            + OrcamentoDAO.carregaNomeVendedor(orcamento.getCodEmissor())
                            + "\nGRÁFICA DO EXÉRCITO - DIVISÃO COMERCIAL\n\n"
                            + "________________________________________________\n"
                            + cliente.getNome().toUpperCase()
                            + "\n"
                            + cliente.getNomeFantasia()
                            + "\n\nDATA:          de                      de 20  ",
                            FontFactory.getFont("arial.ttf", 8));
                    p2.setAlignment(1);
                    p2.setKeepTogether(true);
                    document.add(p2);
                    //----------------------------------------------------------

                    document.setPageCount(document.getPageNumber());
                    document.close();

                    if (!email) {
                        java.awt.Desktop.getDesktop()
                                .open(new File(System.getProperty("java.io.tmpdir") + "/orcamento" + codOrc + ".pdf"));
                    }

                } catch (DocumentException | SQLException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null,
                            "O ARQUIVO DEVE ESTAR SENDO UTILIZADO POR OUTRO PROCESSO OU\n"
                            + "JÁ ESTÁ ABERTO.",
                            "ERRO AO ABRIR O ARQUIVO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(loading);
                }
                loading.setVisible(false);
            }

        }.start();

        if (email) {
            return caminho;
        } else {
            return null;
        }
    }

    public static Double arredonda(double valor, int casas) {
        Double arredondado = valor;
        arredondado *= (Math.pow(10, casas));
        arredondado = Math.ceil(arredondado);
        arredondado /= (Math.pow(10, casas));
        return arredondado;
    }

    public static Double calculaValorTotal(List<Double> valoresUnitarios, List<Integer> quantidades, List<Double> valoresServicos, double frete, boolean haFrete) {
        double retorno = 0.0;
        for (int i = 0; i < valoresUnitarios.size(); i++) {
            retorno += valoresUnitarios.get(i) * quantidades.get(i);
        }
        for (int i = 0; i < valoresServicos.size(); i++) {
            retorno += valoresServicos.get(i);
        }
        if (haFrete) {
            retorno = retorno + frete;
        }
        retorno = arredonda(retorno, 2);
        return retorno;
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
    public static int retornaQuantidadeFolhas(byte tipoProduto,
            byte tipoPapel,
            int quantidadeFolhas,
            int formatoImpressao,
            int tiragem,
            int numeroVias,
            double perca) {
        DecimalFormat df = new DecimalFormat("0");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        int quantidadeFolhasF1 = 0;

        switch (tipoProduto) {
            case 1:
                quantidadeFolhasF1 = retornaMaior((double) tiragem / (double) formatoImpressao);
                quantidadeFolhasF1 += Integer.valueOf(df.format((quantidadeFolhasF1 * perca) / 100));
                break;
            case 2:
                switch (tipoPapel) {
                    case 1:
                    case 2:
                        quantidadeFolhasF1 = retornaMaior((double) tiragem / (double) formatoImpressao);
                        quantidadeFolhasF1 += Integer.valueOf(df.format((quantidadeFolhasF1 * perca) / 100));
                        break;
                    case 3:
                        quantidadeFolhasF1 = retornaMaior(((double) quantidadeFolhas / (double) formatoImpressao) * (double) tiragem);
                        quantidadeFolhasF1 /= 2d;
                        quantidadeFolhasF1 += Integer.valueOf(df.format((quantidadeFolhasF1 * perca) / 100));
                        break;
                }
                break;
            case 3:
                switch (tipoPapel) {
                    case 2:
                        quantidadeFolhasF1 = retornaMaior((double) tiragem / (double) formatoImpressao);
                        quantidadeFolhasF1 += Integer.valueOf(df.format((quantidadeFolhasF1 * perca) / 100));
                        break;
                    case 4:
                    case 5:
                    case 6:
                        quantidadeFolhas /= (double) numeroVias;
                        quantidadeFolhasF1 = retornaMaior((tiragem * quantidadeFolhas) / formatoImpressao);
                        quantidadeFolhasF1 += Integer.valueOf(df.format((quantidadeFolhas * perca) / 100));
                        break;
                }

                break;
            case 4:
                break;
            default:
                break;
        }
        return quantidadeFolhasF1;
    }

    public static int retornaQuantidadeChapas(byte tipoProduto,
            byte tipoPapel,
            int codigoProduto,
            int tiragem,
            int numeroCoresFrente,
            int numeroCoresVerso,
            int formatoImpressao,
            int quantidadePaginas) {

        int quantidadeChapas = 0;
        int coresTotal = numeroCoresFrente + numeroCoresVerso;

        switch (tipoProduto) {
            case 1:
                quantidadeChapas = coresTotal;
                break;
            case 2:
                switch (tipoPapel) {
                    case 2:
                        quantidadeChapas = coresTotal;
                        break;
                    case 3:
                        quantidadeChapas = retornaMaior((double) quantidadePaginas / (double) formatoImpressao) * coresTotal;
                        break;
                }
                break;
            case 3:
                switch (tipoPapel) {
                    case 2:
                        quantidadeChapas = coresTotal;
                        break;
                    case 4:
                    case 5:
                    case 6:
                        quantidadeChapas = coresTotal;
                        break;
                }
                break;
            case 4 | 5:
                quantidadeChapas = 0;
                break;
        }

        return quantidadeChapas;

    }

    private static int retornaMaior(double valor) {
        int valorAux;
        valorAux = (int) Math.round(valor);
        if (valorAux < valor) {
            return valorAux + 1;
        } else {
            return valorAux;
        }
    }

}

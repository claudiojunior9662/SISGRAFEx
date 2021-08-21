/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.lojaIntegrada.Category;
import entities.lojaIntegrada.Order;
import entities.lojaIntegrada.Product;
import entities.sisgrafex.Cliente;
import entities.sisgrafex.Contato;
import entities.sisgrafex.Endereco;
import entities.sisgrafex.Orcamento;
import entities.sisgrafex.ProdOrcamento;
import exception.EnvioExcecao;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.http.HttpHeaders;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import org.json.JSONArray;
import org.json.JSONObject;
import model.dao.ClienteDAO;
import model.dao.ContatoDAO;
import model.dao.EnderecoDAO;
import model.dao.OrcamentoDAO;
import model.dao.ProdutoDAO;
import ui.controle.Controle;

/**
 *
 * @author claud
 */
public class IntegracaoLojaIntegrada {

    public static void main(String[] args) {

    }

    /**
     * Realiza requisição GET para o e-commerce
     *
     * @param tipo 1 - Categorias, 2 - Pedidos Gerais, 3 - Pedidos Específicos
     * @param requisicao
     * @return
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static Object realizaRequisicaoGET(byte tipo, Object requisicao) throws IOException, InterruptedException {
        try {
            HashMap values = null;
            ObjectMapper objectMapper = null;
            String requestBody = null;
            HttpClient client = null;
            HttpRequest request = null;
            HttpResponse<String> response = null;
            HttpHeaders headers = null;

            switch (tipo) {
                case 1:
                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + "/"
                                    + Controle.VERSAO_API
                                    + "/"
                                    + "categoria/?format="
                                    + Controle.FORMATO_SAIDA
                                    + "&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .GET()
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    headers = response.headers();
                    headers.map().forEach((k, v) -> System.out.println(k + ":" + v));
                    break;
                case 2:
                    List<Order> ordersGeral = new ArrayList();
                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + "/"
                                    + Controle.VERSAO_API
                                    + "/"
                                    + "pedido/search/"
                                    + "?since_criado="
                                    + Controle.dataPadraoLojaIntegrada.format(Controle.SYNC_PEDIDOS)
                                    + "T"
                                    + Controle.horaPadraoLojaIntegrada.format(Controle.SYNC_PEDIDOS)
                                    + "&format="
                                    + Controle.FORMATO_SAIDA
                                    + "&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .GET()
                            .build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    JSONObject json = new JSONObject(response.body());
                    JSONArray jsonArray = json.getJSONArray("objects");
                    for (int i = 0; i < (int) json.getJSONObject("meta").get("total_count"); i++) {
                        ordersGeral.add(new Order(
                                jsonArray.getJSONObject(i).getString("cliente"),
                                Timestamp.valueOf(jsonArray.getJSONObject(i).getString("data_criacao").replace("T", " ")),
                                Timestamp.valueOf(jsonArray.getJSONObject(i).getString("data_expiracao").replace("T", " ")),
                                Timestamp.valueOf(jsonArray.getJSONObject(i).getString("data_modificacao").replace("T", " ")),
                                jsonArray.getJSONObject(i).getInt("numero"),
                                jsonArray.getJSONObject(i).isNull("id_externo") ? 0 : (int) jsonArray.getJSONObject(i).get("id_externo"),
                                jsonArray.getJSONObject(i).getJSONObject("situacao").getBoolean("aprovado"),
                                jsonArray.getJSONObject(i).getJSONObject("situacao").getBoolean("cancelado"),
                                jsonArray.getJSONObject(i).getJSONObject("situacao").getInt("id"),
                                jsonArray.getJSONObject(i).getJSONObject("situacao").getString("codigo"),
                                jsonArray.getJSONObject(i).getDouble("valor_subtotal"),
                                jsonArray.getJSONObject(i).getDouble("valor_total")
                        ));
                    }
                    return ordersGeral;
                case 3:
                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + "/"
                                    + Controle.VERSAO_API
                                    + "/"
                                    + "pedido/"
                                    + (int) requisicao
                                    + Controle.SEPARADOR
                                    + "?format="
                                    + Controle.FORMATO_SAIDA
                                    + "&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .GET()
                            .build();
                    System.out.println(request);
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    JSONObject orderEsp = new JSONObject(response.body());
                    List<ProdOrcamento> orderProductsList = new ArrayList();
                    JSONArray orderProducts = orderEsp.getJSONArray("itens");

                    for (int i = 0; i < orderProducts.length(); i++) {
                        orderProductsList.add(new ProdOrcamento(
                                orderProducts.getJSONObject(i).getJSONObject("produto").getInt("id_externo"),
                                orderProducts.getJSONObject(i).getString("sku").substring(0, 2).equals("PP")
                                ? (byte) 1
                                : (byte) 2,
                                orderProducts.getJSONObject(i).getString("nome"),
                                (int) orderProducts.getJSONObject(i).getDouble("quantidade"),
                                (float) orderProducts.getJSONObject(i).getDouble("preco_venda")
                        ));
                    }

                    return new Order(
                            new Cliente(
                                    0,
                                    orderEsp.getJSONObject("cliente").getInt("id"),
                                    orderEsp.getJSONObject("cliente").getString("nome").toUpperCase(),
                                    orderEsp.getJSONObject("endereco_entrega").getString("tipo").equals("PJ")
                                    ? orderEsp.getJSONObject("cliente").getString("razao_social").toUpperCase()
                                    : null,
                                    orderEsp.getJSONObject("endereco_entrega").getString("tipo").equals("PF")
                                    ? Controle.retornaDocumentoFormatado(orderEsp.getJSONObject("cliente").getString("cpf"), (byte) 1)
                                    : null,
                                    orderEsp.getJSONObject("endereco_entrega").getString("tipo").equals("PJ")
                                    ? Controle.retornaDocumentoFormatado(orderEsp.getJSONObject("cliente").getString("cnpj"), (byte) 2)
                                    : null,
                                    orderEsp.getJSONObject("endereco_entrega").getString("tipo").equals("PF")
                                    ? (byte) 1
                                    : (byte) 2
                            ),
                            new Contato(
                                    orderEsp.getJSONObject("cliente").getString("nome").toUpperCase().split(" ")[0],
                                    orderEsp.getJSONObject("cliente").getString("email"),
                                    orderEsp.getJSONObject("cliente").get("telefone_principal") == JSONObject.NULL ? 
                                            Controle.retornaTelefoneFormatado(orderEsp.getJSONObject("cliente").getString("telefone_celular")) :
                                            Controle.retornaTelefoneFormatado(orderEsp.getJSONObject("cliente").getString("telefone_principal")),
                                    orderEsp.getJSONObject("cliente").get("telefone_celular") == JSONObject.NULL ? null : 
                                            Controle.retornaTelefoneFormatado(orderEsp.getJSONObject("cliente").getString("telefone_celular"))
                            ),
                            new Endereco(
                                    Controle.retornaCepFormatado(orderEsp.getJSONObject("endereco_entrega").getString("cep")),
                                    orderEsp.getJSONObject("endereco_entrega").getString("tipo").equals("PF")
                                    ? "RESIDENCIAL"
                                    : "COMERCIAL",
                                    orderEsp.getJSONObject("endereco_entrega").getString("endereco"),
                                    orderEsp.getJSONObject("endereco_entrega").getString("bairro"),
                                    orderEsp.getJSONObject("endereco_entrega").getString("estado"),
                                    orderEsp.getJSONObject("endereco_entrega").getString("complemento"),
                                    orderEsp.getJSONObject("endereco_entrega").getString("cidade")
                            ),
                            new Orcamento(
                                    0,
                                    0,
                                    "SIS",
                                    Timestamp.valueOf(orderEsp.getString("data_expiracao").replace("T", " ")),
                                    Timestamp.valueOf(orderEsp.getString("data_criacao").replace("T", " ")),
                                    0f,
                                    (byte) 1,
                                    "PEDIDO DE ORÇAMENTO RECEBIDO PELO MÓDULO INTEGRADOR",
                                    orderEsp.getJSONObject("endereco_entrega").getString("tipo").equals("PF")
                                    ? (byte) 1
                                    : (byte) 2,
                                    0f,
                                    (float) orderEsp.getDouble("valor_total"),
                                    0,
                                    0,
                                    1,
                                    orderEsp.getJSONArray("envios").getJSONObject(0).getDouble("valor"),
                                    0d
                            ),
                            orderProductsList,
                            orderEsp.isNull("id_externo") ? 0 : (int) orderEsp.get("id_externo")
                    );
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        } catch (InterruptedException ex) {
            throw new InterruptedException(ex.toString());
        }
        return null;
    }

    /**
     * Realiza requisição POST para o e-commerce
     *
     * @param tipo 1 - Cadastro categoria, 2 - Cadastro marca, 3 - Cadastro
     * grade, 4 - Cadastro variação, 5 - Cadastro produto pai, 6 - Cadastro
     * produto filho, 7 - Cadastro imagem produto, 8 - Cadastro cliente, 9 -
     * Alterar Preço do produto, 10 - Alterar produto
     * @param requisicao
     * @throws IOException
     * @throws InterruptedException
     * @throws java.sql.SQLException
     */
    public static void realizaRequisicaoPOST(byte tipo, Object requisicao) throws IOException, InterruptedException, SQLException, Exception {
        try {
            HashMap values = null;
            ObjectMapper objectMapper = null;
            String requestBody = null;
            HttpClient client = null;
            HttpRequest request = null;
            HttpResponse<String> response = null;
            Product product;

            switch (tipo) {
                case 1:
                    Category category = (Category) requisicao;
                    values = new HashMap<String, Object>() {
                        {
                            put("id_externo", String.valueOf(category.getIdExterno()));
                            put("nome", String.valueOf(category.getNome()));
                            put("descricao", String.valueOf(category.getDescricao()));
                            put("categoria_pai", String.valueOf(category.getPai()));
                        }
                    };

                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + Controle.SEPARADOR
                                    + Controle.VERSAO_API
                                    + Controle.SEPARADOR
                                    + "categoria/?format="
                                    + Controle.FORMATO_SAIDA
                                    + "&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    product = (Product) requisicao;
                    values = new HashMap<String, Object>() {
                        {
                            put("id_externo", product.getId());
                            put("sku", product.getSku());
                            put("mpn", String.valueOf(product.getMpn()));
                            put("ncm", String.valueOf(product.getNcm()));
                            put("nome", String.valueOf(product.getNome()));
                            put("descricao_completa", String.valueOf(product.getDescricaoCompleta()));
                            put("ativo", product.isAtivo());
                            put("destaque", product.isDestaque());
                            put("peso", product.getPeso());
                            put("altura", (int) product.getAltura());
                            put("largura", (int) product.getLargura());
                            put("profundidade", (int) product.getProfundidade());
                            put("tipo", product.getTipo());
                            put("usado", product.isUsado());
                        }
                    };

                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + Controle.SEPARADOR
                                    + Controle.VERSAO_API
                                    + Controle.SEPARADOR
                                    + "produto/?format="
                                    + Controle.FORMATO_SAIDA
                                    + "&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());

                    JSONObject json = new JSONObject(response.body());
                    ProdutoDAO.atualizaCodigoLI(Integer.valueOf(product.getId()), (int) json.get("id"), product.getSku().contains("PP") ? (byte) 1 : (byte) 2);

                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                default:
                    break;
            }
        } catch (JsonProcessingException ex) {
            throw new Exception(ex);
        } catch (IOException | InterruptedException ex) {
            throw new Exception(ex);
        }
    }

    /**
     * Realiza requisições PUT para o e-commerce
     *
     * @param tipo 1 - Alterar produto, 2 - Alterar/Adicionar preço produto, 3 -
     * Alterar/Adicionar estoque produto, 4 - Atualiza ID Externo pedido, 5 -
     * Atualiza status do pedido
     * @param requisicao
     * @throws IOException
     * @throws InterruptedException
     * @throws java.sql.SQLException
     */
    public static void realizaRequisicaoPUT(byte tipo, Object requisicao) throws Exception {
        try {
            HashMap values = null;
            ObjectMapper objectMapper = null;
            String requestBody = null;
            HttpClient client = null;
            HttpRequest request = null;
            HttpResponse<String> response = null;
            Product product;

            switch (tipo) {
                case 1:
                    product = (Product) requisicao;
                    values = new HashMap<String, Object>() {
                        {
                            put("id_externo", String.valueOf(product.getId()));
                            put("sku", String.valueOf(product.getSku()));
                            put("nome", String.valueOf(product.getNome()));
                            put("descricao_completa", String.valueOf(product.getDescricaoCompleta()));
                            put("ativo", product.isAtivo());
                            put("destaque", product.isDestaque());
                            put("peso", product.getPeso());
                            put("altura", (int) product.getAltura());
                            put("largura", (int) product.getLargura());
                            put("profundidade", (int) product.getProfundidade());
                            put("tipo", product.getTipo());
                            put("data_criacao", "2014-01-06T23:13:59");
                            put("data_modificacao", "2014-01-06T23:13:59");
                        }
                    };

                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + Controle.SEPARADOR
                                    + Controle.VERSAO_API
                                    + Controle.SEPARADOR
                                    + "produto"
                                    + Controle.SEPARADOR
                                    + product.getId()
                                    + Controle.SEPARADOR
                                    + "?format="
                                    + Controle.FORMATO_SAIDA
                                    + "&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                    break;
                case 2:
                    product = (Product) requisicao;
                    values = new HashMap<String, Object>() {
                        {
                            put("cheio", product.getValorCusto());
                            put("custo", product.getValorCusto());
                            put("promocional", product.getValorPromocional());
                        }
                    };

                    product.setId(String.valueOf(ProdutoDAO.retornaCodigoLI(Integer.valueOf(product.getId()), product.getSku().contains("PP") ? (byte) 1 : (byte) 2)));

                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + Controle.SEPARADOR
                                    + Controle.VERSAO_API
                                    + Controle.SEPARADOR
                                    + "produto_preco"
                                    + Controle.SEPARADOR
                                    + product.getId()
                                    + Controle.SEPARADOR
                                    + "?format=json&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                    break;
                case 3:
                    product = (Product) requisicao;
                    values = new HashMap<String, Object>() {
                        {
                            put("gerenciado", true);
                            put("quantidade", product.getEstoque());
                        }
                    };

                    try {
                        product.setId(String.valueOf(ProdutoDAO.retornaCodigoLI(Integer.valueOf(product.getId()), product.getSku().contains("PP") ? (byte) 1 : (byte) 2)));
                    } catch (SQLException ex) {
                        Logger.getLogger(IntegracaoLojaIntegrada.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + Controle.SEPARADOR
                                    + Controle.VERSAO_API
                                    + Controle.SEPARADOR
                                    + "produto_estoque"
                                    + Controle.SEPARADOR
                                    + product.getId()
                                    + Controle.SEPARADOR
                                    + "?format=json&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                    break;
                case 4:
                    int idExterno = (int) requisicao;

                    values = new HashMap<String, Object>() {
                        {
                            put("pedido_id", requisicao);
                        }
                    };

                    objectMapper = new ObjectMapper();
                    requestBody = objectMapper.writeValueAsString(values);

                    if (Controle.USO_PROXY) {
                        client = HttpClient.newBuilder()
                                .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                                .build();
                    } else {
                        client = HttpClient.newBuilder()
                                .build();
                    }

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + Controle.SEPARADOR
                                    + Controle.VERSAO_API
                                    + Controle.SEPARADOR
                                    + "pedido"
                                    + Controle.SEPARADOR
                                    + "pedido_id"
                                    + Controle.SEPARADOR
                                    + "?format=json&"
                                    + "chave_api="
                                    + Controle.CHAVE_API
                                    + "&"
                                    + "chave_aplicacao="
                                    + Controle.CHAVE_APLICACAO))
                            .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();
                    response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                    break;
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        } catch (InterruptedException ex) {
            throw new InterruptedException();
        }
    }

    /**
     * Atualiza os pedidos dinamicamente
     *
     * Situações: 2 - aguardando_pagamento, 17 - em_producao, 7 -
     * pagamento_devolvido, 3 - pagamento_em_analise, 16 - pedido_chargeback, 6
     * - pagamento_em_disputa, 8 - pedido_cancelado, 9 - pedido_efetuado, 15 -
     * pedido_em_separação, 14 - pedido_entregue, 11 - pedido_enviado, 4 -
     * pedido_pago, 13 - pronto_para_retirada
     */
    public static void atualizaPedidosEcommerce(JLabel labelSincronizacao) {
        new Thread("Atualiza pedidos e-commerce") {
            @Override
            public void run() {
                try {
                    while (true) {
                        for (Order order : (List<Order>) realizaRequisicaoGET((byte) 2, null)) {
                            switch (order.getSituacao()) {
                                case 7:
                                case 16:
                                case 8:
                                    if (order.getIdExterno() != 0) {
                                        if (OrcamentoDAO.verificaOrcamentoExistente(Integer.valueOf((int) order.getIdExterno()))) {
                                            OrcamentoDAO.alteraStatus(Integer.valueOf((int) order.getIdExterno()), (byte) 13);
                                        }
                                    } else {
                                        Order orderDet = (Order) realizaRequisicaoGET((byte) 3, order.getNumero());
                                        List codigoContato = new ArrayList();
                                        List codigoEndereco = new ArrayList();
                                        boolean enderecoNovo = false;
                                        boolean contatoNovo = false;
                                        boolean clienteNovo = false;

                                        /**
                                         * Verifica se o endereço está
                                         * cadastrado no sistema
                                         */
                                        if (!EnderecoDAO.verificaEndereco(orderDet.getEndereco().getCep())) {
                                            /**
                                             * Se o endereço não está
                                             * cadastrado, realiza o cadastro
                                             */
                                            orderDet.getEndereco().setCodigo(EnderecoDAO.retornaUltimoRegistroEnderecos() + 1);
                                            EnderecoDAO.gravarEnderecos(orderDet.getEndereco());
                                            codigoEndereco.add(orderDet.getEndereco().getCodigo());
                                            enderecoNovo = true;
                                        } /**
                                         * Se o endereço existir, atribui o
                                         * código ao cliente
                                         */
                                        else {
                                            orderDet.getEndereco().setCodigo(EnderecoDAO.retornaCodPorCep(orderDet.getEndereco().getCep()));
                                            codigoEndereco.add(orderDet.getEndereco().getCodigo());
                                        }
                                        /**
                                         * Verifica se o contato está cadastrado
                                         * no sistema
                                         */
                                        if (!ContatoDAO.verificaContato(orderDet.getContato())) {
                                            /**
                                             * Se o contato não está cadastrado,
                                             * realiza o cadastro
                                             */
                                            orderDet.getContato().setCod(ContatoDAO.retornaUltimoRegistroContatos() + 1);
                                            ContatoDAO.gravaContatos(orderDet.getContato());
                                            codigoContato.add(orderDet.getContato().getCod());
                                            contatoNovo = true;
                                        }/**
                                         * Se o contato existir, atribui o
                                         * código ao cliente
                                         */
                                        else {
                                            orderDet.getContato().setCod(ContatoDAO.retornaCodPorTelefone(orderDet.getContato()));
                                            codigoContato.add(orderDet.getContato().getCod());
                                        }
                                        /**
                                         * Verifica se o cliente está cadastrado
                                         * no sistema
                                         */
                                        if (!ClienteDAO.verificaCadastroCliente(orderDet.getCliente())) {
                                            /**
                                             * Se o cliente não está cadastrado,
                                             * realiza o cadastro
                                             */
                                            orderDet.getCliente().setCodigo(ClienteDAO.retornaUltimoRegistroClientes(orderDet.getCliente().getTipoPessoa()) + 1);
                                            orderDet.getCliente().setCodigoAtendente("SIS");
                                            orderDet.getCliente().setNomeAtendente("SISTEMA DE VENDAS ONLINE");
                                            orderDet.getCliente().setAtividade("MILITAR");
                                            ClienteDAO.gravarClientes(orderDet.getCliente(), orderDet.getCliente().getTipoPessoa());
                                            clienteNovo = true;
                                            /**
                                             * Faz a associação do endereço e
                                             * contato cadastrados ao cliente
                                             */
                                            if (contatoNovo) {
                                                ClienteDAO.associacaoClientesContatos(codigoContato, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                            } else if (enderecoNovo) {
                                                ClienteDAO.associacaoClientesEnderecos(codigoEndereco, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                            } else if (clienteNovo) {
                                                ClienteDAO.associacaoClientes(codigoEndereco, codigoContato, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                            }
                                        } /**
                                         * Caso o cliente já esteja cadastrado
                                         * no sistema, atribui o código ao
                                         * cliente
                                         */
                                        else {
                                            switch (orderDet.getCliente().getTipoPessoa()) {
                                                case 1:
                                                    orderDet.getCliente().setCodigo(ClienteDAO.retornaCodPorDoc(
                                                            Controle.retornaDocumentoFormatado(orderDet.getCliente().getCpf(), orderDet.getCliente().getTipoPessoa()),
                                                            orderDet.getCliente().getTipoPessoa()));
                                                    break;
                                                case 2:
                                                    orderDet.getCliente().setCodigo(ClienteDAO.retornaCodPorDoc(
                                                            Controle.retornaDocumentoFormatado(orderDet.getCliente().getCnpj(), orderDet.getCliente().getTipoPessoa()),
                                                            orderDet.getCliente().getTipoPessoa()));
                                                    break;
                                            }

                                            if (contatoNovo) {
                                                ClienteDAO.associacaoClientesContatos(codigoContato, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                            } else if (enderecoNovo) {
                                                ClienteDAO.associacaoClientesEnderecos(codigoEndereco, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                            }
                                        }

                                        /**
                                         * Realiza o cadastro do orçamento
                                         */
                                        orderDet.getOrcamento().setCod(OrcamentoDAO.retornaUltimoRegistro() + 1);
                                        orderDet.getOrcamento().setCodCliente(orderDet.getCliente().getCodigo());
                                        orderDet.getOrcamento().setTipoPessoa(orderDet.getCliente().getTipoPessoa());
                                        orderDet.getOrcamento().setCodContato(orderDet.getContato().getCod());
                                        orderDet.getOrcamento().setCodEndereco(orderDet.getEndereco().getCodigo());
                                        OrcamentoDAO.createOrcamentos(orderDet.getOrcamento());

                                        /**
                                         * Realiza o cadastro dos produtos
                                         * associados ao orçamento
                                         */
                                        for (ProdOrcamento produto : orderDet.getProdutos()) {
                                            produto.setCodOrcamento(orderDet.getOrcamento().getCod());
                                            produto.setValorDigital(0.01d);
                                            produto.setMaquina(1);
                                            OrcamentoDAO.criaProdOrc(produto);
                                        }

                                        OrcamentoDAO.alteraStatus(orderDet.getOrcamento().getCod(), (byte) 13);
                                        realizaRequisicaoPUT((byte) 4, orderDet.getOrcamento().getCod());
                                        Controle.atualizaSyncPedidos(new java.sql.Timestamp(new Date().getTime()));
                                    }
                                    break;
                                case 9:
                                    Order orderDet = (Order) realizaRequisicaoGET((byte) 3, order.getNumero());
                                    List codigoContato = new ArrayList();
                                    List codigoEndereco = new ArrayList();
                                    boolean enderecoNovo = false;
                                    boolean contatoNovo = false;
                                    boolean clienteNovo = false;

                                    /**
                                     * Verifica se o endereço está cadastrado no
                                     * sistema
                                     */
                                    if (!EnderecoDAO.verificaEndereco(orderDet.getEndereco().getCep())) {
                                        /**
                                         * Se o endereço não está cadastrado,
                                         * realiza o cadastro
                                         */
                                        orderDet.getEndereco().setCodigo(EnderecoDAO.retornaUltimoRegistroEnderecos() + 1);
                                        EnderecoDAO.gravarEnderecos(orderDet.getEndereco());
                                        codigoEndereco.add(orderDet.getEndereco().getCodigo());
                                        enderecoNovo = true;
                                    } 
                                    /**
                                     * Se o endereço existir, atribui o código
                                     * ao cliente
                                     */
                                    else {
                                        orderDet.getEndereco().setCodigo(EnderecoDAO.retornaCodPorCep(orderDet.getEndereco().getCep()));
                                        codigoEndereco.add(orderDet.getEndereco().getCodigo());
                                    }
                                    
                                    /**
                                     * Verifica se o contato está cadastrado no
                                     * sistema
                                     */
                                    if (!ContatoDAO.verificaContato(orderDet.getContato())) {
                                        /**
                                         * Se o contato não está cadastrado,
                                         * realiza o cadastro
                                         */
                                        orderDet.getContato().setCod(ContatoDAO.retornaUltimoRegistroContatos() + 1);
                                        ContatoDAO.gravaContatos(orderDet.getContato());
                                        codigoContato.add(orderDet.getContato().getCod());
                                        contatoNovo = true;
                                    }
                                    
                                    /**
                                     * Se o contato existir, atribui o código ao
                                     * cliente
                                     */
                                    else {
                                        orderDet.getContato().setCod(ContatoDAO.retornaCodPorTelefone(orderDet.getContato()));
                                        codigoContato.add(orderDet.getContato().getCod());
                                    }
                                    
                                    /**
                                     * Verifica se o cliente está cadastrado no
                                     * sistema
                                     */
                                    if (!ClienteDAO.verificaCadastroCliente(orderDet.getCliente())) {
                                        /**
                                         * Se o cliente não está cadastrado,
                                         * realiza o cadastro
                                         */
                                        orderDet.getCliente().setCodigo(ClienteDAO.retornaUltimoRegistroClientes(orderDet.getCliente().getTipoPessoa()) + 1);
                                        orderDet.getCliente().setCodigoAtendente("SIS");
                                        orderDet.getCliente().setNomeAtendente("SISTEMA DE VENDAS ONLINE");
                                        orderDet.getCliente().setAtividade("MILITAR");
                                        ClienteDAO.gravarClientes(orderDet.getCliente(), orderDet.getCliente().getTipoPessoa());
                                        clienteNovo = true;
                                        /**
                                         * Faz a associação do endereço e
                                         * contato cadastrados ao cliente
                                         */
                                        if (contatoNovo) {
                                            ClienteDAO.associacaoClientesContatos(codigoContato, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                        } else if (enderecoNovo) {
                                            ClienteDAO.associacaoClientesEnderecos(codigoEndereco, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                        } else if (clienteNovo) {
                                            ClienteDAO.associacaoClientes(codigoEndereco, codigoContato, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                        }
                                    } /**
                                     * Caso o cliente já esteja cadastrado no
                                     * sistema, atribui o código ao cliente
                                     */
                                    else {
                                        switch (orderDet.getCliente().getTipoPessoa()) {
                                            case 1:
                                                orderDet.getCliente().setCodigo(ClienteDAO.retornaCodPorDoc(
                                                        Controle.retornaDocumentoFormatado(orderDet.getCliente().getCpf(), orderDet.getCliente().getTipoPessoa()),
                                                        orderDet.getCliente().getTipoPessoa()));
                                                break;
                                            case 2:
                                                orderDet.getCliente().setCodigo(ClienteDAO.retornaCodPorDoc(
                                                        Controle.retornaDocumentoFormatado(orderDet.getCliente().getCnpj(), orderDet.getCliente().getTipoPessoa()),
                                                        orderDet.getCliente().getTipoPessoa()));
                                                break;
                                        }

                                        if (contatoNovo) {
                                            ClienteDAO.associacaoClientesContatos(codigoContato, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                        } else if (enderecoNovo) {
                                            ClienteDAO.associacaoClientesEnderecos(codigoEndereco, orderDet.getCliente().getCodigo(), orderDet.getCliente().getTipoPessoa());
                                        }
                                    }

                                    /**
                                     * Realiza o cadastro do orçamento
                                     */
                                    orderDet.getOrcamento().setCod(OrcamentoDAO.retornaUltimoRegistro() + 1);
                                    orderDet.getOrcamento().setCodCliente(orderDet.getCliente().getCodigo());
                                    orderDet.getOrcamento().setTipoPessoa(orderDet.getCliente().getTipoPessoa());
                                    orderDet.getOrcamento().setCodContato(orderDet.getContato().getCod());
                                    orderDet.getOrcamento().setCodEndereco(orderDet.getEndereco().getCodigo());
                                    OrcamentoDAO.createOrcamentos(orderDet.getOrcamento());

                                    /**
                                     * Realiza o cadastro dos produtos
                                     * associados ao orçamento
                                     */
                                    for (ProdOrcamento produto : orderDet.getProdutos()) {
                                        produto.setCodOrcamento(orderDet.getOrcamento().getCod());
                                        produto.setValorDigital(0.01d);
                                        produto.setMaquina(1);
                                        OrcamentoDAO.criaProdOrc(produto);
                                    }

                                    realizaRequisicaoPUT((byte) 4, orderDet.getOrcamento().getCod());
                                    break;
                            }
                        }
                        labelSincronizacao.setText("ÚLTIMA SINCRONIZAÇÃO: " + new java.sql.Timestamp(new Date().getTime()).toString());
                        Controle.atualizaSyncPedidos(new java.sql.Timestamp(new Date().getTime()));
                        Thread.sleep(60000);
                    }
                } catch (InterruptedException | IOException | SQLException ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(null);
                } catch (Exception ex) {
                    EnvioExcecao envioExcecao = new EnvioExcecao(Controle.getDefaultGj(), ex);
                    EnvioExcecao.envio(null);
                }
            }
        }.start();
    }
}

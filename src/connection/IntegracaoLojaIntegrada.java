/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.lojaIntegrada.Category;
import entities.lojaIntegrada.Product;
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
import ui.controle.Controle;

/**
 *
 * @author claud
 */
public class IntegracaoLojaIntegrada {

    public static void main(String[] args) {
        try {
            Controle.preencheDadosConexaoInternet();
            Controle.preencheDadosConexaoLojaIntegrada();
            Product produto = new Product(
                    null,
                    "prod-simples",
                    "Produto teste integração",
                    "Produto teste integração",
                    true,
                    (float) 0.01,
                    (float) 0.01,
                    (float) 0.01,
                    (float) 0.01,
                    "normal"
            );

            realizaRequisicaoPOST((byte) 5, produto);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(IntegracaoLojaIntegrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void realizaRequisicaoGET(byte tipo) {
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

                    client = HttpClient.newBuilder()
                            .proxy(ProxySelector.of(new InetSocketAddress(Controle.HOST_PROXY, Controle.PORT_PROXY)))
                            .build();

                    request = HttpRequest.newBuilder()
                            .uri(URI.create(Controle.LINK_API
                                    + "/"
                                    + Controle.VERSAO_API
                                    + "/"
                                    + "categoria/?"
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
                    System.out.println(response.statusCode());
                    System.out.println(response.body());

                    break;
                case 2:
                    break;
                case 3:
                    break;
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
            Logger.getLogger(IntegracaoLojaIntegrada.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(IntegracaoLojaIntegrada.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     */
    public static void realizaRequisicaoPOST(byte tipo, Object requisicao) throws IOException, InterruptedException {
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
                                + "categoria/?"
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
                System.out.println(response);
                System.out.println(request);

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
                        put("id", String.valueOf(product.getId()));
                        put("sku", String.valueOf(product.getSku()));
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
                                + "produto/?"
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
                System.out.println(response.body());
                System.out.println(request);
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

    }

    /**
     * @param tipo 1 - Alterar produto, 2 - Alterar preço produto
     * @param requisicao
     * @throws IOException
     * @throws InterruptedException 
     */
    public static void realizaRequisicaoPUT(byte tipo, Object requisicao) throws IOException, InterruptedException {
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
                        put("id", String.valueOf(product.getId()));
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

                System.out.println("CÓDIGO DO PRODUTO: " + product.getId());
                
                request = HttpRequest.newBuilder()
                        .uri(URI.create(Controle.LINK_API
                                + Controle.SEPARADOR
                                + Controle.VERSAO_API
                                + Controle.SEPARADOR
                                + "produto"
                                + Controle.SEPARADOR
                                + product.getId() 
                                + Controle.SEPARADOR 
                                + "?"
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
                System.out.println(response.body());
                System.out.println(request);
                break;
            case 2:
                product = (Product) requisicao;
                values = new HashMap<String, Object>() {
                    {
                        put("cheio", String.valueOf(product.getValorCusto()));
                        put("custo", String.valueOf(product.getValorCusto()));
                        put("promocional", String.valueOf(product.getValorPromocional()));
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
                                + "produto_preco"
                                + Controle.SEPARADOR
                                + product.getId()
                                + Controle.SEPARADOR
                                + "?"
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
                System.out.println(response.body());
                System.out.println(request);
                break;
        }
    }
}

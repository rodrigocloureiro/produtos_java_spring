package br.com.infnet.at.service;

import br.com.infnet.at.exception.ProdutoConflictException;
import br.com.infnet.at.exception.ProdutoNotFoundException;
import br.com.infnet.at.model.Cotacao;
import br.com.infnet.at.model.CotacaoPayload;
import br.com.infnet.at.model.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class ProdutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private HashMap<Integer, Produto> produtos = populaProdutos();
    private int lastId = produtos.size();

    private HashMap<Integer, Produto> populaProdutos() {
        HashMap<Integer, Produto> produtos = new HashMap<>();

        ArrayList<String> tamanhosCamisa = new ArrayList<>();
        tamanhosCamisa.add("P");
        tamanhosCamisa.add("GG");

        ArrayList<String> tamanhosCamiseta = new ArrayList<>();
        tamanhosCamiseta.add("G");
        tamanhosCamiseta.add("GG");

        ArrayList<String> tamanhosCalca = new ArrayList<>();
        tamanhosCalca.add("M");
        tamanhosCalca.add("G");


        Produto camisa = Produto.builder().id(1).nome("Camisa Regata Verão").preco(75.90).tamanhos(tamanhosCamisa).build();
        Produto camiseta = Produto.builder().id(2).nome("Camiseta Lisa").preco(50.00).tamanhos(tamanhosCamiseta).build();
        Produto calca = Produto.builder().id(3).nome("Calça Jeans").preco(100.00).tamanhos(tamanhosCalca).build();

        produtos.put(camisa.getId(), camisa);
        produtos.put(camiseta.getId(), camiseta);
        produtos.put(calca.getId(), calca);

        return produtos;
    }

    public Produto getById(int id) {
        Produto produto = produtos.get(id);
        if (produto == null) throw new ProdutoNotFoundException("Produto não encontrado!");
        return produto;
    }

    public List<Produto> getAll(Double preco, String tamanho, String moeda) {
        try {
            if (preco != null && tamanho != null) return filter(preco, tamanho);
            if (moeda != null) return currencyConversion(moeda);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return getAll();
    }

    public List<Produto> getAll() {
        if (produtos.isEmpty()) throw new ProdutoNotFoundException("Produto não encontrado!");
        return produtos.values().stream().toList();
    }

    public void deleteById(int id) {
        Produto produto = getById(id);
        if (produto == null) throw new ProdutoNotFoundException("Produto não encontrado!");
        produtos.remove(id);
    }

    public List<Produto> add(Produto produto) {
        /*for (Produto item : produtos.values()) {
            if (item.getNome().equals(produto.getNome())
                    && item.getPreco() == produto.getPreco()
                    && item.getTamanhos().equals(produto.getTamanhos())) {
                throw new ProdutoConflictException("Produto já existe na base de dados!");
            }
        }*/
        if (produtos.containsKey(produto.getId())) throw new ProdutoConflictException("Produto já existe na base de dados!");
        produto.setId(++this.lastId);
        produtos.put(produto.getId(), produto);
        return produtos.values().stream().toList();
    }

    public Produto update(int id, Produto produtoAtualizado) {
        Produto produto = getById(id);
        if (produto == null) throw new ProdutoNotFoundException("Produto não encontrado!");
        produtoAtualizado.setId(id);
        produtos.replace(id, produtoAtualizado);
        return produtoAtualizado;
    }

    public List<Produto> filter(Double preco, String tamanho) {
        List<Produto> produtos = getAll();

        if (preco != null) {
            produtos = produtos.stream()
                    .filter(produto -> produto.getPreco() <= preco)
                    .toList();
        }

        if (tamanho != null) {
            produtos = produtos.stream()
                    .filter(produto -> produto.getTamanhos().contains(tamanho))
                    .toList();
        }

        if (produtos.isEmpty()) throw new ProdutoNotFoundException("Produto não encontrado!");

        return produtos;
    }

    public List<Produto> currencyConversion(String moeda) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://economia.awesomeapi.com.br/last/BRL-" + moeda.toUpperCase();
        HttpResponse<String> response = response(url);
        LOGGER.info("Status Code: " + response.statusCode());

        if (response.statusCode() >= 400 && response.statusCode() < 500)
            throw new RuntimeException("Erro ao efetuar requisição!");

        ObjectMapper objectMapper = JsonMapper.builder().build();
        CotacaoPayload cotacaoPayload = objectMapper.readValue(response.body(), CotacaoPayload.class);
        Cotacao cotacao = cotacaoPayload.getCotacao();

        List<Produto> produtos = getAll();

        for (Produto prod : produtos) {
            prod.setPrecoEstrangeiro(formatTwoDecimal(prod.getPreco(), cotacao.getAsk()));
        }

        return produtos;
    }

    private double formatTwoDecimal(double valor, double moeda) {
        DecimalFormatSymbols dfSymbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat df = new DecimalFormat("0.00", dfSymbols);
        df.setRoundingMode(RoundingMode.DOWN);

        return Double.parseDouble(df.format(valor * moeda));
    }

    private HttpResponse<String> response(String url) throws URISyntaxException, IOException, InterruptedException {
        return client().send(request(url), HttpResponse.BodyHandlers.ofString());
    }

    private HttpClient client() {
        return HttpClient.newBuilder().build();
    }

    private HttpRequest request(String url) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(new URI(url))
                .build();
    }
}

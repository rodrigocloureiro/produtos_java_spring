package br.com.infnet.at.service;

import br.com.infnet.at.exception.ProdutoConflict;
import br.com.infnet.at.exception.ProdutoNotFound;
import br.com.infnet.at.model.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProdutoService {
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


        Produto camisa = new Produto(1, "Camisa Regata Verão", 75.90, tamanhosCamisa);
        Produto camiseta = new Produto(2, "Camiseta Lisa", 50.0, tamanhosCamiseta);
        Produto calca = new Produto(3, "Calça Jeans", 100.0, tamanhosCalca);

        produtos.put(camisa.getId(), camisa);
        produtos.put(camiseta.getId(), camiseta);
        produtos.put(calca.getId(), calca);

        return produtos;
    }

    public Produto getById(int id) {
        Produto produto = produtos.get(id);
        if (produto == null) throw new ProdutoNotFound("Produto não encontrado!");
        return produto;
    }

    public List<Produto> getAll() {
        if (produtos.isEmpty()) throw new ProdutoNotFound("Produto não encontrado!");
        return produtos.values().stream().toList();
    }

    public void deleteById(int id) {
        Produto produto = getById(id);
        if (produto == null) throw new ProdutoNotFound("Produto não encontrado!");
        produtos.remove(id);
    }

    public List<Produto> add(Produto produto) {
        /*for (Produto item : produtos.values()) {
            if (item.getNome().equals(produto.getNome())
                    && item.getPreco() == produto.getPreco()
                    && item.getTamanhos().equals(produto.getTamanhos())) {
                throw new ProdutoConflict("Produto já existe na base de dados!");
            }
        }*/
        if (produtos.containsKey(produto.getId())) throw new ProdutoConflict("Produto já existe na base de dados!");
        produto.setId(++this.lastId);
        produtos.put(produto.getId(), produto);
        return produtos.values().stream().toList();
    }

    public Produto update(int id, Produto produtoAtualizado) {
        Produto produto = getById(id);
        if (produto == null) throw new ProdutoNotFound("Produto não encontrado!");
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

        if (produtos.isEmpty()) throw new ProdutoNotFound("Produto não encontrado!");

        return produtos;
    }
}

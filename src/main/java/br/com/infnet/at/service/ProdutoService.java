package br.com.infnet.at.service;

import br.com.infnet.at.exception.ProdutoConflict;
import br.com.infnet.at.exception.ProdutoNotFound;
import br.com.infnet.at.model.Roupa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private HashMap<Integer, Roupa> produtos = populaProdutos();
    private int lastId = produtos.size();

    private HashMap<Integer, Roupa> populaProdutos() {
        HashMap<Integer, Roupa> produtos = new HashMap<>();

        ArrayList<String> tamanhosCamisa = new ArrayList<>();
        tamanhosCamisa.add("P");
        tamanhosCamisa.add("GG");

        ArrayList<String> tamanhosCamiseta = new ArrayList<>();
        tamanhosCamiseta.add("G");
        tamanhosCamiseta.add("GG");

        ArrayList<String> tamanhosCalca = new ArrayList<>();
        tamanhosCalca.add("M");
        tamanhosCalca.add("G");


        Roupa camisa = new Roupa(1, "Camisa Regata Verão", 75.90, tamanhosCamisa);
        Roupa camiseta = new Roupa(2, "Camiseta Lisa", 50.0, tamanhosCamiseta);
        Roupa calca = new Roupa(3, "Calça Jeans", 100.0, tamanhosCalca);

        produtos.put(camisa.getId(), camisa);
        produtos.put(camiseta.getId(), camiseta);
        produtos.put(calca.getId(), calca);

        return produtos;
    }

    public Roupa getById(int id) {
        Roupa produto = produtos.get(id);
        if (produto == null) throw new ProdutoNotFound("Produto não encontrado!");
        return produto;
    }

    public List<Roupa> getAll() {
        if (produtos.isEmpty()) throw new ProdutoNotFound("Produto não encontrado!");
        return produtos.values().stream().toList();
    }

    public void deleteById(int id) {
        Roupa produto = getById(id);
        if (produto == null) throw new ProdutoNotFound("Produto não encontrado!");
        produtos.remove(id);
    }

    public List<Roupa> add(Roupa produto) {
        /*for (Roupa item : produtos.values()) {
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

    public Roupa update(int id, Roupa produtoAtualizado) {
        Roupa produto = getById(id);
        if (produto == null) throw new ProdutoNotFound("Produto não encontrado!");
        produtoAtualizado.setId(id);
        produtos.replace(id, produtoAtualizado);
        return produtoAtualizado;
    }

    public List<Roupa> filter(Double preco, String tamanho) {
        List<Roupa> produtos = getAll();

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

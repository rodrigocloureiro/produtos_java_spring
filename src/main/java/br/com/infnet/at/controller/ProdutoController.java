package br.com.infnet.at.controller;

import br.com.infnet.at.exception.ProdutoConflict;
import br.com.infnet.at.exception.ProdutoNotFound;
import br.com.infnet.at.model.Roupa;
import br.com.infnet.at.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable int id) {
        try {
            Roupa produto = produtoService.getById(id);
            return ResponseEntity.ok(produto.toString());
        } catch (ProdutoNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Roupa>> getAll() {
        List<Roupa> produtos = produtoService.getAll();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Roupa>> getFiltered(@RequestParam(required = false) Double preco,
                                                   @RequestParam(required = false) String tamanho) {
        List<Roupa> produtos = produtoService.getAll().stream().toList();

        if (preco != null && tamanho != null) {
            List<Roupa> produtosFiltrados = produtos.stream()
                    .filter(produto ->
                            produto.getPreco() <= preco && produto.getTamanhos().contains(tamanho))
                    .toList();
            return ResponseEntity.ok(produtosFiltrados);
        } else if (preco != null) {
            List<Roupa> produtosFiltrados = produtos.stream()
                    .filter(produto ->
                            produto.getPreco() <= preco)
                    .toList();
            return ResponseEntity.ok(produtosFiltrados);
        } else if (tamanho != null) {
            List<Roupa> produtosFiltrados = produtos.stream()
                    .filter(produto ->
                            produto.getTamanhos().contains(tamanho))
                    .toList();
            return ResponseEntity.ok(produtosFiltrados);
        }

        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        try {
            Roupa produto = produtoService.getById(id);
            produtoService.deleteById(id);
            return ResponseEntity.ok(produto.toString());
        } catch (ProdutoNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<String> add(@RequestBody Roupa produto) {
        try {
            produtoService.add(produto);
            return ResponseEntity.ok(produtoService.getAll().toString());
        } catch (ProdutoConflict ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Roupa produto) {
        try {
            Roupa produtoAtualizado = produtoService.update(id, produto);
            return ResponseEntity.ok(produtoAtualizado.toString());
        } catch (ProdutoNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

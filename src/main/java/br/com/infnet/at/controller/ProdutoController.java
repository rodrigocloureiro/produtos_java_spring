package br.com.infnet.at.controller;

import br.com.infnet.at.exception.ProdutoConflict;
import br.com.infnet.at.exception.ProdutoNotFound;
import br.com.infnet.at.model.Roupa;
import br.com.infnet.at.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable int id) {
        try {
            Roupa produto = produtoService.getById(id);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(produto.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getAll() {
        try {
            List<Roupa> produtos = produtoService.getAll();
            LOGGER.info(produtos.toString());
            return ResponseEntity.ok(produtos.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/filtro")
    public ResponseEntity<String> getFiltered(@RequestParam(required = false) Double preco,
                                                   @RequestParam(required = false) String tamanho) {
        try {
            List<Roupa> produtos = produtoService.getAll().stream().toList();

            if (preco != null && tamanho != null) {
                List<Roupa> produtosFiltrados = produtos.stream()
                        .filter(produto ->
                                produto.getPreco() <= preco && produto.getTamanhos().contains(tamanho))
                        .toList();
                LOGGER.info(produtosFiltrados.toString());
                return ResponseEntity.ok(produtosFiltrados.toString());
            } else if (preco != null) {
                List<Roupa> produtosFiltrados = produtos.stream()
                        .filter(produto ->
                                produto.getPreco() <= preco)
                        .toList();
                LOGGER.info(produtosFiltrados.toString());
                return ResponseEntity.ok(produtosFiltrados.toString());
            } else if (tamanho != null) {
                List<Roupa> produtosFiltrados = produtos.stream()
                        .filter(produto ->
                                produto.getTamanhos().contains(tamanho))
                        .toList();
                LOGGER.info(produtosFiltrados.toString());
                return ResponseEntity.ok(produtosFiltrados.toString());
            }

            LOGGER.info(produtos.toString());
            return ResponseEntity.ok(produtos.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        try {
            Roupa produto = produtoService.getById(id);
            produtoService.deleteById(id);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(produto.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<String> add(@RequestBody Roupa produto) {
        try {
            produtoService.add(produto);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(produtoService.getAll().toString());
        } catch (ProdutoConflict ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Roupa produto) {
        try {
            Roupa produtoAtualizado = produtoService.update(id, produto);
            LOGGER.info(produtoAtualizado.toString());
            return ResponseEntity.ok(produtoAtualizado.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

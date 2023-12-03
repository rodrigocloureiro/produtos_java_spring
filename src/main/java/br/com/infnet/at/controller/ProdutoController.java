package br.com.infnet.at.controller;

import br.com.infnet.at.exception.ProdutoConflict;
import br.com.infnet.at.exception.ProdutoNotFound;
import br.com.infnet.at.model.Produto;
import br.com.infnet.at.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
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
            Produto produto = produtoService.getById(id);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(produto.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> getAll(@RequestParam(required = false) Double preco,
                                         @RequestParam(required = false) String tamanho,
                                         @RequestParam(required = false) String moeda) {
        try {
            List<Produto> produtos = produtoService.getAll(preco, tamanho, moeda);
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
            Produto produto = produtoService.getById(id);
            produtoService.deleteById(id);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(produto.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<String> add(@RequestBody Produto produto) {
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
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.update(id, produto);
            LOGGER.info(produtoAtualizado.toString());
            return ResponseEntity.ok(produtoAtualizado.toString());
        } catch (ProdutoNotFound ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

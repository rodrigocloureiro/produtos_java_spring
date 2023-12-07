package br.com.infnet.at.controller;

import br.com.infnet.at.exception.CurrencyConversionException;
import br.com.infnet.at.exception.ProdutoConflictException;
import br.com.infnet.at.exception.ProdutoNotFoundException;
import br.com.infnet.at.model.Produto;
import br.com.infnet.at.model.ResponsePayload;
import br.com.infnet.at.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    @Autowired
    ProdutoService produtoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload> getById(@PathVariable int id) {
        try {
            Produto produto = produtoService.getById(id);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(new ResponsePayload(HttpStatus.OK.toString(), List.of(produto)));
        } catch (ProdutoNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePayload(ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ResponsePayload> getAll(@RequestParam(required = false) Optional<Double> preco,
                                                  @RequestParam(required = false) Optional<String> tamanho,
                                                  @RequestParam(required = false) Optional<String> moeda) {
        try {
            List<Produto> produtos = produtoService.getAll(preco, tamanho, moeda);
            LOGGER.info(produtos.toString());
            return ResponseEntity.ok(new ResponsePayload(HttpStatus.OK.toString(), produtos));
        } catch (ProdutoNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePayload(ex.getMessage()));
        } catch (CurrencyConversionException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> deleteById(@PathVariable int id) {
        try {
            Produto produto = produtoService.getById(id);
            produtoService.deleteById(id);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(new ResponsePayload(HttpStatus.OK.toString(), List.of(produto)));
        } catch (ProdutoNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePayload(ex.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<ResponsePayload> add(@RequestBody Produto produto) {
        try {
            produtoService.add(produto);
            LOGGER.info(produto.toString());
            return ResponseEntity.ok(new ResponsePayload(HttpStatus.OK.toString(), produtoService.getAll()));
        } catch (ProdutoConflictException ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponsePayload(ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> update(@PathVariable int id, @RequestBody Produto produto) {
        try {
            Produto produtoAtualizado = produtoService.update(id, produto);
            LOGGER.info(produtoAtualizado.toString());
            return ResponseEntity.ok(new ResponsePayload(HttpStatus.OK.toString(), List.of(produtoAtualizado)));
        } catch (ProdutoNotFoundException ex) {
            LOGGER.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePayload(ex.getMessage()));
        }
    }
}

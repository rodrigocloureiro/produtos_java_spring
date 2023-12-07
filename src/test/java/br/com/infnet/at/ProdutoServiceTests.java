package br.com.infnet.at;

import br.com.infnet.at.exception.ProdutoNotFoundException;
import br.com.infnet.at.model.Produto;
import br.com.infnet.at.service.ProdutoService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoServiceTests {
    @Autowired
    ProdutoService produtoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoServiceTests.class);

    @Test
    @DisplayName("Deve retornar todos os produtos")
    public void testGetAll() {
        int produtosCount = produtoService.getAll().size();
        LOGGER.info("QTD PRODUTOS: " + produtosCount);

        assertEquals(3, produtosCount);
    }

    @Test
    @DisplayName("Deve retornar um produto pelo ID")
    public void testGetById() {
        Produto produto = produtoService.getById(1);
        LOGGER.info(produto.toString());

        assertEquals(1, produto.getId());
        assertEquals("Camisa Regata Verão", produto.getNome());
        assertEquals(75.9, produto.getPreco());
        assertEquals(List.of("P", "GG"), produto.getTamanhos());
    }

    @Test
    @DisplayName("Deve retornar um produto com as condições do filtro")
    public void testGetFiltered() {
        Produto chinelo = Produto.builder()
                .nome("Chinelo Casual")
                .preco(59.90)
                .tamanhos(new ArrayList<>(List.of("39", "40", "41", "42", "43", "44")))
                .build();
        produtoService.add(chinelo);
        LOGGER.info(chinelo.toString());

        List<Produto> filtrado = produtoService.filter(Optional.of(60.0), Optional.of("42"));
        LOGGER.info(filtrado.get(0).toString());

        assertEquals(chinelo.getId(), filtrado.get(0).getId());
        assertEquals(chinelo.getNome(), filtrado.get(0).getNome());
        assertEquals(chinelo.getPreco(), filtrado.get(0).getPreco());
        assertEquals(chinelo.getTamanhos(), filtrado.get(0).getTamanhos());

        produtoService.deleteById(chinelo.getId());
    }

    @Test
    @DisplayName("Deve retornar uma exceção ao tentar deletar um produto não existente")
    public void testDeleteByIdError() {
        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.deleteById(10);
        });
    }

    @Test
    @DisplayName("Deve deletar um produto pelo ID")
    public void testDeleteById() {
        Produto produto = produtoService.getById(1);
        int produtosCount = produtoService.getAll().size();
        LOGGER.info("QTD PRODUTOS: " + produtosCount);

        LOGGER.info(produtoService.getAll().toString());

        produtoService.deleteById(1);
        int produtoDeletadoCount = produtoService.getAll().size();
        LOGGER.info("QTD PRODUTOS APÓS REMOÇÃO: " + produtoDeletadoCount);

        assertEquals(2, produtoDeletadoCount);

        produtoService.add(produto);
    }

    @Test
    @Order(2)
    @DisplayName("Deve adicionar/inserir um produto")
    public void testAdd() {
        Produto bola = Produto.builder()
                .nome("Bola de Futebol")
                .preco(59.90)
                .tamanhos(new ArrayList<>(List.of("Universal")))
                .build();
        LOGGER.info(bola.toString());

        produtoService.add(bola);
        int produtosCount = produtoService.getAll().size();

        assertEquals(4, produtosCount);
        assertEquals(5, bola.getId());
        assertEquals("Bola de Futebol", bola.getNome());
        assertEquals(59.90, bola.getPreco());
        assertEquals(List.of("Universal"), bola.getTamanhos());

        produtoService.deleteById(5);
    }

    @Test
    @Order(1)
    @DisplayName("Deve atualizar o produto")
    public void testUpdate() {
        Produto prod = Produto.builder()
                .nome("Bola de Futebol")
                .preco(59.90)
                .tamanhos(new ArrayList<>(List.of("Universal")))
                .build();
        LOGGER.info(prod.toString());
        produtoService.add(prod);

        prod.setNome("Bola Esportiva");
        produtoService.update(prod.getId(), prod);
        LOGGER.info(produtoService.getAll().toString());

        assertEquals("Bola Esportiva", produtoService.getById(4).getNome());

        produtoService.deleteById(prod.getId());
    }

    @Test
    @DisplayName("Deve retornar o valor do produto em dólar (USD)")
    public void testCurrencyConversion() {
        Produto produto = produtoService.getById(1);
        LOGGER.info(String.valueOf(produto.getPrecoEstrangeiro()));
        assertEquals(0.0, produto.getPrecoEstrangeiro());

        try {
            produto = produtoService.currencyConversion("USD").get(1);
            LOGGER.info(String.valueOf(produto.getPrecoEstrangeiro()));
            assertTrue(produto.getPrecoEstrangeiro() > 0.0);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

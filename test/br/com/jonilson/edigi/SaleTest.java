package br.com.jonilson.edigi;

import br.com.jonilson.edigi.dao.BookDao;
import br.com.jonilson.edigi.dao.CategoryDao;
import br.com.jonilson.edigi.dao.SaleDao;
import br.com.jonilson.edigi.model.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SaleTest {

    @Test
    public void itShouldMakeASaleWithOnlyOneItem() {
        SaleDao saleDao = new SaleDao();

        Author author = new Author("ana", "ana@gmail.com");
        Category category = new Category("Programação");
        Book book = new Book(
                "Aplicações web real-time com Node.js",
                "Node.js é uma poderosa plataforma. Ele permite escrever aplicações JavaScript no server-side, tirando proveito da sintaxe e familiaridade da linguagem para escrever aplicações web escaláveis. Como o Node.js usa um modelo orientado a eventos, focado em I/O não bloqueante, desenvolver nele pode ser diferente para quem está acostumado às aplicações web tradicionais. Neste livro, Caio Ribeiro Pereira quebra essa enorme barreira, mostrando claramente essa mudança de paradigma, além de focar em tópicos importantes, as APIs principais e frameworks como o Express e o Socket.IO.",
                "1 Bem-vindo ao mundo Node.js" +
                        " 1.1 O problema das arquiteturas bloqueantes" +
                        " 1.2 E assim nasceu o Node.js" +
                        " 1.3 Single-thread",
                185,
                "978-85-66250-14-5",
                author,
                category,
                1,
                29.90
        );

        SaleItem item = new SaleItem(book, 3);

        Sale sale = new Sale(item);

        saleDao.add(sale);

        assertTrue(saleDao.list().contains(sale));
    }

    @Test
    public void itShouldMakeASaleWithTwoItem() {
        SaleDao saleDao = new SaleDao();

        Author author = new Author("ana", "ana@gmail.com");
        Category category = new Category("Programação");

        Book book1 = new Book(
                "Aplicações web real-time com Node.js",
                "Node.js é uma poderosa plataforma. Ele permite escrever aplicações JavaScript no server-side, tirando proveito da sintaxe e familiaridade da linguagem para escrever aplicações web escaláveis. Como o Node.js usa um modelo orientado a eventos, focado em I/O não bloqueante, desenvolver nele pode ser diferente para quem está acostumado às aplicações web tradicionais. Neste livro, Caio Ribeiro Pereira quebra essa enorme barreira, mostrando claramente essa mudança de paradigma, além de focar em tópicos importantes, as APIs principais e frameworks como o Express e o Socket.IO.",
                "1 Bem-vindo ao mundo Node.js" +
                        " 1.1 O problema das arquiteturas bloqueantes" +
                        " 1.2 E assim nasceu o Node.js" +
                        " 1.3 Single-thread",
                185,
                "978-85-66250-14-5",
                author,
                category,
                1,
                29.90
        );

        Book book2 = new Book(
                "Aplicações com PHP",
                "Node.js é uma poderosa plataforma. Ele permite escrever aplicações JavaScript no server-side, tirando proveito da sintaxe e familiaridade da linguagem para escrever aplicações web escaláveis. Como o Node.js usa um modelo orientado a eventos, focado em I/O não bloqueante, desenvolver nele pode ser diferente para quem está acostumado às aplicações web tradicionais. Neste livro, Caio Ribeiro Pereira quebra essa enorme barreira, mostrando claramente essa mudança de paradigma, além de focar em tópicos importantes, as APIs principais e frameworks como o Express e o Socket.IO.",
                "1 Bem-vindo ao mundo Node.js" +
                        " 1.1 O problema das arquiteturas bloqueantes" +
                        " 1.2 E assim nasceu o Node.js" +
                        " 1.3 Single-thread",
                143,
                "978-85-66250-14-6",
                author,
                category,
                1,
                32.90
        );

        SaleItem item1 = new SaleItem(book1, 2);
        SaleItem item2 = new SaleItem(book2, 1);

        Sale sale = new Sale(item1);
        sale.addItem(item2);

        saleDao.add(sale);

        assertTrue(saleDao.list().contains(sale));
    }

    @Test
    public void itShouldNotCreateSaleItemWithNullBook() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new SaleItem(null, 3)
        );

        assertEquals("Livro não informado corretamente!", exception.getMessage());
    }

    @Test
    public void itShouldNotCreateSaleItemWithQuantityZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author = new Author("ana", "ana@gmail.com");
            Category category = new Category("Programação");
            Book book = new Book(
                    "Aplicações web real-time com Node.js",
                    "Node.js é uma poderosa plataforma. Ele permite escrever aplicações JavaScript no server-side, tirando proveito da sintaxe e familiaridade da linguagem para escrever aplicações web escaláveis. Como o Node.js usa um modelo orientado a eventos, focado em I/O não bloqueante, desenvolver nele pode ser diferente para quem está acostumado às aplicações web tradicionais. Neste livro, Caio Ribeiro Pereira quebra essa enorme barreira, mostrando claramente essa mudança de paradigma, além de focar em tópicos importantes, as APIs principais e frameworks como o Express e o Socket.IO.",
                    "1 Bem-vindo ao mundo Node.js" +
                            " 1.1 O problema das arquiteturas bloqueantes" +
                            " 1.2 E assim nasceu o Node.js" +
                            " 1.3 Single-thread",
                    185,
                    "978-85-66250-14-5",
                    author,
                    category,
                    1,
                    29.90
            );

            new SaleItem(book, 0);
        });

        assertEquals("A quantidade precisa ser pelo menos 1!", exception.getMessage());
    }

    @Test
    public void itShouldNotCreateSaleWithNullItem() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Sale(null)
        );

        assertEquals("Item da venda não informado corretamente!", exception.getMessage());
    }

    @Test
    public void itShouldNotAddSaleItemNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author = new Author("ana", "ana@gmail.com");
            Category category = new Category("Programação");
            Book book = new Book(
                    "Aplicações web real-time com Node.js",
                    "Node.js é uma poderosa plataforma. Ele permite escrever aplicações JavaScript no server-side, tirando proveito da sintaxe e familiaridade da linguagem para escrever aplicações web escaláveis. Como o Node.js usa um modelo orientado a eventos, focado em I/O não bloqueante, desenvolver nele pode ser diferente para quem está acostumado às aplicações web tradicionais. Neste livro, Caio Ribeiro Pereira quebra essa enorme barreira, mostrando claramente essa mudança de paradigma, além de focar em tópicos importantes, as APIs principais e frameworks como o Express e o Socket.IO.",
                    "1 Bem-vindo ao mundo Node.js" +
                            " 1.1 O problema das arquiteturas bloqueantes" +
                            " 1.2 E assim nasceu o Node.js" +
                            " 1.3 Single-thread",
                    185,
                    "978-85-66250-14-5",
                    author,
                    category,
                    1,
                    29.90
            );

            SaleItem item = new SaleItem(book, 3);

            Sale sale = new Sale(item);
            sale.addItem(null);
        });

        assertEquals("Item da venda não informado corretamente!", exception.getMessage());
    }
}

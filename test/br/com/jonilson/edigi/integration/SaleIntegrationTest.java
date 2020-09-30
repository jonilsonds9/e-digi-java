package br.com.jonilson.edigi.integration;

import br.com.jonilson.edigi.dao.AuthorDao;
import br.com.jonilson.edigi.dao.BookDao;
import br.com.jonilson.edigi.dao.CategoryDao;
import br.com.jonilson.edigi.dao.SaleDao;
import br.com.jonilson.edigi.factory.ConnectionFactory;
import br.com.jonilson.edigi.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SaleIntegrationTest {

    public ConnectionFactory connectionFactory = new ConnectionFactory();
    public Connection connection;

    @BeforeEach
    public void getConnection() {
        this.connection = this.connectionFactory.getConnection();
    }

    @AfterEach
    public void closeConnection() {
        String deleteItems = "DELETE FROM sales_items";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteItems)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        String deleteSales = "DELETE FROM sales";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteSales)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        String deleteAllBooks = "DELETE FROM books";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteAllBooks)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        String deleteAllAuthors = "DELETE FROM authors";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteAllAuthors)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        String deleteAllCategories = "DELETE FROM categories";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteAllCategories)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        this.connectionFactory.closeConnection(this.connection);
    }

    @Test
    public void itShouldMakeASaleWithOnlyOneItem() {
        SaleDao saleDao = new SaleDao(this.connection);
        BookDao bookDao = new BookDao(this.connection);
        AuthorDao authorDao = new AuthorDao(this.connection);
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Author author = new Author("ana", "ana@gmail.com");
        authorDao.add(author);

        Category category = new Category("Programação");
        categoryDao.add(category);

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
        bookDao.add(book);

        SaleItem item = new SaleItem(book, 3);

        Sale sale = new Sale(item);

        saleDao.add(sale);

        assertTrue(saleDao.list().contains(sale));
    }

    @Test
    public void itShouldMakeASaleWithTwoItem() {
        SaleDao saleDao = new SaleDao(this.connection);
        BookDao bookDao = new BookDao(this.connection);
        AuthorDao authorDao = new AuthorDao(this.connection);
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Author author = new Author("ana", "ana@gmail.com");
        authorDao.add(author);

        Category category = new Category("Programação");
        categoryDao.add(category);

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
        bookDao.add(book1);

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
        bookDao.add(book2);

        SaleItem item1 = new SaleItem(book1, 2);
        SaleItem item2 = new SaleItem(book2, 1);

        Sale sale = new Sale(item1);
        sale.addItem(item2);

        saleDao.add(sale);

        System.out.println("--------------- Imprimindo a venda ---------------");
        System.out.println(sale);

//        assertTrue(saleDao.list().contains(sale));
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
        BookDao bookDao = new BookDao(this.connection);
        AuthorDao authorDao = new AuthorDao(this.connection);
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Author author = new Author("ana", "ana@gmail.com");
        authorDao.add(author);

        Category category = new Category("Programação");
        categoryDao.add(category);

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
        bookDao.add(book);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
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
        BookDao bookDao = new BookDao(this.connection);
        AuthorDao authorDao = new AuthorDao(this.connection);
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Author author = new Author("ana", "ana@gmail.com");
        authorDao.add(author);

        Category category = new Category("Programação");
        categoryDao.add(category);

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
        bookDao.add(book);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            SaleItem item = new SaleItem(book, 3);

            Sale sale = new Sale(item);
            sale.addItem(null);
        });

        assertEquals("Item da venda não informado corretamente!", exception.getMessage());
    }
}

package br.com.jonilson.edigi.integration;

import br.com.jonilson.edigi.dao.AuthorDao;
import br.com.jonilson.edigi.dao.BookDao;
import br.com.jonilson.edigi.dao.CategoryDao;
import br.com.jonilson.edigi.factory.ConnectionFactory;
import br.com.jonilson.edigi.model.Author;
import br.com.jonilson.edigi.model.Book;
import br.com.jonilson.edigi.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchBooksIntegrationTest {

    public Connection connection;

    @BeforeEach
    public void getConnection() {
        this.connection = ConnectionFactory.getConnection();
    }

    @AfterEach
    public void closeConnection() {
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

        ConnectionFactory.closeConnection(this.connection);
    }

    @Test
    public void shouldShowBooks() {
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

        bookDao.add(book1);
        bookDao.add(book2);

        List<Book> result = bookDao.searchBooks("Apli");

        assertEquals(2, result.size());
        assertTrue(result.contains(book1));
        assertTrue(result.contains(book2));
    }

    @Test
    public void NotShowBooksWithTitleWithLessThanTwoCharacters() {
        BookDao bookDao = new BookDao(this.connection);
        AuthorDao authorDao = new AuthorDao(this.connection);
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Author author = new Author("ana", "ana@gmail.com");
        authorDao.add(author);

        Category category = new Category("Programação");
        categoryDao.add(category);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
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

            bookDao.searchBooks("A");
        });

        assertEquals("O título precisa conter pelo menos 2 caracteres!", exception.getMessage());
    }

    @Test
    public void shouldNotShowBooksIifNoneAreFound() {
        BookDao bookDao = new BookDao(this.connection);
        AuthorDao authorDao = new AuthorDao(this.connection);
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Author author = new Author("ana", "ana@gmail.com");
        authorDao.add(author);

        Category category = new Category("Programação");
        categoryDao.add(category);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {

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

            bookDao.add(book1);
            bookDao.add(book2);

            bookDao.searchBooks("Testes automatizados");
        });

        assertEquals("Nenhum livro encontrado!", exception.getMessage());
    }
}

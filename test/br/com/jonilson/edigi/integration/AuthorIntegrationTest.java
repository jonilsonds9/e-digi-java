package br.com.jonilson.edigi.integration;

import br.com.jonilson.edigi.dao.AuthorDao;
import br.com.jonilson.edigi.factory.ConnectionFactory;
import br.com.jonilson.edigi.model.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorIntegrationTest {

    public Connection connection;

    @BeforeEach
    public void getConnection() {
        this.connection = ConnectionFactory.getConnection();
    }

    @AfterEach
    public void closeConnection() {
        String deleteAll = "DELETE FROM authors";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteAll)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        ConnectionFactory.closeConnection(this.connection);
    }

    @Test
    public void shouldRegisterAuthor() {
        AuthorDao authorDao = new AuthorDao(this.connection);

        Author author = new Author("ana", "ana@gmail.com");

        authorDao.add(author);

        assertTrue(authorDao.list().contains(author));
    }

    @Test
    public void shouldNotRegisterAuthorInvalid () {
        Exception exceptionWithoutName = assertThrows(IllegalArgumentException.class, () ->
                new Author(null, "ana@gmail.com")
        );

        Exception exceptionWithoutEmail = assertThrows(IllegalArgumentException.class, () ->
                new Author("ana", null)
        );

        Exception exceptionWithEmailInvalid = assertThrows(IllegalArgumentException.class, () ->
                new Author("ana", null)
        );

        assertEquals("O nome é obrigatório!", exceptionWithoutName.getMessage());
        assertEquals("Email informado para o autor é inválido!", exceptionWithoutEmail.getMessage());
        assertEquals("Email informado para o autor é inválido!", exceptionWithEmailInvalid.getMessage());
    }

    @Test
    public void shouldNotRegisterAuthorAlreadyRegistered() {
        AuthorDao authorDao = new AuthorDao(this.connection);

        Author author1 = new Author("ana", "ana@gmail.com");
        authorDao.add(author1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author2 = new Author("ana", "ana@gmail.com");
            authorDao.add(author2);
        });

        assertEquals("Esse email já está cadastrado!", exception.getMessage());
    }
}
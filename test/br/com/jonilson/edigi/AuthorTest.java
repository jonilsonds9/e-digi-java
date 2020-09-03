package br.com.jonilson.edigi;

import br.com.jonilson.edigi.dao.AuthorDao;
import br.com.jonilson.edigi.model.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {

    @Test
    public void shouldRegisterAuthor() {
        AuthorDao authorDao = new AuthorDao();

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
        AuthorDao authorDao = new AuthorDao();

        Author author1 = new Author("ana", "ana@gmail.com");
        authorDao.add(author1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author2 = new Author("ana", "ana@gmail.com");
            authorDao.add(author2);
        });

        assertEquals("Esse email já está cadastrado!", exception.getMessage());
    }
}

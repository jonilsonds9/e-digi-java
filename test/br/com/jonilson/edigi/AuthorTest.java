package br.com.jonilson.edigi;

import br.com.jonilson.edigi.dao.AuthorDao;
import br.com.jonilson.edigi.model.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorTest {

    @Test
    public void shouldRegisterAuthor() {
        AuthorDao authorDao = new AuthorDao();

        Author author = new Author("ana", "ana@gmail.com");

        authorDao.add(author);

        assertEquals(1, authorDao.list().size());
        assertEquals(author.getName(), authorDao.list().iterator().next().getName());
        assertEquals(author.getEmail(), authorDao.list().iterator().next().getEmail());
    }

    @Test
    public void shouldNotRegisterAuthorWithoutName () {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Author(null, "ana@gmail.com")
        );

        assertEquals("O nome é obrigatório!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterAuthorWithoutEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Author("ana", null)
        );

        assertEquals("Email informado para o autor é inválido!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterAuthorWithEmailInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Author("ana", null)
        );

        assertEquals("Email informado para o autor é inválido!", exception.getMessage());
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

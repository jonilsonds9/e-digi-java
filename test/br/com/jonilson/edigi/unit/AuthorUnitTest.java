package br.com.jonilson.edigi.unit;

import br.com.jonilson.edigi.dao.AuthorDao;
import br.com.jonilson.edigi.model.Author;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;

public class AuthorUnitTest {

    @Test
    public void shouldRegisterAuthor() {
        AuthorDao authorDao = Mockito.mock(AuthorDao.class);

        Author author = new Author("ana", "ana@gmail.com");

        Mockito.when(authorDao.list()).thenReturn(new HashSet<>(Arrays.asList(author)));

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
        AuthorDao authorDao = Mockito.mock(AuthorDao.class);

        Mockito.doAnswer(new Answer() {
            private Author authorReceived = null;

            public Object answer(InvocationOnMock invocationOnMock) {
                if (authorReceived == null) {
                    authorReceived = (Author) invocationOnMock.getArguments()[0];
                } else if (authorReceived.equals(invocationOnMock.getArguments()[0])) {
                    throw new IllegalArgumentException("Esse email já está cadastrado!");
                }
                return null;
            }
        }).when(authorDao).add(isA(Author.class));

        Author author1 = new Author("ana", "ana@gmail.com");
        authorDao.add(author1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author2 = new Author("ana", "ana@gmail.com");
            authorDao.add(author2);
        });

        assertEquals("Esse email já está cadastrado!", exception.getMessage());
    }
}

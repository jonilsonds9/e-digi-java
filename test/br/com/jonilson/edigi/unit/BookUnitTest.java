package br.com.jonilson.edigi.unit;

import br.com.jonilson.edigi.dao.BookDao;
import br.com.jonilson.edigi.model.Author;
import br.com.jonilson.edigi.model.Book;
import br.com.jonilson.edigi.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;

public class BookUnitTest {

    @Test
    public void shouldRegisterBook() {
        BookDao bookDao = Mockito.mock(BookDao.class);

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

        Mockito.when(bookDao.list()).thenReturn(new HashSet<>(Arrays.asList(book)));

        bookDao.add(book);

        assertTrue(bookDao.list().contains(book));
    }

    @Test
    public void shouldNotRegisterBookWithoutTitle() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author = new Author("ana", "ana@gmail.com");
            Category category = new Category("Programação");

            Book book = new Book(
                    null,
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
        });

        assertEquals("O título é obrigatório!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithoutResume() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author = new Author("ana", "ana@gmail.com");
            Category category = new Category("Programação");

            Book book = new Book(
                    "Aplicações web real-time com Node.js",
                    null,
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
        });

        assertEquals("O resumo é obrigatório e precisa ter pelo menos 500 caracteres!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithoutSummary() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author = new Author("ana", "ana@gmail.com");
            Category category = new Category("Programação");

            Book book = new Book(
                    "Aplicações web real-time com Node.js",
                    "Node.js é uma poderosa plataforma. Ele permite escrever aplicações JavaScript no server-side, tirando proveito da sintaxe e familiaridade da linguagem para escrever aplicações web escaláveis. Como o Node.js usa um modelo orientado a eventos, focado em I/O não bloqueante, desenvolver nele pode ser diferente para quem está acostumado às aplicações web tradicionais. Neste livro, Caio Ribeiro Pereira quebra essa enorme barreira, mostrando claramente essa mudança de paradigma, além de focar em tópicos importantes, as APIs principais e frameworks como o Express e o Socket.IO.",
                    null,
                    185,
                    "978-85-66250-14-5",
                    author,
                    category,
                    1,
                    29.90
            );
        });

        assertEquals("O sumário é obrigatório e não pode ser vazio!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithNumberPagesNegative() {
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
                    -4,
                    "978-85-66250-14-5",
                    author,
                    category,
                    1,
                    29.90
            );
        });

        assertEquals("O número de página precisa ser maior que zero!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithIsbnInvalid() {
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
                    "978-85-66250-1",
                    author,
                    category,
                    1,
                    29.90
            );
        });

        assertEquals("O ISBN do livro é obrigatório e precisa no formato '978-xx-xxxxx-xx-x'", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithoutAuthor() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
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
                    null,
                    category,
                    1,
                    29.90
            );
        });

        assertEquals("Autor não informado corretamente!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithoutCategory() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Author author = new Author("ana", "ana@gmail.com");

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
                    null,
                    1,
                    29.90
            );
        });

        assertEquals("Categoria não informada corretamente!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithEditionInvalid() {
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
                    -1,
                    29.90
            );
        });

        assertEquals("A edição precisa ser maior que zero!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithPriceInvalid() {
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
                    -29
            );
        });

        assertEquals("O preço deve ser um número positivo!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithTitleAlreadyRegistered() {
        BookDao bookDao = Mockito.mock(BookDao.class);

        Mockito.doAnswer(new Answer() {
            private Book bookReceived = null;

            public Object answer(InvocationOnMock invocationOnMock) {
                if (bookReceived == null) {
                    bookReceived = (Book) invocationOnMock.getArguments()[0];
                } else if (bookReceived.equals(invocationOnMock.getArguments()[0])) {
                    throw new IllegalArgumentException("Esse livro já está cadastrado!");
                }
                return null;
            }
        }).when(bookDao).add(isA(Book.class));

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

        bookDao.add(book1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Book book2 = new Book(
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
            bookDao.add(book2);
        });

        assertEquals("Esse livro já está cadastrado!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterBookWithIsbnAlreadyRegistered() {
        BookDao bookDao = Mockito.mock(BookDao.class);

        Mockito.doAnswer(new Answer() {
            private Book bookReceived = null;

            public Object answer(InvocationOnMock invocationOnMock) {
                if (bookReceived == null) {
                    bookReceived = (Book) invocationOnMock.getArguments()[0];
                } else if (bookReceived.equals(invocationOnMock.getArguments()[0])) {
                    throw new IllegalArgumentException("Esse livro já está cadastrado!");
                }
                return null;
            }
        }).when(bookDao).add(isA(Book.class));

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

        bookDao.add(book1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Book book2 = new Book(
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
            bookDao.add(book2);
        });

        assertEquals("Esse livro já está cadastrado!", exception.getMessage());
    }
}

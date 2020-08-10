package br.com.jonilson.edigi.test;

import br.com.jonilson.edigi.dao.BookDao;
import br.com.jonilson.edigi.model.Author;
import br.com.jonilson.edigi.model.Book;
import br.com.jonilson.edigi.model.Category;

public class BookTest {
    public static void main(String[] args) {
        try {
            BookDao bookDao = new BookDao();

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

            bookDao.add(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

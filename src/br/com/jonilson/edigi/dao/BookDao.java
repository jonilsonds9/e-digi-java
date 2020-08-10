package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Book;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BookDao {
    private Set<Book> books;

    public BookDao() {
        this.books = new HashSet<>();
    }

    public void add(Book book) {
        if (!this.books.add(book)) {
            throw new IllegalArgumentException("Esse livro já está cadastrado!");
        }

        System.out.println("Livro cadastrado com sucesso! \nDados do Livro:");
        System.out.println("Autor: " + book.getAuthor().getName() + "\nLivro: " + book.getTitle() + "\nCadastrado em: " + book.getCreatedAt());
    }

    public Set<Book> list() {
        return Collections.unmodifiableSet(books);
    }
}

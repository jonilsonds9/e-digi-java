package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Book;

import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println(book.infoBookToString());
    }

    public Set<Book> list() {
        return Collections.unmodifiableSet(this.books);
    }

    public List<Book> searchBooks(String title) {
        if (title.length() < 2) {
            throw new IllegalArgumentException("O título precisa conter pelo menos 2 caracteres!");
        }

        List<Book> foundBooks = this.books.stream()
                .filter(e -> e.getTitle().contains(title)).collect(Collectors.toList());

        if (foundBooks.size() == 0) {
            throw new IllegalArgumentException("Nenhum livro encontrado!");
        }

        return foundBooks;
    }
}

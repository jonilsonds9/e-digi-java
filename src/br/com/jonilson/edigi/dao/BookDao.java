package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Book;

import java.util.*;

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
        System.out.println("Autor: " + book.getAuthor().getName() + "\nLivro: " + book.getTitle() + "\nCadastrado em: " + book.getCreatedAt() + "\n");
    }

    public Set<Book> list() {
        return Collections.unmodifiableSet(this.books);
    }

    public List<Book> searchBooks(String title) {
        if (title.length() < 2) {
            throw new IllegalArgumentException("O título precisa conter pelo menos 2 caracteres!");
        }

        List<Book> foundBooks = new ArrayList<>();

         this.books.forEach(e -> {
             if (e.getTitle().contains(title)) {
                 foundBooks.add(e);
             }
        });

         if (foundBooks.size() == 0) {
             throw new IllegalArgumentException("Nenhum livro encontrado!");
         }

        System.out.println("Deu certo!");

        return foundBooks;
    }
}

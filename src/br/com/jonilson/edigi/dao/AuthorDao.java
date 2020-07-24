package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Author;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AuthorDao {
    private Set<Author> authors;

    public AuthorDao() {
        this.authors = new HashSet<>();
    }

    public void add(Author author) {
        if (!this.authors.add(author)) {
            throw new IllegalArgumentException("Esse email já está cadastrado!");
        }

        System.out.println("Autor cadastrado com sucesso! \nDados do Autor:");
        System.out.println("Nome: " + author.getName() + ", email: " + author.getEmail() + "\n");
    }

    public Set<Author> list() {
        return Collections.unmodifiableSet(authors);
    }
}

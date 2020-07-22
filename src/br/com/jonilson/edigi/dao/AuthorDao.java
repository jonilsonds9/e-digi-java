package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Author;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthorDao {
    private List<Author> authors;

    public AuthorDao() {
        this.authors = new ArrayList<>();
    }

    public void add(Author author) {
        if (authors.contains(author)) {
            throw new IllegalArgumentException("Esse email já está cadastrado!");
        }

        this.authors.add(author);

        System.out.println("Autor cadastrado com sucesso! \nDados do Autor:");
        System.out.println("Nome: " + author.getName() + ", email: " + author.getEmail() + "\n");
    }

    public List<Author> list() {
        return Collections.unmodifiableList(authors);
    }
}

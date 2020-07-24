package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Category;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CategoryDao {

    private Set<Category> categories;

    public CategoryDao() {
        this.categories = new HashSet<>();
    }

    public void add(Category category) {
        if (!this.categories.add(category)) {
            throw new IllegalArgumentException("Essa categoria já está cadastrado!");
        }

        System.out.println("Categoria cadastrada com sucesso! \nDados da Categoria:");
        System.out.println("Nome: " + category.getName() + "\n");
    }

    public Set<Category> list() {
        return Collections.unmodifiableSet(categories);
    }
}

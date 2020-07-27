package br.com.jonilson.edigi.model;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Category {

    private String name;
    private ZonedDateTime created_at;

    public Category(String name) {
        this.setName(name);
        this.created_at = ZonedDateTime.now();
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

package br.com.jonilson.edigi.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Category {
    private Integer id;
    private String name;
    private LocalDateTime createdAt;

    public Category(String name) {
        this.setName(name);
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

//    @Override
//    public String toString() {
//        return "Category{" +
//                "name='" + this.name + '\'' +
//                ", createdAt=" + this.createdAt +
//                '}';
//    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
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
        return Objects.hash(this.name);
    }
}

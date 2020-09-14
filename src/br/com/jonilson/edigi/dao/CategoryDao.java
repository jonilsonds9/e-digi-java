package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Category;

import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CategoryDao {

    private Connection connection;

    public CategoryDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Category category) {
        if (this.list().contains(category)) {
            throw new IllegalArgumentException("Essa categoria já está cadastrado!");
        }

        String insert = "INSERT INTO categories (name, created_at) VALUES (?,?)";

        try (PreparedStatement statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            statement.setObject(2, category.getCreatedAt());

            if (statement.executeUpdate() > 0) {

                ResultSet rst = statement.getGeneratedKeys();
                if (rst.next()) {
                    category.setId(rst.getInt(1));
                }

                System.out.println("Categoria cadastrada com sucesso! \nDados da Categoria:");
                System.out.println("Nome: " + category.getName() + "\n");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Set<Category> list() {
        Set<Category> categories = new HashSet<>();

        String query = "SELECT * FROM categories";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();

            try (ResultSet rst = statement.getResultSet()) {
                while (rst.next()) {
                    Category category = new Category(rst.getString(2));
                    category.setId(rst.getInt(1));
                    category.setCreatedAt(rst.getTimestamp(3).toLocalDateTime());

                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        return Collections.unmodifiableSet(categories);
    }
}

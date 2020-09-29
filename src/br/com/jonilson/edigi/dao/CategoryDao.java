package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Author;
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

        String insert = "INSERT INTO categories (name) VALUES (?)";

        try (PreparedStatement statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());

            if (statement.executeUpdate() > 0) {
                ResultSet rst = statement.getGeneratedKeys();

                if (rst.next()) {
                    category.setId(rst.getInt(1));

                    category.setCreatedAt(this.findId(category.getId()).getCreatedAt());

                    System.out.println("Categoria cadastrada com sucesso! \nDados da Categoria:");
                    System.out.println("Nome: " + category.getName() + "\n");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Category> list() {
        Set<Category> categories = new HashSet<>();

        String query = "SELECT * FROM categories";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();

            try (ResultSet rst = statement.getResultSet()) {
                while (rst.next()) {
                    Category category = new Category(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getTimestamp(3).toLocalDateTime());

                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableSet(categories);
    }

    public Category findId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id não foi informado corretamente!");
        }

        String query = "SELECT * FROM categories Where id = ?";

        try (PreparedStatement statementQuery = this.connection.prepareStatement(query)) {
            statementQuery.setInt(1, id);
            ResultSet rst = statementQuery.executeQuery();

            if (rst.next()) {
                return new Category(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getTimestamp(3).toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Erro ao buscar Categoria!");
    }
}

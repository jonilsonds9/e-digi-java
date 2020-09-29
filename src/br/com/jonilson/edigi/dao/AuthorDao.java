package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Author;

import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AuthorDao {
    private Connection connection;

    public AuthorDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Author author) {
        if (this.list().contains(author)) {
            throw new IllegalArgumentException("Esse email já está cadastrado!");
        }

        String insert = "INSERT INTO authors (name, email) VALUES (?,?)";

        try (PreparedStatement statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getEmail());

            if (statement.executeUpdate() > 0) {

                ResultSet rst = statement.getGeneratedKeys();
                if (rst.next()) {
                    author.setId(rst.getInt(1));

                    author.setCreatedAt(this.findId(author.getId()).getCreatedAt());

                    System.out.println("Autor cadastrado com sucesso! \nDados do Autor:");
                    System.out.println("Nome: " + author.getName() + ", email: " + author.getEmail() + "\n");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Author> list() {
        Set<Author> authors = new HashSet<>();

        String query = "SELECT * FROM authors";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();

            try (ResultSet rst = statement.getResultSet()) {
                while (rst.next()) {
                    Author author = new Author(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getTimestamp(4).toLocalDateTime());

                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableSet(authors);
    }

    public Author findId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id não foi informado corretamente!");
        }

        String query = "SELECT * FROM authors Where id = ?";

        try (PreparedStatement statementQuery = this.connection.prepareStatement(query)) {
            statementQuery.setInt(1, id);
            ResultSet rst = statementQuery.executeQuery();

            if (rst.next()) {
                return new Author(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getTimestamp(4).toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Erro ao buscar Autor!");
    }
}

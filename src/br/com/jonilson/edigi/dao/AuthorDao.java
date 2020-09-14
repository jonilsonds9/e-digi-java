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

        String insert = "INSERT INTO authors (name, email, created_at) VALUES (?,?,?)";

        try (PreparedStatement statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getEmail());
            statement.setObject(3, author.getCreatedAt());

            if (statement.executeUpdate() > 0) {

                ResultSet rst = statement.getGeneratedKeys();
                if (rst.next()) {
                    author.setId(rst.getInt(1));
                }

                System.out.println("Autor cadastrado com sucesso! \nDados do Autor:");
                System.out.println("Nome: " + author.getName() + ", email: " + author.getEmail() + "\n");
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Set<Author> list() {
        Set<Author> authors = new HashSet<>();

        String query = "SELECT * FROM authors";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();

            try (ResultSet rst = statement.getResultSet()) {
                while (rst.next()) {
                    Author author = new Author(rst.getString(2), rst.getString(3));
                    author.setId(rst.getInt(1));
                    author.setCreatedAt(rst.getTimestamp(4).toLocalDateTime());

                    authors.add(author);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        return Collections.unmodifiableSet(authors);
    }
}

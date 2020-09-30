package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Book;

import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookDao {

    private Connection connection;
    private AuthorDao authorDao;
    private CategoryDao categoryDao;

    public BookDao(Connection connection) {
        this.connection = connection;
        this.authorDao = new AuthorDao(connection);
        this.categoryDao = new CategoryDao(connection);
    }

    public void add(Book book) {
        if (this.list().contains(book)) {
            throw new IllegalArgumentException("Esse livro já está cadastrado!");
        }

        String insert = "INSERT INTO books (title, resume, summary, number_pages, isbn, author_id, category_id, edition, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getResume());
            statement.setString(3, book.getSummary());
            statement.setInt(4, book.getNumberPages());
            statement.setString(5, book.getIsbn());
            statement.setInt(6, book.getAuthor().getId());
            statement.setInt(7, book.getCategory().getId());
            statement.setInt(8, book.getEdition());
            statement.setDouble(9, book.getPrice());

            if (statement.executeUpdate() > 0) {
                ResultSet rst = statement.getGeneratedKeys();

                if (rst.next()) {
                    book.setId(rst.getInt(1));



                    System.out.println("Livro cadastrado com sucesso! \nDados do Livro:");
                    System.out.println(book.infoBookToString());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Book> list() {
        Set<Book> books = new HashSet<>();

        String query = "SELECT * FROM books";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();

            try (ResultSet rst = statement.getResultSet()) {
                while (rst.next()) {
                    Book book = new Book(
                            rst.getInt("id"),
                            rst.getString("title"),
                            rst.getString("resume"),
                            rst.getString("summary"),
                            rst.getInt("number_pages"),
                            rst.getString("isbn"),
                            this.authorDao.findById(rst.getInt("author_id")),
                            this.categoryDao.findById(rst.getInt("category_id")),
                            rst.getInt("edition"),
                            rst.getDouble("price"),
                            rst.getTimestamp("created_at").toLocalDateTime()
                    );

                    books.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableSet(books);
    }

    public List<Book> searchBooks(String title) {
        if (title.length() < 2) {
            throw new IllegalArgumentException("O título precisa conter pelo menos 2 caracteres!");
        }

        List<Book> foundBooks = this.list().stream()
                .filter(e -> e.getTitle().contains(title)).collect(Collectors.toList());

        if (foundBooks.size() == 0) {
            throw new IllegalArgumentException("Nenhum livro encontrado!");
        }

        return foundBooks;
    }

    public Book findById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id não foi informado corretamente!");
        }

        String query = "SELECT * FROM books Where id = ?";

        try (PreparedStatement statementQuery = this.connection.prepareStatement(query)) {
            statementQuery.setInt(1, id);
            ResultSet rst = statementQuery.executeQuery();

            if (rst.next()) {
                return new Book(
                        rst.getInt("id"),
                        rst.getString("title"),
                        rst.getString("resume"),
                        rst.getString("summary"),
                        rst.getInt("number_pages"),
                        rst.getString("isbn"),
                        this.authorDao.findById(rst.getInt("author_id")),
                        this.categoryDao.findById(rst.getInt("category_id")),
                        rst.getInt("edition"),
                        rst.getDouble("price"),
                        rst.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Erro ao buscar Livro!");
    }
}

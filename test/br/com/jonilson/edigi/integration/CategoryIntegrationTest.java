package br.com.jonilson.edigi.integration;

import br.com.jonilson.edigi.dao.CategoryDao;
import br.com.jonilson.edigi.factory.ConnectionFactory;
import br.com.jonilson.edigi.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryIntegrationTest {

    public ConnectionFactory connectionFactory = new ConnectionFactory();
    public Connection connection;

    @BeforeEach
    public void getConnection() {
        this.connection = this.connectionFactory.getConnection();
    }

    @AfterEach
    public void closeConnection() {
        String deleteAll = "DELETE FROM categories";
        try (PreparedStatement statement = this.connection.prepareStatement(deleteAll)) {
            statement.execute();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }

        this.connectionFactory.closeConnection(this.connection);
    }

    @Test
    public void shouldRegisterCategory() {
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Category category = new Category("Infraestrutura");

        categoryDao.add(category);

        assertTrue(categoryDao.list().contains(category));
    }

    @Test
    public void shouldNotRegisterCategoryWithoutName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Category(null)
        );

        assertEquals("O nome é obrigatório!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterCategoryAlreadyRegistered() {
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Category category1 = new Category("Infraestrutura");
        categoryDao.add(category1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Category category2 = new Category("Infraestrutura");
            categoryDao.add(category2);
        });

        assertEquals("Essa categoria já está cadastrado!", exception.getMessage());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWithoutId() {
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Category category = new Category("Infraestrutura");
        categoryDao.add(category);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> categoryDao.findById(null));

        assertEquals("Id não foi informado corretamente!", exception.getMessage());
    }

    @Test
    public void shouldThrowRuntimeExceptionWithIdNonexistent() {
        CategoryDao categoryDao = new CategoryDao(this.connection);

        Exception exception = assertThrows(RuntimeException.class, () -> categoryDao.findById(1));

        assertEquals("Erro ao buscar Categoria!", exception.getMessage());
    }
}

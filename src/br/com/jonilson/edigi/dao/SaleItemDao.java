package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.*;

import java.sql.*;
import java.util.*;

public class SaleItemDao {

    private Connection connection;
    private AuthorDao authorDao;
    private CategoryDao categoryDao;
    private BookDao bookDao;

    public SaleItemDao(Connection connection) {
        this.connection = connection;
        this.authorDao = new AuthorDao(connection);
        this.categoryDao = new CategoryDao(connection);
        this.bookDao = new BookDao(connection);
    }

    public void add(SaleItem saleItem) {
        String insert = "INSERT INTO sales_items (quantity, total_item, book_id, sale_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, saleItem.getQuantity());
            statement.setBigDecimal(2, saleItem.getTotal());
            statement.setInt(3, saleItem.getBook().getId());
            statement.setInt(4, saleItem.getSaleId());

            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SaleItem> listItems() {
        List<SaleItem> items = new ArrayList<>();
        Set<Book> books = this.bookDao.list();

        String query = "SELECT * FROM sales_items";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();

            try (ResultSet rst = statement.getResultSet()) {
                while (rst.next()) {
                    Integer bookId = rst.getInt("book_id");

                    Book book = books.stream().filter(b -> b.getId() == bookId).findFirst().get();

                    SaleItem item = new SaleItem(book, rst.getInt(2));
                    item.setSaleId(rst.getInt("sale_id"));

                    items.add(item);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return items;
    }
}

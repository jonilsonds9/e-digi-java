package br.com.jonilson.edigi.dao;

import br.com.jonilson.edigi.model.Sale;
import br.com.jonilson.edigi.model.SaleItem;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SaleDao {

    private Connection connection;
    private SaleItemDao saleItemDao;

    public SaleDao(Connection connection) {
        this.connection = connection;
        this.saleItemDao = new SaleItemDao(connection);
    }

    public void add(Sale sale) {
        String insert = "INSERT INTO sales VALUES ()";

        try (PreparedStatement statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

            if (statement.executeUpdate() > 0) {

                ResultSet rst = statement.getGeneratedKeys();
                if (rst.next()) {
                    sale.setId(rst.getInt(1));

                    for (SaleItem item : sale.getItems()) {
                        item.setSaleId(rst.getInt(1));

                        this.saleItemDao.add(item);
                    }

                    Set<Sale> sales = this.list();
                    sale.setCreatedAt(sales.stream().filter(s -> s.getId() == sale.getId()).findFirst().get().getCreatedAt());

                    System.out.println("Venda realizada com sucesso! \nDetalhes da venda:");
                    System.out.println(sale.infoSaleToString());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<Sale> list() {
        Set<Sale> sales = new HashSet<>();

        List<SaleItem> allItems = this.saleItemDao.listItems();

        String query = "SELECT * FROM sales";

        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.execute();

            try (ResultSet rst = statement.getResultSet()) {
                while (rst.next()) {
                    Integer saleId = rst.getInt("id");

                    List<SaleItem> items = allItems.stream()
                            .filter(item -> item.getSaleId() == saleId)
                            .collect(Collectors.toList());

                    Sale sale = new Sale(rst.getInt("id"),
                            items.get(0),
                            rst.getTimestamp("created_at").toLocalDateTime());

                    for (SaleItem item : items) {
                        if (!sale.getItems().contains(item))
                            sale.addItem(item);
                    }

                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Collections.unmodifiableSet(sales);
    }
}

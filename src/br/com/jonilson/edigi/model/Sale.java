package br.com.jonilson.edigi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sale {
    private Integer id;
    private List<SaleItem> items = new ArrayList<>();
    private LocalDateTime createdAt;

    public Sale(SaleItem item) {
        this.addItem(item);
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addItem(SaleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item da venda não informado corretamente!");
        }
        this.items.add(item);
    }

    public List<SaleItem> getItems() {
        return this.items;
    }

    public BigDecimal getTotal() {
        return this.items.stream().map(SaleItem::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String infoSaleToString() {
        return "Sale { \n" +
                "items= " + this.items + ", \n" +
                "total= " + this.getTotal() + "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return items.equals(sale.items) &&
                createdAt.equals(sale.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, createdAt);
    }
}

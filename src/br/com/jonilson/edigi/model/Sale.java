package br.com.jonilson.edigi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private List<SaleItem> items = new ArrayList<>();
    private LocalDateTime createdAt;

    public Sale(SaleItem item) {
        this.addItem(item);
        this.createdAt = LocalDateTime.now();
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

    public String infoSaleToString() {
        return "Sale { \n" +
                "items= " + this.items + ", \n" +
                "total= " + this.getTotal() + "\n}";
    }
}

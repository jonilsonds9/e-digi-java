package br.com.jonilson.edigi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Sale {
    private List<SaleItem> items = new ArrayList<>();
    private BigDecimal total = new BigDecimal("0.0");
    private LocalDateTime createdAt;

    public Sale(SaleItem item) {
        this.setItem(item);
        this.createdAt = LocalDateTime.now();
    }

    public void setItem(SaleItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item da venda não informado corretamente!");
        }
        this.items.add(item);
        this.setTotal(item.getTotal());
    }

    public void setTotal(BigDecimal total) {
        this.total = this.total.add(total);
    }

    public String infoSaleToString() {
        return "Sale { \n" +
                "items= " + this.items + ", \n" +
                "total= " + total + "\n}";
    }
}

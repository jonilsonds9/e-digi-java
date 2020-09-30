package br.com.jonilson.edigi.model;

import java.math.BigDecimal;
import java.util.Objects;

public class SaleItem {

    private Book book;
    private int quantity;
    private Integer saleId;

    public SaleItem(Book book, int quantity) {
        this.setBook(book);
        this.setQuantity(quantity);
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Livro não informado corretamente!");
        }
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade precisa ser pelo menos 1!");
        }

        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return new BigDecimal(Integer.toString(this.quantity)).multiply(new BigDecimal(Double.toString(this.book.getPrice())));
    }

    @Override
    public String toString() {
        return "SaleItem { " +
                "\nbook { " + this.book.infoBookToString() +
                "}, \nquantityItem= " + this.quantity +
                ", \ntotalItem= " + this.getTotal() +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return quantity == saleItem.quantity &&
                book.equals(saleItem.book) &&
                saleId.equals(saleItem.saleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book, quantity, saleId);
    }
}

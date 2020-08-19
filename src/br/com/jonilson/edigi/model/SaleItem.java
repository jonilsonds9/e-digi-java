package br.com.jonilson.edigi.model;

import java.math.BigDecimal;

public class SaleItem {

    private Book book;
    private int quantity;
    private BigDecimal total;

    public SaleItem(Book book, int quantity) {
        this.setBook(book);
        this.setQuantity(quantity);
        this.setTotal(quantity, book.getPrice());
    }

    public void setBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Livro não informado corretamente!");
        }
        this.book = book;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("A quantidade precisa ser pelo menos 1!");
        }

        this.quantity = quantity;
    }

    public void setTotal(int quantity, double price) {
        this.total = new BigDecimal(Integer.toString(quantity)).multiply(new BigDecimal(Double.toString(price)));
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    @Override
    public String toString() {
        return "SaleItem { " +
                "\nbook { " + this.book.infoBookToString() +
                "}, \nquantity= " + quantity +
                ", \ntotal= " + total +
                "\n}";
    }
}

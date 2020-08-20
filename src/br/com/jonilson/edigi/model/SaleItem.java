package br.com.jonilson.edigi.model;

import java.math.BigDecimal;

public class SaleItem {

    private Book book;
    private int quantity;

    public SaleItem(Book book, int quantity) {
        this.setBook(book);
        this.setQuantity(quantity);
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
}

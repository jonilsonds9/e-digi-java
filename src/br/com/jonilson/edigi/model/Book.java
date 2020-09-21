package br.com.jonilson.edigi.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Book {

    private Integer id;
    private String title;
    private String resume;
    private String summary;
    private int numberPages;
    private String isbn;
    private Author author;
    private Category category;
    private int edition;
    private double price;
    private LocalDateTime createdAt;

    public Book(String title, String resume, String summary, int numberPages, String isbn,
                Author author, Category category, int edition, double price) {

        this.setTitle(title);
        this.setResume(resume);
        this.setSummary(summary);
        this.setNumberPages(numberPages);
        this.setIsbn(isbn);
        this.setAuthor(author);
        this.setCategory(category);
        this.setEdition(edition);
        this.setPrice(price);
        this.createdAt = LocalDateTime.now();
    }

    public Book(Integer id, String title, String resume, String summary, int numberPages,
                String isbn, Author author, Category category, int edition, double price,
                LocalDateTime createdAt) {
        this.setId(id);
        this.setTitle(title);
        this.setResume(resume);
        this.setSummary(summary);
        this.setNumberPages(numberPages);
        this.setIsbn(isbn);
        this.setAuthor(author);
        this.setCategory(category);
        this.setEdition(edition);
        this.setPrice(price);
        this.setCreatedAt(createdAt);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    private void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("O título é obrigatório!");
        }
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    private void setResume(String resume) {
        if (resume == null || resume.trim().isEmpty() || resume.length() < 500) {
            throw new IllegalArgumentException("O resumo é obrigatório e precisa ter pelo menos 500 caracteres!");
        }
        this.resume = resume;
    }

    public String getSummary() {
        return summary;
    }

    private void setSummary(String summary) {
        if (summary == null || summary.trim().isEmpty()) {
            throw new IllegalArgumentException("O sumário é obrigatório e não pode ser vazio!");
        }
        this.summary = summary;
    }

    public int getNumberPages() {
        return numberPages;
    }

    private void setNumberPages(int numberPages) {
        if (numberPages <= 0) {
            throw new IllegalArgumentException("O número de página precisa ser maior que zero!");
        }
        this.numberPages = numberPages;
    }

    public String getIsbn() {
        return isbn;
    }

    private void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty() || !(this.isIsbnValid(isbn))) {
            throw new IllegalArgumentException("O ISBN do livro é obrigatório e precisa no formato '978-xx-xxxxx-xx-x'");
        }
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return this.author;
    }

    private void setAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Autor não informado corretamente!");
        }
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    private void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Categoria não informada corretamente!");
        }
        this.category = category;
    }

    public int getEdition() {
        return edition;
    }

    private void setEdition(int edition) {
        if (edition <= 0) {
            throw new IllegalArgumentException("A edição precisa ser maior que zero!");
        }
        this.edition = edition;
    }

    public double getPrice() {
        return this.price;
    }

    private void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("O preço deve ser um número positivo!");
        }
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private boolean isIsbnValid(String isbn) {
        Pattern pattern = Pattern.compile("978-\\d{2}-\\d{5}-\\d{2}-\\d", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(isbn);

        return matcher.matches();
    }

    public String infoBookToString() {
        return "Autor: " + this.author.getName() + "\nLivro: " + this.title + "\nCadastrado em: " + this.createdAt + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title) &&
                isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.isbn);
    }
}

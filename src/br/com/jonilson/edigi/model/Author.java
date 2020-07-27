package br.com.jonilson.edigi.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Author {
    private String name;
    private String email;
    private LocalDateTime createdAt;

    public Author(String name, String email) {
        this.setName(name);
        this.setEmail(email);
        this.createdAt = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome é obrigatório!");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        if (email == null || email.trim().isEmpty() || !validateEmail(email)) {
            throw new IllegalArgumentException("Email informado para o autor é inválido!");
        }

        this.email = email;
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    @Override
    public String toString() {
        return "\nAuthor{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", created_at=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return email.equals(author.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

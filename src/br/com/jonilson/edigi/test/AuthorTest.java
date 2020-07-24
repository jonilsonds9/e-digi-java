package br.com.jonilson.edigi.test;

import br.com.jonilson.edigi.dao.AuthorDao;
import br.com.jonilson.edigi.model.Author;

public class AuthorTest {
    public static void main(String[] args) {

        try {
            AuthorDao authorDao = new AuthorDao();

            Author author = new Author("ana", "ana@gmail.com");
            Author author1 = new Author("Alice", "alice@gmail.com");

            authorDao.add(author);
            authorDao.add(author1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

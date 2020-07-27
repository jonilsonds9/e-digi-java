package br.com.jonilson.edigi.test;

import br.com.jonilson.edigi.dao.CategoryDao;
import br.com.jonilson.edigi.model.Category;

public class CategoryTest {
    public static void main(String[] args) {
        try {
            CategoryDao categoryDao = new CategoryDao();

            Category category1 = new Category("Programação");
            Category category2 = new Category("Infraestrutura");

            categoryDao.add(category1);
            categoryDao.add(category2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package br.com.jonilson.edigi;

import br.com.jonilson.edigi.dao.CategoryDao;
import br.com.jonilson.edigi.model.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CategoryTest {

    @Test
    public void shouldRegisterCategory() {
        CategoryDao categoryDao = new CategoryDao();

        Category category = new Category("Infraestrutura");

        categoryDao.add(category);

        assertEquals(1, categoryDao.list().size());
        assertEquals(category.getName(), categoryDao.list().iterator().next().getName());
    }

    @Test
    public void shouldNotRegisterCategoryWithoutName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new Category(null)
        );

        assertEquals("O nome é obrigatório!", exception.getMessage());
    }

    @Test
    public void shouldNotRegisterCategoryAlreadyRegistered() {
        CategoryDao categoryDao = new CategoryDao();

        Category category1 = new Category("Infraestrutura");
        categoryDao.add(category1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Category category2 = new Category("Infraestrutura");
            categoryDao.add(category2);
        });

        assertEquals("Essa categoria já está cadastrado!", exception.getMessage());
    }

}

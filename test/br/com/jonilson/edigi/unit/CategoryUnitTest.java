package br.com.jonilson.edigi.unit;

import br.com.jonilson.edigi.dao.CategoryDao;
import br.com.jonilson.edigi.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;

public class CategoryUnitTest {

    @Test
    public void shouldRegisterCategory() {
        CategoryDao categoryDao = Mockito.mock(CategoryDao.class);

        Category category = new Category("Infraestrutura");

        Mockito.when(categoryDao.list()).thenReturn(new HashSet<>(Arrays.asList(category)));

        categoryDao.add(category);

        assertTrue(categoryDao.list().contains(category));
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
        CategoryDao categoryDao = Mockito.mock(CategoryDao.class);

        Mockito.doAnswer(new Answer() {
            private Category categoryReceived = null;

            public Object answer(InvocationOnMock invocation) {
                if (categoryReceived == null) {
                    categoryReceived = (Category) invocation.getArguments()[0];
                } else if (categoryReceived.equals(invocation.getArguments()[0])) {
                    throw new IllegalArgumentException("Essa categoria já está cadastrado!");
                }
                return null;
            }
        }).when(categoryDao).add(isA(Category.class));

        Category category1 = new Category("Infraestrutura");
        categoryDao.add(category1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Category category2 = new Category("Infraestrutura");
            categoryDao.add(category2);
        });

        assertEquals("Essa categoria já está cadastrado!", exception.getMessage());
    }

}
//        doAnswer(category -> {
//            throw new IllegalArgumentException("Essa categoria já está cadastrado!");
//        }).when(categoryDao).add(isA(Category.class));

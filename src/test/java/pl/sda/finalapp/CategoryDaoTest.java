package pl.sda.finalapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.sda.finalapp.categories.Category;
import pl.sda.finalapp.categories.CategoryDao;

import java.util.List;

class CategoryDaoTest {

    @Test
    void shouldProperlyPopulateCategoriesList() {
        //given
        int categoryQty = 8;
        int fifthCategoryParentId = 4;
        String nameOfSixthCategory = "Klasa druga";
        //when
        List<Category> categoryList = CategoryDao.getInstance().getCategoryList();
        Category categoryFifth = findCategory(categoryList, 5);
        Category categorySixth = findCategory(categoryList, 6);
        //then
        Assertions.assertEquals(categoryList.size(), categoryQty);
        Assertions.assertEquals(categoryFifth.getParentId(), fifthCategoryParentId);
        Assertions.assertEquals(categorySixth.getCategoryName(), nameOfSixthCategory);

    }


    private Category findCategory(List<Category> categoryList, int categoryId) {
        return categoryList.stream()
                .filter(c -> c.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nie znaleziono kategorii o id " + categoryId));
    }
}

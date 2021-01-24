package pl.sda.finalapp.categories;


import com.google.common.io.Resources;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CategoryDao {//Dao- Data Access Object

    private CategoryDao() {
    }

    private static CategoryDao INSTANCE;
    private final List<Category> categoryList = populateCategories();

    public static CategoryDao getInstance() {
        if (INSTANCE == null) {
            synchronized (CategoryDao.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CategoryDao();
                }
            }
        }
        return INSTANCE;
    }

    private List<Category> populateCategories() {
        List<String> lines = readCategoriesFromFile();
        List<Category> categories = lines.stream()
                .map(x -> Category.createFromText(x))
                .collect(Collectors.toList());

        Map<Integer, List<Category>> categoriesMap = populateMap(categories);

        populateParentId(1, categoriesMap);
        return categories;
    }

    private List<String> readCategoriesFromFile() {
        try {
            return Resources.readLines(Resources.getResource("categories.txt"), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void populateParentId(int depth, Map<Integer, List<Category>> categoriesMap) {
        List<Category> childrenList = categoriesMap.get(depth);
        if (childrenList == null) {
            return;
        }
        List<Category> parentList = categoriesMap.get(depth - 1);
        for (Category child : childrenList) {
            Integer parentId = parentList.stream()
                    .map(c -> c.getId())
                    .filter(id -> id < child.getId())
                    .sorted((a, b) -> b - a)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException());
            child.applyParentId(parentId);
        }
        populateParentId(depth + 1, categoriesMap);
    }

    private Map<Integer, List<Category>> populateMap(List<Category> categories) {
        Map<Integer, List<Category>> categoriesMap = new HashMap<>();
        for (Category category : categories) {
            if (categoriesMap.containsKey(category.getDepth())) {
                List<Category> innerList = categoriesMap.get(category.getDepth());
                innerList.add(category);
            } else {
                List<Category> innerList = new ArrayList<>();
                innerList.add(category);
                categoriesMap.put(category.getDepth(), innerList);
            }
        }
        return categoriesMap;
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void addCategory(String categoryName, Integer parentId) {
        if(thereIsNoCategoryWithId(parentId)) {
            throw new RuntimeException();
        }
        categoryList.add(Category.createFromNameAndParentId(categoryName, parentId));
    }

    private boolean thereIsNoCategoryWithId(Integer parentId) {
        return categoryList.stream()
                .noneMatch(c -> c.getId().equals(parentId));
    }

    public Optional<Category> findCategoryById(Integer id){
        return getCategoryList().stream()
                .filter(c -> id.equals(c.getId()))
                .findFirst();

    }
}

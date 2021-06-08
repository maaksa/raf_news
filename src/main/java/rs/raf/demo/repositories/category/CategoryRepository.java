package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;

import java.util.List;

public interface CategoryRepository {

    public List<Category> allCategory();

    public Category findCategory(String name);

    public Category addCategory(Category category);

    public String deleteCategory(String name);

    public Category updateCategory(Category category, String name);

}

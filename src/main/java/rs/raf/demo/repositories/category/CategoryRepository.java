package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;

import java.util.List;

public interface CategoryRepository {

    public List<Category> allCategory();

    public Category addCategory(Category category);

    public void deleteCategory(String name);

}

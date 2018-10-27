package project.services;


import org.springframework.stereotype.Service;
import project.model.Category;

import java.util.List;


public interface CategoryService {

    public Category getCategory(int id);

    public List<Category> getCategories();

    public Category addCategory(Category cat);

    public void deleteCategory(int id);
}

package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Category;
import project.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategory(int id) {
        return this.categoryRepository.findById(id);
    }

    public Category addCategory(Category cat){

        this.categoryRepository.save(cat);
        return cat;
    }

    public void deleteCategory(int id){

        this.categoryRepository.deleteById(id);
    }

    public List<Category> getCategories(){
        return this.categoryRepository.findAll();
    }

}

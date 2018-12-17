package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;
import project.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public boolean checkIfExists(String name,String manu_name){

        if(this.productRepository.checkIfExists(name,manu_name).isEmpty()){
            return false;
        }
        else return true;

    }

    @Override
    public List<Product> getAllProducts(Pageable pageable)
    {
        return this.productRepository.findAllProducts(pageable);
    }

    @Override
    public int getNumberOfProducts()
    {
        return this.productRepository.getNumberOfProducts();
    }

    @Override
    public int getNumberOfPages(int numberOfProducts){

        int numberOfPages = 0;
        if(numberOfProducts%9==0){
            numberOfPages = numberOfProducts/9;
        }
        else{
            numberOfPages = (int)(numberOfProducts/9)+1;
        }
        return numberOfPages;
    }



    @Override
    public int getNumberOfProductsInCategory(int cat_id){
        return this.productRepository.getNumberOfProductsInCategory(cat_id);
    }

    @Override
    public int getNumberOfProductsByManufacturer(String manufacturer_name){
        return this.productRepository.getNumberOfProductsByManufacturer(manufacturer_name);
    }

    @Override
    public Product getProductsById(int id){
        return this.productRepository.findById(id);
    }

    @Override
    public List<Product> getProductsByName(String name, Pageable pageable){
        return this.productRepository.findByName(name,pageable);
    }

    @Override
    public List<Product> getProductsByNameInCategory(int cat_id, String name, Pageable pageable) {
        return this.productRepository.findByNameInCategory(cat_id,name,pageable);
    }

    @Override
    public int getNumberOfProductsWithName(String name){
        return this.productRepository.getNumberOfProductsWithName(name);
    }



    public int getNumberOfProductsWithNameInCategory(int cat_id,String name){
        return this.productRepository.getNumberOfProductsWithNameInCategory(cat_id,name);
    }


    @Override
    public List<Product> getAllProductsByManufacturer(String manufacturer_name,Pageable pageable){
        return this.productRepository.findByManufacturer(manufacturer_name,pageable);
    }

    @Override
    public List<Product> getProductsOrderByNameByManufacturerAsc(String manufacturer_name,Pageable pageable){
        return this.productRepository.findByManufacturerOrderedByNameAsc(manufacturer_name,pageable);
    }

    @Override
    public List<Product> getProductsOrderByNameByManufacturerDesc(String manufacturer_name,Pageable pageable){
        return this.productRepository.findByManufacturerOrderedByNameDesc(manufacturer_name,pageable);
    }

    @Override
    public List<Product> getProductsOrderByRaitingByManufacturerAsc(String manufacturer_name,Pageable pageable){
        return this.productRepository.findByManufacturerOrderedByRaitingAsc(manufacturer_name,pageable);
    }

    @Override
    public List<Product> getProductsOrderByRaitingByManufacturerDesc(String manufacturer_name,Pageable pageable){
        return this.productRepository.findByManufacturerOrderedByRaitingDesc(manufacturer_name,pageable);
    }



    @Override
    public List<Product> getAllProductsInCategory(int cat_id,Pageable pageable){
        return this.productRepository.findByCat(cat_id,pageable);
    }

    @Override
    public List<Product> getProductsOrderByNameInCategoryAsc(int cat_id,Pageable pageable){
        return this.productRepository.findByCatOrderedByNameAsc(cat_id,pageable);
    }

    @Override
    public List<Product> getProductsOrderByRaitingInCategoryAsc(int cat_id,Pageable pageable){
        return this.productRepository.findByCatOrderedByRaitingAsc(cat_id,pageable);
    }

    @Override
    public List<Product> getProductsOrderByNameInCategoryDesc(int cat_id,Pageable pageable){
        return this.productRepository.findByCatOrderedByNameDesc(cat_id,pageable);
    }

    @Override
    public List<Product> getProductsOrderByRaitingInCategoryDesc(int cat_id,Pageable pageable){
        return this.productRepository.findByCatOrderedByRaitingDesc(cat_id,pageable);
    }


    @Override
    public List<Product> getProductsOrderByRaitingAsc(Pageable pageable){
        return this.productRepository.findProductOrderByRaitingAsc(pageable);
    }

    @Override
    public List<Product> getProductsOrderByRaitingDesc(Pageable pageable){
        return this.productRepository.findProductOrderByRaitingDesc(pageable);
    }

    @Override
    public List<Product> getProductsOrderByNameAsc(Pageable pageable){
        return this.productRepository.findProductOrderByNameAsc(pageable);
    }

    @Override
    public List<Product> getProductsOrderByNameDesc(Pageable pageable){
        return this.productRepository.findProductOrderByNameDesc(pageable);
    }



    @Override
    public Product addProduct(Product product) {

        if(checkIfExists(product.getName(),product.getManufacturer_name())){
            return null;
        }
        else return this.productRepository.save(product);

    }

    @Override
    public void deleteProduct(int productID) {

        this.productRepository.deleteById(productID);
    }


}

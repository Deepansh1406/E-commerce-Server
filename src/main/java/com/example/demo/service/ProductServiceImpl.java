package com.example.demo.service;

import com.example.demo.Exception.ProductException;
import com.example.demo.Request.CreateProductRequest;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements  ProductService{


    private ProductRepository productRepository ;
    private UserService userService;
    private CategoryRepository categoryRepository;

  public  ProductServiceImpl(  ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository){
      this.productRepository = productRepository;
      this.userService = userService ;
      this.categoryRepository = categoryRepository;


  }

//    public Product createProduct(CreateProductRequest req) {
//        // FOR FIRST LEVEL-----
//        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
//        if (topLevel == null) {
//            topLevel = new Category();
//            topLevel.setName(req.getTopLevelCategory());
//            topLevel.setLevel(1);
//            topLevel = categoryRepository.save(topLevel);
//        }
//
//        // SECOND LEVEL=--------
//        Category secondLevel = null;
//        if (topLevel != null) {
//            secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
//            if (secondLevel == null) {
//                secondLevel = new Category();
//                secondLevel.setName(req.getSecondLevelCategory());
//                secondLevel.setCategory(topLevel);
//                secondLevel.setLevel(2);
//                secondLevel = categoryRepository.save(secondLevel);
//            }
//        }
//
//        // Third LEVEL ______-----------
//        Category thirdLevel = null;
//        if (secondLevel != null) {
//            thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
//            if (thirdLevel == null) {
//                thirdLevel = new Category();
//                thirdLevel.setName(req.getThirdLevelCategory());
//                thirdLevel.setCategory(secondLevel);
//                thirdLevel.setLevel(3);
//                thirdLevel = categoryRepository.save(thirdLevel);
//            }
//        }
//
//        Product product = new Product();
//        product.setTitle(req.getTitle());
//        product.setColor(req.getColor());
//        product.setDescription(req.getDescription());
//        product.setDiscountPrice(req.getDiscountedPrice());
//        product.setDiscountPresent(req.getDiscountPresent());
//        product.setImageUrl(req.getImageUrl());
//        product.setBrand(req.getBrand());
//        product.setPrice(req.getPrice());
//        product.setSizes(req.getSize());
//        product.setQuantity(req.getQuantity());
//
//        // Check if thirdLevel is not null before setting it in the product
//        if (thirdLevel != null) {
//            product.setCategory(thirdLevel);
//        }
//
//        product.setCreatedAt(LocalDateTime.now());
//
//        Product savedProduct = productRepository.save(product);
//        return savedProduct;
//    }
@Override
    public Product createProduct(CreateProductRequest req){
      Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());
      if(topLevel==null){
          Category topLevelCategory= new Category();
          topLevelCategory.setName(req.getTopLevelCategory());
          topLevelCategory.setLevel(1);
          topLevel  = categoryRepository.save(topLevelCategory);

      }
    Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevel.getName());
    if(secondLevel==null){
        Category secondLevelCategory= new Category();
        secondLevelCategory.setName(req.getSecondLevelCategory());
        secondLevelCategory.setCategory(topLevel);
        secondLevelCategory.setLevel(2);
        secondLevel  = categoryRepository.save(secondLevelCategory);

    }
    Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());
    if(thirdLevel==null){
        Category thirdLevelCategory= new Category();
        thirdLevelCategory.setName(req.getThirdLevelCategory());
        thirdLevelCategory.setCategory(secondLevel);
        thirdLevelCategory.setLevel(3);
        thirdLevel  = categoryRepository.save(thirdLevelCategory);

    }

    Product product = new Product();
    product.setTitle(req.getTitle());
    product.setColor(req.getColor());
    product.setDescription(req.getDescription());
    product.setDiscountPrice(req.getDiscountedPrice());
    product.setDiscountPresent(req.getDiscountPresent());
    product.setImageUrl(req.getImageUrl());
    product.setBrand(req.getBrand());
    product.setPrice(req.getPrice());
    product.setSizes(req.getSize());
    product.setQuantity(req.getQuantity());
    product.setCategory(thirdLevel);
    product.setCreatedAt(LocalDateTime.now());

    Product savedProduct = productRepository.save(product);
    return  savedProduct;

  }


    @Override
    public String deleteProduct(Long productId) throws ProductException {

      Product product = findProductById(productId);
      product.getSizes().clear();
      productRepository.delete(product);
        return "Product Deleted Successfully!!";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);


        if(req.getQuantity() != 0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);

    }

    @Override
    public Product findProductById(Long id) throws ProductException {

        Optional<Product> opt = productRepository.findById(id);
        if(opt.isPresent()){
            return  opt.get();

        }
        throw new ProductException("Product not found with id "+ id);

    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        System.out.println("Colors : " + colors);
        System.out.println("Page Number: " + pageNumber);
        System.out.println("Page Size: " + pageSize);
        System.out.println("Category: " + category);
        System.out.println("Min Price: " + minPrice);
        System.out.println("Max Price: " + maxPrice);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        System.out.println("Pageable::"+ pageable);
        System.out.println("PageNUmber::"+pageNumber);
        System.out.println("PageSIze::"+ pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
        System.out.println("Products::"+ products);
        if (colors != null && !colors.isEmpty()) {
            products = products.stream()
                    .filter(p -> colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if(stock != null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity() > 0).collect(Collectors.toList());
            }
            else  if (stock.equals("out_of_stock")){
                products = products.stream().filter(p ->p.getQuantity() <1).collect(Collectors.toList());
            }
        }
        System.out.println("Products::"+products);


        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

// Ensure startIndex is less than endIndex to avoid IllegalArgumentException
        if (startIndex < endIndex) {
            List<Product> pageContent = products.subList(startIndex, endIndex);
            System.out.println("Number of Filtered Products: " + products.size());

            Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
            System.out.println("Product size::" + products.size());

            System.out.println("Filtered Products::" + filteredProducts);

            return filteredProducts;
        } else {
            // Handle the case where startIndex is greater than or equal to endIndex
            // This could happen when the requested page is beyond the available data
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

    }



//    testing(************)




//    ***********FOR finding the all products ********



    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

}

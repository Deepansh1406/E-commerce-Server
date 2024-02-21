package com.example.demo.Controller;
import com.example.demo.Exception.ProductException;
import com.example.demo.Request.CreateProductRequest;
import com.example.demo.model.Product;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req){
        Product product = productService.createProduct(req);
        return  new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public  ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException{
        productService.deleteProduct(productId);
        ApiResponse  res= new ApiResponse();
        res.setMessage("Product Deleted Successfully!!");
        res.setStatus(true);
        return  new ResponseEntity<>(res, HttpStatus.OK);

    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> products = productService.findAllProducts();
        return  new ResponseEntity<>(products, HttpStatus.OK);

    }
    @PutMapping("/{productId}/update")
    public  ResponseEntity<Product> updateProduct(@RequestBody Product req , @PathVariable Long   productId) throws  ProductException{
        Product product = productService.updateProduct(productId, req);
        return  new ResponseEntity<Product>(product,HttpStatus.CREATED);
    }
    @PostMapping("/creates")
    public  ResponseEntity<ApiResponse>createMultipleProduct(@RequestBody CreateProductRequest[] req){
        for (CreateProductRequest productRequest:req){
            productService.createProduct(productRequest);

        }
        ApiResponse res= new ApiResponse();
        res.setMessage("Product Created Successfully!!");
        res.setStatus(true);
        return  new ResponseEntity<>(res, HttpStatus.CREATED);

    }
}

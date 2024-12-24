package mkm.ecom.backend.controller;

import jakarta.persistence.EntityNotFoundException;
import mkm.ecom.backend.model.Product;
import mkm.ecom.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/e-com/api/v1/")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        System.out.println("Received from UI..."+product.getCreatedDateTime());
        System.out.println("Received from UI..."+product.getProduct());
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping("/product/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/product")
    public ResponseEntity<Page<Product>> getPaginatedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.getPaginatedProducts(pageable));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);
        if(product==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/{id}")
    public  ResponseEntity<Product> editProduct(@RequestBody Product product, @PathVariable Long id){

        Product updatedProduct = productService.editProduct(product,id);

        if(updatedProduct==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Product with ID " + id + " deleted successfully");
            return ResponseEntity.ok(response); // Return JSON response
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }


    /*
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product with ID "+id+" deleted successfully", HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        */


        /* Alterenate Delete Method
        Product productObj = productService.getProductById(id);
        String deleteMsg=null;
        if(productObj!=null){
            productService.deleteProduct(productObj.getId());
            deleteMsg = "Product Deleted Successfully for ID "+id;
        }
        return deleteMsg;
        */

    }
}

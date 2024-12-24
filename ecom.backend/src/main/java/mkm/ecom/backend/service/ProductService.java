package mkm.ecom.backend.service;

import mkm.ecom.backend.exception.ResourceNotFoundException;
import mkm.ecom.backend.model.Product;
import mkm.ecom.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product createProduct(Product product){
        //product.setCreatedDateTime(LocalDateTime.now());
        //product.setUpdatedDateTime(LocalDateTime.now());
        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Page<Product> getPaginatedProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist with id :" + id));
    }
    public Product editProduct(Product product, Long id){
        Product existingProduct = getProductById(id);
        existingProduct.setProduct(product.getProduct());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setModel(product.getModel());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCreatedDateTime(product.getCreatedDateTime());
        existingProduct.setUpdatedDateTime(product.getUpdatedDateTime());
        Product updatedProduct = productRepository.save(existingProduct);
        return existingProduct;
    }

    public String deleteProduct(Long id){
        Product product = getProductById(id);
        productRepository.delete(product);
        return "Product Deleted Successfully..."+id;
    }
}

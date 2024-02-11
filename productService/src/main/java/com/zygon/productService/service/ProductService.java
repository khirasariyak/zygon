package com.zygon.productService.service;

import com.zygon.productService.model.ProductRequest;
import com.zygon.productService.model.ProductResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {
  long addProduct(ProductRequest productRequest);

  ProductResponse getProductById(long productId);

  void reduceQuantity(long productId, long quantity);
}

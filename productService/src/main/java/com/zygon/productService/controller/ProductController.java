package com.zygon.productService.controller;

import com.zygon.productService.model.ProductRequest;
import com.zygon.productService.model.ProductResponse;
import com.zygon.productService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping
  public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest) {
    long productId = productService.addProduct(productRequest);
    return new ResponseEntity<>(productId, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId) {
    ProductResponse productResponse = productService.getProductById(productId);
    return new ResponseEntity<>(productResponse, HttpStatus.OK);
  }

  @PutMapping("/{id}/reduceQuantity")
  public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity) {
    productService.reduceQuantity(productId, quantity);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
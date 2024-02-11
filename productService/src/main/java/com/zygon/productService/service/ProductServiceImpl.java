package com.zygon.productService.service;

import com.zygon.productService.entity.Product;
import com.zygon.productService.exception.ProductServiceCustomException;
import com.zygon.productService.model.ProductRequest;
import com.zygon.productService.model.ProductResponse;
import com.zygon.productService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.zygon.productService.common.ExceptionMessageConstants.PRODUCT_NOT_FOUND;
import static com.zygon.productService.common.ExceptionMessageConstants.PRODUCT_INSUFFICIENT_QUANTITY;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public long addProduct(ProductRequest productRequest) {
    log.info("Adding a product...");

    Product product = Product.builder()
      .productName(productRequest.getProductName())
      .price(productRequest.getPrice())
      .quantity(productRequest.getQuantity())
      .build();

    productRepository.save(product);
    log.info("Product got created!");
    return product.getProductId();
  }

  @Override
  public ProductResponse getProductById(long productId) {
    log.info("Getting the product for productId={}", productId);
    Product product = productRepository.findById(productId)
      .orElseThrow(
        () -> new ProductServiceCustomException(
          PRODUCT_NOT_FOUND,
          NOT_FOUND
        )
      );

    ProductResponse productResponse = new ProductResponse();
    copyProperties(product, productResponse);
    return productResponse;
  }

  @Override
  public void reduceQuantity(long productId, long quantity) {

    Product product = productRepository.findById(productId)
      .orElseThrow(
        () -> new ProductServiceCustomException(
          PRODUCT_NOT_FOUND,
          NOT_FOUND
        )
      );

    long productQuantity = product.getQuantity();

    if (quantity > productQuantity) {
      throw new ProductServiceCustomException(PRODUCT_INSUFFICIENT_QUANTITY, BAD_REQUEST);
    }

    log.info("Reducing the quantity by {} for the productId={}", quantity, productId);
    product.setQuantity(productQuantity - quantity);
    productRepository.save(product);
    log.info("Successfully reduced the quantity for productId={}", productId);
  }

}

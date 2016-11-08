package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public interface ProductDao {
    int addProduct(Product product);

    List<Product> getProducts();

    Optional<Product> getProductWithMaxPrice();
    Optional<Product> getProductWithMinPrice();
}

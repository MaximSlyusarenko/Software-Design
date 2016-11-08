package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class ProductInMemoryDao implements ProductDao {

    private final AtomicInteger lastId = new AtomicInteger(0);
    private final List<Product> products = new CopyOnWriteArrayList<>();

    public int addProduct(Product product) {
        int id = lastId.incrementAndGet();
        product.setId(id);
        products.add(product);
        return id;
    }

    public List<Product> getProducts() {
        return new ArrayList(products);
    }

    public Optional<Product> getProductWithMaxPrice() {
        return products.stream().max(Product.PRICE_COMPARATOR);
    }

    public Optional<Product> getProductWithMinPrice() {
        return products.stream().min(Product.PRICE_COMPARATOR);
    }
}

package ru.ifmo.ctddev.slyusarenko.sd.lab5.logic;

import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.ProductDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Product;

import java.util.*;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public abstract class DataFilter {

    private static Map<String, DataFilter> filters = createFiltersMap();

    static HashMap<String, DataFilter> createFiltersMap() {
        HashMap<String, DataFilter> filters = new HashMap<>();
        filters.put("all", new AllFilter());
        filters.put("max", new MaxFilter());
        filters.put("min", new MinFilter());
        return filters;
    }

    public abstract List<Product> filter(ProductDao productDao);

    private static class AllFilter extends DataFilter {
        public List<Product> filter(ProductDao productDao) {
            return productDao.getProducts();
        }
    };

    private static class MaxFilter extends DataFilter {
        public List<Product> filter(ProductDao productDao) {
            return productDao.getProductWithMaxPrice()
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
    };

    private static class MinFilter extends DataFilter {
        public List<Product> filter(ProductDao productDao) {
            return productDao.getProductWithMinPrice()
                    .map(Collections::singletonList)
                    .orElse(Collections.emptyList());
        }
    };

    public static Optional<DataFilter> getFilterByName(String name) {
        return Optional.ofNullable(filters.get(name));
    }
}

package ru.ifmo.ctddev.slyusarenko.sd.lab5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.dao.ProductDao;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.logic.DataFilter;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Filter;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
@Controller
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/add-product", method = RequestMethod.POST)
    public String addQuestion(@ModelAttribute("product") Product product) {
        productDao.addProduct(product);
        return "redirect:/get-products";
    }

    @RequestMapping(value = "/get-products", method = RequestMethod.GET)
    public String getProducts(ModelMap map) {
        prepareModelMap(map, productDao.getProducts());
        return "index";
    }

    @RequestMapping(value = "/filter-products", method = RequestMethod.GET)
    public String getProducts(@RequestParam String filter, ModelMap map) {
        Optional<DataFilter> dataFilter = DataFilter.getFilterByName(filter);
        if (dataFilter.isPresent()) {
            prepareModelMap(map, dataFilter.get().filter(productDao));
        }

        return "index";
    }

    private void prepareModelMap(ModelMap map, List<Product> products) {
        map.addAttribute("products", products);
        map.addAttribute("product", new Product());
        map.addAttribute("filter", new Filter());
    }
}

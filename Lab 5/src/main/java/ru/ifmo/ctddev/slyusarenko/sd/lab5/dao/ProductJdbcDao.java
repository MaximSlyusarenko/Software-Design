package ru.ifmo.ctddev.slyusarenko.sd.lab5.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import ru.ifmo.ctddev.slyusarenko.sd.lab5.model.Product;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/**
 * @author Maxim Slyusarenko
 * @since 08.11.16
 */
public class ProductJdbcDao extends JdbcDaoSupport implements ProductDao {

    /*private static final String CREATE_TABLE_LISTS = "CREATE TABLE IF NOT EXISTS LISTS" +
            "(NAME TEXT PRIMARY KEY NOT NULL)";

    private static final String CREATE_TABLE_TASKS = "CREATE TABLE IF NOT EXISTS TASKS" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " LIST_NAME      TEXT    NOT NULL, " +
            " DONE           INT     NOT NULL, " +
            " FOREIGN KEY (LIST_NAME) REFERENCES LISTS(NAME))";*/

    public ProductJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
    }

    @Override
    public int addProduct(Product product) {
        String sql = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (?, ?)";
        return getJdbcTemplate().update(sql, new Object[] { product.getName(), product.getPrice()});
    }

    @Override
    public List<Product> getProducts() {
//        getJdbcTemplate().execute("DROP TABLE TASKS");
//        getJdbcTemplate().execute("DROP TABLE LISTS");
//        getJdbcTemplate().execute(CREATE_TABLE_LISTS);
//        getJdbcTemplate().execute(CREATE_TABLE_TASKS);

        String sql = "SELECT * FROM PRODUCT";
        return getProductsByRequest(sql);
    }

    @Override
    public Optional<Product> getProductWithMaxPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        return getProductsByRequest(sql).stream().findFirst();
    }

    @Override
    public Optional<Product> getProductWithMinPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        return getProductsByRequest(sql).stream().findFirst();

    }

    private List<Product> getProductsByRequest(String sql) {
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Product.class));
    }
}

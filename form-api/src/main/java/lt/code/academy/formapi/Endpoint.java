package lt.code.academy.formapi;

public class Endpoint {
    String productId = "productId";

    //path
    String PRODUCTS = "/products";
    String PRODUCT = "/{"+ productId +"}";
    String SEARCH = "/search";
    String LOGIN = "/login";
    String USERS = "/users";
}

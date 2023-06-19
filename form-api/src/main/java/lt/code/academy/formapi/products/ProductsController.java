package lt.code.academy.formapi.products;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PRODUCTS)
@OpenAPIDefinition(tags = {
        @Tag(name = "Products Controller", description = "This controller is used for actions with products")
})
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(tags = "Products Controller", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products returned successfully", content = {@Content(schema = @Schema(implementation = Product.class))})
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping(value = PRODUCT, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProduct(@PathVariable(productId) UUID id) {
        return productService.getProduct(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = PRODUCT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateProduct(@RequestBody Product product, @PathVariable(productId) UUID id) {
        product.setId(id);
        productService.updateProduct(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(PRODUCT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable(productId) UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping(value = SEARCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> search(@RequestParam String query) {
        return productService.search(query);
    }
}

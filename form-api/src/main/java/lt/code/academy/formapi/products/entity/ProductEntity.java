package lt.code.academy.formapi.products.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.formapi.products.dto.Product;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity
{
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private UUID id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 200)
    private String category;
    @Column(nullable = false, length = 500)
    private String description;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private BigDecimal price;

    public static ProductEntity convert(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice()
        );
    }
}
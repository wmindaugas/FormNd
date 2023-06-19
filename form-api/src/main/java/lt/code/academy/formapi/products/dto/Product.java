package lt.code.academy.formapi.products.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.code.academy.formapi.products.entity.ProductEntity;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product
{
    private UUID id;
    @NotBlank
    @Size(min = 5, max = 20)
    private String name;
    @NotBlank
    @Size(min = 2, max = 500, message = "{validation.product.size}")
    private String description;
    @NotBlank
    @Size(min = 2, max = 200)
    private String category;
    @NotNull
    @PositiveOrZero
    private Integer quantity;
    @Positive
    @NotNull
    private BigDecimal price;

    public static Product convert(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCategory(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }
}
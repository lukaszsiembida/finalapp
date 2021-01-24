package pl.sda.finalapp.products;

import pl.sda.finalapp.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Product extends BaseEntity {

    private String title;
    private String pictureUrl;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    private Integer categoryId;

    public Product() {

    }

    public Product(String title, String pictureUrl, BigDecimal price, ProductType productType, Integer categoryId) {
        this.title = title;
        this.pictureUrl = pictureUrl;
        this.price = price;
        this.productType = productType;
        this.categoryId = categoryId;
    }

    public ProductListDto toListDto(String categoryName) {
        return new ProductListDto(getId(),
                this.title,
                this.pictureUrl,
                this.price,
                this.productType,
                categoryName);
    }

    public ProductDto toDto() {
        return new ProductDto(getId(),
                this.title,
                this.pictureUrl,
                this.price,
                this.productType,
                this.categoryId);
    }

    public static Product fromDto(ProductDto dto){
        Product product = new Product();
        product.title = dto.getTitle();
        product.pictureUrl = dto.getPictureUrl();
        product.price = dto.getPrice();
        product.productType = dto.getProductType();
        product.categoryId = dto.getCategoryId();
        return product;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void apply(ProductDto dto) {
        this.title = dto.getTitle();
        this.pictureUrl = dto.getPictureUrl();
        this.price = dto.getPrice();
        this.productType = dto.getProductType();
        this.categoryId = dto.getCategoryId();
    }
}

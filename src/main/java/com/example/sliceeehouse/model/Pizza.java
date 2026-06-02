// package com.example.sliceeehouse.model;

// import com.example.sliceeehouse.model.enums.Category;
// import jakarta.persistence.*;
// import lombok.Data;

// @Data
// @Entity
// public class Pizza {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String name;

//     @Enumerated(EnumType.STRING)
//     private Category category;   // enum category

//     private String smallDescription;

//     @Lob
//     private String description;

//     private Integer sellingPrice;
//     private Integer discountedPrice;

//     @Lob
//     private byte[] productImage; // store image in DB

//     public Object getDiscountedPrice() {
//         throw new UnsupportedOperationException("Not supported yet.");
//     }

    
//     }



package com.example.sliceeehouse.model;

import com.example.sliceeehouse.model.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String smallDescription;

    @Lob
    private String description;

    private Integer sellingPrice;
    private Integer discountedPrice;

    @Lob
    private byte[] productImage;

    // ✅ CORRECT GETTER (NO EXCEPTION)
    public Integer getSellingPrice() {
        return sellingPrice;
    }

    // ✅ OPTIONAL HELPER
    public int getFinalPrice() {
        return (discountedPrice != null && discountedPrice > 0)
                ? discountedPrice
                : sellingPrice;
    }
    public Integer getDiscountedPrice() {
    return discountedPrice;
}

public void setDiscountedPrice(Integer discountedPrice) {
    this.discountedPrice = discountedPrice;
}
}
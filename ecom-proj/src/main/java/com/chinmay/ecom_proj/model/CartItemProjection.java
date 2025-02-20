package com.chinmay.ecom_proj.model;

import com.chinmay.ecom_proj.Utils.ImageUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public interface CartItemProjection {
    int getId();
    int getProductId();  // Corrected from product_id
    String getName();  // Changed from int to String
    String getDescription();
    String getBrand();
    String getCategory();
    BigDecimal getPrice();
    int getQuantity();  // Fixed spelling from "qunatity"
    String getImageName();
    String getImageType();

    @JsonIgnore
    byte[] getImageData();

    default String getImageUrl() {
        return ImageUtils.generateImageUrl(getImageData(), getImageType());
    }
}

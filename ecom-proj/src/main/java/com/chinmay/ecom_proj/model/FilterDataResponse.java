package com.chinmay.ecom_proj.model;

import java.util.List;

public class FilterDataResponse {
    private List<String> categories;
    private List<String> brands;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }
}

package com.ecommerce.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private int id;
    private int categoryId;
    private String categoryName;
    private String name;
    private String brand;
    private String partNumber;
    private String vehicleType;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private int stockQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getPartNumber() { return partNumber; }
    public void setPartNumber(String partNumber) { this.partNumber = partNumber; }
    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStoredImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getImageUrl() {
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            return imageUrl;
        }
        String safeName = name == null ? "" : name;
        String safeCategory = categoryName == null ? "" : categoryName;
        String value = (safeName + " " + safeCategory).toLowerCase();
        if (value.contains("brake")) {
            return "https://images.unsplash.com/photo-1600706432502-77a0e2e533a0?auto=format&fit=crop&w=800&q=80";
        }
        if (value.contains("filter")) {
            return "https://images.unsplash.com/photo-1632823471565-1ecdf5c8d6ec?auto=format&fit=crop&w=800&q=80";
        }
        if (value.contains("battery")) {
            return "https://images.unsplash.com/photo-1617886322168-72b886573c01?auto=format&fit=crop&w=800&q=80";
        }
        if (value.contains("mirror")) {
            return "https://images.unsplash.com/photo-1542362567-b07e54358753?auto=format&fit=crop&w=800&q=80";
        }
        if (value.contains("shock") || value.contains("suspension")) {
            return "https://images.unsplash.com/photo-1486262715619-67b85e0b08d3?auto=format&fit=crop&w=800&q=80";
        }
        return "https://images.unsplash.com/photo-1532974297617-c0f05fe48bff?auto=format&fit=crop&w=800&q=80";
    }
}

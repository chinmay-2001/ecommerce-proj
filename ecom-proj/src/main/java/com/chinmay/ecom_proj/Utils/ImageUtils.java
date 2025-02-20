package com.chinmay.ecom_proj.Utils;

import java.util.Base64;

public class ImageUtils {
    public static String generateImageUrl(byte[] imageData, String imageType) {
        if (imageData == null || imageData.length == 0) {
            return null; // Handle missing images gracefully
        }
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        return "data:image/" + imageType + ";base64," + base64Image;
    }
}

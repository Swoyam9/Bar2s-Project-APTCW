package com.ecommerce.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public final class ValidationUtils {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE = Pattern.compile("^[0-9+\\- ]{7,20}$");
    private static final Pattern PASSWORD =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$");

    private ValidationUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return !isBlank(email) && EMAIL.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return !isBlank(phone) && PHONE.matcher(phone).matches();
    }

    public static boolean isStrongEnoughPassword(String password) {
        return password != null && PASSWORD.matcher(password).matches();
    }

    public static boolean isValidVehicleType(String vehicleType) {
        return "Bike".equals(vehicleType) || "Car".equals(vehicleType) || "Scooter".equals(vehicleType);
    }

    public static boolean isPositivePrice(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isNonNegative(int value) {
        return value >= 0;
    }
}

package com.algaworks.algashop.ordering.domain.model.exception;

import java.util.Locale;

public class ErrorMessages {
    public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";
    public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";
    public static final String VALIDATION_ERROR_FULLNAME_IS_NULL = "FullName cannot be null";
    public static final String VALIDATION_ERROR_FULLNAME_IS_BLANK = "FullName cannot be blank";

    public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is archived it cannot be changed";

    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Cannot change order %s status from %s to %s";

    public static final String ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST = "Order %s expected delivery date cannot be in the past";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_ITEMS = "Order %s cannot be placed, it has no items";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_SHIPPING_INFO = "Order %s cannot be placed, it has no shipping info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_BILLING_INFO = "Order %s cannot be placed, it has no billing info";

    public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NO_PAYMENT_METHOD = "Order %s cannot be placed, it has no payment method";

    public static final String ERROR_ORDER_DOES_NOT_CONTAIN_ITEM = "Order %s does not contain item %s";

    public static final String ERROR_PRODUCT_IS_OUT_OF_STOCK = "Product %s is out of stock";

    public static final String ERROR_ORDER_CANNOT_BE_EDITED = "Order %s with status %s cannot be edited";

    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM = "Shopping Cart %s does not contain item %s";

    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT = "Shopping Cart %s does not contain product %s";

    public static final String ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT = "Shopping Cart %s cannot be updated, incompatible product %s";
}
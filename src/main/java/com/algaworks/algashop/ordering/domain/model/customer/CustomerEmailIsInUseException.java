package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.DomainException;
import com.algaworks.algashop.ordering.domain.model.ErrorMessages;

public class CustomerEmailIsInUseException extends DomainException {
    public CustomerEmailIsInUseException() {
        super(ErrorMessages.ERROR_CUSTOMER_EMAIL_IS_IN_USE);
    }
}

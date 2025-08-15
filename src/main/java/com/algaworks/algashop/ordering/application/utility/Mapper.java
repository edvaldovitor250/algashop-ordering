package com.algaworks.algashop.ordering.application.utility;

public interface Mapper {
    <T> T convert(Object obj, Class<T> destinationType);
}

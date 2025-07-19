package com.algaworks.algashop.ordering.domain.model.valueobject;

public record Document(String documento) {
    
    public Document(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new IllegalArgumentException("Document cannot be null or blank");
        }
        this.documento = documento;
    }

    @Override
    public String toString() {
        return documento;
    }

}

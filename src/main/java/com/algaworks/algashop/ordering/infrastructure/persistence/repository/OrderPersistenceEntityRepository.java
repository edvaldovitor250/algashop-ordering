package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.UUID;

public interface OrderPersistenceEntityRepository extends JpaRepository<OrderPersistenceEntity, Long> {

    @Query("""
    SELECT o FROM OrderPersistenceEntity o
    WHERE o.customerId = :customerId
    AND FUNCTION('YEAR', o.placedAt) = :year
    """)
    List<OrderPersistenceEntity> placedByCustomerInYear(
            @Param("customerId") UUID customerId,
            @Param("year") Integer year
    );

    @Query("""
    SELECT COUNT(o)
    FROM OrderPersistenceEntity o
    WHERE o.customer.id = :customerId
    AND YEAR(o.placedAt) = :year
    AND o.canceledAt IS NULL
""")
    long salesQuantityByCustomerInYear(
            @Param("customerId") UUID customerId,
            @Param("year") int year
    );

    @Query("""
    SELECT SUM(o.totalAmount)
    FROM OrderPersistenceEntity o
    WHERE o.customerId = :customerId
    AND o.canceledAt IS NULL
    AND o.paidAt IS NOT NULL

""")
    BigDecimal totalAmountByCustomerInYear(@Param("customerId") UUID customerId);

}

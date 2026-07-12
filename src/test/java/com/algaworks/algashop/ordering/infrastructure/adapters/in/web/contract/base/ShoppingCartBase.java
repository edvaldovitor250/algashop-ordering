package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.contract.base;

import com.algaworks.algashop.ordering.core.application.security.SecurityChecks;
import com.algaworks.algashop.ordering.core.application.shoppingcart.query.ShoppingCartOutputTestDataBuilder;
import com.algaworks.algashop.ordering.core.application.shoppingcart.ShoppingCartManagementApplicationService;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.core.domain.model.shoppingcart.ShoppingCartNotFoundException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.shoppingcart.MyShoppingCartController;
import com.algaworks.algashop.ordering.utils.MockJwtDecoderFactory;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@WebMvcTest(controllers = MyShoppingCartController.class)
public class ShoppingCartBase {

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ShoppingCartManagementApplicationService managementService;

    @MockitoBean
    private ForQueryingShoppingCarts queryService;

    @MockitoBean
    private SecurityChecks securityChecks;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    public static final UUID validShoppingCartId = UUID.fromString("ad265aa3-c77d-46e9-9782-b70c487c1e17");

    public static final UUID notFoundShoppingCartId = UUID.fromString("e2103964-5353-4910-81ee-212a40a2ca70");

    private static final UUID AUTHENTICATED_CUSTOMER_ID = UUID.fromString(MockJwtDecoderFactory.DEFAULT_SUBJECT);

    @BeforeEach
    void setUp() {
        Mockito.when(jwtDecoder.decode(Mockito.anyString()))
                .thenReturn(MockJwtDecoderFactory.buildDefaultJwt());

        Mockito.when(securityChecks.getAuthenticatedUserId())
                .thenReturn(AUTHENTICATED_CUSTOMER_ID);

        MockMvcRequestSpecification spec = RestAssuredMockMvc.given()
                .header("Authorization", "Bearer " + MockJwtDecoderFactory.DEFAULT_TOKEN_VALUE);

        RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context)
                        .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                        .build()
        );

        RestAssuredMockMvc.requestSpecification = spec;
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();

        Mockito.when(queryService.findByCustomerId(AUTHENTICATED_CUSTOMER_ID))
                .thenReturn(ShoppingCartOutputTestDataBuilder.aShoppingCart().id(validShoppingCartId).customerId(AUTHENTICATED_CUSTOMER_ID).build());

        Mockito.when(queryService.findById(validShoppingCartId))
                .thenReturn(ShoppingCartOutputTestDataBuilder.aShoppingCart().id(validShoppingCartId).customerId(AUTHENTICATED_CUSTOMER_ID).build());

        Mockito.when(queryService.findById(notFoundShoppingCartId))
                .thenThrow(new ShoppingCartNotFoundException(notFoundShoppingCartId));

        Mockito.when(managementService.createNew(Mockito.any(UUID.class)))
                .thenReturn(validShoppingCartId);

    }
}

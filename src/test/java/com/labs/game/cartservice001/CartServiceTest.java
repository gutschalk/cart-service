package com.labs.game.cartservice001;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.extractProperty;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceTest {


    @MockBean
    private CartService cartService;
    @Mock
    private CartRepository repository;

    @Before
    public void setUp() {
        cartService = new CartService(repository);
        given(repository.findByCustomer(eq("111"))).willReturn(
                new CartItem[]{
                        new CartItem(0, "111", "AAA", 10),
                        new CartItem(0, "111", "BBB", 100)
                });

        given(repository.findByCustomer(eq("222"))).willReturn(
                new CartItem[]{
                        new CartItem(0, "222", "AAA", 10)
                });

        given(repository.findByCustomer(eq("333"))).willReturn(null);
    }


    @Test
    public void findByCustomer_returnsMultipleProducts() {
        final CartItem[] cartItems = cartService.findByCustomer("111");
        assertThat(cartItems).isNotNull();
        assertThat(cartItems.length).isEqualTo(2);
        assertThat(extractProperty("product").from(cartItems)).contains("AAA", "BBB");
    }

    @Test
    public void findByCustomer_returnsSingleProduct() {
        final CartItem[] cartItems = cartService.findByCustomer("222");
        assertThat(cartItems).isNotNull();
        assertThat(cartItems.length).isEqualTo(1);
        assertThat(cartItems[0].getCustomer()).isEqualTo("222");

    }

    @Test
    public void findByCustomer_returnsEmptyArray() {
        final CartItem[] cartItems = cartService.findByCustomer("333");
        assertThat(cartItems).isNotNull();
        assertThat(cartItems.length).isEqualTo(0);
    }
}

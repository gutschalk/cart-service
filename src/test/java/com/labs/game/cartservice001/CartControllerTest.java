package com.labs.game.cartservice001;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
public class CartControllerTest {

    @MockBean
    private CartService cartService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

        given(cartService.findByCustomer(eq("111"))).willReturn(
                new CartItem[]{
                        new CartItem(0, "111", "AAA", 10),
                        new CartItem(0, "111", "BBB", 100)
                });

        given(cartService.findByCustomer(eq("222"))).willReturn(
                new CartItem[]{
                        new CartItem(0, "222", "AAA", 10)
                });

        given(cartService.findByCustomer(eq("333"))).willReturn(new CartItem[0]);
    }

    @Test
    public void getItems_returnsOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart-items?customer=111")) // calling mocked MVC Controller
                .andExpect(status().isOk()); //asserting that it responds with HTTP 200 status code


    }

    @Test
    public void getItems_returnsCustomer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/cart-items?customer=111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer").value("111")); // customer code should correspond to the requested value

        mockMvc.perform(MockMvcRequestBuilders.get("/cart-items?customer=222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customer").value("222")); // the same should happen for a different customer code
    }

    @Test
    public void getItems_returnsEmptyList_forUnknownCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart-items?customer=333"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0))); // empty list should be returned for unknown customer

    }
}
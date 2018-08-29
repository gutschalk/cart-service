package com.labs.game.cartservice001;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getCart_returnsOK(){
        final ResponseEntity<CartItem[]> entity = restTemplate.getForEntity("/cart-items?customer=1111", CartItem[].class); // here the service is called
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK); // assert validates that service responds with HTTP 200 code

    }
}

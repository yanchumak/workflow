package org.example.delivery;

import lombok.Builder;

@Builder
public record DeliveryMetadata(String comment,
                               long zipCode,
                               int pizzas,
                               int drinks) {

}

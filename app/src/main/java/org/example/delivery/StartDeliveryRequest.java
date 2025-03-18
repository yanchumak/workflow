package org.example.delivery;

import lombok.Builder;

@Builder
public record StartDeliveryRequest(int availablePizzas, int availableDrinks) {

}

package org.example.delivery;

import lombok.Builder;

@Builder
public record OrderPlacementRequest(int pizzas, int drinks, long zipCode) {

}

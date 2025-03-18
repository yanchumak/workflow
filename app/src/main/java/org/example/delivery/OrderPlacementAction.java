package org.example.delivery;

import com.workflow.Action;

public class OrderPlacementAction implements Action<DeliveryMetadata, OrderPlacementRequest> {

  @Override
  public DeliveryMetadata execute(DeliveryMetadata metadata, OrderPlacementRequest payload) {

    // Call 3rd-party service to place the order, Send event, etc.
    System.out.println("Order placed: " + payload);
    return DeliveryMetadata.builder()
        .drinks(payload.drinks())
        .pizzas(payload.pizzas())
        .zipCode(payload.zipCode()).build();
  }

}

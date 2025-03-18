package org.example.delivery;

import com.workflow.Condition;

public class CheckAvailabilityCondition implements Condition<DeliveryMetadata, StartDeliveryRequest> {

  @Override
  public boolean test(DeliveryMetadata metadata, StartDeliveryRequest payload) {

    return payload.availableDrinks() >= metadata.drinks() &&
        payload.availablePizzas() >= metadata.pizzas();
  }

}

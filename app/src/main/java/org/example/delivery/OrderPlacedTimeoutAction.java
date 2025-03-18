package org.example.delivery;

import com.workflow.Action;

public class OrderPlacedTimeoutAction implements Action<DeliveryMetadata, Timeout> {

  @Override
  public DeliveryMetadata execute(DeliveryMetadata metadata, Timeout payload) {
    System.out.println("Order placed timeout: " + metadata);
    return metadata;
  }

}

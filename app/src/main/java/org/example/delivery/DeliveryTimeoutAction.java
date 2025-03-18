package org.example.delivery;

import com.workflow.Action;

public class DeliveryTimeoutAction implements Action<DeliveryMetadata, Timeout> {

  @Override
  public DeliveryMetadata execute(DeliveryMetadata metadata, Timeout payload) {
    System.out.println("Delivery timeout: " + metadata);
    return metadata;
  }

}

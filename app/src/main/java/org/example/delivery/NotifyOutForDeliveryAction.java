package org.example.delivery;

import com.workflow.Action;

public class NotifyOutForDeliveryAction implements Action<DeliveryMetadata, StartDeliveryRequest> {

  @Override
  public DeliveryMetadata execute(DeliveryMetadata metadata, StartDeliveryRequest payload) {
    System.out.println("Notifying out for delivery, metadata " + metadata + ", payload " + payload);
    return metadata;
  }
}

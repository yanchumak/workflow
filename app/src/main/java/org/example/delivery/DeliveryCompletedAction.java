package org.example.delivery;

import com.workflow.Action;

public class DeliveryCompletedAction implements Action<DeliveryMetadata, DeliveryCompletedEvent> {

  @Override
  public DeliveryMetadata execute(DeliveryMetadata metadata, DeliveryCompletedEvent payload) {
    System.out.println("Delivery completed: " + payload);
    return metadata;
  }

}

package org.example.delivery;

import com.workflow.Action;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartDeliveryAction implements Action<DeliveryMetadata, StartDeliveryRequest> {

  private final DeliveryService deliveryService;

  @Override
  public DeliveryMetadata execute(DeliveryMetadata metadata, StartDeliveryRequest payload) {
    System.out.println("Start delivery: " + payload);
    return metadata;
  }
}

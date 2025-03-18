
package org.example;

import java.util.List;

import com.workflow.BaseWorkflowExecutor;
import com.workflow.State;
import com.workflow.Transition;
import com.workflow.Workflow;
import org.example.delivery.CheckAvailabilityCondition;
import org.example.delivery.DeliveryCompletedAction;
import org.example.delivery.DeliveryCompletedEvent;
import org.example.delivery.DeliveryMetadata;
import org.example.delivery.DeliveryTimeoutAction;
import org.example.delivery.DeliveryWorkflowExecution;
import org.example.delivery.NotifyOutForDeliveryAction;
import org.example.delivery.OrderPlacedTimeoutAction;
import org.example.delivery.OrderPlacementAction;
import org.example.delivery.OrderPlacementRequest;
import org.example.delivery.StartDeliveryAction;
import org.example.delivery.StartDeliveryRequest;
import org.example.delivery.Timeout;

public class App {

  public static void main(String[] args) {

    final var orderDeliveryWorkflow = Workflow.<DeliveryMetadata>builder()
        .name("Order delivery")
        .initialState("INIT")
        .expirableStates(List.of("ORDER_PLACED", "DELIVERY_STARTED"))
        .defaultStateOnError("ERROR")
        .states(List.of(
            initState(),
            orderPlacedState(),
            deliveryStartedState()))
        .build();

    final var workflowExecutor = new BaseWorkflowExecutor<DeliveryMetadata, DeliveryWorkflowExecution>(null, null);

    workflowExecutor.execute(orderDeliveryWorkflow,
        "ORDER_PLACEMENT_REQUEST", new OrderPlacementRequest(1, 1, 12345));
  }

  private static State<DeliveryMetadata> deliveryStartedState() {
    final var deliveryStartedTimeoutTransition = Transition.<DeliveryMetadata, Timeout>builder()
        .trigger("TIMEOUT")
        .action(new DeliveryTimeoutAction())
        .state("DELIVERY_STARTED_TIMEOUT").build();

    final var deliveryCompletedTransition = Transition.<DeliveryMetadata, DeliveryCompletedEvent>builder()
        .trigger("DELIVERY_COMPLETED_EVENT")
        .action(new DeliveryCompletedAction())
        .state("DELIVERY_COMPLETED").build();

    return State.<DeliveryMetadata>builder()
        .transitions(List.of(deliveryCompletedTransition, deliveryStartedTimeoutTransition))
        .name("DELIVERY_STARTED").build();
  }

  private static State<DeliveryMetadata> orderPlacedState() {
    final var startDeliveryTransition = Transition.<DeliveryMetadata, StartDeliveryRequest>builder()
        .trigger("START_DELIVERY_REQUEST")
        .conditions(List.of(new CheckAvailabilityCondition()))
        .action(new StartDeliveryAction())
        .state("DELIVERY_STARTED").build();

    final var outForDeliveryTransition = Transition.<DeliveryMetadata, StartDeliveryRequest>builder()
        .trigger("START_DELIVERY_REQUEST")
        .action(new NotifyOutForDeliveryAction())
        .state("OUT_FOR_DELIVERY").build();

    final var orderPlacedTimeoutTransition = Transition.<DeliveryMetadata, Timeout>builder()
        .trigger("TIMEOUT")
        .action(new OrderPlacedTimeoutAction())
        .state("ORDER_PLACED_TIMEOUT").build();

    return State.<DeliveryMetadata>builder()
        .transitions(List.of(startDeliveryTransition, outForDeliveryTransition, orderPlacedTimeoutTransition))
        .name("ORDER_PLACED").build();
  }

  static State<DeliveryMetadata> initState() {
    final var orderPlacementTransition = Transition.<DeliveryMetadata, OrderPlacementRequest>builder()
        .trigger("ORDER_PLACEMENT_REQUEST")
        .action(new OrderPlacementAction())
        .state("ORDER_PLACED").build();

    return State.<DeliveryMetadata>builder()
        .transitions(List.of(orderPlacementTransition))
        .name("INIT").build();
  }
}

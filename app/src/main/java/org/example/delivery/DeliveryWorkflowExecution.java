package org.example.delivery;

import java.time.LocalDateTime;

import com.workflow.WorkflowExecution;

public class DeliveryWorkflowExecution implements WorkflowExecution<DeliveryMetadata> {

  @Override
  public DeliveryMetadata metadata() {
    return null;
  }

  @Override
  public int version() {
    return 0;
  }

  @Override
  public String state() {
    return "";
  }

  @Override
  public LocalDateTime createdAt() {
    return null;
  }

  @Override
  public LocalDateTime updatedAt() {
    return null;
  }
}

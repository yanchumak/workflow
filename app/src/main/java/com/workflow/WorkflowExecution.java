package com.workflow;

import java.time.LocalDateTime;

public interface WorkflowExecution<M> {
  M metadata();

  int version();

  String state();

  LocalDateTime createdAt();

  LocalDateTime updatedAt();
}

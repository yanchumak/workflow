package com.workflow;

public interface WorkflowExecutionFactory<M, S extends WorkflowExecution<M>> {

  S create(M metadata);
}

package com.workflow;

public interface WorkflowExecutor<M, S extends WorkflowExecution<M>> {

  <T> S execute(Workflow<M> workflow, String trigger, T payload);
}

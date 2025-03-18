package com.workflow;

import java.util.Optional;

public interface WorkflowExecutionRepository<M, S extends WorkflowExecution<M>> {

  Optional<S> findByMetadata(M metadata);

  S save(S workflowExecution);
}

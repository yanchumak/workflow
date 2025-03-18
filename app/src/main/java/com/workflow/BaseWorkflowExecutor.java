package com.workflow;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.util.Optional;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class BaseWorkflowExecutor<M, S extends WorkflowExecution<M>> implements WorkflowExecutor<M, S> {

  private final static MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

  private final static MethodHandle ACTION_METHOD_HANDLE = actionMethodHandle(LOOKUP);

  private final static MethodHandle CONDITION_METHOD_HANDLE = conditionMethodHandle(LOOKUP);

  @SneakyThrows
  private static MethodHandle actionMethodHandle(Lookup lookup) {
    final var methodType = MethodType.methodType(Object.class, Object.class, Object.class);
    return lookup.findVirtual(Action.class, "execute", methodType);
  }

  @SneakyThrows
  private static MethodHandle conditionMethodHandle(Lookup lookup) {
    final var methodType = MethodType.methodType(boolean.class, Object.class, Object.class);
    return lookup.findVirtual(Condition.class, "test", methodType);
  }

  private final WorkflowExecutionRepository<M, S> repository;

  private final WorkflowExecutionFactory<M, S> factory;

  @Override
  public <T> S execute(Workflow<M> workflow, String trigger, T payload) {

    M metadata = null;
    final var maybeExecution = Optional.<WorkflowExecution>empty(); //this.repository.findByMetadata(metadata);
    final var currentStateName = maybeExecution.map(WorkflowExecution::state)
        .orElseGet(workflow::initialState);

    final var currentState = workflow.states().stream()
        .filter(state -> state.name().equals(currentStateName))
        .findFirst().orElseThrow(() -> new IllegalStateException("State with name '" + currentStateName + "' not found"));

    final var matchedTransition = currentState.transitions().stream()
        .filter(transition -> transition.trigger().equals(trigger))
        .filter(transition -> checkConditions(transition, metadata, payload))
        .findFirst().orElseThrow(() -> new IllegalStateException("Transition for trigger '" + trigger + "' not found"));

    var nextState = matchedTransition.state();
    try {
      final var action = matchedTransition.action();
      final var updatedMetadata = (M) ACTION_METHOD_HANDLE.invoke(action, metadata, payload);
    } catch (Throwable e) {
      e.printStackTrace(); //TODO logging required
      nextState =
          Optional.ofNullable(matchedTransition.stateOnError())
              .orElseGet(workflow::defaultStateOnError);
    }
    // next state save
    return null;
  }

  private <T> boolean checkConditions(Transition<M, ?> transition, M metadata, T payload) {
    if (transition.conditions() == null || transition.conditions().isEmpty()) {
      return true;
    }

    return transition.conditions().stream()
        .anyMatch(condition -> {
          try {
            return (boolean)CONDITION_METHOD_HANDLE.invoke(condition, metadata, payload);
          } catch (Throwable e) {
            throw new RuntimeException(e);
          }
        });
  }
}

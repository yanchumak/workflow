package com.workflow;

import java.util.List;

import lombok.Builder;

@Builder
public record Transition<M, T>(String trigger,
                         Action<M, T> action,
                         List<? extends Condition<M, T>> conditions,
                         String state,
                         String stateOnError) {
}

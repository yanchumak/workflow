package com.workflow;

public interface Action<M, T> {
  M execute(M metadata, T payload);
}

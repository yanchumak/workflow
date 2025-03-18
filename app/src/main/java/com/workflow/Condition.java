package com.workflow;

public interface Condition<M, T> {

     boolean test(M metadata, T payload);
}

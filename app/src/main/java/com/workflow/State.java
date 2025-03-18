package com.workflow;

import java.util.List;

import lombok.Builder;

@Builder
public record State<M>(String name, List<Transition<M, ?>> transitions) {

}

package com.workflow;

import java.util.List;

import lombok.Builder;

@Builder
public record Workflow<M>(String name,
                       List<String> expirableStates,
                       String initialState,
                       String defaultStateOnError,
                       List<State<M>> states) {

}

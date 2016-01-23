package org.camunda.bpm.engine.test.api.runtime.util;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SetLocalVariableTask implements JavaDelegate {

  public void execute(DelegateExecution execution) throws Exception {
    execution.setVariableLocal("test", "test2");
  }

}
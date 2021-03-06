/*
 * Copyright © 2013-2019 camunda services GmbH and various authors (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.util;

import static org.junit.Assert.assertEquals;

import org.camunda.bpm.engine.impl.batch.BatchConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.util.List;

public class BatchUtilTest {
  
  private ProcessEngineConfigurationImpl engineConfiguration;
  private BatchConfiguration batchConfiguration;
  private List<String> ids;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() {
    batchConfiguration = Mockito.mock(BatchConfiguration.class);
    engineConfiguration = Mockito.mock(ProcessEngineConfigurationImpl.class);
    ids = Mockito.mock(List.class);
    when(batchConfiguration.getIds()).thenReturn(ids);
  }
  
  @Test
  public void shouldReturnCorrectSizeUneven() {
    when(ids.size()).thenReturn(5);
    when(engineConfiguration.getInvocationsPerBatchJob()).thenReturn(2);
    testConfiguration(engineConfiguration, batchConfiguration, 3);
  }

  @Test
  public void shouldReturnCorrectSizeZeroBatchSize() {
    when(ids.size()).thenReturn(2);
    when(engineConfiguration.getInvocationsPerBatchJob()).thenReturn(0);
    testConfiguration(engineConfiguration, batchConfiguration, 0);
  }

  @Test
  public void shouldReturnCorrectSizeEven() {
    when(ids.size()).thenReturn(4);
    when(engineConfiguration.getInvocationsPerBatchJob()).thenReturn(2);
    testConfiguration(engineConfiguration, batchConfiguration, 2);
  }

  @Test
  public void shouldReturnCorrectSizeZeroInstances() {
    when(ids.size()).thenReturn(0);
    when(engineConfiguration.getInvocationsPerBatchJob()).thenReturn(2);
    testConfiguration(engineConfiguration, batchConfiguration, 0);
  }

  @Test
  public void shouldReturnCorrectSizeZeroInstancesZeroBatchSize() {
    when(ids.size()).thenReturn(0);
    when(engineConfiguration.getInvocationsPerBatchJob()).thenReturn(0);
    testConfiguration(engineConfiguration, batchConfiguration, 0);
  }
  
  private void testConfiguration(ProcessEngineConfigurationImpl engineConfiguration, BatchConfiguration batchConfiguration, int expectedResult) {
    assertEquals(expectedResult, BatchUtil.calculateBatchSize(engineConfiguration, batchConfiguration));
  }
}

/*
 * Copyright © 2013-2018 camunda services GmbH and various authors (info@camunda.com)
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
package org.camunda.bpm.engine.impl.batch.job;

import org.camunda.bpm.engine.impl.batch.SetRetriesBatchConfiguration;
import org.camunda.bpm.engine.impl.json.JsonObjectConverter;
import org.camunda.bpm.engine.impl.util.JsonUtil;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * @author Askar Akhmerov
 */
public class SetJobRetriesBatchConfigurationJsonConverter extends JsonObjectConverter<SetRetriesBatchConfiguration> {
  public static final SetJobRetriesBatchConfigurationJsonConverter INSTANCE = new SetJobRetriesBatchConfigurationJsonConverter();

  public static final String JOB_IDS = "jobIds";
  public static final String RETRIES = "retries";

  public JsonObject toJsonObject(SetRetriesBatchConfiguration configuration) {
    JsonObject json = JsonUtil.createObject();

    JsonUtil.addListField(json, JOB_IDS, configuration.getIds());
    JsonUtil.addField(json, RETRIES, configuration.getRetries());
    return json;
  }

  public SetRetriesBatchConfiguration toObject(JsonObject json) {
    SetRetriesBatchConfiguration configuration = new SetRetriesBatchConfiguration(
        readJobIds(json),
        JsonUtil.getInt(json, RETRIES)
    );

    return configuration;
  }

  protected List<String> readJobIds(JsonObject jsonObject) {
    return JsonUtil.asList(JsonUtil.getArray(jsonObject, JOB_IDS));
  }
}

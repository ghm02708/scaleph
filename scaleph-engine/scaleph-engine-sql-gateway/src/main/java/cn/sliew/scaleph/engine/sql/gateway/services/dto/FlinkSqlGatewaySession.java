/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.engine.sql.gateway.services.dto;

import java.util.Map;

import org.apache.flink.table.gateway.api.session.SessionHandle;
import org.apache.flink.table.gateway.service.context.SessionContext;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.sliew.scaleph.system.model.BaseDTO;
import lombok.Data;

/**
 * org.apache.flink.table.gateway.service.session.Session
 */
@Data
public class FlinkSqlGatewaySession extends BaseDTO implements AutoCloseable {

    private SessionHandle sessionHandle;
    private Map<String, String> sessionConfig;

    @JsonIgnore
    private SessionContext sessionContext;

    @Override
    public void close() {
        sessionContext.close();
    }
}
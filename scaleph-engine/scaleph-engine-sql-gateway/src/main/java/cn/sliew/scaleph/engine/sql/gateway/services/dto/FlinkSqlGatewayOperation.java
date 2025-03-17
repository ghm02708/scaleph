/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.sliew.scaleph.engine.sql.gateway.services.dto;

import cn.sliew.scaleph.common.dict.common.YesOrNo;
import cn.sliew.scaleph.system.model.BaseDTO;
import lombok.Data;
import org.apache.flink.api.common.JobID;
import org.apache.flink.table.gateway.api.operation.OperationHandle;
import org.apache.flink.table.gateway.api.operation.OperationStatus;
import org.apache.flink.table.gateway.api.session.SessionHandle;

/**
 * org.apache.flink.table.gateway.service.operation.OperationManager.Operation
 */
@Data
public class FlinkSqlGatewayOperation extends BaseDTO {

    private SessionHandle sessionHandle;
    private OperationHandle operationHandle;
    private OperationStatus operationStatus;
    private String operationError;
    private JobID jobID;
    private YesOrNo isQuery;
}

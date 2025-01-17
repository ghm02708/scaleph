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

package cn.sliew.scaleph.dataservice.service.dto;

import cn.sliew.scaleph.system.model.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 数据服务 返回结果映射
 * </p>
 */
@Data
@Schema(name = "DataserviceResultMapping对象", description = "数据服务 返回结果映射")
public class DataserviceResultMappingDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "返回结果集id")
    private Long resultMapId;

    @Schema(description = "属性")
    private String property;

    @Schema(description = "java 类型")
    private String javaType;

    @Schema(description = "列")
    private String column;

    @Schema(description = "jdbc 类型")
    private String jdbcType;

    @Schema(description = "类型转换器")
    private String typeHandler;

}

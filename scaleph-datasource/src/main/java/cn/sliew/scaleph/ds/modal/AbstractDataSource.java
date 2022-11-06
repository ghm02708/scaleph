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

package cn.sliew.scaleph.ds.modal;

import cn.sliew.scaleph.common.dict.job.DataSourceType;
import cn.sliew.scaleph.ds.service.dto.DsInfoDTO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = "MySQL", value = MySQLDataSource.class),
        @JsonSubTypes.Type(name = "Elasticsearch", value = ElasticsearchDataSource.class),
})
public abstract class AbstractDataSource {

    @NotNull
    @ApiModelProperty("data source type")
    private DataSourceType type;

    @NotNull
    @ApiModelProperty("data source type id")
    private Long dsTypeId;

    @ApiModelProperty("version")
    private String version;

    @NotBlank
    @ApiModelProperty("name")
    private String name;

    @ApiModelProperty("remark")
    private String remark;

//    @NotEmpty
//    @ApiModelProperty("additional props")
//    private Map<String, Object> additionalProps;

    public abstract DsInfoDTO toDsInfo();

}
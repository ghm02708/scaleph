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

package cn.sliew.scaleph.common.dict.seatunnel;

import cn.sliew.carp.framework.common.dict.DictInstance;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SeaTunnelConnectorFeature implements DictInstance {

    BATCH("batch", "batch"),
    STREAM("stream", "stream"),
    EXACTLY_ONCE("exactly-once", "exactly-once"),
    SCHEMA_PROJECTION("schema projection", "schema projection"),
    PARALLELISM("parallelism", "parallelism"),
    SUPPORT_USER_DEFINED_SPLIT("support user-defined split", "support user-defined split"),
    CDC("cdc", "cdc"),
    ;

    @JsonCreator
    public static SeaTunnelConnectorFeature of(String value) {
        return Arrays.stream(values())
                .filter(instance -> instance.getValue().equals(value))
                .findAny().orElseThrow(() -> new EnumConstantNotPresentException(SeaTunnelConnectorFeature.class, value));
    }

    @EnumValue
    private String value;
    private String label;

    SeaTunnelConnectorFeature(String value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

}

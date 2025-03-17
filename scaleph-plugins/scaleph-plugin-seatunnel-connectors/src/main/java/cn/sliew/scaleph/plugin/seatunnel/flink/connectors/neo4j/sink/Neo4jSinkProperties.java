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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.neo4j.sink;

import cn.sliew.scaleph.plugin.framework.property.*;
import com.fasterxml.jackson.databind.JsonNode;

public enum Neo4jSinkProperties {
    ;

    public static final PropertyDescriptor<Integer> MAX_BATCH_SIZE = new PropertyDescriptor.Builder()
            .name("max_batch_size")
            .description("max_batch_size refers to the maximum number of data entries that can be written in a single transaction when writing to a database.")
            .type(PropertyType.INT)
            .parser(Parsers.INTEGER_PARSER)
            .addValidator(Validators.POSITIVE_INTEGER_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<String> WRITE_MODE = new PropertyDescriptor.Builder()
            .name("write_mode")
            .description("The default value is oneByOne, or set it to \"Batch\" if you want to have the ability to write in batches")
            .type(PropertyType.STRING)
            .parser(Parsers.STRING_PARSER)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

    public static final PropertyDescriptor<JsonNode> QUERY_PARAM_POSITION = new PropertyDescriptor.Builder()
            .name("queryParamPosition")
            .description("position mapping information for query parameters.")
            .type(PropertyType.STRING)
            .parser(Parsers.JSON_PARSER)
            .properties(Property.Required)
            .addValidator(Validators.NON_BLANK_VALIDATOR)
            .validateAndBuild();

}

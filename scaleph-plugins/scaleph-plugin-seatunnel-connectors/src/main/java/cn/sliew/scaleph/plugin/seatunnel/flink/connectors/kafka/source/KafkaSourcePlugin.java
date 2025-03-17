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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.kafka.source;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.mq.KafkaDataSourceProperties;
import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.dict.seatunnel.SeaTunnelPluginMapping;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.seatunnel.flink.SeaTunnelConnectorPlugin;
import cn.sliew.scaleph.plugin.seatunnel.flink.env.CommonProperties;
import cn.sliew.scaleph.plugin.seatunnel.flink.resource.ResourceProperties;
import cn.sliew.scaleph.plugin.seatunnel.flink.resource.ResourceProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.kafka.KafkaProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.kafka.source.KafkaSourceProperties.*;

/**
 * @author lizu
 */
@AutoService(SeaTunnelConnectorPlugin.class)
public class KafkaSourcePlugin extends SeaTunnelConnectorPlugin {

    public KafkaSourcePlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Kafka Source Plugin",
                KafkaSourcePlugin.class.getName());

        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(TOPIC);
        props.add(PATTERN);
        props.add(CONSUMER_GROUP);
        props.add(COMMIT_ON_CHECKPOINT);
        props.add(SCHEMA);
        props.add(FORMAT);
        props.add(FORMAT_ERROR_HANDLE_WAY);
        props.add(FIELD_DELIMITER);
        props.add(START_MODE);
        props.add(START_MODE_TIMESTAMP);
        props.add(START_MODE_OFFSETS);
        props.add(PARTITION_DISCOVERY_INTERVAL_MILLIS);
        props.add(KAFKA_CONFIG);
        props.add(CommonProperties.PARALLELISM);
        props.add(CommonProperties.RESULT_TABLE_NAME);
        supportedProperties = Collections.unmodifiableList(props);
    }


    @Override
    public List<ResourceProperty> getRequiredResources() {
        return Collections.singletonList(ResourceProperties.DATASOURCE_RESOURCE);
    }

    @Override
    public ObjectNode createConf() {
        ObjectNode conf = super.createConf();
        JsonNode jsonNode = properties.get(ResourceProperties.DATASOURCE);
        DataSourceInfo dataSourceInfo = JacksonUtil.toObject(jsonNode, DataSourceInfo.class);
        KafkaDataSourceProperties props = (KafkaDataSourceProperties) dataSourceInfo.getProps();

        conf.putPOJO(BOOTSTRAP_SERVERS.getName(), props.getBootstrapServers());
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SOURCE_KAFKA;
    }
}

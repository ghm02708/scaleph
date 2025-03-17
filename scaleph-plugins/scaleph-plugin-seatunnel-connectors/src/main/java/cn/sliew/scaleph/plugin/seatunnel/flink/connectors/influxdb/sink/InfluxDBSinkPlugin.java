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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.influxdb.sink;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.InfluxDBDataSourceProperties;
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
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.influxdb.InfluxDBProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.influxdb.sink.InfluxDBSinkProperties.*;

@AutoService(SeaTunnelConnectorPlugin.class)
public class InfluxDBSinkPlugin extends SeaTunnelConnectorPlugin {

    public InfluxDBSinkPlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Write data to InfluxDB.",
                InfluxDBSinkPlugin.class.getName());
        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(DATABASE);
        props.add(MEASUREMENT);
        props.add(KEY_TIME);
        props.add(KEY_TAGS);
        props.add(BATCH_SIZE);
        props.add(BATCH_INTERVAL_MS);
        props.add(MAX_RETRIES);
        props.add(RETRY_BACKOFF_MULTIPLIER_MS);
        props.add(MAX_RETRY_BACKOFF_MS);
        props.add(CONNECT_TIMEOUT_MS);
        props.add(CommonProperties.PARALLELISM);
        props.add(CommonProperties.SOURCE_TABLE_NAME);
        this.supportedProperties = Collections.unmodifiableList(props);
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
        InfluxDBDataSourceProperties props = (InfluxDBDataSourceProperties) dataSourceInfo.getProps();

        conf.putPOJO(URL.getName(), props.getUrl());
        if (StringUtils.hasText(props.getUsername())) {
            conf.putPOJO(USERNAME.getName(), props.getUsername());
        }
        if (StringUtils.hasText(props.getPassword())) {
            conf.putPOJO(PASSWORD.getName(), props.getPassword());
        }
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SINK_INFLUXDB;
    }
}

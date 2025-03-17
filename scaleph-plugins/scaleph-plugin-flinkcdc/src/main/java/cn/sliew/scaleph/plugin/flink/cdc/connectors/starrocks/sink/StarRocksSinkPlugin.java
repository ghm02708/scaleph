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

package cn.sliew.scaleph.plugin.flink.cdc.connectors.starrocks.sink;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.olap.StarRocksDataSourceProperties;
import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.dict.flink.cdc.FlinkCDCPluginMapping;
import cn.sliew.scaleph.plugin.flink.cdc.FlinkCDCPipilineConnectorPlugin;
import cn.sliew.scaleph.plugin.flink.cdc.connectors.CommonProperties;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.framework.resource.ResourceProperties;
import cn.sliew.scaleph.plugin.framework.resource.ResourceProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.sliew.scaleph.plugin.flink.cdc.connectors.mysql.source.MySQLSourceProperties.PASSWORD;
import static cn.sliew.scaleph.plugin.flink.cdc.connectors.mysql.source.MySQLSourceProperties.USERNAME;
import static cn.sliew.scaleph.plugin.flink.cdc.connectors.starrocks.sink.StarRocksSinkProperties.*;

@AutoService(FlinkCDCPipilineConnectorPlugin.class)
public class StarRocksSinkPlugin extends FlinkCDCPipilineConnectorPlugin {

    public StarRocksSinkPlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "starrocks",
                StarRocksSinkPlugin.class.getName());
        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(CommonProperties.NAME);
        props.add(CommonProperties.TYPE);
        props.add(SINK_LABEL_PREFIX);
        props.add(SINK_CONNECT_TIMEOUT_MS);
        props.add(SINK_WAIT_FOR_CONTINUE_TIMEOUT_MS);
        props.add(SINK_BUFFER_FLUSH_MAX_BYTES);
        props.add(SINK_BUFFER_FLUSH_INTERVAL_MS);
        props.add(SINK_SCAN_FREQUENCY_MS);
        props.add(SINK_IO_THREAD_COUNT);
        props.add(SINK_AT_LEAST_ONCE_USE_TRANSACTION_STREAM_LOAD);
        props.add(SINK_PROPERTIES);
        props.add(TABLE_CREATE_NUM_BUCKETS);
        props.add(TABLE_SCHEMA_CHANGE_TIMEOUT);
        props.add(TABLE_CREATE_PROPERTIES);
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
        StarRocksDataSourceProperties props = (StarRocksDataSourceProperties) dataSourceInfo.getProps();

        conf.putPOJO(JDBC_URL.getName(), props.getNodeUrls());
        conf.putPOJO(LOAD_URL.getName(), props.getNodeUrls());
        conf.putPOJO(USERNAME.getName(), props.getUsername());
        conf.putPOJO(PASSWORD.getName(), props.getPassword());
        return conf;
    }

    @Override
    protected FlinkCDCPluginMapping getPluginMapping() {
        return FlinkCDCPluginMapping.SINK_STARROCKS;
    }
}

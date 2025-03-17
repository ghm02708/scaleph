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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.jdbc.source;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.jdbc.JdbcDataSourceProperties;
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

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.jdbc.JdbcProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.jdbc.source.JdbcSourceProperties.*;

@AutoService(SeaTunnelConnectorPlugin.class)
public class JdbcSourcePlugin extends SeaTunnelConnectorPlugin {

    public JdbcSourcePlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Jdbc Source Plugin, input records from jdbc connection.",
                JdbcSourcePlugin.class.getName());

        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(CONNECTION_CHECK_TIMEOUT_SEC);
        props.add(COMPATIBLE_MODE);
        props.add(QUERY);
        props.add(TABLE_PATH);
        props.add(TABLE_LIST);
        props.add(WHERE_CONDITION);
        props.add(PARTITION_COLUMN);
        props.add(PARTITION_UPPER_BOUND);
        props.add(PARTITION_LOWER_BOUND);
        props.add(PARTITION_NUM);
        props.add(SPLIT_SIZE);
        props.add(SPLIT_EVEN_DISTRIBUTION_FACTOR_LOWER_BOUND);
        props.add(SPLIT_EVEN_DISTRIBUTION_FACTOR_UPPER_BOUND);
        props.add(SPLIT_SAMPLE_SHARDING_THRESHOLD);
        props.add(SPLIT_INVERSE_SAMPLING_RATE);
        props.add(FETCH_SIZE);
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
        JdbcDataSourceProperties props = (JdbcDataSourceProperties) dataSourceInfo.getProps();

        conf.put(URL.getName(), props.getUrl());
        conf.putPOJO(DRIVER.getName(), props.getDriverClassName());
        conf.putPOJO(USER.getName(), props.getUser());
        conf.putPOJO(PASSWORD.getName(), props.getPassword());
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SOURCE_JDBC;
    }

}

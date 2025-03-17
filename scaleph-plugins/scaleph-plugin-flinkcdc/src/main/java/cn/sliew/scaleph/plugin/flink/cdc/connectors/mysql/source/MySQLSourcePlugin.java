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

package cn.sliew.scaleph.plugin.flink.cdc.connectors.mysql.source;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.jdbc.MySQLDataSourceProperties;
import cn.sliew.milky.common.exception.Rethrower;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.sliew.scaleph.plugin.flink.cdc.connectors.mysql.source.MySQLSourceProperties.*;

@AutoService(FlinkCDCPipilineConnectorPlugin.class)
public class MySQLSourcePlugin extends FlinkCDCPipilineConnectorPlugin {

    public MySQLSourcePlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "mysql",
                MySQLSourcePlugin.class.getName());
        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(CommonProperties.NAME);
        props.add(CommonProperties.TYPE);
        props.add(TABLES);
        props.add(SCHEMA_CHANGE_ENABLED);
        props.add(SERVER_ID);
        props.add(SCAN_INCREMENTAL_CLOSE_IDLE_READER_ENABLED);
        props.add(SCAN_INCREMENTAL_SNAPSHOT_CHUNK_SIZE);
        props.add(SCAN_SNAPSHOT_FETCH_SIZE);
        props.add(SCAN_STARTUP_MODE);
        props.add(SCAN_STARTUP_SPECIFIC_OFFSET_FILE);
        props.add(SCAN_STARTUP_SPECIFIC_OFFSET_POS);
        props.add(SCAN_STARTUP_SPECIFIC_OFFSET_GTID_SET);
        props.add(SCAN_STARTUP_SPECIFIC_OFFSET_SKIP_EVENTS);
        props.add(SCAN_STARTUP_SPECIFIC_OFFSET_SKIP_ROWS);
        props.add(CONNECT_TIMEOUT);
        props.add(CONNECT_MAX_RETRIES);
        props.add(CONNECT_POOL_SIZE);
        props.add(HEARTBEAT_INTERVAL);
        props.add(JDBC_PROPERTIES);
        props.add(DEBEZIUM);
        this.supportedProperties = Collections.unmodifiableList(props);
    }

    @Override
    public List<ResourceProperty> getRequiredResources() {
        return Collections.singletonList(ResourceProperties.DATASOURCE_RESOURCE);
    }

    @Override
    public ObjectNode createConf() {
        try {
            ObjectNode conf = super.createConf();
            JsonNode jsonNode = properties.get(ResourceProperties.DATASOURCE);
            DataSourceInfo dataSourceInfo = JacksonUtil.toObject(jsonNode, DataSourceInfo.class);
            MySQLDataSourceProperties props = (MySQLDataSourceProperties) dataSourceInfo.getProps();

            URI url = new URI(props.getUrl().replace("jdbc:", ""));
            conf.putPOJO(HOSTNAME.getName(), url.getHost());
            conf.putPOJO(PORT.getName(), url.getPort());
            conf.putPOJO(USERNAME.getName(), props.getUser());
            conf.putPOJO(PASSWORD.getName(), props.getPassword());
            return conf;
        } catch (URISyntaxException e) {
            Rethrower.throwAs(e);
            return null;
        }
    }

    @Override
    protected FlinkCDCPluginMapping getPluginMapping() {
        return FlinkCDCPluginMapping.SOURCE_MYSQL;
    }
}

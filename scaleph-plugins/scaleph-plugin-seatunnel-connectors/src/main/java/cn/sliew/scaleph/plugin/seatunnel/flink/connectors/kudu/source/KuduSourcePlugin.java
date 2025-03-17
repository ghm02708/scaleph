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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.kudu.source;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.olap.KuduDataSourceProperties;
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

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.kudu.KuduProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.kudu.source.KuduSourceProperties.*;

@AutoService(SeaTunnelConnectorPlugin.class)
public class KuduSourcePlugin extends SeaTunnelConnectorPlugin {

    public KuduSourcePlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Used to read data from Kudu.",
                KuduSourcePlugin.class.getName());
        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(ENABLE_KERBEROS);
        props.add(KERBEROS_PRINCIPAL);
        props.add(KERBEROS_KEYTAB);
        props.add(KERBEROS_KRB5CONF);
        props.add(CLIENT_WORKER_COUNT);
        props.add(CLIENT_DEFAULT_OPERATION_TIMEOUT_MS);
        props.add(CLIENT_DEFAULT_ADMIN_OPERATION_TIMEOUT_MS);
        props.add(TABLE_NAME);
        props.add(SCAN_TOKEN_QUERY_TIMEOUT);
        props.add(SCAN_TOKEN_BATCH_SIZE_BYTES);
        props.add(SCHEMA);
        props.add(TABLE_LIST);
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
        KuduDataSourceProperties props = (KuduDataSourceProperties) dataSourceInfo.getProps();

        conf.putPOJO(KUDU_MASTER.getName(), props.getMasters());
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SOURCE_KUDU;
    }
}

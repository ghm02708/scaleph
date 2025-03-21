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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hive.source;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.olap.HiveDataSourceProperties;
import cn.sliew.milky.common.util.JacksonUtil;
import cn.sliew.scaleph.common.dict.seatunnel.SeaTunnelPluginMapping;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.seatunnel.flink.SeaTunnelConnectorPlugin;
import cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.FileProperties;
import cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.FileSourceProperties;
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

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.hive.HiveProperties.*;

@AutoService(SeaTunnelConnectorPlugin.class)
public class HiveSourcePlugin extends SeaTunnelConnectorPlugin {

    public HiveSourcePlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Hive Source Plugin.",
                HiveSourcePlugin.class.getName());

        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(TABLE_NAME);
        props.add(FileProperties.COMPRESS_CODEC);
        props.add(FileSourceProperties.READ_COLUMNS);
        props.add(FileSourceProperties.READ_PARTITIONS);
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
        HiveDataSourceProperties props = (HiveDataSourceProperties) dataSourceInfo.getProps();

        conf.putPOJO(METASTORE_URI.getName(), props.getMetastoreUri());
        if (StringUtils.hasText(props.getHdfsSitePath())) {
            conf.putPOJO(HDFS_SITE_PATH.getName(), props.getHdfsSitePath());
        }
        if (StringUtils.hasText(props.getKerberosKeytabPath())) {
            conf.putPOJO(KERBEROS_KEYTAB_PATH.getName(), props.getKerberosKeytabPath());
        }
        if (StringUtils.hasText(props.getKerberosPrincipal())) {
            conf.putPOJO(KERBEROS_PRINCIPAL.getName(), props.getKerberosPrincipal());
        }
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SOURCE_HIVE;
    }

}

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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.hdfs.source;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.file.HDFSDataSourceProperties;
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

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.FileProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.FileSourceProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.hdfs.HDFSProperties.*;

@AutoService(SeaTunnelConnectorPlugin.class)
public class HDFSFileSourcePlugin extends SeaTunnelConnectorPlugin {

    public HDFSFileSourcePlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Read data from HDFS",
                HDFSFileSourcePlugin.class.getName());

        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(PATH);
        props.add(SKIP_HEADER_ROW_NUMBER);
        props.add(SHEET_NAME);
        props.add(XML_ROW_TAG);
        props.add(XML_USE_ATTR_FORMAT);
        props.add(COMPRESS_CODEC);
        props.add(ENCODING);
        props.add(FILE_FILTER_PATTERN);
        props.add(FILE_FORMAT_TYPE);
        props.add(READ_COLUMNS);
        props.add(SCHEMA);
        props.add(FIELD_DELIMITER);
        props.add(PARSE_PARTITION_FROM_PATH);
        props.add(DATE_FORMAT);
        props.add(TIME_FORMAT);
        props.add(DATETIME_FORMAT);
        props.add(REMOTE_USER);
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
        HDFSDataSourceProperties props = (HDFSDataSourceProperties) dataSourceInfo.getProps();

        conf.put(FS_DEFAULT_FS.getName(), props.getFsDefaultFS());
        if (StringUtils.hasText(props.getHdfsSitePath())) {
            conf.put(HDFS_SITE_PATH.getName(), props.getHdfsSitePath());
        }
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SOURCE_HDFS_FILE;
    }

}

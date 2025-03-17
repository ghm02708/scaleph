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

package cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.sftp.sink;

import cn.sliew.carp.module.datasource.modal.DataSourceInfo;
import cn.sliew.carp.module.datasource.modal.file.SftpDataSourceProperties;
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

import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.FileProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.FileSinkProperties.*;
import static cn.sliew.scaleph.plugin.seatunnel.flink.connectors.file.sftp.SftpFileProperties.*;

@AutoService(SeaTunnelConnectorPlugin.class)
public class SftpFileSinkPlugin extends SeaTunnelConnectorPlugin {

    public SftpFileSinkPlugin() {
        this.pluginInfo = new PluginInfo(getIdentity(),
                "Sftp file sink connector",
                SftpFileSinkPlugin.class.getName());

        final List<PropertyDescriptor> props = new ArrayList<>();
        props.add(PATH);
        props.add(SHEET_NAME);
        props.add(XML_ROOT_TAG);
        props.add(XML_ROW_TAG);
        props.add(XML_USE_ATTR_FORMAT);
        props.add(COMPRESS_CODEC);
        props.add(ENCODING);
        props.add(TMP_PATH);
        props.add(FILE_FORMAT_TYPE);
        props.add(CUSTOM_FILENAME);
        props.add(FILE_NAME_EXPRESSION);
        props.add(FILENAME_TIME_FORMAT);
        props.add(FIELD_DELIMITER);
        props.add(ROW_DELIMITER);
        props.add(HAVE_PARTITION);
        props.add(PARTITION_BY);
        props.add(PARTITION_DIR_EXPRESSION);
        props.add(IS_PARTITION_FIELD_WRITE_IN_FILE);
        props.add(SINK_COLUMNS);
        props.add(IS_ENABLE_TRANSACTION);
        props.add(BATCH_SIZE);
        props.add(MAX_ROWS_IN_MEMORY);
        props.add(ENABLE_HEADER_WRITE);
        props.add(CommonProperties.PARALLELISM);
        props.add(CommonProperties.SOURCE_TABLE_NAME);
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
        SftpDataSourceProperties props = (SftpDataSourceProperties) dataSourceInfo.getProps();

        conf.put(HOST.getName(), props.getHost());
        conf.putPOJO(PORT.getName(), props.getPort());
        conf.putPOJO(USER.getName(), props.getUsername());
        conf.putPOJO(PASSWORD.getName(), props.getPassword());
        return conf;
    }

    @Override
    protected SeaTunnelPluginMapping getPluginMapping() {
        return SeaTunnelPluginMapping.SINK_SFTP_FILE;
    }
}

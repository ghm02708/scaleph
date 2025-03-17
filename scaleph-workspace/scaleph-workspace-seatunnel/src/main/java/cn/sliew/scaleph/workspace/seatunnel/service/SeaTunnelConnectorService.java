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

package cn.sliew.scaleph.workspace.seatunnel.service;

import cn.sliew.scaleph.common.dict.seatunnel.SeaTunnelPluginName;
import cn.sliew.scaleph.common.dict.seatunnel.SeaTunnelPluginType;
import cn.sliew.scaleph.plugin.framework.core.PluginInfo;
import cn.sliew.scaleph.plugin.framework.exception.PluginException;
import cn.sliew.scaleph.plugin.framework.property.PropertyDescriptor;
import cn.sliew.scaleph.plugin.seatunnel.flink.SeaTunnelConnectorPlugin;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface SeaTunnelConnectorService {

    List<PropertyDescriptor> getSupportedEnvProperties();

    Set<SeaTunnelConnectorPlugin> getAvailableConnectors(SeaTunnelPluginType stepType);

    SeaTunnelConnectorPlugin getConnector(PluginInfo pluginInfo) throws PluginException;

    SeaTunnelConnectorPlugin getConnector(SeaTunnelPluginType type, SeaTunnelPluginName name) throws PluginException;

    SeaTunnelConnectorPlugin newConnector(String name, Properties properties);

}

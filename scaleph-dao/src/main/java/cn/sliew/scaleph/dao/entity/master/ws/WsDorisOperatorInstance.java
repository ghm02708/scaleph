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

package cn.sliew.scaleph.dao.entity.master.ws;

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import cn.sliew.scaleph.common.dict.common.YesOrNo;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * doris instance
 */
@Data
@TableName("ws_doris_operator_instance")
public class WsDorisOperatorInstance extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    @TableField("project_id")
    private Long projectId;

    @TableField("cluster_credential_id")
    private Long clusterCredentialId;

    @TableField("`name`")
    private String name;

    @TableField("instance_id")
    private String instanceId;

    @TableField("namespace")
    private String namespace;

    @TableField("`admin`")
    private String admin;

    @TableField("fe_spec")
    private String feSpec;

    @TableField("be_spec")
    private String beSpec;

    @TableField("cn_spec")
    private String cnSpec;

    @TableField("broker_spec")
    private String brokerSpec;

    @TableField("deployed")
    private YesOrNo deployed;

    @TableField(value = "fe_status", updateStrategy = FieldStrategy.IGNORED)
    private String feStatus;

    @TableField(value = "be_status", updateStrategy = FieldStrategy.IGNORED)
    private String beStatus;

    @TableField(value = "cn_status", updateStrategy = FieldStrategy.IGNORED)
    private String cnStatus;

    @TableField(value = "broker_status", updateStrategy = FieldStrategy.IGNORED)
    private String brokerStatus;

    @TableField("remark")
    private String remark;
}

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

package cn.sliew.scaleph.mail.service.vo;

import cn.sliew.scaleph.common.constant.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 系统邮箱配置类
 *
 * @author gleiyu
 */
@Data
@Schema(name = "邮箱配置信息", description = "邮箱配置信息")
public class EmailConfigVO {

    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String host;

    @NotNull
    private Integer port;

    private String encoding;

    public String getEncoding() {
        return StringUtils.hasText(this.encoding) ? this.encoding : Constants.DEFAULT_CHARSET;
    }
}

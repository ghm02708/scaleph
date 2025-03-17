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

package cn.sliew.scaleph.system.service.impl;

import cn.sliew.carp.framework.common.dict.DictInstance;
import cn.sliew.scaleph.common.dict.DictType;
import cn.sliew.scaleph.system.service.SysDictService;
import cn.sliew.scaleph.system.service.SysDictTypeService;
import cn.sliew.scaleph.system.service.dto.SysDictDTO;
import cn.sliew.scaleph.system.service.param.SysDictParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author liyu
 * @since 2021-07-24
 */
@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    @Override
    public List<DictInstance> selectByType(DictType type) {
        return EnumUtils.getEnumList(type.getInstanceClass());
    }

    @Override
    public Page<SysDictDTO> listByPage(SysDictParam param) {
        List<SysDictDTO> dictInstances;
        if (param.getDictType() != null) {
            dictInstances = convert(param.getDictType());
        } else {
            dictInstances = sysDictTypeService.selectAll().stream()
                    .flatMap(dictType -> convert(dictType).stream())
                    .collect(Collectors.toList());
        }
        List<SysDictDTO> filteredDictInstances = dictInstances.stream().filter(dictInstance -> {
            if (StringUtils.hasText(param.getValue())) {
                return dictInstance.getValue().contains(param.getValue());
            }
            return true;
        }).filter(dictInstance -> {
            if (StringUtils.hasText(param.getLabel())) {
                return dictInstance.getLabel().contains(param.getLabel());
            }
            return true;
        }).collect(Collectors.toList());

        Page<SysDictDTO> result = new Page<>(param.getCurrent(), param.getPageSize(), filteredDictInstances.size());
        Long from = (param.getCurrent() - 1) * param.getPageSize();
        Long to = from + param.getPageSize();
        if (from >= filteredDictInstances.size()) {
            result.setRecords(Collections.emptyList());
            return result;
        }

        result.setRecords(filteredDictInstances.subList(from.intValue(), to.intValue() < filteredDictInstances.size() ? to.intValue() : filteredDictInstances.size() - 1));
        return result;
    }

    private List<SysDictDTO> convert(DictType type) {
        return selectByType(type).stream()
                .map(dictInstance -> convert(type, dictInstance))
                .collect(Collectors.toList());
    }

    private SysDictDTO convert(DictType type, DictInstance instance) {
        SysDictDTO dictDTO = new SysDictDTO();
        dictDTO.setDictType(type);
        dictDTO.setValue(instance.getValue());
        dictDTO.setLabel(instance.getLabel());
        dictDTO.setValid(instance.isDisabled() == false);
        dictDTO.setRemark(instance.getRemark());
        return dictDTO;
    }
}

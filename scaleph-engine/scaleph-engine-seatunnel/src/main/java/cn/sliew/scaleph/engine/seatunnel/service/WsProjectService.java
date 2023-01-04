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

package cn.sliew.scaleph.engine.seatunnel.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.sliew.scaleph.common.exception.ScalephException;
import cn.sliew.scaleph.engine.seatunnel.service.dto.WsProjectDTO;
import cn.sliew.scaleph.engine.seatunnel.service.param.WsProjectParam;
import cn.sliew.scaleph.system.service.vo.DictVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface WsProjectService {

    Page<WsProjectDTO> listByPage(WsProjectParam param);

    List<DictVO> listAll();

    WsProjectDTO selectOne(Serializable id);

    int insert(WsProjectDTO dto);

    int update(WsProjectDTO dto);

    int deleteById(Long id) throws ScalephException;

    int deleteBatch(Map<Integer, Long> map) throws ScalephException;

    Long totalCnt();

}
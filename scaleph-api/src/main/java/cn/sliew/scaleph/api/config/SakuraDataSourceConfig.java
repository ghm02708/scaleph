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

package cn.sliew.scaleph.api.config;

import cn.sliew.carp.framework.mybatis.config.CarpMybatisConfig;
import cn.sliew.scaleph.dao.DataSourceConstants;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = DataSourceConstants.SAKURA_MAPPER_PACKAGE, sqlSessionFactoryRef = DataSourceConstants.SAKURA_SQL_SESSION_FACTORY)
public class SakuraDataSourceConfig {

    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;
    @Autowired
    private CarpMybatisConfig.CarpMetaHandler carpMetaHandler;

    @Bean(DataSourceConstants.SAKURA_DATA_SOURCE_FACTORY)
    @ConfigurationProperties(prefix = "spring.datasource.sakura")
    public DataSource logDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .build();
    }

    @Bean(DataSourceConstants.SAKURA_TRANSACTION_MANAGER_FACTORY)
    public DataSourceTransactionManager logTransactionManager() {
        return new DataSourceTransactionManager(logDataSource());
    }

    @Bean(DataSourceConstants.SAKURA_SQL_SESSION_FACTORY)
    public SqlSessionFactory logSqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        GlobalConfig globalConfig = GlobalConfigUtils.defaults();
        globalConfig.setMetaObjectHandler(carpMetaHandler);
        MybatisPlusProperties props = new MybatisPlusProperties();
        props.setMapperLocations(new String[]{DataSourceConstants.SAKURA_MAPPER_XML_PATH});
        factoryBean.setMapperLocations(props.resolveMapperLocations());

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultEnumTypeHandler(MybatisEnumTypeHandler.class);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Slf4jImpl.class);
        factoryBean.setConfiguration(configuration);
        factoryBean.setGlobalConfig(globalConfig);
        factoryBean.setDataSource(logDataSource());
        factoryBean.setTypeAliasesPackage(DataSourceConstants.SAKURA_ENTITY_PACKAGE);
        factoryBean.setPlugins(mybatisPlusInterceptor);
        return factoryBean.getObject();
    }

}

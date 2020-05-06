package com.shch.demo.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.shch.demo.GlobalPropertySource;

@Configuration
@MapperScan(value="com.shch.demo.inf.enportal.mapper", sqlSessionFactoryRef="msSqlSessionFactory")
@EnableTransactionManagement
public class MssqlDatabaseConfig {

    @Autowired
    GlobalPropertySource globalPropertySource;
    
    @Bean(name = "msDataSource")
    public DataSource msDataSource() {
        return DataSourceBuilder
            .create()
            .url(globalPropertySource.getMsurl())
            .driverClassName(globalPropertySource.getMsdriverClassName())
            .username(globalPropertySource.getMsusername())
            .password(globalPropertySource.getMspassword())
            .build();
    }
    
    @Bean(name = "msSqlSessionFactory")
    public SqlSessionFactory msSqlSessionFactory(@Qualifier("msDataSource") DataSource msDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(msDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:shch/inf/enportal/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean(name = "msSqlSessionTemplate")
    public SqlSessionTemplate msSqlSessionTemplate(SqlSessionFactory msSqlSessionFactory) throws Exception {
      return new SqlSessionTemplate(msSqlSessionFactory);
    }

}
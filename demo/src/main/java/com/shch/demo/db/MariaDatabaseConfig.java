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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.shch.demo.GlobalPropertySource;

@Configuration
@MapperScan(value="com.shch.demo.*.mapper", sqlSessionFactoryRef="mariaSqlSessionFactory")
@EnableTransactionManagement
public class MariaDatabaseConfig {

    @Autowired
    GlobalPropertySource globalPropertySource;
    
    @Bean(name = "mariaDataSource")
    @Primary
    public DataSource mariaDataSource() {
        return DataSourceBuilder
            .create()
            .url(globalPropertySource.getUrl())
            .driverClassName(globalPropertySource.getDriverClassName())
            .username(globalPropertySource.getUsername())
            .password(globalPropertySource.getPassword())
            .build();
    }
    
    @Bean(name = "mariaSqlSessionFactory")
    @Primary
    public SqlSessionFactory mariaSqlSessionFactory(@Qualifier("mariaDataSource") DataSource mariaDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mariaDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:shch/**/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean(name = "mariaSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate mariaSqlSessionTemplate(SqlSessionFactory mariaSqlSessionFactory) throws Exception {
      return new SqlSessionTemplate(mariaSqlSessionFactory);
    }

}
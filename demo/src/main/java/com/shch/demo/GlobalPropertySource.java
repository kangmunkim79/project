  
package com.shch.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
    @PropertySource( value = "classpath:config.properties" )
})
public class GlobalPropertySource {
    
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String username;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    public String getDriverClassName() {
        return driverClassName;
    }
 
    public String getUrl() {
        return url;
    }
 
    public String getUsername() {
        return username;
    }
 
    public String getPassword() {
        return password;
    }
    
    @Value("${spring.maria.datasource.driverClassName}")
    private String mariadriverClassName;
    
    @Value("${spring.maria.datasource.url}")
    private String mariaurl;
    
    @Value("${spring.maria.datasource.username}")
    private String mariausername;
    
    @Value("${spring.maria.datasource.password}")
    private String mariapassword;
    
    public String getMariadriverClassName() {
        return mariadriverClassName;
    }
 
    public String getMariaurl() {
        return mariaurl;
    }
 
    public String getMariausername() {
        return mariausername;
    }
 
    public String getMariapassword() {
        return mariapassword;
    }

    @Value("${spring.ms.datasource.driverClassName}")
    private String msdriverClassName;
    
    @Value("${spring.ms.datasource.url}")
    private String msurl;
    
    @Value("${spring.ms.datasource.username}")
    private String msusername;
    
    @Value("${spring.ms.datasource.password}")
    private String mspassword;

	public String getMsdriverClassName() {
		return msdriverClassName;
	}

	public String getMsurl() {
		return msurl;
	}

	public String getMsusername() {
		return msusername;
	}

	public String getMspassword() {
		return mspassword;
	}

    @Value("${spring.file.upload.url}")
    private String filepath;
    
    @Value("${spring.excel.file.url}")
    private String exfilepath;
    
	public String getFilepath() {
		return filepath;
	}

	public String getExfilepath() {
		return exfilepath;
	}

}
package org.mycompany;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	
	@Value("${spring.datasource.driver-class-name}")
	public String dbDriver;
	
	@Value("${spring.datasource.url}")
	public String dbUrl;
	
	@Value("${spring.datasource.username}")
	public String dbUserName;
	
	@Value("${spring.datasource.password}")
	public String dbPassword;
	
    
    @Bean("myDataSource")
    public DataSource getDataSource() {		
		  DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		  dataSourceBuilder.driverClassName(dbDriver);
		  dataSourceBuilder.url(dbUrl);
		  dataSourceBuilder.username(dbUserName);
		  dataSourceBuilder.password(dbPassword); 
		  return dataSourceBuilder.build();
    }
}
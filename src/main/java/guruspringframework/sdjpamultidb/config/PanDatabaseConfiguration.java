package guruspringframework.sdjpamultidb.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import guruspringframework.sdjpamultidb.domain.pan.CreditCardPan;

@Configuration
@EnableJpaRepositories(basePackages = "guruspringframework.sdjpamultidb.repositories.pan",
entityManagerFactoryRef = "panEntityManagerFactory",
transactionManagerRef = "panTransactionManager")
public class PanDatabaseConfiguration {
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.pan.datasource")
	public DataSourceProperties panDatasourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	@ConfigurationProperties("spring.pan.datasource.hikari") //setuje hikari vrednost u datasource bean kad se
	                                                          //instancira
	public DataSource panDatasource(@Qualifier("panDatasourceProperties")DataSourceProperties panDatasourceProperties) {
		return panDatasourceProperties
				.initializeDataSourceBuilder()
				.type(HikariDataSource.class)
				.build();
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean panEntityManagerFactory(@Qualifier("panDatasource")DataSource panDatasource,
			EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(panDatasource)
				.packages(CreditCardPan.class)
				.persistenceUnit("pan")
				.properties(Map.of("hibernate.hbm2ddl.auto", "validate", 
						           "hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"))
				.build();
	}
	
	@Bean
	@Primary
	public PlatformTransactionManager panTransactionManager(@Qualifier("panEntityManagerFactory")LocalContainerEntityManagerFactoryBean panEntityManagerFactory) {
		 return new JpaTransactionManager(panEntityManagerFactory.getObject());
	}


}

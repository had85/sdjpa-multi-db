package guruspringframework.sdjpamultidb.config;

import javax.sql.DataSource;

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

import guruspringframework.sdjpamultidb.domain.pan.CreditCardPAN;

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
	public DataSource panDatasource(DataSourceProperties panDatasourceProperties) {
		return panDatasourceProperties
				.initializeDataSourceBuilder()
				.type(HikariDataSource.class)
				.build();
	}
	
	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean panEntityManagerFactory(DataSource panDatasource,
			EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(panDatasource)
				.packages(CreditCardPAN.class)
				.persistenceUnit("pan")
				.build();
	}
	
	@Bean
	@Primary
	public PlatformTransactionManager panTransactionManager(LocalContainerEntityManagerFactoryBean panEntityManagerFactory) {
		 return new JpaTransactionManager(panEntityManagerFactory.getObject());
	}


}

package guruspringframework.sdjpamultidb.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import guruspringframework.sdjpamultidb.domain.cardholder.CreditCardHolder;

@Configuration
@EnableJpaRepositories(basePackages = "guruspringframework.sdjpamultidb.repositories.cardholder", 
entityManagerFactoryRef = "cardholderEntityManagerFactory",
transactionManagerRef = "cardholderTransactionManager")
public class CardholderDatabaseConfiguration {
	
	@Bean
	@ConfigurationProperties("spring.cardholder.datasource")
	public DataSourceProperties cardholderDatasourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource cardholderDatasource(DataSourceProperties cardholderDatasourceProperties) {
		return cardholderDatasourceProperties
				.initializeDataSourceBuilder()
				.type(HikariDataSource.class)
				.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean cardholderEntityManagerFactory(DataSource cardholderDatasource,
			EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(cardholderDatasource)
				.packages(CreditCardHolder.class)
				.persistenceUnit("cardholder")
				.build();
	}
	
	@Bean
	public PlatformTransactionManager cardholderTransactionManager(LocalContainerEntityManagerFactoryBean cardholderEntityManagerFactory) {
		 return new JpaTransactionManager(cardholderEntityManagerFactory.getObject());
	}
	
}

package guruspringframework.sdjpamultidb.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
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
	@ConfigurationProperties("spring.cardholder.datasource.hikari") //setuje hikari vrednost u datasource bean kad se
	                                                          //instancira
	public DataSource cardholderDatasource(@Qualifier("cardholderDatasourceProperties")DataSourceProperties cardholderDatasourceProperties) {
		
		return cardholderDatasourceProperties
						.initializeDataSourceBuilder()
						.type(HikariDataSource.class)
						.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean cardholderEntityManagerFactory(@Qualifier("cardholderDatasource")DataSource cardholderDatasource,
			EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(cardholderDatasource)
				.packages(CreditCardHolder.class)
				.persistenceUnit("cardholder")
				.properties(Map.of("hibernate.hbm2ddl.auto", "validate", 
						           "hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"))
				.build();
	}
	
	@Bean
	public PlatformTransactionManager cardholderTransactionManager(@Qualifier("cardholderEntityManagerFactory")LocalContainerEntityManagerFactoryBean cardholderEntityManagerFactory) {
		 return new JpaTransactionManager(cardholderEntityManagerFactory.getObject());
	}
	
}

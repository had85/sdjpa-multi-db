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

import guruspringframework.sdjpamultidb.domain.creditcard.CreditCard;


@Configuration
@EnableJpaRepositories(basePackages = "guruspringframework.sdjpamultidb.repositories.creditcard", 
entityManagerFactoryRef = "cardEntityManagerFactory",
transactionManagerRef = "cardTransactionManager") //trazi se ime bean sto je ime metode u konfigu
//ovaj konfig sa ovim datasource-om kupi samo credit card repositorije
public class CardDatabaseConfiguration {
	
	@Bean
	@ConfigurationProperties("spring.card.datasource")
	public DataSourceProperties cardDatasourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties("spring.card.datasource.hikari") //setuje hikari vrednost u datasource bean kad se
	                                                          //instancira
	public DataSource cardDatasource(@Qualifier("cardDatasourceProperties") DataSourceProperties cardDatasourceProperties) {
		return cardDatasourceProperties
				.initializeDataSourceBuilder()
				.type(HikariDataSource.class)
				.build();
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean cardEntityManagerFactory(@Qualifier("cardDatasource")DataSource cardDatasource,
			EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(cardDatasource)
				.packages(CreditCard.class)
				.persistenceUnit("card")
				.properties(Map.of("hibernate.hbm2ddl.auto", "validate",
						           "hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"))
				.build();
	}
	
	@Bean
	public PlatformTransactionManager cardTransactionManager(@Qualifier("cardEntityManagerFactory")LocalContainerEntityManagerFactoryBean cardEntityManagerFactory) {
		 return new JpaTransactionManager(cardEntityManagerFactory.getObject());
	}

}

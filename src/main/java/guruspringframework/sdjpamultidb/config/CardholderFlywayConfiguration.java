package guruspringframework.sdjpamultidb.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration //trazi se ime bean sto je ime metode u konfigu
public class CardholderFlywayConfiguration {
	
	@Bean
	@ConfigurationProperties("spring.cardholder.flyway")
	public DataSourceProperties cardholderFlywayDatasourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(initMethod = "migrate") //poziva se migrate metoda u Flyway objektu kad se instancira
	public Flyway cardholderFlyway(@Qualifier("cardholderFlywayDatasourceProperties")DataSourceProperties cardholderFlywayDatasourceProperties) {		
		return Flyway.configure()
				.dataSource(cardholderFlywayDatasourceProperties.getUrl(),
						cardholderFlywayDatasourceProperties.getUsername(),
						cardholderFlywayDatasourceProperties.getPassword())
				.locations("classpath:/db/migration/cardholder")
				.load();
	}

}

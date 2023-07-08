package guruspringframework.sdjpamultidb.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration //trazi se ime bean sto je ime metode u konfigu
public class CardFlywayConfiguration {
	
	@Bean
	@ConfigurationProperties("spring.card.flyway")
	public DataSourceProperties cardFlywayDatasourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(initMethod = "migrate") //poziva se migrate metoda u Flyway objektu kad se instancira
	                              //instanciranje ovog bean-a izvrsava flyway skripte i uskladjuje bazu sa kodom
	public Flyway cardFlyway(@Qualifier("cardFlywayDatasourceProperties")DataSourceProperties cardFlywayDatasourceProperties) {		
		return Flyway.configure()
				.dataSource(cardFlywayDatasourceProperties.getUrl(),
						cardFlywayDatasourceProperties.getUsername(),
						cardFlywayDatasourceProperties.getPassword())
				.locations("classpath:/db/migration/card")
				.load();
	}

}

package guruspringframework.sdjpamultidb.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration //trazi se ime bean sto je ime metode u konfigu
public class PanFlywayConfiguration {
	
	@Bean
	@ConfigurationProperties("spring.pan.flyway")
	public DataSourceProperties panFlywayDatasourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(initMethod = "migrate") //poziva se migrate metoda u Flyway objektu kad se instancira
	public Flyway panFlyway(@Qualifier("panFlywayDatasourceProperties")DataSourceProperties panFlywayDatasourceProperties) {		
		return Flyway.configure()
				.dataSource(panFlywayDatasourceProperties.getUrl(),
						panFlywayDatasourceProperties.getUsername(),
						panFlywayDatasourceProperties.getPassword())
				.locations("classpath:/db/migration/pan")
				.load();
	}

}

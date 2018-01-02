package cz.vhromada.catalog;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * A class represents Spring configuration.
 *
 * @author Vladimir Hromada
 */
@Configuration
@Import(CatalogConfiguration.class)
public class SwingConfiguration {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:.\\db\\Catalog;CIPHER=AES")
                .username("Lavina")
                .password("stargate stargate")
                .build();
    }

}

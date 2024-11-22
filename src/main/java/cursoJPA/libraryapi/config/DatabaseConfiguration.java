package cursoJPA.libraryapi.config;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String passoword;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;


    //essa conexao n e para usar em producao pois n aguenta mts conexoes, nao faz pool de conexoes
    // @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(passoword);
        ds.setDriverClassName(driver);
        return ds;
    }

    @Bean
    public DataSource hikariDataSource(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(passoword);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(1); //tamanho inicial de conexoes
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); //tempo de vida da conexao
        config.setConnectionTimeout(100000); //tempo maximo tentativa de conexao
        config.setConnectionTestQuery("select 1");

        return new HikariDataSource(config);

    }
}

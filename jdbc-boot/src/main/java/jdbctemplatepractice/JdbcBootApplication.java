/**
 * Project: JDBCTemplate Practice
 * Description: Entry point and primary configuration source for the application. The application is a simple practice
 *              project that implements a repository layer without the assistance of an orm for educational/learning
 *              purposes.
 * Author: Benjamin Soto-Roberts
 * Created: 03/03/2026
 */

package jdbctemplatepractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
public class JdbcBootApplication {

    private static final Logger logger = LoggerFactory.getLogger(JdbcBootApplication.class);



    public static void main(String[] args) {
        SpringApplication.run(JdbcBootApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate) {
        String query = "SELECT count(*) FROM PRODUCTS";
        return args -> logger.error("Hello there are " + jdbcTemplate.queryForObject(query, Long.class) + " accounts");
    }

}


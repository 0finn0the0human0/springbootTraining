/**
 * Project: JDBCTemplate Practice
 * Description: Entry point and primary configuration source for the application. The application is a simple practice
 *              project that implements a repository layer without the assistance of an orm for educational/learning
 *              purposes.
 * Author: Benjamin Soto-Roberts
 * Created: 03/03/2026
 */

package jdbctemplatepractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JdbcBootApplication {




    public static void main(String[] args) {
        SpringApplication.run(JdbcBootApplication.class, args);
    }



}


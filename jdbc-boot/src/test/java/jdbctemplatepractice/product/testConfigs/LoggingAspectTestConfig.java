/**
 * Project: JDBCTemplate Practice
 * Description: Test-only configuration providing simple service and repository beans to trigger LoggingAspect behavior
 *              during integration tests.
 * Author: Benjamin Soto-Roberts
 * Created: 04/08/2026
 */

package jdbctemplatepractice.product.testConfigs;

import jdbctemplatepractice.common.LoggingAspect;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@TestConfiguration
@Import(LoggingAspect.class)
public class LoggingAspectTestConfig {


    /**
     * Minimal service used to exercise service-level logging.
     */
    @Service
    public static class TestLoggingService{

        public String doWork(String input) {
            return "Processed: " + input;
        }

        public void fail() {
            throw new IllegalStateException("Boom1");
        }

    }


    /**
     * Minimal repository used to exercise repository-level logging.
     */
    @Repository
    public static class TestLoggingRepository{

        public String findSomething(String input) {
            return "Found: " + input;
        }

        public void fail() {
            throw new IllegalStateException("Boom2");
        }

    }


}


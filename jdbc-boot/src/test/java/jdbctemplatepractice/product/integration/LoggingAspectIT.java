/**
 * Project: JDBCTemplate Practice
 * Description: Integration tests verifying that the LoggingAspect produces the expected service and repository level
 *              log events for success and error paths. Uses simple testing stereotypes from LoggingAspectTestConfig.
 * Author: Benjamin Soto-Roberts
 * Created: 04/08/2026
 */

package jdbctemplatepractice.product.integration;

import jdbctemplatepractice.product.testConfigs.LoggingAspectTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest()
@Import(LoggingAspectTestConfig.class)
class LoggingAspectIT {

    @Autowired
    private LoggingAspectTestConfig.TestLoggingService service;

    @Autowired
    private LoggingAspectTestConfig.TestLoggingRepository repository;


    /**
     * Verifies that service calls log start/end markers.
     */
    @Test
    void logsServiceStartAndEnd(CapturedOutput output) {
        service.doWork("abc");

        assertThat(output.getOut())
                .contains("[SERVICE START] TestLoggingService.doWork")
                .contains("[SERVICE END] TestLoggingService.doWork");
    }

    /**
     * Verifies that service throws and logs error.
     */
    @Test
    void logsServiceError(CapturedOutput output) {
        assertThrows(IllegalStateException.class, service::fail);

        assertThat(output.getOut())
                .contains("[SERVICE ERROR] TestLoggingService.fail")
                .contains("Boom1");
    }


    /**
     * Verifies that repository calls log start/end markers.
     */
    @Test
    void logsRepoStartAndEnd(CapturedOutput output) {
        repository.findSomething("abc");

        assertThat(output.getOut())
                .contains("[REPO START] TestLoggingRepository.findSomething")
                .contains("[REPO END] TestLoggingRepository.findSomething");
    }

    /**
     * Verifies that repository throws and logs error.
     */
    @Test
    void logRepoError(CapturedOutput output) {
        assertThrows(IllegalStateException.class, repository::fail);

        assertThat(output.getOut())
                .contains("[REPO ERROR] TestLoggingRepository.fail")
                .contains("Boom2");
    }
}


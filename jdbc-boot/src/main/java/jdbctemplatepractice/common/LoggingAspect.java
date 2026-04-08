/**
 * Project: JDBCTemplate Practice
 * Description: Cross-cutting logging aspect for service and repository layers.
 * Author: Benjamin Soto-Roberts
 * Created: 04/03/2026
 */

package jdbctemplatepractice.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final String logStartBody = "[{} START] {} args={}";
    private static final String logEndBody = "[{} END] {} duration={} result={}";
    private static final String logErrorBody = "[{} ERROR] {} duration={} ex={}";


    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut targeting all methods within the product package that also have a Repository Stereotype
     */
    @Pointcut("within(jdbctemplatepractice.product..*) && @within(org.springframework.stereotype.Repository)")
    void repositoryMethods() {}

    /**
     * Pointcut targeting all methods within the product package that also have a Service Stereotype
     */
    @Pointcut("within(jdbctemplatepractice.product..*) && @within(org.springframework.stereotype.Service)")
    void serviceMethods() {}


    /**
     * Around advice for all methods matched by repositoryMethods() pointcut. When a Repository layer method matches the
     * pointcut, Spring AOP intercepts that method call and routes execution through this advice first. This method then
     * delegates to the shared logAround helper to handle entry logging, execution timing, exit logging and exception
     * logging.
     * */
    @Around("repositoryMethods()")
    public Object logRepo(ProceedingJoinPoint pjp) throws Throwable {
        return logAround(pjp, "REPO", false);
    }


    /**
     * Around advice for all methods matched by serviceMethods() pointcut. When a service layer method matches the
     * pointcut, Spring AOP intercepts that method call and routes execution through this advice first. This method then
     * delegates to the shared logAround helper to handle entry logging, execution timing, exit logging and exception
     * logging.
     * */
    @Around("serviceMethods()")
    public Object logService(ProceedingJoinPoint pjp) throws Throwable {
        return logAround(pjp, "SERVICE", true);
    }

    /**
     * Logs around method handles delegation to helper methods for logging events related to pointcuts. Captures duration
     * between logging events, methods called, the layer that the pointcut flagged and arguments.
     * java:S2139 - Logging and rethrowing is intentional in this cross-cutting aspect.
     * java:S2629 - Sonar false positive: SLF4J handles lazy evaluation
     * */
    @SuppressWarnings({"java:S2139", "java:S2629"})
    private Object logAround(ProceedingJoinPoint pjp, String layer, boolean infoLevel) throws Throwable {
        String method = getClassName(pjp.getTarget()) + "." + pjp.getSignature().getName(); // Identifies method
        long start = System.currentTimeMillis(); // Start timer initiated

        log(infoLevel, logStartBody, layer, method, summarizeArgs(pjp.getArgs()));

        try {
            Object result = pjp.proceed();
            long duration = System.currentTimeMillis() - start;
            log(infoLevel, logEndBody, layer, method, duration, summarize(result));
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            log(infoLevel, logErrorBody, layer, method, duration, ex.toString());
            throw ex;
        }
    }

    /**
     * Helper method responsible for logging action.
     * */
    private void log(boolean infoLevel, String message, Object... args) {
        if (infoLevel) logger.info(message, args);
        else logger.debug(message, args);
    }


    /**
     * Helper method unwraps the spring aop proxy to retrieve the actual underlying class name
     * */
    private String getClassName(Object target) {
        return AopUtils.getTargetClass(target).getSimpleName();
    }

    /**
     * Helper method converts result to a short, safe log string (truncates long values)
     * */
    private String summarize(Object result) {
        if (result == null) return "null";
        String s = result.toString();
        return s.length() > 200 ? s.substring(0, 200) + "..." : s;
    }

    /**
     * Formats and summarizes method arguments for logging
     * */
    private String summarizeArgs(Object[] args) {
        if (args == null || args.length == 0) return "[]";
        return summarize(Arrays.toString(args));
    }
}

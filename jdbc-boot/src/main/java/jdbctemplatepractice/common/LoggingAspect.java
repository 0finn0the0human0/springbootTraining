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


    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut targeting all methods in any class ending with Repository under jdbctemplatepractice.product package
     */
    @Pointcut("execution(* jdbctemplatepractice.product..*Repository.*(..))")
    void repositoryMethods() {}

    /**
     * Pointcut targeting all methods in any class ending with Service under jdbctemplatepractice.product package
     * */
    @Pointcut("execution(* jdbctemplatepractice.product..*Service.*(..))")
    void serviceMethods() {}

    /**
     * Logs service-layer method calls, execution time, results, and errors
     * */
    @Around("serviceMethods()")
    public Object logService(ProceedingJoinPoint pjp) throws Throwable{
        String method = getClassName(pjp.getTarget()) + "." + pjp.getSignature().getName(); // Identifies method
        long start = System.currentTimeMillis(); // Start timer initiated

        logger.info("[SERVICE_START] {} args={}", method, summarizeArgs(pjp.getArgs()));

        try {
            Object result = pjp.proceed();
            long duration = System.currentTimeMillis() - start;
            logger.info("[SERVICE_END] {} durationMs={} result={}", method, duration, summarize(result));
            return result;

        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            logger.warn("[SERVICE_ERROR] {} durationMs={} ex={}", method, duration, ex.toString());
            throw ex;
        }

    }

    /**
     * Logs repository-layer method calls with debug-level detail
     * */
    @Around("repositoryMethods()")
    public Object logRep(ProceedingJoinPoint pjp) throws Throwable{
        String method = getClassName(pjp.getTarget()) + "." + pjp.getSignature().getName(); // Identifies method
        long start = System.currentTimeMillis(); // Start timer initiated

        logger.debug("[REPO_START] {} args={}", method, summarizeArgs(pjp.getArgs()));

        try {
            Object result = pjp.proceed();
            long duration = System.currentTimeMillis() - start;
            logger.debug("[REPO_END] {} durationMs={} result={}", method, duration, summarize(result));
            return result;

        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            logger.warn("[REPO_ERROR] {} durationMs={} ex={}", method, duration, ex.toString());
            throw ex;
        }

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

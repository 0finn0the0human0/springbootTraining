/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations
 * Description: A basic implementation of a logging aspect to manage logging concerns across the application.
 * Author: Benjamin Soto-Roberts
 * Created: 02/19/26
 * */

package org.bsr.springboot.foundations.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.bsr.springboot.foundations.persistence.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// Marks this class as an AspectJ aspect and a Spring-managed component
@Aspect
@Component
public class LoggingAspect {

    // Logger instance scoped to this aspect class
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Pointcut targeting all methods in any class ending with Repository under the persistence.repository package
    @Pointcut("execution(* org.bsr.springboot.foundations.persistence.repository..*Repository.*(..))")
    void repositoryPointcut() {}

    // Pointcut targeting all methods in any class ending with Service under the service package
    @Pointcut("execution(* org.bsr.springboot.foundations.service..*Service.*(..))")
    void servicePointcut() {}

    /**
     * Helper method unwraps the Spring AOP proxy to retrieve the actual underlying class name. Using AopUtils is
     * necessary because repositories are wrapped in JDK dynamic proxies at runtime, which would return $Proxy instead
     * of the actual class name.
     */
    private String getClassName(JoinPoint joinPoint) {
        return AopUtils.getTargetClass(joinPoint.getTarget()).getSimpleName();
    }

    /**
     * Advice that runs before any method matched by the repository or service pointcuts. Logs the class name, method
     * name, and incoming arguments before execution.
     */
    @Before(value = "repositoryPointcut() || servicePointcut()")
    void logBeforeRepo(JoinPoint joinPoint) {
        logger.info("[BEFORE] Class: {} | Method: {} | Args: {}",
                getClassName(joinPoint),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * Advice that runs after a method matched by the repository or service pointcuts returns successfully. Logs the
     * class name, method name, and the returned Product object.
     */
    @AfterReturning(value = "repositoryPointcut() || servicePointcut()", returning = "product")
    void logAfterRepo(JoinPoint joinPoint, Product product) {
        logger.info("[AFTER_RETURNING] Class: {} | Method: {} | Returned: {}",
                getClassName(joinPoint),
                joinPoint.getSignature().getName(),
                product);
    }

}

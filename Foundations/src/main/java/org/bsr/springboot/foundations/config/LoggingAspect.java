/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations
 * Description: A basic implementation of a logging aspect to manage logging concerns across the application.
 * Author: Benjamin Soto-Roberts
 * Created: 02/19/26
 * */

package org.bsr.springboot.foundations.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * The method uses a joinPoint expression to define the logging behavior.
     * */
    @Before(value = "execution(* org.bsr.springboot.foundations.persistence.repository..*Repository.*(..))")
    void logBefore(JoinPoint joinPoint) {
        logger.info("Request data: {} Request Type: {}", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getName());
    }
}

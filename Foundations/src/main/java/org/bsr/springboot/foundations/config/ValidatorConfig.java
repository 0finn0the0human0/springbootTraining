/***********************************************************************************************************************
 * Project: Core Spring Boot Foundations - Phase 1
 * Description: The ValidatorConfig is a configuration class that applies hibernate.validator.fail_fast to the bean
 *              validator. This allows validation rules to fail on first error ensuring that only the first failed
 *              error is propagated to the client. Example: Blank input returns all validation errors to client in ui
 *              instead of just telling the client the input is blank without ValidatorConfig.
 * Author: Benjamin Soto-Roberts
 * Created: 01/07/26
 * */

package org.bsr.springboot.foundations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidatorConfig {

    /**
     * The @Configuration + @Bean validator() annotations define the Validator bean that Spring MVC uses for
     * /@Valid/@Validated. This method configures the LocalValidatorFactoryBean which is Spring's adaptor to the Bean
     * Validation provider (Hibernate Validator) to allow the bean validator to fail fast. This means that only one
     * error will be added to the BindingResult per validation invocation.
     * */
    @Bean
    public LocalValidatorFactoryBean validatorFactory() {
        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        return validatorFactoryBean;
    }
}

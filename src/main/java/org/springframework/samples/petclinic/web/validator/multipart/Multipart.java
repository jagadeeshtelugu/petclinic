package org.springframework.samples.petclinic.web.validator.multipart;

import org.springframework.samples.petclinic.web.validator.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = MultipartValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Multipart {

    String message() default "{Multipart.default}";

    String[] fileTypes() default {"png", "jpeg", "jpg", "gif"};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

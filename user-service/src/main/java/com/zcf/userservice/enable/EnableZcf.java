package com.zcf.userservice.enable;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ZcfRegistrar.class)
public @interface EnableZcf {
}

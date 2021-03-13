package com.sfxcode.sapphire.javafx.fxml;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FxmlLocation {
    String path() default "";
}

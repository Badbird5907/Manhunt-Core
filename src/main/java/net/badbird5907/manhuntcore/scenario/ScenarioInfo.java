package net.badbird5907.manhuntcore.scenario;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface ScenarioInfo {
    String name();
    Material menuItem();
}



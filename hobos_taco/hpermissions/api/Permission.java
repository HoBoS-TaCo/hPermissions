package hobos_taco.hpermissions.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 	Apply to your ICommand implementing command and insert your permission string.
 *	This file can be included in your mod download.
 */
public @interface Permission
{
    public String value();
}

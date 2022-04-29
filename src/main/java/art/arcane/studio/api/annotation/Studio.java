package art.arcane.studio.api.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Optional;

public interface Studio {
    static <T extends Annotation> Optional<T> get(Class<?> c, Class<T> t)
    {
        return Optional.ofNullable(c.getDeclaredAnnotation(t));
    }

    static <T extends Annotation> Optional<T> get(Field c, Class<T> t)
    {
        return Optional.ofNullable(c.getDeclaredAnnotation(t));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Tooltip {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Required {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface Deprecated {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface MaxValue {
        double value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface MinValue {
        double value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface MaxLength {
        int value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface MinLength {
        int value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @interface ListType {
        Class<?> value();
    }
}

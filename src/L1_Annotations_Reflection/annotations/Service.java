package L1_Annotations_Reflection.annotations;

import java.lang.annotation.*;

/**
 * @Documented означает, что класс, помеченный нашей аннотацией попадет в Javadoc
 * @Inherited - наша аннотация унаследуется всеми потомками
 * @Target(ElementType.TYPE) - область применения над классами и интерфейсами. Можно в массиве указать несколько мест
 * @Retention(RetentionPolicy.RUNTIME) - наша аннотация будет жить не только во время компиляции и генерации джавадока,
 * но и в рантайме
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    /** Свойство аннотации. Может быть String'ом или простым типом. Если дефолтного значения нет, то обязательно для заполнения*/
    String name();

    boolean lazyLoad() default false;
}

package L1_Annotations_Reflection;

import L1_Annotations_Reflection.annotations.Init;
import L1_Annotations_Reflection.annotations.Service;
import L1_Annotations_Reflection.service.LazyService;
import L1_Annotations_Reflection.service.SimpleService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
* Аннотации - это метаданные, описыващие код. Они хранятся внутри кода и напрямую на код не влияют. Для того, чтобы они
* влияли на код, нужен какой-то обработчик.
 * Есть аннотации, которые нужны для компиляции. Например, @Deprecated, которая говорит компилятору, что класс устарел.
 * Есть аннотации, делающие что-то во время выполнения.
*/

public class Main {

    private static Map<String, Object> map = new HashMap<>();

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
    inspectService(SimpleService.class);
    inspectService(LazyService.class);
    inspectService(String.class);
    loadService("L1_Annotations_Reflection.service.SimpleService");
    loadService("L1_Annotations_Reflection.service.LazyService");
    loadService("java.lang.String");
        System.out.println(map.get("SimpleServiceName"));
        System.out.println(map.get("LazyServiceName"));
    }

    private static void loadService(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(name);
        if (clazz.isAnnotationPresent(Service.class)) {
            Object objService = clazz.newInstance();
            map.put(clazz.getAnnotation(Service.class).name(), objService);
            System.out.println(map);
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Init.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(objService);
                    } catch (InvocationTargetException e) {
                        Init ann = method.getAnnotation(Init.class);
                        if (ann.suppressException()) {
                            System.err.println(e.getMessage());
                        } else {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

    }



    private static void inspectService(Class<?> service) {
        if (service.isAnnotationPresent(Service.class)) {
            Service ann = service.getAnnotation(Service.class);
            System.out.println(ann.name());
        } else {
            System.out.println(service + "Annotation not found");
        }
        Method[] methods = service.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Init.class)) {
                System.out.println(String.format("Method %s has @Init", method));
            } else {
                System.out.println(String.format("Method %s has no @Init", method));
            }
        }
    }
}

package L1_Annotations_Reflection.service;

import L1_Annotations_Reflection.annotations.Init;
import L1_Annotations_Reflection.annotations.Service;

@Service(name = "LazyServiceName")
public class LazyService {

    @Init
    private void lazyInitService() throws Exception{
        System.out.println("Lazy Init L1_Annotations_Reflection.service");
    }
}

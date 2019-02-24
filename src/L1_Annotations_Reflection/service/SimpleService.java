package L1_Annotations_Reflection.service;

import L1_Annotations_Reflection.annotations.Init;
import L1_Annotations_Reflection.annotations.Service;

@Service(name = "SimpleServiceName")
public class SimpleService {

    @Init
    private void initService() {
        System.out.println("Init L1_Annotations_Reflection.service");
    }

    public void notInit() {
        System.out.println("Not Init");
    }
}

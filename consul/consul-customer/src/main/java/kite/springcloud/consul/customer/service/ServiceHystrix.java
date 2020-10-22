package kite.springcloud.consul.customer.service;

import org.springframework.stereotype.Component;

@Component
public class ServiceHystrix implements IHelloService {
    @Override
    public String test() {
        return "这是test()调用失败的降级处理";
    }

    @Override
    public String nice() {
        return "这是nice()调用失败的降级处理";
    }
}

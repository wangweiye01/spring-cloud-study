package kite.springcloud.stream.rabbit.customer.service;

import kite.springcloud.common.stream.MyProcessor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class DelayExchangeListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @StreamListener(MyProcessor.DELAY_MESSAGE_INPUT)
    public void processLogMessage(String message) {
        logger.info(String.format("收到延迟队列的数据：%s", message));
    }
}

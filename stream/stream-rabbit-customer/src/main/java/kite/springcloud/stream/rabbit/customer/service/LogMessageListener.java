package kite.springcloud.stream.rabbit.customer.service;

import kite.springcloud.common.stream.LogInfo;
import kite.springcloud.common.stream.MyProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * LogMessageListener
 *
 * @author fengzheng
 * @date 2018/12/26
 */
@Slf4j
@Component
public class LogMessageListener {
    int retryCount = 1;

    /**
     * 通过 MyProcessor.MESSAGE_INPUT 接收消息
     * 然后通过 SendTo 将处理后的消息发送到 MyProcessor.LOG_FORMAT_OUTPUT
     * <p>
     * 当消费失败时，默认的策略是重试。当重试成功了，则不会抛出异常；当重试失败，抛出异常，消费结束。
     *
     * @param message
     * @return
     */
    @StreamListener(MyProcessor.MESSAGE_INPUT)
    @SendTo(MyProcessor.LOG_FORMAT_OUTPUT)
    public String processLogMessage(String message) {
        if (retryCount == 3) {
            log.info(String.format("重试成功！重试次数：%d, 接收到原始消息：%s", retryCount, message));
            return "「" + message + "」";
        } else {
            retryCount++;
            throw new RuntimeException("消费失败!");
        }
    }

    /**
     * 自定义消费错误处理
     */
    @ServiceActivator(inputChannel = "kite.log.messages.logConsumer-group1.errors")
    public void error(Message<?> message) {
        log.info("消息消费失败，这里处理降级逻辑！");
    }

//    @StreamListener(MyProcessor.MESSAGE_INPUT)
//    @SendTo(MyProcessor.LOG_FORMAT_OUTPUT)
//    public String processObjectLogMessage(LogInfo logInfo) {
//        log.info("接收到原始 object 消息：" + logInfo.toString());
//        return "「" + logInfo.toString() +"」";
//    }

    /**
     * 接收来自 MyProcessor.LOG_FORMAT_INPUT 的消息
     * 也就是加工后的消息，也就是通过上面的 SendTo 发送来的
     * 因为 MyProcessor.LOG_FORMAT_OUTPUT 和 MyProcessor.LOG_FORMAT_INPUT 是指向同一 exchange
     *
     * @param message
     */
    @StreamListener(MyProcessor.LOG_FORMAT_INPUT)
    public void processFormatLogMessage(String message) {
        log.info("接收到格式化后的消息：" + message);
    }
}

package rabbitmq.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FanoutSender {


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange", "", context);
    }

    /**
     * 设置生产者的生产消息的ack信息回调(公共处理)
     */
    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return (correlationData, ack, cause) -> {
            //我们可以通过correlationData原始数据 来对消息进行后续处理，但是这是有个要求在于发送必须使用CorrelationData类
            if (ack) {
                log.info("消息发送成功!!!!!,消息data:{}，时间:{}", correlationData, System.currentTimeMillis());
            } else {
                log.error("消息发送失败!!!!,原因是:{}", cause);
            }
        };
    }

    /**
     * 发送者失败通知
     */
    @Bean
    public RabbitTemplate.ReturnCallback returnCallback() {
        //构建一个
        return (Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
            log.error("发送者路由失败，请检查路由 Returned replyCode:{} Returned replyText:{} Returned routingKey:{} Returned message:{}"
                    , replyCode, replyText, routingKey, new String(message.getBody()));
        };
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
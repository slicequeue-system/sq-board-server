package app.slicequeue.sq_board.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * Kafka 소비자(Consumer) 설정 클래스
 * - Spring Kafka 의 `@KafkaListener`를 사용할 때, 메시지 수신 동작을 설정한다.
 */
@Configuration
public class KafkaConfig {

    /**
     * Kafka 메시지를 처리하는 컨테이너 팩토리 Bean 생성
     *
     * @param consumerFactory Kafka 소비자 팩토리 (자동 주입)
     * @return `ConcurrentKafkaListenerContainerFactory<String, String>` 빈
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {
        // Kafka 리스너 컨테이너 팩토리 생성
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory); // 주어진 ConsumerFactory 를 사용하여 설정
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);  // 메시지 수동 커밋 설정 (수동 `acknowledge()` 필요)
        return factory;
    }
}

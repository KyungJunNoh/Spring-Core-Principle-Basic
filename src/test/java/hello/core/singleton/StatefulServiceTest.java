package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : A 사용자 10000원 주문
        int userA = statefulService1.order("userA", 10000);
        // ThreadA : B 사용자 20000원 주문
        int userB = statefulService2.order("userB", 20000);

        // Thread A : 사용자 A 주문 금액 조회
        int price = userA;
        System.out.println("price = " + price);

        Assertions.assertThat(userA).isEqualTo(10000); // B 사용자가 중간에 20000 원으로 결제를 하여 값을 공유를 하는 필드(변수) 때문에 A 까지 영향을 미침
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}

/*
- 실무에서 이런 경우를 종종 보는데, 이로 인해 정말 해결하기 어려운 큰 문제들이 터진다. ( 몇년에 한번씩 꼭 만난다. )
- 진짜 공유필드는 조심해야 한다! 스프링 빈은 항상 무상태 ( stateless ) 로 설계하자.
 */
package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// DI ( Dependency Injection - 의존성 주입 )
// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성 한다.
@Configuration // 애플리케이션의 설정 정보 선언 // @Configuration 이 싱글톤을 유지해주는 핵심 어노테이션이다 이게 없으면 싱글톤이 깨져버린다.
public class AppConfig {

    // 생각했던 값
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository

    // 실제 값
    // call AppConfig.memberService
    // call AppConfig.memberRepository // 메소드가 한 번만 호출됨
    // call AppConfig.orderService

    @Bean
    public MemberService memberService(){
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository()); // 생성자 주입
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository(); // 추후에 다른 구현체로 바꿀때 이 부분만 바꿔주면 된다.
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy(); // 추후에 다른 구현체로 바꿀때 이 부분만 바꿔주면 된다.
    }

}

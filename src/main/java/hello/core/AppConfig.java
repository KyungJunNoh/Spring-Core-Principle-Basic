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

// DI ( Dependency Injection - 의존성 주입 )
// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성 한다.
public class AppConfig {

    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository()); // 생성자 주입
    }

    private MemberRepository memberRepository() {
        return new MemoryMemberRepository(); // 추후에 다른 구현체로 바꿀때 이 부분만 바꿔주면 된다.
    }

    public OrderService orderService(){
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy()
        );
    }

    private DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy(); // 추후에 다른 구현체로 바꿀때 이 부분만 바꿔주면 된다.
    }

}

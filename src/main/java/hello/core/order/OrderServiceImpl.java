package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor // final 이 붙은 필드에 생성자를 주입 // lombok 에서 지원
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository; // 인터페이스에만 의존하도록 변경 ( DIP 지킴 )
    private final DiscountPolicy discountPolicy; // 인터페이스에만 의존하도록 변경 ( DIP 지킴 )

    @Autowired // @Autowired 의 생성자 빈 조회 방법은 타입을 먼저 조회한 다음에 같은 타입의 빈이 여러개라면 그 다음에 이름으로 조회를 한다. 그래서 파라미터에 주입을 받을 이름을 입력해주면 빈이 여러개여도 주입이 가능하다.
    public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) { // 같은 Qualifier 을 찾아서 매핑
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); // 설계가 잘된 예시) SRP - 단일 책임의 원칙 가 잘 지켜짐

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}

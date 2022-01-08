package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // final 이 붙은 필드에 생성자를 주입 // lombok 에서 지원g
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository; // 인터페이스에만 의존하도록 변경 ( DIP 지킴 )
    private final DiscountPolicy discountPolicy; // 인터페이스에만 의존하도록 변경 ( DIP 지킴 )

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

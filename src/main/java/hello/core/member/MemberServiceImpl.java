package hello.core.member;

// MemberServiceImpl 입장에서는 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
// MemberServiceImpl 의 생성를 통해서 어떤 구현 객체를 주입할건지 오직 외부('AppConfig')에서 결정된다.
// MemberServiceImpl 은 이제 의존관계에대한 고민은 외부에서 맡기고 실행에만 집중하면 된다.
public class MemberServiceImpl implements MemberService{

    // 설계 변경으로 MemberServiceImpl은 MemoryMemberRepository( 구현체 ) 를 의존하지 않는다!
    private final MemberRepository memberRepository; // 추상화에만 의존하게 변경

    public MemberServiceImpl(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}

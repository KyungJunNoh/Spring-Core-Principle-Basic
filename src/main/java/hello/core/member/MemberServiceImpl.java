package hello.core.member;

public class MemberServiceImpl implements MemberService{

    // 현재는 역할(MemberRepository)과 구현(MemoryMemberRepository) 에 모두 의존 되어있음. (DIP가 지켜지지 않음)
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

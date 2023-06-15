package personal.delivery.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.constant.Role;
import personal.delivery.member.Member;
import personal.delivery.member.dto.MemberDto;
import personal.delivery.member.dto.MemberResponseDto;
import personal.delivery.member.repository.MemberRepository;

import java.time.LocalDateTime;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BeanConfiguration beanConfiguration;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, BeanConfiguration beanConfiguration) {
        this.memberRepository = memberRepository;
        this.beanConfiguration = beanConfiguration;
    }

    public MemberResponseDto saveMember(Member member) {

        validateDuplicateMember(member);

        Member savedMember = memberRepository.save(member);

        MemberResponseDto memberResponseDto = beanConfiguration.modelMapper()
                .map(savedMember, MemberResponseDto.class);

        return memberResponseDto;

    }

    public MemberResponseDto createMember(MemberDto memberDto) {

        Member member = Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .address(memberDto.getAddress())
                .role(Role.USER)
                .registrationTime(LocalDateTime.now())
                .build();

        return saveMember(member);

    }

    private void validateDuplicateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");

        }

    }

}

package personal.delivery.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.config.BeanConfiguration;
import personal.delivery.constant.Role;
import personal.delivery.member.dto.MemberDto;
import personal.delivery.member.dto.MemberResponseDto;
import personal.delivery.member.eneity.Member;
import personal.delivery.member.repository.MemberRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BeanConfiguration beanConfiguration;

    public MemberResponseDto saveMember(Member member) {

        validateDuplicateMember(member);

        Member savedMember = memberRepository.save(member);

        MemberResponseDto memberResponseDto = beanConfiguration.modelMapper()
                .map(savedMember, MemberResponseDto.class);

        return memberResponseDto;

    }

    public MemberResponseDto createSeller(MemberDto memberDto) {

        Member member = Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .address(memberDto.getAddress())
                .role(Role.SELLER)
                .registrationTime(LocalDateTime.now())
                .build();

        return saveMember(member);

    }

    public MemberResponseDto createCustomer(MemberDto memberDto) {

        Member member = Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .password(memberDto.getPassword())
                .address(memberDto.getAddress())
                .role(Role.CUSTOMER)
                .registrationTime(LocalDateTime.now())
                .build();

        return saveMember(member);

    }

    private void validateDuplicateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다. (email: " + member.getEmail() + ")");
        }

    }

}

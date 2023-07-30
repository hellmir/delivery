package personal.delivery.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.configuration.BeanConfiguration;
import personal.delivery.constant.Role;
import personal.delivery.member.dto.MemberRequestDto;
import personal.delivery.member.dto.MemberResponseDto;
import personal.delivery.member.entity.Member;
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

    public MemberResponseDto createSeller(MemberRequestDto memberRequestDto) {

        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .address(memberRequestDto.getAddress())
                .role(Role.SELLER)
                .registrationTime(LocalDateTime.now())
                .build();

        return saveMember(member);

    }

    public MemberResponseDto createCustomer(MemberRequestDto memberRequestDto) {

        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .address(memberRequestDto.getAddress())
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

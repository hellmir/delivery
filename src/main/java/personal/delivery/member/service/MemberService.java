package personal.delivery.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import personal.delivery.member.Member;
import personal.delivery.member.dto.MemberDto;
import personal.delivery.member.dto.MemberResponseDto;

public interface MemberService {

    MemberResponseDto saveMember(Member member);

    MemberResponseDto createMember(MemberDto memberDto);

}

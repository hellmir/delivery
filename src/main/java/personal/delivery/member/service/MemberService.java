package personal.delivery.member.service;

import personal.delivery.member.entity.Member;
import personal.delivery.member.dto.MemberRequestDto;
import personal.delivery.member.dto.MemberResponseDto;

public interface MemberService {

    MemberResponseDto saveMember(Member member);

    MemberResponseDto createSeller(MemberRequestDto memberRequestDto);

    MemberResponseDto createCustomer(MemberRequestDto memberRequestDto);

}

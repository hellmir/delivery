package personal.delivery.member.service;

import personal.delivery.member.eneity.Member;
import personal.delivery.member.dto.MemberDto;
import personal.delivery.member.dto.MemberResponseDto;

public interface MemberService {

    MemberResponseDto saveMember(Member member);

    MemberResponseDto createSeller(MemberDto memberDto);

    MemberResponseDto createCustomer(MemberDto memberDto);

}

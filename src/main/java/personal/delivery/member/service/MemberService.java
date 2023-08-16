package personal.delivery.member.service;

import personal.delivery.member.constant.Role;
import personal.delivery.member.dto.MemberRequestDto;
import personal.delivery.member.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {

    List<MemberResponseDto> getAllMembersByRole(Role role);

    MemberResponseDto createMember(MemberRequestDto memberRequestDto);

    MemberResponseDto getMemberInformation(Long id);

    MemberResponseDto updateMemberInformation(Long id, MemberRequestDto memberRequestDto);

    void deleteMember(Long id);

}

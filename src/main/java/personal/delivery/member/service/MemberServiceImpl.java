package personal.delivery.member.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.exception.TryToSaveDuplicateMemberException;
import personal.delivery.member.constant.Role;
import personal.delivery.member.dto.MemberRequestDto;
import personal.delivery.member.dto.MemberResponseDto;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MemberResponseDto> getAllMembersByRole(Role role) {

        List<Member> members = memberRepository.findAllByRole(role);

        return members.stream()
                .map(member -> modelMapper.map(member, MemberResponseDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public MemberResponseDto createMember(MemberRequestDto memberRequestDto) {

        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .address(memberRequestDto.getAddress())
                .role(memberRequestDto.getRole())
                .build();

        validateDuplicateMember(member);

        Member savedMember = memberRepository.save(member);

        return modelMapper.map(savedMember, MemberResponseDto.class);

    }

    @Override
    public MemberResponseDto getMemberInformation(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다. (id: " + id + ")"));

        return modelMapper.map(member, MemberResponseDto.class);

    }

    @Override
    public MemberResponseDto updateMemberInformation(Long id, MemberRequestDto memberRequestDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다. (id: " + id + ")"));

        Member changingMember = Member.builder()
                .id(member.getId())
                .name(memberRequestDto.getName() == null ? member.getName() : memberRequestDto.getName())
                .email(member.getEmail())
                .password(memberRequestDto.getPassword() == null
                        ? member.getPassword() : memberRequestDto.getPassword())
                .address(memberRequestDto.getAddress() == null ? member.getAddress() : memberRequestDto.getAddress())
                .role(member.getRole())
                .registeredTime(member.getRegisteredTime())
                .updatedTime(LocalDateTime.now())
                .build();

        Member changedMember = memberRepository.save(changingMember);

        return modelMapper.map(changedMember, MemberResponseDto.class);

    }

    @Override
    public void deleteMember(Long id) {

        memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다. (id: " + id + ")"));

        memberRepository.deleteById(id);

    }

    private void validateDuplicateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new TryToSaveDuplicateMemberException("이미 가입된 회원입니다. (email: " + member.getEmail() + ")");
        }

    }

}

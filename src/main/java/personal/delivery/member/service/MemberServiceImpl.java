package personal.delivery.member.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.constant.Role;
import personal.delivery.member.dto.MemberRequestDto;
import personal.delivery.member.dto.MemberResponseDto;
import personal.delivery.member.entity.Member;
import personal.delivery.member.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public MemberResponseDto createSeller(MemberRequestDto memberRequestDto) {

        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .address(memberRequestDto.getAddress())
                .role(Role.SELLER)
                .build();

        return saveMember(member);

    }

    @Override
    public MemberResponseDto createCustomer(MemberRequestDto memberRequestDto) {

        Member member = Member.builder()
                .name(memberRequestDto.getName())
                .email(memberRequestDto.getEmail())
                .password(memberRequestDto.getPassword())
                .address(memberRequestDto.getAddress())
                .role(Role.CUSTOMER)
                .build();

        return saveMember(member);

    }

    @Override
    public MemberResponseDto getMemberInformation(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("해당 회원이 존재하지 않습니다. (id: " + id + ")"));

        return modelMapper.map(member, MemberResponseDto.class);

    }

    private MemberResponseDto saveMember(Member member) {

        validateDuplicateMember(member);

        Member savedMember = memberRepository.save(member);

        return modelMapper.map(savedMember, MemberResponseDto.class);

    }

    private void validateDuplicateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다. (email: " + member.getEmail() + ")");
        }

    }

}

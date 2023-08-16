package personal.delivery.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import personal.delivery.member.constant.Role;
import personal.delivery.member.dto.MemberRequestDto;
import personal.delivery.member.dto.MemberResponseDto;
import personal.delivery.member.service.MemberService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("roles/{role}")
    public ResponseEntity<List<MemberResponseDto>> getAllMembersByRole(@PathVariable Role role) {

        List<MemberResponseDto> memberResponseDtoList = memberService.getAllMembersByRole(role);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDtoList);

    }

    @PostMapping()
    public ResponseEntity<MemberResponseDto> signUpMember(@Valid @RequestBody MemberRequestDto memberRequestDto) {

        MemberResponseDto memberResponseDto = memberService.createMember(memberRequestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(memberResponseDto.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(memberResponseDto);

    }

    @GetMapping("{id}")
    public ResponseEntity<MemberResponseDto> getMemberInformation(@PathVariable Long id) {

        MemberResponseDto memberResponseDto = memberService.getMemberInformation(id);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);

    }

    @PatchMapping("{id}")
    public ResponseEntity<MemberResponseDto> changeMemberInformation
            (@PathVariable Long id, @RequestBody MemberRequestDto memberRequestDto) {

        MemberResponseDto memberResponseDto = memberService.updateMemberInformation(id, memberRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {

        memberService.deleteMember(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}

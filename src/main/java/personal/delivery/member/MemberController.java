package personal.delivery.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.delivery.member.dto.MemberDto;
import personal.delivery.member.dto.MemberResponseDto;
import personal.delivery.member.service.MemberService;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<MemberResponseDto> signUpMember(@RequestBody MemberDto memberDto) {

        MemberResponseDto memberResponseDto = memberService.createMember(memberDto);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

}

package personal.delivery.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import personal.delivery.member.constant.Role;
import personal.delivery.member.entity.Address;
import personal.delivery.member.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static personal.delivery.member.constant.Role.SELLER;

@ActiveProfiles("test")
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일 주소를 통해 해당 이메일 주소로 가입한 회원을 찾을 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "홍길동, abcd@abc.com, 1234, SELLER",
            "고길동, abcd@abcd.com, 1234, CUSTOMER",
            "김길동, abcd@abcde.com, 1234, SELLER",
    })
    void findByEmail(String memberName, String email, String password, String role) {

        // given

        Member member = Member.builder()
                .name(memberName)
                .email(email)
                .password(password)
                .address(mock(Address.class))
                .role(Role.valueOf(role))
                .build();

        memberRepository.save(member);

        // when
        Member findedMember = memberRepository.findByEmail(email);

        // then
        assertThat(findedMember.getName()).isEqualTo(memberName);
        assertThat(findedMember.getEmail()).isEqualTo(email);
        assertThat(findedMember.getPassword()).isEqualTo(password);
        assertThat(findedMember.getRole()).isEqualTo(Role.valueOf(role));

    }

    @DisplayName("존재하지 않는 이메일 주소로 회원을 찾으면 회원을 반환하지 않는다.")
    @Test
    void voidfindByEmailWithNonExistentEmail() {

        // given

        Member member = Member.builder()
                .name("홍길동")
                .email("abc@abcde.com")
                .password("1234")
                .address(mock(Address.class))
                .role(SELLER)
                .build();

        memberRepository.save(member);

        // when
        Member findedMember = memberRepository.findByEmail("ab@abcd.com");

        // then
        assertThat(findedMember).isNull();

    }

}

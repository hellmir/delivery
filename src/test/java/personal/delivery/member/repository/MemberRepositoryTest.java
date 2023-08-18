package personal.delivery.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import personal.delivery.member.constant.Role;
import personal.delivery.member.entity.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static personal.delivery.member.constant.Role.CUSTOMER;
import static personal.delivery.member.constant.Role.SELLER;
import static personal.delivery.test_util.TestObjectFactory.createMember;

@Transactional
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

        Member member = createMember(memberName, email, password, Role.valueOf(role));

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
    void findByEmailWithNonExistentEmail() {

        // given

        Member member = createMember("홍길동", "abcd@abc.com", "1234", SELLER);

        memberRepository.save(member);

        // when
        Member findedMember = memberRepository.findByEmail("ab@abcd.com");

        // then
        assertThat(findedMember).isNull();

    }

    @DisplayName("Role을 통해 원하는 분류의 회원 목록을 조회할 수 있다.")
    @Test
    void findAllByRole() {

        // given

        Member member1 = createMember("홍길동", "abcd@abc.com", "1234", SELLER);

        Member member2 = createMember("고길동", "abcd@abcd.com", "1234", CUSTOMER);

        Member member3 = createMember("김길동", "abcd@abcde.com", "1234", SELLER);

        Member member4 = createMember("길동", "abcd@abcdef.com", "1234", CUSTOMER);

        memberRepository.saveAll(List.of(member1, member2, member3, member4));

        // when
        List<Member> selectedMembers1 = memberRepository.findAllByRole(SELLER);
        List<Member> selectedMembers2 = memberRepository.findAllByRole(CUSTOMER);

        // then
        assertThat(selectedMembers1).hasSize(2)
                .extracting("name", "email", "role")
                .containsExactlyInAnyOrder(
                        tuple("홍길동", "abcd@abc.com", SELLER),
                        tuple("김길동", "abcd@abcde.com", SELLER)
                );

        assertThat(selectedMembers2).hasSize(2)
                .extracting("name", "email", "role")
                .containsExactlyInAnyOrder(
                        tuple("고길동", "abcd@abcd.com", CUSTOMER),
                        tuple("길동", "abcd@abcdef.com", CUSTOMER)
                );

    }

}

package personal.delivery.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;
import personal.delivery.member.constant.Role;
import personal.delivery.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    List<Member> findAllByRole(Role role);

}

package personal.delivery.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.member.constant.Role;
import personal.delivery.member.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    List<Member> findAllByRole(Role role);

}

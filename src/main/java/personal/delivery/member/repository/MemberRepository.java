package personal.delivery.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personal.delivery.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

}

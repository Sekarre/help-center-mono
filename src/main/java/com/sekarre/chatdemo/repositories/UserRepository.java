package com.sekarre.chatdemo.repositories;

import com.sekarre.chatdemo.domain.Role;
import com.sekarre.chatdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAllByRolesContaining(Role role);

    @Query(value = "select distinct *" +
            "from users u" +
            "         join role_user ru" +
            "              on u.id = ru.user_id" +
            "         join role r on ru.role_id = r.id" +
            "where r.specialization = 'GAMES'" +
            "  and r.name = 'SUPPORT'" +
            "  and u.id = (select i.user_id" +
            "              from issue i" +
            "                       join role_user ru2" +
            "                            on i.user_id = ru2.user_id" +
            "                       join role r2 on ru2.role_id = r2.id" +
            "              where r2.specialization = r.specialization" +
            "              group by i.user_id" +
            "              order by count(*) desc" +
            "              limit 1)", nativeQuery = true)
    Optional<User> findUsersWithLeastIssuesAndMatchingSpecialization();
}

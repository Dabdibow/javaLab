package ru.itis.javalab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.javalab.models.User;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UsersRepository extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);

    @Modifying
    @Query("update User user set confirm_state = 'CONFIRMED' where user.confirmCode like concat('%', :code, '%') ")
    void confirmUserWithCode(@Param("code") String code);

    @Modifying
    @Query("update User user set role = 'USER' where user.confirmCode like concat('%', :code, '%') ")
    void setUserRoleWithCode(@Param("code") String code);

    @Modifying
    @Query("update User user set state = 'ACTIVE' where user.confirmCode like concat('%', :code, '%') ")
    void setUserActivityWithCode(@Param("code") String code);

}

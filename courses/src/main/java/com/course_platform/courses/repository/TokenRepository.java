package com.course_platform.courses.repository;

import com.course_platform.courses.dto.request.TokenRequest;
import com.course_platform.courses.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity,String> {

    Optional<TokenEntity> findByToken(String token );
    @Transactional
    @Modifying
    @Query("UPDATE TokenEntity t SET t.isExpired = true WHERE t.user.id = ?1")
    void updateExpiredToken(String id);
}

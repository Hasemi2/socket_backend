package com.chat.realtime.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUserIdAndPassword(String userId, String password);

    Optional<User> findByUserToken(String userToken);

    List<User> findAllByUserTokenIn(Iterable<String> userToken);
}

package likelion.festival.repository;

import likelion.festival.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByCookieKey(String cookieKey);
}

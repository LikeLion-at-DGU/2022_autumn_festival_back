package likelion.festival.repository;

import likelion.festival.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBooth_IdOrderByCreatedDateTimeDesc(Long id);
}

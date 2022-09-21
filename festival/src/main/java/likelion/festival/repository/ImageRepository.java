package likelion.festival.repository;

import likelion.festival.entitiy.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

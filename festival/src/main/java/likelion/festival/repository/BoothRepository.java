package likelion.festival.repository;


import likelion.festival.entity.Booth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoothRepository extends JpaRepository<Booth, Long> {

    List<Booth> findByLocation(String location);

    List<Booth> findByTitleContaining(String title);

    // TODO : 메뉴검색 문 추가
    List<Booth> findByMenus_NameContaining(String menu);
}

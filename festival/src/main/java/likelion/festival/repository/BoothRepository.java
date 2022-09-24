package likelion.festival.repository;


import likelion.festival.entity.Booth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoothRepository extends JpaRepository<Booth, Long> {

    List<Booth> findByLocation(String location);

    // TODO : 좋아요 기준으로 탑 3개 추출하는 것으로 refactoring 하기
    @Query("SELECT e FROM Booth e ORDER BY e.title")
    List<Booth> findByTop3();

    List<Booth> findByTitle(String title);

    // TODO : 메뉴검색 문 추가
}

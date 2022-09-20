package likelion.festival.repository;


import likelion.festival.entitiy.Booth;
import likelion.festival.entitiy.BoothLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoothRepository extends JpaRepository<Booth, Long> {

    List<Booth> findByBoothLocation(BoothLocation boothLocation);

    // TODO : 좋아요 기준으로 탑 3개 추출하는 것으로 refactoring 하기
    @Query("SELECT e FROM Booth e ORDER BY e.title")
    List<Booth> findByTop3();

    @Query("SELECT e FROM Booth e where e.title = :title")
    List<Booth> findByTitle(@Param("title") String title);

    // TODO : 메뉴검색 문 추가
}

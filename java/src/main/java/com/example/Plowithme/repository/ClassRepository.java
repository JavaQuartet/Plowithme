package com.example.Plowithme.repository;
import com.example.Plowithme.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    @Modifying
    @Query(value = "update ClassEntity c set c.status=c.status + 1 where c.id=:id")
    void updateStatus(@Param("id") Long id);


    @Modifying
    @Query(value = "update ClassEntity c set c.status=c.status - 1 where c.id=:id")
    void downStatus(@Param("id") Long id);


/*    @Modifying
    @Query(value = "update User u set u.total_distance = u.total_distance + u.distance where u.id=:id")
    void endclass(@Param("id") Long id);*/

//    @Query(value = "SELECT b FROM class_table b WHERE b.depth_1 LIKE %:keyword% OR b.depth_2 LIKE %:keyword% OR b.depth_3 LIKE %:keyword%"
//    )
//    List<ClassEntity> findAllSearch(String keyword);

    Page<ClassEntity> findByTitleContaining(String searchKey, Pageable pageable);
}

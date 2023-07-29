package com.example.Plowithme.repository;

import com.example.Plowithme.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    /*@Modifying
    @Query(value = "update ClassEntity b set b.classHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);*/
}

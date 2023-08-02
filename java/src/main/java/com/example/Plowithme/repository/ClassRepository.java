package com.example.Plowithme.repository;

import com.example.Plowithme.Entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    @Modifying
    @Query(value = "update ClassEntity c set c.status=c.status + 1 where c.id=:id")
    void updateStatus(@Param("id") Long id);
}

package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    @Modifying
    @Query(value = "update ClassEntity c set c.status=c.status + 1 where c.id=:id")
    void updateStatus(@Param("id") Long id);


    @Modifying
    @Query(value = "update ClassEntity c set c.status=c.status - 1 where c.id=:id")
    void downStatus(@Param("id") Long id);

    @Modifying
    @Query(value = "update ClassEntity c set c.classjoined=1 where c.id=:id")
    void joinedclass(@Param("id") Long id);

    @Modifying
    @Query(value = "update ClassEntity c set c.classjoined=0 where c.id=:id")
    void unjoinedclass(@Param("id") Long id);
}

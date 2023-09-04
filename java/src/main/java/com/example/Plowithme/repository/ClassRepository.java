package com.example.Plowithme.repository;
import com.example.Plowithme.dto.request.meeting.ClassDTO;
import com.example.Plowithme.entity.ClassEntity;
import com.example.Plowithme.entity.ClassParticipantsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {
    @Modifying
    @Query(value = "update ClassEntity c set c.member_current=c.member_current + 1 where c.id=:id")
    void updateStatus(@Param("id") Long id);


    @Modifying
    @Query(value = "update ClassEntity c set c.member_current=c.member_current - 1 where c.id=:id")
    void downStatus(@Param("id") Long id);



/*    @Modifying
    @Query(value = "update User u set u.total_distance = u.total_distance + u.distance where u.id=:id")
    void endclass(@Param("id") Long id);*/

    Page<ClassEntity> findByStartRegionContaining(String searchKey, Pageable pageable);

    /*List<ClassEntity> findAllByMaker_idAndStatus(Long userId, int Status);*/


}

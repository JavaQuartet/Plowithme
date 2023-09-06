package com.example.Plowithme.repository;

import com.example.Plowithme.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>, JpaSpecificationExecutor<BoardEntity> {

    @Modifying
    @Query(value = "update BoardEntity b set b.postHits=b.postHits+1 where b.postId=:postId")
    void updatePostHits(@Param("postId") Long postId);

}

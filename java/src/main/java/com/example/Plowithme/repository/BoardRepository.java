package com.example.Plowithme.repository;

import com.example.Plowithme.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
//*래파지토리로 작업할 때는 반드시 엔티티로 넘겨줘야 함
public interface BoardRepository extends JpaRepository<BoardEntity, Long>, JpaSpecificationExecutor<BoardEntity> {

    //update board_table set board_postHits=board_postHits+1 where id=?
    //b 는 boardEntity
    @Modifying
    @Query(value = "update BoardEntity b set b.postHits=b.postHits+1 where b.id=:id")
    void updatePostHits(@Param("id") Long id);

}

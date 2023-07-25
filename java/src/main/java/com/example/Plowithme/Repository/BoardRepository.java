package com.example.Plowithme.Repository;

import com.example.Plowithme.Entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//*래파지토리로 작업할 때는 반드시 엔티티로 넘겨줘야 함
public interface BoardRepository extends JpaRepository<BoardEntity, String> {

}

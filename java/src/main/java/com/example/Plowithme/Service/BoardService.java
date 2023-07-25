package com.example.Plowithme.Service;

import com.example.Plowithme.Dto.BoardDto;
import com.example.Plowithme.Entity.BoardEntity;
import com.example.Plowithme.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void savePosting(BoardDto boardDto) {
        /*
            1. dto>entity 변환
            2. repository의 savaPosting 메서드 호출
        */
        BoardEntity boardEntity=BoardEntity.toBoardEntity(boardDto);
        //boardEntity의 toBoardEntity메소드를 매개변수 boardDto 이용해서 호출
        //변환된 entity를 가져와야 하므로 BoardEntity boardEntity=~~

        boardRepository.save(boardEntity);
        //{jpa 제공하는) 레파지토리 save메소드 호출


    }

}

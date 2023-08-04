package com.example.Plowithme.service;

import com.example.Plowithme.dto.BoardDto;
import com.example.Plowithme.Entity.BaseEntity;
import com.example.Plowithme.Entity.BoardEntity;
import com.example.Plowithme.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDto boardDto) {
        /*
            1. dto>entity 변환
            2. repository의 savaPosting 메서드 호출
        */
        BoardEntity boardEntity=BoardEntity.toSaveEntity(boardDto);
        //boardEntity의 toBoardEntity메소드를 매개변수 boardDto 이용해서 호출
        //변환된 entity를 가져와야 하므로 BoardEntity boardEntity=~~

        boardRepository.save(boardEntity);
        //{jpa 제공하는) 레파지토리 save메소드 호출

    }
    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toboardDto(boardEntity));
        }
        return boardDtoList;
    }

}

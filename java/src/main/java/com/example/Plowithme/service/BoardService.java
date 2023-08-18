package com.example.Plowithme.service;

import com.example.Plowithme.dto.BoardDto;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        // boardrepsitory에서 findAll 함수를 호출, 엔티티 리스트 형태로 받는다
        // 엔티티로 넘어온 객체를 다시 dto로 담아서 넘겨야 한다!

        List<BoardDto> boardDtoList = new ArrayList<>();
        //리스트 객체 선언

        for (BoardEntity boardEntity : boardEntityList) {
            boardDtoList.add(BoardDto.toboardDto(boardEntity));
        }
        //엔티티 객체를 하나씩 꺼내서 dto 객체로 하나씩 옮겨 담는다.

        return boardDtoList;
    }


    //게시글 조회수 증가
    @Transactional
    public void updatePostHits(Long postId) {
        boardRepository.updatePostHits(postId);
    }

    public BoardDto findByPostId(Long postId) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(postId);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity= optionalBoardEntity.get();
            BoardDto boardDto = BoardDto.toboardDto(boardEntity);
            return boardDto;
        } else {
            return null;
        }
    }
}

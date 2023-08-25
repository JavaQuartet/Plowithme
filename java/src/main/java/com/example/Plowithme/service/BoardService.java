package com.example.Plowithme.service;

import com.example.Plowithme.dto.BoardDto;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardEntity getPostById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No post searched with id: " + id));
    }

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

    //게시글 조회수 증가
    @Transactional
    public void updatePostHits(Long id) {
        boardRepository.updatePostHits(id);
    }


    public BoardDto findByPostId(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity= optionalBoardEntity.get();
            BoardDto boardDto = BoardDto.toboardDto(boardEntity);
            return boardDto;
        } else {
            return null;
        }
    }

    //게시글 삭제 기능
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    //게시글 수정 기능
//    public void updatePost(Long id, String title, String contents) {
//        Optional<BoardEntity> board= boardRepository.findById(id);
//        BoardEntity boardEntity= board.orElseThrow(() -> new NotFoundException("No post searched"));
//        boardEntity.setTitle(title);
//        boardEntity.setContents(contents);
//        boardRepository.save(boardEntity);
//
//    }


}
package com.example.Plowithme.service;

import com.example.Plowithme.dto.request.community.BoardDto;
import com.example.Plowithme.dto.request.community.BoardSaveDto;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.Comment;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.BoardRepository;
import com.example.Plowithme.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

  //  private final BoardEntity boardEntity;

    private final UserRepository userRepository;

    public void save(User currentUser, BoardDto boardDto) {
        /*
            1. dto>entity 변환
            2. repository의 savaPosting 메서드 호출
        */
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니당."));

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        User optionalUserEntity=userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });

      //  BoardEntity boardEntity=BoardEntity.toSaveEntity(boardSaveDto);
        //boardEntity의 toBoardEntity메소드를 매개변수 boardDto 이용해서 호출
        //변환된 entity를 가져와야 하므로 BoardEntity boardEntity=~~

        BoardEntity boardEntity= new BoardEntity();
        boardEntity.setWriterId(currentUser.getId());
        boardEntity.toSaveEntity(boardDto);
        userRepository.save(user);

        boardRepository.save(boardEntity);
        //{jpa 제공하는) 레파지토리 save메소드 호출

    }

    //게시글 조회기능
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

    //게시글 삭제 기능
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public List<BoardDto> getFilter(Specification<BoardEntity> spec) {
        List<BoardEntity> questions = boardRepository.findAll(spec);
        return questions.stream()
                .map(BoardDto::toboardDto)
                .collect(Collectors.toList());

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
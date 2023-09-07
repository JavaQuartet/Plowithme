package com.example.Plowithme.service;

import com.example.Plowithme.dto.community.*;
import com.example.Plowithme.dto.community.BoardDto;
import com.example.Plowithme.dto.community.BoardUpdateDto;
import com.example.Plowithme.entity.BoardEntity;
import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.CommentException;
import com.example.Plowithme.exception.custom.FileException;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.BoardRepository;
import com.example.Plowithme.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final UserRepository userRepository;

    private final ImageService imageService;

    public void save(User currentUser, BoardSaveDto boardSaveDto) {

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니다."));

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        User optionalUserEntity=userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });

        BoardEntity boardEntity= new BoardEntity();
        boardEntity.setWriter(currentUser.getNickname());
        boardEntity.setUser(currentUser);
        boardEntity.setContents(boardSaveDto.getContents());
        boardEntity.setCategory(boardSaveDto.getCategory());
        boardEntity.setTitle(boardSaveDto.getTitle());

        if(!boardSaveDto.getFile().isEmpty()) {
            try {
                String imagePath = imageService.saveImage(boardSaveDto.getFile());

                boardEntity.setImagePath(imagePath);
            } catch (Exception e) {
                throw new RuntimeException("이미지 파일을 가져오는 것에 실패했습니다.");
            }
        }

        boardRepository.save(boardEntity);

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
    public String delete(User currentUser, Long postId) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니다."));
        BoardEntity boardEntity = boardRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("존재하지 않는 게시글입니다."));

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        if (boardEntity.getUser().getId()==currentUser.getId()) {

            if(boardEntity.getImagePath() != null) {
                imageService.deleteImage(boardEntity.getImagePath());
            }
            boardRepository.deleteById(postId);
            return "ok";
        }
        return null;
    }

    public List<BoardDto> getFilter(Specification<BoardEntity> spec) {
        List<BoardEntity> questions = boardRepository.findAll(spec);
        return questions.stream()
                .map(BoardDto::toboardDto)
                .collect(Collectors.toList());

    }


    //게시글 수정 기능
    @Transactional
    public void updatePost(User currentUser,BoardEntity boardEntity, BoardUpdateDto boardUpdateDto) {
        if (boardEntity.getUser().getId().equals(currentUser.getId())) {

            if (boardUpdateDto.getTitle() != null) {
                boardEntity.setTitle(boardUpdateDto.getTitle());
            }
            if (boardUpdateDto.getContents() != null) {
                boardEntity.setContents(boardUpdateDto.getContents());
            }
            if (boardUpdateDto.getCategory() != null) {
                boardEntity.setCategory(boardUpdateDto.getCategory());
            }


        }

    }
}
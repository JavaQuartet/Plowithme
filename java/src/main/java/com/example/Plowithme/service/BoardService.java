package com.example.Plowithme.service;

import com.example.Plowithme.dto.request.community.BoardDto;
import com.example.Plowithme.dto.request.community.BoardUpdateDto;
import com.example.Plowithme.dto.request.mypage.ProfileFindDto;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

  //  private final BoardEntity boardEntity;

    private final UserRepository userRepository;

   // private final Path root = Paths.get("uploads/profiles");

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
//        String dir = "D://";
//        UUID uuid = UUID.randomUUID();
//        String postImage = uuid + "_" + image.getOriginalFilename();
//        File saveFile = new File(dir, postImage);
////        if (!saveFile.exists()) {
//            image.transferTo(saveFile);
//            boardEntity.setPostImage(postImage);
//            boardEntity.setImagePath("/postImages/" + postImage);
//            boardEntity.setWriterId(currentUser.getId());

            boardEntity.setUser(currentUser);
            boardEntity.setContents(boardDto.getContents());
            boardEntity.setCategory(boardDto.getCategory());
            boardEntity.setTitle(boardDto.getTitle());
            boardRepository.save(boardEntity);
//        } throw new IOException ("이미지 파일이 존재하지 않습니다");

//        boardEntity.setWriterId(currentUser.getId());
//        boardEntity.setUser(currentUser);
//        boardEntity.setContents(boardDto.getContents());
//        boardEntity.setCategory(boardDto.getCategory());
//        boardEntity.setTitle(boardDto.getTitle());
        //toSaveEntity(boardDto);
       // userRepository.save(user);

      //  boardRepository.save(boardEntity);
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
    public String delete(User currentUser, Long postId) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니다."));

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        if (boardRepository.findById(postId).get().getWriterId()==currentUser.getId()) {
            userRepository.save(user);
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


    @Transactional //DB에 반영이 안 됐는데 @Transactional 넣으니 해결됨...
    //게시글 수정 기능
    public void updatePost(User currentUser,BoardEntity boardEntity, BoardUpdateDto boardUpdateDto) {
        if (boardEntity.getWriterId().equals(currentUser.getId())) {
            //BoardEntity boardEntity=BoardEntity.toSaveEntity(boardDto);
            //userRepository.save(user);
           //boardRepository.save(boardEntity);
            if (boardUpdateDto.getTitle() != null) {
                boardEntity.setTitle(boardUpdateDto.getTitle());
            }
            if (boardUpdateDto.getContents() != null) {
                boardEntity.setContents(boardUpdateDto.getContents());
            }
            if (boardUpdateDto.getCategory() != null) {
                boardEntity.setCategory(boardUpdateDto.getCategory());
            }

        } else throw new CommentException("게시글 수정 권한이 없습니다.");



//        Optional<BoardEntity> board=boardRepository.findById(postId);
//        BoardEntity boardEntity=board.orElseThrow(() -> new NotFoundException("No post searched"));
      //  BoardDto boardDto=BoardDto.toboardDto(boardEntity);

//        boardEntity.setTitle(boardDto.getTitle());
//        boardEntity.setContents(boardDto.getContents());
//        boardEntity.setCategory(boardDto.getCategory());

    }

    //게시글 이미지 등록 기능
    public void saveImage(User currentUser, MultipartFile image) throws Exception {

        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("로그인이 필요합니다."));

        if(!user.getId().equals(currentUser.getId())){
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }

        User optionalUserEntity=userRepository.findById(currentUser.getId()).orElseThrow(() -> {
            return new ResourceNotFoundException("유저를 찾을 수 없습니다.");
        });

        BoardEntity boardEntity= new BoardEntity();

        String dir = "D://";
        UUID uuid = UUID.randomUUID();
        String postImage = uuid + "_" + image.getOriginalFilename();
        File saveFile = new File(dir, postImage);

        image.transferTo(saveFile);
        boardEntity.setPostImage(postImage);
        boardEntity.setImagePath("/postImages/" + postImage);

//        boardRepository.save(boardEntity);
//        UUID uuid = UUID.randomUUID();
//        String postImage = uuid + "_" + image.getOriginalFilename();

        //File saveFile = new File(postImage);

//        if (!image.isEmpty()) {
//            Path path = Paths.get(postImage).toAbsolutePath();
//            image.transferTo(path.toFile());
//            //Files.copy(image.getInputStream(), this.root.resolve(postImage));
//            boardEntity.setPostImage(postImage);
//            boardEntity.setImagePath("/postImages/" + postImage);
//        } throw new IOException("이미지 파일 업로드에 실패하였습니다.");



    }
//
//    public BoardDto showImage(BoardDto boardDto) {
//        boardRepository.find(boardDto.getPostImage());
//    }
//

//    @Transactional
//    public ProfileFindDto findWriterProfile(BoardDto forProfileDto, Long writerId) {
//
//        User user = userRepository.findById(forProfileDto.getWriterId()).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));
//
//        try {
//            ProfileFindDto profileFindDto = ProfileFindDto.builder()
//                    .profile_url(Paths.get("uploads/profiles").resolve(user.getProfile()).toUri().toURL().toString())
//                    .nickname(user.getNickname())
//                    .introduction(user.getIntroduction())
//                    .build();
//            return profileFindDto;
//
//        } catch (Exception e) {
//            throw new FileException("파일을 조회할 수 없습니다.");
//        }
//    }



}
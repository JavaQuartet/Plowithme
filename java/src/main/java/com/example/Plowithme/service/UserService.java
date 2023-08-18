package com.example.Plowithme.service;

import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepository userRepository;
//회원 계정 설정



//    private final Path root = Paths.get("uploads");
//
//    //프로필 업로드 초기 설정
//    public void init() {
//        try {
//            Files.createDirectories(root);
//        } catch (IOException e) {
//            throw new RuntimeException("파일 업로드 폴더 생성 안됨");
//        }
//    }
//
//    //프로필 저장
//    public void save(MultipartFile file) {
//        try {
//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
//            UUID gi =
//            UserRepository.findById()
//        } catch (Exception e) {
//            if (e instanceof FileAlreadyExistsException) {
//                throw new RuntimeException("파일 이름 이미 존재");
//            }
//
//            throw new RuntimeException(e.getMessage());
//        }
//    }
//
//  //프로필 조화
//    public Resource load(String filename) {
//        try {
//            Path file = root.resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                throw new RuntimeException("파일을 읽음 불가");
//            }
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("파일 다운로드 불가");
//        }
//    }




}



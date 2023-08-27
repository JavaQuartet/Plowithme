package com.example.Plowithme.service;

import com.example.Plowithme.entity.User;
import com.example.Plowithme.exception.custom.FileException;
import com.example.Plowithme.exception.custom.ResourceNotFoundException;
import com.example.Plowithme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class ImageService {

    private final UserRepository userRepository;

    private final Path root = Paths.get("uploads");



    //이미지 저장
    @Transactional
    public String saveImage(MultipartFile file) {

        if(file.isEmpty()){
            throw new FileException("파일이 없습니다.");
        }


        //확장자 제한
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(!ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("png")){
            throw new FileException("업로드가 불가능한 확장자입니다.");
        }

        String imageName = UUID.randomUUID()+file.getOriginalFilename();
        try {

        Files.copy(file.getInputStream(), this.root.resolve(imageName));
        return imageName;

        } catch (Exception e) {
            throw new FileException("파일을 저장할 수 없습니다.");
    }

}

    @Transactional
    //이미지 수정
    public String updateImage(MultipartFile file,  Long id, String oldImageName) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("유저를 찾을 수 없습니다."));

        if(file.isEmpty()){
            throw new FileException("파일이 없습니다.");
        }

        //확장자 제한
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(!ext.equals("jpeg") && !ext.equals("jpg") && !ext.equals("png") ){
            throw new FileException("업로드가 불가능한 확장자입니다.");
        }

        String newImageName = UUID.randomUUID()+file.getOriginalFilename();
        try {

            //원래 사진 삭제
            Path files = root.resolve(oldImageName);
            Files.deleteIfExists(files);
            //사진 업로드
            Files.copy(file.getInputStream(), this.root.resolve(newImageName));
            Path imagePath = root.resolve(newImageName);

            return newImageName;


        } catch (Exception e) {
            throw new FileException("파일을 수정할 수 없습니다.");
        }

    }

    //이미지 삭제
    @Transactional
    public boolean deleteImage(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


}

package com.example.Plowithme.Service;

import com.example.Plowithme.Entity.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ProfileStore {
    @Value("${file.dir}")
    private String fileDir;

    //전체 경로
    public String getFullPath(String filename) {
        return fileDir + filename;
    }
    //프로필 저장
    public Profile storeFile(MultipartFile multipartFile) throws IOException
    {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new Profile(originalFilename, storeFileName);
    }

    //서버 내부 프로필명 생성
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자 추출
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}


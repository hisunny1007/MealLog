package com.yunyun.meallog.global.common;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.upload-dir}")
    // spring 환경 설정 값을 직접 필드에 주입할 때 사용
    private String uploadDir;;

    private static final List<String> DEFAULT_MEAL_IMAGES = List.of(
            "default/meal1.png",
            "default/meal2.png"
    );


    public String saveFile(MultipartFile image) throws FileUploadException {
        try {
            // 현재 프로젝트 디렉토리 (절대 경로 가져옴)
            String projectDir = Paths.get("").toAbsolutePath().toString();

            // 디렉토리 관련 객체 생성
            File directory = new File(projectDir, uploadDir);

            // 디렉토리가 없으면 생성
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 원본 파일명
            String originalName = image.getOriginalFilename();

            // 저장할 파일명 (UUID + 원본파일명)
            String savedFileName = UUID.randomUUID() + "_" + originalName;

            // 파일 저장
            image.transferTo(new File(directory, savedFileName));

            return savedFileName;


        } catch (IOException e){
            throw new FileUploadException();
        }
    }

    // 랜덤이미지
    public String getRandomDefaultMealImage() {
        int idx = (int) (Math.random() * DEFAULT_MEAL_IMAGES.size());
        return DEFAULT_MEAL_IMAGES.get(idx);
    }
}
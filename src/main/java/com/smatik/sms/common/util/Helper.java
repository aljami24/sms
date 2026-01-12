package com.smatik.sms.common.util;

import com.smatik.sms.common.constants.Constants;
import com.smatik.sms.student.model.dto.request.FileUpload;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Helper Class
 * Author: jami
 * Created On: 2026-01-07
 * Module:
 */

public class Helper {

    public static void studentFilesUpload(String uploadDir, FileUpload fileUpload) {
        try {
            if (fileUpload.getPhoto() != null && !fileUpload.getPhoto().isEmpty()) {
                String path = Constants.STUDENT_PHOTO_PATH + fileUpload.getId();
                docUpload(uploadDir + path, fileUpload.getPhoto());
                fileUpload.setPhotoDir(path);
            }
            if (fileUpload.getNid() != null && !fileUpload.getNid().isEmpty()) {
                String path = Constants.STUDENT_NID_DOB_PATH + fileUpload.getId();
                docUpload(uploadDir + path, fileUpload.getNid());
                fileUpload.setNidDir(path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void employeeFilesUpload(String uploadDir, FileUpload fileUpload) {
        try {
            if (fileUpload.getPhoto() != null && !fileUpload.getPhoto().isEmpty()) {
                String path = Constants.EMPLOYEE_PHOTO_PATH + fileUpload.getId();
                docUpload(uploadDir + path, fileUpload.getPhoto());
                fileUpload.setPhotoDir(path);
            }
            if (fileUpload.getNid() != null && !fileUpload.getNid().isEmpty()) {
                String path = Constants.EMPLOYEE_NID_DOB_PATH + fileUpload.getId();
                docUpload(uploadDir + path, fileUpload.getNid());
                fileUpload.setNidDir(path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static <T> void docUpload(String filePath, T document) {
        try {
            Path formPath = Paths.get(filePath);
            Files.createDirectories(formPath.getParent());
            Files.copy(((MultipartFile) document).getInputStream(), formPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudentAllFiles(String uploadDir, Long id) {
        deliteFile(uploadDir + Constants.STUDENT_PHOTO_PATH + id);
        deliteFile(uploadDir + Constants.STUDENT_NID_DOB_PATH + id);

    }

    public static void deleteEmployeeAllFiles(String uploadDir, Long id) {
        deliteFile(uploadDir + Constants.EMPLOYEE_PHOTO_PATH + id);
        deliteFile(uploadDir + Constants.EMPLOYEE_NID_DOB_PATH + id);

    }

    public static void deliteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

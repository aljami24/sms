package com.smatik.sms.student.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUpload Class
 * Author: jami
 * Created On: 2026-01-07
 * Module:
 */

@Getter
@Setter
public class FileUpload {

    private Long id;
    private MultipartFile photo;
    private String photoDir;
    private MultipartFile nid;
    private String nidDir;

}

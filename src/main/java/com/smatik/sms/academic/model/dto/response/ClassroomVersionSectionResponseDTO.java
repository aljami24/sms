package com.smatik.sms.academic.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomVersionSectionResponseDTO {

    private Long id;
    private String className;
    private String versionName;
    private String sectionName;
}

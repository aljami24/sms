package com.smha.sms.academic.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomVersionSectionRequestDTO {

    private Long classRoomId;
    private Long versionId;
    private Long sectionId;
}

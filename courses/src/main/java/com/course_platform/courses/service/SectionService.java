package com.course_platform.courses.service;

import com.course_platform.courses.dto.request.SectionRequest;
import com.course_platform.courses.dto.response.Section;
import com.course_platform.courses.entity.SectionEntity;

import java.util.List;

public interface SectionService extends BaseService<SectionEntity,Section> {
    List<Section> findAll();
    List<Section> findByCourseCode(String course);

    Section create(SectionRequest sectionRequest);

    Section update(String sectionId, SectionRequest sectionRequest);
}

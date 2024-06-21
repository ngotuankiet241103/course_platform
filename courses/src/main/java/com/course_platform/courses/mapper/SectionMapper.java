package com.course_platform.courses.mapper;

import com.course_platform.courses.dto.request.SectionRequest;
import com.course_platform.courses.dto.response.Section;
import com.course_platform.courses.entity.SectionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    SectionEntity toSectionEntity(SectionRequest sectionRequest);
    @Mapping(target = "lessons",ignore = true)
    Section toSection(SectionEntity sectionEntity);
}

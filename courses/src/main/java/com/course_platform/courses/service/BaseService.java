package com.course_platform.courses.service;

import com.course_platform.courses.dto.response.Course;

import java.util.List;

public interface BaseService<E,T> {

    T mappingOne(E e);
    List<T> mappingList(List<E> e);

}

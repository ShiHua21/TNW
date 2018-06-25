package com.jic.tnw.web.api.service;

import com.jic.tnw.db.mysql.tables.pojos.Question;
import com.jic.elearning.web.api.dto.course.AddCourseSubjectDTO;

public interface QuestionService {
    /**
     *
     * @param addCourseSubjectDTO
     * @return
     */
    Question addQuestionSubject(AddCourseSubjectDTO addCourseSubjectDTO);
}

package com.jic.tnw.web.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jic.tnw.db.mysql.tables.pojos.Question;
import com.jic.tnw.db.repository.QuestionRepository;
import com.jic.elearning.web.api.dto.course.AddCourseSubjectDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class QuestionServiceImpl implements QuestionService {
    private static final Log LOGGER = LogFactory.getLog(CourseServiceImpl.class);

    private final ObjectMapper mapper;
    private final QuestionRepository questionRepository;

    @Autowired
    QuestionServiceImpl(ObjectMapper mapper, QuestionRepository questionRepository) {
        this.mapper = mapper;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Question addQuestionSubject(AddCourseSubjectDTO addCourseSubjectDTO) {
        //单选提INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, metas, target) VALUES ('0', '0', 'normal', '<p>sad</p>\r\n', '[\"0\"]', '', '2', 'single_choice', '0', '33', '3', '3', '1524452510', '1524452510', '{\"choices\":[\"<p>\\u770b\\u770b<\\/p>\\r\\n\",\"<p>t\\u5077\\u5077<\\/p>\\r\\n\",\"<p>q\\u8bf7\\u6c42<\\/p>\\r\\n\"]}', 'course-33')
        //材料题  INSERT INTO question (courseId, lessonId, difficulty, stem, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, target) VALUES ('0', '0', 'normal', '<p>阿斯顿打算打点滴</p>\r\n', '', '2', 'material', '0', '35', '3', '3', '1524452690', '1524452690', 'course-35')
        //不定向选择题 INSERT INTO question (courseId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, metas, target) VALUES ('0', 'normal', '<p>1+1=？</p>\r\n', '[\"1\"]', '', '2', 'uncertain_choice', '0', '35', '3', '3', '1524453904', '1524453904', '{\"choices\":[\"<p>1<\\/p>\\r\\n\",\"<p>2<\\/p>\\r\\n\",\"<p>3<\\/p>\\r\\n\",\"<p>4<\\/p>\\r\\n\"]}', 'course-35')
        //问答题 INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, target) VALUES ('0', '0', 'difficulty', '<p>1*1=？</p>\r\n', '[\"<p>2<\\/p>\\n\"]', '', '2', 'essay', '0', '35', '3', '3', '1524462252', '1524462252', 'course-35')
        //判断题 INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, target) VALUES ('0', '0', 'normal', '<p>7*8=56</p>\r\n', '[\"1\"]', '', '2', 'determine', '0', '35', '3', '3', '1524463220', '1524463220', 'course-35')
        //填空题 INSERT INTO question (courseId, lessonId, difficulty, stem, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, answer, target) VALUES ('0', '0', 'normal', '<p>1*3=[[]][[]]23</p>\r\n', '<p>啊当初的纯纯粹粹</p>\n', '2', 'fill', '0', '35', '3', '3', '1524463901', '1524463901', '[[\"]][[\"]]', 'course-35')
        //多选题 INSERT INTO question (courseId, lessonId, difficulty, stem, answer, analysis, score, type, parentId, courseSetId, createdUserId, updatedUserId, createdTime, updatedTime, metas, target) VALUES ('0', '0', 'normal', '<p>动物类()</p>\r\n', '[\"0\",\"1\"]', '', '2', 'choice', '0', '35', '3', '3', '1524465846', '1524465846', '{\"choices\":[\"<p>\\u732a<\\/p>\\n\",\"<p>\\u72d7<\\/p>\\n\",\"<p>\\u9999\\u8549<\\/p>\\n\",\"<p>\\u9ec4\\u74dc<\\/p>\\n\"]}', 'course-35')


        Question question = new Question();
        question.setCourseId(0);
        question.setLessonId(0);
        question.setDifficulty(addCourseSubjectDTO.getDifficuty());


        return null;
    }
}

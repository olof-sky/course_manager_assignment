package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        return converters.courseToCourseView(courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration()));
    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        return null;
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        return converters.coursesToCourseViews(courseDao.findByNameContains(courseName));
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        return null;
    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        return null;
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        return courseDao.findById(courseId).enrollStudent(studentDao.findById(studentId));
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        return courseDao.findById(courseId).unEnrollStudent(studentDao.findById(studentId));
    }

    @Override
    public CourseView findById(int id) { return converters.courseToCourseView(courseDao.findById(id)); }

    @Override
    public List<CourseView> findAll() { return converters.coursesToCourseViews(courseDao.findAll()); }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        List<CourseView> courses = new ArrayList<>();
        for (Course course : courseDao.findAll()){
            for (Student student : course.getStudents()){
                if (student.getId() == studentId){
                    courses.add(converters.courseToCourseView(course));
                }else{return courses;}
            }
        }
        return courses;
    }

    @Override
    public boolean deleteCourse(int id) {
        return courseDao.removeCourse(courseDao.findById(id));
    }
}

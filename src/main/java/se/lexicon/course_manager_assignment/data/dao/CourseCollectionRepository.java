package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) { this.courses = courses; }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
            if (courseName != null && startDate != null){
                Course course = new Course(CourseSequencer.nextCourseId(), courseName, startDate, weekDuration);
                courses.add(course);
                return course;
            }return null;
    }

    @Override
    public Course findById(int id) {
        for (Course course : courses){
            if (course.getId() == id){
                return course;
            }
        }return null;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        Collection<Course> courseCollection = new HashSet<>();
        for (Course course : courses){
            if (course.getCourseName().contains(name)){
                courseCollection.add(course);
            }return courseCollection;
        }
        return null;
    }

    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        Collection<Course> coursesReturn = new ArrayList<>();
        for (Course course : courses){
                if (course.getStartDate().isBefore(end)){
                    coursesReturn.add(course);
                }return coursesReturn;
            }
        return null;
    }

    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        Collection<Course> coursesReturn = new ArrayList<>();
        for (Course course : courses){
            if (course.getStartDate().isAfter(start)){
                coursesReturn.add(course);
            }return coursesReturn;
        }
        return null;
    }

    @Override
    public Collection<Course> findAll() {
        return courses;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        Collection<Course> courses = new ArrayList<>();
        for (Course course : courses){
            for (Student student : course.getStudents()){
                if (student.getId() == studentId){ courses.add(course); }
            }return courses;
        }
        return null;
    }

    @Override
    public boolean removeCourse(Course course) {
        courses.remove(course);
        return false;
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }
}

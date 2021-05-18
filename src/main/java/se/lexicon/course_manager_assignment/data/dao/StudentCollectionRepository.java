package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;
import java.util.HashSet;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        Student student = new Student(StudentSequencer.nextStudentId(), name, email, address);
        students.add(student);
        return student;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        for (Student student : students){
            if (student.getEmail().equalsIgnoreCase(email)){
                return student;
            }
        }
        return null;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        Collection<Student> studentCollection = new HashSet<>();
        for (Student student : students){
            if (student.getName().contains(name)){
                studentCollection.add(student);
            }
            return studentCollection;
        }
        return null;
    }

    @Override
    public Student findById(int id) {
        for (Student student : students){
            if (student.getId() == id){
                return student;
            }
        }
        return null;
    }

    @Override
    public Collection<Student> findAll() {
        return students;
    }

    @Override
    public boolean removeStudent(Student student) {
        students.remove(student);
        return false;
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }
}

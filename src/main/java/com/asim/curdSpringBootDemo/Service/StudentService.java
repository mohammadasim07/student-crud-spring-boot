package com.asim.curdSpringBootDemo.Service;

import com.asim.curdSpringBootDemo.entity.Student;
import com.asim.curdSpringBootDemo.repository.StudentRepostiroy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    //1 work -: end point ko listen kar(/app/student post);
    //2 work -: business logic to store in data nase'
    //3 work interact with database;
    //4 response back to clint(Postman);
    private StudentRepostiroy studentRepostiroy;

    public StudentService(StudentRepostiroy studentRepostiroy){
        this.studentRepostiroy = studentRepostiroy;
    }

    public Student createStudent(Student studentReq){
        // have to write business logic
        //store to db but service ka kaam hai repositry ko bataye ki db me save karo;

        Student student = studentRepostiroy.save(studentReq);

        return student;
    }

    public Student readStudent(Long id){
        //optional ka matlab value ho bhi sakta hai
        Optional<Student> student1 = studentRepostiroy.findById(id);
        if(student1.isPresent()){
            return student1.get();
        }
        return null;
    }

    public List<Student> readAllStudent(){
        List<Student> studentList = studentRepostiroy.findAll();
        return studentList;
    }

    public Student updateStudent(Long id, @RequestBody Student student){
        Optional<Student> studentResp = studentRepostiroy.findById(id);
        if(studentResp.isEmpty())return null;

        Student studentToSave = studentResp.get();
        studentToSave.setAge(student.getAge());
        studentToSave.setRollNo(student.getRollNo());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setName(student.getName());
        studentToSave.setSubject(student.getSubject());
        return studentRepostiroy.save(studentToSave);
    }

    // for delete
    public Boolean deleteStudent(Long id){
        Boolean isStudent = studentRepostiroy.existsById(id);
        if(!isStudent)return false;
        studentRepostiroy.deleteById(id);
        return true;
    }

}

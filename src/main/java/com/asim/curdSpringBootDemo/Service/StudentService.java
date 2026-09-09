package com.asim.curdSpringBootDemo.Service;

import com.asim.curdSpringBootDemo.dto.StudentRequestDto;
import com.asim.curdSpringBootDemo.dto.StudentResponseDTO;
import com.asim.curdSpringBootDemo.entity.Student;
import com.asim.curdSpringBootDemo.repository.StudentRepostiroy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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



    public StudentResponseDTO createStudent(StudentRequestDto studentRequestDto){
       Student student = mapToEntity(studentRequestDto);
       Student studentResp = studentRepostiroy.save(student);

       return mapToDto(studentResp);
    }

    public Student readStudent(Long id){
        //optional ka matlab value ho bhi sakta hai
        Optional<Student> student1 = studentRepostiroy.findByIdAndDeletedIsFalse(id);
        if(student1.isPresent()){
            return student1.get();
        }
        return null;
    }

    public List<Student> readAllStudent(){
        List<Student> studentList = studentRepostiroy.findByDeletedIsFalse();
        return studentList;
    }

    public Student updateStudent(Long id, @RequestBody Student student){
        Optional<Student> studentResp = studentRepostiroy.findByIdAndDeletedIsFalse(id);
        if(studentResp.isEmpty())return null;

        Student studentToSave = studentResp.get();
        studentToSave.setAge(student.getAge());
        studentToSave.setRollNo(student.getRollNo());
        studentToSave.setEmail(student.getEmail());
        studentToSave.setName(student.getName());
        studentToSave.setSubject(student.getSubject());
        studentToSave.setDeleted(false);
        return studentRepostiroy.save(studentToSave);
    }

    // for delete
    public Boolean deleteStudent(Long id){
        Boolean isStudent = studentRepostiroy.existsById(id);
        if(!isStudent)return false;
        studentRepostiroy.deleteById(id);
        return true;
    }

    public Boolean deleteStudentSoftly(Long id){
        Optional<Student> student1 = studentRepostiroy.findByIdAndDeletedIsFalse(id);
        if(student1.isEmpty()) return false;

        Student studentToSave = student1.get();
        studentToSave.setDeleted(true);
        studentRepostiroy.save(studentToSave);
        return true;
    }

    private Student mapToEntity(StudentRequestDto studentRequestDto){
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setRollNo(studentRequestDto.getRollNo());
        student.setSubject(studentRequestDto.getSubject());
        student.setDeleted(false);

        return student;
    }

    private StudentResponseDTO mapToDto(Student student){
        StudentResponseDTO studentResponseDTO = new StudentResponseDTO();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setAge(student.getAge());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setRollNo(student.getRollNo());
        studentResponseDTO.setSubject(student.getSubject());
        studentResponseDTO.setMessage("Response done");
        return studentResponseDTO;
    }
}

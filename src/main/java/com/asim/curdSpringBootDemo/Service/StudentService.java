package com.asim.curdSpringBootDemo.Service;

import com.asim.curdSpringBootDemo.dto.CreateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.CreateStudentResponseDTO;
import com.asim.curdSpringBootDemo.dto.updateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.updateStudentResponesDto;
import com.asim.curdSpringBootDemo.entity.Student;
import com.asim.curdSpringBootDemo.repository.StudentRepostiroy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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



    public CreateStudentResponseDTO createStudent(CreateStudentRequestDto studentRequestDto){
       Student student = mapToEntity(studentRequestDto);
       student.setCreatedAt(LocalDateTime.now());
       student.setUpdatedAt(LocalDateTime.now());
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

    public updateStudentResponesDto updateStudent(Long id, updateStudentRequestDto student){
        Optional<Student> studentResp = studentRepostiroy.findByIdAndDeletedIsFalse(id);
        if(studentResp.isEmpty())return null;

        Student studentToSave = studentResp.get();
        studentToSave.setAge(student.getAge());
        studentToSave.setRollNo(student.getRollNo());
        studentToSave.setName(student.getName());
        studentToSave.setSubject(student.getSubject());
        studentToSave.setDeleted(false);
        studentToSave.setUpdatedAt(LocalDateTime.now());
        Student savedStudent = studentRepostiroy.save(studentToSave);
        return mapUpdateToDto(savedStudent);
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

    private Student mapToEntity(CreateStudentRequestDto studentRequestDto){
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setRollNo(studentRequestDto.getRollNo());
        student.setSubject(studentRequestDto.getSubject());
        student.setDeleted(false);

        return student;
    }

    private CreateStudentResponseDTO mapToDto(Student student){
        CreateStudentResponseDTO studentResponseDTO = new CreateStudentResponseDTO();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setAge(student.getAge());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setRollNo(student.getRollNo());
        studentResponseDTO.setSubject(student.getSubject());
        studentResponseDTO.setMessage("Response done");
        studentResponseDTO.setCreateAt(student.getCreatedAt());
        studentResponseDTO.setUpdatedAt(student.getUpdatedAt());
        return studentResponseDTO;
    }
    private updateStudentResponesDto mapUpdateToDto(Student student){
        updateStudentResponesDto studentResponseDTO = new updateStudentResponesDto();
        studentResponseDTO.setId(student.getId());
        studentResponseDTO.setName(student.getName());
        studentResponseDTO.setAge(student.getAge());
        studentResponseDTO.setEmail(student.getEmail());
        studentResponseDTO.setRollNo(student.getRollNo());
        studentResponseDTO.setSubject(student.getSubject());
        studentResponseDTO.setupdatedAt(student.getUpdatedAt());
        studentResponseDTO.setMessage("Updated successfully");
        return studentResponseDTO;
    }
}

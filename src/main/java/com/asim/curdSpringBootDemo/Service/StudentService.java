package com.asim.curdSpringBootDemo.Service;

import com.asim.curdSpringBootDemo.dto.CreateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.CreateStudentResponseDTO;
import com.asim.curdSpringBootDemo.dto.updateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.updateStudentResponesDto;
import com.asim.curdSpringBootDemo.entity.Student;
import com.asim.curdSpringBootDemo.exception.DublicateException;
import com.asim.curdSpringBootDemo.exception.ResourceNotFoundException;
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



    public CreateStudentResponseDTO createStudent(CreateStudentRequestDto studentRequestDto) {
       Student student = mapToEntity(studentRequestDto);
        if(existsByEmail(student)){
            throw new DublicateException("Email exist");
        }
       Student studentResp = studentRepostiroy.save(student);

       return mapToDto(studentResp);
    }

    public CreateStudentResponseDTO readStudent(Long id){
       Student studentRes = studentRepostiroy.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not found Id is " + id));
       return mapToDto(studentRes);
    }

    public List<CreateStudentResponseDTO> readAllStudent(){
        List<Student> studentList = studentRepostiroy.findByDeletedIsFalse();
        return studentList.stream().map(this::mapToDto).toList();
    }

    public updateStudentResponesDto updateStudent(Long id, updateStudentRequestDto student){
        Student studentToSave = studentRepostiroy.findByIdAndDeletedIsFalse(id).orElseThrow(()-> new  ResourceNotFoundException("Not Found to Update"));

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
    public void deleteStudent(Long id){
       Student student = studentRepostiroy.findById(id).orElseThrow(()-> new ResourceNotFoundException("Data not found on id to delete"));

        studentRepostiroy.delete(student);
    }

    public void deleteStudentSoftly(Long id){
        Student studentToSave = studentRepostiroy.findByIdAndDeletedIsFalse(id).orElseThrow(()-> new ResourceNotFoundException("Soft delete id is not found"));

        studentToSave.setDeleted(true);
        studentRepostiroy.save(studentToSave);

    }

    private Student mapToEntity(CreateStudentRequestDto studentRequestDto){
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setAge(studentRequestDto.getAge());
        student.setEmail(studentRequestDto.getEmail());
        student.setRollNo(studentRequestDto.getRollNo());
        student.setSubject(studentRequestDto.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
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

    private boolean existsByEmail(Student student){
        return studentRepostiroy.existsByEmail(student.getEmail());
    }
}

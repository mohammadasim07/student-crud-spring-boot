package com.asim.curdSpringBootDemo.Controller;

import com.asim.curdSpringBootDemo.Service.StudentService;
import com.asim.curdSpringBootDemo.dto.CreateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.CreateStudentResponseDTO;
import com.asim.curdSpringBootDemo.dto.updateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.updateStudentResponesDto;
import com.asim.curdSpringBootDemo.entity.Student;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    //createl
    @PostMapping
    public ResponseEntity<CreateStudentResponseDTO> createStudent( @Valid @RequestBody CreateStudentRequestDto studentRequestDto){
        CreateStudentResponseDTO student =
                studentService.createStudent(studentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(student);
    }

    //read for db
    @GetMapping("/{id}")
    public ResponseEntity< CreateStudentResponseDTO> readStudent(@PathVariable Long id){
        CreateStudentResponseDTO readStudent = studentService.readStudent(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStudent);
    }

    // get all
    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDTO>> readAllStudent(){
        List<CreateStudentResponseDTO> readStudentList = studentService.readAllStudent();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStudentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<updateStudentResponesDto> updateStudent(@PathVariable Long id, @RequestBody updateStudentRequestDto updateRequestDto){
        updateStudentResponesDto readStudent =
                studentService.updateStudent(id, updateRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudnet(@PathVariable Long id){
         studentService.deleteStudent(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteStudentSoftly(@PathVariable Long id){
        studentService.deleteStudentSoftly(id);
        return ResponseEntity.noContent().build();
    }
}

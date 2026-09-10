package com.asim.curdSpringBootDemo.Controller;

import com.asim.curdSpringBootDemo.Service.StudentService;
import com.asim.curdSpringBootDemo.dto.CreateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.CreateStudentResponseDTO;
import com.asim.curdSpringBootDemo.dto.updateStudentRequestDto;
import com.asim.curdSpringBootDemo.dto.updateStudentResponesDto;
import com.asim.curdSpringBootDemo.entity.Student;
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

    //create
    @PostMapping("/create")
    public ResponseEntity<CreateStudentResponseDTO> createStudent(@RequestBody CreateStudentRequestDto studentRequestDto){
        CreateStudentResponseDTO student =
                studentService.createStudent(studentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(student);
    }

    //read for db
    @GetMapping("/get/{id}")
    public ResponseEntity<Student> readStudent(@PathVariable Long id){
        Student readStudent = studentService.readStudent(id);
        if(readStudent == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStudent);
    }

    // get all
    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> readAllStudent(){
        List<Student> readStudentList = studentService.readAllStudent();
        if(readStudentList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStudentList);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<updateStudentResponesDto> updateStudent(@PathVariable Long id, @RequestBody updateStudentRequestDto updateRequestDto){
        updateStudentResponesDto readStudent =
                studentService.updateStudent(id, updateRequestDto);

        if (readStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStudent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudnet(@PathVariable Long id){
        Boolean isDelete = studentService.deleteStudent(id);
        if(!isDelete){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record deleted");
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteStudentSoftly(@PathVariable Long id){
       Boolean isDeleted = studentService.deleteStudentSoftly(id);
       if(!isDeleted) return ResponseEntity.notFound().build();

       return ResponseEntity.ok("Deleted Softly");
    }
}

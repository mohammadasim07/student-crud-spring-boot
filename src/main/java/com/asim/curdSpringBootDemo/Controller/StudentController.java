package com.asim.curdSpringBootDemo.Controller;

import com.asim.curdSpringBootDemo.Service.StudentService;
import com.asim.curdSpringBootDemo.dto.StudentRequestDto;
import com.asim.curdSpringBootDemo.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
    public ResponseEntity<Student> createStudent(@RequestBody StudentRequestDto studentRequestDto){
       Student student = studentService.createStudent(studentRequestDto);
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
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student){
        Student readStudent = studentService.updateStudent(id, student);
        if(readStudent == null){
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

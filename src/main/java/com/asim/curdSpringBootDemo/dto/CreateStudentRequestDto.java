package com.asim.curdSpringBootDemo.dto;


import jakarta.validation.constraints.*;

public class CreateStudentRequestDto {

    @NotBlank(message = "Name not be NULL/Empty or blank")
    @Size(min = 2,max = 50, message = "Student must be under 2-50 character")
    private String name ;

    @Email
    private String email ;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be greater than 18")
    private Integer age ;

    @NotNull()
    private Integer rollNo;

    @NotBlank(message = "Subject is NULL/empty or blank")
    @Size(min = 3,max = 50)
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

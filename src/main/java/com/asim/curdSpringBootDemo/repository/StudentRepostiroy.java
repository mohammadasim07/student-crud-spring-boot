package com.asim.curdSpringBootDemo.repository;

import com.asim.curdSpringBootDemo.entity.Student;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface StudentRepostiroy extends JpaRepository<Student, Long> {
// do not have to write the abstract method here that will be done by spring framwork
}

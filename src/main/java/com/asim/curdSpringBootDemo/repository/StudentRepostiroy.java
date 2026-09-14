package com.asim.curdSpringBootDemo.repository;

import com.asim.curdSpringBootDemo.entity.Student;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface StudentRepostiroy extends JpaRepository<Student, Long> {
// do not have to write the abstract method here that will be done by spring framwork
    Optional<Student> findByIdAndDeletedIsFalse(Long id);
    List<Student> findByDeletedIsFalse();
    //findBy + fieldName + condition
    boolean existsByEmail(String email);

}

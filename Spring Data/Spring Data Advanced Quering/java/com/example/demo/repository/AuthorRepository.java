package com.example.demo.repository;

import com.example.demo.domain.entities.Author;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findAuthorById(Integer id);

    List<Author> findAllByFirstNameEndsWith(String endsWith);

    List<Author> findAllByLastNameStartingWith(String startsWith);

//    @Procedure(procedureName = "udp_get_books_count_by_author_first_last_names")
//    int getBooksCountByFirstAndLastName(String firstName, String LastName);

    @Query(value = "CALL udp_get_books_count_by_author_first_last_names(:first_name, :last_name);", nativeQuery = true)
    int getBooksCountByFirstAndLastName(@Param("first_name") String firstName, @Param("last_name") String lastName);
}

package com.example.demo.repository;

import com.example.demo.domain.entities.Book;
import com.example.demo.domain.entities.enums.AgeRestriction;
import com.example.demo.domain.entities.enums.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByReleaseDateAfter(Date after);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceGreaterThanOrPriceLessThan(BigDecimal higher, BigDecimal lower);

    List<Book> findAllByReleaseDateAfterOrReleaseDateBefore(Date before, Date after);

    List<Book> findAllByReleaseDateBefore(Date date);

    List<Book> findAllByTitleContaining(String input);

    @Query(value = "SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :characters")
    int findCountByTitleLongerThan(@Param("characters") int characters);
}
package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.BMIData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BMIDataRepository extends JpaRepository<BMIData, Long>{
    Optional<BMIData> findBySexAndAge(int sex, double age);

    @Query("SELECT b FROM BMIData b WHERE b.sex = ?1 AND ABS(b.age - ?2) = (SELECT MIN(ABS(b2.age - ?2)) FROM BMIData b2 WHERE b2.sex = ?1)")
    Optional<BMIData> findClosestBySexAndAge(int sex, double age);
}

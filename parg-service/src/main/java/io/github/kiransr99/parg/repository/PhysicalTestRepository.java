package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.PhysicalTest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhysicalTestRepository extends JpaRepository<PhysicalTest, Long> {

}

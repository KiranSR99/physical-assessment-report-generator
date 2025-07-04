package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByStatusTrue();
}

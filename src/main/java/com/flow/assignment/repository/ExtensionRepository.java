package com.flow.assignment.repository;

import com.flow.assignment.entity.Extension;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExtensionRepository extends JpaRepository<Extension, Long> {
    Extension findByName(String name);
    List<Extension> findByIsFixed(boolean isFixed);
    long countByIsFixedFalse(); //커스텀 확장자 개수 확인
    boolean existsByName(String name); //중복 체크
}
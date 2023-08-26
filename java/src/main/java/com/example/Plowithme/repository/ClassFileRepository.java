package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassFileRepository extends JpaRepository<ClassFileEntity, Long> {


}

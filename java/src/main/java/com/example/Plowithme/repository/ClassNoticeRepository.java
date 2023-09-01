package com.example.Plowithme.repository;

import com.example.Plowithme.entity.ClassNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassNoticeRepository extends JpaRepository<ClassNoticeEntity, Long> {
}

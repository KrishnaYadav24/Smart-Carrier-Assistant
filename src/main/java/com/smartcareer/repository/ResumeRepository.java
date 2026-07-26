package com.smartcareer.repository;

import com.smartcareer.domain.Resume;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository
        extends JpaRepository<Resume, Long> {

    /*
        FIND USER HISTORY
     */

    List<Resume> findByUsername(

            String username
    );
}
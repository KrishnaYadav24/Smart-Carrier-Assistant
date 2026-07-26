package com.smartcareer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcareer.domain.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}
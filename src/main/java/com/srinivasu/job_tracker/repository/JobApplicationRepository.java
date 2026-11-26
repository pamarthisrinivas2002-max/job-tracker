package com.srinivasu.job_tracker.repository;

import com.srinivasu.job_tracker.model.JobApplication;
import com.srinivasu.job_tracker.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    // Spring writes the SQL for this from the method name
    List<JobApplication> findByStatus(Status status);
}

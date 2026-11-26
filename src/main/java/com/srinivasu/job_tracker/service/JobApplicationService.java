package com.srinivasu.job_tracker.service;

import com.srinivasu.job_tracker.model.JobApplication;
import com.srinivasu.job_tracker.model.Status;
import com.srinivasu.job_tracker.repository.JobApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    private final JobApplicationRepository repository;

    // Constructor injection: Spring passes in the repository automatically
    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public List<JobApplication> getAll() {
        return repository.findAll();
    }

    public List<JobApplication> getByStatus(Status status) {
        return repository.findByStatus(status);
    }

    public JobApplication getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    public JobApplication create(JobApplication application) {
        application.setId(null); // make sure a new row is created
        return repository.save(application);
    }

    public JobApplication update(Long id, JobApplication updated) {
        JobApplication existing = getById(id);
        existing.setCompany(updated.getCompany());
        existing.setRole(updated.getRole());
        existing.setLocation(updated.getLocation());
        existing.setStatus(updated.getStatus());
        existing.setAppliedDate(updated.getAppliedDate());
        existing.setRequiresClearance(updated.isRequiresClearance());
        existing.setNotes(updated.getNotes());
        return repository.save(existing);
    }

    public void delete(Long id) {
        JobApplication existing = getById(id);
        repository.delete(existing);
    }
}

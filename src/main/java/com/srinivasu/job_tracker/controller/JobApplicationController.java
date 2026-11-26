package com.srinivasu.job_tracker.controller;

import com.srinivasu.job_tracker.model.JobApplication;
import com.srinivasu.job_tracker.model.Status;
import com.srinivasu.job_tracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationService service;

    public JobApplicationController(JobApplicationService service) {
        this.service = service;
    }

    // GET /api/applications  or  GET /api/applications?status=APPLIED
    @GetMapping
    public List<JobApplication> getAll(@RequestParam(name = "status", required = false) Status status) {
        return (status == null) ? service.getAll() : service.getByStatus(status);
    }

    // GET /api/applications/1
    @GetMapping("/{id}")
    public JobApplication getById(@PathVariable("id") Long id) {
        return service.getById(id);
    }

    // POST /api/applications
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobApplication create(@Valid @RequestBody JobApplication application) {
        return service.create(application);
    }

    // PUT /api/applications/1
    @PutMapping("/{id}")
    public JobApplication update(@PathVariable("id") Long id,
                                 @Valid @RequestBody JobApplication application) {
        return service.update(id, application);
    }

    // DELETE /api/applications/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}

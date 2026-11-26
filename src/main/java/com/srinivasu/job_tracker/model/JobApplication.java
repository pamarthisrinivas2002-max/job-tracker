package com.srinivasu.job_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Company is required")
    private String company;

    @NotBlank(message = "Role is required")
    private String role;

    private String location;

    @Enumerated(EnumType.STRING)
    private Status status = Status.APPLIED;

    private LocalDate appliedDate = LocalDate.now();

    private boolean requiresClearance;

    private String notes;

    // JPA needs an empty constructor
    public JobApplication() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getAppliedDate() { return appliedDate; }
    public void setAppliedDate(LocalDate appliedDate) { this.appliedDate = appliedDate; }

    public boolean isRequiresClearance() { return requiresClearance; }
    public void setRequiresClearance(boolean requiresClearance) { this.requiresClearance = requiresClearance; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

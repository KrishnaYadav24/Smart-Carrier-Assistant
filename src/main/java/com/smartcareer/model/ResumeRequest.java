package com.smartcareer.model;

public class ResumeRequest {

private String template;

private String fullName;
private String email;
private String phone;
private String linkedin;
private String github;

private String summary;
private String education;
private String skills;
private String projects;
private String experience;
private String certifications;

public String getTemplate() {
    return template;
}

public void setTemplate(String template) {
    this.template = template;
}

public String getFullName() {
    return fullName;
}

public void setFullName(String fullName) {
    this.fullName = fullName;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}

public String getLinkedin() {
    return linkedin;
}

public void setLinkedin(String linkedin) {
    this.linkedin = linkedin;
}

public String getGithub() {
    return github;
}

public void setGithub(String github) {
    this.github = github;
}

public String getSummary() {
    return summary;
}

public void setSummary(String summary) {
    this.summary = summary;
}

public String getEducation() {
    return education;
}

public void setEducation(String education) {
    this.education = education;
}

public String getSkills() {
    return skills;
}

public void setSkills(String skills) {
    this.skills = skills;
}

public String getProjects() {
    return projects;
}

public void setProjects(String projects) {
    this.projects = projects;
}

public String getExperience() {
    return experience;
}

public void setExperience(String experience) {
    this.experience = experience;
}

public String getCertifications() {
    return certifications;
}

public void setCertifications(String certifications) {
    this.certifications = certifications;
}

}

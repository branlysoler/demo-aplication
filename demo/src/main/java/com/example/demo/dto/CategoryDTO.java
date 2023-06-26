package com.example.demo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CategoryDTO implements Serializable{

    private Long id;

    private String name;

    private String description;

    @JsonIgnore
    private List<EmploymentDTO> employments = new ArrayList<>();
    
    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return List<EmploymentDTO> return the employments
     */
    public List<EmploymentDTO> getEmployments() {
        return employments;
    }

    /**
     * @param employments the employments to set
     */
    public void setEmployments(List<EmploymentDTO> employments) {
        this.employments = employments;
    }
}
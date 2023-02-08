package com.springgoals.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "name must be between 1 and 45 characters")
    @Size(min = 1, max = 45)
    private String name;

    @NotNull(message = "surname must be between 1 and 45 characters")
    @Size(min = 1, max = 45)
    private String surname;

    @NotNull(message = "age must not be null")
    @Min(value = 18, message = "Age should not be less than 18")
    private Integer age;

    @NotNull(message = "primary_subject1 must be between 1 and 45 characters")
    @Size(min = 1, max = 45)
    private String primary_subject1;

    @NotNull(message = "primary_subject2 must be between 1 and 45 characters")
    @Size(min = 1, max = 45)
    private String primary_subject2;

    @NotNull(message = "professor_faculty must not be null")
    private Integer professor_faculty;

    public Professor() {
    }

    public Professor(String name, String surname, String primary_subject1, String primary_subject2, Integer age) {

        this.name = name;
        this.surname = surname;
        this.age = age;
        this.primary_subject1 = primary_subject1;
        this.primary_subject2 = primary_subject2;
    }

    public Professor(String name, String surname, String primary_subject1, String primary_subject2, Integer age, Integer professor_faculty) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.primary_subject1 = primary_subject1;
        this.primary_subject2 = primary_subject2;
        this.professor_faculty = professor_faculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(id, professor.id) && Objects.equals(name, professor.name)
                && Objects.equals(surname, professor.surname)
                && Objects.equals(primary_subject1, professor.primary_subject1)
                && Objects.equals(primary_subject2, professor.primary_subject2)
                && Objects.equals(age, professor.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, primary_subject1, primary_subject2, age);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPrimary_subject1() {
        return primary_subject1;
    }

    public void setPrimary_subject1(String primary_subject1) {
        this.primary_subject1 = primary_subject1;
    }

    public String getPrimary_subject2() {
        return primary_subject2;
    }

    public void setPrimary_subject2(String primary_subject2) {
        this.primary_subject2 = primary_subject2;
    }

    public Integer getProfessor_faculty() {
        return professor_faculty;
    }

    public void setProfessor_faculty(Integer professor_faculty) {
        this.professor_faculty = professor_faculty;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", primary_subject1='" + primary_subject1 + '\'' +
                ", primary_subject2='" + primary_subject2 + '\'' +
                '}';
    }
}


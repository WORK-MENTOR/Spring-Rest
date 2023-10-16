package com.springgoals.controller;

import com.springgoals.model.Subject;
import com.springgoals.service.StudentService;
import com.springgoals.service.impl.SubjectServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import com.springgoals.exception.EntityNotFoundException;
import com.springgoals.exception.QueryException;
import com.springgoals.exception.ValidationsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    private static final Logger logger = LogManager.getLogger(SubjectController.class);
    @Autowired
    private SubjectServiceImpl subjectService;
    @Autowired
    private StudentService studentService;
    ObjectMapper objectMapper = new ObjectMapper();

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subject>> getSubjects() throws SQLException {

        List<Subject> subjects = subjectService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/map", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Integer, Subject>> mapSubject() throws SQLException {

        Map<Integer, Subject> subjects = subjectService.getMap();
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Subject>> searchSubjects(
            @RequestParam("name") String name,
            @RequestParam("semester") String semester,
            @RequestParam("credits") Integer credits
    ) throws SQLException, QueryException {
        List<Subject> subjects = null;
        if ((name == null || name.equals("")) && (semester == null || semester.equals("")) && (credits == null || credits.equals(""))) {
            logger.error("Error in searchSubjects: not enough query parameters");
            throw new QueryException("Error in searchSubjects: not enough query parameters");
        } else {
            subjects = subjectService.searchSubjects(name, semester, credits);
        }
        return ResponseEntity.status(HttpStatus.OK).body(subjects);
    }

    @PreAuthorize("hasAuthority('EDIT')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> getById(@PathVariable("id") Integer id) throws SQLException {
        Subject subject = subjectService.getById(id);
        if (subject.getId() == null) {
            logger.error("Subject with id " + id + " not found in DB ");
            throw new EntityNotFoundException("Subject with id " + id + " not found in DB ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(subject);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> add(@RequestBody Subject subject) throws SQLException, ValidationsException, JsonProcessingException {

        if (subject == null) {
            logger.error("Missing subject payload");
            throw new ValidationsException("Missing subject payload");
        }
        subjectService.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString("Successfully Created"));
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Subject subject) throws SQLException, ValidationsException {

        if (subject == null) {
            logger.error("Missing subject payload");
            throw new ValidationsException("Missing subject payload");
        }
        subjectService.update(subject);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated");
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteSubject(@PathVariable("id") Integer id) throws SQLException {

        subjectService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
    }

}

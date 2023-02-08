package com.springgoals.service.impl;

import com.springgoals.dao.impl.SubjectDAOImpl;
import com.springgoals.exception.CustomException;
import com.springgoals.model.Subject;
import com.springgoals.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class SubjectServiceImpl implements SubjectService {

    private Validator validator;

    @Autowired
    private SubjectDAOImpl subjectDAO;


    @Override
    public Subject getById(Integer id) throws SQLException {
        Subject subject = subjectDAO.getById(id);
        return subject;
    }

    @Override
    public List<Subject> getAll() throws SQLException {
        return subjectDAO.getAll();
    }

    @Override
    public void update(Subject subject) throws SQLException, CustomException {
        Set< ConstraintViolation <Subject> > violations = validator.validate(subject);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Subject> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new CustomException("Error occurred: " + sb.toString());
        }
        subjectDAO.update(subject);
    }

    @Override
    public void save(Subject subject) throws SQLException, CustomException {
        Set< ConstraintViolation <Subject> > violations = validator.validate(subject);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Subject> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new CustomException("Error occurred: " + sb.toString());
        }
        subjectDAO.save(subject);
    }

    @Override
    public void delete(Integer id) throws SQLException {
        subjectDAO.delete(id);
    }
}

package com.springgoals.service.impl;

import com.springgoals.dao.impl.FacultyDAOImpl;
import com.springgoals.dao.impl.UniversityDAOImpl;
import com.springgoals.model.University;
import com.springgoals.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityDAOImpl universityDAO;

    @Override
    public University getById(Integer id) throws SQLException {
        University university = universityDAO.getById(id);
        return university;
    }

    @Override
    public List<University> getAll() throws SQLException {
        return universityDAO.getAll();
    }

    @Override
    public void update(University university) throws SQLException {
        universityDAO.update(university);
    }

    @Override
    public void save(University university) throws SQLException {
        universityDAO.save(university);
    }

    @Override
    public void delete(Integer id) throws SQLException {

        universityDAO.delete(id);

    }
}
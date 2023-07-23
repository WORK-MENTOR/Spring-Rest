package com.springgoals.service.impl;

import com.springgoals.dao.impl.FacultyDAOImpl;
import com.springgoals.dao.impl.UniversityDAOImpl;
import com.springgoals.exception.ValidationsException;
import com.springgoals.model.Faculty;
import com.springgoals.model.University;
import com.springgoals.model.dto.UniversityFacultiesDTO;
import com.springgoals.model.dto.UniversityFacultyDTO;
import com.springgoals.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private Validator validator;

    @Autowired
    private UniversityDAOImpl universityDAO;

    @Autowired
    private FacultyDAOImpl facultyDAO;

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
    public Map<Integer, University> getMap() throws SQLException {
        return universityDAO.getMap();
    }

    @Override
    public List<University> searchUniversities(String name, String description)
            throws SQLException {
        StringBuilder sql = new StringBuilder("Select * from university where 1=1");
        if (name != null && !name.equals("")) {
            sql.append(" and name = \"");
            sql.append(name);
            sql.append("\"");
        }
        if (description != null && !description.equals("")) {
            sql.append(" and description = \"");
            sql.append(description);
            sql.append("\"");
        }

        return universityDAO.searchUniversities(sql.toString());
    }

    @Override
    @Transactional
    public void update(University university)
            throws SQLException, ValidationsException {
        Set<ConstraintViolation<University>> violations = validator.validate(university);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<University> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        universityDAO.update(university);
    }

    @Override
    @Transactional
    public void save(University university) throws SQLException, ValidationsException {
        Set<ConstraintViolation<University>> violations = validator.validate(university);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<University> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }
        universityDAO.save(university);
    }

    @Override
    @Transactional
    public void delete(Integer id) throws SQLException {

        universityDAO.delete(id);

    }

    @Override
    public UniversityFacultyDTO getFacultiesByUniId(Integer id) throws SQLException {

        return universityDAO.getFacultiesByUniId(id);
    }

    @Override
    public void saveUniversityFaculties(UniversityFacultiesDTO updateUniversityFacultiesDTO) throws ValidationsException, SQLException {

        Set<ConstraintViolation<University>> violationUniversity = validator.validate(updateUniversityFacultiesDTO.getUniversity());
        Set<ConstraintViolation<Faculty>> violationFaculty = null;

        for(Faculty faculty : updateUniversityFacultiesDTO.getFacultyList()){
            violationFaculty = validator.validate(faculty);
        }

        if (!violationUniversity.isEmpty() || !violationFaculty.isEmpty()) {
                StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<University> constraintViolation : violationUniversity) {
                sb.append(constraintViolation.getMessage());
            }
            for (ConstraintViolation<Faculty> constraintViolation : violationFaculty) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ValidationsException("Error occurred: " + sb.toString());
        }

        Integer universityId =universityDAO.saveReturnId(updateUniversityFacultiesDTO.getUniversity());
        for(Faculty faculty : updateUniversityFacultiesDTO.getFacultyList()){
            faculty.setUniversity_id(universityId);
            facultyDAO.save(faculty);

        }

    }


}

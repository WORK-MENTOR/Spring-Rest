package com.springgoals.dao;

import com.springgoals.model.Faculty;
import com.springgoals.model.Subject;
import com.springgoals.model.dto.FacultySubjectDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface FacultyDAO {

    List<Faculty> getAll() throws SQLException;

    Map<Integer, Faculty> getMap() throws SQLException;

    List<Faculty> searchFaculties(String sql) throws SQLException;

    Faculty getById(Integer id) throws SQLException;

    void update(Faculty user) throws SQLException;

    void delete(Integer id) throws SQLException;

    void save(Faculty user) throws SQLException;

    void saveDTO(Faculty faculty,Integer universityId) throws SQLException;

    FacultySubjectDTO getSubjectsByFacId(Integer id) throws SQLException;

}

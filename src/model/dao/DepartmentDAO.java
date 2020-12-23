package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {

	void insert(Department d);
	Department findById(Integer id);
	List<Department> findAll();
	void update(Department d);
	void deleteById(Integer id);
}

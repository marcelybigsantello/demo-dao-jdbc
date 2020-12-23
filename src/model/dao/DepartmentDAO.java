package model.dao;

public interface DepartmentDAO {

	void insert(Department d);
	Department findById(Integer id);
	List<Department> findAll();
	void update(Department d);
	void deleteById(Integer id);
}

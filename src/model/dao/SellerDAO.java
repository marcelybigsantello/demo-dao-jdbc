package model.dao;

import java.util.List;

public interface SellerDAO {

	void insert(Department d);
	Department findById(Integer id);
	List<Department> findAll();
	void update(Department d);
	void deleteById(Integer id);
}

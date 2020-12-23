package model.dao;

import java.util.List;

import model.entities.Seller;

public interface SellerDAO {

	void insert(Seller d);
	Seller findById(Integer id);
	List<Seller> findAll();
	void update(Seller d);
	void deleteById(Integer id);
}

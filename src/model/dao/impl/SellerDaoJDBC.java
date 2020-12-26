package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDAO {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	} 
	
	@Override
	public void insert(Seller d) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("INSERT INTO seller (Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, d.getName());
			ps.setString(2, d.getEmail());
			ps.setDate(3, new java.sql.Date(d.getBirthDate().getTime()));
			ps.setFloat(4, d.getBaseSalary());
			ps.setInt(5, d.getDepartment().getId());
			
			int affectedRows = ps.executeUpdate();
			if (affectedRows > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {					
					int id = rs.getInt(1);
					d.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT s.*, dep.Name as DepartName, dep.Description as DepartDescript " 
					+ "from seller s INNER JOIN department dep " 
					+ "on s.DepartmentId = dep.Id " 
					+ "WHERE s.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller s = instantiateSeller(rs, dep);				
				return s;
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
		
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepartName"));
		dep.setDescription(rs.getString("DepartDescript"));
		
		return dep;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller s = new Seller();
		s.setId(rs.getInt("Id"));
		s.setName(rs.getString("Name"));
		s.setEmail(rs.getString("Email"));
		s.setBirthDate(rs.getDate("BirthDate"));
		s.setBaseSalary(rs.getFloat("BaseSalary"));
		s.setDepartment(dep);
		
		return s;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT s.*, dep.Name as DepartName, dep.Description as DepartDescript " 
					+"from seller s inner join department dep " 
					+"on s.DepartmentId = dep.Id " 
					+"order by s.Name ASC;");
			
			rs = ps.executeQuery();
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			
			while(rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller seller = instantiateSeller(rs, dep);
				list.add(seller);
			}
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage()); 
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller d) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("UPDATE seller SET Name=?, Email=?, BirthDate=?, BaseSalary=?, DepartmentId=? "
					+ "WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, d.getName());
			ps.setString(2, d.getEmail());
			ps.setDate(3, new java.sql.Date(d.getBirthDate().getTime()));
			ps.setFloat(4, d.getBaseSalary());
			ps.setInt(5, d.getDepartment().getId());
			ps.setInt(6, d.getId());
			
			ps.executeUpdate();
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			ps.setInt(1, id);
			
			int affectedRows = ps.executeUpdate();
			if (affectedRows == 0) {
				throw new DbException("Unexpected error! Id not found!");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT s.*, dep.Name as DepartName, dep.Description as DepartDescript " 
					+"from seller s inner join department dep " 
					+"on s.DepartmentId = dep.Id " 
					+"where s.DepartmentId = ? " 
					+"order by dep.Name ASC");
			
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			
			List<Seller> list = new ArrayList<Seller>();
			Map<Integer, Department> map = new HashMap<Integer, Department>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller s = instantiateSeller(rs, dep);				
				list.add(s);
			}
			
			return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

}

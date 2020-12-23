package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Department d1 = new Department(01, "Books", "Department of generic and specialized books");
		
		Seller s1 = new Seller(20, "Bob", "bob@gmail.com.br", new Date(), 2400.00f, d1);
		
		SellerDAO sellerDAO = DaoFactory.createSellerDao();
		System.out.println(s1);
	}

}

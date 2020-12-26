package application;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SellerDAO sellerDAO = DaoFactory.createSellerDao();
		Seller seller = sellerDAO.findById(1);
		System.out.println("========Test 1: Seller FindById =======");
		System.out.println(seller);
	}

}

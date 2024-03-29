package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SellerDAO sellerDAO = DaoFactory.createSellerDao();
		Seller seller = sellerDAO.findById(1);
		System.out.println("========Test 1: Seller FindById =======");
		System.out.println(seller);
		
		System.out.println("\n========Test 2: Seller FindByDepartment =======");
		Department department = new Department(1, null, null);
		List<Seller> sellers = sellerDAO.findByDepartment(department);
		
		for(Seller item : sellers) {
			System.out.println(item);
			System.out.println();
		}
		
		System.out.println("\n========Test 3: Seller findAll =======");
		sellers = sellerDAO.findAll();
		for (Seller item : sellers) {
			System.out.println(item);
			System.out.println();
		}
		
		/*System.out.println("\n========Test 4: Seller insert =======");
		Seller s = new Seller(null, "Maribel Soarez", "maribel.soarez@gmail.com", new Date(), 4300f, department);
		sellerDAO.insert(s);
		System.out.println("Inserted! New seller = " + s.getId());*/
		
		System.out.println("\n========Test 5: Seller update =======");
		seller = sellerDAO.findById(7);
		Department d = new Department(4, null, null);
		seller.setName("Carl Espinoza");
		seller.setEmail("carl.espinoza@gmail.com");
		seller.setDepartment(d);
		sellerDAO.update(seller);
		System.out.println("Update completed!");
		
		System.out.println("\n========Test 6: Seller delete =======");
		sellerDAO.deleteById(12);
		System.out.println("Delete completed!");
		
	}

}

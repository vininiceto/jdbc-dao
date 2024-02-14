package application;

import java.util.Date;

import model.entities.Departament;
import model.entities.Seller;

public class Program {
	
	public static void main(String[] args) {
		
		Departament dp = new Departament(1, "Books");
		System.out.println(dp);
		
		Seller sl = new Seller (21, "Bob", "Bob@gmail.com", new Date(), 3200.00, dp);
		System.out.println(sl);
	}

}

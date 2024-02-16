package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJdbc;
import model.dao.impl.DepartamentDaoJdbc;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJdbc(DB.getConnection());
	}
	
	public static DepartamentDao createDepartmentDao() {
		return new DepartamentDaoJdbc(DB.getConnection());
	}
}

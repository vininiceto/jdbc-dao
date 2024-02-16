package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbIntegrityException;
import db.dbException;
import model.dao.DepartamentDao;
import model.entities.Departament;

public class DepartamentDaoJdbc implements DepartamentDao {

	private Connection conn;

	public DepartamentDaoJdbc(Connection conn) {
		this.conn = conn;
	}

	public Departament findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Departament obj = new Departament();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new dbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public List<Departament> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
			rs = st.executeQuery();

			List<Departament> list = new ArrayList<>();

			while (rs.next()) {
				Departament obj = new Departament();
				obj.setId(rs.getInt("Id"));
				obj.setName(rs.getString("Name"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new dbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	public void insert(Departament obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department " + "(Name) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getName());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new dbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new dbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	public void update(Departament obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department " + "SET Name = ? " + "WHERE Id = ?");

			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new dbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}
}

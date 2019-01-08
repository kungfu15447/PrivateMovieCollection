/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Frederik Jensen
 */
public class CategoryDAO
{
    private final ConnectionDAO cb;
    
    public CategoryDAO() {
        cb = new ConnectionDAO();
    }
    
    public List<Category> getAllCategories() throws SQLException {
        List<Category> catList = new ArrayList<>();
        
        try (Connection con = cb.getConnection()) {
            String sql = "SELECT * FROM Category;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(id, name);
                catList.add(category);
            }
            
        }
        return catList;
    }
    
    public Category createCategory(String name) throws SQLException {
        try (Connection con = cb.getConnection()) {
            String sql = "INSERT INTO Category (name) VALUES (?);";
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            
            int rowsAffected = st.executeUpdate();
            
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            Category category = new Category(id, name);
            return category;
        }
    }
    
    public void deleteCategory(Category category) throws SQLException {
        try (Connection con = cb.getConnection()) {
            Statement st = con.createStatement();
            String sql = "DELETE FROM Category WHERE = " + category.getId() + ";";
            st.executeUpdate(sql);
        }
    }
}

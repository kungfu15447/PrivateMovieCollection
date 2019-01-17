/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import DAL.Exception.MTDalException;
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
    private final ConnectionDAO CB;
    
    /**
     * Constructor, connects to database.
     */
    public CategoryDAO() {
        CB = new ConnectionDAO();
    }
    
    /**
     * Gets all categories from the Category table.
     * @return a list containing all categories
     * @throws MTDalException 
     */
    public List<Category> getAllCategories() throws MTDalException {
        List<Category> catList = new ArrayList<>();
        
        try (Connection con = CB.getConnection()) {
            String sql = "SELECT * FROM Category;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(id, name);
                catList.add(category);
            }
            
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not get all categories.", ex);
        }
        return catList;
    }
    
    /**
     * Creates a category.
     * @param name
     * @return the category
     * @throws MTDalException 
     */
    public Category createCategory(String name) throws MTDalException {
        try (Connection con = CB.getConnection()) {
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
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not create category.", ex);
        }
    }
    
    /**
     * Deletes a category.
     * @param category
     * @throws MTDalException 
     */
    public void deleteCategory(Category category) throws MTDalException {
        try (Connection con = CB.getConnection()) {
            Statement st = con.createStatement();
            String sql = "DELETE FROM Category WHERE id = " + category.getId() + ";";
            st.executeUpdate(sql);
        } catch (SQLException ex)
        {
            throw new MTDalException("Could not delete category.", ex);
        }
    }
}

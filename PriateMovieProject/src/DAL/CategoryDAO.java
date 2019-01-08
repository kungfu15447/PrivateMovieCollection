/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import java.sql.Connection;
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
            }
            
        }
        return catList;
    }
}

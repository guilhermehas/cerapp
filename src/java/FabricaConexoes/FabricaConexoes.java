/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FabricaConexoes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author guilherme
 */
public class FabricaConexoes {
    public Connection getConnection() throws ClassNotFoundException
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/cerveja", "root", "12345");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    
}

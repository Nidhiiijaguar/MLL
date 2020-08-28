/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import au.com.bytecode.opencsv.CSVReader;

public class CSVReaders 
{
    public static void main(String[] args) 
    {
    	String csvFile = "E:\\workspace\\Citation_Java\\src\\pro\\Citations.csv";
        CSVReader reader = null;
        try
        {
            Connection con=new DB().Con();
            PreparedStatement delete=con.prepareStatement("truncate table temp");
        	delete.executeUpdate();
            
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            int i=0;
            while ((line = reader.readNext()) != null)
            {
            	i++;
            	if(i!=1)
            	{
	            	PreparedStatement query=con.prepareStatement("insert into temp(Year,FileName,Title,Citation_Count)values('"+line[0]+"','"+line[1]+"','"+line[2].replaceAll("'", "").replaceAll(",", "")+"','"+line[3]+"') ");
	            	query.executeUpdate();
	            	System.out.println(i);
            	}
            }
        } catch (IOException | SQLException e) 
        {
            System.out.println(e);
        }


    }
    
}

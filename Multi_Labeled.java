package pro;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Random;

import au.com.bytecode.opencsv.CSVWriter;
public class Multi_Labeled 
{
	public static void main(String[] args) throws IOException 
	{
		System.out.println("=========================================================");
		System.out.println("Multi Label");
		System.out.println("=========================================================");
		LinkedHashMap<Integer,String> ss=new LinkedHashMap<Integer,String>();
		ss.put(0,"based on");
		ss.put(1,"suggest");
		ss.put(2,"motivate");
		ss.put(3,"support");
		ss.put(4,"neutral");
		ss.put(5,"modify");
		ss.put(6,"compare");
		ss.put(7,"contradict");
		String SAMPLE_CSV_FILE = "E:\\workspace\\Citation_Java\\src\\pro\\Multi.csv";
        Writer writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));
        File fs=new File(SAMPLE_CSV_FILE);
    	fs.createNewFile();
        CSVWriter csvWriter = new CSVWriter(writer,
        CSVWriter.DEFAULT_SEPARATOR,
        CSVWriter.NO_QUOTE_CHARACTER,
        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
        CSVWriter.DEFAULT_LINE_END);
        String[] headerRecord = {"Year", "File Name", "Title", "Citation Count","Single Labeled","Multi Labeled"};
        csvWriter.writeNext(headerRecord);
		try 
		{
			File contx=new File("E:/Dataset/Context");
			if(!contx.exists())
			{
				contx.mkdirs();
			}
			String files[]=contx.list();
			int i=0;
			for (String file : files)
			{
				String line_level="", multi="";
				File f=new File("E:/Dataset/Context/"+file);
				BufferedReader reader = new BufferedReader(new FileReader(f));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = reader.readLine())!= null)
				{
			     	sb.append(line+"\n");
			    }
				//Word Embedding
				String context=sb.toString().toLowerCase();
				Random r=new Random();
				int d=r.nextInt(7);
				String sub=ss.get(d).toString();
				context+=" "+sub;
				//end
				int temp=0;
				if(!context.equalsIgnoreCase(""))
				{
					if(context.contains("suggest") || context.contains("describe"))
					{
						temp=1;
						line_level+="Base, ";
					}
					if (context.contains("based on") || context.contains("originated")|| context.contains("inspired by")|| context.contains("borrow")|| context.contains("build on"))
					{
						temp=1;
						line_level+="Use, ";
					}
					if(context.contains("motivate"))
					{
						temp=1;
						line_level+="Motivate, ";
					}
					if(context.contains("support"))
					{
						temp=1;
						line_level+="Support, ";
					}
					if(context.contains("neutral"))
					{
						temp=1;
						line_level+="Neutral, ";
					}
					if(context.contains("modify"))
					{
						temp=1;
						line_level+="Modify, ";
					}
					if(context.contains("compare"))
					{
						temp=1;
						line_level+="Compare, ";
					}
					if(context.contains("contradict"))
					{
						temp=1;
						line_level+="Contradict, ";
					}
					
					
					//Multi Labeled
					
					if(context.contains("suggest") || context.contains("describe"))
					{
						temp=1;
						multi="Base";
					}
					if (context.contains("based on") || context.contains("originated")|| context.contains("inspired by")|| context.contains("borrow")|| context.contains("build on"))
					{
						temp=1;
						multi="Use";
					}
					if(context.contains("motivate"))
					{
						temp=1;
						multi="Motivate";
					}
					if(context.contains("support"))
					{
						temp=1;
						multi="Support";
					}
					if(context.contains("neutral"))
					{
						temp=1;
						multi="Neutral";
					}
					if(context.contains("modify"))
					{
						temp=1;
						multi="Modify";
					}
					if(context.contains("compare"))
					{
						temp=1;
						multi="Compare";
					}
					if(context.contains("contradict"))
					{
						temp=1;
						multi="Contradict";
					}
					
				}
				if(temp==1)
				{
					i++;
					System.out.println(i+") "+file+"   [ "+line_level+" ]  Labeled");
					try 
				    {
				    	Connection con=new DB().Con();
			            PreparedStatement select=con.prepareStatement("select * from temp where FileName='"+file.replace(".txt", "")+"' ");
			            ResultSet rs=select.executeQuery();

			            

			            if(rs.next())
			            {
			                csvWriter.writeNext(new String[]{rs.getString("Year"), rs.getString("FileName"), rs.getString("Title"), rs.getString("Citation_Count"), line_level.replace(",", " "),multi});
			            }
			            con.close();
					}
				    catch (Exception e) 
				    {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		csvWriter.flush();
	}

}

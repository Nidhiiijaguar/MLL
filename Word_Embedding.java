/**
 * 
 */
package pro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Random;

import javax.naming.Context;

/**
 * @author DLK-E1
 *
 */
public class Word_Embedding
{
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		System.out.println("=========================================================");
		System.out.println("Word Embedding");
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
				String line_level="";
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
				}
				if(temp!=0)
				{
					i++;
					System.out.println(i+") "+file+"   [ "+line_level+" ]  Labeled");
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
	}
}

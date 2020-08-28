package pro;
import java.io.*;
import java.util.*;
public class Context_Extractions 
{
	public void Process()
	{
		try 
		{
			File loc=new File("E:/Context Extraction Find Citation (Run on Console)/SOURCE CODE/DATASETS/TextFilesFull");//just given dataset's location
			File contx=new File("E:/Context Extraction Find Citation (Run on Console)/SOURCE CODE/DATASETS/Context");//here created new folder
			if(!contx.exists())
			{
				contx.mkdirs();
			}
			
			String files[]=loc.list();//list all base paper from dataset's
			//Delete Existing Files
			for (String file: files)
			{
				File del=new File("E:/Context Extraction Find Citation (Run on Console)/SOURCE CODE/DATASETS/Context/"+file);//delete existing base papers
				del.delete();
			}
			
			System.out.println(files.length+"   Total Count");
			int d=0;
			for (String file : files)
			{
				//read base paper content
				StringBuilder str=new StringBuilder();
				File f=new File("E:/Context Extraction Find Citation (Run on Console)/SOURCE CODE/DATASETS/TextFilesFull/"+file);
				BufferedReader reader = new BufferedReader(new FileReader(f));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = reader.readLine())!= null)
				{
			     	sb.append(line+"\n");
			    }
				String txt=sb.toString().trim(); //base paper content stored here
				
				int flag=0;
				//here reference extraction code(context extractions)
				//K-Means Clustering Algorithm Using Here for citation prediction
				for(int j=1;j<=30;j++)
				{
					if(txt.contains("["+j+"]"))
					{
						String word=txt.substring(0,txt.indexOf("["+j+"]"));
						int val=0;
						for(int i=word.length()-1;i>=0;i--)
						{
							String temp=word.charAt(i)+"";
							if(temp.equalsIgnoreCase("."))
							{
								val=i;
								break;
							}
						}
						word=word.substring(val+1, word.length()).trim();
						if(!word.equalsIgnoreCase(""))
						{
							flag++;
							//ss.add(flag+" ) "+word);
							str.append("["+flag+"] "+word+".\n\n");
						}
					}
				}
				//end K-means algo
				//end Context Extraction code
				d++;
				//create file (context extracted that file)
				File cit=new File("E:/Context Extraction Find Citation (Run on Console)/SOURCE CODE/DATASETS/Context/"+file);
				System.out.println(d+" = Data's");
				cit.createNewFile();
				FileWriter fw=new FileWriter(cit);
				fw.write(str.toString());
				fw.close();
				//end code
			}
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Successfully Context Extracted");
	}
	public static void main(String[] args) 
	{
		Context_Extractions ld=new Context_Extractions();
		ld.Process();
	}
}

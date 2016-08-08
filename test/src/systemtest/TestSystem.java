package systemtest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestSystem {
	public static void main(String[] args){ 
		
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){  
		  System.out.println(os + " can't gunzip");  
		}  
		
        InputStream in = null;  //rsync --delete-before -d blank/ testDeletePricture/
        try {  
            Process pro = Runtime.getRuntime().exec(new String[]{"mkdir","blank"});  
            pro.waitFor();
            Process pro2 = Runtime.getRuntime().exec(new String[]{"rsync","--delete-before","-d","blank/", "testDeletePricture/"});  
            pro2.waitFor();
            
            in = pro.getInputStream();  
            BufferedReader read = new BufferedReader(new InputStreamReader(in));  
            String result = read.readLine();  
            System.out.println("INFO:"+result);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}

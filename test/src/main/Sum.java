package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sum {
	
	public static void main(String[] args) throws FileNotFoundException{
		String t1="08:32:54",t2="17:30:00";
		sumTime(t1,t2);
		 
		try {
			BufferedReader in=new BufferedReader(new FileReader("D:/Documents/Downloads/打卡详情.txt"));  
			String line=null;
			List<String[]> list = new ArrayList<String[]>();
			while((line=in.readLine())!=null)  
			{  
				list.add(getTime(line).split(","));
				
			}
			in.close();
			List<String[]> list2 = new ArrayList<String[]>();
			for(int i=0;i<list.size();i++){
				if(list.get(i).length==1){
					String[] ss = {"","17:30:00"};
					ss[0] = list.get(i)[0];
					list2.add(ss);
				}else{
					list2.add(list.get(i));
				}
				
			}
			List<String> ls = new ArrayList<String>();
			for(int i=0;i<list2.size();i++){
				ls.add(sumTime("9:00:00",sumTime(list2.get(i)[0],list2.get(i)[1])));
			}
			zong(ls);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  		
	}
	
	public static String getTime(String str){
		
		int pos = str.lastIndexOf('	');//制表符

		return str.substring(pos+1);
		
	}
	public static void S2I(Integer i[],String s[]){
		for(int j=0;j<s.length;j++){
			i[j] =Integer.parseInt(s[j]);
		}
		
	}
	public static String sum(Integer[] i1,Integer[] i2){
		i2[2] +=i1[2];
		if(i2[2]>60){
			i2[1]+=1;
			i2[2]-=60;
			if(i2[1]>60){
				i2[0]+=1;
				i2[1]-=60;
			}
		}
		i2[1]+=i1[1];
		if(i2[1]>60){
			i2[0]+=1;
			i2[1]-=60;
		}
		i2[0]+=i1[0];
		System.out.println(i2[0]+":"+i2[1]+":"+i2[2]);
		return i2[0]+":"+i2[1]+":"+i2[2];		
	}
	public static void zong(List<String> ls){
		Integer[] i1 = {0,0,0};
		Integer[] i2 = {0,0,0};
		for(int i=0;i<ls.size();i++){
			S2I(i1,ls.get(i).split(":"));
			if(i1[0]<0)
				continue;
			sum(i1,i2);
		}				
	}
	public static String sumTime(String start1,String end1){
		String[] start = start1.split(":");
		String[] end = end1.split(":");
		Integer[] st ={0,0,0};
		Integer[] ed ={0,0,0};
		S2I(st,start);S2I(ed,end);
		if(ed[2]<st[2]){
			ed[2]=ed[2]+60-st[2];
			ed[1] -=1;
			if(ed[1]<0){
				ed[1]++;
				ed[0]-=1;
			}
		}else{
			ed[2]-=st[2];
		}
		
		if(ed[1]<st[1]){
			ed[1]+=60-st[1];
			ed[0]-=1;
		}
		ed[0]-=st[0];						
		return ed[0]+":"+ed[1]+":"+ed[2];
	}
	
}

package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyDate {

	
	private static List<Date> getDayMi(Date date) {

		 Calendar tt = Calendar.getInstance();
         tt.setTime(date);
         tt.add(Calendar.MINUTE, 10);
         Calendar t2 = Calendar.getInstance();
         t2.setTime(date);
         t2.add(Calendar.MINUTE, -10);
         List<Date> dateList = new ArrayList<Date>();
         while(tt.compareTo(t2)>0){
         	t2.add(Calendar.MINUTE, 1);
         	 dateList.add(t2.getTime());
         }
        return dateList;
    }
	
	public static void main(String[] args) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2015-08-26 12:02:00");
        List<Date> dateMis = getDayMi(date);
        for (Date dateMi : dateMis) {
            System.out.println(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(dateMi));
        }
        
        Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date datec = calendar.getTime();
		
    }
}

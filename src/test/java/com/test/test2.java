package com.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeNow=format.format(date);
		
		System.out.println(timeNow);
		


		Calendar now = Calendar.getInstance(); 

		Calendar time = Calendar.getInstance();
		time.add(Calendar.MONTH, -1);
		long timeSt = time.getTime().getTime();
		Date dateDt = new Date(timeSt);

		String year = new SimpleDateFormat("yyyy").format(dateDt); 
		String  monthStr = new SimpleDateFormat("MM").format(dateDt);
		String month = String.valueOf(Integer.valueOf(monthStr));
		int day = Integer.valueOf(new SimpleDateFormat("dd").format(dateDt));

		now.add(Calendar.DATE, -day);
		long nowSt = now.getTime().getTime();
		Date dateNowDt = new Date(nowSt);
		
		String endDayOfMonth = new SimpleDateFormat("dd").format(dateNowDt);
		String startDayOfMonth = String.valueOf(Integer.valueOf(endDayOfMonth)+1);


		
		System.out.println("year:"+year);
		System.out.println("month:"+month);
		System.out.println("endDayOfMonth:"+endDayOfMonth);
		System.out.println("startDayOfMonth:"+startDayOfMonth);
		
	}

}

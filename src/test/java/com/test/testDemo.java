package com.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class testDemo {

	public static void main(String[] args) {
		
		Calendar now = Calendar.getInstance(); 
		now.add(Calendar.DATE, -40);
		now.add(Calendar.HOUR, -1);
//		now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - 1);
		long endTimeSt = now.getTime().getTime();
				
		now.add(Calendar.SECOND,-10);
		long startTimeSt = now.getTime().getTime();
		
	
		Date endDate = new Date(endTimeSt);
		Date startDate = new Date(startTimeSt);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String endTime = simpleDateFormat.format(endDate);
		String startTime = simpleDateFormat.format(startDate);
		

		
		System.out.println("startTime="+startTime);
		System.out.println("endTime="+endTime);

	}

}

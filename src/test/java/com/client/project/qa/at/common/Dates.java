package com.client.project.qa.at.common;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Dates {
	
	public static LocalDate today = getNowDate();
	static DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("mm/dd/YYYY");
	
	public static LocalDate getNowDate(){
		return LocalDate.now();
	}
	
	public static String getCurrentdate(){
		String cDate = String.valueOf(LocalDate.now());
		String[] d=cDate.split("-");
		if(d[2].startsWith("0")) d[2] = String.valueOf(d[2].charAt(1));
		if(d[1].startsWith("0")) d[1] = String.valueOf(d[1].charAt(1));
		System.out.println(d[1]+"/"+d[2]+"/"+d[0]);
		return d[1]+"/"+d[2]+"/"+d[0];
	}
	
	public static String getPastYearsDate(int yrs){
		String pastYear = String.valueOf(today.minusYears(yrs));
		String[] past = pastYear.split("-");
		System.out.println(past[1]+"/"+past[2]+"/"+past[0]);
		return past[1]+"/"+past[2]+"/"+past[0];
	}
	
	public static String getFutureYearsDate(int yrs){
		String futureYear = String.valueOf(today.plusYears(yrs));
		String[] fut = futureYear.split("-");
		System.out.println(fut[1]+"/"+fut[2]+"/"+fut[0]);
		return fut[1]+"/"+fut[2]+"/"+fut[0];
	}
	
	public static String getAfterDays(long days){
		String futureDays = String.valueOf(today.plusDays(days));
		String[] d = futureDays.split("-");
		if(d[2].startsWith("0")) d[2] = String.valueOf(d[2].charAt(1));
		if(d[1].startsWith("0")) d[1] = String.valueOf(d[1].charAt(1));
		System.out.println(d[1]+"/"+d[2]+"/"+d[0]);
		return d[1]+"/"+d[2]+"/"+d[0];
	}
	
	public static String getTimeStamp(){
		//sdf.format(new Timestamp(System.currentTimeMillis()))
		return new SimpleDateFormat("yyyymmddhhmmss").format(new Timestamp(System.currentTimeMillis()));
	}
	
	public static String processDOBWithSlashes(String date){

		String aDate = null;
		String dd;
		String mm;
		String yyyy;
		char[] c = date.toCharArray();
		if(c.length==7){
			dd="0"+c[0];
			aDate=dd+date.substring(1, c.length);
		}else if(c.length==4){
			dd="01";
			mm="02";
			yyyy=new String(c);System.out.println(yyyy);
			aDate=mm+"/"+dd+"/"+yyyy;
			System.out.println("***");
		}else if(c.length==6){
			dd="0"+c[0];
			mm="0"+c[1];
			yyyy=date.substring(2);
			aDate = mm+"/"+dd+"/"+yyyy;
		}else if(c.length==8){
			dd=c[0]+""+c[1];
			mm=c[2]+""+c[3];
			yyyy=date.substring(4);
			aDate = mm+"/"+dd+"/"+yyyy;
		}
		System.out.println(aDate);
		return aDate;
	}
	
	public static String processExpiredDates(String date){
		String aDate = null;
		String dd;
		String mm;
		String yyyy;
		char[] c = date.toCharArray();
		if(c.length==7){
			mm="0"+c[0];
			aDate=mm+date.substring(1, c.length);
		}else if(c.length==4){
			dd="01";
			mm="02";
			yyyy=new String(c);System.out.println(yyyy);
			aDate=mm+"/"+dd+"/"+yyyy;
			System.out.println("***");
		}else if(c.length==6){
			dd="0"+c[1];
			mm="0"+c[0];
			yyyy=date.substring(2);
			aDate = mm+"/"+dd+"/"+yyyy;
		}else if(c.length==8){
			mm=c[0]+""+c[1];
			dd=c[2]+""+c[3];
			yyyy=date.substring(4);
			aDate = mm+"/"+dd+"/"+yyyy;
		}
		System.out.println(aDate);
		return aDate;
	}
	
	
	public static String processDateWithSlashes(String date){
		if(date.contains("/")){
			date=date.replace("/", "");
		}
		return processExpiredDates(date);
	}
	
	public static void main(String[] a){
		
//		System.out.println(getNowDate());
//		System.out.println(getCurrentdate());
//		String date ="7/8/2023";
//		if(date.contains("/")){
//			date=date.replace("/", "");
//		}
//		System.out.println("Date"+processDOBWithSlashes(date));
//		System.out.println("****"+processDOBWithSlashes(date));
		
		System.out.println(processDateWithSlashes(getFutureYearsDate(5)));
//		8/8/2018
		
//		08/08/2018
		
	}
}

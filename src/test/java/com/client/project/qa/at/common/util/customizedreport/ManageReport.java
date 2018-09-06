package com.client.project.qa.at.common.util.customizedreport;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.relevantcodes.extentreports.ExtentReports;

public class ManageReport {
	public String dirPathName = System.getProperty("user.dir") + "\\reports\\reports_"+getTimeStamp()+"\\screenshots";
	protected final String reportFileName = System.getProperty("user.dir") + "\\reports\\reports_"+getTimeStamp()+"\\TestReport.html";
	public ExtentReports eReport;

	public ExtentReports getReportName() {
		eReport = new ExtentReports(this.reportFileName, false);
		return eReport;
	}

		public void createReportDirectories() {
			boolean isFile = false;
			File file = new File(dirPathName);
			/*
			 * exists() method tests whether the file or directory denoted by this
			 * abstract pathname exists or not accordingly it will return TRUE /
			 * FALSE.
			 */
	
			if (!file.exists()) {
				/*
				 * mkdirs() method creates the directory mentioned by this abstract
				 * pathname including any necessary but nonexistent parent
				 * directories.
				 *
				 * Accordingly it will return TRUE or FALSE if directory created
				 * successfully or not. If this operation fails it may have
				 * succeeded in creating some of the necessary parent directories.
				 */
				isFile = file.mkdirs();
			}
	
			if (isFile)
				System.out.println("Created Directory: " + dirPathName);
			else
				System.out.println("Directory Available: " + dirPathName);
		}
	
//		public void cleanReports() throws IOException {
//	
//			boolean isFile = false;
//	
//			String delDir = System.getProperty("user.dir") + "\\reports";
//			System.out.println("Reports: " + delDir);
//	
//			File fl = new File(delDir);
//	
//			/*
//			 * exists() method tests whether the file or directory denoted by this
//			 * abstract pathname exists or not accordingly it will return TRUE /
//			 * FALSE.
//			 */
//	
//			if (fl.exists()) {
//	
//				/*
//				 * mkdirs() method creates the directory mentioned by this abstract
//				 * pathname including any necessary but nonexistent parent
//				 * directories.
//				 *
//				 * Accordingly it will return TRUE or FALSE if directory created
//				 * successfully or not. If this operation fails it may have
//				 * succeeded in creating some of the necessary parent directories.
//				 */
//	
//				FileUtils.deleteDirectory(fl);
//				isFile = true;
//			}
//	
//			if (isFile)
//				System.out.println("Teardown Reports!");
//			else
//				System.out.println("Already teardown reports!");
//		}
		
		public static String getTimeStamp(){
			//sdf.format(new Timestamp(System.currentTimeMillis()))
			return new SimpleDateFormat("ddhhmmss").format(new Timestamp(System.currentTimeMillis()));
		}
}

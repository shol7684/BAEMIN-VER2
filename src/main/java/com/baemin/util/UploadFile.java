package com.baemin.util;


	import java.io.File;
	import java.io.IOException;
	import java.text.DecimalFormat;
	import java.util.Calendar;
	import java.util.UUID;

	import org.springframework.util.FileCopyUtils;
	import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;


	public class UploadFile {
	  
	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;

	public static String uploadPath = "C:\\sts4\\com.baemin\\src\\main\\resources\\static\\upload";
	 
		public static String upload(MultipartFile img) throws IOException, Exception {
			String imgUploadPath = uploadPath + File.separator + "imgUpload";
			String ymdPath = UploadFile.calcPath(imgUploadPath);
			String fileName =  UploadFile.fileUpload(imgUploadPath, img.getOriginalFilename(), img.getBytes(), ymdPath);
			
			
			String getPath = File.separator + "upload" + File.separator + "imgUpload" + ymdPath + File.separator + fileName;
			
			
			return getPath;
		}
		
		public static String thumbUpload(MultipartFile img) throws IOException, Exception {
			String imgUploadPath = uploadPath + File.separator + "imgUpload";
			String ymdPath = UploadFile.calcPath(imgUploadPath);
			String fileName =  UploadFile.fileUpload(imgUploadPath, img.getOriginalFilename(), img.getBytes(), ymdPath);
			
			String getPath = File.separator + "upload" + File.separator + "imgUpload" + ymdPath + File.separator + "s" + File.separator + "s_" + fileName;
			
			
			return getPath;
		}








		public static String fileUpload(String uploadPath, String fileName, byte[] fileData, String ymdPath) throws Exception {

			UUID uid = UUID.randomUUID();
	  
			String newFileName = uid + "_" + fileName;
			String imgPath = uploadPath + ymdPath;
			
			File target = new File(imgPath, newFileName);
			FileCopyUtils.copy(fileData, target);
			  
			String thumbFileName = "s_" + newFileName;
			File image = new File(imgPath + File.separator + newFileName);
			
			File thumbnail = new File(imgPath + File.separator + "s" + File.separator + thumbFileName);
			
			if (image.exists()) {
				
				thumbnail.getParentFile().mkdirs();
				Thumbnails.of(image).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);
				
			}
				
			return newFileName;

		}
		
		
		
		public static String calcPath(String uploadPath) {
			Calendar cal = Calendar.getInstance();
			String yearPath = File.separator + cal.get(Calendar.YEAR);
			String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
			String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

			makeDir(uploadPath, yearPath, monthPath, datePath);
			makeDir(uploadPath, yearPath, monthPath, datePath + "\\s");
			
			return datePath;
		}
		
		
		

		private static void makeDir(String uploadPath, String... paths) {
		
			if (new File(paths[paths.length - 1]).exists()) { 
				return; 
			}
			
			
			for (String path : paths) {
				
				File dirPath = new File(uploadPath + path);
		
				if (!dirPath.exists()) {
					dirPath.mkdir();
				}
			}
			
			
		 }
		
		
		
		
		
}

<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");

	// 1.이미지 저장될 폴더 path 설정
// 	String dir = "/Users/OKSU/Desktop/IOTstudy149/webProject/web16FileUpload/WebContent/uploadimg";
	String dir = application.getRealPath("uploadimg");
	System.out.println("file...getRealPath>>"+application.getRealPath("uploadimg"));
	
	
	// 2. 최대 크기 
	int max = 10 * 1024 * 1024;

	boolean isMultipart = 
			ServletFileUpload.isMultipartContent(request);

	String comment1 = "";
	String comment2 = "";
	String originalName = "";
	String saveName = "";

	if (isMultipart) {
		File temporaryDir = new File(dir);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(max);
		factory.setRepository(temporaryDir);
		ServletFileUpload upload = new ServletFileUpload(factory);

		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
			System.out.println("items.size():" + items.size());
			for(FileItem fi : items){
				String fieldName = fi.getFieldName();
				if(fieldName.equals("comment1")){
					System.out.println(fieldName+":" + fi.getString());
					comment1 = fi.getString();
				}
				if(fieldName.equals("comment2")){
					System.out.println(fieldName+":" + fi.getString());
					comment2 = fi.getString();
				}
				if(fieldName.equals("file")){
					System.out.println(fieldName+":" + fi.getName());
					if(fi.getName().length() !=0){
	 					originalName = fi.getName();
	 					System.out.println(originalName+":" + originalName);
						
	 					long nowTime = System.currentTimeMillis();
	 					String format = originalName.substring(originalName.lastIndexOf('.'));
	 					saveName = nowTime+format;
	 					
	 					try {
							File uploadedFile = new File(dir, saveName);
							fi.write(uploadedFile);
						} catch (Exception e) {
							System.out.println(e);
						}
	 				}
				}
				
				
				
				if(fieldName.equals("tel")){
					System.out.println(fieldName+":" + fi.getString());
					comment2 = fi.getString();
				}
				if(fieldName.equals("multipartFile")){
					System.out.println(fieldName+":" + fi.getName());
					if(fi.getName().length() !=0){
	 					originalName = fi.getName();
	 					System.out.println(originalName+":" + originalName);
						
	 					long nowTime = System.currentTimeMillis();
	 					String format = originalName.substring(originalName.lastIndexOf('.'));
	 					saveName = nowTime+format;
	 					
	 					try {
							File uploadedFile = new File(dir, saveName);
							fi.write(uploadedFile);
						} catch (Exception e) {
							System.out.println(e);
						}
	 				}
				}
				
				
			}
		} catch (FileUploadException fe) {
			System.out.println(fe);
		}
	}
%>
<!DOCTYPE>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	comment1 :<%=comment1%><br /> 
	comment2 :<%=comment2%><br /> 
	originalName :
	<%=originalName%><br /> saveName :
	<%=saveName%>
</body>
</html>
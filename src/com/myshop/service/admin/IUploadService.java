package com.myshop.service.admin;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.FileItem;

public interface IUploadService {

	String uploadFile(ServletContext servletContext, FileItem fileItem) ;



	

}

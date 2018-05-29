package com.myshop.service.admin.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;

import com.myshop.service.admin.IUploadService;
import com.myshop.utils.UploadUtils;

public class UploadServiceImpl implements IUploadService{

	@Override
	public String uploadFile(ServletContext servletContext, FileItem fileItem)  {
		//将改文件即将保存的路径放到数据库
		//获取一个随机存放文件的文件夹
		String dir= UploadUtils.getDir();
		String name = fileItem.getName();
		String realName = UploadUtils.getRealName(name);
		//获取一个随机的文件名
		String uuidName = UploadUtils.getUUIDName(realName);
		//将文件存放到服务器
		
		try {
			InputStream inputStream = fileItem.getInputStream();
			//获取真是路径
			String realPath = servletContext.getRealPath("products");
			//在products文件夹内创建文件夹
			File fileDir=new File(realPath,dir);
			if(!fileDir.exists()){
				fileDir.mkdirs();
			}
			OutputStream output=new FileOutputStream(new File(fileDir,uuidName));
			IOUtils.copy(inputStream, output);
			//删除临时文件
			fileItem.delete();
			//关流
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(output);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "products"+dir+"/"+uuidName;
	}


}

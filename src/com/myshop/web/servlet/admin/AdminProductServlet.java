package com.myshop.web.servlet.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.msyshop.constant.Constant;
import com.myshop.bean.Category;
import com.myshop.bean.PageBean;
import com.myshop.bean.Product;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IProductService;
import com.myshop.service.admin.IAdminProductService;
import com.myshop.service.admin.IUploadService;
import com.myshop.utils.DateUtil;
import com.myshop.utils.UUIDUtils;
import com.myshop.web.servlet.BaseServlet;

/**
 * 处理后台商品管理的servlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String page(HttpServletRequest request,HttpServletResponse response) {
		//获取参数
		Integer curPage=Integer.parseInt(request.getParameter("curPage"));
		
		//调用业务层的方法
		PageBean<Product> page=null;
		try {
			IAdminProductService service = (IAdminProductService) ContextFactory.getInstance("adminProduct_service");
		page=service.findPageBean(curPage);
		HttpSession session = request.getSession();
		session.setAttribute("page", page);
		//重定向
		response.sendRedirect(request.getContextPath()+"/admin/product/list.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	public String add(HttpServletRequest request,HttpServletResponse response) {
		//创建磁盘文件项工厂
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//创造核心上传对象
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//创建一个map来存储请求参数的参数名和参数值
		Map<String, String> map=new HashMap<>();
		
		//解析请求获取所有的文件上传组件
		try {
			List<FileItem> fileItems=upload.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				//遍历出每一个文件上传组件
				//判断当前文件上传组件时文件还是普通组件
				if(fileItem.isFormField()){
					//普通组件
					//获取参数名和参数值并放到map中
					String name = fileItem.getFieldName();
					String value = fileItem.getString("UTF-8");
					map.put(name, value);
				}else {
					//文件
					//将图片路径存放到map中
					IUploadService service=(IUploadService) ContextFactory.getInstance("upload_service");
					ServletContext servletContext = request.getServletContext();
					String path=service.uploadFile(servletContext,fileItem);
					map.put("pimage", path);
				}
			}
		
			//将map中的参数封装到peoduct对象中
			String cid = map.get("cid");
			
			Product product= new Product();
			BeanUtils.populate(product, map);
			Category category=new Category();
			category.setCid(cid);
			//手动设置pid pdate pflag
			product.setPid(UUIDUtils.getId());
			product.setPdate(DateUtil.getCurrentTime());
			product.setPflag(Constant.UPJIA);
			product.setCategory(category);
			
			//调用ProductService的方法添加商品
			IAdminProductService service=(IAdminProductService) ContextFactory.getInstance("adminProduct_service");
			service.saveProduct(product);
			
			//查看所有商品,重定向
			response.sendRedirect(request.getContextPath()+"/adminProduct?methodStr=page&curPage=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return null;
				
	}
	public String del(HttpServletRequest request,HttpServletResponse response) {
		String pid = request.getParameter("pid");
		
		//调用业务层的方法
		try {
			IAdminProductService service=(IAdminProductService) ContextFactory.getInstance("adminProduct_service");
			service.delProduct(pid);
			
			//查看所有商品,重定向
			response.sendRedirect(request.getContextPath()+"/adminProduct?methodStr=page&curPage=1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String findProductByPid(HttpServletRequest request,HttpServletResponse response) {
		String pid = request.getParameter("pid");

		//调用业务层的方法
		Product product=null;
		try {
			IAdminProductService service=(IAdminProductService) ContextFactory.getInstance("adminProduct_service");
			product=service.findProductByPid(pid);
		System.out.println(pid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("product", product);
		return "/admin/product/edit.jsp";
	}
	public String edit(HttpServletRequest request,HttpServletResponse response) {
		//创建磁盘文件项工厂
				DiskFileItemFactory factory=new DiskFileItemFactory();
				//创造核心上传对象
				ServletFileUpload upload = new ServletFileUpload(factory);
				
				//创建一个map来存储请求参数的参数名和参数值
				Map<String, String> map=new HashMap<>();
				
				//解析请求获取所有的文件上传组件
				try {
					List<FileItem> fileItems=upload.parseRequest(request);
					for (FileItem fileItem : fileItems) {
						//遍历出每一个文件上传组件
						//判断当前文件上传组件时文件还是普通组件
						if(fileItem.isFormField()){
							//普通组件
							//获取参数名和参数值并放到map中
							String name = fileItem.getFieldName();
							String value = fileItem.getString("UTF-8");
							map.put(name, value);
						}else {
							//文件
							//将图片路径存放到map中
							IUploadService service=(IUploadService) ContextFactory.getInstance("upload_service");
							ServletContext servletContext = request.getServletContext();
							String path=service.uploadFile(servletContext,fileItem);
							map.put("pimage", path);
						}
					}
				
					//将map中的参数封装到peoduct对象中
					String cid = map.get("cid");
					String pid = map.get("pid");
					String pname = map.get("pname");
					String market_price = map.get("market_price");
					String shop_price = map.get("shop_price");
					String is_hot = map.get("is_hot");
					String pdesc = map.get("pdesc");
					
					
					Product product= new Product();
					BeanUtils.populate(product, map);
					Category category=new Category();
					category.setCid(cid);
					//手动设置pid pdate pflag
					product.setPid(pid);
					product.setPdate(DateUtil.getCurrentTime());
					product.setPname(pname);
					product.setMarket_price(Double.valueOf(market_price));
					product.setShop_price(Double.valueOf(shop_price));
					product.setIs_hot(Integer.parseInt(is_hot));
					product.setPdesc(pdesc);
				
					product.setCategory(category);
					
					//调用ProductService的方法添加商品
					IAdminProductService service=(IAdminProductService) ContextFactory.getInstance("adminProduct_service");
					service.updateProduct(product);
					
					//查看所有商品,重定向
					response.sendRedirect(request.getContextPath()+"/adminProduct?methodStr=page&curPage=1");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
	}
}

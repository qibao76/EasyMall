package web.back;

import entity.Product;
import factory.BasicFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/6/4.
 */
public class BackproducaddtServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //保存表单提交的数据
        HashMap<String, String> map = new HashMap<String, String>();
        //将商品id保存
        map.put("id", UUID.randomUUID().toString());
        //创建临时文件的路径
        System.out.println(getServletContext().getRealPath("/web"));
        String tmp="WEB-INF/tmp";
        //创建上传文件的路径
        String path="WEB-INF/upload";
        //创建DiskFileItemFactory类对象,并设置缓冲区大小和临时文件的路径
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory(10240,new File(getServletContext().getRealPath(tmp)));
        //生成文件上传的核心类ServletFileUpload的对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        //解决中文乱码问题
        servletFileUpload.setHeaderEncoding("utf-8");
        //设置单个文件上传大小
        servletFileUpload.setFileSizeMax(1024*1024*2);//2MB
        //设置表单中所有文件的大小
        servletFileUpload.setSizeMax(1024*1024*3);
        //解析request对象List<FileItem>
        try {
            List<FileItem> list = servletFileUpload.parseRequest(httpServletRequest);
            for (FileItem item:list) {
                //判断表单项是普通还是文件
                if(item.isFormField()){
                    map.put(item.getFieldName(),item.getString("utf-8"));
                }else{
                    //获取上传文件的名字
                    String fname=item.getName();
                    //获取表单项name属性的名字
                    String name=item.getFieldName();
                    //处理不同浏览器发过来还包含绝对路径
                    if ((fname.indexOf("\\")!=-1)) {
                        fname.substring(fname.lastIndexOf("\\") + 1);
                    }
                    //防止文件重名
                    fname=UUID.randomUUID().toString()+"_"+fname;
                    String hexString = Integer.toHexString(fname.hashCode());
                    //保证长度为8位
                    if (hexString.length()<8){
                        hexString+="0";
                    }
                    //拼接路径
                    for(char c:hexString.toCharArray()){
                        path+=("/"+c);
                    }
                    //创建保存文件的路径
                    new File(getServletContext().getRealPath(path)).mkdirs();
                    //将路径保存到map中
                    map.put(name,path+"/"+fname);
                    //操作io实现文件上传
                    InputStream inputStream = item.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(getServletContext().getRealPath(path) + "/" + fname);
                    byte[] bytes= new byte[1024];
                    int len=-1;
                    while ((len=inputStream.read(bytes))!=-1){
                        fileOutputStream.write(bytes,0,len);
                    }
                    //关闭流
                    fileOutputStream.close();
                    inputStream.close();
                    //删除临时文件
                    item.delete();
                }
            }
            //将商品信息添加到数据库中
            //1.将map封装成对象
            Product product=new Product();
            BeanUtils.populate(product,map);
            ProductService service = BasicFactory.getBasicFactory().getInstance(ProductService.class);
            service.addProd(product);
            //A6添加成功
            httpServletResponse.getWriter().write("恭喜您，商品添加成功！");
            httpServletResponse.setHeader("Refresh", "2;url="+httpServletRequest.getContextPath()
                    +"/servlet/BackProdListServlet");
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}

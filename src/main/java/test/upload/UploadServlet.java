package test.upload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/26.
 */
public class UploadServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //检查当前表单是否使用multipart/form-data类型
        if(!ServletFileUpload.isMultipartContent(httpServletRequest)){
            throw new RuntimeException("请使用正确的表单属性上传文件");
        }
        //创建DiskFileItemFactory类对象
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        //1.1设置缓冲区的大小
        diskFileItemFactory.setSizeThreshold(1024*1000);//100kB
        //1.2设置临时文件的保存路径
        String path=getServletContext().getRealPath("/WEB-INF/tmp");
        diskFileItemFactory.setRepository(new File(path));
        //2创建servletFileUpload对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        //2.1处理上传文件名的编码
        servletFileUpload.setHeaderEncoding("utf-8");
        //2.2设置单个文件的大小
        servletFileUpload.setFileSizeMax(1024*1024*2);//2MB
        //2.3设置本次form表单的所有文件的大小总和
        servletFileUpload.setSizeMax(1024*1024*10);

        //添加监听
        final long startTime = System.currentTimeMillis();
        servletFileUpload.setProgressListener(new ProgressListener() {
            //l1已经上传的文件大小   l2文件的总大小   i上传的第几个元素
            public void update(long l1, long l2, int i) {
                System.out.println("已经上传:"+l1+"上传大小"+l2+"第"+i+"个表单项");
                //已使用时间
                long now = System.currentTimeMillis();
                long useTime = now - startTime;
                //剩余大小
                long lsy = l2 - l1;
                //计算速度
                double speed=0;
                if(useTime!=0){
                    speed=l1/useTime;//B/s    iB=8b
                }
                //剩余时间
                long leftTime=0;
                if (speed!=0){
                    leftTime= (long) (lsy/speed);//毫秒
                }
            }
        });

        //3.解析request对象,获取所有的输入项目对象集合
        try {
            List<FileItem> list = servletFileUpload.parseRequest(httpServletRequest);
            //4.遍历该集合对象
            for (FileItem fileItem:list){
                //判断该输入框对象是否为普通输入项
               if (fileItem.isFormField()){//是普通输入项
                   //获取该输入项name属性的值
                   String fieldName = fileItem.getFieldName();
                   //获取输入项的value属性值
                   String value = fileItem.getString("utf-8");
                   System.out.println(fieldName+":"+value);
               }else{//文件输入流
                   //获取文件名
                   String name = fileItem.getName();
                   //获取表单项name属性的名字
                  // String fieldName = fileItem.getFieldName();
                   //IE部分版本浏览器在获取文件名中包含本地路径,解决方案截取文件文件名
                   if (name.indexOf("\\")!=-1){
                       name=name.substring(name.lastIndexOf("\\")+1);
                   }
                    //处理保存目录(安全)
                   String loadPath="/WEB-INF/upload";
                    //处理文件重名问题
                   name= UUID.randomUUID().toString()+"_"+name;
                   //处理同一目录文件数过多问题
                   String hashcode = Integer.toHexString(name.hashCode());//asfdrqe346ghjfq
                   char[] chars = hashcode.toCharArray();
                   for (char cha:chars){
                       loadPath+=("/"+cha);
                   }
                   //创建目录
                   new File(getServletContext().getRealPath(loadPath)).mkdirs();
                   //IO操作将客户端的文件流保存到服务端的对应目录下
                   //获取客户端当前文件输入项对应的输入流
                   InputStream inputStream = fileItem.getInputStream();
                   FileOutputStream fileOutputStream = new FileOutputStream(getServletContext().getRealPath(loadPath + "/" + name));
                   byte[] bytes = new byte[1024];
                   int len=-1;
                   while ((len=inputStream.read(bytes))!=-1){
                       fileOutputStream.write(bytes,0,len);
                   }
                   //关闭流
                   fileOutputStream.close();
                   inputStream.close();
                   //删除临时文件
                   fileItem.delete();


               }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            httpServletResponse.getWriter().write("上传文件超过上限");
        }


    }
}

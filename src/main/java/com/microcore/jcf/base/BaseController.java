package com.microcore.jcf.base;

import com.microcore.config.MVCDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**
 * C层基本类
 *
 * @author LiuChunfu
 * @date 2017/3/14.
 */
public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new MVCDateFormat());
    }

    /**
     * 重定向
     *
     * @param url
     * @throws IOException
     */
    protected void redirectTo(String url) throws IOException {
        response.sendRedirect(url);
    }

    /**
     * @return
     */
    protected String basePath() {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path;
        return basePath;
    }

    ///**
    // * 导出
    // *
    // * @param type
    // * @param size
    // * @param queryParams
    // * @throws IOException
    // */
    //@RequestMapping(value = "download/{type}/{size}", method = RequestMethod.GET)
    //public void download(@PathVariable("type") Type type, @PathVariable("size") Size size, QueryParams queryParams) throws Exception
    //{
    //    ExportPageOrListParams params = new ExportPageOrListParams();
    //    params.setType(type);
    //    params.setSize(size);
    //    params.setQueryParams(queryParams);
    //    File file = getExportFile(params);
    //    if (file == null)
    //    {
    //        throw ServiceExceptionUtil.getErrorServiceException("导出文件不可为空!");
    //    }
    //    responseStream(file);
    //}

    ///**
    // * 获取导出的文件，由子类重写该方法
    // *
    // * @param params
    // * @return
    // */
    //protected File getExportFile(ExportPageOrListParams params) throws Exception
    //{
    //    return null;
    //}


    protected void responseStream(File file) {
        try
                (
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())
                ) {
            /*
            * 下载响应头设置
        */
            String filename = URLEncoder.encode(file.getName(), "UTF-8");
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            /*
            * 输入流读取文件，返回前端 输出流
        */
            byte[] buff = new byte[1024 * 10];
            int len;
            while ((len = bis.read(buff, 0, buff.length)) != -1) {
                bos.write(buff, 0, len);
            }
            // 文件流输出完成后删除服务器临时文件
            //  FileUtil.deleteFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.yimei.finance.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by hary on 16/3/30.
 */
public class WebUtils {

    static public void doDownloadFile(File file,  HttpServletResponse response) throws IOException {
        try {
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static public void doDownloadFile(File file, String filename, HttpServletResponse response) throws IOException {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param inputStream 文件输入流
     * @param fileName    文件名称
     * @return
     * @throws IOException
     */
    public static HttpEntity<byte[]> downloadFile(InputStream inputStream, String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        return new HttpEntity<byte[]>(IOUtils.toByteArray(inputStream), headers);
    }


    /**
     * 返回map方法
     *
     * @param map     map
     * @param success success
     * @param error   错误信息
     * @return map对象
     */
    public Map<String, Object> setMapMessage(Map<String, Object> map, boolean success, String error) {
        map.put("success", success);
        map.put("error", error);
        return map;
    }

    /**
     * 返回map方法
     *
     * @param errortype 错误信息类型
     */
    public Map<String, Object> setMapMessage(Map<String, Object> map, boolean success, String errortype, String error) {
        map.put("success", success);
        map.put("errortype", errortype);
        map.put("error", error);
        return map;
    }



}

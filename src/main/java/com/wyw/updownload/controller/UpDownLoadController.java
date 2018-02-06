package com.wyw.updownload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author wyw
 * @date 2018\2\5 0005 14:01
 */
@Controller
public class UpDownLoadController {

    @GetMapping(value = "/index")
    public String upDownLoad() {
        return "html/upDownLoad.html";
    }

    @PostMapping(value = "/upLoadFile")
    @ResponseBody
    public String upLoadFile(MultipartFile file) {
        if (null == file) {
            return "fail to upload";
        } else {
            String fileName = file.getOriginalFilename();
            try {
                ///File tempFile = File.createTempFile(fileName);
                File nFile = new File("/" + fileName);
                file.transferTo(nFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "upload success";
        }
    }

    @GetMapping(value = "/downLoadFile")
    @ResponseBody
    public void downLoad(HttpServletResponse res) {
        String fileName;
        try{
            //浏览器会以iso8859-1的编码格式去解析header(包括filename)，所以需要转换为iso8859-1格式
            fileName = new String("森林公园.jpg".getBytes("utf-8"),"iso8859-1");
            String path = this.getClass().getResource("/static/download/20170108181458.jpg").getPath();
            res.setHeader("content-type", "application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            res.setContentType("application/octet-stream;charset=utf-8");
            res.setCharacterEncoding("UTF-8");
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os;
            try {
                os = res.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(new File(path)));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭bis或者os中的任意一个socket就会被关闭，因此不用同时手动关闭os和bis
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

}

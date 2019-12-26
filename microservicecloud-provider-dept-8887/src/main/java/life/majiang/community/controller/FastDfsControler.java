package life.majiang.community.controller;

import life.majiang.community.exception.CustmizeException;
import life.majiang.community.exception.CustomizeErrorcode;
import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@RestController
public class FastDfsControler {

    /**
     * 本地文件上传文件到FastDFS
     *
     * @param file
     */
    @RequestMapping(value = "/fastDFSUpload", method = RequestMethod.POST)
    @ResponseBody
    public void fastDFSUpload(MultipartFile file) throws FileNotFoundException {

        String ext_Name = file.getOriginalFilename().split("\\.")[1];
        String file_Name = file.getOriginalFilename().split("\\.")[0];

        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath = uploadFile(bytes, ext_Name, file_Name);
    }

    /**
     * FastDFS实现fastdfs服务器文件下载
     *
     * @param filePath
     */
    @RequestMapping(value = "/fastDFSDownload", method = RequestMethod.GET)
    @ResponseBody
    public void fastDFSDownload(String filePath) {
        try {
            ClientGlobal.initByProperties("application.properties");

            // 链接FastDFS服务器，创建tracker和Stroage
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            byte[] b = storageClient.download_file("group1", filePath);
            if (b == null) {
                throw new IOException("文件" + filePath + "不存在");
            }

            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            FileOutputStream fileOutputStream = new FileOutputStream("c://" + fileName);
            IOUtils.write(b, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FastDFS获取将本地文件上传文件信息
     */
    @RequestMapping(value = "/fastDFSGetFileInfo", method = RequestMethod.GET)
    @ResponseBody
    public void fastDFSGetFileInfo(String filePath) {
        try {
            // 链接FastDFS服务器，创建tracker和Stroage
            ClientGlobal.initByProperties("application.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            FileInfo fi = storageClient.get_file_info("group1", filePath);
            if (fi == null) {
                throw new IOException("文件" + filePath + "不存在");
            }

//            log.debug("文件信息=" + fi.toString());
            System.out.println("文件信息=" + fi.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FastDFS获取文件名称
     */
    @RequestMapping(value = "/fastDFSGetFileName", method = RequestMethod.GET)
    @ResponseBody
    public void fastDFSGetFileName(String filePath) {
        try {
            // 链接FastDFS服务器，创建tracker和Stroage
            ClientGlobal.initByProperties("application.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            NameValuePair[] nvps = storageClient.get_metadata("group1", filePath);
            if (nvps == null) {
                throw new IOException("文件" + filePath + "不存在");
            }

//            log.debug(nvps[0].getName() + "." + nvps[0].getValue());
            System.out.println(nvps[0].getName() + "." + nvps[0].getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * FastDFS实现删除文件
     */
    @RequestMapping(value = "/fastDFSDelete", method = RequestMethod.GET)
    @ResponseBody
    public void fastDFSDelete(String filePath) {
        try {
            // 链接FastDFS服务器，创建tracker和Stroage
            ClientGlobal.initByProperties("application.properties");
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            int i = storageClient.delete_file("group1", filePath);
//            log.debug(i == 0 ? "删除成功" : "删除失败:" + i);
            System.out.println(i == 0 ? "删除成功" : "删除失败:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadFile(byte[] byteFile, String ext_file, String file_Name) {

        // 拼接服务区的文件路径
        StringBuffer sbPath = new StringBuffer();
        sbPath.append("http://121.41.85.42/uploads/");
        try {
            // 初始化文件资源
            ClientGlobal.initByProperties("application.properties");

            // 链接FastDFS服务器，创建tracker和Stroage
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();

            String storageServerIp = getStorageServerIp(trackerClient, trackerServer);
            StorageServer storageServer = getStorageServer(storageServerIp);
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            //利用字节流上传文件
            String[] strings = storageClient.upload_file(byteFile, ext_file, null);

            sbPath.append("/");
            sbPath.append(strings);
            System.out.println("上传路径=" + sbPath.toString());
        } catch (IOException | MyException e) {
            e.printStackTrace();
        }
        return sbPath.toString();
    }

    /**
     * 得到Storage服务
     *
     * @param storageIp
     * @return 返回Storage服务
     */
    private static StorageServer getStorageServer(String storageIp) {
        StorageServer storageServer = null;
        if (storageIp != null && !("").equals(storageIp)) {
            try {
                // ip port store_path下标
                storageServer = new StorageServer(storageIp, 23000, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        log.debug("——storage server生成");
        System.out.println("——storage server生成");
        return storageServer;
    }

    /**
     * 获得可用的storage IP
     *
     * @param trackerClient
     * @param trackerServer
     * @return 返回storage IP
     */
    private static String getStorageServerIp(TrackerClient trackerClient, TrackerServer trackerServer) {
        String storageIp = null;
        if (trackerClient != null && trackerServer != null) {
            try {
                StorageServer storageServer = trackerClient.getStoreStorage(trackerServer, "group1");
                storageIp = storageServer.getSocket().getInetAddress().getHostAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        log.debug("——获取组中可用的storage IP——" + storageIp);
        System.out.println("——获取组中可用的storage IP——" + storageIp);
        return storageIp;
    }

}

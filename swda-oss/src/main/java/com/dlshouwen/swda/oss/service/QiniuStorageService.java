package com.dlshouwen.swda.oss.service;

import com.dlshouwen.swda.core.exception.SwdaException;
import com.dlshouwen.swda.oss.properties.StorageProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云存储
 *
 * @author 阿沐 babamu@126.com
 * <a href="https://maku.net">MAKU</a>
 */
public class QiniuStorageService extends StorageService {
    private final UploadManager uploadManager;

    public QiniuStorageService(StorageProperties properties) {
        this.properties = properties;

        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));

    }

    @Override
    public String upload(byte[] data, String path) {
        try {
            String token = Auth.create(properties.getQiniu().getAccessKey(), properties.getQiniu().getSecretKey()).
                    uploadToken(properties.getQiniu().getBucketName());

            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                throw new SwdaException(res.toString());
            }

            return properties.getConfig().getDomain() + "/" + path;
        } catch (Exception e) {
            throw new SwdaException("上传文件失败：", e);
        }
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new SwdaException("上传文件失败：", e);
        }
    }

}

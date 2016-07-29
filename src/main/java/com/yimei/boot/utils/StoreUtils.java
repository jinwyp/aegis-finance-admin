package com.yimei.boot.utils;

import com.yimei.boot.services.Storage;
import com.yimei.boot.services.StorageException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by hary on 16/3/30.
 */
public class StoreUtils {


    private  static  final Logger logger= LoggerFactory.getLogger(StoreUtils.class);

    public enum FileSizeUnits {
        KB, MB, GB, PB
    }


    //计算文件大小
    public static BigInteger calcFileSize(long byteSize, FileSizeUnits units) {
        if (units == FileSizeUnits.KB) {
            return BigInteger.valueOf(byteSize).divide(FileUtils.ONE_KB_BI);
        } else if (units == FileSizeUnits.MB) {
            return BigInteger.valueOf(byteSize).divide(FileUtils.ONE_MB_BI);
        } else if (units == FileSizeUnits.GB) {
            return BigInteger.valueOf(byteSize).divide(FileUtils.ONE_GB_BI);
        } else if (units == FileSizeUnits.PB) {
            return BigInteger.valueOf(byteSize).divide(FileUtils.ONE_PB_BI);
        }
        return BigInteger.valueOf(-1);
    }



    public static String getFileType(MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }


    /**
     * @param storage
     * @param file
     * @param bucket
     * @return
     * @throws IOException
     */
    public static String save(Storage storage, MultipartFile file, String bucket) throws IOException {

        String suffix = getFileType(file);

        String filename = Hex.encodeHexString(DigestUtils.md5(file.getInputStream())) + "." + suffix;

        try {
            storage.save(bucket, filename, file.getInputStream(), 0, null);
        } catch (StorageException e) {
            logger.error("上传文件出错:{}",e.getMessage());
            throw new RuntimeException("can not save");
        }

        return filename;
    }

}

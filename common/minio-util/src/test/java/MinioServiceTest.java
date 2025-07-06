import cn.edu.scnu.danmaku.minio.service.MinioService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class MinioServiceTest {
    private MinioService minioService;

    public MinioServiceTest () {
        minioService = new MinioService();
        minioService.setEndpoint("http://192.168.159.6:9000");
    }

    public static void main (String[] args) {
        MinioServiceTest minioServiceTest = new MinioServiceTest();
        try {
            String uploadFileName = minioServiceTest.testUploadFile();

            minioServiceTest.testDownloadFile(uploadFileName);

            minioServiceTest.testDeleteFile(uploadFileName);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String testUploadFile () throws Exception {
        File pic = new File("C:\\Users\\admin1\\Desktop\\感谢相遇.png");
        try (InputStream inputStream = new FileInputStream("C:\\Users\\admin1\\Desktop\\感谢相遇.png")) {
            MultipartFile file = new MockMultipartFile(
                    "file",
                    "感谢相遇.png",
                    "image/png",
                    inputStream
            );
            String uploadFileName = minioService.uploadFile(file, "video");
            return uploadFileName;
        }
    }

    public void testDownloadFile (String uploadFileName) throws Exception {
        String localFilePath = "C:\\Users\\admin1\\Desktop\\测试\\" + uploadFileName;

        try (InputStream stream = minioService.downloadFile("video", uploadFileName);
             OutputStream out = new FileOutputStream(localFilePath)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            System.out.println("文件下载并保存成功：" + localFilePath);

        } catch (Exception e) {
            System.err.println("下载或保存失败：" + e.getMessage());
            throw e;
        }
    }

    public boolean testDeleteFile (String uploadFileName) {
        try {
            minioService.deleteFile("video", uploadFileName);
            System.out.println("文件删除成功：" + uploadFileName);
            return true;
        } catch (Exception e) {
            System.err.println("文件删除失败：" + e.getMessage());
            return false;
        }
    }
}

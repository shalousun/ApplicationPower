import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.power.common.util.FileUtil;


/**
 * @author gg on 2022/10/12
 */
public class S3Util {

    private S3Util() {

    }

    private static String accessKeyId = "";

    private static String accessKeySecret = "";

    private static String bucketName = "";

    private static String regionName = "cn-north-1";

    private static String S3Url = "https://test.s3.cn-north-1.amazonaws.com.cn/";


    public static AmazonS3 amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, accessKeySecret);
        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials));
        AmazonS3 s3Client = builder.build();
        return s3Client;
    }

    public static String upload(String bucketName, String cephPath, File localFile) throws Exception {
        AmazonS3 amazonS3Client = amazonS3();
        amazonS3Client.putObject(bucketName, cephPath, localFile);
        return getPrivateURL(amazonS3Client, bucketName, cephPath);
    }

    private static String getPrivateURL(AmazonS3 amazonS3Client, String bucket, String key) throws ParseException {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key);
        Date expirationDate = null;
        expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-12");
        request.setExpiration(expirationDate);
        URL url = amazonS3Client.generatePresignedUrl(request);
        return url == null ? "Null URL" : url.toString();
    }

    public static boolean downLoad(String fileName, File filePath) throws Exception {
        AmazonS3 amazonS3Client = amazonS3();
        S3Object object = amazonS3Client.getObject(new GetObjectRequest(bucketName, fileName));
        if (object != null) {
            return FileUtil.copyInputStreamToFile(object.getObjectContent(), filePath, null);
        }
        return true;
    }

    public void listObj(String bucket, String prefix) {
        AmazonS3 amazonS3Client = amazonS3();
        ListObjectsRequest request = new ListObjectsRequest().withBucketName(bucket).withDelimiter("/")
            .withPrefix(prefix).withMaxKeys(10);
        int count = 0;
        ObjectListing objects = amazonS3Client.listObjects(request);
        do {

            for (String tmpPrefix : objects.getCommonPrefixes())
                System.out.println((++count) + "    prefix:" + tmpPrefix);
            for (S3ObjectSummary summary : objects.getObjectSummaries()) {
                System.out.println((++count) + "    " + summary.getKey() + "\t" + summary.getSize() + "\t");
            }
            objects = amazonS3Client.listNextBatchOfObjects(objects);
            System.out.println("Next batch ......");
        } while (objects.isTruncated());
        for (S3ObjectSummary summary : objects.getObjectSummaries()) {
            System.out.println((++count) + "    " + summary.getKey() + "\t" + summary.getSize() + "\t");
        }
    }


}

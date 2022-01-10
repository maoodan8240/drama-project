import dm.relationship.utils.FtpUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestFtp {
    @Test
    public void test1() {
//        String host, int port, String username, String password, String basePath,String filePath, String filename, InputStream input
        try {

            File file = new File("D:\\work_space\\ttt.jpg");
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            boolean jbs = FtpUtils.uploadFile(
                    "121.36.23.124",//
                    21,//
                    "jbs",//
                    "jbs..123Com",//
                    "/web/",//
                    "player_icon/",//
                    "ttt.jpg",//
                    fis
            );
            System.out.println(jbs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

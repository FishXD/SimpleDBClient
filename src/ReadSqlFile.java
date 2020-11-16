import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author sanling
 * @date 2020/11/12
 */
public class ReadSqlFile {
    FileInputStream fis = null;

    /**
     * 读默认的sql测试文件
     */
    public ReadSqlFile() {
        try {
            fis = new FileInputStream("sqlFile");
            byte[] bt = new byte[4];
            int readCount;
            while ((readCount = fis.read(bt)) != -1) {
                System.out.print(new String(bt, 0, readCount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读指定的sql文件
     *
     * @param msg 文件路径+文件名称
     */
    public ReadSqlFile(String msg)  {
        try {
            fis = new FileInputStream("msg");
            byte[] bt = new byte[4];
            int readCount;
            while ((readCount = fis.read(bt)) != -1) {
                System.out.print(new String(bt, 0, readCount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String SqlStatements() {
        String cmd = "";
        try {
            fis = new FileInputStream("sqlFile");
            byte[] bt = new byte[4];
            int readCount;
            while ((readCount = fis.read(bt)) != -1) {
                System.out.print(new String(bt, 0, readCount));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cmd;
    }
}

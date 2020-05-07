package nio.channel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * User: 智深
 * Date-Time: 2013-04-17
 */
public class MappedByteBufferTest {
    static public void main(String args[]) throws Exception {
        String filePath = "/Users/yan.zhang/Downloads/data.dmg";
        Path path = Paths.get(filePath);
        path.toFile().createNewFile();

        initFile(path);
        long start = System.currentTimeMillis();
//        bufferedStream(path);
//        mappedByteBuffer(path);
//        printStreamTime(path);
        copyFile(path);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static void initFile(Path path) throws IOException {
        FileChannel open = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
        ByteBuffer buf = ByteBuffer.allocate(100);

        buf.put("".getBytes());
        open.position(Integer.MAX_VALUE- buf.limit());
        open .write(buf);
    }

    public static void inputStream(Path filename) {
        try (InputStream is = Files.newInputStream(filename)) {
            int c;
            while ((c = is.read())!=-1) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void bufferedStream(Path path) throws IOException {
        try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
            int c;
            while ((c = is.read())!=-1) {
                is.skip(4);//跳过,可以用来大概估算运行时间.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void mappedByteBuffer(Path path) throws IOException {

        FileChannel fileChannel = FileChannel.open(path);
        long size = fileChannel.size();
        //size不能超过过Integer.MAX_VALUE
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
        for (int i = 0; i<size; i++) {
            mappedByteBuffer.get(i);
        }

    }private static void copyFile(Path path) throws IOException {
        Path fileBak = Paths.get(path.toFile().getAbsolutePath() + ".bak");
        fileChannelCopy(path.toFile(), fileBak.toFile());
    }


    public static void fileChannelCopy(File s, File t) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();//得到对应的文件通道
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

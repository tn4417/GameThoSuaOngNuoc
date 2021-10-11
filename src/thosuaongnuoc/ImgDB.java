package thosuaongnuoc;

import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class ImgDB {

    private final ClassLoader c = this.getClass().getClassLoader();

    private static final File dir[] = {
        new File("src\\img\\label\\"),
        new File("src\\img\\pipe0\\"),
        new File("src\\img\\pipe1\\"),
        new File("src\\img\\pipe2\\"),
        new File("src\\img\\pipe3\\"),
        new File("src\\img\\pipe4\\"),
        new File("src\\img\\pipe5\\")
    };
    private static int maxFile = 0;
    private static int curFile = 0;
    private static final HashMap<String, HashMap<String, ImageIcon>> imgData = new HashMap<String, HashMap<String, ImageIcon>>();

    public ImgDB() {

    }

    public static int getMax() {
        for (int i = 0; i < dir.length; i++) {
            maxFile += dir[i].listFiles().length;
        }
        return maxFile;
    }

    public static void loadFromFile() {
        for (int i = 0; i < dir.length; i++) {
            loadFolder(dir[i]);
        }
    }

    public static void loadFolder(File dir) {
        File list[] = dir.listFiles();
        for (int i = 0; i < list.length; i++) {
            HashMap<String, ImageIcon> tmp = new HashMap<String, ImageIcon>();
            tmp.put(list[i].getName(), new ImageIcon(list[i].getPath()));
            imgData.put(list[i].getPath(), tmp);
            curFile++;
        }
    }

    public static ImageIcon get(String name) {
        HashMap<String, ImageIcon> tmp = null;
        ImageIcon tmpI = null;
        for (String key : imgData.keySet()) {
            tmp = imgData.get(key);
            if (!tmp.containsKey(name)) {
                continue;
            }
            tmpI = tmp.get(name);
        }
        return tmpI;
    }
}

package thosuaongnuoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class LevelDB {

    private static GLevel levels[][] = new GLevel[2][45];//0: Level game, 1: Level tu tao
    private static int cSys = 0;
    private static int cUser = 0;
    private static int curSysLevel = 0;
    private static GLevel curLevel = null;

    public static GLevel getCurLevel() {
        return curLevel;
    }

    public static void setCurLevel(int type, int index) {
        curLevel = levels[type][index];
        if (type == 0) {
            curSysLevel = index;
        }
    }

    public static int getIndexOfCurLevel() {
        return curSysLevel;
    }

    public static int getcSys() {
        return cSys;
    }

    public static boolean canNext() {
        if (curSysLevel < cSys - 1) {
            return true;
        }
        return false;
    }

    public static void unlockNextLevel() {
        GLevel nextL = levels[0][curSysLevel + 1];
        nextL.setLocked(false);
        updateInLevelFile(nextL);
    }

    public static void unlockAllLevel() {
        for (int i = 0; i < cSys; i++) {
            unlockLevel(i);
        }
    }

    public static void unlockLevel(int i) {
        GLevel index = levels[0][i];
        index.setLocked(false);
        updateInLevelFile(index);
    }

    public static GLevel getLevel(int type, int index) {
        return levels[type][index];
    }

    public static int getcUser() {
        return cUser;
    }

    public static void removeInLevelFile(GLevel ripLevel) {
        //Thuat: xoa path -> xoa trong database -> cap nhat lai
        File tmp = new File(ripLevel.getPath());
        tmp.delete();
        ArrayList<GLevel> tmpL = (ArrayList<GLevel>) readOb(ripLevel.getType());
        Iterator<GLevel> it = tmpL.iterator();
        while (it.hasNext()) {
            if (it.next().getPath().equals(ripLevel.getPath())) {
                it.remove();
            }
        }
        writeOb(tmpL, ripLevel.getType());
        loadLevelFromFile(ripLevel.getType());
    }

    public static boolean insertInLevelFile(GLevel newLevel, boolean override) {
        //Thuat: override = true: Tim kiem xoa -> Them | override = false: Tim kiem return

        ArrayList<GLevel> tmpL = (ArrayList<GLevel>) readOb(newLevel.getType());
        Iterator<GLevel> it = tmpL.iterator();
        while (it.hasNext()) {
            if (it.next().getPath().equals(newLevel.getPath())) {
                if (override) {
                    it.remove();
                    break;
                } else {
                    return false;
                }
            }
        }
        tmpL.add(newLevel);
        writeOb(tmpL, newLevel.getType());
        loadLevelFromFile(newLevel.getType());
        return true;
    }

    public static void updateInLevelFile(GLevel upLevel) {
        ArrayList<GLevel> tmp = (ArrayList<GLevel>) readOb(upLevel.getType());
        for (GLevel i : tmp) {
            if (i.getName().equals(upLevel.getName())) {
                tmp.set(tmp.indexOf(i), upLevel);
            }
        }
        writeOb(tmp, upLevel.getType());
    }

    public static void loadLevelFromFile(int type) {
        int dem = 0;
        for (int i = 0; i < 45; i++) {
            levels[type][i] = null;
        }

        ArrayList<GLevel> tmp = new ArrayList<GLevel>();
        try (FileInputStream fin = new FileInputStream("src\\map\\" + (type == 0 ? "ds" : "du") + "_leveldata.bin")) {
            ObjectInputStream oin = new ObjectInputStream(fin);
            tmp = (ArrayList) oin.readObject();
            for (GLevel level : tmp) {
                levels[type][dem++] = level;

            }
            oin.close();
        } catch (ClassNotFoundException | IOException e) {
        }
        if (type == 0) {
            cSys = dem;
        } else {
            cUser = dem;
        }
    }

    //Reset Game
    public static void resetLevelFile() {
        //Tao Set
        ArrayList<GLevel> levelSet0 = new ArrayList<GLevel>();
        ArrayList<GLevel> levelSet1 = new ArrayList<GLevel>();

        File dir = new File("src\\map\\");
        File files[] = dir.listFiles();
        Arrays.sort(files);
        int index0 = 0;
        int index1 = 0;
        for (int i = 0; i < files.length; i++) {
            String tmp = files[i].getName().substring(0, 2);
            if (index0 < 45 && tmp.equals("sm")) {
                int info[] = getInfoLevel(files[i].getPath());
                levelSet0.add(new GLevel(files[i].getPath(), 0, 0, info[0], info[1], false /*(index0 == 0 ? false : true)*/));
                index0++;
            }
            if (index1 < 45 && tmp.equals("us")) {
                int info[] = getInfoLevel(files[i].getPath());
                levelSet1.add(new GLevel(files[i].getPath(), 0, 0, info[0], info[1], false));
                index1++;
            }

        }
        //Ghi File
        try (FileOutputStream fout0 = new FileOutputStream("src\\map\\" + "ds_leveldata.bin");
                FileOutputStream fout1 = new FileOutputStream("src\\map\\" + "du_leveldata.bin")) {
            ObjectOutputStream oout0 = new ObjectOutputStream(fout0);
            ObjectOutputStream oout1 = new ObjectOutputStream(fout1);
            oout0.writeObject((Object) levelSet0);
            oout1.writeObject((Object) levelSet1);
            oout0.close();
            oout1.close();
        } catch (IOException e) {
        }
    }

    private static int[] getInfoLevel(String path) {
        int item[] = new int[2];
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            item[0] = Integer.parseInt(br.readLine());
            item[1] = Integer.parseInt(br.readLine());
        } catch (FileNotFoundException e) {
            System.err.println("Khong tim thay file!!");
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (NullPointerException ex) {
        };
        return item;
    }

    private static void writeOb(Object ob, int type) {
        try (FileOutputStream fout = new FileOutputStream("src\\map\\" + (type == 0 ? "ds" : "du") + "_leveldata.bin")) {
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(ob);
            oout.close();
        } catch (IOException e) {
        }
    }

    private static Object readOb(int type) {
        Object tmp = null;
        try (FileInputStream fin = new FileInputStream("src\\map\\" + (type == 0 ? "ds" : "du") + "_leveldata.bin")) {
            ObjectInputStream oin = new ObjectInputStream(fin);
            tmp = oin.readObject();
        } catch (ClassNotFoundException | IOException e) {
        }
        return tmp;
    }
}

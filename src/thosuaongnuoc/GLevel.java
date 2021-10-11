package thosuaongnuoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class GLevel implements Serializable {

    private String name;
    private String path;
    private int scores;
    private int star;
    private boolean locked = false;
    private int time;
    private int step;
    private int type;

    public GLevel() {
    }

    public GLevel(String path, int scores, int star, int time, int step, boolean locked) {
        setInfo(path, scores, star, locked, time, step);
    }

    public void setInfo(String path, int scores, int star, boolean locked, int time, int step) {
        this.path = path;
        this.scores = scores;
        this.star = star;
        this.locked = false;
        this.time = time;
        this.step = step;
        this.name = new File(path).getName();
        if (name.substring(0, 2).equals("sm")) {
            type = 0;
        } else {
            type = 1;
        }
    }

    public String getSName() {
        return name.substring(2).substring(0, name.length() - 6);
    }

    public void setScores(int s) {
        this.scores = s;
    }

    public void setStar(int s) {
        this.star = s;
    }

    public void setLocked(boolean locked) {
        this.locked = false;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public int getStar() {
        return star;
    }

    public int getScores() {
        return scores;
    }

    public int getTime() {
        return time;
    }

    public int getStep() {
        return step;
    }

    public boolean getLooked() {
        return locked;
    }

    public void writeToFile(int map[][]) {
        try (FileWriter fout = new FileWriter(path)) {
            fout.write(String.valueOf(time) + "\r\n");
            fout.write(String.valueOf(step) + "\r\n");
            for (int i = 0; i < 45; i++) {
                fout.write(map[i][0] + " " + map[i][1] + "\r\n");
            }
        } catch (IOException e) {
        };
    }

    public int[][] getMap() {
        int tmp[][] = new int[45][2];
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();
            br.readLine();
            for (int i = 0; i < 45; i++) {
                String item[] = br.readLine().split(" ");
                tmp[i][0] = Integer.parseInt(item[0]);
                tmp[i][1] = Integer.parseInt(item[1]);
            }
        } catch (IOException | NullPointerException ex) {
        };
        return tmp;
    }
}

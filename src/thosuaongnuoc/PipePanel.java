package thosuaongnuoc;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PipePanel extends JPanel {

    private int r = 5;
    private int c = 9;
    private PipeCell arr[][] = new PipeCell[r][c];
    private int over[][] = new int[45][3];
    private int overC = 0;
    private int desk = 0;
    private boolean haveDesk = false;
    private ArrayList<Integer> thamList = new ArrayList<Integer>();
    private JLabel overL[] = new JLabel[45];

    public PipePanel(JLabel overL[]) {
        setLayout(new GridLayout(r, c));
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                arr[i][j] = new PipeCell();
                add(arr[i][j]);
            }
        }
        for (int i = 0; i < 45; i++) {
            this.overL[i] = overL[i];
        }
    }

    public PipePanel() {
        setLayout(new GridLayout(r, c));
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                arr[i][j] = new PipeCell();
                add(arr[i][j]);
            }
        }
    }

    public void reset() {
        thamList.clear();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                arr[i][j].setPipe(-1, 0);
            }
        }
        desk = 0;
        overC = 0;
        for (int i = 0; i < 45; i++) {
            if (overL[i] != null) {
                overL[i].setVisible(false);
            }
        }
    }

    public PipeCell getPipeCell(int x, int y) {
        return arr[x][y];
    }

    public void setPipeCell(int x, int y, int type, int rot) {
        arr[x][y].setPipe(type, rot);
    }

    public void loadArray(int[][] tmp) {
        for (int i = 0; i < 45; i++) {
            arr[i / 9][i % 9].setPipe(tmp[i][0], tmp[i][1]);
            if (tmp[i][0] == 1) {
                desk++;
            }
        }
        if (desk > 0) {
            haveDesk = true;
        } else {
            haveDesk = false;
        }
    }

    public int[][] toArray() {
        int tmp[][] = new int[45][2];
        for (int i = 0; i < 45; i++) {
            tmp[i][0] = arr[i / 9][i % 9].getType();
            tmp[i][1] = arr[i / 9][i % 9].getRotStt();
        }
        return tmp;
    }

    public boolean run() {
        boolean dk1 = checkWay(0, 0, -1);
        boolean dk2;
        if (desk == 0) {
            dk2 = true;
        } else {
            dk2 = false;
        }
        boolean dk3 = haveDesk;
        if (overC != 0) {
            showOverCell();
        }
        return dk1 && dk2 && dk3;
    }

    public void showOverCell() {
        for (int i = 0; i < overC; i++) {
            overL[i].setLocation(this.getLocation().x + over[i][1] * 85, this.getLocation().y + over[i][0] * 85);
            overL[i].setIcon(ImgDB.get("overf_r" + over[i][2] + ".gif"));
            overL[i].setVisible(true);
            System.out.println("Over");
        }
    }

    public boolean checkWay(int x, int y, int in) {            //OK: return -1 || FailL return IN o ke tiep
        System.out.println(x + "-" + y);
        int xT = x, yT = y, inT = 0, slN = 0, slO = 0;
        boolean sttNhanh[] = new boolean[4];
        boolean result;

        //Neu nhay ra ngoai, ko vao duoc -> false
        if ((x < 0 || x > 4) || (y < 0 || y > 8) || arr[x][y].havePort(in) == false) {
            System.out.println("-> False");
            over[overC][0] = x;
            over[overC][1] = y;
            over[overC][2] = in;
            overC++;
            return false;
        }
        //Neu vao lai 1 Source -> true
        if (thamList.contains(x * 9 + y)) {
            int x2 = 0, y2 = 0;
            return true;
        }
        //Vao o Dich -> true
        if (arr[x][y].getType() == 1) {
            desk--;
            return true;
        }

        //Di den O tiep theo
        //Reset PORT
        arr[x][y].resetPort();
        boolean[] inA = arr[x][y].getPin();
        if (in != -1) {
            arr[x][y].setPin(true, in);
        }

        //Tim Cac Port OUT o hien tai
        arr[x][y].setPout(inA);
        boolean outA[] = arr[x][y].getPout();

        //Neu Cell co > 2 PORT OUT cho vao thamList
        //So luong PORT Out
        for (int i = 0; i < 4; i++) {
            if (outA[i] == true) {
                slO++;
            }
        }
        //Them vao ThamList
        if (slO > 1) {
            thamList.add(x * 9 + y);
        }

        //SAP Xep PORT Ngan nhat
        int count[] = {50, 50, 50, 50};
        for (int i = 0; i < 4; i++) {
            if (outA[i] == false) {
                continue;
            }
            inT = arr[x][y].getPortOpposite(i);  //Tim PORT giap port Out
            xT = x;
            yT = y;
            switch (i) {                    //Tim Vitri o ke tiep
                case 0:
                    yT -= 1;
                    break;
                case 1:
                    xT -= 1;
                    break;
                case 2:
                    yT += 1;
                    break;
                case 3:
                    xT += 1;
                    break;
            }
            int c = countWay(xT, yT, inT);
            count[i] = c;//==0? 50: c;
        }
        int[] sortedPort = sortPort(count);

        //Di cac PORT NGAN NHAT
        for (int k = 0; k < 4; k++) {
            int i = sortedPort[k];
            if (outA[i] == false) {
                continue;
            }
            inT = arr[x][y].getPortOpposite(i);  //Tim PORT giap port Out
            xT = x;
            yT = y;
            switch (i) {                    //Tim Vitri o ke tiep
                case 0:
                    yT -= 1;
                    break;
                case 1:
                    xT -= 1;
                    break;
                case 2:
                    yT += 1;
                    break;
                case 3:
                    xT += 1;
                    break;
            }
            sttNhanh[slN++] = checkWay(xT, yT, inT);
            if (arr[xT][yT].getType() == 1) {
                continue; //Loai tru o dich
            }
            if (arr[xT][yT].getIntPortOut() == 0 || arr[xT][yT].getPout(inT) == true || arr[xT][yT].removed(inT) == true) {
                arr[x][y].remPort(i);
            }

        }
        //Render hinh
        //setImage(x, y);
        if (arr[x][y].getIntPortOut() == 0 && arr[x][y].getType() != 0) {
            arr[x][y].cellIde();
        } else {
            arr[x][y].cellRun();
        }

        //TOng hop ket qua cac LUOT DI
        result = sttNhanh[0];
        for (int i = 1; i < slN; i++) {
            result = result && sttNhanh[i];
        }

        return result;
    }

    public int[] sortPort(int[] count) {
        int port[] = {0, 1, 2, 3};
        int min, tmp, k;
        for (int i = 0; i < 3; i++) {
            min = i;
            for (int j = i + 1; j < 4; j++) {
                if (count[j] < count[min]) {
                    min = j;
                }
            }
            tmp = count[i];
            count[i] = count[min];
            count[min] = tmp;

            tmp = port[i];
            port[i] = port[min];
            port[min] = tmp;
        }
        return port;
    }

    public int countWay(int x, int y, int in) {
        if (arr[x][y].getType() != 0) {
            if ((x < 0 || x > 4) || (y < 0 || y > 8) || arr[x][y].havePort(in) == false) {
                return 0;
            }
        }
        if (arr[x][y].getType() != 2 && arr[x][y].getType() != 3) {
            return 0;
        }
        //Tim toa do o tiep theo
        int inT = 0, xT = 0, yT = 0;
        arr[x][y].resetPort();
        boolean[] inA = arr[x][y].getPin();
        if (in != -1) {
            arr[x][y].setPin(true, in);
        }
        arr[x][y].setPout(inA);
        boolean outA[] = arr[x][y].getPout();
        for (int i = 0; i < 4; i++) {
            if (outA[i] == false) {
                continue;
            }
            inT = arr[x][y].getPortOpposite(i);
            xT = x;
            yT = y;
            switch (i) {
                case 0:
                    yT -= 1;
                    break;
                case 1:
                    xT -= 1;
                    break;
                case 2:
                    yT += 1;
                    break;
                case 3:
                    xT += 1;
                    break;
            }
        }
        return 1 + countWay(xT, yT, inT);
    }
}

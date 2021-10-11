package thosuaongnuoc;

import javax.swing.JButton;

public class PipeCell extends JButton {

    private int type;
    private int rotStt = 0;
    private boolean drr[] = {false, false, false, false}; //L,T,R,B
    public boolean Pout[] = {false, false, false, false};
    public boolean Pin[] = {false, false, false, false};

    public PipeCell() {
        type = -1;
        cellEmpty();
    }

    ;
    
    public PipeCell(int type, int rot) {
        setPipe(type, rot);
    }

    public void resetPort() {
        Pin[0] = Pin[1] = Pin[2] = Pin[3] = false;
        Pout[0] = Pout[1] = Pout[2] = Pout[3] = false;
    }

    public boolean[] getPin() {
        return Pin;
    }

    public boolean getPin(int i) {
        return Pin[i];
    }

    public void setPin(boolean stt, int i) {
        Pin[i] = stt;
    }

    public boolean[] getDRR() {
        return drr;
    }

    public void setPout(boolean stt, int i) {
        Pout[i] = stt;
    }

    public void setPout(boolean in[]) {
        for (int i = 0; i < 4; i++) {
            if (in[i] == true) {
                continue;
            }
            Pout[i] = drr[i];
        }
    }

    public int getIntPortIn() {
        int inI = 0;
        for (int i = 0; i < 4; i++) {
            inI *= 2;
            if (Pin[i] == true) {
                inI += 1;
            }
        }
        return inI;
    }

    public int getIntPortOut() {
        int outI = 0, inI = 0;
        for (int i = 0; i < 4; i++) {
            outI *= 2;
            if (Pout[i] == true) {
                outI += 1;
            }
            if (Pin[i] == true) {
                inI += 1;
            }
        }
        if (outI == 0 && inI == 0) {
            return -1;
        } else {
            return outI;
        }
    }

    public boolean[] getPout() {
        return Pout;
    }

    public boolean getPout(int i) {
        return Pout[i];
    }

    public void remPort(int i) {
        Pin[i] = Pout[i] = false;
    }

    public boolean removed(int i) {
        if (drr[i] == true && Pin[i] == false && Pout[i] == false) {
            return true;
        }
        return false;
    }

    public boolean havePort(int pIn) {
        if (pIn == -1) {
            return true;
        }
        return drr[pIn];
    }

    public boolean canIn(int i) {
        return Pin[i];
    }

    public int getPortOpposite(int out) {
        return out == 0 ? 2 : out == 1 ? 3 : out == 2 ? 0 : 1;
    }

    public void rotatePipe(int rot) {
        if (type == 5) {
            cellEmpty();
            return;
        }
        for (int j = 1; j <= rot; j++) {
            boolean tmp = drr[3];
            for (int i = 3; i > 0; i--) {
                drr[i] = drr[i - 1];
            }
            drr[0] = tmp;
            if (rotStt + 1 > 3) {
                rotStt = 0;
            } else {
                rotStt += 1;
            }
        }
        cellEmpty();
    }

    public void resetCell() {
        rotStt = 0;
        drr[0] = drr[1] = drr[2] = drr[3] = false;
    }

    public void cellEmpty() {
        if (type == -1) {
            setIcon(ImgDB.get("null.png"));
        } else {
            setIcon(ImgDB.get("p" + type + "e_r" + rotStt + ".png"));
        }
    }

    public void cellRun() {
        String inI = "", outI = "";
        if (type == 5) {
            rotStt = 0;
        }
        if (type == 0) {
            setIcon(ImgDB.get("p0r.gif"));
        } else if (type != 1) {
            for (int i = 0; i < 4; i++) {
                if (Pin[i] == true) {
                    inI += 1;
                } else {
                    inI += 0;
                }
                if (Pout[i] == true) {
                    outI += 1;
                } else {
                    outI += 0;
                }
            }
            setIcon(ImgDB.get("p" + type + "r_" + inI + "_" + outI + "_" + rotStt + ".gif"));
        }
    }

    public void cellIde() {
        setIcon(ImgDB.get("p" + type + "i_r" + rotStt + ".gif"));
    }

    public int getType() {
        return type;
    }

    public int getRotStt() {
        return rotStt;
    }

    public void setPipe(int type, int rot) {
        resetCell();
        this.type = type;
        switch (type) {
            case -1:
                drr[0] = drr[1] = drr[2] = drr[3] = false;
                rot = 0;
                break;
            case 0:
                drr[2] = true;
                break;
            case 1:
                drr[1] = true;
                break;
            case 2:
                drr[0] = drr[2] = true;
                break;
            case 3:
                drr[0] = drr[3] = true;
                break;
            case 4:
                drr[0] = drr[1] = drr[3] = true;
                break;
            case 5:
                drr[0] = drr[1] = drr[2] = drr[3] = true;
        }
        cellEmpty();
        rotatePipe(rot);
    }
}

package thosuaongnuoc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StageLayer extends JLayeredPane implements ActionListener {

    //Layer
    private final JPanel msgP;
    private final JPanel mainP;

    //Main Content Panel
    private final PipePanel pipeP;
    private final JLabel lbStage;

    private final JLabel lbTime;
    private final JLabel lbStep;
    private final JButton btnRun;
    private final JButton btnHome;

    //Font
    private final Font cFont = new Font("Circle", Font.PLAIN, 24);
    private final Font dFont = new Font("SF Digital Readout", Font.BOLD, 40);
    private final Font eFont = new Font("UVN Giay Trang", Font.PLAIN, 24);
    private final Font mFont = new Font("MercuriusScript", Font.BOLD, 20);
    //Vars
    private GLevel tmpL;
    private boolean win;
    private int timeC;
    private int timeT;
    private int stepsT;
    private int stepsC;
    private int scores;
    private boolean stageRunning = false;
    private JPanel msg_form = null;
    private JLabel msg_glass;

    private Timer time = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (isRunning()) {
                timeC -= 1;
                refeshInfo();
                if (timeC == 0) {
                    endStage();
                    stopStage();
                }
            }
        }
    });

    private Timer timeE = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!win) {      //Thua
                msg_lbTitle.setText("Thua Rồi!!");
            } else {            //Thang
                msg_lbTitle.setText("Thắng Rồi!!");
            }
            msg_form.setVisible(true);
            msg_glass.setVisible(true);
            timeE.stop();
        }
    });

    private int stepTs;
    private final JLabel msg_lbTitle;
    private final JLabel msg_lbLevel;
    private final JLabel msg_lbTime;
    private final JLabel msg_scores;
    private final JLabel msg_sttPic;
    private final JLabel msg_lbStep;
    private final JButton btnReplayE;
    private final JButton btnHomeE;
    private final JButton btnNextE;
    private final JButton btnLevel;
    private final JLabel[] overL = new JLabel[45];

    public StageLayer() {
        setLayout(null);
        //=========  Main stage
        mainP = new JPanel();
        mainP.setBounds(0, 0, 800, 600);
        mainP.setLayout(null);
        add(mainP, new Integer(1));

        for (int i = 0; i < 45; i++) {
            overL[i] = new JLabel();
            overL[i].setBounds(0, 0, 85, 85);
            overL[i].setVisible(false);
            mainP.add(overL[i]);
        }

        //Man choi
        lbStage = new JLabel();
        lbStage.setFont(cFont);
        lbStage.setBounds(20, 10, 150, 50);
        lbStage.setHorizontalTextPosition(JLabel.CENTER);
        lbStage.setVerticalTextPosition(JLabel.CENTER);
        lbStage.setForeground(Color.BLACK);

        //So buoc
        lbStep = new JLabel();
        lbStep.setFont(cFont);
        lbStep.setBounds(20, 70, 150, 50);
        lbStep.setHorizontalTextPosition(JLabel.CENTER);
        lbStep.setVerticalTextPosition(JLabel.CENTER);
        lbStep.setForeground(Color.BLACK);

        //Time
        lbTime = new JLabel();
        lbTime.setFont(dFont);
        lbTime.setBounds(300, 5, 150, 50);
        lbTime.setVerticalTextPosition(JLabel.CENTER);
        lbTime.setHorizontalTextPosition(JLabel.CENTER);
        lbTime.setForeground(Color.BLACK);

        //Mo nuoc
        btnRun = new JButton("Mở Nước");
        btnRun.setFont(mFont);
        btnRun.setBounds(520, 40, 150, 50);
        btnRun.addActionListener(this);

        //Home
        btnHome = new JButton("Home");
        btnHome.setFont(mFont);
        btnHome.setBounds(520 + 160, 40, 90, 50);

        //PipePanel
        pipeP = new PipePanel(overL);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                pipeP.getPipeCell(i, j).addActionListener(this);
            }
        }
        pipeP.setBounds((800 - (85 * 9)) / 2, 125, 85 * 9 - 5, 85 * 5 - 5);

        //Add, component nao add sau se chiem xuong
        //add(endP);
        mainP.add(pipeP);
        mainP.add(lbStage);
        mainP.add(lbStep);
        mainP.add(lbTime);
        mainP.add(btnRun);
        mainP.add(btnHome);

        setVisible(false);
//=========Msg
        //glass
        msgP = new JPanel();
        msgP.setOpaque(false);
        msgP.setBounds(0, 0, 800, 600);
        msgP.setLayout(null);
        msgP.addMouseListener(new MouseAdapter() {
        });
        add(msgP, new Integer(0));

        //form
        msg_form = new JPanel();
        //
        /*
      
        show/hide mesages
         */
        //msg_form.setOpaque(false);
        /*
            
        
         */
        msg_form.setBackground(Color.ORANGE);
        msg_form.setLayout(null);
        msg_form.setBounds(110, 100, 575, 385);
        msgP.add(msg_form);

        //Win Or Lose
        msg_lbTitle = new JLabel();//ImgDB.get("notice.png")
        msg_lbTitle.setBounds(60, 40, 180, 40);
        msg_lbTitle.setFont(cFont);
        msg_lbTitle.setVerticalTextPosition(JLabel.CENTER);
        msg_lbTitle.setHorizontalTextPosition(JLabel.CENTER);
        msg_lbTitle.setForeground(Color.BLACK);
        msg_form.add(msg_lbTitle);

        //Level
        JLabel tmp0 = new JLabel("Màn chơi");
        tmp0.setFont(eFont);
        tmp0.setBounds(50, 90, 100, 30);
        msg_form.add(tmp0);
        msg_lbLevel = new JLabel();
        msg_lbLevel.setFont(eFont);
        msg_lbLevel.setBounds(200, 90, 50, 30);
        msg_lbLevel.setHorizontalAlignment(JLabel.RIGHT);
        msg_form.add(msg_lbLevel);
        //Step
        JLabel tmp1 = new JLabel("Số lượt đi");
        tmp1.setFont(eFont);
        tmp1.setBounds(50, 120, 150, 30);
        msg_form.add(tmp1);
        msg_lbStep = new JLabel();
        msg_lbStep.setFont(eFont);
        msg_lbStep.setBounds(200, 120, 50, 30);
        msg_lbStep.setHorizontalAlignment(JLabel.RIGHT);
        msg_form.add(msg_lbStep);

        //Time
        JLabel tmp2 = new JLabel("Thời gian chơi");
        tmp2.setFont(eFont);
        tmp2.setBounds(50, 150, 150, 30);
        msg_form.add(tmp2);
        msg_lbTime = new JLabel();
        msg_lbTime.setFont(eFont);
        msg_lbTime.setBounds(200, 150, 50, 30);
        msg_lbTime.setHorizontalAlignment(JLabel.RIGHT);
        msg_form.add(msg_lbTime);

        //Split bar
        JLabel splitbar = new JLabel();
        splitbar.setOpaque(true);
        splitbar.setBounds(50, 180, 200, 3);
        msg_form.add(splitbar);

        //Diem
        JLabel tmp3 = new JLabel("Tổng Điểm");
        tmp3.setFont(eFont);
        tmp3.setBounds(50, 190, 150, 30);
        msg_form.add(tmp3);

        msg_scores = new JLabel();
        msg_scores.setFont(eFont);
        msg_scores.setBounds(150, 190, 90, 30);
        msg_scores.setHorizontalAlignment(JLabel.RIGHT);
        msg_form.add(msg_scores);

        //sttPic
        msg_sttPic = new JLabel();
        msg_sttPic.setBounds(265, 50, 250, 210);
        msg_form.add(msg_sttPic);

        //Replay
        btnReplayE = new JButton("Chơi lại");
        btnReplayE.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnReplayE.setBounds(100, 290, 80, 50);
        msg_form.add(btnReplayE);
        //Home
        btnHomeE = new JButton("Home");
        btnHomeE.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnHomeE.setBounds(400, 290, 80, 50);
        msg_form.add(btnHomeE);
        //LevelList
        btnLevel = new JButton("Levels");
        btnLevel.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnLevel.setBounds(200, 290, 80, 50);
        msg_form.add(btnLevel);

        //Next
        btnNextE = new JButton("Next");
        btnNextE.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnNextE.setBounds(300, 290, 60, 50);
        msg_form.add(btnNextE);

        //form back
        JLabel msg_form_back = new JLabel();
        msg_form_back.setBounds(0, 0, 575, 385);
        msg_form.add(msg_form_back);

        //glass
        msg_glass = new JLabel();
        msg_glass.addMouseListener(new MouseAdapter() {
        });
        msg_glass.setBounds(0, 0, 800, 600);
        msgP.add(msg_glass);

        setVisible(false);
    }

    public void loadLevel(GLevel level) {
        pipeP.reset();
        tmpL = level;
        timeT = timeC = level.getTime();
        stepsT = stepsC = level.getStep();
        pipeP.loadArray(level.getMap());
    }

    public void startStage() {
        msgP.setVisible(false);
        stageRunning = true;
        //tip.setVisible(false);

        if (tmpL.getName().substring(0, 2).equals("sm")) {
            lbStage.setText("Màn " + tmpL.getSName());
        } else {
            lbStage.setText(tmpL.getSName());
        }
        refeshInfo();
        time.start();
        setVisible(true);
    }

    public boolean isRunning() {
        return stageRunning;
    }

    public void stopStage() {
        stageRunning = false;
    }

    public void refeshInfo() {
        //step
        lbStep.setText(Integer.toString(stepsC));
        //time
        int m = timeC / 60;
        int s = timeC % 60;
        lbTime.setText(String.format("%02d:%02d", m, s));
    }

    public boolean getStatus() {
        return win;
    }

    public void endStage() {

        if (pipeP.run() == true) { //win
            scores = stepsC * 10 + timeC * 10;
            win = true;
            if (scores > tmpL.getScores()) {
                tmpL.setScores(scores);
                LevelDB.updateInLevelFile(tmpL);
            }
        } else { //lose
            win = false;
            scores = 0;
        }
        showMsg();
        stopStage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRun) {                      //Mo nuoc
            endStage();
            pipeP.getPipeCell(0, 0).cellRun();
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (pipeP.getPipeCell(i, j).getType() == 0
                        || pipeP.getPipeCell(i, j).getType() == 1
                        || pipeP.getPipeCell(i, j).getType() == -1) {
                    continue;
                }
                if (e.getSource() == pipeP.getPipeCell(i, j)) {
                    pipeP.getPipeCell(i, j).rotatePipe(1);
                    stepsC--;
                    refeshInfo();
                    if (stepsC == 0) {
                        endStage();
                    }
                }
            }
        }
    }

    //MSG BOX
    public void showMsg() {
        //reset
        msg_form.setVisible(false);
        msg_glass.setVisible(false);
        msgP.setVisible(true);
        //show title, step,time, scores
        this.setLayer(mainP, 0);
        this.setLayer(msgP, 1);

        msg_lbLevel.setText(tmpL.getSName());
        msg_lbStep.setText(String.valueOf(stepsT - stepsC));
        msg_lbTime.setText(String.valueOf(timeT - timeC));
        msg_scores.setText(String.valueOf(scores));

        //show next btn
        if (tmpL.getType() == 0 && LevelDB.canNext() && win == true) {
            LevelDB.unlockNextLevel();
            btnNextE.setVisible(true);
        } else {
            btnNextE.setVisible(false);
        }

        timeE.restart();
    }

    //GET BUTTON
    public JButton getBtnLevel() {
        return btnLevel;
    }

    public JButton getBtnReplayE() {
        return btnReplayE;
    }

    public JButton getBtnHomeE() {
        return btnHomeE;
    }

    public JButton getBtnNextE() {
        return btnNextE;
    }

    public JButton getBtnHome() {
        return btnHome;
    }
}

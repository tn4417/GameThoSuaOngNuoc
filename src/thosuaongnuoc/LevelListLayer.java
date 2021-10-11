package thosuaongnuoc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class LevelListLayer extends JLayeredPane implements ActionListener {

    private final JPanel mainP;
    private final JPanel msgP;
    private final QuestPanel quest;

    private final Font mFont = new Font("MercuriusScript", Font.BOLD, 20);
    private final JButton btnLevel[] = new JButton[15];

    private int type = 0;

    private final JLabel lbpage;

    private final JButton btnNext;
    private final JButton btnPrev;
    private final JButton btnHome;
    private final JButton btnMyLevel;
    private final JButton btnSysLevel;
    private final JButton btnPlay;
    private final JButton btnRemove;
    private final JButton btnEdit;
    private final JButton btnClose;
    private final JButton btnCreate;
    private final JButton btnReset;

    private int maxPage = 0;
    private int curPage = 0;
    private final JLabel msgP_star;
    private final JLabel msgP_thumb;
    private final JLabel msgP_lbTen;
    private final JLabel msgP_lbTime;
    private final JLabel msgP_lbStep;
    private final JLabel msgP_lbScores;
    private final JLabel lbTypeTag;

    public LevelListLayer() {
        setLayout(null);
//========Quest
        quest = new QuestPanel();
        quest.getBtnYes().addActionListener(this);
        quest.getBtnNo().addActionListener(this);
        add(quest, new Integer(2));

//========Main
        mainP = new JPanel();
        mainP.setLayout(null);
        mainP.setBounds(0, 0, 800, 600);
        add(mainP, new Integer(1));

        JPanel form = new JPanel();
        form.setBounds(20, 20, 760, 530);
        form.setLayout(null);
        //form.setOpaque(false);
        mainP.add(form);

        JLabel title_lb = new JLabel("Chọn màn chơi");
        title_lb.setVerticalTextPosition(JLabel.CENTER);
        title_lb.setHorizontalTextPosition(JLabel.CENTER);
        title_lb.setBounds(290, 20, 180, 40);
        title_lb.setFont(mFont.deriveFont(Font.BOLD, 25));
        title_lb.setForeground(Color.BLACK);
        form.add(title_lb);

        JPanel level_p = new JPanel();
        level_p.setLayout(null);
        level_p.setBounds(75, 80, 620, 370);
        //level_p.setOpaque(false);
        form.add(level_p);

        for (int i = 0; i < 15; i++) {
            btnLevel[i] = new JButton();
            btnLevel[i].setBounds(30 + (i > 4 ? i > 9 ? i - 10 : i - 5 : i) * 120, i > 4 ? i > 9 ? 230 : 130 : 30, 95, 95);
            btnLevel[i].setHorizontalTextPosition(JButton.CENTER);
            btnLevel[i].setVerticalTextPosition(JButton.CENTER);
            btnLevel[i].setForeground(Color.BLACK);
            btnLevel[i].addActionListener(this);
            level_p.add(btnLevel[i]);
        }

        lbpage = new JLabel();
        lbpage.setBounds(280, 330, 60, 20);
        lbpage.setFont(mFont.deriveFont(Font.BOLD, 25));
        level_p.add(lbpage);
        btnPrev = new JButton("<");
        btnPrev.setFont(mFont);
        btnPrev.setBounds(100, 460, 50, 50);
        btnPrev.addActionListener(this);
        form.add(btnPrev);

        btnNext = new JButton(">");
        btnNext.setFont(mFont);
        btnNext.setBounds(160, 460, 50, 50);
        btnNext.addActionListener(this);
        form.add(btnNext);

        btnSysLevel = new JButton("Level");
        btnSysLevel.setFont(mFont);
        btnSysLevel.setBounds(500, 460, 100, 50);
        btnSysLevel.setVisible(false);
        btnSysLevel.addActionListener(this);
        form.add(btnSysLevel);

        btnMyLevel = new JButton("User");
        btnMyLevel.setFont(mFont);
        btnMyLevel.setBounds(500, 460, 100, 50);
        btnMyLevel.addActionListener(this);
        form.add(btnMyLevel);

        btnHome = new JButton("Home");
        btnHome.setFont(mFont);
        btnHome.setBounds(620, 460, 100, 50);
        form.add(btnHome);

        btnCreate = new JButton("Create");
        btnCreate.setFont(mFont);
        btnCreate.setBounds(620, 20, 100, 50);
        form.add(btnCreate);

        btnReset = new JButton("Reset");
        btnReset.setFont(mFont);
        btnReset.setBounds(100 - 10, 20, 100, 50);
        form.add(btnReset);

        lbTypeTag = new JLabel("");
        lbTypeTag.setBounds(760 / 2 - 100, 465, 200, 40);
        lbTypeTag.setFont(mFont.deriveFont(Font.BOLD, 30));
        lbTypeTag.setHorizontalAlignment(JLabel.CENTER);
        form.add(lbTypeTag);

//========== MSG panel
        msgP = new JPanel();
        msgP.setLayout(null);
        msgP.setBounds(0, 0, 800, 600);
        msgP.setOpaque(false);
        add(msgP, new Integer(0));

        //form
        JPanel msgP_form = new JPanel();
        msgP_form.setLayout(null);
        msgP_form.setBounds(400 - 380 / 2, 300 - 220 / 2, 380, 220);
        //msgP_form.setOpaque(false);
        msgP_form.setBackground(Color.ORANGE);
        msgP.add(msgP_form);

        //star
        msgP_star = new JLabel();
        msgP_star.setBounds(230, 110, 120, 40);
        msgP_form.add(msgP_star);

        //thumbnail
        msgP_thumb = new JLabel();
        msgP_thumb.setBounds(225, 20, 130, 130);
        msgP_form.add(msgP_thumb);

        //lb Ten man
        msgP_lbTen = new JLabel();
        msgP_lbTen.setBounds(10, 20, 180, 40);
        msgP_lbTen.setHorizontalTextPosition(JLabel.CENTER);
        msgP_lbTen.setVerticalTextPosition(JLabel.CENTER);
        msgP_lbTen.setFont(mFont.deriveFont(Font.PLAIN, 25));
        msgP_lbTen.setForeground(Color.BLACK);
        msgP_form.add(msgP_lbTen);

        //lb Thoi gian
        JLabel msgP_lbTimeTag = new JLabel("Thời gian:");
        msgP_lbTimeTag.setBounds(20, 65, 100, 25);
        msgP_lbTimeTag.setFont(mFont.deriveFont(Font.PLAIN, 20));
        msgP_form.add(msgP_lbTimeTag);

        msgP_lbTime = new JLabel("");
        msgP_lbTime.setBounds(140, 65, 40, 25);
        msgP_lbTime.setFont(mFont.deriveFont(Font.PLAIN, 20));
        msgP_lbTime.setHorizontalAlignment(JLabel.RIGHT);
        msgP_form.add(msgP_lbTime);

        //lb Luot di
        JLabel msgP_lbStepTag = new JLabel("Lượt đi:");
        msgP_lbStepTag.setBounds(20, 90, 100, 25);
        msgP_lbStepTag.setFont(mFont.deriveFont(Font.PLAIN, 20));
        msgP_form.add(msgP_lbStepTag);

        msgP_lbStep = new JLabel("");
        msgP_lbStep.setBounds(140, 90, 40, 25);
        msgP_lbStep.setFont(mFont.deriveFont(Font.PLAIN, 20));
        msgP_lbStep.setHorizontalAlignment(JLabel.RIGHT);
        msgP_form.add(msgP_lbStep);

        //lb Diem Cao
        JLabel msgP_lbHSTag = new JLabel("Điểm cao:");
        msgP_lbHSTag.setBounds(20, 115, 100, 25);
        msgP_lbHSTag.setFont(mFont.deriveFont(Font.PLAIN, 20));
        msgP_form.add(msgP_lbHSTag);

        msgP_lbScores = new JLabel("");
        msgP_lbScores.setBounds(130, 115, 50, 25);
        msgP_lbScores.setFont(mFont.deriveFont(Font.PLAIN, 20));
        msgP_lbScores.setHorizontalAlignment(JLabel.RIGHT);
        msgP_form.add(msgP_lbScores);

        //btn Play
        btnPlay = new JButton("Play");
        btnPlay.setFont(mFont);
        btnPlay.setBounds(20, 155, 150, 50);
        msgP_form.add(btnPlay);

        btnRemove = new JButton("Xoá");
        btnRemove.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnRemove.setBounds(205 - 20, 155, 60, 50);
        btnRemove.addActionListener(this);
        msgP_form.add(btnRemove);

        btnEdit = new JButton("Edit");
        btnEdit.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnEdit.setBounds(266 - 20, 155, 60, 50);
        msgP_form.add(btnEdit);

        btnClose = new JButton("Close");
        btnClose.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnClose.setBounds(327 - 20, 155, 60, 50);
        btnClose.addActionListener(this);
        msgP_form.add(btnClose);
        this.setVisible(false);
    }

    public void init(int type) {
        this.type = type;
        if (type == 0) {
            btnSysLevel.setVisible(false);
            btnMyLevel.setVisible(true);
            calMaxPage(type);
        } else {
            btnSysLevel.setVisible(true);
            btnMyLevel.setVisible(false);
            calMaxPage(type);
        }
        this.setLayer(mainP, 1);
        this.setLayer(msgP, 0);
    }

    private void calMaxPage(int type) {
        int count = type == 0 ? LevelDB.getcSys() : LevelDB.getcUser();
        maxPage = count / 15 + (count % 15 == 0 ? 0 : 1);
    }

    public JButton getBtnReset() {
        return btnReset;
    }

    public JButton getBtnCreate() {
        return btnCreate;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnHome() {
        return btnHome;
    }

    public JButton getBtnPlay() {
        return btnPlay;
    }

    public JButton getBtnComRemove() {
        return quest.getBtnYes();
    }

    public int getType() {
        return type;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public QuestPanel getQuest() {
        return quest;
    }

    public void setImagePage() {
        lbpage.setIcon(ImgDB.get("page_" + curPage + maxPage + ".png"));
    }

    public void showLevel(JButton btn, GLevel level) {
        btn.setVisible(false);
        if (level == null) {
            return;
        }
        if (!level.getLooked() || type == 1) {
            btn.setText(level.getSName());
            btn.setFont(mFont);
            btn.setVisible(true);
        } else {
            btn.setText("");
            btn.setFont(mFont);
            btn.setVisible(false);
        }
    }

    public void showMsg() {
        //xu li thong tin
        String name = LevelDB.getCurLevel().getName().substring(0, LevelDB.getCurLevel().getName().length() - 4);
        msgP_lbTime.setText(LevelDB.getCurLevel().getTime() + "");
        msgP_lbStep.setText(LevelDB.getCurLevel().getStep() + "");
        msgP_lbScores.setText(LevelDB.getCurLevel().getScores() + "");
        if (LevelDB.getCurLevel().getLooked()) {
            btnPlay.setEnabled(false);
        } else {
            btnPlay.setEnabled(true);
        }
        //xu li form
        this.setLayer(msgP, 1);
        this.setLayer(mainP, 0);
        if (type == 0) {
            msgP_lbTen.setText("Màn " + name.substring(2));
            btnRemove.setVisible(false);
            btnEdit.setVisible(false);
        } else {
            msgP_lbTen.setText(name.substring(2));
            btnRemove.setVisible(true);
            btnEdit.setVisible(true);
        }

    }

    public void showPage(int page) {
        GLevel tmp = null;
        curPage = page;
        setImagePage();
        if (type == 0) {
            lbTypeTag.setText("Mùa " + curPage);
        } else {
            lbTypeTag.setText("Tự xây dựng");
        }
        for (int i = 0; i < 15; i++) {
            switch (page) {
                case 1:
                    tmp = LevelDB.getLevel(type, i);
                    break;
                case 2:
                    tmp = LevelDB.getLevel(type, i + 15);
                    break;
                case 3:
                    tmp = LevelDB.getLevel(type, i + 30);
                    break;
            }
            showLevel(btnLevel[i], tmp);
        }
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);
        if (curPage == 1) {
            btnPrev.setEnabled(false);
        }
        if (curPage == maxPage) {
            btnNext.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent me) {
        if (me.getSource() == btnMyLevel) {
            type = 1;
            calMaxPage(type);
            showPage(1);
            btnMyLevel.setVisible(false);
            btnSysLevel.setVisible(true);
        }
        if (me.getSource() == btnSysLevel) {
            type = 0;
            calMaxPage(type);
            showPage(1);
            btnSysLevel.setVisible(false);
            btnMyLevel.setVisible(true);
        }
        if (me.getSource() == btnPrev) {
            if (curPage > 1) {
                showPage(curPage - 1);
            }
            btnPrev.setEnabled(true);
            btnNext.setEnabled(true);
            if (curPage == 1) {
                btnPrev.setEnabled(false);
            }
            if (curPage == maxPage) {
                btnNext.setEnabled(false);
            }
        }
        if (me.getSource() == btnNext) {
            if (curPage < maxPage) {
                showPage(curPage + 1);
            }
            btnPrev.setEnabled(true);
            btnNext.setEnabled(true);

            if (curPage == 1) {
                btnPrev.setEnabled(false);
            }
            if (curPage == maxPage) {
                btnNext.setEnabled(false);
            }
        }
        if (me.getSource() == btnClose) {
            this.setLayer(msgP, 0);
            this.setLayer(mainP, 1);
        }
        if (me.getSource() == btnRemove) {
            quest.showQuest("Bạn có muốn xóa màn chơi này?", 25);
        }
        if (me.getSource() == quest.getBtnNo()) {
            quest.hideQuest();
        }

        for (int i = 0; i < 15; i++) {
            if (me.getSource() == btnLevel[i]) {
                LevelDB.setCurLevel(type, i + (curPage - 1) * 15);
                showMsg();
            }
        }
    }
}

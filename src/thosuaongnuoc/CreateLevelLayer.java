package thosuaongnuoc;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

public class CreateLevelLayer extends JLayeredPane implements MouseListener, ActionListener {

    private final PipePanel pipeP;
    private final JPanel mainP;
    private final JPanel saveP;
    private final QuestPanel quest;

    private final JPanel ctrlP;
    private final JButton btnHome;
    private final JButton btnSave;
    private final JButton btnOk;
    private final JButton btnCancel;
    private final JLabel saveFormLbErr;
    private final JLabel lbLevelName;
    private final JTextField saveFormTfName;
    private final JTextField saveFormTfStep;
    private final JTextField saveFormTfTime;

    private final PipeCell pipe1 = new PipeCell(1, 0);
    private final PipeCell pipe2 = new PipeCell(2, 0);
    private final PipeCell pipe3 = new PipeCell(3, 0);
    private final PipeCell pipeN = new PipeCell(-1, 0);
    private final PipeCell pipe4 = new PipeCell(4, 0);
    private final PipeCell pipe5 = new PipeCell(5, 0);

    private int mode;
    private PipeCell curPipe = pipeN;
    private Font inputFont = new Font("MercuriusScript", Font.BOLD, 20);
    private GLevel editL = null;
    private int typeq;

    public CreateLevelLayer() {
        setLayout(null);
//========Quest
        quest = new QuestPanel();
        quest.getBtnNo().addActionListener(this);
        quest.getBtnYes().addActionListener(this);
        add(quest, new Integer(3));

//========Main Pane
        mainP = new JPanel();
        mainP.setLayout(null);
        mainP.setBounds(0, 0, 800, 600);
        add(mainP, new Integer(1));

        //Control panel
        ctrlP = new JPanel();
        ctrlP.setBounds(10, 10, 775, 100);
        ctrlP.setLayout(null);
        //ctrlP.setOpaque(false);
        mainP.add(ctrlP);

        //lbName
        lbLevelName = new JLabel();
        lbLevelName.setFont(inputFont);
        lbLevelName.setBounds(630, 5, 110, 30);
        lbLevelName.setHorizontalAlignment(JLabel.CENTER);
        ctrlP.add(lbLevelName);

        //btnHome
        btnHome = new JButton("Home");
        btnHome.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnHome.setBounds(700, 40, 60, 50);
        ctrlP.add(btnHome);

        //btnSave
        btnSave = new JButton("Save");
        btnSave.setFont(new Font("MercuriusScript", Font.BOLD, 10));
        btnSave.setBounds(630, 40, 60, 50);
        btnSave.addActionListener(this);
        ctrlP.add(btnSave);

        //Control panel PipeCell
        pipeN.setBounds(10, 7, 85, 85);
        pipeN.addActionListener(this);
        pipeN.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        ctrlP.add(pipeN);

        pipe1.setBounds(100, 7, 85, 85);
        pipe1.addActionListener(this);
        ctrlP.add(pipe1);

        pipe2.setBounds(190, 7, 85, 85);
        pipe2.addActionListener(this);
        ctrlP.add(pipe2);

        pipe3.setBounds(280, 7, 85, 85);
        pipe3.addActionListener(this);
        ctrlP.add(pipe3);

        pipe4.setBounds(370, 7, 85, 85);
        pipe4.addActionListener(this);
        ctrlP.add(pipe4);

        pipe5.setBounds(460, 7, 85, 85);
        pipe5.addActionListener(this);
        ctrlP.add(pipe5);

        //Control panel Back
        JLabel ctrlP_back = new JLabel();
        ctrlP_back.setBounds(0, 0, 775, 100);
        ctrlP.add(ctrlP_back);

        //Pipe Panel;
        pipeP = new PipePanel();
        pipeP.setBounds((800 - (85 * 9)) / 2, 125, 85 * 9 - 5, 85 * 5 - 5);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                pipeP.getPipeCell(i, j).addMouseListener(this);
            }
        }
        mainP.add(pipeP);

        //BackGnd
        JLabel backgnd = new JLabel();
        backgnd.setBounds(0, 0, 800, 600);
        mainP.add(backgnd);

//======== Save Pane
        //SaveP
        saveP = new JPanel();
        saveP.setLayout(null);
        saveP.setBounds(0, 0, 800, 600);
        //saveP.setOpaque(false);
        saveP.addMouseListener(new MouseAdapter() {
        });
        add(saveP, new Integer(0));

        //form
        JPanel save_form = new JPanel();
        save_form.setBounds(250, 100, 300, 330);
        save_form.setLayout(null);
        //save_form.setOpaque(false);
        saveP.add(save_form);

        //offset
        int xOff = 0;
        int yOff = 0;

        //Title
        JLabel saveFormLbTitle = new JLabel("Lưu Màn Chơi");
        saveFormLbTitle.setVerticalTextPosition(JLabel.CENTER);
        saveFormLbTitle.setHorizontalTextPosition(JLabel.CENTER);
        saveFormLbTitle.setBounds(60, 10, 180, 40);
        saveFormLbTitle.setFont(inputFont);
        saveFormLbTitle.setForeground(Color.BLACK);
        save_form.add(saveFormLbTitle);

        //Name TextField
        JLabel saveFormLbNameTag = new JLabel("Nhập Tên: ");
        saveFormLbNameTag.setBounds(20 + xOff, 73 + yOff, 100, 35);
        saveFormLbNameTag.setFont(inputFont);
        saveFormLbNameTag.setForeground(Color.BLACK);
        save_form.add(saveFormLbNameTag);

        saveFormTfName = new JTextField();
        saveFormTfName.requestFocus();
        saveFormTfName.setBounds(saveFormLbNameTag.getBounds().x + 110, saveFormLbNameTag.getBounds().y, 135, 35);
        saveFormTfName.setCaretColor(Color.BLACK);
        //saveFormTfName.setOpaque(false);
        saveFormTfName.setHorizontalAlignment(JTextField.CENTER);
        saveFormTfName.setFont(inputFont.deriveFont(20));
        saveFormTfName.setDocument(new LimitDocument(4, false));
        save_form.add(saveFormTfName);

        JLabel saveFormTfBack = new JLabel();
        saveFormTfBack.setBounds(saveFormTfName.getBounds().x - 7, saveFormTfName.getBounds().y - 3,
                saveFormTfName.getBounds().width + 15, saveFormTfName.getBounds().height + 5);
        save_form.add(saveFormTfBack);

        //Step TextField
        JLabel step_lb = new JLabel("Số Lượt Đi: ");
        step_lb.setBounds(20 + xOff, 123 + yOff, 150, 35);
        step_lb.setFont(inputFont);
        save_form.add(step_lb);

        saveFormTfStep = new JTextField();
        saveFormTfStep.requestFocus();
        saveFormTfStep.setBounds(step_lb.getBounds().x + 203 - 35, step_lb.getBounds().y, 100, 35);
        saveFormTfStep.setCaretColor(Color.BLACK);
        //saveFormTfStep.setOpaque(false);
        saveFormTfStep.setHorizontalAlignment(JTextField.CENTER);
        saveFormTfStep.setFont(inputFont);
        saveFormTfStep.setDocument(new LimitDocument(3, true));
        save_form.add(saveFormTfStep);

        JLabel step_tf_back = new JLabel();
        step_tf_back.setBounds(saveFormTfStep.getBounds().x - 7, saveFormTfStep.getBounds().y - 3,
                saveFormTfStep.getBounds().width + 15, saveFormTfStep.getBounds().height + 5);
        save_form.add(step_tf_back);

        //Time
        JLabel time_lb = new JLabel("Thời Gian (s): ");
        time_lb.setBounds(20 + xOff, 173 + yOff, 150, 35);
        time_lb.setFont(inputFont);
        save_form.add(time_lb);

        saveFormTfTime = new JTextField();
        saveFormTfTime.requestFocus();
        saveFormTfTime.setBounds(time_lb.getBounds().x + 203 - 35, time_lb.getBounds().y, 100, 35);
        saveFormTfTime.setCaretColor(Color.BLACK);
        //saveFormTfTime.setOpaque(false);
        saveFormTfTime.setHorizontalAlignment(JTextField.CENTER);
        saveFormTfTime.setFont(inputFont);
        saveFormTfTime.setDocument(new LimitDocument(3, true));
        save_form.add(saveFormTfTime);

        JLabel time_tf_back = new JLabel();
        time_tf_back.setBounds(saveFormTfTime.getBounds().x - 7, saveFormTfTime.getBounds().y - 3, saveFormTfTime.getBounds().width + 15, saveFormTfTime.getBounds().height + 5);
        save_form.add(time_tf_back);

        //Err
        saveFormLbErr = new JLabel();
        saveFormLbErr.setBounds(30, 220, 240, 20);
        saveFormLbErr.setForeground(Color.red);
        saveFormLbErr.setHorizontalAlignment(JLabel.CENTER);
        save_form.add(saveFormLbErr);

        //Btn Ok
        btnOk = new JButton("Ok");
        btnOk.setFont(new Font("MercuriusScript", Font.BOLD, 12));
        btnOk.setBounds(80 - 10, 255, 65, 65);
        btnOk.addActionListener(this);
        save_form.add(btnOk);

        //Btn Cancel
        btnCancel = new JButton("No");
        btnCancel.setFont(new Font("MercuriusScript", Font.BOLD, 12));
        btnCancel.setBounds(155 + 10, 255, 65, 65);
        btnCancel.addActionListener(this);
        save_form.add(btnCancel);

        //back form
        JLabel savef_back = new JLabel();
        savef_back.setBounds(0, 0, 300, 330);
        savef_back.setBackground(Color.ORANGE);
        save_form.add(savef_back);

        //glass
        JLabel glass = new JLabel();
        glass.setBounds(0, 0, 800, 600);
        saveP.add(glass);

        this.setVisible(false);
    }

    public void newLevel() {
        mode = 0;
        pipeP.reset();
        pipeP.setPipeCell(0, 0, 0, 0);
        lbLevelName.setText("New");
    }

    public void editLevel(GLevel level) {
        mode = 1;
        pipeP.reset();
        editL = level;
        lbLevelName.setText(level.getSName());
        try {
            BufferedReader br = new BufferedReader(new FileReader(level.getPath()));
            saveFormTfName.setText(level.getSName());
            saveFormTfTime.setText(br.readLine());
            saveFormTfStep.setText(br.readLine());
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    String item[] = br.readLine().split(" ");
                    pipeP.setPipeCell(i, j, Integer.parseInt(item[0]), Integer.parseInt(item[1]));
                }
            }
        } catch (IOException | NullPointerException ex) {
        };
    }

    public JButton getBtnHome() {
        return btnHome;
    }

    private void setPipeBorder(PipeCell pipeT) {
        pipeN.setBorder(BorderFactory.createEmptyBorder());
        pipe1.setBorder(BorderFactory.createEmptyBorder());
        pipe2.setBorder(BorderFactory.createEmptyBorder());
        pipe3.setBorder(BorderFactory.createEmptyBorder());
        pipe4.setBorder(BorderFactory.createEmptyBorder());
        pipe5.setBorder(BorderFactory.createEmptyBorder());
        pipeT.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
    }

    @Override
    public void mousePressed(MouseEvent me) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (me.getSource() == pipeP.getPipeCell(i, j)) {
                    if (me.getButton() == MouseEvent.BUTTON1) { //chuot Trai
                        if (i + j != 0) {
                            pipeP.getPipeCell(i, j).setPipe(curPipe.getType(), curPipe.getRotStt());
                        }
                    } else if (me.getButton() == MouseEvent.BUTTON3) { //chuot Phai
                        if (pipeP.getPipeCell(i, j).getType() != -1) {
                            pipeP.getPipeCell(i, j).rotatePipe(1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void actionPerformed(ActionEvent me) {
        if (me.getSource() == pipeN) {
            curPipe = pipeN;
            setPipeBorder(pipeN);
        }
        if (me.getSource() == pipe1) {
            curPipe = pipe1;
            setPipeBorder(pipe1);
        }
        if (me.getSource() == pipe2) {
            curPipe = pipe2;
            setPipeBorder(pipe2);
        }
        if (me.getSource() == pipe3) {
            curPipe = pipe3;
            setPipeBorder(pipe3);
        }
        if (me.getSource() == pipe4) {
            curPipe = pipe4;
            setPipeBorder(pipe4);
        }
        if (me.getSource() == pipe5) {
            curPipe = pipe5;
            setPipeBorder(pipe5);
        }

        if (me.getSource() == btnSave) {
            //reset form
            if (mode == 0) {
                saveFormLbErr.setText("");
                saveFormTfName.setText("");
                saveFormTfStep.setText("");
                saveFormTfTime.setText("");
            }
            this.setLayer(mainP, 0);
            this.setLayer(saveP, 1);
        }

        if (me.getSource() == btnOk) {
            saveFormLbErr.setText("");
            if (!saveFormTfName.getText().equals("") && !saveFormTfStep.getText().equals("") && !saveFormTfTime.getText().equals("")) {
                String path = "src\\map\\" + "us" + saveFormTfName.getText() + ".txt";
                //Kiem tra File ton tai
                File flevel = new File(path);
                GLevel newL = new GLevel(path, 0, 0, Integer.parseInt(saveFormTfTime.getText()), Integer.parseInt(saveFormTfStep.getText()), false);

                if (flevel.exists() == true) {
                    if (mode == 1 && editL.getPath().equals(path)) {          //Cap nhat file
                        LevelDB.removeInLevelFile(editL);
                        newL.writeToFile(pipeP.toArray());
                        LevelDB.insertInLevelFile(newL, false);
                        quest.showQuest("Cập nhật thành công!!", 18);
                        lbLevelName.setText(newL.getSName());
                    } else {
                        quest.showQuest("Level đã tồn tại<br>Bạn có muốn ghi đè?", 25);
                    }
                } else {
                    if (mode == 1) {
                        LevelDB.removeInLevelFile(editL);
                        newL.writeToFile(pipeP.toArray());
                        LevelDB.insertInLevelFile(newL, false);
                        this.setLayer(mainP, 1);
                        this.setLayer(saveP, 0);
                    } else {
                        newL.writeToFile(pipeP.toArray());
                        LevelDB.insertInLevelFile(newL, false);
                        editL = newL;
                        mode = 1;
                        quest.showQuest("Tạo mới thành công!!", 18);
                        this.setLayer(mainP, 1);
                        this.setLayer(saveP, 0);
                    }
                    lbLevelName.setText(newL.getSName());
                }
            } else {
                saveFormLbErr.setText("Vui lòng nhập đầy đủ thông tin");
            }
        }
        if (me.getSource() == btnCancel) {
            this.setLayer(mainP, 1);
            this.setLayer(saveP, 0);
        }
        if (me.getSource() == quest.getBtnNo()) {
            quest.hideQuest();
        }
        if (me.getSource() == quest.getBtnYes()) {
            String path = "src\\map\\" + "us" + saveFormTfName.getText() + ".txt";
            GLevel newL = new GLevel(path, 0, 0, Integer.parseInt(saveFormTfTime.getText()), Integer.parseInt(saveFormTfStep.getText()), false);
            newL.writeToFile(pipeP.toArray());
            LevelDB.insertInLevelFile(newL, true);
            editL = newL;
            mode = 1;
            lbLevelName.setText(newL.getSName());
            quest.hideQuest();
            this.setLayer(mainP, 1);
            this.setLayer(saveP, 0);
        }
    }

    class LimitDocument extends PlainDocument {

        private int value;
        private boolean oDigits;

        public LimitDocument(int value, boolean oDigits) {
            super();
            this.value = value;
            this.oDigits = oDigits;
        }

        public void insertString(int offset, String str, AttributeSet attr) {
            if (str == null) {
                return;
            }
            boolean limit_stt = false, num_stt = true;
            char[] conv;
            if ((getLength() + str.length()) <= value) {
                limit_stt = true;
            }

            conv = str.toCharArray();
            for (int i = 0; i < conv.length; i++) {
                if (conv[i] < '0' || conv[i] > '9') {
                    num_stt = false;
                }
            }

            if (oDigits == false && limit_stt == true) {
                try {
                    super.insertString(offset, str, attr);
                } catch (BadLocationException ex) {
                }
            }
            if (oDigits == true && limit_stt == true && num_stt == true) {
                try {
                    super.insertString(offset, new String(conv), attr);
                } catch (BadLocationException ex) {
                }
            }
        }
    }
}

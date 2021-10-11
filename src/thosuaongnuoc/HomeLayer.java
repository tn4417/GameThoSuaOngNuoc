package thosuaongnuoc;

import java.awt.Font;
import javax.swing.*;

public class HomeLayer extends JLayeredPane {

    private final JButton btnStart, btnQuit;
    private final JPanel pnInfo;
    private final Font mFont = new Font("MercuriusScript", Font.BOLD, 32);

    public HomeLayer() {
        this.setName("Home");
        setLayout(null);

        //Play
        btnStart = new JButton();
        btnStart.setBounds(320, 240, 160, 65);
        btnStart.setText("Play");
        btnStart.setFont(mFont);
        add(btnStart);

        //thoat
        btnQuit = new JButton();
        btnQuit.setBounds(320, 470, 160, 65);
        btnQuit.setText("Thoát");
        btnQuit.setFont(mFont);
        add(btnQuit);

        //Info panel
        pnInfo = new JPanel();
        pnInfo.setLayout(null);
        pnInfo.setOpaque(false);
        JLabel title = new JLabel("Báo Cáo Đề Tài");
        title.setBounds(50, 10, 200, 30);
        pnInfo.add(title);
        JLabel gameID = new JLabel("Game: Thợ Sửa Ống Nước");
        gameID.setBounds(15, 30, 200, 30);
        pnInfo.add(gameID);
        JLabel listGr = new JLabel("Danh sách thành viên nhóm:");
        listGr.setBounds(15, 70, 200, 30);
        pnInfo.add(listGr);
        JLabel back_gnd = new JLabel();
        back_gnd.setBounds(0, 0, 200, 200);
        pnInfo.add(back_gnd);
        pnInfo.setBounds(10, 10, 200, 200);
        add(pnInfo);
        pnInfo.setVisible(true);
        setVisible(false);

        //Ai_mem();
        Java_mem();
    }

    //Ai
    public void Ai_mem() {
        JLabel sv1 = new JLabel("Phạm Nguyễn Ngọc Hoài");
        sv1.setBounds(15, 90, 200, 30);
        pnInfo.add(sv1);
        JLabel sv2 = new JLabel("Nguyễn Thị Mỹ Hạnh");
        sv2.setBounds(15, 110, 200, 30);
        pnInfo.add(sv2);
        JLabel sv3 = new JLabel("Vũ Tuấn Anh");
        sv3.setBounds(15, 130, 200, 30);
        pnInfo.add(sv3);
        JLabel sv4 = new JLabel("Nguyễn Trung Nguyên");
        sv4.setBounds(15, 150, 200, 30);
        pnInfo.add(sv4);
    }

    //Java
    public void Java_mem() {
        JLabel sv5 = new JLabel("Nguyễn Trung Nguyên");
        sv5.setBounds(15, 90, 200, 30);
        pnInfo.add(sv5);
        JLabel sv6 = new JLabel("Lê Công Danh");
        sv6.setBounds(15, 110, 200, 30);
        pnInfo.add(sv6);
        JLabel sv7 = new JLabel("Nguyễn Minh Thuận");
        sv7.setBounds(15, 130, 200, 30);
        pnInfo.add(sv7);
    }

    public JButton getBtnStart() {
        return btnStart;
    }

    public JButton getBtnQuit() {
        return btnQuit;
    }
}

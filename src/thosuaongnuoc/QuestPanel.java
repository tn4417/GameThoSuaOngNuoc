package thosuaongnuoc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import javax.swing.*;

public class QuestPanel extends JPanel {

    private final JButton btnYes;
    private final JButton btnNo;
    private final JLabel form_text;
    private final Font mFont = new Font("MercuriusScript", Font.BOLD, 25);

    public QuestPanel() {
        setLayout(null);
        //setOpaque(false);
        setBounds(0, 0, 800, 600);

        JPanel form = new JPanel();
        form.setBounds(400 - 190, 300 - 90, 380, 220);
        form.setLayout(null);
        //form.setOpaque(false);
        add(form);

        JLabel form_title = new JLabel("Thông Báo!!");
        form_title.setBounds(380 / 2 - 90, 10, 180, 40);
        form_title.setFont(mFont);
        form_title.setForeground(Color.BLACK);
        form_title.setVerticalTextPosition(JLabel.CENTER);
        form_title.setHorizontalTextPosition(JLabel.CENTER);
        form.add(form_title);

        form_text = new JLabel();
        form_text.setBounds(10, 60, 360, 80);
        form.add(form_text);

        btnYes = new JButton("Yes");
        btnYes.setFont(new Font("MercuriusScript", Font.BOLD, 12));
        btnYes.setBounds(380 / 2 - 60, 110 + 50, 60, 60);
        //btnYes.setLocation(380 / 2 - 60, 110 + 50);
        form.add(btnYes);

        btnNo = new JButton("No");
        btnNo.setFont(new Font("MercuriusScript", Font.BOLD, 12));
        btnNo.setBounds(380 / 2 + 10, 110 + 50, 60, 60);
        form.add(btnNo);

        JLabel form_back = new JLabel();
        form_back.setBounds(0, 0, 380, 220);
        form.add(form_back);

        JLabel glass = new JLabel();
        glass.setBounds(0, 0, 800, 600);
        glass.addMouseListener(new MouseAdapter() {
        });
        add(glass);
        setVisible(false);
    }

    public void showQuest(String content, int fontS) {
        form_text.setText(String.format("<html><div WIDTH=360 style='text-align: center;'>%s</div></html>", content));
        form_text.setFont(mFont.deriveFont(Font.PLAIN, fontS));
        setVisible(true);
    }

    public void hideQuest() {
        setVisible(false);
    }

    public JButton getBtnYes() {
        return btnYes;
    }

    public JButton getBtnNo() {
        return btnNo;
    }
}

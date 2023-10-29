package Text;

import javax.swing.*;

public class AboutBook extends JFrame{
    public AboutBook(){
        super("关于");
        this.setSize(220, 100);
        this.setLocation(200,300);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("作者：Mirror New");

        panel.add(label);
        this.add(panel);
    }
}
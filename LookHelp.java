package Text;

import javax.swing.*;

import java.awt.*;

public class LookHelp extends JFrame{

    public LookHelp(){
        super("查看帮助");
        this.setSize(500, 200);
        this.setLocation(200,300);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        Container c = this.getContentPane();
        c.setLayout(new GridLayout(5,0));

        JLabel label1 = new JLabel("1、“编辑”中剪切、复制、粘贴可用快捷键Ctrl+X、Ctrl+C、Ctrl+V实现。");
        JLabel label2 = new JLabel("2、“格式”中“自动换行”需要保持被选中才可进行自动换行。");
        JLabel label3 = new JLabel("3、无法改变字体的样式，大小和形态。");

        c.add(label1);c.add(label2);c.add(label3);
    }
}
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TextFrame extends JFrame {

    public TextFrame() {
        super("记事本");

        // 创建菜单栏(JMenuBar)对象
        JMenuBar mBar = new JMenuBar();
        // 在 JFrame 等容器中设置菜单栏对象，即将菜单栏添加到框架容器中
        this.setJMenuBar(mBar);

        // 创建菜单对象
        JMenu file = new JMenu("文件");
        JMenu edit = new JMenu("编辑");
        JMenu form = new JMenu("格式");
        JMenu help = new JMenu("帮助");
        JMenu font = new JMenu("字体");

        // 将菜单添加到菜单栏中
        mBar.add(file);
        mBar.add(edit);
        mBar.add(form);
        mBar.add(help);
        mBar.add(font);

        JTextArea workArea = new JTextArea();
        JScrollPane imgScrollPane = new JScrollPane(workArea);
        // add(workArea);
        add(imgScrollPane, BorderLayout.CENTER);

        // 定义打开和保存对话框
        FileDialog openDia;
        FileDialog saveDia;
        // 默认模式为 FileDialog.LOAD
        openDia = new FileDialog(this, "打开", FileDialog.LOAD);
        saveDia = new FileDialog(this, "另存为", FileDialog.SAVE);

        JMenuItem item1_1 = new JMenuItem("新建");
        item1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workArea.setText("");// 清空文本
            }
        });
        JMenuItem item1_2 = new JMenuItem("打开");
        item1_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDia.setVisible(true);

                String dirPath = openDia.getDirectory();// 获取文件路径
                String fileName = openDia.getFile();// 获取文件名称

                // 如果打开路径 或 目录为空 则返回空
                if (dirPath == null || fileName == null) {
                    return;
                }

                workArea.setText("");// 清空文本

                File fileO = new File(dirPath, fileName);

                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(fileO));

                    String line = null;

                    while ((line = bufr.readLine())!= null) {
                        workArea.append(line + "\r\n");
                    }

                    bufr.close(); // 关闭文本
                } catch (IOException er1) {
                    throw new RuntimeException("文件读取失败！");
                }

            }
        });
        JMenuItem item1_3 = new JMenuItem("保存");
        item1_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File fileS = null;
                if (fileS == null) {
                    saveDia.setVisible(true);
                    String dirPath = saveDia.getDirectory();
                    String fileName = saveDia.getFile();

                    if (dirPath == null || fileName == null)
                        return;

                    fileS = new File(dirPath, fileName);
                }

                try {
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));
                    String text = workArea.getText();
                    bufw.write(text);
                    bufw.close();
                } catch (IOException er) {
                    throw new RuntimeException("文件保存失败！");
                }
            }
        });
        JMenuItem item1_4 = new JMenuItem("另存为");
        item1_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File fileS = null;
                if (fileS == null) {
                    saveDia.setVisible(true);
                    String dirPath = saveDia.getDirectory();
                    String fileName = saveDia.getFile();

                    if (dirPath == null || fileName == null)
                        return;

                    fileS = new File(dirPath, fileName);
                }

                try {
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));
                    String text = workArea.getText();
                    bufw.write(text);
                    bufw.close();
                } catch (IOException er) {
                    throw new RuntimeException("文件保存失败！");
                }
            }
        });
        JMenuItem item1_5 = new JMenuItem("退出");
        item1_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenuItem item2_1 = new JMenuItem("剪切");
        JMenuItem item2_2 = new JMenuItem("复制");
        JMenuItem item2_3 = new JMenuItem("粘贴");

        JRadioButtonMenuItem item3_1 = new JRadioButtonMenuItem("自动换行", false);
        item3_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();

                if (source == item3_1)
                    workArea.setLineWrap(true);    // 自动换行
                else if (source!= item3_1)
                    workArea.setLineWrap(false);
            }
        });

        JMenuItem item4_1 = new JMenuItem("查看帮助");
        item4_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new LookHelp();
            }
        });
        JMenuItem item4_2 = new JMenuItem("关于");
        item4_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new AboutBook();
            }
        });

        // 添加更改字体大小菜单项
        JMenuItem changeFontSizeItem = new JMenuItem("更改字体大小");
        changeFontSizeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(TextFrame.this, "请输入字体大小：");
                if (input!= null &&!input.isEmpty()) {
                    try {
                        int size = Integer.parseInt(input);
                        Font currentFont = workArea.getFont();
                        workArea.setFont(new Font(currentFont.getName(), currentFont.getStyle(), size));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(TextFrame.this, "输入的不是有效的数字。", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // 添加更改字体颜色菜单项
        JMenuItem changeFontColorItem = new JMenuItem("更改字体颜色");
        changeFontColorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(TextFrame.this, "选择字体颜色", workArea.getForeground());
                if (newColor!= null) {
                    workArea.setForeground(newColor);
                }
            }
        });

        // 在菜单中添加菜单项
        file.add(item1_1);
        file.add(item1_2);
        file.add(item1_3);
        file.add(item1_4);
        file.add(item1_5);
        edit.add(item2_1);
        edit.add(item2_2);
        edit.add(item2_3);
        form.add(item3_1);
        help.add(item4_1);
        help.add(item4_2);
        font.add(changeFontSizeItem);
        font.add(changeFontColorItem);

    }// 构造方法结束

    public static void main(String args[]) {
        TextFrame app = new TextFrame();

        app.setSize(600, 400);
        app.setLocation(200, 200);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
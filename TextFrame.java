package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import javax.swing.undo.*;

public class TextFrame extends JFrame {

    private JTextArea workArea;
    private UndoManager undoManager = new UndoManager(); // 添加撤销/重做功能

    public TextFrame() {
        super("记事本");

        // 创建菜单栏(JMenuBar)对象
        JMenuBar mBar = new JMenuBar();
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

        // 初始化文本区域并添加到滚动面板
        workArea = new JTextArea();
        JScrollPane imgScrollPane = new JScrollPane(workArea);
        add(imgScrollPane, BorderLayout.CENTER);
        workArea.getDocument().addUndoableEditListener(undoManager); // 启用撤销管理

        // 定义打开和保存对话框
        FileDialog openDia = new FileDialog(this, "打开", FileDialog.LOAD);
        FileDialog saveDia = new FileDialog(this, "另存为", FileDialog.SAVE);

        // 新建功能
        JMenuItem item1_1 = new JMenuItem("新建");
        item1_1.addActionListener(e -> workArea.setText(""));

        // 打开文件
        JMenuItem item1_2 = new JMenuItem("打开");
        item1_2.addActionListener(e -> {
            openDia.setVisible(true);
            String dirPath = openDia.getDirectory();
            String fileName = openDia.getFile();
            if (dirPath == null || fileName == null) return;

            workArea.setText("");
            try (BufferedReader bufr = new BufferedReader(new FileReader(new File(dirPath, fileName)))) {
                String line;
                while ((line = bufr.readLine()) != null) workArea.append(line + "\r\n");
            } catch (IOException er) {
                JOptionPane.showMessageDialog(this, "文件读取失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 保存文件
        JMenuItem item1_3 = new JMenuItem("保存");
        item1_3.addActionListener(e -> saveFile(saveDia));

        // 另存为
        JMenuItem item1_4 = new JMenuItem("另存为");
        item1_4.addActionListener(e -> saveFile(saveDia));

        // 退出功能
        JMenuItem item1_5 = new JMenuItem("退出");
        item1_5.addActionListener(e -> System.exit(0));

        // 剪切、复制、粘贴
        JMenuItem item2_1 = new JMenuItem("剪切");
        item2_1.addActionListener(e -> workArea.cut());
        JMenuItem item2_2 = new JMenuItem("复制");
        item2_2.addActionListener(e -> workArea.copy());
        JMenuItem item2_3 = new JMenuItem("粘贴");
        item2_3.addActionListener(e -> workArea.paste());

        // 撤销、重做功能
        JMenuItem undoItem = new JMenuItem("撤销");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undoItem.addActionListener(e -> {
            if (undoManager.canUndo()) undoManager.undo();
        });

        JMenuItem redoItem = new JMenuItem("重做");
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        redoItem.addActionListener(e -> {
            if (undoManager.canRedo()) undoManager.redo();
        });

        // 自动换行功能
        JRadioButtonMenuItem item3_1 = new JRadioButtonMenuItem("自动换行", false);
        item3_1.addActionListener(e -> workArea.setLineWrap(item3_1.isSelected()));

        // 查找文本
        JMenuItem findItem = new JMenuItem("查找");
        findItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        findItem.addActionListener(e -> {
            String searchTerm = JOptionPane.showInputDialog(this, "输入要查找的文本：");
            if (searchTerm != null && !searchTerm.isEmpty()) {
                String content = workArea.getText();
                int index = content.indexOf(searchTerm);
                if (index >= 0) {
                    workArea.setCaretPosition(index);
                    workArea.select(index, index + searchTerm.length());
                } else {
                    JOptionPane.showMessageDialog(this, "未找到匹配内容");
                }
            }
        });

        // 更改字体大小
        JMenuItem changeFontSizeItem = new JMenuItem("更改字体大小");
        changeFontSizeItem.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "请输入字体大小：");
            if (input != null && !input.isEmpty()) {
                try {
                    int size = Integer.parseInt(input);
                    Font currentFont = workArea.getFont();
                    workArea.setFont(new Font(currentFont.getName(), currentFont.getStyle(), size));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "输入的不是有效的数字。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 更改字体颜色
        JMenuItem changeFontColorItem = new JMenuItem("更改字体颜色");
        changeFontColorItem.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "选择字体颜色", workArea.getForeground());
            if (newColor != null) workArea.setForeground(newColor);
        });

        // 添加菜单项
        file.add(item1_1);
        file.add(item1_2);
        file.add(item1_3);
        file.add(item1_4);
        file.add(item1_5);
        edit.add(item2_1);
        edit.add(item2_2);
        edit.add(item2_3);
        edit.add(undoItem);
        edit.add(redoItem);
        edit.add(findItem);
        form.add(item3_1);
        help.add(new JMenuItem("查看帮助"));
        help.add(new JMenuItem("关于"));
        font.add(changeFontSizeItem);
        font.add(changeFontColorItem);
    }

    private void saveFile(FileDialog saveDia) {
        saveDia.setVisible(true);
        String dirPath = saveDia.getDirectory();
        String fileName = saveDia.getFile();
        if (dirPath == null || fileName == null) return;

        try (BufferedWriter bufw = new BufferedWriter(new FileWriter(new File(dirPath, fileName)))) {
            bufw.write(workArea.getText());
        } catch (IOException er) {
            JOptionPane.showMessageDialog(this, "文件保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        TextFrame app = new TextFrame();
        app.setSize(600, 400);
        app.setLocation(200, 200);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}

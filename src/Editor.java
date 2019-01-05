/**
 * @Author: 吴硕涵
 * @Date: 2019/1/5 12:57 PM
 * @Version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

//简单的文本编辑器

public class Editor extends JFrame {
    public JTextPane textPane = new JTextPane(); //文本窗格d，编辑窗口
    public JFileChooser filechooser = new JFileChooser(); //文件选择器

    public Editor() {
        super("简记");

        Action[] actions =            //菜单项的各种功能
                {
                        new NewAction(),
                        new OpenAction(),
                        new SaveAction(),
                        new CutAction(),
                        new CopyAction(),
                        new PasteAction(),
                        new AboutAction(),
                        new ExitAction(),
                        new HelpAction()
                };
        setJMenuBar(createJMenuBar(actions));        //根据actions创建菜单栏
        Container container = getContentPane();
        container.add(textPane, BorderLayout.CENTER);

        setSize(1300, 1500);
        setVisible(true);
        //	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JMenuBar createJMenuBar(Action[] actions)    //创建菜单栏的函数
    {
        JMenuBar menubar = new JMenuBar();
        JMenu menuFile = new JMenu("文件(F)");
        JMenu menuEdit = new JMenu("编辑(E)");
        JMenu menuAbout = new JMenu("帮助(H)");
        menuFile.add(new JMenuItem(actions[0]));
        menuFile.add(new JMenuItem(actions[1]));
        menuFile.add(new JMenuItem(actions[2]));
        menuFile.add(new JMenuItem(actions[7]));
        menuEdit.add(new JMenuItem(actions[3]));
        menuEdit.add(new JMenuItem(actions[4]));
        menuEdit.add(new JMenuItem(actions[5]));
        menuAbout.add(new JMenuItem(actions[6]));
        menuAbout.add(new JMenuItem(actions[8]));
        menubar.add(menuFile);
        menubar.add(menuEdit);
        menubar.add(menuAbout);
        return menubar;
    }

    class NewAction extends AbstractAction        //新建
    {
        public NewAction() {
            super("新建(N)     Ctrl+N");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textPane.setDocument(new DefaultStyledDocument());
        }
    }

    class OpenAction extends AbstractAction        //打开
    {
        public OpenAction() {
            super("打开(O)     Ctrl+O");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = filechooser.showOpenDialog(Editor.this);            //显示打开文件对话框
            if (i == JFileChooser.APPROVE_OPTION)            //点击对话框打开选项
            {
                File f = filechooser.getSelectedFile();    //得到选择的文件
                try {
                    InputStream is = new FileInputStream(f);
                    textPane.read(is, "d");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class SaveAction extends AbstractAction        //保存
    {
        public SaveAction() {
            super("保存(S)     Ctrl+S");
        }

        public void actionPerformed(ActionEvent e) {
            int i = filechooser.showSaveDialog(Editor.this);
            if (i == JFileChooser.APPROVE_OPTION) {
                File f = filechooser.getSelectedFile();
                try {
                    FileOutputStream out = new FileOutputStream(f);
                    out.write(textPane.getText().getBytes());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class ExitAction extends AbstractAction        //退出
    {
        public ExitAction() {
            super("退出(X)");
        }

        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    class CutAction extends AbstractAction        //剪切
    {
        public CutAction() {
            super("剪切(T)     Ctrl+X");
        }

        public void actionPerformed(ActionEvent e) {
            textPane.cut();
        }
    }

    class CopyAction extends AbstractAction        //复制
    {
        public CopyAction() {
            super("复制(C)     Ctrl+C");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textPane.copy();
        }
    }

    class PasteAction extends AbstractAction        //粘贴
    {
        public PasteAction() {
            super("粘贴(P)     Ctrl+V");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textPane.paste();
        }
    }

    class AboutAction extends AbstractAction {
        public AboutAction() {
            super("关于简记(A)");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Editor.this, "实现了记事本的一些基本功能", "关于", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class HelpAction extends AbstractAction {
        public HelpAction() {
            super("联系开发者");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(Editor.this, "cpeng2424@gmail.com", "开发者邮箱", JOptionPane.PLAIN_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Editor();
    }
}

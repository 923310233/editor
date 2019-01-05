/**
 * @Author: 吴硕涵
 * @Date: 2019/1/5 1:16 PM
 * @Version 1.0
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class FileManagement extends JFrame implements ActionListener{

    private JMenuBar menuBar;
    //菜单栏
    private JMenu menu_File,menu_Edit,menu_Help,menu_Format;
    //菜单栏内的菜单
    private JMenuItem item_new,item_open,item_save,item_exit;
    //对于file菜单的子项
    private JMenuItem item_undo,item_cut,item_copy,item_stick,item_delete;
    //对于edit菜单的子项
    private JMenuItem item_about;
    //对于help菜单的子项
    private JMenuItem item_word_format;

    private static JTextArea edit_text_area;
    //private JTextArea edit_text_area;

    //编辑区域
    private JScrollPane scroll_bar;
    //可滚动的pane 里面添加edit_text_area就可以变为一个可以滚动的文本框，JScrollPane是一个pane，同时可以设置方向

    private JFileChooser fileChooser = null;
    //JFileChooser组件  响应时间弹出对话框

    public static void main(String[] args) {
        FileManagement m1 = new FileManagement();
    }

    public static JTextArea getEdit_text_area() {
        //public JTextArea getEdit_text_area() {
        return edit_text_area;
    }


    public FileManagement(){
        initMenuBar();
        initEditArea();
        initListener();




        this.setJMenuBar(menuBar);
        this.setSize(800,600);
        this.add(scroll_bar);
        this.setTitle("自定义文本编辑器");
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 初始化编辑区域
     * 用scrollpane装textarea
     * 同时对pane设置方向
     */
    public void initEditArea() {
        edit_text_area = new JTextArea();
        scroll_bar = new JScrollPane(edit_text_area);
        scroll_bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * 初始化菜单栏，对菜单栏添加子菜单menu
     * 同时可以给菜单添加二级菜单、菜单项
     */
    public void initMenuBar() {
        menuBar = new JMenuBar();
        menu_File = new JMenu("文件(F)");
        menu_File.setMnemonic('f');//f+alt打开
        item_new = new JMenuItem("新建");
        item_open = new JMenuItem("打开");
        item_save = new JMenuItem("保存");
        item_exit = new JMenuItem("退出");
        menu_File.add(item_new);
        menu_File.add(item_open);
        menu_File.add(item_save);
        menu_File.add(item_exit);
        //File 菜单

        menu_Edit = new JMenu("编辑(E)");
        menu_Edit.setMnemonic('e');
        item_undo = new JMenuItem("撤销");
        item_cut = new JMenuItem("剪切");
        item_copy = new JMenuItem("复制");
        item_stick = new JMenuItem("粘贴");
        item_delete = new JMenuItem("删除");
        menu_Edit.add(item_undo);
        menu_Edit.add(item_cut);
        menu_Edit.add(item_copy);
        menu_Edit.add(item_stick);
        menu_Edit.add(item_delete);
        //Edit 菜单

        menu_Help = new JMenu("帮助(H)");
        menu_Help.setMnemonic('h');
        item_about = new JMenuItem("关于");
        menu_Help.add(item_about);
        //Help 菜单

        menu_Format = new JMenu("格式(O)");
        menu_Format.setMnemonic('o');
        item_word_format = new JMenuItem("字体(F)");
        item_word_format.setAccelerator(KeyStroke.getKeyStroke('F',java.awt.Event.CTRL_MASK,false));//给item添加快捷键
        menu_Format.add(item_word_format);


        menuBar.add(menu_File);
        menuBar.add(menu_Edit);
        menuBar.add(menu_Format);
        menuBar.add(menu_Help);
    }

    /**
     * 对所有btn跟item统一设置监听器
     */
    public void initListener() {
        item_new.addActionListener(this);
        item_open.addActionListener(this);
        item_save.addActionListener(this);
        item_exit.addActionListener(this);
        item_undo.addActionListener(this);
        item_cut.addActionListener(this);
        item_copy.addActionListener(this);
        item_stick.addActionListener(this);
        item_delete.addActionListener(this);
        item_word_format.addActionListener(this);
        item_about.addActionListener(this);
    }

    /**
     * 对所有menu里所有item的事件监听器
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == item_about) {
            new About_Window();
        }else if (e.getSource() == item_word_format) {
            About_Format newFormat = new About_Format();
        }else if (e.getSource() == item_new) {
            new FileManagement(); // 选中新建 new一个新窗口 ，有bug，关闭任意子窗口父窗口也会跟着关闭
        }else if (e.getSource() == item_exit) {
            this.dispose();
        }else if (e.getSource() == item_open) {
            openFile();
        }else if (e.getSource() == item_save) {
            saveFile();
        }
    }

    private void saveFile() {
        File file = null;
        int result ;
        fileChooser = new JFileChooser("C:\\");
        fileChooser.setApproveButtonToolTipText("保存"); // 设置确认按钮的现实文本
        fileChooser.setDialogTitle("保存文件"); // 设置title
        result = fileChooser.showOpenDialog(rootPane); // 设置Dialog的根View 根布局

        //--------------------------------------------------------------------------
        if(result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
        }

        //--------------------------------------------------------------------------
		/*FileOutputStream fileOutputStream = null; // 文件io类
		if (file != null) {
			try {
				fileOutputStream = new FileOutputStream(file);
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			String content = edit_text_area.getText();

			try {
				fileOutputStream.write(content.getBytes());
			}catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if (fileOutputStream!=null) {
						fileOutputStream.close();
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/
        //---------------这里有严重bug，对于字符写入文件没问题，但是在读取中文字符的时候会出现乱码-----------
        //--------------------------------------------------------------------------

        try{
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); // 对字符进行编码转换
            BufferedWriter writer = new BufferedWriter(write);
            String content = edit_text_area.getText();
            writer.write(content);
            writer.close();
        }catch(IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 点击新建按item时 打开JFileChooser对话框
     * 并且对文件读取进行处理
     */
    private void openFile() {
        File file = null;
        int result ;
        fileChooser = new JFileChooser("C:\\");
        fileChooser.setApproveButtonToolTipText("确定"); // 设置确认按钮的现实文本
        fileChooser.setDialogTitle("打开文件"); // 设置title
        result = fileChooser.showOpenDialog(rootPane); // 设置Dialog的根View 根布局

        //--------------------------------------------------------------------------
        if(result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
        }

        //--------------------------------------------------------------------------
        //--------------------下面对文件进行处理，把内容装到父窗体的textarea中--------------------
		/*FileInputStream fileInputStream = null;
		if (file != null) {
			try { //此处需要注意空指针异常 即没有找到文件的时候需要处理
				fileInputStream = new FileInputStream(file); // 将file文件的数据流装到fileInputStream里
			}catch (FileNotFoundException e) {  // 捕获到异常 ，需要处理
				e.printStackTrace(); // 将异常实例化为e 然后在控制台Console 打印出错误的位置和原因
				TipDialog tmpDialog = new TipDialog(this,"错误文件",true,"文件夹名称错误，请重新检查!");// 此处我们还可以对一场做一些处理，在这里弹出一个警示对话框

			}

			//读取文件
			int readbyte ;
			try {
				while ((readbyte = fileInputStream.read())!=-1) { //一段段的读取文件
					edit_text_area.append(String.valueOf((char)readbyte)); //在editarea 里一行行添加
				}
			}catch (IOException e) { // 处理异常
				e.printStackTrace();
			}finally {
				try {
					if (fileInputStream != null) { //对fileInputStream 回收
						fileInputStream.close();
					}
				}catch (IOException e) { //抛出异常
					e.printStackTrace();
				}
			}
		}*/
        //---------------这里有严重bug，对于读取中文字符会出现乱码-------------------------------
        //--------------------------------------------------------------------------

        if(file.isFile() && file.exists()) {
            BufferedReader reader = null;
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
                reader = new BufferedReader(inputStreamReader);

                String readLine = "";
                while ((readLine = reader.readLine()) != null) { // 对BufferedReader数据一行行读
                    //edit_text_area.append(readLine); 这样写会出现所有的句子都出现在同一行的情况，所以在每次append的时候在后面加一个换行符
                    edit_text_area.append(readLine+'\n');  //对edit_text_area 一行行加
                }

                reader.close(); // 关闭reader

            }catch (IOException e) {
                e.printStackTrace();
                TipDialog tmpDialog = new TipDialog(this,"错误文件",true,"文件夹名称错误，请重新检查!");
            }


        }
    }

    /**
     * 写一个内部类，用来new一个自定义的Dialog
     * 内部类的特点是只能供内部类的外部类使用，其他类无法使用
     * 相当于高级定制
     * 适合代码较短，完成功能较简单的情况
     * @author Administrator
     *
     */
    class TipDialog extends JDialog{

        public TipDialog (JFrame jf,String title ,boolean flag ,String info) {
            super(jf,title,flag);
            JLabel Jlb = new JLabel(info);
            this.add(Jlb);
            this.setSize(200, 150);
            this.setLocationRelativeTo(null);
            this.setVisible(true);
            this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        }

    }


}

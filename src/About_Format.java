/**
 * @Author: 吴硕涵
 * @Date: 2019/1/5 1:18 PM
 * @Version 1.0
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 其实这个窗口可以从一个根类写起
 * 这样一个组件一个组件的写起来对编程难度低很多
 * 但是因为第一时间考虑的时候没有想到，所以把所有的额组件都写成一堆了，这点是不符合编程理念的
 * 以后需要注意
 * @author Administrator
 *
 */
public class About_Format extends JFrame implements ItemListener,ActionListener{

    private JComboBox choose_word_style;
    private JComboBox choose_word_big;
    private JComboBox choose_word_pattern;
    private JComboBox choose_word_color;

    private JButton btn_ok;
    private JButton btn_cancel;

    private String[] styles = {"宋体","黑体","楷体","微软雅黑","隶书"};
    private String[] colors = {"红色","蓝色","绿色","黑色","白色","黄色"};
    private String[] word_big = {"2","4","8","16","24","32","64","72"};
    private String[] pattern = {"常规","倾斜","粗体"};

    private JTextField showText ;

    private JPanel paneNorth;
    private JPanel paneCenter;
    private JPanel paneSouth;

    // 用一个font来装选中的的属性，每选中一次，对对应的属性修改
    //然后集成一个font里进行修改
    //对selectedFont 设置默认属性
    private Font selectedFont = new Font("黑体",Font.PLAIN, 32);
    private String selectedStyle = "宋体";
    private int selectedBig = 32;
    private int selectedPattern = Font.PLAIN;
    private Color selectedColor = Color.BLACK;


    public Font getSelectedFont() {
        return selectedFont;
    }

    public String getSelectedStyle() {
        return selectedStyle;
    }

    public int getSelectedBig() {
        return selectedBig;
    }

    public int getSelectedPattern() {
        return selectedPattern;
    }
    /**
     * 这个窗口的够渣函数
     */
    public About_Format() {
        initBox();
        initText();
        initButton();
        initLocation();
        initListener();
        addBtnListener();

        this.setSize(550,200);
        this.setTitle("文字格式");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * 两个btn的监听事件回调
     * @param arg0
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cancel) {
            this.dispose();//销毁当前窗口
        }else if (e.getSource() == btn_ok) { // 调用父窗体的实例，拿到textarea并对其setFont
            FileManagement.getEdit_text_area().setFont(selectedFont); // 这里的Edit_text_area设置为静态变量static 函数也一样  这样才能调用
            FileManagement.getEdit_text_area().setForeground(selectedColor); // 设置颜色
            // 在父窗口内必须将Edit_text_area设置为static变量（静态变量）
            // 静态变量的特点是，已经对象一经实例化（test1被new出来） 其中的静态变量也会在内存中存在，一直持续到实例被销毁
            // 这时我们我们可以对其进行访问

		  /*test1 t1 = new test1();
		  t1.getEdit_text_area().setFont(selectedFont);*/
            /**
             * 以上这个方法是不行的，因为会通过实例化test1 窗口来设置一个新的Font的窗口，与我们想要的效果不相符
             * 我们想要的是在原父窗口内将其字体改变格式
             */
            this.dispose();
        }
    }

    /**
     * 对ComboBox添加监听器
     */
    private void initListener() {
        choose_word_style.addItemListener(this);
        choose_word_big.addItemListener(this);
        choose_word_pattern.addItemListener(this);
        choose_word_color.addItemListener(this);
    }

    /**
     * 初始化ok 和cancel 两个按钮
     */
    private void initButton() {
        btn_ok = new JButton("OK");
        btn_cancel = new JButton("CANCEL");
    }

    /**
     * 给两个btn添加监听器
     */
    public void addBtnListener() {
        btn_ok.addActionListener(this);
        btn_cancel.addActionListener(this);
    }

    /**
     * 初始化布局
     * 将每个控件按照一定得布局排在this窗口中
     */
    public void initLocation() {
        paneNorth = new JPanel();
        paneNorth.add(new JLabel("字体:"));
        paneNorth.add(choose_word_style);
        paneNorth.add(new JLabel("字号:"));
        paneNorth.add(choose_word_big);
        paneNorth.add(new JLabel("字形:"));
        paneNorth.add(choose_word_pattern);
        paneNorth.add(new JLabel("颜色:"));
        paneNorth.add(choose_word_color);
        this.add(paneNorth,BorderLayout.NORTH);

        paneCenter = new JPanel();
        paneCenter.add(showText);
        this.add(paneCenter, BorderLayout.CENTER);

        paneSouth = new JPanel();
        paneSouth.add(btn_ok);
        paneSouth.add(btn_cancel);
        this.add(paneSouth, BorderLayout.SOUTH);


    }

    /**
     * 初始化展示字体区域
     */
    public void initText() {
        showText = new JTextField("字体展示");
        showText.setFont(selectedFont);
        showText.setEditable(false);
        showText.setSize(100,160);
        //showText.setForeground(Color.red);
    }

    /**
     * 初始化几个comboBox
     * 把相应的选项加入
     */
    public void initBox() {
        choose_word_style = new JComboBox(styles);
        choose_word_big = new JComboBox(word_big);
        choose_word_pattern = new JComboBox(pattern);
        choose_word_color = new JComboBox(colors);
    }


    public void renewFont() {
        selectedFont = new Font(selectedStyle,selectedPattern,selectedBig);
        showText.setFont(selectedFont);
        showText.setForeground(selectedColor);
    }

    /**
     * 时间监听回调函数
     * 对每个item做出事件响应
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == "宋体") {
            selectedStyle = "宋体";
            renewFont();
        }else if (e.getItem() == "黑体") {
            selectedStyle = "黑体";
            renewFont();
        }else if (e.getItem() == "楷体") {
            selectedStyle = "楷体";
            renewFont();
        }else if (e.getItem() == "微软雅黑") {
            selectedStyle = "微软雅黑";
            renewFont();
        }else if (e.getItem() == "隶书") {
            selectedStyle = "隶书";
            renewFont();
        }else if (e.getItem() == "常规") {
            selectedPattern = Font.PLAIN;
            renewFont();
        }else if (e.getItem() == "倾斜") {
            selectedPattern = Font.ITALIC;
            renewFont();
        }else if (e.getItem() == "粗体") {
            selectedPattern = Font.BOLD;
            renewFont();
        }else if (e.getItem() == "2") {
            selectedBig = 2;
            renewFont();
        }else if (e.getItem() == "4") {
            selectedBig = 4;
            renewFont();
        }else if (e.getItem() == "8") {
            selectedBig = 8;
            renewFont();
        }else if (e.getItem() == "16") {
            selectedBig = 16;
            renewFont();
        }else if (e.getItem() == "24") {
            selectedBig = 24;
            renewFont();
        }else if (e.getItem() == "32") {
            selectedBig = 32;
            renewFont();
        }else if (e.getItem() == "64") {
            selectedBig = 64;
            renewFont();
        }else if (e.getItem() == "72") {
            selectedBig = 72;
            renewFont();
        }else if (e.getItem() == "红色") {
            selectedColor = Color.red;
            renewFont();
        }else if (e.getItem() == "黑色") {
            selectedColor = Color.black;
            renewFont();
        }else if (e.getItem() == "蓝色") {
            selectedColor = Color.blue;
            renewFont();
        }else if (e.getItem() == "黄色") {
            selectedColor = Color.yellow;
            renewFont();
        }else if (e.getItem() == "绿色") {
            selectedColor = Color.green;
            renewFont();
        }else if (e.getItem() == "白色") {
            selectedColor = Color.WHITE;
            renewFont();
        }
    }


}

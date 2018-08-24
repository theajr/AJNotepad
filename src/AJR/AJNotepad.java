package AJR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class AJNotepad {

    static JFrame mw;

    static JMenuBar menu;
    static JMenu file, edit, info, caser;
    static JMenuItem open, latest, sav, sava, cclose, exit, prefer, upcase, lowcase, titlecase, invertcase, wrap, ab, help;
    static JTextArea con;
    protected static JTextField search;
    protected static JMenuItem stats;
    static JTabbedPane tb;
    static StringBuilder sb;
    static JPanel body;
    private static JPanel tbar;
    protected static JPanel lpan;
    private static JPanel WindowControls;

    public static MyActionListener ActionMaster;
    private static JLabel titleLabel;
    protected static JPanel rpan;
    protected static JLabel status;
    protected static JToggleButton autoSave;
    protected static JCheckBox colorMode;
    protected static JCheckBox wordWraping;
    public static JCheckBox lineWraping;
    private static JPanel MenuPanel;
    protected static Box rpanVBox;
    public static JCheckBox alwaysOnTop;
    protected static JMenuItem sideList;
    protected static JMenuItem satusVisibility;
    protected static Font myfont;
    protected static JCheckBox metalTheme;
    protected static JCheckBox regularTheme;

    private static MyFormFarmer mfset;
    private static TabManager tmanage;
    protected static JButton openButton;

    protected static JList<String> currentFiles;
    protected static DefaultListModel<String> dls;
    protected static JLabel newLab;
    private static TBarListener tbl;
    protected static JLabel openLab;
    protected static JLabel saveLab;
    protected static JLabel saveAsLab;
    protected static JLabel copyLab;
    protected static JLabel pasteLab;
    protected static JLabel minimizeLab;
    protected static JLabel DimenLab;
    protected static JLabel exitLab;

    static String makeStr(String str) {
        sb = new StringBuilder(str);
        sb.setLength(((str.length() / 5) + 1) * 5);
        return sb.toString();
    }

    public static void main(String[] filesLocations) {

        ActionMaster = new MyActionListener();
        tmanage = new TabManager();
        tbl = new TBarListener();
        mw = new JFrame("AJEditor");
        mw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mw.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 100));
        mw.setUndecorated(true);

        int tbIconWidth = 25;
        int tbIconHeight = 25;
        ImageIcon nfic = new ImageIcon(AJNotepad.class.getResource("/imAJes/newFile.png"));
        ImageIcon ofic = new ImageIcon(AJNotepad.class.getResource("/imAJes/openFIle.png"));
        ImageIcon sfic = new ImageIcon(AJNotepad.class.getResource("/imAJes/saveFIle.png"));
        ImageIcon sfasic = new ImageIcon(AJNotepad.class.getResource("/imAJes/saveFileAs.png"));
        ImageIcon clfic = new ImageIcon(AJNotepad.class.getResource("/imAJes/close.png"));
        ImageIcon copyIc=new ImageIcon(AJNotepad.class.getResource("/imAJes/copyText.png"));
        ImageIcon psti = new ImageIcon(AJNotepad.class.getResource("/imAJes/pasteText.png"));
        ImageIcon exitIcon = new ImageIcon(AJNotepad.class.getResource("/imAJes/exit.png"));
        ImageIcon preIcon = new ImageIcon(AJNotepad.class.getResource("/imAJes/preferences.png"));
        ImageIcon sidePaneIcon = new ImageIcon(AJNotepad.class.getResource("/imAJes/sidepane.png"));
        ImageIcon statusBarIcon = new ImageIcon(AJNotepad.class.getResource("/imAJes/statusBar.png"));
        ImageIcon caseIcon = new ImageIcon(AJNotepad.class.getResource("/imAJes/case.png"));
        ImageIcon docOvIcon = new ImageIcon(AJNotepad.class.getResource("/imAJes/docView.png"));
        ImageIcon aboutIcons = new ImageIcon(AJNotepad.class.getResource("/imAJes/about.png"));
        ImageIcon checkedIc = new ImageIcon(AJNotepad.class.getResource("/imAJes/checked.png"));
        ImageIcon unchechkedIc = new ImageIcon(AJNotepad.class.getResource("/imAJes/unchecked.png"));
        ImageIcon miniIc = new ImageIcon(AJNotepad.class.getResource("/imAJes/minimize.png"));
        ImageIcon dimIc = new ImageIcon(AJNotepad.class.getResource("/imAJes/fullScreen.png"));

        ImageIcon newFileIcon = getRzIcon(nfic, tbIconWidth, tbIconHeight);
        ImageIcon openFileIcon = getRzIcon(ofic, tbIconWidth, tbIconHeight);
        ImageIcon saveFileIcon = getRzIcon(sfic, tbIconWidth, tbIconHeight);
        ImageIcon saveFileAsIcon = getRzIcon(sfasic, tbIconWidth, tbIconHeight);
        ImageIcon copyTextIcon = getRzIcon(copyIc, tbIconWidth, tbIconHeight);
        ImageIcon pasteTextIcon = getRzIcon(psti, tbIconWidth, tbIconHeight);

        menu = new JMenuBar();
       // menu.setBackground(SystemColor.activeCaption);
        menu.setBackground(Color.white);

        file = new JMenu(makeStr("File "));
        file.setMnemonic('F');
        edit = new JMenu(makeStr("Edit "));
        edit.setMnemonic('E');
        info = new JMenu(makeStr("Info "));
        info.setMnemonic('I');
        int menuIcW = 15;
        int menuIcH = 16;
        open = new JMenuItem(makeStr(("Open File")), getRzIcon(ofic, menuIcW, menuIcH));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(ActionMaster);

        latest = new JMenuItem(makeStr("New File"), getRzIcon(nfic, menuIcW, menuIcH));

        latest.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        latest.addActionListener(ActionMaster);

        sav = new JMenuItem("Save", getRzIcon(sfic, menuIcW, menuIcH));
        sav.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        sav.addActionListener(ActionMaster);

        sava = new JMenuItem("Save As", getRzIcon(sfasic, menuIcW, menuIcH));
        sava.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
        sava.addActionListener(ActionMaster);

        cclose = new JMenuItem("Close  ", getRzIcon(clfic, menuIcH, menuIcH));
        cclose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        cclose.addActionListener(ActionMaster);

        exit = new JMenuItem("Exit", getRzIcon(exitIcon, menuIcH, menuIcH));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exit.addActionListener(ActionMaster);

        prefer = new JMenuItem("Preferences", getRzIcon(preIcon, menuIcH, menuIcH));
        prefer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
        prefer.addActionListener(ActionMaster);

        sideList = new JMenuItem("Side Pane", getRzIcon(sidePaneIcon, menuIcH, menuIcH));
        sideList.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
        sideList.addActionListener(ActionMaster);

        satusVisibility = new JMenuItem("Status Bar", getRzIcon(statusBarIcon, menuIcH, menuIcH));
        satusVisibility.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
        satusVisibility.addActionListener(ActionMaster);
        caser = new JMenu("Change Case");

        upcase = new JMenuItem("Upper Case", getRzIcon(caseIcon, menuIcH, menuIcH));
        upcase.addActionListener(ActionMaster);
        lowcase = new JMenuItem("Lower Case", getRzIcon(caseIcon, menuIcH, menuIcH));
        lowcase.addActionListener(ActionMaster);
        titlecase = new JMenuItem("Title Case", getRzIcon(caseIcon, menuIcH, menuIcH));
        titlecase.addActionListener(ActionMaster);
        invertcase = new JMenuItem("Invert Case", getRzIcon(caseIcon, menuIcH, menuIcH));
        invertcase.addActionListener(ActionMaster);

        caser.add(upcase);
        caser.add(lowcase);
        caser.add(titlecase);
        caser.add(invertcase);

        stats = new JMenuItem("Doc overview", getRzIcon(docOvIcon, menuIcH, menuIcH));
        stats.addActionListener(ActionMaster);

        ab = new JMenuItem("About", getRzIcon(aboutIcons, menuIcW, menuIcH));

        file.addSeparator();
        file.add(latest);

        file.add(open);
        file.addSeparator();
        file.add(sav);
        file.add(sava);
        file.addSeparator();
        file.add(cclose);
        file.add(exit);
        file.addSeparator();

        edit.addSeparator();
        edit.add(prefer);
        edit.add(sideList);
        edit.add(satusVisibility);
        edit.addSeparator();
        edit.add(caser);
        edit.add(stats);
        edit.addSeparator();

        menu.add(file);
        info.addSeparator();
        info.add(ab);
        info.addSeparator();
        ab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.CTRL_MASK));
        ab.addActionListener(ActionMaster);

        menu.add(edit);

        menu.add(info);

        tbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        tbar.setDoubleBuffered(true);

        search = new JTextField("Search..", 10);
        search.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent Mom) {
                if (search.getText().equals("Search..")) {
                    search.setText("");
                }

            }

            @Override
            public void focusLost(FocusEvent Mom) {
                if (!search.getText().equals("Search..")) {

                    search.setText("Search..");
                    tmanage.highLightFindings();
                }
            }

        });
        search.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent arg0) {
                tmanage.highLightFindings();

            }

            @Override
            public void keyReleased(KeyEvent arg0) {
                tmanage.highLightFindings();
            }

            @Override
            public void keyTyped(KeyEvent kEvent) {
                tmanage.highLightFindings();

            }

        });

        newLab = new JLabel(newFileIcon);
        newLab.setToolTipText("New File");
        openLab = new JLabel(openFileIcon);
        openLab.setToolTipText("Open File");
        saveLab = new JLabel(saveFileIcon);
        saveLab.setToolTipText("Save");
        saveAsLab = new JLabel(saveFileAsIcon);
        saveAsLab.setToolTipText("Save As");
        saveAsLab.addMouseListener(tbl);
        copyLab = new JLabel(copyTextIcon);
        copyLab.setToolTipText("Copy");
        pasteLab = new JLabel(pasteTextIcon);
        pasteLab.setToolTipText("Paste");
        newLab.addMouseListener(tbl);
        openLab.addMouseListener(tbl);
        saveLab.addMouseListener(tbl);
        copyLab.addMouseListener(tbl);
        pasteLab.addMouseListener(tbl);
        tbar.add(newLab);
        tbar.add(openLab);

        tbar.add(saveAsLab);
        tbar.add(saveLab);
        tbar.add(copyLab);
        tbar.add(pasteLab);

        tbar.add(search);

        body = new JPanel(new BorderLayout(1, 1), true);
        tb = new JTabbedPane();
        tb.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tb.setBackground(Color.WHITE);
        tb.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        body.add(tb, BorderLayout.CENTER);
        lpan = new JPanel(new BorderLayout());
        lpan.setAlignmentY(JComponent.TOP_ALIGNMENT);
        lpan.setPreferredSize(new Dimension(100, 0));
        lpan.setBorder(tb.getBorder());
        dls = new DefaultListModel<String>();
        currentFiles = new JList<String>(dls);
        currentFiles.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (lse.getSource() == currentFiles) {
                    int lsel = currentFiles.getSelectedIndex();
                    if (lsel != -1) {
                        tb.setSelectedIndex(lsel);

                    }

                }
            }
        });
        tb.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {

                if (tb.getSelectedIndex() == -1 && tb.getTabCount() == 0) {
                    tb.setSelectedIndex(tb.getTabCount() - 1);

                }
                currentFiles.setSelectedIndex(tb.getSelectedIndex());
                tmanage.updateTabsWithSiderPane();

            }
        });
        lpan.add(new JScrollPane(currentFiles, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
        rpan = new JPanel(new GridLayout(0, 1, 0, 0));
        String[] sets = new String[]{};
        File setCF = new File("AJRConfig.config");
        mfset = new MyFormFarmer(setCF);

        if (!setCF.exists()) {
            mfset.setTextContent("true,true,false,false,true,false,true,");
        }

        sets = mfset.getTextContent().split(",");

        rpan.setAlignmentY(JComponent.TOP_ALIGNMENT);

        Icon checkedIcon = getRzIcon(checkedIc, 17, 17);
        Icon uncheckedIcon = getRzIcon(unchechkedIc, 17, 17);

        lineWraping = new JCheckBox("Line Wrap", Boolean.valueOf(sets[0]));
        lineWraping.setSelectedIcon(checkedIcon);
        lineWraping.setIcon(uncheckedIcon);

        wordWraping = new JCheckBox("Word Wrap", Boolean.valueOf(sets[1]));
        wordWraping.setSelectedIcon(checkedIcon);
        wordWraping.setIcon(uncheckedIcon);

        colorMode = new JCheckBox("Night Mode", Boolean.valueOf(sets[2]));
        colorMode.setSelectedIcon(checkedIcon);
        colorMode.setIcon(uncheckedIcon);

        autoSave = new JCheckBox("Auto Save Before Exit", Boolean.valueOf(sets[3]));
        autoSave.setSelectedIcon(checkedIcon);
        autoSave.setIcon(uncheckedIcon);

        alwaysOnTop = new JCheckBox("Always On Top", Boolean.valueOf(sets[4]));
        alwaysOnTop.setSelectedIcon(checkedIcon);
        alwaysOnTop.setIcon(uncheckedIcon);

        mw.setAlwaysOnTop(Boolean.valueOf(sets[4]));

        lineWraping.addActionListener(ActionMaster);
        wordWraping.addActionListener(ActionMaster);
        colorMode.addActionListener(ActionMaster);
        alwaysOnTop.addActionListener(ActionMaster);
        rpanVBox = Box.createVerticalBox();
        rpanVBox.add(lineWraping);
        rpanVBox.add(wordWraping);
        rpanVBox.add(colorMode);
        rpanVBox.add(autoSave);
        rpanVBox.add(alwaysOnTop);
        metalTheme = new JCheckBox("Metal Theme", Boolean.valueOf(sets[5]));
        regularTheme = new JCheckBox("Regular Theme", Boolean.valueOf(sets[6]));
        metalTheme.addActionListener(ActionMaster);
        regularTheme.addActionListener(ActionMaster);

        ButtonGroup theme = new ButtonGroup();
        theme.add(regularTheme);
        theme.add(metalTheme);
        rpanVBox.add(metalTheme);
        rpanVBox.add(regularTheme);
        rpan.add(rpanVBox);
        rpan.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        rpanVBox.setBackground(Color.white);
        MenuPanel = new JPanel(new GridLayout(0, 1));
        MenuPanel.add(menu);
        MenuPanel.setPreferredSize(new Dimension(200, 22));
        status = new JLabel("", JLabel.CENTER);
        status.setBorder(BorderFactory.createLineBorder(Color.green, 0));
        myfont = new Font("candara", Font.PLAIN, 17);

        status.setFont(new Font("Candara", Font.PLAIN, 10));
        body.add(tbar, BorderLayout.NORTH);
        body.add(lpan, BorderLayout.WEST);
        body.add(rpan, BorderLayout.EAST);
        rpan.setVisible(false);
        body.add(status, BorderLayout.SOUTH);
        WindowControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 1, 1));
        //WindowControls.setBackground(SystemColor.textHighlight);
        WindowControls.setBackground(Color.cyan);
        titleLabel = new JLabel("AJR TextEditor");
        minimizeLab = new JLabel(getRzIcon(miniIc, 20, 20));
        DimenLab = new JLabel(getRzIcon(dimIc, 15, 15));
        exitLab = new JLabel(getRzIcon(exitIcon, 15, 15));
        minimizeLab.addMouseListener(tbl);
        DimenLab.addMouseListener(tbl);
        exitLab.addMouseListener(tbl);
        Box windowControllers = Box.createHorizontalBox();
        windowControllers.add(titleLabel);
        windowControllers.add(Box.createHorizontalStrut(10));
        windowControllers.add(minimizeLab);
        windowControllers.add(Box.createHorizontalStrut(10));

        windowControllers.add(DimenLab);
        windowControllers.add(Box.createHorizontalStrut(10));

        windowControllers.add(exitLab);
        windowControllers.add(Box.createHorizontalStrut(10));

        titleLabel.setFont(new Font("candara", Font.PLAIN, 22));
        windowControllers.add(Box.createHorizontalStrut(1));
        WindowControls.add(windowControllers);

        Box completePane = Box.createVerticalBox();
        completePane.add(WindowControls);
        completePane.add(MenuPanel);

        completePane.add(body);
        completePane.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.blue));
        mw.setUndecorated(true);
        mw.setContentPane(completePane);
        mw.setSize(new Dimension(700, 500));
        mw.setResizable(true);
        mw.setLocationRelativeTo(null);
        try {
            if (Boolean.valueOf(sets[6])) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } else {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
        } catch (Exception e) {
        } finally {
            SwingUtilities.updateComponentTreeUI(mw);
        }
        tmanage.updateSettingsNow();
        mw.setIconImage(new ImageIcon(AJNotepad.class.getResource("/imAJes/AJR.png").toString()).getImage());
        mw.setVisible(true);
        if (tb.getTabCount() == 0) {
            latest.doClick();
            tb.setTitleAt(0, "   WEL-COME  ");
            tb.setToolTipTextAt(0, "#IntroMsg#");
            tmanage.setTextInTab(0, "\n\tHello Users..\n\t-------------------\n\n\t1.Advantages:\n\n\tYou Can Explore While Using!!\n\n");
            tmanage.updateTabsWithSiderPane();
        }
    }

    public static ImageIcon getRzIcon(ImageIcon imageIcon, int wd, int ht) {

      Image ret =imageIcon.getImage().getScaledInstance(wd, ht, Image.SCALE_SMOOTH);
      return (ret!=null)?new ImageIcon(ret):null;
     //   return imageIcon;
    }

}

package AJR;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MyActionListener extends AJNotepad implements ActionListener {

    JFileChooser dfg;
    TabManager tm;
    MyFormFarmer mff;
    Latest newLat;
    private JLabel ccll;
    private JLabel wcll;
    private JLabel lcll;
    Toolkit myKit;
    Clipboard myClipBoard;

    public void actionPerformed(ActionEvent act) {
        JComponent sel = (JComponent) act.getSource();
        dfg = new JFileChooser();
        tm = new TabManager();
        myKit = Toolkit.getDefaultToolkit();

        if (sel == open) {
            dfg.setMultiSelectionEnabled(true);
            dfg.setDialogTitle("Import Text Files!");
            dfg.setApproveButtonText("Open..");
            dfg.setApproveButtonToolTipText("Click to open selected files!");
            int sto = dfg.showOpenDialog(mw);

            if (sto == JFileChooser.APPROVE_OPTION) {
                File[] all = dfg.getSelectedFiles();
                for (File fi : all) {
                    File fii = fi;
                    mff = new MyFormFarmer(fii);
                    String fiCon = mff.getTextContent();
                    if(fiCon!=null){
                    tm.addNewTab(fii.getName(), fiCon, fii.toString());
                    tb.setSelectedIndex(tb.getTabCount() - 1);
                    }else{
                    status.setText(fii.getName()+" is Not a text file");
                    }
                }
            }

        }
        if (sel == latest) {
            tm.addNewTab("New File-" + tm.getNewFileSuffix(), "", "#AJR#");
            tm.updateTabsWithSiderPane();
        } else if (sel == sav) {
            int sti = tb.getSelectedIndex();
            if (sti != -1) {
                String ttt = tb.getToolTipTextAt(sti);
                String tratxt = tm.getTextInTab(sti);
                if (ttt.equals("#AJR#") == false) {
                    mff = new MyFormFarmer(new File(ttt));
                    mff.setTextContent(tratxt);
                } else {
                    mw.setAlwaysOnTop(false);
                    newLat = new Latest();
                    newLat.createLatest(tratxt);
                }
            } else {
                status.setText("No file is opened to save!");
            }

        } else if (sel == sava) {
            if (tb.getSelectedIndex() != -1) {
                newLat = new Latest();
                newLat.createLatest(tm.getTextInTab(tb.getSelectedIndex()));
            } else {
                status.setText("No file is opened!");
            }
        } else if (sel == cclose) {
            if (tb.getTabCount() > 0) {
                tm.closeTab(tb.getSelectedIndex());
                currentFiles.setSelectedIndex(tb.getSelectedIndex());
            } else {
                status.setText("No file is opened to close!");
            }

        } else if (sel == exit) {
            tm.updateTabsWithSiderPane();
            while (tb.getTabCount() != 0) {
                cclose.doClick();
            }
            System.exit(0);
        } else if (sel == prefer) {
            rpan.setVisible(!rpan.isShowing());
        } else if (sel == sideList) {
            tm.updateTabsWithSiderPane();
            lpan.setVisible(!lpan.isShowing());
        } else if (sel == satusVisibility) {
            status.setVisible(!status.isShowing());
        } else if (sel == upcase) {
            tm.changeCase(1);
        } else if (sel == lowcase) {
            tm.changeCase(2);
        } else if (sel == titlecase) {
            tm.changeCase(3);
        } else if (sel == invertcase) {
            tm.changeCase(4);
        } else if (sel == stats) {
            if (tb.getSelectedIndex() != -1) {
                final JFrame docView = new JFrame("Over view of \"" + tb.getTitleAt(tb.getSelectedIndex()) + "\"");
                docView.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                docView.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
                docView.setUndecorated(true);

                JPanel docP = new JPanel(new GridLayout(0, 2, 10, 2));
                int wordsCount = tm.getTextInTab(tb.getSelectedIndex()).split("\\s+").length;
                int letterCount = tm.getTextInTab(tb.getSelectedIndex()).length();
                int lineCount = tm.getTextInTab(tb.getSelectedIndex()).split("\n").length;
                JLabel wcl = new JLabel("No.Of Words");
                JLabel ccl = new JLabel("No.Of Letters");
                JLabel lcl = new JLabel("No.of Lines");
                Font myFont = new Font("candara", Font.PLAIN, 17);
                wcl.setFont(myFont);
                ccl.setFont(myFont);
                lcl.setFont(myFont);
                docP.add(ccl);
                ccll = new JLabel("" + letterCount);
                wcll = new JLabel("" + wordsCount);
                lcll = new JLabel("" + lineCount);
                ccll.setFont(myFont);
                wcll.setFont(myFont);
                lcll.setFont(myFont);
                docP.add(ccll);
                docP.add(wcl);
                docP.add(wcll);
                docP.add(lcl);
                docP.add(lcll);
                docView.add(docP);
                final JButton okAndClose = new JButton("OK");
                okAndClose.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent hu) {
                        JButton hus = (JButton) hu.getSource();
                        if (hus == okAndClose) {
                            docView.setVisible(false);
                        }
                    }
                });
                docP.add(okAndClose);
                docView.pack();
                docView.setLocationRelativeTo(mw);
                //docView.setSize(300,100);
                docView.setVisible(true);
            } else {
                status.setText("No file is opened!");
            }

        } else if (sel == regularTheme) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                SwingUtilities.updateComponentTreeUI(mw);
                tm.updateSettingsNow();

            }

        } else if (sel == metalTheme) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                SwingUtilities.updateComponentTreeUI(mw);
                tm.updateSettingsNow();

            }
        } else if (sel == ab) {
            mw.setFocusable(false);
            AboutFrame af = new AboutFrame();
            af.setAlwaysOnTop(true);
            af.setVisible(true);

        } else if (sel == lineWraping || sel == wordWraping || sel == colorMode || sel == alwaysOnTop) {
            tm.updateSettingsNow();
        } else{
        }
    }
}

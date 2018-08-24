package AJR;

import static AJR.AJNotepad.mw;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AboutFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JTextArea moreInfo;
    private final JLabel nameL;
    private final JButton closeButn;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ;
        }

    }
    Thread myName;
    private final boolean temp;

    public AboutFrame() {
        temp = mw.isAlwaysOnTop();
        mw.setAlwaysOnTop(false);
        this.setVisible(false);
        setTitle("About Designer!");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        Box vb = Box.createVerticalBox();
        nameL = new JLabel("ZZ");
        vb.setAlignmentX(LEFT_ALIGNMENT);
        nameL.setFont(new Font("Consolas", 1, 24));
        nameL.setHorizontalAlignment(JLabel.LEFT);
        moreInfo = new JTextArea("App: AJEditor v2.1\nID:B101396\nDate:15th September,2014\nFeedback:ajr1396@gmail.com   \n", 4, 10);
        moreInfo.setForeground(Color.BLUE);
        moreInfo.setDoubleBuffered(true);
        moreInfo.setBackground(Color.gray);
        closeButn = new JButton("OK");
        closeButn.addActionListener(this);
        moreInfo.setEditable(false);
        vb.add(moreInfo);
        vb.add(Box.createVerticalStrut(10));
        vb.add(closeButn);
        setLocationRelativeTo(null);
        setUndecorated(true);
        
        add(nameL);
        
        myName = new Thread(new Runnable() {
            String AJ = " AJay Reddy";

            int index = 0;
            Color[] colors = {Color.blue, Color.red, Color.green, Color.cyan, Color.orange};

            @Override
            public void run() {
                while (!myName.isInterrupted()) {
                    nameL.setForeground(colors[index % colors.length]);
                    nameL.setText(AJ.substring(0, 1 + index % AJ.length()));
                    moreInfo.setForeground(colors[2 + index % 3]);
                    setSize(332, 220);
                    index += 1;

                    try {
                        Thread.sleep(500);

                    } catch (Exception e) {

                    }
                }

            }
        });
        add(vb);
       
        myName.start();
        pack();
        setAutoRequestFocus(true);
        SwingUtilities.updateComponentTreeUI(this);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == closeButn) {
            this.setVisible(false);
            mw.setAlwaysOnTop(temp);
            mw.setFocusable(true);
        }
    }
}

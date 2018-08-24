package AJR;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TBarListener extends AJNotepad implements MouseListener {

    Toolkit myKit;
    Clipboard myClipBoard;
    TabManager tm;

    @Override
    public void mouseClicked(MouseEvent me) {
        JComponent clickedLab = (JComponent) me.getSource();
        tm = new TabManager();
        myKit = Toolkit.getDefaultToolkit();
        if (clickedLab == newLab) {
            latest.doClick();
        } else if (clickedLab == openLab) {
            open.doClick();
        } else if (clickedLab == saveLab) {
            sav.doClick();
        } else if (clickedLab == saveAsLab) {
            sava.doClick();
        } else if (clickedLab == copyLab) {
            int selIn = tb.getSelectedIndex();
            if (selIn != -1) {
                myClipBoard = myKit.getSystemClipboard();
                String markedT = tm.getMarkedText(selIn);
                if (markedT != null) {
                    StringSelection markedText = new StringSelection(markedT);
                    myClipBoard.setContents(markedText, null);
                }
            }
        } else if (clickedLab == pasteLab) {
            if (tb.getSelectedIndex() != -1) {
                myClipBoard = myKit.getSystemClipboard();
                JTextArea curJTA = tm.getJTextArea(tb.getSelectedIndex());
                Transferable clipData = myClipBoard.getContents(myClipBoard);
                try {
                    String toPaste = (String) clipData.getTransferData(DataFlavor.stringFlavor);
                    curJTA.insert(toPaste, curJTA.getCaretPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                status.setText("No file is opened to get pasted!");
            }
        } else if (clickedLab == minimizeLab) {

            mw.setState(JFrame.ICONIFIED);

        } else if (clickedLab == DimenLab) {
            if (mw.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                DimenLab.setIcon(super.getRzIcon(new ImageIcon(AJNotepad.class.getResource("/imAJes/fullScreen.png")), 15, 15));
                mw.setSize(700, 500);
            } else {
                mw.setExtendedState(JFrame.MAXIMIZED_BOTH);
                DimenLab.setIcon(super.getRzIcon(new ImageIcon(AJNotepad.class.getResource("/imAJes/exitFullScreen.png")), 15, 15));
            }
        } else if (clickedLab == exitLab) {
            while (tb.getTabCount() != 0) {
                cclose.doClick();
            }
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
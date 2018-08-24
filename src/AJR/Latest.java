package AJR;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Latest extends AJNotepad {

    JFileChooser jfc;
    private TabManager Tman;
    boolean mwATop;

    private void changeMWTop() {
        mwATop = mw.isAlwaysOnTop();
        if (mwATop) {
            mw.setAlwaysOnTop(false);
        }
    }

    public void createLatest(String textCon) {
        changeMWTop();
        jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        int sres = jfc.showSaveDialog(jfc);
        if (sres == JFileChooser.APPROVE_OPTION) {
            File ndoc = jfc.getSelectedFile();
            if (ndoc.exists()) {
                int confirmSel = JOptionPane.showConfirmDialog(jfc, "Selected Exsiting File..\nDo U Wanna proceed to overwrite it?", "Check Your Selection??", sres);
                if (confirmSel == 0) {
                    MyFormFarmer mff = new MyFormFarmer(ndoc);
                    mff.setTextContent(textCon);
                    status.setText("Saved to " + ndoc);
                } else {
                    sav.doClick();
                }
            } else {
                MyFormFarmer mff = new MyFormFarmer(ndoc);
                mff.setTextContent(textCon);
                status.setText("Saved to " + ndoc);
            }
            Tman = new TabManager();
            tb.removeTabAt(tb.getSelectedIndex());
            Tman.addNewTab(ndoc.getName(), new MyFormFarmer(ndoc).getTextContent(), ndoc.toString());

        } else {
            if (textCon != null && autoSave.isSelected() == true) {
                int tempCount = 1;
                File currentDir = new File(".");
                File[] allFiles = currentDir.listFiles();
                for (File uniq : allFiles) {
                    if (uniq.getName().toString().startsWith("temp") == true) {
                        tempCount += 1;
                    }
                }
                File nowFile = new File("temp" + tempCount + ".txt");
                MyFormFarmer mff = new MyFormFarmer(nowFile);
                mff.setTextContent(textCon);
                status.setText("Saved to:" + nowFile);
            }
            tb.removeTabAt(tb.getSelectedIndex());
        }
        mw.setAlwaysOnTop(mwATop);
    }

}

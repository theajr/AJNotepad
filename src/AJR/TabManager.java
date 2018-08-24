package AJR;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class TabManager extends AJNotepad {

    private MyFormFarmer mfftm;

    public void addNewTab(String TabTitle, String TextAreaContent, String ToolTipTextContent) {
        final JTextArea textArea = new JTextArea(TextAreaContent);
        textArea.setRows(10);
        textArea.setBorder(BorderFactory.createLineBorder(Color.white));
        JScrollPane sPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sPane.setIgnoreRepaint(true);
        sPane.setBorder(BorderFactory.createEmptyBorder());
        sPane.setAutoscrolls(true);
        int prevIndex = tb.indexOfTab(TabTitle);
        if (prevIndex != -1 && tb.getToolTipTextAt(prevIndex).equals(ToolTipTextContent)) {
            tb.removeTabAt(prevIndex);
        }
        tb.addTab(TabTitle, sPane);
        tb.setToolTipTextAt(tb.getTabCount() - 1, ToolTipTextContent);
        tb.setSelectedIndex(tb.getTabCount() - 1);
        textArea.requestFocus(true);
        int prLen = textArea.getText().length();
        if (prLen > 0) {
            textArea.setCaretPosition(prLen - 1);
        }

        textArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        textArea.requestFocus(true);
        updateTabsWithSiderPane();
        updateSettingsNow();
        currentFiles.setSelectedIndex(tb.getSelectedIndex());
    }

    public int getNewFileSuffix() {
        int loop;
        int suffix = 1;
        for (loop = 0; loop < tb.getTabCount(); loop++) {
            if (tb.getTitleAt(loop).startsWith("New File-") == true) {
                suffix += 1;
            }
        }
        return suffix;

    }

    public JTextArea getJTextArea(int index) {
        Component tcc = tb.getComponentAt(index);
        JViewport xcv = ((JScrollPane) tcc).getViewport();
        JTextArea currentTextArea = (JTextArea) xcv.getView();
        return currentTextArea;

    }

    public String getMarkedText(int sIndex) {
        JTextArea currentJTA = getJTextArea(sIndex);
        return currentJTA.getSelectedText();
    }

    public String getTextInTab(int sIndex) {
        JTextArea currentJTA = getJTextArea(sIndex);
        return currentJTA.getText();
    }

    public void setTextInTab(int sIndex, String NewText) {
        JTextArea currentJTA = getJTextArea(sIndex);
        currentJTA.setText(NewText);

    }

    public void changeCase(int conversionCode) {
        /*conversion codes		 
         * 1-upper case		 
         * 2-lower case		 
         * 3-title case		 
         * 4-invert case 	
        */
        int nowti = tb.getSelectedIndex();
        if (nowti != -1) {
            StringBuilder tx = new StringBuilder(getTextInTab(nowti));
            String stx = getMarkedText(nowti);
            if (stx != null) {
                int startIndex = getJTextArea(nowti).getSelectionStart();
                StringBuilder uptxt = tx.delete(startIndex, getJTextArea(nowti).getSelectionStart() + stx.length());
                if (conversionCode == 1) {
                    uptxt.insert(getJTextArea(nowti).getSelectionStart(), stx.toUpperCase());
                } else if (conversionCode == 2) {
                    uptxt.insert(getJTextArea(nowti).getSelectionStart(), stx.toLowerCase());
                } else if (conversionCode == 3) {
                    int funcStart = 0;
                    if (startIndex > 0) {

                        if (("" + tx.charAt(startIndex - 1)).equals(" ") || ("" + tx.charAt(startIndex - 1)).equals("\n")) {
                            funcStart = 0;
                        } else {
                            funcStart = stx.indexOf(" ") < stx.indexOf("\n") ? stx.indexOf(" ") : stx.indexOf("\n");
                        }
                    }
                    uptxt.insert(getJTextArea(nowti).getSelectionStart(), toTitleCase(stx, funcStart));

                } else if (conversionCode == 4) {
                    uptxt.insert(getJTextArea(nowti).getSelectionStart(), toInvertCase(stx));
                }
                setTextInTab(nowti, uptxt.toString());
            } else {
                JOptionPane.showMessageDialog(null, "You haven't selected text..");
            }
        } else {
            status.setText("No File is opened!");
        }
    }

    private String toInvertCase(String estx) {

        String invStr = "";
        int invl;
        String Uestx = estx.toUpperCase();
        for (invl = 0; invl < estx.length(); invl++) {
            String es = "" + estx.charAt(invl);
            String ues = "" + Uestx.charAt(invl);
            invStr += (es.equals(ues)) ? es.toLowerCase() : ues;

        }
        return invStr;
    }

    private String toTitleCase(String estx, int fStart) {
        String titCase = "";
        if (fStart != 0) {
            titCase += estx.substring(0, fStart);
        }
        estx = estx.substring(fStart);
        String aestx[] = estx.split("\n");
        int iaa, ibb;
        for (iaa = 0; iaa < aestx.length; iaa++) {
            String[] apia = aestx[iaa].split(" ");
            for (ibb = 0; ibb < apia.length; ibb++) {
                String nwwrd = apia[ibb];
                if (nwwrd.length() <= 1) {
                    titCase += nwwrd.toUpperCase();
                } else {
                    titCase += (("" + nwwrd.charAt(0)).toUpperCase() + (nwwrd.substring(1)).toLowerCase());
                }
                titCase += " ";
            }
            if (iaa != aestx.length - 1) {
                titCase += "\n";
            }
        }
        return titCase;
    }

    public void closeTab(int cIndex) {
        String cttt = new String(tb.getToolTipTextAt(cIndex));
        String cctxt = new String(getTextInTab(cIndex));
        if (cttt.equals("#AJR#")) {
            if (cctxt.length() == 0) {
                tb.removeTabAt(cIndex);
            } else {
                sava.doClick();
            }
        } else if(cttt.equals("#IntroMsg#")){
        	tb.removeTabAt(cIndex);
        }
        
        else {
            mfftm = new MyFormFarmer(new File(cttt));
            String mfftmtxt = new String(mfftm.getTextContent());

            if (mfftmtxt.equals(cctxt)) {
                tb.removeTabAt(cIndex);
                this.updateSettingsNow();
            } else {
                if (autoSave.isSelected()) {
                    sav.doClick();
                    tb.removeTabAt(cIndex);
                    this.updateSettingsNow();

                } else {
                    int sORn = JOptionPane.showConfirmDialog(mw, "Save Modifications to \"" + tb.getTitleAt(cIndex) + "\"", "Data Modification Alert", 1);
                    if (sORn == 0) {
                        sav.doClick();
                        tb.removeTabAt(cIndex);
                    } else if (sORn == 1) {
                        tb.removeTabAt(cIndex);
                        this.updateSettingsNow();

                    } else {
                        ;;
                    }
                }
            }
        }
        updateTabsWithSiderPane();
    }

    void updateTabsWithSiderPane() {
        if (dls.getSize() != tb.getTabCount()) {
            dls.removeAllElements();
            for (int utws = 0; utws < tb.getTabCount(); utws++) {
                dls.addElement((utws + 1) + "." + tb.getTitleAt(utws));
            }
        }
    }

    public void updateSettingsNow() {
        boolean lineWrapOpt = lineWraping.isSelected();
        boolean wordWrapOpt = wordWraping.isSelected();
        boolean colorModeOpt = colorMode.isSelected();
        for (int sind = 0; sind < tb.getTabCount(); sind++) {
            JTextArea tra = getJTextArea(sind);
            tra.setLineWrap(lineWrapOpt);
            tra.setWrapStyleWord(wordWrapOpt);
            Color modeForeColor = (colorModeOpt == true) ? Color.white : Color.black;
            Color modeBackColor = (colorModeOpt == false) ? Color.white : Color.black;
            tra.setForeground(modeForeColor);
            tra.setBackground(modeBackColor);
            tra.setCaretColor(modeForeColor);
        }
        mw.setAlwaysOnTop(alwaysOnTop.isSelected());
        File setCFile = new File("AJRConfig.config");
        MyFormFarmer mffnn = new MyFormFarmer(setCFile);
        mffnn.setTextContent(lineWrapOpt + "," + wordWrapOpt + "," + colorModeOpt + "," + autoSave.isSelected() + "," + alwaysOnTop.isSelected() + "," + metalTheme.isSelected() + "," + regularTheme.isSelected() + ",");

    }

    public void highLightFindings() {
        int nowAt = tb.getSelectedIndex();
        String keyWord = search.getText();
        if (nowAt != -1 && keyWord != null) {
            JTextArea nowJ = getJTextArea(nowAt);

            String whole = getTextInTab(nowAt);
            Highlighter hl = nowJ.getHighlighter();
            hl.removeAllHighlights();
            if (whole.contains(keyWord)) {
                int lot = 0;
                boolean can = true;
                while (can) {
                    int startIndex = whole.indexOf(keyWord, lot);
                    try {
                        hl.addHighlight(startIndex, startIndex + keyWord.length(), DefaultHighlighter.DefaultPainter);
                        lot = startIndex + 1;
                        if (startIndex == whole.lastIndexOf(keyWord)) {
                            can = false;
                        }

                        if (keyWord.length() == 0) {
                            hl.removeAllHighlights();
                        }

                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

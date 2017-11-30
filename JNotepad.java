import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

class JNotepad
  extends JFrame { 
  
  public JNotepad() {
    super("Untitled - Notepad");
    
    JMenuBar menubar = new JMenuBar();
    JMenu menu_f = new JMenu("File");
    menu_f.setMnemonic(KeyEvent.VK_F);
    JMenu menu_e = new JMenu("Edit");
    menu_e.setMnemonic(KeyEvent.VK_E);
    JMenu menu_o = new JMenu("Format");
    menu_o.setMnemonic(KeyEvent.VK_O);
    JMenu menu_v = new JMenu("View");
    menu_v.setMnemonic(KeyEvent.VK_V);
    JMenu menu_h = new JMenu("Help");
    menu_h.setMnemonic(KeyEvent.VK_H);
    menubar.add(menu_f);
    menubar.add(menu_e);
    menubar.add(menu_o);
    menubar.add(menu_v);
    menubar.add(menu_h);
    
    setLayout(new BorderLayout());
    JTextArea ta = new JTextArea();
    ta.setFont(JDesktopApp.FONT);
    JScrollPane sp = new JScrollPane(ta);   // JTextArea is placed in a JScrollPane.
    getContentPane().add(sp);
    setJMenuBar(menubar);
    
    setLocation(100,100);
    setSize(700,400);
    setVisible(true);
    
    toFront();
  }

}

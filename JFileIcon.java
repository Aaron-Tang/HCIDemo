import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public class JFileIcon extends JPanel implements MouseListener, FocusListener, AWTEventListener {

  protected File f;
  private ImageIcon icon;
  private JLabel label = new JLabel(new String(), SwingConstants.CENTER);
  
	private JTextArea textfield;

	public JFileIcon(File f,ImageIcon icon) {
		super();
    
    this.f = f;

    BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
    this.setBackground(Color.lightGray);

    this.icon = icon;
    this.textfield = new JTextArea(f.getName());
    this.label.setText(chop(textfield.getText()));
    
    this.label.setBackground(Color.lightGray);
    this.textfield.setBackground(Color.lightGray);
    this.label.setSize(110,17);
    this.textfield.setSize(110,17);
    this.textfield.setLineWrap(true);

    this.textfield.setEditable(true);
    this.textfield.setEnabled(true);
    
    add(new JLabel(icon),layout.CENTER);
    add(label,layout.SOUTH);
    
    setSize(110,127);
    setLocation(-1,-1);
    
    this.label.addMouseListener(this);
    this.textfield.addFocusListener(this);
	}
  
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mouseClicked(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {
    this.remove(label);
    this.validate();
    this.add(textfield,BorderLayout.SOUTH);
    this.validate();
    textfield.requestFocus();
    textfield.setCaretPosition(textfield.getText().length());
    
    Toolkit.getDefaultToolkit().addAWTEventListener(this,AWTEvent.MOUSE_EVENT_MASK);
  }
  
  public void eventDispatched(AWTEvent e) {
    if (e.getSource() != textfield) {
      textfield.transferFocus();
      Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    }
      
  }
  
  public void focusLost(FocusEvent e) {
    this.label.setText(chop(textfield.getText()));
    this.remove(textfield);
    this.validate();
    this.add(this.label,BorderLayout.SOUTH);
    this.validate();
  }
  
  public void focusGained(FocusEvent e) {
  }
  
  public void keyPressed(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
  
  public String chop(String str) {
    String new_string = str;
    if (str.length() > 15) {
      new_string = str.substring(0,13) + "...";
    }
    
    return(new_string);
  }
}

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.AdobePDFSchema;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.XMPBasicSchema;
import org.apache.xmpbox.xml.DomXmpParser;
import org.apache.xmpbox.xml.XmpParsingException;

class JDesktopApp
  extends JWindow
  implements ActionListener  { 
  
  public static final Font FONT = new Font("sans-serif",Font.PLAIN,20);
  
  public static final String DESKTOP_PATH = "./desktop";
  public static final String DESKTOP_IMAGE = "./images/desktop_blank.png";
  
  public static final String EXPLORER_APP_ICON = "./images/app_explorer.png";
  public static final String CHROME_APP_ICON = "./images/app_chrome.png";
  public static final String PPT_APP_ICON = "./images/app_ppt.png";
  public static final String PDF_APP_ICON = "./images/app_pdf.png";
  public static final String NOTEPAD_APP_ICON = "./images/app_notepad.png";
  
  public static final String DOC_ICON = "./images/file_doc.png";
  public static final String FOLDER_ICON = "./images/file_folder.png";
  public static final String IMAGE_ICON = "./images/file_image.png";
  public static final String MOVIE_ICON = "./images/file_movie.png";
  public static final String PDF_ICON = "./images/file_pdf.png";
  public static final String PPT_ICON = "./images/file_ppt.png";
  public static final String RECYCLE_ICON = "./images/file_recycle.png";
  public static final String TXT_ICON = "./images/file_txt.png";
  
  protected BufferedImage bgImage = null;
  protected Vector files = new Vector();
   
  protected JButton explorer_button = null;
  protected JButton chrome_button = null;
  protected JButton ppt_button = null;
  protected JButton pdf_button = null;
  protected JButton notepad_button = null;
  
  protected JPanel main_panel = new JPanel();
  
  public JDesktopApp() {
    super(new JFrame(){public boolean isShowing(){return true;}});
    
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    setSize(toolkit.getScreenSize().width,toolkit.getScreenSize().height);
    UIManager.put("Button.focus", Color.black);
    UIManager.put("Menu.font", FONT);
    
    setLocation(0,0);
    setVisible(true);
    //setAlwaysOnTop(true);
    toFront();

    
    JLayeredPane pane = getLayeredPane();

    JPanel quick_launch_panel = new JPanel();
    explorer_button = new JButton(new ImageIcon(EXPLORER_APP_ICON));
    explorer_button.setBackground(Color.black);
    explorer_button.setBorder(null);
    explorer_button.addActionListener(this);

    
    chrome_button = new JButton(new ImageIcon(CHROME_APP_ICON));
    chrome_button.setBackground(Color.black);
    chrome_button.setBorder(null);

    ppt_button = new JButton(new ImageIcon(PPT_APP_ICON));
    ppt_button.setBackground(Color.black);
    ppt_button.setBorder(null);

    pdf_button = new JButton(new ImageIcon(PDF_APP_ICON));
    pdf_button.setBackground(Color.black);
    pdf_button.setBorder(null);
    pdf_button.addActionListener(this);

    notepad_button = new JButton(new ImageIcon(NOTEPAD_APP_ICON));
    notepad_button.setBackground(Color.black);
    notepad_button.setBorder(null);
    notepad_button.addActionListener(this);

    quick_launch_panel.setSize(58*5,52);
    
    quick_launch_panel.setLayout(new GridLayout(1,5));
    quick_launch_panel.add(explorer_button);
    quick_launch_panel.add(chrome_button);
    quick_launch_panel.add(ppt_button);
    quick_launch_panel.add(pdf_button);
    quick_launch_panel.add(notepad_button);
    
    quick_launch_panel.setLocation(150,toolkit.getScreenSize().height-quick_launch_panel.getSize().height);
    pane.add(quick_launch_panel);
    
    main_panel.setLayout(null);
    main_panel.setSize(toolkit.getScreenSize().width,quick_launch_panel.getLocation().y-7);
    main_panel.setBackground(Color.lightGray);
    pane.add(main_panel);
    
    for (int i=0; i<files.size(); i++) {
      File f = (File) files.elementAt(i);
      JFileIcon icon = getIcon(f);
      main_panel.add(icon);
    }

    files = readFilesAndFolders();
    add(files);
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == notepad_button)
      new JNotepad();
    else if (e.getSource() == explorer_button) {
    	new fileView();
    }
    else if (e.getSource() == pdf_button) {
    	new pdfView();
    }
  }

  public JFileIcon getIcon(File f) {
    JFileIcon icon = null;
    try {
      if (f.isDirectory())
        icon = new JFileIcon(f,new ImageIcon(FOLDER_ICON));
      else if (f.getName().endsWith(".doc"))
        icon = new JFileIcon(f,new ImageIcon(DOC_ICON));
      else if (f.getName().endsWith(".jpg"))
        icon = new JFileIcon(f,new ImageIcon(IMAGE_ICON));
      else if (f.getName().endsWith(".mp4") || f.getName().endsWith(".mkv") || f.getName().endsWith(".avi"))
        icon = new JFileIcon(f,new ImageIcon(MOVIE_ICON));
      else if (f.getName().endsWith(".pdf"))
        icon = new JFileIcon(f,new ImageIcon(PDF_ICON));
      else if (f.getName().endsWith(".ppt"))
        icon = new JFileIcon(f,new ImageIcon(PPT_ICON));
      else if (f.getName().endsWith(".txt"))
        icon = new JFileIcon(f,new ImageIcon(TXT_ICON));
    } catch(Exception e) {
    }
    return(icon);
  }

  public void add(Vector files) {
    int column = -1;
    int y_0 = 0;
    
    for (int i=0; i<files.size(); i++) {
      JFileIcon icon = (JFileIcon)files.elementAt(i);
      main_panel.add(icon);
      
      y_0 = y_0 + icon.getSize().height;
      if ((i==0) || (y_0 + icon.getSize().height > main_panel.getSize().height)) {
        y_0 = 0;
        column++;
      }
      
      int x = column * icon.getSize().width;
      int y = y_0;
      icon.setLocation(x,y);
    }
  }
  
  public void arrangeIcons(Vector files) {
    for (int i=0; i<files.size(); i++) {
      JFileIcon icon = (JFileIcon)files.elementAt(i);
    }
  }
  
  public Vector readFilesAndFolders() {
    Vector files = new Vector();
    try {
      File path = new File(DESKTOP_PATH);
      String[] list = path.list();
      for (int i=0; i<list.length; i++) {
        File f = new File(path.getPath() + "\\" + list[i]);
        JFileIcon icon = getIcon(f);
        files.addElement(icon);
      }
    } catch(Exception e) {
    }
    return(files);
  }
  
  public void paint(Graphics g) {
    if (bgImage == null) {
      try {
          File img = new File(DESKTOP_IMAGE);
          bgImage = ImageIO.read(img); 
      } catch (IOException e) { 
          e.printStackTrace(); 
      }
   }
   if (bgImage != null)
     g.drawImage(bgImage,0,0,this);
  }
  
  public static void main(String argv[]) {
    JDesktopApp desktop = new JDesktopApp();
    desktop.setVisible(true);
    desktop.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          Window win = e.getWindow();
          win.setVisible(false);
          win.dispose();
          System.exit(0);
        }
      }
    );
  }
}

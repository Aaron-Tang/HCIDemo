import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class pdfView extends JFrame{

	JTextField sourceFiles, destFile;
	JLabel label1, label2;
	JPanel panel, textPanel;
	JButton button1;
	private ArrayList<String> allFiles = new ArrayList<String>();
	private ArrayList<String> pdfTitles = new ArrayList<String>();
	
	public pdfView(ArrayList<String> allFiles, JNotepad main_notepad) {
		this.allFiles = allFiles;
	    setTitle("Add Titles to Text File");
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setSize(375,400);
	    setResizable(false);
	    
		Font font1 = new Font("SansSerif", Font.PLAIN, 20);
	    
	    panel = new JPanel(new BorderLayout());
	    textPanel = new JPanel();
	    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
	    add(panel);
	    panel.add(textPanel, BorderLayout.CENTER);
	    
//	    sourceFiles = new JTextField(10);
//	    sourceFiles.setFont(font1);
	    destFile = new JTextField();
	    destFile.setFont(font1);
	    
//	    label1 = new JLabel("Enter the file(s) with one of the following formats \n"
//	    		+ "eg: 1, 2-4,");
//
//	    label1.setFont(font1);
	    label2 = new JLabel("Enter the destination text file below");
	    label2.setFont(font1);

	    
//	    textPanel.add(label1);
//	    textPanel.add(sourceFiles);
	    
	    textPanel.add(label2);
	    textPanel.add(destFile);
	    
	    button1 = new JButton("OK");
	    button1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		String targetFile = destFile.getText();

	    		pdfTitles = addTitlesToFile(targetFile);
	    		System.out.println("THIS" + pdfTitles);
	    		
	    		for (String title: pdfTitles) {
	    			System.out.println("TITLE: " + title);
		    		main_notepad.addToTextArea(title + "\n");	    		
	    		}
	    		
	    	}
	    });
	    panel.add(button1, BorderLayout.SOUTH);
	    
//	    pack();
	    setVisible(true);
	}
	
	
	public ArrayList<String> addTitlesToFile(String target) {
		ArrayList<String> output = new ArrayList<String>();
		for (String fileName: this.allFiles) {
			File file = new File(fileName);

			PDDocument doc = null;
			try {
				doc = PDDocument.load(file);
				
				if (target.toLowerCase().equals("open")) {
					output.add(doc.getDocumentInformation().getTitle());
				}
				else {
					BufferedWriter bw = new BufferedWriter(
							new FileWriter(
									new File(target).getAbsolutePath(), true));
					
					bw.write(doc.getDocumentInformation().getTitle());
					bw.newLine();
					            
		            bw.close();
				}
	            doc.close();

			} catch (InvalidPasswordException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return output;
		
//		File[] dir = new File(
//        		"C:\\Users\\aaron\\Documents\\Year 5 University"
//        		+ "\\CSC428\\Project\\desktop\\pdfs").listFiles();
		
//		Arrays.sort(dir);    	
//		
//		int i = 0;
//		int lastFile = 0;
//		
//    	if (fileIndexes.contains("-")) {
//    		i = Integer.parseInt(fileIndexes.split("-")[0]);
//    		lastFile = Integer.parseInt(fileIndexes.split("-")[1]) + 1;
//    	}
//    	else if (fileIndexes.toLowerCase().equals("all")) {
//    		lastFile = dir.length;
//    	}
//    	else {
//    		i = Integer.parseInt(fileIndexes) - 1;
//    		lastFile = Integer.parseInt(fileIndexes);
//    	}

//    	for (int firstFile = i; firstFile < lastFile; firstFile++) {
//    		File file = dir[firstFile];
//    		System.out.println(file);
//			PDDocument doc = null;
//			try {
//				doc = PDDocument.load(file);
//				BufferedWriter bw = new BufferedWriter(
//						new FileWriter(
//								new File(target).getAbsolutePath(), true));
//				
//				bw.write(doc.getDocumentInformation().getTitle());
//				bw.newLine();
//				
//	            System.out.println(doc.getDocumentInformation().getTitle());
//	            
//	            bw.close();
//	            doc.close();
//			} catch (InvalidPasswordException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//    	}
	}
	
//	  public static void main(String arg[]) {
//		  pdfView addText = new pdfView(new ArrayList<String>(), );
//	  }

}

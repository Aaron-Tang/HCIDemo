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

@SuppressWarnings("serial")
public class pdfView extends JFrame{

	JTextField sourceFiles, destFile;
	JLabel label1, label2;
	JPanel panel, textPanel;
	JButton button1;
	protected ArrayList<String> allFiles = new ArrayList<String>();
	Font font1 = new Font("SansSerif", Font.PLAIN, 20);
	String lastDest;
	JNotepad notepad;
	
	
	public pdfView(ArrayList<String> allFiles, JNotepad main_notepad) {
		this.allFiles = allFiles;
		this.notepad = main_notepad;
	    setTitle("Add Titles to Text File");
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setSize(375,400);
	    setLocation(200,300);

	    setResizable(false);
	    
	    
	    panel = new JPanel(new BorderLayout());
	    textPanel = new JPanel();
	    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
	    add(panel);
	    panel.add(textPanel, BorderLayout.CENTER);
	    
	    destFile = new JTextField();
	    destFile.setFont(font1);
	    
	    label2 = new JLabel("Enter the destination text file below");
	    label2.setFont(font1);
	    
	    textPanel.add(label2);
	    textPanel.add(destFile);
	    
	    button1 = new JButton("OK");
	    button1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		String targetFile = destFile.getText();
	    		
	    		addTitlesToFile(targetFile);
	    	}
	    });
	    panel.add(button1, BorderLayout.SOUTH);
	    
	    setVisible(true);
	}
	
	
	public ArrayList<String> addTitlesToFile(String target) {
		ArrayList<String> output = new ArrayList<String>();
		System.out.println(output);
		for (String fileName: this.allFiles) {
			System.out.println(fileName);
			File file = new File(fileName);

			if (file.getName().endsWith(".pdf")) {
				PDDocument doc = null;
				try {

					if (target.toLowerCase().equals("open")) {
						System.out.println("THIS");
						doc = PDDocument.load(file);
						this.notepad.addToTextArea(doc.getDocumentInformation().getTitle() + "\n");
						doc.close();
					}
					else if (!new File(target).exists() || !target.endsWith(".txt")) {
						JOptionPane.showMessageDialog(null, 
								"The destination file is incorrect!",
								"Not A Valid File",
								JOptionPane.WARNING_MESSAGE);
						return output;
					}
					else {
						doc = PDDocument.load(file);
						BufferedWriter bw = new BufferedWriter(
								new FileWriter(
										new File(target).getAbsolutePath(), true));

						bw.write(doc.getDocumentInformation().getTitle());
						bw.newLine();

						bw.close();
						doc.close();
					}
					lastDest = target;
					System.out.println("lastdest" + lastDest);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("HERE");
					JOptionPane.showMessageDialog(null, 
							"The PDF file can't be opened!",
							"File Can't be Opened",
							JOptionPane.ERROR_MESSAGE);
					//					e1.printStackTrace();
					return output;
				}	
			}
			else {
				JOptionPane.showMessageDialog(null, 
						"The file isn't a PDF!",
						"Not A PDF",
						JOptionPane.WARNING_MESSAGE);
				return output;
			}
			
			
			
		}
		return output;		
	}
}

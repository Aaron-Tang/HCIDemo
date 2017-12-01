import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.*;

import javax.swing.*;

public class fileView extends JFrame{
	
	Map<String, String[]> conversions = new HashMap<String, String[]>();
	JTextField sourceFiles, URL;
	JLabel label1, label2;
	JPanel panel, textPanel;
	JButton button1;
	
	public fileView (){
		conversions.put("family guy", new String[] {"\\Family.Guy.S13E13.The Simpsons Guy.avi"});
		conversions.put("game of thrones", new String[] {
				"\\Game.of.Thrones.S06E07.The Broken Man.mp4", 
				"\\Game.of.Thrones.S06E08.No One.mp4"});
		conversions.put("monk", new String[] {
				"\\Monk.S01E01.Mr. Monk and the Candidate Part 1.mp4",
				"\\Monk.S01E01.Mr. Monk and the Candidate Part 2.avi",
				"\\Monk.S01E03.Mr. Monk and the Psychic.avi",
				"\\Monk.S01E04.Mr. Monk Meets Dale the Whale.mp4",
				"\\Monk.S01E07.Mr. Monk and the Billionaire Mugger.avi",
				"\\Monk.S01E08.Mr. Monk and the Other Woman.avi",
				"\\Monk.S01E09.Mr. Monk and the Marathon Man.avi",
				"\\Monk.S02E01.Mr. Monk Goes Back to School.avi",
				"\\Monk.S02E02.Mr. Monk Goes to Mexico.mp4",
				"\\Monk.S03E10.Mr. Monk and the Red Herring.mp4"
		});
		conversions.put("the big bang theory", new String[] {
				"\\The.Big.Bang.Theory.S03E05.The Creepy Candy Coating Corollary.avi",
				"\\The.Big.Bang.Theory.S03E08.The Adhesive Duck Deficiency.avi",
				"\\The.Big.Bang.Theory.S03E10.The Gorilla Experiment.avi"
		});
		
		setTitle("Change File Names");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(700,250);
	    setResizable(false);
	    
		Font font1 = new Font("SansSerif", Font.PLAIN, 20);
	    
	    panel = new JPanel(new BorderLayout());
	    textPanel = new JPanel();
	    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
	    add(panel);
	    panel.add(textPanel, BorderLayout.CENTER);
	    
	    sourceFiles = new JTextField(10);
	    sourceFiles.setFont(font1);
	    
	    URL = new JTextField(10);
	    URL.setFont(font1);
	    
	    label1 = new JLabel("Which TV series would you like to rename: \n");
	    label1.setFont(font1);
	    
	    label2 = new JLabel("Enter the URL for the show titles here: ");
	    label2.setFont(font1);
	    
	    textPanel.add(label1);
	    textPanel.add(sourceFiles);
	    
	    textPanel.add(label2);
	    textPanel.add(URL);
	    
	    button1 = new JButton("OK");
	    button1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		String tvSeries = sourceFiles.getText().toLowerCase();

	    		renameFiles(tvSeries);	    		
	    	}
	    });
	    panel.add(button1, BorderLayout.SOUTH);
	    
	    pack();
	    setVisible(true);
	}
	
	public void renameFiles(String tvSeries) {
		File[] dir = new File(
        		"C:\\Users\\aaron\\Videos").listFiles();
		
		Arrays.sort(dir);    
		
		int i = 1;
		int lastFile = 0;
		
		if (tvSeries.equals("family guy")){
    		lastFile = 2;
		}
		else if (tvSeries.equals("game of thrones")) {
			i = 2;
			lastFile = 4;
		}
		else if (tvSeries.equals("monk")) {
			i = 4;
			lastFile = 14;
		}
		else if (tvSeries.equals("the big bang theory")){
			i = 14;
			lastFile = 17;
		}
		
		else {
			System.out.println("No Files Found");
			return;
		}
		int counter = 0;
		for (int firstFile = i; firstFile < lastFile; firstFile++) {
    		File file = dir[firstFile];
    		
        	String absolutePath = file.getAbsolutePath();
        	String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
        	
//    		System.out.println(filePath);
        	File file2 = new File(filePath + conversions.get(tvSeries)[counter]);

//        	System.out.println(filePath + conversions.get(tvSeries)[counter]);
//    		System.out.println(conversions.get(tvSeries)[counter]);
        	
        	boolean success = file.renameTo(file2);
//        	System.out.println(success);
    		counter += 1;
		}
	}
	
	public static void main(String arg[]) {
		  fileView changeNames = new fileView();
	  }
}

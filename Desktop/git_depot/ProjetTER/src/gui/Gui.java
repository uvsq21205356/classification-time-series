package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.JFileChooser;

public class Gui extends JFrame implements ActionListener  {
	
	JTextPane t = new JTextPane(); 
	JCheckBox C=new JCheckBox();
	JCheckBox C1=new JCheckBox();
	JCheckBox C2=new JCheckBox();
	JRadioButton R1=new JRadioButton("Shapelets lenght");
	JRadioButton R2=new JRadioButton("perc parameter");
	 JButton b =new JButton("extract shapelets");
	 JButton b1=new JButton ("visualization");
	 JLabel L1= new JLabel("information:");
	 JLabel L2=new JLabel("options");
	 JLabel L3=new JLabel("minimal lenght");
	 JLabel L4=new JLabel("maximal lenght");
	 JLabel L5=new JLabel("last line of classification");
	 JLabel L6=new JLabel("test mod");
	 JLabel L7=new JLabel("normal execution");
	 JLabel L8=new JLabel("sup :");
	 JLabel L9=new JLabel("inf :  ");
	 JPanel P1=new JPanel ();
	 JPanel P2=new JPanel ();
	 JPanel P3=new JPanel();
	 JPanel P4=new JPanel();
	 JTextField T1=new JTextField("dbl between 0 and 1");
	 JSpinner min=new JSpinner();
	 JSpinner max=new JSpinner();
	 JSpinner sup =new JSpinner();
	 JSpinner inf =new JSpinner();
	 JSpinner taux= new JSpinner();
	 
	 JSeparator sep1=new JSeparator();
	 JMenuItem s1 =new JMenuItem("selectTraining Dataset");
	 JMenuItem s2 =new JMenuItem("quit");
	 JMenuItem s3 =new JMenuItem("clear Information");
	 JMenuItem s4=new JMenuItem("Show an MTS ");
	 ButtonGroup G;
	 private Box B1,B2,B3,B4,B5,B6,B7,B8,B9,B10,BA,BB,BC,BD,BE,BF,B11;
	  //LE MENU 
	  private JMenuBar menuBar = new JMenuBar();
	  JMenu   file = new JMenu("file"),
	    edit = new JMenu("Edit");
	

	  JMenuItem   neww = new JMenuItem("new"),
	    open = new JMenuItem("open");
	    
	 //
	  Border cadre = BorderFactory.createTitledBorder("information");
	 Border cadre2=BorderFactory.createTitledBorder("Option");
	 Border cadre3=BorderFactory.createTitledBorder("Test");
	 Border cadre4 =BorderFactory.createTitledBorder("Execution");
	private char[][] args;
	
	
	
	
	
	
	public Gui (){
		
	this.setTitle("test");
	this.setSize(500, 500);
	this.setVisible(true);
	this.setDefaultCloseOperation(3);
	Container c= getContentPane();
	c.setLayout(new FlowLayout());
	B1=B1.createHorizontalBox();
	B2=B2.createHorizontalBox();
	B3=B3.createHorizontalBox();
	B4=B4.createHorizontalBox();
	B5=B5.createHorizontalBox();
	B6=B6.createHorizontalBox();
	B7=B7.createHorizontalBox();
	B8=B8.createHorizontalBox();
	B9=B9.createHorizontalBox();
	B10=B10.createHorizontalBox();
	B11=B11.createHorizontalBox();
	BA=BA.createVerticalBox();
	BB=BB.createVerticalBox();
	BF=BF.createVerticalBox();
	BC=BC.createHorizontalBox();
	BD=BD.createVerticalBox();
	BE=BE.createVerticalBox();
	G=new ButtonGroup();
	/*Menu*/
	
	
	file.add(s1);
	file.add(s4);
	file.add(s2);
	edit.add(s3);
	
	menuBar.add(file);
	menuBar.add(edit);
	this.setJMenuBar(menuBar);
	s1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK));
	s2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,Event.CTRL_MASK));
	s3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK));
	s4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK));
	
	s1.addActionListener(this);
	//panel 1*/
	
	P1.setLayout(new FlowLayout());
	t.setSize(40,50);
	P1.add(t);
	t.setEnabled(false);
	P1.setBorder(cadre);
	t.setText("                                              ");
	
	
	/* panel2*/
	
	P2.setLayout(new FlowLayout());
	P2.setBorder(cadre2);
	min.setValue(100);
	max.setValue(100);
	BA.add(L3);
	BA.add(min);
	BA.add(L4);
	BA.add(max);
	BA.add(sep1);
	B1.add(L5);
	B1.add(C);
	BA.add(B1);
	P2.add(BA);
	
	
	/*panel3*/
	P3.setLayout(new FlowLayout());
	P3.setBorder(cadre3);
	G.add(R2);
	G.add(R1);
	B9.add(L6);
	B9.add(C1);
	
	B5.add(R1);

	B6.add(R2);
	B7.add(L9);
	B7.add(inf);
	B8.add(L8);
	B8.add(sup);
	BD.add(B5);
	BD.add(B6);
	BD.add(B7);
	BD.add(B8);
	
	BE.add(B9) ;
	BE.add(BD);
	P3.add(BE);
	BC.add(P3);
	inf.setEnabled(false);
	sup.setEnabled(false);
	R1.setSelected(true);
	R1.setEnabled(false);
	R2.setEnabled(false);
	C1.addActionListener(this);
	
	R1.addActionListener(this);
	/*panel4*/
	T1.setEnabled(false);
	P4.setLayout(new FlowLayout());
	P4.setBorder(cadre4);
	B10.add(L7);
	B10.add(C2);
	BF.add(B10);
	BF.add(T1);
	P4.add(BF);
	BC.add(P4);
	
	R2.addActionListener(this);
	/* Box final */
	BB.add(P1);
	BB.add(P2);
	B11.add(b);
	B11.add(b1);
	BB.add(B11);
	BB.add(BC);
	C2.addActionListener(this);
	c.add(BB);
	
	
	
	
	
	
	
	
}
	
	 
	
	
	public static void main(String[] args) {
	JFrame F=new Gui();
	

	}




	public void actionPerformed(ActionEvent e) {
		boolean state=true;
		if(e.getSource()==s1){
			JFileChooser dialogue = new JFileChooser(new File("."));
			PrintWriter sortie = null;
			File fichier;
			
			if (dialogue.showOpenDialog(null)== 
			    JFileChooser.APPROVE_OPTION) {
			    fichier = dialogue.getSelectedFile();
			    try {
					sortie = new PrintWriter
					(new FileWriter(fichier.getPath(), true));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    args = null;
				sortie.println(args[0]);
			    sortie.close();
			}
		}
		else if(e.getSource()==R1){
			inf.setEnabled(false);
			sup.setEnabled(false);
		
			
		}
		else if(e.getSource()==R2){
			inf.setEnabled(true);
			sup.setEnabled(true);
			
			
			
		}
		else if (e.getSource()==C2){
			if(C2.isSelected()==true)
			T1.setEnabled(true);
			else
				T1.setEnabled(false);
			
		
		}
		else if(e.getSource()==C1){
			if(C1.isSelected()==true){
				R1.setEnabled(true);
				R2.setEnabled(true);
			}
				else{
					R1.setEnabled(false);
				R2.setEnabled(false);
			}
				}
		}
	}


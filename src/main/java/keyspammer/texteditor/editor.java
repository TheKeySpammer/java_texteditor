package keyspammer.texteditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

public class editor implements ActionListener {

    JTextArea t;

    JFrame f;

    // Constructor
    editor() {
        t = new JTextArea();
        f = new JFrame("Editor");

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
            System.err.println("Theme setting error");
        }

        this.createMenuBar();

        f.add(t);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(500, 500);
        f.setVisible(true);

    }

    private void createMenuBar() {
        // Adding a menu bar
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");

        JMenuItem m11 = new JMenuItem("New");
        JMenuItem m12 = new JMenuItem("Open");
        JMenuItem m13 = new JMenuItem("Save");
        JMenuItem m14 = new JMenuItem("Print");

        m11.addActionListener(this);
        m12.addActionListener(this);
        m13.addActionListener(this);
        m14.addActionListener(this);

        m1.add(m11);
        m1.add(m12);
        m1.add(m13);
        m1.add(m14);

        JMenu m2 = new JMenu("Edit");

        JMenuItem m21 = new JMenuItem("Cut");
        JMenuItem m22 = new JMenuItem("Copy");
        JMenuItem m23 = new JMenuItem("Paste");

        m21.addActionListener(this);
        m22.addActionListener(this);
        m23.addActionListener(this);

        m2.add(m21);
        m2.add(m22);
        m2.add(m23);

        JMenuItem mc = new JMenuItem("Close");
        mc.addActionListener(this);

        mb.add(m1);
        mb.add(m2);
        mb.add(mc);

        this.f.setJMenuBar(mb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equalsIgnoreCase("Cut")){
            this.t.cut();
        }
        else if(s.equalsIgnoreCase("Copy")){
            this.t.copy();
        }
        else if(s.equalsIgnoreCase("Paste")){
            this.t.paste();
        }
        else if(s.equalsIgnoreCase("New")){
            this.t.setText("");
        }
        else if(s.equalsIgnoreCase("Open")){
            JFileChooser fc = new JFileChooser("~/Desktop/");
            int choice = fc.showSaveDialog(null);
            if(choice == JFileChooser.APPROVE_OPTION){
                File fi = new File(fc.getSelectedFile().getAbsolutePath());
                try {
                    FileReader fr = new FileReader(fi);
                    String readString = "";
                    BufferedReader br = new BufferedReader(fr);
                    readString = br.readLine();
                    while((readString = br.readLine()) != null){
                        readString = readString + "\n";
                    }
                    t.setText(readString);
                    fr.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(this.f, evt.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(this.f, "User canceled");
            }
        }
        else if (s.equalsIgnoreCase("Save")){
            JFileChooser fc = new JFileChooser("~/Desktop/");
            int choice = fc.showSaveDialog(null);
            if (choice == JFileChooser.APPROVE_OPTION){
                File fi = new File(fc.getSelectedFile().getAbsolutePath());
                try{
                    FileWriter fw = new FileWriter(fi, false);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(this.t.getText());
                    bw.flush();
                    bw.close();
                }catch(Exception evt){
                    JOptionPane.showMessageDialog(this.f, evt.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this.f, "User canceled");
            }
        } else if (s.equalsIgnoreCase("print")){
            try{
                this.t.print();

            }catch(Exception evt){
                JOptionPane.showMessageDialog(this.f, evt.getMessage());
            }
        }
        else if (s.equalsIgnoreCase("close")){
            this.f.setVisible(false);
        }
    }

}
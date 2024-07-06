package cs;

import org.drjekyll.fontchooser.FontDialog;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextEditor extends JFrame implements ActionListener, KeyListener, DocumentListener {

    JTextArea userInputArea = new JTextArea();
    JScrollPane scrollPane = new JScrollPane(userInputArea);
    JMenuBar menu;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu fontMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    JMenuItem undoItem;
    JMenuItem redoItem;
    JMenuItem clearTextItem;
    JMenuItem fontItem;
    JMenuItem fontColorItem;


    protected TextEditor() {

        //====Frame Settings====//
        this.setVisible(true);
        this.setSize(800, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        this.setTitle("Text Editor");

        //====Elements====//

        userInputArea.setFont(new Font("Dialog", Font.PLAIN, 20));
        userInputArea.setLineWrap(true);
        userInputArea.setWrapStyleWord(true);

        scrollPane.setPreferredSize(new Dimension(600, 600));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


        //====Menu Bar====//

        menu = new JMenuBar();

        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        editMenu = new JMenu("Edit");
        undoItem = new JMenuItem("Undo");
        redoItem = new JMenuItem("Redo");
        clearTextItem = new JMenuItem("Clear Text");
        fontMenu = new JMenu("Font");
        fontItem = new JMenuItem("Select Font");
        fontColorItem = new JMenuItem("Select Font Color");

        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(clearTextItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        fontMenu.add(fontItem);
        fontMenu.add(fontColorItem);
        menu.add(fileMenu);
        menu.add(editMenu);
        menu.add(fontMenu);

        //====Event Listeners====//

        undoItem.addActionListener(this);
        redoItem.addActionListener(this);
        clearTextItem.addActionListener(this);
        userInputArea.addKeyListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        fontItem.addActionListener(this);
        fontColorItem.addActionListener(this);

        //====Add Elements====//
        this.setJMenuBar(menu);
        this.add(scrollPane);



    }


    public void undo(KeyEvent keyEvent){


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == fontItem){
            FontDialog dialog = new FontDialog((Frame)null,"Font Dialog Example",true);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            if(!dialog.isCancelSelected()){
                userInputArea.setFont(dialog.getSelectedFont());
            }
        }

        if(e.getSource() == fontColorItem){
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "", Color.black);

            userInputArea.setForeground(color);
        }

        if(e.getSource() == openItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("Documents"));

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;

                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()){
                        userInputArea.setText("");
                        while(fileIn.hasNextLine()){
                            String line = fileIn.nextLine() + "\n";
                            userInputArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileIn.close();
                }

            }
        }

        if(e.getSource() == saveItem){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("Documents"));

            int response = fileChooser.showSaveDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){

                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                PrintWriter fileOut = null;
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(userInputArea.getText());
                }catch (FileNotFoundException e1){
                    e1.printStackTrace();
                }finally{
                    fileOut.close();
                }
            }
        }
        if(e.getSource() == exitItem){
            System.exit(0);
        }

        if(e.getSource() == undoItem){

        }
        if(e.getSource() == undoItem){

        }
        if(e.getSource() == clearTextItem){
            userInputArea.setText("");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_X){
            deleteCurrentLine();
        }
    }


    private void deleteCurrentLine() {
        int caretPosition = userInputArea.getCaretPosition();
        try {
            int start = Utilities.getRowStart(userInputArea, caretPosition);
            int end = Utilities.getRowEnd(userInputArea, caretPosition);
            userInputArea.getDocument().remove(start, end - start);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}

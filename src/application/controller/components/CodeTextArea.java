/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller.components;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import application.model.C;


public class CodeTextArea extends CodeArea {

    private static String Theme = "default";
    private Pattern PATTERN;
    //private CodeArea this;
    private ExecutorService executor;
    private String filePath = "";
    public Scene scene;
    private C syntax;

   
    ArrayList<String> suggestions;
    private static final String COMMIT_ACTION = "commit";

    
    /**
     * *
     * Default Constructor
     */
    public CodeTextArea() {
        this("");
    }


 
 


    /**
     * *
     *
     * @param file String Path to file Reads file from given path and set
     * contents to SyntaxTextAreaFX
     */
    public CodeTextArea(String file) {
        filePath = file;
        // executor = Executors.newSingleThreadExecutor();
        executor = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setDaemon(true);
            return thread;
        });

      

        this.setParagraphGraphicFactory(LineNumberFactory.get(this));

       


       
       

       
        Subscription s = this.plainTextChanges().subscribe(tc -> {
            String removed = tc.getRemoved();
            String inserted = tc.getInserted();

            if (!removed.isEmpty() && inserted.isEmpty()) {
                // deletion
            } else if (!inserted.isEmpty() && removed.isEmpty()) {
                // insertion
            } else {
                // replacement
            }
        });

    }

    /**
     * *
     *
     * @param text sets text to SyntaxTextAreaFX
     */
    public void setText(String text) {

        this.replaceText(0, this.getText().length(), text);
        this.getUndoManager().forgetHistory();
        this.getUndoManager().mark();

    }

    /**
     *
     * @return Text as String from SyntaxTextAreaFX
     */
    /**
     * *
     *
     * @param text appends text to SyntaxTextAreaFX
     */
    public void appendText(String text) {
        this.appendText(text);

    }

    /**
     * **
     *
     * @return the Graphical Node, which can be added to Layout
     */
    public CodeArea getNode() {
        return this;
    }


    /**
     * *
     * saves the contents of SyntaxTextAreaFX to @FilePath
     */
    public void save() {
        if (filePath.trim().length() > 0) {
            try {
                write(new File(filePath), getText());
            } catch (IOException ex) {
                Logger.getLogger(CodeTextArea.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.err.println("File Path is Empty !!!");
        }
    }

    /**
     * *
     *
     * @param filePath saves content to filePath
     */
    public void saveAs(String filePath) {
        this.filePath = filePath;
        try {
            write(new File(filePath), getText());
        } catch (IOException ex) {
            Logger.getLogger(CodeTextArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 

   



    /**
     * *
     *
     * @param file File to be saved
     * @param text Content to write
     * @throws IOException if unable to find the file path
     */
    private void write(File file, String text) throws IOException {
        file.getParentFile().mkdirs();
        try (FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw)) {
            pw.print(text);
            pw.close();
            fw.close();
        }

    }


 

    

    

    


}

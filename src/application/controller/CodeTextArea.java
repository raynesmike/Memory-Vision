/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;


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

import application.controller.CodeTextArea.CONSTANTS.LANGS;

/**
 *
 * @author Nika
 */
public class CodeTextArea extends CodeArea {

    private static String Theme = "default";
    private Pattern PATTERN;
    //private CodeArea this;
    private ExecutorService executor;
    private String filePath = "", externalThemePath = "";
    public Scene scene;
    private Syntax syntax;

    public static enum Mode {
        INSERT, COMPLETION
    };

    private Mode mode = Mode.INSERT;
    ArrayList<String> suggestions;
    private static final String COMMIT_ACTION = "commit";

    public static class CONSTANTS {

        public static enum FILE_TYPES {
            as, adb, ads, forth, asp, am, awk,
            prg, bib, bsv, boo, c, cg, changelog,
            cmake, cobol, cpp, cxx, cc, C, h, csharp, css, cuda, d,
            def, desktop, diff, patch, rej, docbook, dosbatch, dot, dpatch,
            dtd, eiffel, erlang, fcl, fortran, fsharp, gap, gdblog,
            genie, glsl, gtkdoc, gtkrc, haddock, haskell, haskellliterate,
            html, idlexelis, imagej, ini, j, jade, java, javascript, json,
            julia, latex, lex, libtool, llvm, m, m4, makefile, Makefile, GNUmakefile, mallard, markdown,
            matlab, mediawiki, modelica, mxml, nemerle, nemo_action, netrexx,
            nsis, objj, ocaml, ocl, octave, ooc, opal, pascal, perl, php, pig,
            pkgconfig, po, protobuf, puppet, python, python3, r, rpmspec, ruby,
            rust, scala, scheme, scilab, sh, sparql, sql, sweave, systemverilog,
            text, txt, t2t, tcl, thrift, vala, vbnet, verilog, vhdl, xml, yacc, yaml
        };

        public static enum LANGS {
            actionscript, ada, ansforth94, asp,
            automake, awk, bennugd, bibtex,
            bluespec, boo, c, cg, changelog,
            chdr, cmake, cobol, cpp,
            csharp, css, csv,
            cuda, d, def, desktop,
            diff, docbook, dosbatch, dot,
            dpatch, dtd, eiffel, erlang,
            fcl, forth, fortran, fsharp,
            gap, gdb_log, genie, glsl,
            go, gtk_doc, gtkrc, haddock,
            haskell, haskell_literate, html, idl, idl_exelis,
            imagej, ini, j, jade, java,
            javascript, json, julia,
            latex, lex, libtool, llvm,
            lua, m4, makefile, mallard, markdown,
            matlab, mediawiki, meson, modelica,
            mxml, nemerle, nemo_action, netrexx,
            nsis, objc, objj, ocaml,
            ocl, octave, ooc, opal,
            opencl, pascal, perl, php,
            pig, pkgconfig, po, prolog,
            protobuf, puppet, python, python3,
            R, rpmspec, rst, ruby,
            rust, scala, scheme, scilab,
            sh, sml, sparql, sql,
            sweave, systemverilog, t2t, tcl,
            texinfo, text, thrift, vala, vbnet,
            verilog, vhdl, xml, xslt,
            yacc, yaml
        };

        public static String[] ALT_FILE_TYPES = new String[]{"4th", "", "c++"};

    }

    private static LANGS CodingStyle = LANGS.text;

    /**
     * *
     * Default Constructor
     */
    public CodeTextArea() {
        this("");
    }

    /**
     *
     * @return Theme Name as String
     */
    public static String getTheme() {
        return Theme;
    }

    /**
     * *
     *
     * @param Theme Name of theme as String, where Theme is the name of the
     * directory available in res/css/Theme/
     *
     */
    public static void setTheme(String Theme) {
        CodeTextArea.Theme = Theme;
    }

    /**
     * *
     * Sets theme to default
     */
    public static void setThemeDefault() {
        CodeTextArea.Theme = "default";
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

       


       
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(this::computeHighlightingAsync)
                .awaitLatest(this.richChanges())
                .filterMap(t -> {
                    if (t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(this::applyHighlighting);

       
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
     *
     * @return Path to External Theme
     */
    public String getExternalThemePath() {
        return externalThemePath;
    }

    /**
     * *
     *
     * @param externalThemePath sets external theme path
     */
    public void setExternalThemePath(String externalThemePath) {
        this.externalThemePath = externalThemePath;
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
     * @return get the Coding Style of SyntaxTextAreaFX
     */
    public LANGS getCodingStyle() {
        return CodingStyle;
    }

    /**
     * *
     *
     * @param CodingStyle sets the coding style of the SyntaxTextAreaFX
     */
    public void setCodingStyle(LANGS CodingStyle) {
        CodeTextArea.CodingStyle = CodingStyle;

      
            this.getStylesheets().add(CodeTextArea.class.getResource("../view/c.css").toExternalForm());
        
        System.out.println(""+this.getStylesheets().toString());
        loadLanguage();
        loadKeywordSuggestions();
        generatePattern();
    }

    /**
     * *
     *
     * @param CSScode add CSS code to SyntaxTextAreaFX
     */
    public void addStyleSheet(String CSScode) {
        this.getStylesheets().add(CSScode);
    }

    /**
     * **
     * Remove all CSS coding sheets
     */
    public void clearStyleSheets() {
        this.getStylesheets().clear();
    }

    /**
     * *
     *
     * @return all CSS file as a List of Strings
     */
    public ObservableList<String> getAllStyleSheets() {
        return this.getStylesheets();
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


    /**
     * *
     *
     * @return which computes the Highlighting
     */
    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = this.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    /**
     * *
     *
     * @param highlighting Apply Highlighting style to SyntaxTextAreaFX
     */
    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        this.setStyleSpans(0, highlighting);
    }

    /**
     * **
     *
     * @param text computes Highlighting on provided text
     * @return Collection
     */
    private StyleSpans<Collection<String>> computeHighlighting(String text) {
    	syntax = new Syntax(new C());
    	PATTERN = syntax.generatePattern();
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        //generatePattern();
        while (matcher.find()) {
            String styleClass
                    = getStyleClass(matcher);
            System.out.println(styleClass);
            /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }






    private void loadKeywordSuggestions() {
        suggestions = syntax.getKeywords();
    }

    private void loadLanguage() {
    	syntax = new Syntax(new C());
    }

    /**
     * *
     * @param
     *
     * @return s
     */
    private String getStyleClass(Matcher matcher) {

        return syntax.getStyleClass(matcher);
    }

    private void generatePattern() {

        PATTERN = syntax.generatePattern();
    }


}

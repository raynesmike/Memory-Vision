
package application.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import java.util.Collections;
/** 
 * Application Programming Project:
 * 
 * This class is responsible for defining the code text area's syntax highlighting
 * 
 * @author * 
 * - Tyler Mitchell
 * - Jamal Dabas
 * - Michael Raynes
 * UTSA CS 3443 Application Programming
 * Fall 2018 
 **/

public class C  {
	private Pattern ptrn;

    String KEYWORDS[] = new String[]{"break", "case", "continue", "default", "do", "else", "enum", "for",  "goto", "if", "return", "struct", "switch", "typedef", "union", "while"};
    String OPERATORS[] = new String[]{"sizeof", "typeof"};
    String TYPES[] = new String[]{"bool", "char", "double", "float", "int", "long",  "short", "signed", "size_t", "ssize_t", "unsigned", "void"};
    String STORAGE_CLASS[] = new String[]{"const", "static", "volatile"};
    String BOOLEAN[] = new String[]{"true", "false"};
    String COMMON_DEFINES[] = new String[]{"null", "MAX", "MIN", "true", "false"};
    String STANDARD_STREAMS[] = new String[]{"stdin", "stdout", "stderr"};

    
    /**
     * Generates pattern
     */
    public Pattern generatePattern() {
        Pattern pattern;
        String KEYWORDS_PATTERN;
        String OPERATORS_PATTERN;
        String TYPES_PATTERN;
        String STORAGE_CLASS_PATTERN;
        String BOOLEAN_PATTERN;
        String COMMON_DEFINES_PATTERN;
        String STANDARD_STREAMS_PATTERN;
        String STRING_PATTERN;
        String COMMENT_PATTERN;
        KEYWORDS_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
        OPERATORS_PATTERN = "\\b(" + String.join("|", OPERATORS) + ")\\b";
        TYPES_PATTERN = "\\b(" + String.join("|", TYPES) + ")\\b";
        STORAGE_CLASS_PATTERN = "\\b(" + String.join("|", STORAGE_CLASS) + ")\\b";
        BOOLEAN_PATTERN = "\\b(" + String.join("|", BOOLEAN) + ")\\b";
        COMMON_DEFINES_PATTERN = "\\b(" + String.join("|", COMMON_DEFINES) + ")\\b";
        STANDARD_STREAMS_PATTERN = "\\b(" + String.join("|", STANDARD_STREAMS) + ")\\b";
        STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
        COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

        pattern = Pattern.compile(
                "(?<KEYWORDS>" + KEYWORDS_PATTERN + ")"
                + "|(?<OPERATORS>" + OPERATORS_PATTERN + ")"
                + "|(?<TYPES>" + TYPES_PATTERN + ")"
                + "|(?<STORAGECLASS>" + STORAGE_CLASS_PATTERN + ")"
                + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
                + "|(?<COMMONDEFINES>" + COMMON_DEFINES_PATTERN + ")"
                + "|(?<STANDARDSTREAMS>" + STANDARD_STREAMS_PATTERN + ")"
                + "|(?<STRING>" + STRING_PATTERN + ")"
                + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
        );
        this.ptrn = pattern;
        return pattern;
    }
    
    /**
     * getter method
     * @return ptrn Pattern
     */
    public Pattern getPattern() {
		return ptrn;
    }
  
    /**
     * CSS/Design
     * @param matcher Matcher
     * @return Matcher.groups
     */
    public String getStyleClass(Matcher matcher) {
        return  matcher.group("KEYWORDS") != null ? "keywords"
                : matcher.group("OPERATORS") != null ? "operators"
                : matcher.group("TYPES") != null ? "types"
                : matcher.group("STORAGECLASS") != null ? "storage-class"
                : matcher.group("BOOLEAN") != null ? "boolean"
                : matcher.group("COMMONDEFINES") != null ? "common-defines"
                : matcher.group("STANDARDSTREAMS") != null ? "standard-streams"
                : matcher.group("STRING") != null ? "string"
                : matcher.group("COMMENT") != null ? "comment"
                : null;
    }

   /**
    * Gets keywords
    * @return keywordList ArrayList<String>
    */
    public ArrayList<String> getKeywords() {
        ArrayList<String> keywordList = new ArrayList<>();
        keywordList.addAll(Arrays.asList(KEYWORDS));
        keywordList.addAll(Arrays.asList(OPERATORS));
        keywordList.addAll(Arrays.asList(TYPES));
        keywordList.addAll(Arrays.asList(STORAGE_CLASS));
        keywordList.addAll(Arrays.asList(BOOLEAN));
        keywordList.addAll(Arrays.asList(COMMON_DEFINES));
        keywordList.addAll(Arrays.asList(STANDARD_STREAMS));
        Collections.sort(keywordList);
        return keywordList;
    }
    
    /**
     * computes Highlighting
     * @param text String
     * @return spansBuilder.create()
     */
    public StyleSpans<Collection<String>> computeHighlighting(String text){
    	Pattern PATTERN = generatePattern();
    	Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

    	while(matcher.find()) {
    		String styleClass = getStyleClass(matcher);
    		spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
    		
    	}
    	spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

}

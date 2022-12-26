package workshop;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PlaintextToHtmlConverter {
    String source;
    int i;
    List<String> result;
    List<String> convertedLine;
    String characterToConvert;

    public String toHtml() throws Exception {
        String htmlLines = basicHtmlEncode(read());
        return htmlLines;
    }

    protected String read() throws IOException {
        return new String(Files.readAllBytes(Paths.get("sample.txt")));
    }

    private String basicHtmlEncode(String source) {
        this.source = source;
        i = 0;
        result = new ArrayList<>();
        convertedLine = new ArrayList<>();
 

        while (i <= this.source.length()) {                   	
            characterToConvert = stashNextCharacter();
            convert();
        }
        addANewLine();
        String finalResult = String.join("<br />", result);
        return finalResult;
    }
    
    private void convert() {
    	if(characterToConvert == "<")
            convertedLine.add("&lt;");
        
        else if(characterToConvert == ">")
            convertedLine.add("&gt;");

        else if(characterToConvert == "&")   
            convertedLine.add("&amp;");

        else if(characterToConvert == "\n")
            addANewLine();
        else
        	convertedLine.add(characterToConvert);
    }

    private String stashNextCharacter() {
        char c = source.charAt(i);
        i += 1;
        return String.valueOf(c);
    }

    private void addANewLine() {
        String line = String.join("", convertedLine);
        result.add(line);
        convertedLine = new ArrayList<>();
    }
}
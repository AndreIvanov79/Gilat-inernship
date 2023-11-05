package students.examples.gg.fileprocessor;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Result {
	
    @JacksonXmlProperty(localName = "Game")
    private String game;

    @JacksonXmlProperty(localName = "Outcome")
    private String outcome;
    
    public Result() {};

}

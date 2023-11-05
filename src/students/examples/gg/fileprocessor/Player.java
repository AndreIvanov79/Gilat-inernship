package students.examples.gg.fileprocessor;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;



@JacksonXmlRootElement(localName = "Player")
public class Player {
    @JacksonXmlProperty(localName = "id")
    private String id;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "score")
    private int score;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Friends")
    private List<String> friends;

    @JacksonXmlProperty(localName = "type")
    private String type;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Results")
    private Map<String, String> results;

    @JacksonXmlProperty(localName = "avatar")
    private String avatar;

    @JacksonXmlProperty(localName = "profilePage")
    private String profilePage;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Scripts")
    private List<String> scripts;

    @JacksonXmlProperty(localName = "createdAt")
    private String createdAt;

    @JacksonXmlProperty(localName = "updatedAt")
    private String updatedAt;

    @JacksonXmlProperty(localName = "wasOnlineAt")
    private String wasOnlineAt;
    
    public Player () {};
    
    public Player(String id, String name, int score, 
    		List<String> friends, String type, Map<String, String> results, 
    		String avatar, String profilePage, List<String> scripts, 
    		String createdAt, String updatedAt, String wasOnlineAt) {
    	this.id = id;
    	this.name =name;
    	this.score = score;
    	this.friends = friends;
    	this.type = type;
    	this.results = results;
    	this.avatar = avatar;
    	this.profilePage = profilePage;
    	this.scripts =scripts;
    	this.createdAt =createdAt;
    	this.updatedAt =updatedAt;
    	this.wasOnlineAt = wasOnlineAt;
    };
    
    @Override
    public String toString() {
    	return "Player: "+id+", "+name+", "+score+", "+friends+", "+type+", "+results+", "+avatar+", "+profilePage+", "+scripts+", "+
    createdAt+", "+updatedAt+", "+wasOnlineAt+".";
    }

}


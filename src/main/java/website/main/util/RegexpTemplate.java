package website.main.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RegexpTemplate {
	FULLNAME("[A-Za-z]+ [A-Za-z]+"),
	EMAIL("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"),
	PHONE("373\\d{8}"),
	PASSWORDLENGTH("^.{8,}$"),
	TOKEN("^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}==|[A-Za-z0-9+\\/]{3}=)?$");
	
	private final String regex;

	RegexpTemplate(String regex) {
		this.regex = regex;
	}
	
	 public boolean validate(String input) {
	        Pattern pattern = Pattern.compile(regex);
	        Matcher matcher = pattern.matcher(input);
	        return matcher.matches();
	    }
}

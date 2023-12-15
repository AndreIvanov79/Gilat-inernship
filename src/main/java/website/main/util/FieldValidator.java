package website.main.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {
	private Map<String, RegexpTemplate> validators = new HashMap<>();

	public String validate(String field, String value) {
		populateMap();
		String message = null;
		for (Map.Entry<String, RegexpTemplate> entry : validators.entrySet()) {
			if (entry.getKey().equals(field)) {
				String key = entry.getKey();

				if (!validators.get(key).validate(value)) {
					message = "Entered " + field + " is not valid.";
				}
			}
		}

		return message;
	}
	
	public String confirmPasswordMatching(String password, String confirmPassword) {
		String message = null;
//		if (!password.equals(confirmPassword)) {
//			message = "Entered value doesn`t match to the password.";
//		}
		Pattern pattern = Pattern.compile(password);
        Matcher matcher = pattern.matcher(confirmPassword);
        if (!matcher.matches()) {
        	message = "Entered value doesn`t match to the password.";
        }
        return message;
	}
	
	private void populateMap() {
		validators.put("fullName", RegexpTemplate.FULLNAME);
		validators.put("email", RegexpTemplate.EMAIL);
		validators.put("phone", RegexpTemplate.PHONE);
		validators.put("password", RegexpTemplate.PASSWORDLENGTH);
		validators.put("token", RegexpTemplate.TOKEN);

	}
}

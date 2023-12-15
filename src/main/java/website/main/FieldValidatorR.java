package website.main;

public class FieldValidatorR {
	public String validateFullName(String value) {
        if (!value.matches("[A-Za-z]+ [A-Za-z]+")) {
            return "Full name must contain at least 2 words, only Latin characters";
        }
        return null;
    }
}

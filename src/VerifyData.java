import java.util.regex.Matcher; 
import java.util.regex.Pattern;
public class VerifyData {
	public String firstName,lastName,address,gender,phone,email,password;
	VerifyData(String[] test)
	{
		this.firstName=test[1];	
		this.lastName=test[2];	
		this.address=test[3];	
		this.gender=test[5];	
		this.phone=test[6];	
		this.email=test[7];	
		this.password=test[8];			
	}
	
	String startVerify() {
		String data="",str;
		
		//verify firstName
		str=validateName(firstName);
		if(!str.equals("true")) {
			data=data+"first name: "+str+" ";
		}
		
		//verify lastName
		str=validateName(lastName);
		if(!str.equals("true")) {
			data=data+"last name: "+str+" ";
		}
		
		//verify address
		str=validateAddress(address);
		if(!str.equals("true")) {
			data=data+"address: "+str+" ";
		}
		
		//verify phone
		str=validatePhone(phone);
		if(!str.equals("true")) {
			data=data+"Phone: "+str+" ";
		}
		
		//verify email
		str=validateEmail(email);
		if(!str.equals("true")) {
			data=data+"email: "+str+" ";
		}
		//verify password
		
		
		return data;
	}

	private String validateEmail(String email2) { 
        String regex = "^(.+)@(.+)$";  
        Pattern pattern = Pattern.compile(regex);  
          
        Matcher matcher = pattern.matcher(email2);  
        if(matcher.matches())
        	return "true";
        else
        	return "Email is not valid. It does not match email pattern";
	}

	private String validatePhone(String phone2) {
		
		try {
			int intValue = Integer.parseInt(phone2);
			int len=phone.length();
			if(len!=11) {
				return "Phone number is not 11 digit";
			}
			return "true";
		} 
		catch (NumberFormatException e) {
			return "The phone number is not a valid number. It is a string";
		}
		
	}

	private String validateAddress(String address2) {
		//checks if it is a string or number
		try {
			int intValue = Integer.parseInt(address2);
			return "The name is a number";
		} 
		catch (NumberFormatException e) {
			return "true";
		}
	}

	private String validateName(String firstName) {
		//checks if it contains special character or not
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(firstName);
        boolean isStringContainsSpecialCharacter = matcher.find();
        if(isStringContainsSpecialCharacter)
            return "It has special character";	
        
        //checks if it is a string or number
		try {
			int intValue = Integer.parseInt(firstName);
			return "The name is a number";
		} 
		catch (NumberFormatException e) {
			return "true";
		}
	}
	
}

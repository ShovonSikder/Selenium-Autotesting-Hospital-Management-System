import java.io.File;

public class Operation {
	String location;
	Operation(){
		
	}
	
	void setLocation(String location) {
		this.location=location;
	}
	
	void deleteSS() {
		File file
        = new File(location);
		if (file.delete()) {
			System.out.println("File deleted successfully");
		}
		else {
			System.out.println("Failed to delete the file");
		}
	}
}

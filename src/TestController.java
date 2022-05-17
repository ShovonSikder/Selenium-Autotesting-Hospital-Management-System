
public class TestController {
	
	public static void initiateTestSequence() throws InterruptedException {
		System.out.println("controller running... ok");
		System.out.print("Test Initiating in...  3");
		Thread.sleep(1000);
		System.out.print("\b2");
		Thread.sleep(1000);
		System.out.print("\b1");
		Thread.sleep(1000);
		System.out.print("\b0\n");
	}

	public static void main(String[] args) throws InterruptedException {

		initiateTestSequence();
		
		HospitalTest modules = new HospitalTest(SiteUrls.home);

//test done	//modules.home();
		//modules.addNewPatient();
		
//test done	// modules.login(SiteUrls.loginLinksMap.get("admin"));
//test done	//modules.login(SiteUrls.loginLinksMap.get("doctor"));
//test done	//modules.login(SiteUrls.loginLinksMap.get("patient"));
		
//test done	//modules.doctor();
//test done	//	modules.patient();
//test done	//	modules.admin();
		
		modules.initiateJira();
		
		

		
		
	}

}

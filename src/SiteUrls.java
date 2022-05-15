import java.util.HashMap;
import java.util.Map;

public class SiteUrls {
	public static String home = "http://localhost/myhospital/home.html";
	public static String patientReg = "http://localhost/myhospital/patientregform.php";
	public static String wellcomeScreen = "http://localhost/myhospital/wellcome.html";
	public static String patientDashboard="http://localhost/myhospital/patientdashboard.php";
	public static String doctorDashboard="http://localhost/myhospital/doctordashboard.php";
	public static String adminDashboard="http://localhost/myhospital/admindashboard.php";
	public static String patientLogin="http://localhost/myhospital/patientlogin.php";
	public static String doctorLogin="http://localhost/myhospital/doctorlogin.php";
	public static String adminLogin="http://localhost/myhospital/adminlogin.php";
	public static String doctorAppoint="http://localhost/myhospital/appointmentdoctor.php";
	public static String docAddVisti="http://localhost/myhospital/makevisitdoc.php";
	public static String docSarve="http://localhost/myhospital/doctorserve.php";
	public static String patientAppoint="http://localhost/myhospital/appointmentpatient.php";
	public static String patientVisit="http://localhost/myhospital/visitpatient.php";
	public static String patientBills="http://localhost/myhospital/billpatient.php";
	public static String visitInfo="http://localhost/myhospital/visits.php";
	public static String billInfo="http://localhost/myhospital/bills.php";
	public static String docList="http://localhost/myhospital/doctorlist.php";
	public static String admit="http://localhost/myhospital/admitted.php";
	public static String patientList="http://localhost/myhospital/patientlist.php";
	public static String rooms="http://localhost/myhospital/rooms.php";
	public static String disease="http://localhost/myhospital/disease.php";

	public static final Map<String, String> loginLinksMap = new HashMap<String, String>() {
		{
			put("admin", "//*[@id=\"display\"]/div[1]/div[3]/a[1]/button");
			put("doctor", "//*[@id=\"display\"]/div[1]/div[3]/a[2]/button");
			put("patient", "//*[@id=\"display\"]/div[1]/div[3]/a[3]/button");
		}
	};

}

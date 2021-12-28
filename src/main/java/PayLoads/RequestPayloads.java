package PayLoads;

public class RequestPayloads {

	public static String userLoginChecker()

	{
		return "{\r\n" + "    \"userIdentification\": \"ahmad.irfan\",\r\n"
				+ "    \"loginFieldType\": \"USER_NAME\",\r\n" + "    \"password\": \"Mandiri1\",\r\n"
				+ "    \"userAgent\": null,\r\n" + "    \"ipAddress\": null,\r\n" + "    \"latitude\": null,\r\n"
				+ "    \"longitude\": null,\r\n" + "    \"localTime\": null,\r\n" + "    \"timezone\": null\r\n"
				+ "}\r\n" + "";

	}
	public static String createUserBody(String username,String email)

	{
		return "{\r\n" + 
				"    \"name\": \""+username+"\",\r\n" + 
				"    \"gender\": \"male\",\r\n" + 
				"    \"email\": \""+email+"\",\r\n" + 
				"    \"status\": \"active\"\r\n" + 
				"}";

		
}
	public static String updateUserBody(String username,String email)

	{
		return "{\r\n" + 
				"    \"name\": \""+username+"\",\r\n" + 
				"    \"gender\": \"male\",\r\n" + 
				"    \"email\": \""+email+"\",\r\n" + 
				"    \"status\": \"active\"\r\n" + 
				"}";

		
}
	}
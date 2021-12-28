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
	public static String creditLimitPayload(String customerId)

	{
		return "{\r\n" + 
				"   \"customerID\":\""+customerId+"\",\r\n" + 
				"   \"pkNumber\":\"TEST_PK_NUMBER\",\r\n" + 
				"   \"pkCreationDate\": \"2021-10-16\",\r\n" + 
				"   \"creditLimitAmount\": 500000\r\n" + 
				"}";

	}
	public static String blockCreditLimitPayload(String customerId)

	{
		return "{\r\n" + 
				"\"customerID\":\""+customerId+"\",\r\n" + 
				"\"decision\": 2,\r\n" + 
				"\"creditLimitAmount\": 500000,\r\n" + 
				"\"blockCode\":\"BC01\",\r\n" + 
				"\"emailAddress\": \"\",\r\n" + 
				"\"phoneNumber\": \"\"\r\n" + 
				"}";

	}
	
	public static String updateCreditLimitPayload(String customerId)

	{
		return "{\r\n" + 
				"\"customerID\":\""+customerId+"\",\r\n" + 
				"\"decision\": 1,\r\n" + 
				"\"creditLimitAmount\": 2000000,\r\n" + 
				"\"blockCode\":\"\",\r\n" + 
				"\"emailAddress\": \"\",\r\n" + 
				"\"phoneNumber\": \"\"\r\n" + 
				"}";

	}
	public static String loanDirsbument(String bindingId, String TrxId )

	{
		return "{\r\n" + 
				" \"bindingId\": \""+bindingId+"\",\r\n" + 
				"  \"partnerTrxId\": \""+TrxId+"\",\r\n" + 
				"  \"disbursementAmount\": 500000,\r\n" + 
				"  \"term\": 3,\r\n" + 
				"  \"productType\": 1\r\n" + 
				"}\r\n" + 
				"";

	}
	
	
	public static String bindingAndRebinding(String customerId)

	{
		return "{\r\n" + 
				"    \"customerId\":\""+customerId+"\",\r\n" + 
				"    \"partnerId\":\"LKA\"\r\n" + 
				"\r\n" + 
				"}";

	}
	public static String invalidbinding(String customerId,String partnerCode)

	{
		return "{\r\n" + 
				"    \"customerId\":\""+customerId+"\",\r\n" + 
				"    \"partnerId\":\""+partnerCode+"\"\r\n" + 
				"\r\n" + 
				"}";

	}

	

	public static String lenderBody(String username, String email, String mobileNumber, String bankAccountNo)

	{
		return "{\r\n" + "\"userDisplayName\":\"QANt2h636\",\r\n" + "\"email\":\"" + email + "\",\r\n"
				+ "\"username\":\"" + username + "\",\r\n" + "\"mobileNumber\":\"" + mobileNumber + "\",\r\n"
				+ "\"deviceId\":\"\",\r\n" + "\"deviceToken\":\"\",\r\n" + "\"biometricKey\":\"\",\r\n"
				+ "\"roleId\":\"1fc2f162-c841-49e5-94f1-470697736cf9\",\r\n" + "  \"identityType\":\"ID CARD\",\r\n"
				+ "  \"identityNumber\":\"123\",\r\n" + "\"taxPayerIdentificationNumber\":\"thgdh7849\",\r\n"
				+ "\"legalEntityType\":\"Central Government\",\r\n" + "  \"birthPlace\":\"India\",\r\n"
				+ "\"gender\":\"Male\",\r\n" + "\"address\":500,\r\n" + "\"city\":\"Kab. Aceh Selatan\",\r\n"
				+ "\"province\":\"Papua\",\r\n" + "\"postalCode\":\"474006\",\r\n" + "\"religion\":\"Hindu\",\r\n"
				+ "  \"maritalStatus\":\"Married\",\r\n" + "  \"profession\":\"Legal Entity\",\r\n"
				+ "  \"professionField\":\"profes\",\r\n" + "  \"revenue\":\"20\",\r\n"
				+ "  \"workExperience\":\"STUDENTS_OR_NEVER_WORKING\",\r\n" + "  \"education\":\"Postgraduate\",\r\n"
				+ "  \"representativeName\":\"abc\",\r\n" + "  \"representativeIdentityNumber\":\"30\",\r\n"
				+ "  \"remarks\":\"lender creation\",\r\n" + "  \"tenantCode\":\"tenantLinkaja\",\r\n"
				+ "\"registrationDate\":\"2023-Apr-15 23:59:59\",\r\n" + "\"birthdate\":\"2025-Apr-15 23:59:59\",\r\n"
				+ "\"amlDttotChecking\":true,\r\n" + "\"flagRegisteredDigisign\":true,\r\n"
				+ "\"bankAccountNumber\": \"" + bankAccountNo + "\",\r\n" + "\"withdrawalAccountNumber\": \""
				+ bankAccountNo + "\",\r\n" + "\"branchOpeningLocationCode\": \"JAKARTA_KOTA\",\r\n"
				+ "\"idIssuingCity\": \"string\",\r\n" + "\"numberOfLoanChannelingAgreement\": \"string\",\r\n"
				+ "\"representativePosition\": \"string\",\r\n" + "\"numberOfPowerAttorneyP2P\": \"string\",\r\n"
				+ "\"sourceOfFund\": \"INVESTMENT\",\r\n" + "\"accountType\": \"RDL\",\r\n"
				+ "\"firstName\": \"Dinesh\",\r\n" + "\"lastName\": \"Sahu\",\r\n"
				+ "\"numberOfApprovalReceipt\": \"hut\"\r\n" + "}";

	}
	
	public static String createBorrowerBody(String username, String email, String mobileNumber,String identityNumber, String cifNumber)
	{
		return "{\r\n" + 
				"    \"customerAddresses\": [\r\n" + 
				"        {\r\n" + 
				"        \"addressType\": 1,\r\n" + 
				"        \"address\": \"Delhi\",\r\n" + 
				"        \"province\": \"Delhi\",\r\n" + 
				"        \"city\": \"Delhi\",\r\n" + 
				"        \"subDistrict\": \"Delhi\",\r\n" + 
				"        \"urbanVillage\": \"Delhi\",\r\n" + 
				"        \"dati_II\": \"0100\",\r\n" + 
				"        \"rt\": \"01\",\r\n" + 
				"        \"rw\": \"003\",\r\n" + 
				"        \"postalCode\": \"54413\"\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"        \"addressType\": 2,\r\n" + 
				"        \"address\": \"Noida\",\r\n" + 
				"        \"province\": \"Noida\",\r\n" + 
				"        \"city\": \"Noida\",\r\n" + 
				"        \"subDistrict\": \"Noida\",\r\n" + 
				"        \"urbanVillage\": \"Noida\",\r\n" + 
				"        \"dati_II\": \"0100\",\r\n" + 
				"        \"rt\": \"01\",\r\n" + 
				"        \"rw\": \"003\",\r\n" + 
				"        \"postalCode\": \"54413\"\r\n" + 
				"    }\r\n" + 
				"    ],\r\n" + 
				"   \"customerContacts\": [\r\n" + 
				"        {\r\n" + 
				" \"contactType\": \"2\",\r\n" + 
				" \"areaCode\": \"0212\",\r\n" + 
				" \"phoneNumber\": \""+mobileNumber+"\"\r\n" + 
				" }\r\n" + 
				" ],\r\n" + 
				" \"emergencyContactPerson\": {\r\n" + 
				" \"econName\": \"Test 4\",\r\n" + 
				" \"econAddress\": \"Delhi\",\r\n" + 
				" \"econMobileNumber\": \""+mobileNumber+"\",\r\n" + 
				" \"econGender\": \"L\",\r\n" + 
				" \"econRelationship\": 2\r\n" + 
				" },\r\n" + 
				" \"customerType\": \"I\",\r\n" + 
				" \"cifNumber\": \""+cifNumber+"\",\r\n" + 
				" \"savingAccNumber\": \"113550117505434\",\r\n" + 
				" \"identityType\": 1,\r\n" + 
				" \"identityNumber\": \""+identityNumber+"\",\r\n" + 
				" \"emailAddress\": \""+email+"\",\r\n" + 
				" \"nameBasedOnId\": \""+username+"\",\r\n" + 
				" \"nameWithoutSalutation\": \""+username+"\",\r\n" + 
				" \"gender\": \"L\",\r\n" + 
				" \"birthPlace\": \"Jakarta\",\r\n" + 
				" \"dob\": \"2021-09-14\",\r\n" + 
				" \"religion\": \"01\",\r\n" + 
				" \"educationType\": \"01\",\r\n" + 
				" \"taxNumber\": \"987656789865412\",\r\n" + 
				" \"nationality\": \"01\",\r\n" + 
				" \"countryCode\": \"AF\",\r\n" + 
				" \"jobTitleCode\": \"034\",\r\n" + 
				" \"jobTypeCode\": \"001\",\r\n" + 
				" \"companyName\": \"Bank Mandiri\",\r\n" + 
				" \"companyEconomicSector\": \"011110\",\r\n" + 
				" \"grossIncomePerMonth\": 5000.00,\r\n" + 
				" \"sourceOfIncome\": 1,\r\n" + 
				" \"numberOfDependents\": 3,\r\n" + 
				" \"relationWithBank\": \"0120\",\r\n" + 
				" \"customerGroupCode\": \"0010\",\r\n" + 
				" \"maritalStatus\": 1,\r\n" + 
				" \"spouseIdentityType\": 1,\r\n" + 
				" \"spouseIdentityNumber\": \"9876545678987654\",\r\n" + 
				" \"spouseName\": \"0987658789\",\r\n" + 
				" \"spouseGender\": \"L\",\r\n" + 
				" \"spouseDob\": \"2021-09-14\",\r\n" + 
				" \"perjanjianPisahHarta\": \"Y\",\r\n" + 
				" \"motherMaidenName\": \"niktest\",\r\n" + 
				" \"violatingBMPK\": \"Y\",\r\n" + 
				" \"exceedingBMPK\": \"Y\",\r\n" + 
				" \"creditEconomicSector\": \"011110\",\r\n" + 
				" \"residenceType\": \"001\",\r\n" + 
				" \"lengthOfStay\": 100,\r\n" + 
				" \"residenceOwnership\": \"001\"\r\n" + 
				"}";
	}

	public static String updateBorrowerUser(String customerId, String email,String mobileNumber,String identityNumber)

	{
		return "{\r\n" + 
				"\"customerAddresses\": [\r\n" + 
				"        {\r\n" + 
				"        \"addressType\": 3,\r\n" + 
				"        \"address\": \"Delhi\",\r\n" + 
				"        \"province\": \"Delhi\",\r\n" + 
				"        \"city\": \"Delhi\",\r\n" + 
				"        \"subDistrict\": \"Delhi\",\r\n" + 
				"        \"urbanVillage\": \"Delhi\",\r\n" + 
				"        \"dati_II\": \"0100\",\r\n" + 
				"        \"rt\": \"01\",\r\n" + 
				"        \"rw\": \"003\",\r\n" + 
				"        \"postalCode\": \"54413\"\r\n" + 
				"    }\r\n" + 
				"    ],\r\n" + 
				"    \"customerContacts\": [\r\n" + 
				"        {\r\n" + 
				"        \"contactType\": \"1\",\r\n" + 
				"        \"areaCode\": \"021\",\r\n" + 
				"        \"phoneNumber\": \""+mobileNumber+"\"\r\n" + 
				"    }\r\n" + 
				"    ],\r\n" + 
				"    \"emergencyContactPerson\": {\r\n" + 
				"        \"econName\": \"Test\",\r\n" + 
				"        \"econAddress\": \"Delhi\",\r\n" + 
				"        \"econMobileNumber\": \""+mobileNumber+"\",\r\n" + 
				"        \"econGender\": \"L\",\r\n" + 
				"        \"econRelationship\": \"2\"\r\n" + 
				"    },\r\n" + 
				"    \"customerID\": \""+customerId+"\",\r\n" + 
				"    \"emailAddress\": \""+email+"\",\r\n" + 
				"    \"nameWithoutSalutation\": \"Test User\",\r\n" + 
				"    \"birthPlace\": \"Jakarta\",\r\n" + 
				"    \"dob\": \"2021-08-20\",\r\n" + 
				"    \"gender\": \"P\",\r\n" + 
				"    \"religion\": \"02\",\r\n" + 
				"    \"educationType\": \"04\",\r\n" + 
				"    \"taxNumber\": \"792220992314000\",\r\n" + 
				"    \"nationality\": \"01\",\r\n" + 
				"    \"countryCode\": \"AF\",\r\n" + 
				"    \"jobTitleCode\": \"003\",\r\n" + 
				"    \"jobTypeCode\": \"001\",\r\n" + 
				"    \"companyName\": \"Bank Mandiri\",\r\n" + 
				"    \"companyEconomicSector\": \"011110\",\r\n" + 
				"    \"grossIncomePerMonth\": 5600000.00,\r\n" + 
				"    \"sourceOfIncome\": 1,\r\n" + 
				"    \"numberOfDependents\": 3,\r\n" + 
				"    \"maritalStatus\": \"1\",\r\n" + 
				"    \"spouseIdentityType\": \"1\",\r\n" + 
				"    \"spouseIdentityNumber\": \""+identityNumber+"\",\r\n" + 
				"    \"spouseName\": \"Lestari Subekti\",\r\n" + 
				"    \"spouseGender\": \"P\",\r\n" + 
				"    \"spouseDob\": \"2021-08-20\",\r\n" + 
				"    \"perjanjianPisahHarta\": \"Y\",\r\n" + 
				"    \"motherMaidenName\": \"Ratnasari\",\r\n" + 
				"    \"residenceType\": \"001\",\r\n" + 
				"    \"lengthOfStay\": 36,\r\n" + 
				"    \"residenceOwnership\": \"001\"\r\n" + 
				"}";
	}
	
	
	
	public static String partnerBody(String username, String email, String mobileNumber, String partnerShortName)

	{
		return "{\r\n" + 
				"\r\n" + 
				"\"userDisplayName\":\"QAtest14\",\r\n" + 
				"\"email\":\""+email+"\",\r\n" + 
				"\"username\":\""+username+"\",\r\n" + 
				"\"mobileNumber\":\""+mobileNumber+"\",\r\n" + 
				"\"deviceId\":\"2333\",\r\n" + 
				"\"deviceToken\":\"344\",\r\n" + 
				"\"biometricKey\":\"\",\r\n" + 
				"\"roleId\":\"64963e56-6876-11ea-bc55-0242ac130003\",\r\n" + 
				"\"firstName\": \"Devid\",\r\n" + 
				"\"lastName\": \"Gupta\",\r\n" + 
				"\"partnershortName\": \""+partnerShortName+"\",\r\n" + 
				"\"officeaddress1\":\"Jakarta,Indonesia\",\r\n" + 
				"\"officeaddress2\":\"Java Island\",\r\n" + 
				"\"factorywarehouseaddress1\":\"Sumatra Island\",\r\n" + 
				"\"factorywarehouseaddress2\":\"None\",\r\n" + 
				"\"legalEntity\":\"Cooperative\",\r\n" + 
				"\"numberofemployees\":\"51-200\",\r\n" + 
				"\"annualsales\":\"1200\",\r\n" + 
				"\"partnergiroaccount\":\"1200\",\r\n" + 
				"\"startagreementdate\":\"2021-06-29T09:09:20.485Z\",\r\n" + 
				"\"endagreementdate\":\"2021-06-29T09:09:20.485Z\",\r\n" + 
				"\"maxexposurelimit\":10,\r\n" + 
				"\"performancetrigger\":34,\r\n" + 
				"\"performancecap\":22,\r\n" + 
				"\"pricing\":\"10\",\r\n" + 
				"\"tenor\": 10,\r\n" + 
				"\"marketingprogramcode\": 30,\r\n" + 
				"\"setreviewdate\":\"2021-06-29T09:09:20.485Z\",\r\n" + 
				"\"additionalnotes\":\"abc\",\r\n" + 
				"\"taxpayeridnumber\":\"Txpayernone\",\r\n" + 
				"\"businessidnumber\":\"BIZ70811\",\r\n" + 
				"\"keyMan\":\"AlternatePartner\",\r\n" + 
				"\"establishmentdate\":\"2021-06-29T09:09:20.485Z\",\r\n" + 
				"\"representativename\":\"repre1\",\r\n" + 
				"\"representativeidnumber\":\"67898977\",\r\n" + 
				"\"representativephonenumber\":\"567886\",\r\n" + 
				"\"representativeemail\":\"representativeemail1@gmail.com\",\r\n" + 
				"\"industrytype\": \"FMCG\",\r\n" + 
				"  \"bindingExpiryDays\": 4,\r\n" + 
				"  \"accountNumber\": \"sjg\",\r\n" + 
				"  \"bankCode\": \"11\",\r\n" + 
				"  \"branchLocation\": \"aSAS\",\r\n" + 
				"  \"issuingCity\": \"aa\",\r\n" + 
				"  \"bindingAllowed\": true,\r\n" + 
				"  \"preferredNotificationLocale\": \"ss\"\r\n" + 
				"\r\n" + 
				"\r\n" + 
				" }";

	}

	public static String updatePartnerUser(String partnerUserId)

	{
		return "{\r\n" + 
				"  \"id\":\""+partnerUserId+"\",\r\n" + 
				"  \"partnerUpdateRequest\": {\r\n" + 
				"    \"partnershortName\": \"NikUp\",\r\n" + 
				"    \"officeaddress1\": \"NoidaUpdate\",\r\n" + 
				"    \"officeaddress2\": \"Puresoftware01\",\r\n" + 
				"    \"factorywarehouseaddress1\": \"NCR224\",\r\n" + 
				"    \"factorywarehouseaddress2\": \"Delhi24\",\r\n" + 
				"    \"legalEntity\": \"Individual\",\r\n" + 
				"    \"numberofemployees\": \"51-200\",\r\n" + 
				"    \"industrytype\":\"Otomotif\",\r\n" + 
				"    \"annualsales\": \"100000\",\r\n" + 
				"    \"partnergiroaccount\": \"abjkkii\",\r\n" + 
				"    \"endagreementdate\": \"2028-Apr-29 23:59:59\",\r\n" + 
				"    \"maxexposurelimit\": \"10\",\r\n" + 
				"    \"performancetrigger\": 30,\r\n" + 
				"    \"performancecap\": \"abcd\",\r\n" + 
				"    \"pricing\": \"123\",\r\n" + 
				"    \"tenor\": 0,\r\n" + 
				"    \"marketingprogramcode\": 9876,\r\n" + 
				"    \"setreviewdate\": \"2010-Apr-05 23:59:59\",\r\n" + 
				"    \"additionalnotes\": \"abh)(*87\",\r\n" + 
				"    \"keyMan\": \"123\",\r\n" + 
				"    \"representativename\": \"abh98*&%\",\r\n" + 
				"    \"representativeidnumber\": \"sbsm12kdjW;LDJL\",\r\n" + 
				"    \"representativephonenumber\": \"sbsmkdjWKNk777\",\r\n" + 
				"    \"representativeemail\":\"sbsmkdjW@gmail.com\"\r\n" + 
				"  }\r\n" + 
				"}\r\n" + 
				"";
	}
	
	
	
}
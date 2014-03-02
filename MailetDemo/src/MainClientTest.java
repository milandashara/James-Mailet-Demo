public class MainClientTest {
	public static void main(String[] args) throws Exception {
		// CREATE CLIENT INSTANCES
		MailClient milanClient = new MailClient("milan", "localhost");
		MailClient vijayClient = new MailClient("vijay", "localhost");
		

		// CLEAR EVERYBODY'S INBOX
		milanClient.checkInbox(MailClient.CLEAR_MESSAGES);
		//vijayClient.checkInbox(MailClient.CLEAR_MESSAGES);
		//blueClient.checkInbox(MailClient.CLEAR_MESSAGES);
		Thread.sleep(500); // Let the server catch up

		// SEND A COUPLE OF MESSAGES TO BLUE (FROM milan AND vijay)
		milanClient.sendMessage("vijay@localhost", "Testing blue from milan",
				"This is a test message");
		milanClient.sendMessage("vijay@localhost", "Testing blue from vijay",
				"This is a test message");
		Thread.sleep(5000); // Let the server catch up
		
		// LIST MESSAGES FOR BLUE (EXPECT MESSAGES FROM RED AND GREEN)
		vijayClient.checkInbox(MailClient.SHOW_AND_CLEAR);
	}
}
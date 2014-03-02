import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailClient extends Authenticator {
	public static final int SHOW_MESSAGES = 1;
	public static final int CLEAR_MESSAGES = 2;
	public static final int SHOW_AND_CLEAR = SHOW_MESSAGES + CLEAR_MESSAGES;

	protected String from;
	protected Session session;
	protected PasswordAuthentication authentication;

	public MailClient(String user, String host) {
		this(user, host, false);
	}

	public MailClient(String user, String host, boolean debug) {
		from = user + '@' + host;
		authentication = new PasswordAuthentication(user, user);
		Properties props = new Properties();
		props.put("mail.user", user);
		props.put("mail.host", host);
		props.put("mail.debug", debug ? "true" : "false");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");
		session = Session.getInstance(props, this);
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}

	public void sendMessage(String to, String subject, String content)
			throws MessagingException {
		System.out.println("SENDING message from " + from + " to " + to);
		System.out.println();
		MimeMessage msg = new MimeMessage(session);
		msg.addRecipients(Message.RecipientType.TO, to);
		msg.setSubject(subject);
		msg.setText(content);
		
		// second part (the image)
		 // first part (the html)
		// This mail has 2 part, the BODY and the embedded image
        MimeMultipart multipart = new MimeMultipart("image");
		BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(
           "D:\\source\\online exam\\workspace\\MailetDemo\\src\\image.png");

        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-Type", "image/png");
        

        // add image to the multipart
        multipart.addBodyPart(messageBodyPart);
		
		msg.setContent(multipart);
		Transport.send(msg);
	}

	public void checkInbox(int mode) throws MessagingException, IOException {
		if (mode == 0)
			return;
		boolean show = (mode & SHOW_MESSAGES) > 0;
		boolean clear = (mode & CLEAR_MESSAGES) > 0;
		String action = (show ? "Show" : "") + (show && clear ? " and " : "")
				+ (clear ? "Clear" : "");
		System.out.println(action + " INBOX for " + from);
		Store store = session.getStore();
		store.connect();
		Folder root = store.getDefaultFolder();
		Folder inbox = root.getFolder("inbox");
		inbox.open(Folder.READ_WRITE);
		Message[] msgs = inbox.getMessages();
		if (msgs.length == 0 && show) {
			System.out.println("No messages in inbox");
		}
		for (int i = 0; i < msgs.length; i++) {
			MimeMessage msg = (MimeMessage) msgs[i];
			if (show) {
				System.out.println("    From: " + msg.getFrom()[0]);
				System.out.println(" Subject: " + msg.getSubject());
				System.out.println(" Content: " + msg.getContent());
			}
			if (clear) {
				msg.setFlag(Flags.Flag.DELETED, true);
			}
		}
		inbox.close(true);
		store.close();
		System.out.println();
	}
}
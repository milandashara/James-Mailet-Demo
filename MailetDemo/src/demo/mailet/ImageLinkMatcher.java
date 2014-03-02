package demo.mailet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.james.transport.matchers.HasAttachment;
import org.apache.mailet.Mail;
import org.apache.mailet.MatcherConfig;

public class ImageLinkMatcher extends HasAttachment {

	/**
	 * Image attachment MIME type
	 */
	private static String contentTypeStartWith = "image/";

	/**
	 * Init method inherited from GenericMatcher
	 * 
	 * @param config
	 *            matcher configuration, if any
	 */
	public void init(MatcherConfig config) throws MessagingException {
		super.init(config);
	}

	/**
	 * Implement the match method in the GenericMatcher interface. See if the
	 * email has an attachment of type "image/".
	 * 
	 * @param mail
	 *            the email message
	 */
	public Collection match(Mail mail) throws MessagingException {
		List mmps = new ArrayList();
		
		MimeMultipart mmp;
		try {
			mmp = (MimeMultipart) mail.getMessage().getContent();
			for (int i = 0; i < mmp.getCount(); i++) {
				MimeBodyPart mbp = (MimeBodyPart) mmp.getBodyPart(i);
				if (mbp.getContentType().startsWith(contentTypeStartWith)) {
					mmps.add(mbp);
				}
			}
		} catch (IOException e) {
//			Put logger
//			e.printStackTrace();
		}

		return mmps;
	}
}
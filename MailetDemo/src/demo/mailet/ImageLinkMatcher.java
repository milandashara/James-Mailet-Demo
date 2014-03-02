package demo.mailet;
import java.io.IOException;
import java.util.Collection;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.james.transport.matchers.HasAttachment;
import org.apache.mailet.Mail;
import org.apache.mailet.MatcherConfig;

public class ImageLinkMatcher extends HasAttachment {

	/**
	 * GIF attachment MIME type
	 */
	private static String m_gifAttachmentMimeType = "image/gif";
	private static String m_jpegAttachmentMimeType = "image/jpeg";
	private static String m_pjpegAttachmentMimeType = "image/pjpeg";
	private static String m_pngAttachmentMimeType = "image/png";
	//private static String m_svgxmlAttachmentMimeType = "image/svg+xml";

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
	 * email has an attachment of type "image/gif".
	 * 
	 * @param mail
	 *            the email message
	 */
	public Collection match(Mail mail) throws MessagingException {
		boolean foundImageAttachment = false;

		MimeMultipart mmp;
		try {
			mmp = (MimeMultipart) mail.getMessage().getContent();
			for (int i = 0; i < mmp.getCount(); i++) {
				MimeBodyPart mbp = (MimeBodyPart) mmp.getBodyPart(i);
				if (mbp.getContentType().equals(m_gifAttachmentMimeType) | mbp.getContentType().equals(m_jpegAttachmentMimeType) | mbp.getContentType().equals(m_pjpegAttachmentMimeType) | mbp.getContentType().equals(m_pngAttachmentMimeType)) {
					foundImageAttachment = true;
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (foundImageAttachment) {
			// this message needs further processing
			return mail.getRecipients();
		} else {
			return null;
		}
	}
}
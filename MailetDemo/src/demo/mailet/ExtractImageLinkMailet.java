package demo.mailet;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

import org.apache.mailet.Mail;
import org.apache.mailet.MailetContext;
import org.apache.mailet.base.GenericMailet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractImageLinkMailet extends GenericMailet{
  private static final Logger logger = LoggerFactory.getLogger(ExtractImageLinkMailet.class);
  @Override
  public void service(Mail mail) throws MessagingException {
    log("ExtractImageLinkMailet :log via mailet logger with INFO level");
    logger.info("Log via slf4j with INFO level !!! Add log4j.logger.com.test=INFO, CONS, FILE in the log4j.properties");
    
    MailetContext mailetContext=getMailetContext();
    try {
		Object content=mail.getMessage().getContent();
		if(content instanceof Multipart)
		{
			Multipart mmp=(Multipart) content;
			for (int i = 0; i < mmp.getCount(); i++) {
				MimeBodyPart mbp = (MimeBodyPart) mmp.getBodyPart(i);
				mbp.saveFile("c:\\");
			}
		}
		
		log("ExtractImageLinkMailet :log via mailet logger with INFO level class name:"+content.getClass());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    logger.debug(" ExtractImageLinkMailet :Log via slf4j with DEBUG level !!! Add log4j.logger.com.test=DEBUG, CONS, FILE in the log4j.properties");
  }
}
package org.recap.ils;

import com.ceridwen.circulation.SIP.exceptions.InvalidFieldLength;
import com.ceridwen.circulation.SIP.exceptions.MandatoryFieldOmitted;
import com.ceridwen.circulation.SIP.exceptions.MessageNotUnderstood;
import com.ceridwen.circulation.SIP.messages.CheckOutResponse;
import com.ceridwen.circulation.SIP.messages.ItemInformationResponse;
import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by saravanakumarp on 28/9/16.
 */
public class PrincetonESIPConnectorUT extends BaseTestCase {

    @Autowired
    private PrincetonESIPConnector princetonESIPConnector;

    @Test
    public void lookupItem() throws MessageNotUnderstood, InvalidFieldLength, MandatoryFieldOmitted {
        ItemInformationResponse response = (ItemInformationResponse) princetonESIPConnector.lookupItem("PUL","32101077423406",new java.util.Date());
        assertEquals("32101077423406",response.getItemIdentifier());
        assertEquals("Bolshevism, by an eye-witness from Wisconsin, by Lieutenant A. W. Kliefoth ...",response.getTitleIdentifier());
    }

    @Test
    public void lookupUser() throws Exception {
        String patronIdentifier = "45678915";
        PatronInformationResponse response = (PatronInformationResponse) princetonESIPConnector.lookupUser(patronIdentifier);
        assertNotNull(response);
        assertFalse(response.getScreenMessage().equalsIgnoreCase("Patron barcode not found"));
    }

    @Test
    public void checkout() throws MessageNotUnderstood, InvalidFieldLength, MandatoryFieldOmitted {
        CheckOutResponse response = (CheckOutResponse) princetonESIPConnector.checkoutItem("32101077423406", "45678915");
        assertNotNull(response);
    }
}

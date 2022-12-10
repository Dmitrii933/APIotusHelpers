package ApiHelpersVersion.tests;

import ApiHelpersVersion.features.CustomMarshaller;
import ApiHelpersVersion.pojoSoap.xmlSoap.webservicesserver.NumberToDollars;
import ApiHelpersVersion.pojoSoap.xmlSoap.webservicesserver.NumberToDollarsResponse;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.ws.actions.SoapActionBuilder.soap;

public class FirstTestSoap extends TestNGCitrusSupport {

    private TestContext context;

    CustomMarshaller<Class<NumberToDollars>> ptxRq = new CustomMarshaller<>();
    CustomMarshaller<Class<NumberToDollarsResponse>> ptxRs = new CustomMarshaller<>();


    @Test(description = "Перевод доллара из цифры в фразу", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();
        run(soap()
                .client("soapClient")
                .send()
                .message()
                .body(ptxRq.convert(NumberToDollars.class,getNumberToDollarsRequest(),
                        "http://www.dataaccess.com/webservicesserver/","NumberToDollars"))
        );

        $(soap()
                .client("soapClient")
                .receive()
                .message()
                .body(ptxRs.convert(NumberToDollarsResponse.class,getNumberToDollarsResponse(),
                        "http://www.dataaccess.com/webservicesserver/","NumberToDollarsResponse"))
        );
    }

    public NumberToDollars getNumberToDollarsRequest() {
        NumberToDollars numberToDollars = new NumberToDollars();
        numberToDollars.setDNum(new BigDecimal("15"));
        return numberToDollars;
    }

    public NumberToDollarsResponse getNumberToDollarsResponse() {
        NumberToDollarsResponse numberToDollarsResponse = new NumberToDollarsResponse();
        numberToDollarsResponse.setNumberToDollarsResult("fifteen dollars");
        return numberToDollarsResponse;
    }
}

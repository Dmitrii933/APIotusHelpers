package ApiHelpersVersion.testMock;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.apache.tools.ant.taskdefs.Get;
import org.springframework.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import stub.GetKurs;
import stub.UserScore;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class GetKurs_Test extends TestNGCitrusSupport {

    {
        new GetKurs();


    }


    private TestContext context;
    private String name0 ="QA java";
    private int price0 = 15000;

    private String name1 ="Java";
    private int price1 = 12000;


    @BeforeClass
    public static void startWireMock() {
        configureFor(8080);
    }

    @Test(description = "Получение списка курсов", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();
        $(http()
                .client("restClientWire")
                .send()
                .get("/cource/get/all")
        );

        $(http()
                .client("restClientWire")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .validate(jsonPath()
                        .expression("$.[0].name", name0)
                        .expression("$.[0].price", price0)
                        .expression("$.[1].name", name1)
                        .expression("$.[1].price", price1)));

    }

    @AfterClass
    public static void tearDownMockServer() {
        //wireMockServer.stop();
    }
}

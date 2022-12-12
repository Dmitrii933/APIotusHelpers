package ApiHelpersVersion.testMock;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import stub.GetUserAll;
import stub.UserScore;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class AllUser_Test extends TestNGCitrusSupport {

    {
        new GetUserAll();
    }

    private TestContext context;
    private String name ="Test user";
    private String cource = "QA";
    private String email = "test@test.test";
    private int age = 23;

    @BeforeClass
    public static void startWireMock() {
        configureFor(8080);
    }

    @Test(description = "Получение списка пользователей", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();
        $(http()
                .client("restClientWire")
                .send()
                .get("user/get/all")
        );

        $(http()
                .client("restClientWire")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .validate(jsonPath()
                        .expression("$.name", name)
                        .expression("$.cource", cource)
                        .expression("$.email", email)
                        .expression("$.age", age)));
    }

}

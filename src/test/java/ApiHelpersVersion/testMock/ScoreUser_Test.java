package ApiHelpersVersion.testMock;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojoGet.Data;
import pojoGet.Support;
import pojoGet.User;
import pojoPost.UserRq;
import stub.GetUserStubWireMock;
import stub.UserScore;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class ScoreUser_Test extends TestNGCitrusSupport {
    {
        new UserScore();
    }

    private TestContext context;
    private String name ="Test user";
    private int score = 78;

    @BeforeClass
    public static void startWireMock() {
        configureFor(8080);
    }

    @Test(description = "Получение оценки пользователя", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();
        $(http()
                .client("restClientWire")
                .send()
                .get("user/get/" + context.getVariable("userId"))
        );

        $(http()
                .client("restClientWire")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .type("application/json")
                .validate(jsonPath()
                        .expression("$.name", name)
                        .expression("$.score", score))

        );
    }

    @AfterClass
    public static void tearDownMockServer() {
        //wireMockServer.stop();
    }


}

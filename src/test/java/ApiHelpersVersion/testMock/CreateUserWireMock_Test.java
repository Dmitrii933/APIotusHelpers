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

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class CreateUserWireMock_Test extends TestNGCitrusSupport {

    {
        new GetUserStubWireMock();
    }
   /* private static WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options()
           .port(5050));*/
    private TestContext context;

    @BeforeClass
    public static void startWireMock() {
       // wireMockServer.start();
      //  WireMock.configureFor("localhost", 5050);
        configureFor(8080);
    }

    @Test(description = "Получение пользователя", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();
        $(http()
                .client("restClientWire")
                .send()
                .get("users/" + context.getVariable("userId"))
        );

        $(http()
                        .client("restClientWire")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type("application/json")
                        .body(new ObjectMappingPayloadBuilder(getJsonData(), "objectMapper"))

        );
    }

    @AfterClass
    public static void tearDownMockServer() {
    //wireMockServer.stop();
   }

        public UserRq getJsonDataRq() {
            UserRq user = UserRq.builder()
                    .job("QA")
                    .name("Dmitriy")
                    .build();
            return user;
        }

    public User getJsonData() {
        Data data = Data.builder()
                .id(2L)
                .email("janet.weaver@reqres.in")
                .first_name("Janet")
                .last_name("Weaver")
                .avatar("https://reqres.in/img/faces/2-image.jpg")
                .build();

        Support support = Support.builder()
                .url("https://reqres.in/#support-heading")
                .text("To keep ReqRes free, contributions towards server costs are appreciated!")
                .build();

        User user = User.builder()
                .data(data)
                .support(support)
                .build();
        return user;
    }

}

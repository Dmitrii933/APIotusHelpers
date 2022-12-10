package ApiHelpersVersion.mock;

import ApiHelpersVersion.behavior.CreateUserBehavior;
import ApiHelpersVersion.pojoGet.Data;
import ApiHelpersVersion.pojoGet.Support;
import ApiHelpersVersion.pojoGet.User;
import ApiHelpersVersion.pojoPost.UserRq;
import ApiHelpersVersion.pojoPost.UserRs;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.validation.json.JsonMessageValidationContext.Builder.json;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class TestMock extends TestNGCitrusSupport {

    private TestContext context;

    @Test(description = "Получение информации о пользователе", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();




        $(http()
                .client("restClient")
                .send()
                .get("users/" + context.getVariable("userId"))
                .fork(true)
        );

        run(http()
                .server("restServer")
                .receive()
                .get());

        run(http()
                .server("restServer")
                .send()
                .response()
                .message()
                .contentType("application/json")
                .body("{\n" +
                        "    \"data\": {\n" +
                        "        \"id\": ${userId},\n" +
                        "        \"email\": \"janet.weaver@reqres.in\",\n" +
                        "        \"first_name\": \"Janet\",\n" +
                        "        \"last_name\": \"Weaver\",\n" +
                        "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "    },\n" +
                        "    \"support\": {\n" +
                        "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                        "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "    }\n" +
                        "}"));

        $(http()
                        .client("restClient")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type("application/json")
                        .body(new ObjectMappingPayloadBuilder(getJsonData(), "objectMapper"))

        );
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

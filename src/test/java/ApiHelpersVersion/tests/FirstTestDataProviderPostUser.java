package ApiHelpersVersion.tests;

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.validation.json.JsonMessageValidationContext.Builder.json;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class FirstTestDataProviderPostUser extends TestNGCitrusSupport {

    private TestContext context;

    @DataProvider(name="dataProvider")
    public  Object[][] cardTypeProvider(){
        return new Object[][]{
                new Object[]{"George","Bluth"},
                new Object[]{"Janet","Weaver"},
                new Object[]{"Emma","Wong"}
        };
    }

    @Test(description = "Создание пользователя", enabled = true,dataProvider = "dataProvider")
    @CitrusTest
    public void getTestActions(String name, String job) {
        this.context = citrus.getCitrusContext().createTestContext();

        $(http()
                .client("restClientReqres")
                .send()
                .post("users")
                .message()
                .type("application/json")
                .body("{\n" +
                        "    \"name\": \"" + name + "\",\n" +
                        "    \"job\": \"" + job + "\"\n" +
                        "}")
        );

        $(http()
                .client("restClientReqres")
                .receive()
                .response(HttpStatus.CREATED)
                .message()
                .type("application/json")
                .validate(json().ignore("$.createdAt").ignore("$.id"))
                .validate(jsonPath()
                        .expression("$.name", name)
                        .expression("$.job", job))
                .extract(fromBody()
                        .expression("$.id", "currentId")
                        .expression("$.createdAt","createdAt"))
        );
        $(echo("currentId=${currentId} and createdAt = ${createdAt}"));
    }
}

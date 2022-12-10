package ApiHelpersVersion.tests;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.json.JsonPathMessageValidationContext.Builder.jsonPath;

public class FirstTestDataProviderGetUser extends TestNGCitrusSupport {
    private TestContext context;

@DataProvider(name="dataProvider")
public  Object[][] typeProvider(){
    return new Object[][]{
            new Object[]{"1","George","Bluth"},
    new Object[]{"2","Janet","Weaver"},
    new Object[]{"3","Emma","Wong"}
    };
}
    @Test(description = "Получение информации о пользователе", enabled = true, dataProvider = "dataProvider")
    @CitrusTest
    public void getTestActions(String id,String name, String surname) {
        this.context = citrus.getCitrusContext().createTestContext();

        $(http()
                .client("restClientReqres")
                .send()
                .get("users/" + id)
        );

        $(http()
                        .client("restClientReqres")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type("application/json")
                        .validate(jsonPath()
                                .expression("$.data.id",id)
                .expression("$.data.first_name",name)
                .expression("$.data.last_name",surname)));
    }


    }

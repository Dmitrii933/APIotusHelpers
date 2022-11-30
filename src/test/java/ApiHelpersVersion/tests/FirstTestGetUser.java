package ApiHelpersVersion.tests;

import ApiHelpersVersion.pojoGet.Data;
import ApiHelpersVersion.pojoGet.Support;
import ApiHelpersVersion.pojoGet.User;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.TestNGCitrusSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.EchoAction.Builder.echo;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class FirstTestGetUser extends TestNGCitrusSupport {


    private TestContext context;

    @Test(description = "Получение информации о пользователе", enabled = true)
    @CitrusTest
    public void getTestActions() {
        this.context = citrus.getCitrusContext().createTestContext();

        context.setVariable("value", "superValue");

        $(echo("Property \"value\" = " + context.getVariable("value")));

        $(echo("We have userID = " + context.getVariable("userId")));
        $(echo("Property \"userId\" = " + "${userId}"));

        variable("now", "citrus:currentDate()");
        $(echo("Today id: ${now}"));


        $(http()
                .client("restClientReqres")
                .send()
                .get("users/" + context.getVariable("userId"))
        );

        $(http()
                        .client("restClientReqres")
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .type("application/json")
//        .body("{\n" +
//                "    \"data\": {\n" +
//                "        \"id\": 2,\n" +
//                "        \"email\": \"janet.weaver@reqres.in\",\n" +
//                "        \"first_name\": \"Janet\",\n" +
//                "        \"last_name\": \"Weaver\",\n" +
//                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
//                "    },\n" +
//                "    \"support\": {\n" +
//                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
//                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
//                "    }\n" +
//                "}")
//                .body(new ClassPathResource("user2.json"))
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

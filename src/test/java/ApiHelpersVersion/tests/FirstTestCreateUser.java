package ApiHelpersVersion.tests;

import ApiHelpersVersion.behavior.CreateUserBehavior;
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

public class FirstTestCreateUser extends TestNGCitrusSupport {

        private TestContext context;
    private String name = "Dmitriy";
    private String job = "QA";

        @Test(description = "Создание пользователя", enabled = true, invocationCount = 3)
        @CitrusTest
        public void getTestActions() {
            this.context = citrus.getCitrusContext().createTestContext();

run(applyBehavior(new CreateUserBehavior("Andrey", "QA", context)));

            $(http()
                    .client("restClientReqres")
                    .send()
                    .post("users")
                    .message()
                    .type("application/json")
                    .body(new ObjectMappingPayloadBuilder(getJsonDataRq(), "objectMapper"))
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
                    .body(new ObjectMappingPayloadBuilder(getJsonDataRs(), "objectMapper"))
            );
            $(echo("currentId=${currentId} and createdAt = ${createdAt}"));
        }

        public UserRq getJsonDataRq() {
            UserRq user = UserRq.builder()
                    .job("QA")
                    .name("Dmitriy")
                    .build();
            return user;
        }

    public UserRs getJsonDataRs() {
        UserRs user = UserRs.builder()
                .createdAt("${createdAt}")
                .id("${currentId}")
                .name("Dmitriy")
                .job("QA")
                .build();
        return user;
    }



    }



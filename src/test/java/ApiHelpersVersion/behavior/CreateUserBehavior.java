package ApiHelpersVersion.behavior;

import pojoPost.UserRs;
import com.consol.citrus.TestActionRunner;
import com.consol.citrus.TestBehavior;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import org.springframework.http.HttpStatus;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;
import static com.consol.citrus.validation.json.JsonMessageValidationContext.Builder.json;

public class CreateUserBehavior implements TestBehavior {

    private TestContext context;
    public String name;
    public String job;

    public CreateUserBehavior(String name, String job, TestContext contex) {
        this.context=contex;
        this.name = name;
        this.job = job;

    }

    @Override
    public void apply(TestActionRunner testActionRunner) {
testActionRunner.run(http()
        .client("restClientReqres")
        .send()
        .post("users")
        .message()
        .type("application/json")
        .body("{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + job + "\"\n" +
                "}"));

testActionRunner.run(http()
        .client("restClientReqres")
        .receive()
        .response(HttpStatus.CREATED)
        .message()
        .type("application/json")
        .extract(fromBody()
                .expression("$.id", "currentId")
                .expression("$.createdAt","createdAt"))
        .validate(json().ignore("$.createdAt").ignore("$.id"))
        .body(new ObjectMappingPayloadBuilder(getJsonDataRs(name,job), "objectMapper")));
    }

    public UserRs getJsonDataRs(String name, String job) {
        UserRs user = UserRs.builder()
                .createdAt("${createdAt}")
                .id("${currentId}")
                .name(name)
                .job(job)
                .build();
        return user;
    }
}

package stub;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

public class GetUserAll {

    {
        stubAllUser();
    }
    public static void stubAllUser() {
        stubFor(WireMock.get(WireMock.urlEqualTo("/user/get/all"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "\"name\":\"Test user\",\n" +
                                "\"cource\":\"QA\",\n" +
                                "\"email\":\"test@test.test\",\n" +
                                "\"age\": 23\n" +
                                "}")));
    }
}

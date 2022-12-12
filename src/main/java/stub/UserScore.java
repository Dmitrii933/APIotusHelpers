package stub;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
public class UserScore {
    {
        stubScoreUser();
    }
    public static void stubScoreUser() {
        stubFor(WireMock.get(WireMock.urlEqualTo("/user/get/2"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "\"name\":\"Test user\",\n" +
                                "\"score\": 78\n" +
                                "}")));
    }

}

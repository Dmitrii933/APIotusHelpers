package stub;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

public class GetKurs {

    {
        stubUGetKurs();
    }
    public static void stubUGetKurs() {
        stubFor(WireMock.get(WireMock.urlEqualTo("/cource/get/all"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("[\n" +
                                "{\n" +
                                "\"name\":\"QA java\",\n" +
                                "\"price\": 15000\n" +
                                "},\n" +
                                "{\n" +
                                "\"name\":\"Java\",\n" +
                                "\"price\": 12000\n" +
                                "}\n" +
                                "]")));
    }
}

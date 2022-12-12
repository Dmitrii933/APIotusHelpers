package stub;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

public class GetUserStubWireMock {

    {
        stubGetUser();
    }
    public static void stubGetUser() {
        stubFor(WireMock.get(WireMock.urlEqualTo("/api/users/2"))

                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withBody("{\n" +
                                "    \"data\": {\n" +
                                "        \"id\": 2,\n" +
                                "        \"email\": \"janet.weaver@reqres.in\",\n" +
                                "        \"first_name\": \"Janet\",\n" +
                                "        \"last_name\": \"Weaver\",\n" +
                                "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                                "    },\n" +
                                "    \"support\": {\n" +
                                "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                                "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                                "    }\n" +
                                "}")));
    }

}

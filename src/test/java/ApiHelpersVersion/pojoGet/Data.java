
package ApiHelpersVersion.pojoGet;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;

@lombok.Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {


    private String avatar;

    private String email;

    private String first_name;

    private Long id;

    private String last_name;


}


package ApiHelpersVersion.pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@lombok.Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {


    private Data data;

    private Support support;

}

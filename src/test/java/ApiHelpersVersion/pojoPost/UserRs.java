
package ApiHelpersVersion.pojoPost;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;

@lombok.Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRs {

    private String createdAt;

    private String id;

    private String job;

    private String name;


}

package ir.sobhan.model.output;
import ir.sobhan.model.entity.User;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;

@Getter
public class StudentOutputDTO extends OutPutDTO<User>{
    public StudentOutputDTO(User realObj) throws Exception {
        super(realObj);
    }
    public Long id;
    public String username;
    public StudentINFOutputDTO studentInf;
    public String name;
    public String phone;
    public String nationalId;
    public Boolean isActive;
    public Boolean isAdmin;
}

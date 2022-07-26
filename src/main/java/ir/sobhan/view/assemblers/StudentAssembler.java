package ir.sobhan.view.assemblers;

import ir.sobhan.model.entity.User;
import ir.sobhan.model.output.StudentOutputDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class StudentAssembler implements RepresentationModelAssembler<StudentOutputDTO, EntityModel<StudentOutputDTO>> {
    @Override
    public EntityModel<StudentOutputDTO> toModel(StudentOutputDTO entity) {
        return EntityModel.of(entity);
    }
}

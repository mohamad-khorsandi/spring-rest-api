package ir.sobhan.service.AbstractService;

import ir.sobhan.business.exception.EntityNotFoundException;
import ir.sobhan.service.AbstractService.model.input.InputDTO;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import ir.sobhan.service.courseSection.model.output.CourseSectionOutputDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Setter
abstract public class LCRUD<ENTITY, INPUT_DTO extends InputDTO<ENTITY>> extends CRUD<ENTITY, INPUT_DTO>{
    public LCRUD(JpaRepository<ENTITY, Long> repository, Class<? extends OutPutDTO<ENTITY>> outDTOClass) {
        super(repository, outDTOClass);
    }

    public LCRUD(JpaRepository<ENTITY, Long> repository, Class<? extends OutPutDTO<ENTITY>> outDTOClass, Predicate<ENTITY> getFilter, Consumer<ENTITY> postInitializer) {
        super(repository, outDTOClass, getFilter, postInitializer);
    }

    @GetMapping
    public ResponseEntity<?> list(){
        List<?> list = repository.findAll().stream().filter(getFilter)
                .map((entity -> {
                    try {
                        return toOutDTOModel(entity);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })).collect(Collectors.toList());

        CollectionModel<?> collectionModel = CollectionModel.of(list);
        return ResponseEntity.ok(collectionModel);
    }
}

package ir.sobhan.control;

import ir.sobhan.exception.EntityNotFoundException;
import ir.sobhan.model.input.InputDTO;
import ir.sobhan.model.output.OutPutDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RequiredArgsConstructor
abstract public class Controller <ENTITY, INPUT_DTO extends InputDTO<ENTITY>>{
    public Controller(JpaRepository<ENTITY, Long> repository, Class<? extends OutPutDTO<ENTITY>> outDTOClass, Predicate<ENTITY> getFilter, Consumer<ENTITY> postInitializer) {
        this.repository = repository;
        this.outDTOClass = outDTOClass;
        this.getFilter = getFilter;
        this.postInitializer = postInitializer;
    }

    final private JpaRepository<ENTITY, Long> repository;
    final private Class<? extends OutPutDTO<ENTITY>> outDTOClass;
    private Predicate<ENTITY> getFilter = entity -> true;
    private Consumer<ENTITY> postInitializer = entity -> {};
    String idFieldName = "id";

    @GetMapping
    ResponseEntity<?> list(){
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

    @GetMapping({"{id}"})
    ResponseEntity<?> read(@PathVariable Long id) throws Exception {
        ENTITY entity = repository.findById(id).filter(getFilter).orElseThrow(() -> new EntityNotFoundException(id));

        EntityModel<?> userEntityModel = toOutDTOModel(entity);
        return ResponseEntity.ok(userEntityModel);
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody INPUT_DTO inputEntity) throws Exception{
        ENTITY entity = inputEntity.toRealObj(null);

        postInitializer.accept(entity);
        repository.save(entity);

        EntityModel<?> entityModel = toOutDTOModel(entity);
        return ResponseEntity.created(linkTo(methodOn(Controller.class).read(getIdOfEntity(entity))).toUri()).body(entityModel);
    }

    @PutMapping("{id}")
    ResponseEntity<?> update(@RequestBody INPUT_DTO inputEntity, @PathVariable Long id) throws Exception {
        ENTITY entity = repository.findById(id).filter(getFilter)
                .orElseThrow(() -> new EntityNotFoundException(id));
        inputEntity.toRealObj(entity);

        repository.save(entity);

        EntityModel<?> entityModel = toOutDTOModel(entity);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private Long getIdOfEntity(ENTITY entity) throws IllegalAccessException, NoSuchFieldException {

        Field field = entity.getClass().getDeclaredField(idFieldName);
        field.setAccessible(true);
        return (Long) field.get(entity);
    }

    private EntityModel<? extends OutPutDTO<ENTITY>> toOutDTOModel(ENTITY entity) throws Exception {
        OutPutDTO<ENTITY> outPutDTO = outDTOClass.getConstructor(entity.getClass()).newInstance(entity);
        return (EntityModel<? extends OutPutDTO<ENTITY>>)outPutDTO.toModel();
    }
}

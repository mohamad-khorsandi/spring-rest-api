package ir.sobhan.service.AbstractService;

import ir.sobhan.business.DBService.DBService;
import ir.sobhan.business.exception.NotFoundException;
import ir.sobhan.service.AbstractService.model.input.InputDTO;
import ir.sobhan.service.AbstractService.model.output.OutPutDTO;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Setter
@RequiredArgsConstructor
abstract public class LCRUD<ENTITY, INPUT_DTO extends InputDTO<ENTITY>> {
    public LCRUD(DBService<ENTITY> dbService, Class<? extends OutPutDTO<ENTITY>> outDTOClass, Consumer<ENTITY> postInitializer) {
        this.dbService = dbService;
        this.outDTOClass = outDTOClass;
        this.postInitializer = postInitializer;
    }

    final protected DBService<ENTITY> dbService;
    final protected Class<? extends OutPutDTO<ENTITY>> outDTOClass;
    protected Consumer<ENTITY> postInitializer = entity -> {
    };
    String idFieldName = "id";

    @GetMapping
    public ResponseEntity<?> list(@PathParam("page-number") Integer pageNumber,
                                  @PathParam("page-size") Integer pageSize) {

        List<?> list = dbService.getList(pageNumber, pageSize)
                .stream().map(this::toOutDTOModel).collect(Collectors.toList());

        CollectionModel<?> collectionModel = CollectionModel.of(list);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping({"{id}"})
    ResponseEntity<?> read(@PathVariable Long id) throws Exception {
        ENTITY entity = dbService.getById(id);

        EntityModel<?> userEntityModel = toOutDTOModel(entity);
        return ResponseEntity.ok(userEntityModel);
    }

    @PostMapping
    ResponseEntity<?> create(@RequestBody INPUT_DTO dtoInputEntity) throws Exception {
        ENTITY entity = dtoInputEntity.toRealObj(null);

        postInitializer.accept(entity);

        dbService.save(entity);

        EntityModel<?> entityModel = toOutDTOModel(entity);
        return ResponseEntity.created(linkTo(methodOn(LCRUD.class).read(getIdOfEntity(entity))).toUri()).body(entityModel);
    }

    @PutMapping("{id}")
    ResponseEntity<?> update(@RequestBody INPUT_DTO inputEntity, @PathVariable Long id) throws Exception {
        ENTITY entity = dbService.getById(id);
        inputEntity.toRealObj(entity);

        dbService.save(entity);

        EntityModel<?> entityModel = toOutDTOModel(entity);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable Long id) throws NotFoundException {
        dbService.getById(id);
        dbService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private Long getIdOfEntity(ENTITY entity) throws IllegalAccessException, NoSuchFieldException {
        Field field = entity.getClass().getDeclaredField(idFieldName);
        field.setAccessible(true);
        return (Long) field.get(entity);
    }

    protected EntityModel<? extends OutPutDTO<ENTITY>> toOutDTOModel(ENTITY entity) {
        try {
            Constructor constructor = outDTOClass.getConstructor(entity.getClass());
            OutPutDTO<ENTITY> outPutDTO = (OutPutDTO) constructor.newInstance(entity);
            return (EntityModel<? extends OutPutDTO<ENTITY>>) outPutDTO.toModel();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

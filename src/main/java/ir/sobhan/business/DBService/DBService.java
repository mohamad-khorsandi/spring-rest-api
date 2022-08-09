package ir.sobhan.business.DBService;

import ir.sobhan.business.exception.BadInputException;
import ir.sobhan.business.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DBService<ENTITY> {
    final protected JpaRepository<ENTITY, Long> repository;

    public List<ENTITY> getList(int pageNumber, int pageSize) {
        return repository.findAll(PageRequest.of(pageNumber, pageSize)).stream().collect(Collectors.toList());
    }

    public ENTITY getById(Long id) throws NotFoundException {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void save(ENTITY entity) throws BadInputException {
        try {
            repository.save(entity);
        } catch (RuntimeException e) {
            throw new BadInputException();
        }
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void throwIfNotExists(Long id) throws NotFoundException {
        if (!repository.existsById(id))
            throw new NotFoundException(id.toString());
    }
}

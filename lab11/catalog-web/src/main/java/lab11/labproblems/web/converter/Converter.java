package lab11.labproblems.web.converter;

import lab11.labproblems.core.model.entities.BaseEntity;
import lab11.labproblems.web.dto.BaseDto;


public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {

    Model convertDtoToModel(Dto dto);

    Dto convertModelToDto(Model model);

}


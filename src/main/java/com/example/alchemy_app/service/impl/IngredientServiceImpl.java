package com.example.alchemy_app.service.impl;

import com.example.alchemy_app.dto.ingredient.IngredientCreateDto;
import com.example.alchemy_app.dto.ingredient.IngredientResponseDto;
import com.example.alchemy_app.entity.Ingredient;
import com.example.alchemy_app.exception.EntityNotExistException;
import com.example.alchemy_app.mapper.IngredientMapper;
import com.example.alchemy_app.repository.IngredientRepository;
import com.example.alchemy_app.service.EmailSenderService;
import com.example.alchemy_app.service.IngredientService;
import com.example.alchemy_app.service.UserIngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final EmailSenderService emailSenderService;
    private final UserIngredientService userIngredientService;

    @Override
    @Transactional
    public void createIngredient(IngredientCreateDto ingredientDto) {
        Ingredient newIngredient;
        if (ingredientDto.getIngredientIds() == null || ingredientDto.getIngredientIds().isEmpty()) {
            newIngredient = ingredientRepository.save(newBasicIngredient(ingredientDto));
            userIngredientService.addNewBasicIngredientToUsers(newIngredient);
        } else {
            newIngredient = ingredientRepository.save(newRecipeIngredient(ingredientDto));
        }
        log.info("New ingredient with id: " + newIngredient.getId() + ", saved successfully.");
        emailSenderService.sendNotificationAboutNewIngredient(newIngredient);
    }

    @Override
    public Page<IngredientResponseDto> getAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable).map(ingredientMapper::fromEntityToDto);
    }

    @Override
    public IngredientResponseDto getIngredientById(Long id) {
        return ingredientMapper.fromEntityToDto(ingredientRepository.findById(id).
                orElseThrow(()->new EntityNotExistException("Ingredient with such id: " + id + ", not exist.")));
    }

    private Ingredient newBasicIngredient(IngredientCreateDto ingredientDto) {
        if (ingredientDto.getPrice()!=0 || ingredientDto.getLossProbability()!=0) {
            throw new ValidationException("Loss probability and price of basic ingredient must be equal 0.");
        }
        return ingredientMapper.fromDtoToEntity(ingredientDto);
    }

    private Ingredient newRecipeIngredient(IngredientCreateDto ingredientDto) {
        List<Ingredient> ingredientsFromIngredientCreated = ingredientRepository.findAllById(ingredientDto.getIngredientIds());
        Ingredient newRecipeIngredient = ingredientMapper.fromDtoToEntity(ingredientDto);
        newRecipeIngredient.setIngredients(ingredientsFromIngredientCreated);
        return newRecipeIngredient;
    }
}

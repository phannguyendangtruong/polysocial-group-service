package com.polysocial.service;

import java.util.List;

import com.polysocial.dto.ExercisesDTO;
import com.polysocial.entity.Exercises;

public interface ExercisesService {
    
    ExercisesDTO createOne(ExercisesDTO exercise);

    ExercisesDTO updateOne(ExercisesDTO exercise);

    ExercisesDTO deleteOne(Long exId);

    List<ExercisesDTO> getAllExercisesEndDate(Long groupId);

}

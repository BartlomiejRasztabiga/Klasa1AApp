package pl.rasztabiga.klasa1a.data.source.exams;


import android.support.annotation.Nullable;

import java.util.List;

import pl.rasztabiga.klasa1a.data.source.exams.models.Exam;

public interface ExamsDataSource {

    @Nullable
    List<Exam> getExams();

    void refreshExams();

}

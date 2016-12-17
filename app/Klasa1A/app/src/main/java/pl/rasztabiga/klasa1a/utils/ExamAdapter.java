package pl.rasztabiga.klasa1a.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.R;
import pl.rasztabiga.klasa1a.models.Exam;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private static final String TAG = ExamAdapter.class.getSimpleName();
    private ArrayList<Exam> examList;

    public ExamAdapter() {}

    @Override
    public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.exam_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new ExamViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(examList == null) return 0;
        return examList.size();
    }

    @Override
    public void onBindViewHolder(ExamViewHolder holder, int position) {
        Exam exam = examList.get(position);
        holder.mExamSubjectTextView.setText(exam.getSubject());
        holder.mExamDescTextView.setText(exam.getDesc());
    }

    public void setExamsData(List<Exam> examsList) {
        examList = new ArrayList<>(examsList);

        notifyDataSetChanged();
    }

    class ExamViewHolder extends RecyclerView.ViewHolder {

        public final LinearLayout mExamData;
        public final TextView mExamSubjectTextView;
        public final TextView mExamDescTextView;


        public ExamViewHolder(View itemView) {
            super(itemView);
            mExamData = (LinearLayout) itemView.findViewById(R.id.exam_data);
            mExamSubjectTextView = (TextView) itemView.findViewById(R.id.tv_exam_subject);
            mExamDescTextView = (TextView) itemView.findViewById(R.id.tv_exam_desc);

        }

    }


}

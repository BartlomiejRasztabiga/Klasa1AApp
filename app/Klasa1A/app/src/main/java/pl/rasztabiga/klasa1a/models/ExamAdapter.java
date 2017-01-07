package pl.rasztabiga.klasa1a.models;

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

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private static final String TAG = ExamAdapter.class.getSimpleName();
    final private ExamClickListener onClickListener;
    private ArrayList<Exam> examList;

    public ExamAdapter(ExamClickListener listener) {
        this.onClickListener = listener;
    }

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
        if (examList == null) return 0;
        return examList.size();
    }

    public ArrayList<Exam> getExamList() {
        return examList;
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

    public interface ExamClickListener {
        void onExamClick(int clickedItemIndex);
    }

    class ExamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final LinearLayout mExamData;
        public final TextView mExamSubjectTextView;
        public final TextView mExamDescTextView;


        public ExamViewHolder(View itemView) {
            super(itemView);
            mExamData = (LinearLayout) itemView.findViewById(R.id.exam_data);
            mExamSubjectTextView = (TextView) itemView.findViewById(R.id.tv_exam_subject);
            mExamDescTextView = (TextView) itemView.findViewById(R.id.tv_exam_desc);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            onClickListener.onExamClick(clickedPosition);
        }
    }


}

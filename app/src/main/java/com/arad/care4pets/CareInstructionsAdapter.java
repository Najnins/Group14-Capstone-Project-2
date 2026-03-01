package com.arad.care4pets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CareInstructionsAdapter extends RecyclerView.Adapter<CareInstructionsAdapter.ViewHolder> {

    private List<CareInstruction> instructions = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_care_instruction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CareInstruction instruction = instructions.get(position);
        holder.instructionText.setText(instruction.getInstruction());
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public void setInstructions(List<CareInstruction> instructions) {
        this.instructions = instructions;
        notifyDataSetChanged(); // For simplicity, though more specific methods are better
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView instructionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instructionText = itemView.findViewById(R.id.tvInstruction);
        }
    }
}

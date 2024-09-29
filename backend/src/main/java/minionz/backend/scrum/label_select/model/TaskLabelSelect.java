package minionz.backend.scrum.label_select.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.scrum.label.model.TaskLabel;
import minionz.backend.scrum.task.model.Task;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TaskLabelSelect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskLabelSelectId;

    // TaskLabelSelect : TaskLabel = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_label_id")
    private TaskLabel taskLabel;

    // TaskLabelSelect : Task = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}

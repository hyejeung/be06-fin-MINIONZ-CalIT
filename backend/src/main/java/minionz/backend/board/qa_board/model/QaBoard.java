package minionz.backend.board.qa_board.model;

import jakarta.persistence.*;
import lombok.*;
import minionz.backend.board.qa_comment.model.QaComment;
import minionz.backend.scrum.task.model.Task;
import minionz.backend.scrum.workspace.model.Workspace;
import minionz.backend.user.model.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class QaBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qaBoardId;
    private String qaboardTitle;
    private String qaboardContent;


    @Enumerated(EnumType.ORDINAL)
    private AnswerStatus answerStatus; //답변상태

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "qaBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QaComment> commentList= new ArrayList<>();
    @OneToMany(mappedBy = "qaBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QaBoardImage> qaBoardImageList= new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workSpace;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



}

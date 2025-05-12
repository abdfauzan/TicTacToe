package com.tictactoe.game.model;

import com.tictactoe.game.common.DateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "tictactoe_schema", name = "board_size_configuration")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardSizeConfiguration {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "value", nullable = false)
    private int value;

    @Column(name = "label", length = 128, nullable = false)
    private String label;

    @Column(name = "created_by", nullable = false, length = 64)
    private String createdBy;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = DateTime.now();

    @Column(name = "updated_by", nullable = true, length = 64)
    private String updatedBy;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = DateTime.now();
    }
}


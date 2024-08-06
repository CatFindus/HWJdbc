package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message implements Entity{
    private Integer id;
    private Integer userId;
    private Integer chatId;
    private String message;
}

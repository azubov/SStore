package ru.lanit.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String itemName;
    private String partNumber;
    private String price;
    private String categoryName;
    private String chosenColor;
    private String uploadedImageName;
}

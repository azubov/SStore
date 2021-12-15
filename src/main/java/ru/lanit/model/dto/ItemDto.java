package ru.lanit.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lanit.model.entity.Item;

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

    public void populateWith(Item itemFromDb) {
        this.id = itemFromDb.getId();

        if (this.itemName == null) {
            this.itemName = itemFromDb.getName();
        }
        if (this.partNumber == null) {
            this.partNumber = itemFromDb.getPartNumber();
        }
        if (this.price == null) {
            this.price = itemFromDb.getPrice().toString();
        }
        if (this.categoryName == null) {
            this.categoryName = itemFromDb.getCategory().getName();
        }
        if (this.chosenColor == null) {
            this.chosenColor = itemFromDb.getColor();
        }
        if (this.uploadedImageName == null) {
            this.uploadedImageName = itemFromDb.getImageUrl();
        }
    }
}

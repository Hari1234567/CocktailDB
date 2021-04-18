package com.example.cocktaildb2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Cocktails")
public class CocktailFavData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    private String Name;
    private String Category;
    private String Alcoholic;
    private String Instructions;
    private String ing1,ing2,ing3,ing4,ing5,ing6,ing7,ing8,ing9,ing10,ing11,ing12,ing13,ing14,ing15;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getAlcoholic() {
        return Alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        Alcoholic = alcoholic;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public String getIng1() {
        return ing1;
    }

    public void setIng1(String ing1) {
        this.ing1 = ing1;
    }

    public String getIng2() {
        return ing2;
    }

    public void setIng2(String ing2) {
        this.ing2 = ing2;
    }

    public String getIng3() {
        return ing3;
    }

    public void setIng3(String ing3) {
        this.ing3 = ing3;
    }

    public String getIng4() {
        return ing4;
    }

    public void setIng4(String ing4) {
        this.ing4 = ing4;
    }

    public String getIng5() {
        return ing5;
    }

    public void setIng5(String ing5) {
        this.ing5 = ing5;
    }

    public String getIng6() {
        return ing6;
    }

    public void setIng6(String ing6) {
        this.ing6 = ing6;
    }

    public String getIng7() {
        return ing7;
    }

    public void setIng7(String ing7) {
        this.ing7 = ing7;
    }

    public String getIng8() {
        return ing8;
    }

    public void setIng8(String ing8) {
        this.ing8 = ing8;
    }

    public String getIng9() {
        return ing9;
    }

    public void setIng9(String ing9) {
        this.ing9 = ing9;
    }

    public String getIng10() {
        return ing10;
    }

    public void setIng10(String ing10) {
        this.ing10 = ing10;
    }

    public String getIng11() {
        return ing11;
    }

    public void setIng11(String ing11) {
        this.ing11 = ing11;
    }

    public String getIng12() {
        return ing12;
    }

    public void setIng12(String ing12) {
        this.ing12 = ing12;
    }

    public String getIng13() {
        return ing13;
    }

    public void setIng13(String ing13) {
        this.ing13 = ing13;
    }

    public String getIng14() {
        return ing14;
    }

    public void setIng14(String ing14) {
        this.ing14 = ing14;
    }

    public String getIng15() {
        return ing15;
    }

    public void setIng15(String ing15) {
        this.ing15 = ing15;
    }
}

package com.willyborja.WorkingCalculator.DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReaderDAO {

    private File file;
    private Scanner reader;

    //Open a specific file
    public void openFile(){
        try{
            file = new File("C://Users//Bor_W//Desktop//Doc.txt");
            reader = new Scanner(file);
        }catch(FileNotFoundException e) {
            System.out.println("Error!");
        }
    }

    //Read the file content and store data inside a List
    public List<String> readFile(){
        List<String> data = new ArrayList<>();
        while(reader.hasNextLine()){
            data.add(reader.nextLine());
        }
        return data;
    }

    //Closes the opened file
    public void closeFile(){
        if(reader!=null){
            reader.close();
        }
    }



}
